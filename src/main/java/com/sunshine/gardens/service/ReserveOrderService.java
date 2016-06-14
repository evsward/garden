package com.sunshine.gardens.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dance.core.orm.BaseDao;
import com.dance.core.service.BaseServiceImpl;
import com.dance.core.utils.spring.SpringContextHolder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.sunshine.gardens.dao.CmsColumnDao;
import com.sunshine.gardens.dao.ProductDao;
import com.sunshine.gardens.dao.ReserveOrderDao;
import com.sunshine.gardens.exception.BusinessException;
import com.sunshine.gardens.exception.BusinessException.ErrorKey;
import com.sunshine.gardens.model.po.CmsColumn;
import com.sunshine.gardens.model.po.Product;
import com.sunshine.gardens.model.po.ReserveOrder;
import com.sunshine.gardens.model.pojo.MemberInfo;
import com.sunshine.gardens.utils.CommonUtil;
import com.sunshine.gardens.utils.HttpAPIClient;
import com.sunshine.gardens.utils.MyPropertyPlaceholderConfigurer;
import com.sunshine.gardens.utils.SignUtil;
import com.sunshine.gardens.utils.ThreeDES;
import com.sunshine.gardens.utils.XmlUtil;
import com.sunshine.gardens.utils.webservice.IReservationServiceProxy;

import net.sf.json.JSONObject;

@Service
public class ReserveOrderService extends BaseServiceImpl<ReserveOrder, Integer> {
	@Autowired
	private ReserveOrderDao reserveOrderDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private CmsColumnDao columnDao;
	@Autowired
	private VipInfoService vipInfoService;
	private static IReservationServiceProxy IReservationServiceProxy = new IReservationServiceProxy();
	private final static MyPropertyPlaceholderConfigurer configrue = SpringContextHolder.getBean("propertyPlaceholder");

	@Override
	public BaseDao<ReserveOrder, Integer> getDao() {
		return reserveOrderDao;
	}

	// 1、调用统一下单API()，获取prepay_id
	public Map<String, Object> unifiedOrder(String reserveOrderId, String userIp) throws Exception {
		logger.info("开始支付，订单号：" + reserveOrderId);
		Map<String, Object> resultMap = new TreeMap<String, Object>();
		ReserveOrder rOrder = reserveOrderDao.find(new ReserveOrder(reserveOrderId)).get(0);
		Product product = productDao.find(new Product(rOrder.getProductId())).get(0);

		CmsColumn cColumn = columnDao.find(new CmsColumn(product.getColumnId())).get(0);
		String sign = "";
		Map<String, Object> paramMap = new TreeMap<String, Object>();
		paramMap.put("appid", configrue.getProp("appId"));
		paramMap.put("mch_id", configrue.getProp("mch_id"));
		paramMap.put("device_info", "WEB");
		paramMap.put("nonce_str", CommonUtil.getRandomString(32));
		paramMap.put("body", cColumn.getColumnName() + "-" + product.getProductName());
		paramMap.put("out_trade_no", reserveOrderId);
		paramMap.put("total_fee", rOrder.getTotalMoney() * 100);
		paramMap.put("spbill_create_ip", userIp);
		paramMap.put("notify_url", configrue.getProp("wechat.notify.url"));
		paramMap.put("trade_type", "JSAPI");
		paramMap.put("openid", rOrder.getOpenid());
		// 签名前的明文
		sign = SignUtil.createLinkString(paramMap) + "&key=" + configrue.getProp("md5_key");
		logger.info("----------签名前的明文=" + sign);
		// 签名后的密文
		sign = SignUtil.md5Str(sign).toUpperCase();
		logger.info("----------签名后的密文=" + sign);
		paramMap.put("sign", sign);
		String prepay_id = "";
		Map<String, Object> mapRes = new TreeMap<String, Object>();
		mapRes = sendPost(configrue.getProp("unified_url"), paramMap);
		if ("SUCCESS".equals(mapRes.get("return_code"))) {
			prepay_id = mapRes.get("prepay_id").toString();
		} else {
			throw new BusinessException(new ErrorKey(mapRes.get("return_msg").toString()));
		}
		resultMap.put("appId", configrue.getProp("appId"));
		resultMap.put("nonceStr", CommonUtil.getRandomString(32));
		resultMap.put("package", "prepay_id=" + prepay_id);
		resultMap.put("timeStamp", System.currentTimeMillis() / 1000);
		resultMap.put("signType", "MD5");
		String paySign = "";
		// 签名前的明文
		paySign = SignUtil.createLinkString(resultMap) + "&key=" + configrue.getProp("md5_key");
		logger.info("----------paySign签名前的明文=" + paySign);
		// 签名后的密文
		paySign = SignUtil.md5Str(paySign).toUpperCase();
		logger.info("----------paySign签名后的密文=" + paySign);
		resultMap.put("paySign", paySign);

		return resultMap;
	}

	public Map<String, Object> sendPost(String url, Map<String, Object> xmlMap) {
		// 1、发送请求二维码 将要提交给API的数据对象转换成XML格式数据Post给API
		StringBuffer postDataXML = new StringBuffer();
		postDataXML.append("<xml>\n");
		for (Map.Entry<String, Object> entry : xmlMap.entrySet()) {
			if ("sign".equals(entry.getKey())) {
				continue;
			}
			postDataXML.append("\t<" + entry.getKey() + ">").append(entry.getValue())
					.append("</" + entry.getKey() + ">\n");
		}
		postDataXML.append("\t<sign>").append(xmlMap.get("sign")).append("</sign>\n");
		postDataXML.append("</xml>\n");
		// 2、接收返回二维码数据
		String res = HttpAPIClient.sendXMLPost(postDataXML.toString(), url);
		Map<String, Object> mapRes = new TreeMap<String, Object>();
		if (!StringUtils.isEmpty(res) && XmlUtil.isXmlStr(res, "utf-8")) {
			mapRes = XmlUtil.xmlStr2MapObjParent(res);
			String retCode = (String) mapRes.get("return_code");
			if (retCode != null && retCode.equals("FAIL")) {
				logger.info("统一下单支付失败原因：" + mapRes.get("return_msg"));
				return mapRes;
			}
			String resSign = (String) mapRes.remove("sign");
			mapRes = SignUtil.paraFilter(mapRes);
			String checkSign = "";
			// 签名前的明文
			checkSign = SignUtil.createLinkString(mapRes) + "&key=" + configrue.getProp("md5_key");
			// 签名后的密文
			try {
				checkSign = SignUtil.md5Str(checkSign).toUpperCase();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (!checkSign.equals(resSign)) {
				logger.error("返回二维码参数验签失败");
			}
		} else {
			logger.error("请求二维码通信失败");
		}
		return mapRes;
	}

	// 应该将{网络地址}存到预订订单表中，新增字段{QRCODEPATH}。
	public String generateQRCode(String reserveId, String requestURL) throws Exception {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("orderId", reserveId);
		String qrCodePicRelativePath = "static/QRCodePics/";
		String filePath = "/home/work/service/garden/web/" + qrCodePicRelativePath;
		String fileName = reserveId + "qrcodepic.png";
		File file = new File(filePath, fileName);
		logger.info("开始创建订单号二维码图片，文件路径: " + file.getAbsolutePath());
		// 判断目标文件所在的目录是否存在
		if (!file.getParentFile().exists()) {
			if (!file.getParentFile().mkdirs()) {
				throw new Exception("创建目标文件所在目录失败！");
			}
		}
		int width = 200; // 图像宽度
		int height = 200; // 图像高度
		String format = "png";// 图像类型
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(ThreeDES.encryptThreeDESECB(jsonObj.toString()),
				BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
		MatrixToImageWriter.writeToFile(bitMatrix, format, file);// 输出图像
		String qrCodePath = requestURL.split("garden")[0] + "garden/" + qrCodePicRelativePath + fileName;
		logger.info("订单号二维码生成网络地址：" + qrCodePath);
		return qrCodePath;
	}

	public String vipPay(String reserveOrderId, String vipId) throws Exception {
		ReserveOrder rOrder = reserveOrderDao.find(new ReserveOrder(reserveOrderId)).get(0);
		MemberInfo me = vipInfoService.getBalanceInfo(vipId);
		Double balance = me.getBalance();
		if (rOrder.getTotalMoney() > balance) {
			logger.error("VIP会员卡支付，余额不足!");
			return "VIP会员卡支付，余额不足!";
		}
		StringBuffer xml = new StringBuffer();
		xml.append(XmlUtil.xmlWSHeadStr(1030));
		xml.append("<Body><ReservationOperator><confirmationID>");
		xml.append(reserveOrderId);
		xml.append("</confirmationID><paycode>");
		xml.append(configrue.getProp("payCode"));
		xml.append("</paycode><CashOrderNo>");
		xml.append(reserveOrderId);
		xml.append("</CashOrderNo><amount>");
		xml.append(rOrder.getTotalMoney());
		xml.append("</amount><Operator>");
		xml.append(configrue.getProp("payOperator"));
		xml.append("</Operator><account>");
		xml.append(me.getArAccount());
		xml.append("</account></ReservationOperator></Body></Request>");
		String payObjStr = IReservationServiceProxy.reservationAccountment(xml.toString());
		Map<String, Object> xmlObjMap = new HashMap<String, Object>();
		if (XmlUtil.isXmlStr(payObjStr, "utf-8")) {
			xmlObjMap = XmlUtil.xmlStr2MapObjParent(payObjStr);
			logger.info(xmlObjMap.get("retmsg").toString());
			if ("00001".equals(xmlObjMap.get("retcode"))) {// 成功
				logger.info("卡号：" + vipId + ",VIP会员卡支付成功！");
				return "1";
			}else{
				return xmlObjMap.get("retmsg").toString();
			}
		}
		return null;
	}
}
