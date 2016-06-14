package com.sunshine.gardens.controller.h5client;

import java.io.BufferedInputStream;
import java.nio.ByteBuffer;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dance.core.utils.BaseAppException;
import com.dance.core.utils.spring.SpringContextHolder;
import com.sunshine.gardens.model.WCPayRequest;
import com.sunshine.gardens.model.po.CmsColumn;
import com.sunshine.gardens.model.po.Product;
import com.sunshine.gardens.model.po.ReserveOrder;
import com.sunshine.gardens.model.po.VipInfo;
import com.sunshine.gardens.model.pojo.MemberInfo;
import com.sunshine.gardens.model.pojo.Ticket;
import com.sunshine.gardens.model.pojo.Token;
import com.sunshine.gardens.service.ColumnService;
import com.sunshine.gardens.service.ProductService;
import com.sunshine.gardens.service.ReserveOrderService;
import com.sunshine.gardens.service.VipInfoService;
import com.sunshine.gardens.utils.CommonUtil;
import com.sunshine.gardens.utils.Const;
import com.sunshine.gardens.utils.HttpAPIClient;
import com.sunshine.gardens.utils.MyPropertyPlaceholderConfigurer;
import com.sunshine.gardens.utils.SignUtil;
import com.sunshine.gardens.utils.XmlUtil;

@Controller
@RequestMapping("/h5client/bookroom")
public class BookRoomController {
	private final static Log logger = LogFactory.getLog(BookRoomController.class);
	private final static MyPropertyPlaceholderConfigurer configrue = SpringContextHolder.getBean("propertyPlaceholder");

	@Autowired
	private ProductService productService;
	@Autowired
	private ColumnService columnService;
	@Autowired
	private ReserveOrderService reserveOrderService;
	@Autowired
	private VipInfoService vipInfoService;

	private Date checkinDate;
	private int nights;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd EEEE");
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmSS");

	@RequestMapping
	public String loadIndex(ModelMap map, HttpSession session) {
		logger.info("进入酒店预订");
		if (checkinDate == null) {
			checkinDate = new Date();
		}
		map.addAttribute("checkinDate", checkinDate);
		map.addAttribute("nights", nights);
		map.addAttribute("checkoutDate", CommonUtil.calculateDate(checkinDate, nights));
		return "pages/bookroom/reservation";
	}

	private void checkDate(String startTime, String endTime) {
		try {
			if (startTime == null || endTime == null) {
				logger.info("数据出错。。。" + startTime + "  " + endTime);
				return;
			}
			checkinDate = sdf.parse(startTime);
			Date endDate = sdf.parse(endTime);
			long time = endDate.getTime() - checkinDate.getTime();
			nights = (int) (time / (24 * 60 * 60 * 1000));
		} catch (Exception e) {
			logger.error("数据处理失败。。。", e);
		}
	}

	@RequestMapping(value = "/holidayHotelReservation")
	public String holidayHotelReservation(String startTime, String endTime, ModelMap map) throws BaseAppException {
		Product holidyHotel = new Product();
		List<Product> list = new ArrayList<Product>();
		holidyHotel.setColumnId(Long.valueOf(configrue.getProp("HOLIDAY_HOTEL_COLUMNID")));
		holidyHotel.setProductState(Integer.parseInt(configrue.getProp("positive")));
		list = productService.find(holidyHotel);
		checkDate(startTime, endTime);
		map.addAttribute("allProducts", list);
		if (checkinDate == null) {
			checkinDate = new Date();
		}
		map.addAttribute("checkinDate", checkinDate);
		map.addAttribute("nights", nights);
		map.addAttribute("checkoutDate", CommonUtil.calculateDate(checkinDate, nights));
		return "pages/bookroom/holidayHotelReservation";
	}

	@RequestMapping(value = "/timeChoose")
	public String timeChoose(ModelMap map, String return_url) {
		Date now = new Date();
		map.addAttribute("startDate", sdf.format(now));
		map.addAttribute("endDate",
				sdf.format(CommonUtil.calculateDate(now, Integer.parseInt(configrue.getProp("VALID_DAYS")))));
		map.addAttribute("maxNights", configrue.getProp("VALID_DAYS"));
		map.addAttribute("nights", nights);
		if (checkinDate == null) {
			checkinDate = new Date();
		}
		map.addAttribute("checkinDate", checkinDate);
		map.addAttribute("checkoutDate", CommonUtil.calculateDate(checkinDate, nights));
		map.addAttribute("return_url", return_url);
		return "pages/bookroom/timeChoose";
	}

	@RequestMapping(value = "/changeTimeChoose")
	public String changeTimeChoose(String user_checkinDate, int user_nights, String returnUrl) throws ParseException {
		checkinDate = sdf.parse(user_checkinDate);
		nights = user_nights;
		return "redirect:" + returnUrl;
	}

	@RequestMapping(value = "/inputOrder")
	public String inputOrder(ModelMap map, int productId, HttpSession session) throws BaseAppException {
		// TODO 留一个口，未来在这里根据会员卡改价格~
		String openId = (String) session.getAttribute("openId");
		if (openId == null) {
			// openId = "oZU-Rs1c1AuixNLwI1c6aqWT_Tx8";// 本地调试
			logger.error("系统无法获得微信ID，无法查看！");
			map.addAttribute("msg", "系统无法获得您的微信ID，无法查看！");
			return "pages/bookroom/orderFailed";
		}
		logger.info("查询vip卡，openid：" + openId);
		VipInfo info = new VipInfo();
		info.setOpenid(openId);
		try {
			List<MemberInfo> members = new ArrayList<MemberInfo>();
			List<VipInfo> infos = vipInfoService.find(info);
			for (VipInfo v : infos) {
				String vipID = v.getVipId();
				// 传入vipID调用西软GetMemberInfo接口，查询会员信息ArrayList<VipInfo>，返回到“我的会员卡”主页
				MemberInfo m = vipInfoService.getWSMemberInfo(vipID);
				if (m == null) {
					logger.error("未获取到vip卡相关信息！");
					map.addAttribute("msg", "未获取到vip卡相关信息！");
					return "pages/bookroom/orderFailed";
				}
				members.add(m);
			}
			map.addAttribute("members", members);
		} catch (BaseAppException e) {
			logger.error("查询vip卡失败", e);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		map.addAttribute("nights", nights);
		if (checkinDate == null) {
			checkinDate = new Date();
		}
		map.addAttribute("vipType", "非周末价");
		map.addAttribute("checkinDate", checkinDate);
		map.addAttribute("checkoutDate", CommonUtil.calculateDate(checkinDate, nights));
		Product pd = new Product();
		pd.setProductId(productId);
		List<Product> productDetails = new ArrayList<Product>();
		Product product = productService.find(pd).get(0);
		if (product.getInventory() < 1) {
			map.addAttribute("msg", "该房型已订满！");
			logger.error("该房型已订满！");
			return "pages/bookroom/orderFailed";
		}
		map.addAttribute("product", product);
		int totalMoney = 0;
		for (int i = 0; i < nights; i++) {
			Date oneDay = CommonUtil.calculateDate(checkinDate, i);
			Calendar cal = Calendar.getInstance();
			cal.setTime(oneDay);
			Product detail = new Product();
			detail.setValidFrom(oneDay);
			if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				detail.setUnitPrice(product.getHolidayPrice());
			} else {
				detail.setUnitPrice(product.getWorkdayPrice());
			}
			totalMoney += detail.getUnitPrice();
			productDetails.add(detail);
		}
		map.addAttribute("productDetails", productDetails);
		map.addAttribute("totalMoney", totalMoney);
		return "pages/bookroom/inputOrder";
	}

	/**
	 * 我的会员卡
	 * 
	 * @param map
	 * @param session
	 * @return
	 * @throws RemoteException
	 */
	@RequestMapping(value = "/findVipInfo")
	public String findVipInfo(ModelMap map, HttpSession session) throws RemoteException {
		String openId = (String) session.getAttribute("openId");
		if (openId == null) {
			// openId = "oZU-Rs1c1AuixNLwI1c6aqWT_Tx8";// 本地调试
			logger.error("系统无法获得微信ID，无法查看！");
			map.addAttribute("msg", "系统无法获得您的微信ID，无法查看！");
			return "pages/bookroom/orderFailed";
		}
		logger.info("查询vip卡，openid：" + openId);
		VipInfo info = new VipInfo();
		info.setOpenid(openId);
		try {
			List<MemberInfo> members = new ArrayList<MemberInfo>();
			List<VipInfo> infos = vipInfoService.find(info);
			if (infos.size() == 0) {
				// 跳转到绑定页面
				return "redirect:/h5client/index/bind";
			} else {// 已有卡被绑定
				for (VipInfo v : infos) {
					String vipID = v.getVipId();
					// 传入vipID调用西软GetMemberInfo接口，查询会员信息ArrayList<VipInfo>，返回到“我的会员卡”主页
					MemberInfo m = vipInfoService.getWSMemberInfo(vipID);
					if (m == null) {
						logger.error("未获取到vip卡相关信息！");
						map.addAttribute("msg", "未获取到vip卡相关信息！");
						return "pages/bookroom/orderFailed";
					}
					members.add(m);
				}
				map.addAttribute("members", members);
				return "pages/bookroom/vipInfo";
			}
		} catch (BaseAppException e) {
			logger.error("查询vip卡失败", e);
		}
		return null;
	}

	@RequestMapping(value = "/checkVip")
	@ResponseBody
	public ReserveOrder checkVip(ModelMap map, ReserveOrder reserveOrder, HttpSession session) throws Exception {
		String openId = (String) session.getAttribute("openId");
		// String openId = "oZU-Rs1c1AuixNLwI1c6aqWT_Tx8";
		logger.info("预定订单，openid：" + openId);
		// if (openId == null) {
		// return null;
		// }
		// 1、西软接口，查询会员类型
		int vipCartType = productService.checkVipService(reserveOrder.getVipId(), reserveOrder.getPassword());
		// logger.info("====== vipCartType==" + vipCartType);
		// if (vipCartType != Const.NOVIP) {
		// VipInfo findInfo = new VipInfo();
		// findInfo.setIdNumber("" + reserveOrder.getVipId());
		// findInfo.setOpenid(openId);
		// List<VipInfo> list = vipInfoService.find(findInfo);
		// if (list != null && !list.isEmpty()) {
		// logger.info("此卡已绑定=========");
		// } else {
		// VipInfo info = new VipInfo();
		// String vipName = "";
		// switch (vipCartType) {
		// case Const.SILVER:
		// vipName = "白金卡会员";
		// break;
		// case Const.GOLD:
		// vipName = "金卡会员";
		// case Const.INFINITE:
		// vipName = "无限卡会员";
		// default:
		// break;
		// }
		// info.setOpenid(openId);
		// info.setVipName(vipName);
		// info.setIdNumber(reserveOrder.getVipId() + "");
		// vipInfoService.insert(info);
		// logger.info("VIP验证成功，插入一条数据......" + info.getIdNumber());
		// }
		// }
		map.addAttribute("reserveOrder", reserveOrder);
		Product pd = new Product();
		pd.setProductId(reserveOrder.getProductId());
		List<Product> productDetails = new ArrayList<Product>();
		Product product = productService.find(pd).get(0);
		long totalMoney = 0;
		for (int i = 0; i < nights; i++) {
			Date oneDay = CommonUtil.calculateDate(checkinDate, i);
			Calendar cal = Calendar.getInstance();
			cal.setTime(oneDay);
			Product detail = new Product();
			detail.setValidDay(sdf1.format(oneDay));
			switch (vipCartType) {
			case Const.SILVER:
				detail.setUnitPrice(product.getSilverPrice());
				reserveOrder.setVipType("白金卡会员");
				break;
			case Const.GOLD:
				detail.setUnitPrice(product.getGoldPrice());
				reserveOrder.setVipType("金卡会员");
				break;
			case Const.INFINITE:
				detail.setUnitPrice(product.getInfinitePrice());
				reserveOrder.setVipType("无限卡会员");
				break;
			default:
				reserveOrder.setVipType("非周末价");
				if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
						|| cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
					detail.setUnitPrice(product.getHolidayPrice());
				} else {
					detail.setUnitPrice(product.getWorkdayPrice());
				}
				break;
			}
			totalMoney += detail.getUnitPrice();
			productDetails.add(detail);
		}
		reserveOrder.setTotalMoney(totalMoney);
		reserveOrder.setProductDetails(productDetails);
		return reserveOrder;
	}

	@RequestMapping(value = "/findMyOrders")
	public String findMyOrders(ModelMap map, HttpSession session) throws BaseAppException {
		String openId = (String) session.getAttribute("openId");
		logger.info("预定订单，openid：" + openId);
		if (openId == null) {
			map.addAttribute("msg", "系统无法获得您的微信ID，无法预定！");
			logger.error("系统无法获得微信ID，无法查看！");
			return "pages/bookroom/orderFailed";
		}
		ReserveOrder ro = new ReserveOrder();
		ro.setOpenid(openId);
		List<ReserveOrder> rOrderList = reserveOrderService.find(ro);
		List<ReserveOrder> rOrderResultList = new ArrayList<ReserveOrder>();
		for (ReserveOrder rOrder : rOrderList) {
			Product product = productService.find(new Product(rOrder.getProductId())).get(0);
			if (product.getProductType() == 1) {// 客房产品
				CmsColumn cc = new CmsColumn();
				cc.setColumnId(product.getColumnId());
				CmsColumn column = columnService.find(cc).get(0);
				rOrder.setProductName(product.getProductName());
				rOrder.setColumnName(column.getColumnName());
				rOrderResultList.add(rOrder);
			}
		}
		map.addAttribute("rOrderList", rOrderResultList);
		return "pages/bookroom/myOrders";
	}

	@RequestMapping(value = "/hsorderFinish")
	public String hsorderFinish(ModelMap map, ReserveOrder reserveOrder, HttpSession session, HttpServletRequest request)
			throws Exception {
		String openId = (String) session.getAttribute("openId");
		logger.info("预定订单，openid：" + openId);
		if (openId == null) {
			map.addAttribute("msg", "系统无法获得您的微信ID，无法预定！");
			logger.error("系统无法获得微信ID，无法查看！");
			return "pages/bookroom/orderFailed";
		}
		Product pd = new Product();
		pd.setProductId(reserveOrder.getProductId());
		Product product = productService.find(pd).get(0);
		// 库存检查
		int left = product.getInventory();
		if (left <= 1) {
			map.addAttribute("msg", "库存不足，无法预定！");
			logger.error("库存不足，无法预定！");
			return "pages/bookroom/orderFailed";
		}
		String reserveOrderId = sdf2.format(new Date()) + CommonUtil.getRandomString(12);
		Date today = sdf.parse(sdf.format(new Date()));
		Date deadline = CommonUtil.calculateDate(today, 30);// 温泉门票，30天有效期
		// 生成订单号二维码图片
		String qrCodePath = reserveOrderService.generateQRCode(reserveOrderId, request.getRequestURL().toString());
		reserveOrder.setReserveId(reserveOrderId);
		reserveOrder.setOpenid(openId);
		reserveOrder.setOrderState(Integer.parseInt(configrue.getProp("order.state.init")));
		reserveOrder.setCreateTime(new Date());
		reserveOrder.setProductType(2);// 温泉会产品
		reserveOrder.setQrCodePath(qrCodePath);
		reserveOrder.setCheckoutDate(deadline);
		reserveOrderService.insert(reserveOrder);
		map.addAttribute("reserveId", reserveOrder.getReserveId());
		return "pages/hotspring/success";
	}

	@RequestMapping(value = "/orderFinish")
	public String orderFinish(ModelMap map, ReserveOrder reserveOrder, HttpSession session, HttpServletRequest request)
			throws Exception {
		String openId = (String) session.getAttribute("openId");
		logger.info("预定订单，openid：" + openId);
		if (openId == null) {
			// openId = "oZU-Rs1c1AuixNLwI1c6aqWT_Tx8";
			map.addAttribute("msg", "系统无法获得您的微信ID，无法预定！");
			logger.error("系统无法获得微信ID，无法预定！");
			return "pages/bookroom/orderFailed";
		}
		if (nights == 0) {
			map.addAttribute("msg", "您选择的房晚不可为0！");
			logger.error("选择的房晚不可为0！");
			return "pages/bookroom/orderFailed";
		}
		String reserveOrderId = sdf2.format(new Date()) + CommonUtil.getRandomString(12);
		// 生成订单号二维码图片
		String qrCodePath = reserveOrderService.generateQRCode(reserveOrderId, request.getRequestURL().toString());
		reserveOrder.setReserveId(reserveOrderId);
		reserveOrder.setOpenid(openId);
		reserveOrder.setOrderState(Integer.parseInt(configrue.getProp("order.state.init")));
		reserveOrder.setCreateTime(new Date());
		reserveOrder.setProductType(1);// 客房产品

		Product pd = new Product();
		pd.setProductId(reserveOrder.getProductId());
		List<Product> productDetails = new ArrayList<Product>();
		Product product = productService.find(pd).get(0);
		// 库存检查
		int left = product.getInventory();
		if (left <= 1) {
			map.addAttribute("msg", "库存不足，无法预定！");
			logger.error("库存不足，无法预定！");
			return "pages/bookroom/orderFailed";
		}
		long totalMoney = 0;
		for (int i = 0; i < nights; i++) {
			Date oneDay = CommonUtil.calculateDate(checkinDate, i);
			Calendar cal = Calendar.getInstance();
			cal.setTime(oneDay);
			Product detail = new Product();
			detail.setValidDay(sdf1.format(oneDay));
			reserveOrder.setVipType("非周末价");
			if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				detail.setUnitPrice(product.getHolidayPrice());
			} else {
				detail.setUnitPrice(product.getWorkdayPrice());
			}
			totalMoney += detail.getUnitPrice();
			productDetails.add(detail);
		}
		reserveOrder.setTotalMoney(totalMoney * reserveOrder.getAmounts());
		reserveOrder.setQrCodePath(qrCodePath);
		reserveOrderService.insert(reserveOrder);
		map.addAttribute("reserveId", reserveOrder.getReserveId());
		if ("WXpay".equals(reserveOrder.getPayMethod())) {
			return "pages/bookroom/orderFinish";
		}
		map.addAttribute("vipId", reserveOrder.getPayMethod());
		return "pages/bookroom/orderVIPFinish";
	}

	@RequestMapping(value = "/payment")
	@ResponseBody
	public WCPayRequest payment(String reserveOrderId, ModelMap map, HttpServletRequest req) throws Exception {
		Map<String, Object> wcPayMap = reserveOrderService.unifiedOrder(reserveOrderId, HttpAPIClient.getIpAddr(req));
		WCPayRequest wcPay = new WCPayRequest();
		wcPay.setAppId(wcPayMap.get("appId").toString());
		wcPay.setNonceStr(wcPayMap.get("nonceStr").toString());
		wcPay.setPackageStr(wcPayMap.get("package").toString());
		wcPay.setSignType(wcPayMap.get("signType").toString());
		wcPay.setTimeStamp(wcPayMap.get("timeStamp").toString());
		wcPay.setPaySign(wcPayMap.get("paySign").toString());
		return wcPay;
	}

	@RequestMapping(value = "/vipPayment")
	@ResponseBody
	public JSONObject vipPayment(String reserveOrderId, String vipId, ModelMap map, HttpServletRequest req)
			throws Exception {
		logger.info("开始VIP支付，订单号：" + reserveOrderId);
		VipInfo info = new VipInfo();
		info.setVipId(vipId);
		List<VipInfo> infos = vipInfoService.find(info);
		JSONObject jsonObj = new JSONObject();
		if (infos.size() == 0) {
			logger.error("vip卡号有误!");
			jsonObj.put("result", 0);
			jsonObj.put("msg", "请检查vip卡号关联，或者稍后重试！");
			return jsonObj;
		}
		if (infos.size() > 1) {
			logger.error("一张vip卡绑定了多个微信用户！");
		}
		// TODO 暂定储存会员卡号，不调用接口扣费。　
		ReserveOrder reserveOrder = new ReserveOrder();
		reserveOrder.setVipId(vipId);
		reserveOrderService.update(reserveOrder);
		// String payResult = reserveOrderService.vipPay(reserveOrderId,
		// vipId);
		// if ("1".equals(payResult)) {
		jsonObj.put("result", 1);
		jsonObj.put("msg", "卡号：" + vipId + ",VIP会员卡预订成功！");
		// } else {
		// jsonObj.put("result", 0);
		// jsonObj.put("msg", payResult);
		// }
		return jsonObj;
	}

	@RequestMapping(value = "/notify")
	@ResponseBody
	public String notify(ModelMap map, HttpServletRequest req) throws BaseAppException {
		String reserveOrderId = null;
		String resultXML = "<xml> <return_code><![CDATA[?]]></return_code> <return_msg><![CDATA[$]]></return_msg></xml>";
		try {
			BufferedInputStream reader = new BufferedInputStream(req.getInputStream());
			byte buff[] = new byte[1024];
			ByteBuffer bb = ByteBuffer.allocate(2048);
			while (reader.read(buff) != -1) {
				bb.put(buff);
			}
			reader.close();
			int len = bb.position();
			byte[] arr = new byte[len];
			bb.flip();
			bb.get(arr, 0, len);
			String params = new String(arr, "utf-8").trim();
			logger.info("获取参数======>>>" + params);
			Map<String, Object> dataMap = new TreeMap<String, Object>();
			if (!StringUtils.isEmpty(params) && XmlUtil.isXmlStr(params, "utf-8")) {
				dataMap = XmlUtil.xmlStr2MapObjParent(params);
				String retCode = (String) dataMap.get("return_code");
				if (retCode != null && retCode.equals("SUCCESS")) {
					String signReq = (String) dataMap.remove("sign");
					String sign = "";
					// 签名前的明文
					sign = SignUtil.createLinkString(dataMap) + "&key=" + configrue.getProp("md5_key");
					logger.info("----------签名前的明文=" + sign);
					// 签名后的密文
					sign = SignUtil.md5Str(sign).toUpperCase();
					logger.info("----------签名后的密文=" + sign);
					if (signReq.equals(sign)) {
						reserveOrderId = (String) dataMap.get("out_trade_no");
						ReserveOrder rOrder = reserveOrderService.find(new ReserveOrder(reserveOrderId)).get(0);
						Product pd = new Product();
						pd.setProductId(rOrder.getProductId());
						Product product = productService.find(pd).get(0);
						if (product.getProductType() == 1) {// 客房订单
							if (rOrder.getOrderState() == Integer.parseInt(configrue.getProp("order.state.init"))
									|| rOrder.getOrderState() == Integer.parseInt(configrue
											.getProp("order.state.payed"))) {
								logger.info("提交订单-西软预订-提交订单者：" + rOrder.getSubmitterName());
								boolean result = productService.reservation(rOrder, product);
								if (result) {
									// 更新订单状态->预定成功
									rOrder.setOrderState(Integer.parseInt(configrue.getProp("order.state.reserved")));
									reserveOrderService.update(rOrder);
									// 去库存{若放在微信支付结果通知里，会重复通知，则会删减太多库存}
									int left = product.getInventory();
									product.setInventory(left - 1);
									productService.update(product);
									logger.info("支付成功，更新 预定结果 ...." + rOrder.getReserveId() + " 预定成功");
									map.addAttribute("reserveId", rOrder.getReserveId());
									resultXML.replace("?", "SUCCESS");
									resultXML.replace("$", "OK");
									return resultXML;
								} else {
									// 更新订单状态->支付成功（预定失败）
									rOrder.setOrderState(Integer.parseInt(configrue.getProp("order.state.payed")));
									reserveOrderService.update(rOrder);
									logger.info("支付成功，更新 预定结果 ...." + rOrder.getReserveId() + " 预定失败");
									resultXML.replace("?", "FAIL");
									resultXML.replace("$", "发货失败");
									return resultXML;
								}
							}
						} else if (product.getProductType() == 2) {
							if (rOrder.getOrderState() == Integer.parseInt(configrue.getProp("order.state.init"))) {
								// 只有初始订单 -> 更新订单状态->预定成功
								rOrder.setOrderState(Integer.parseInt(configrue.getProp("order.state.reserved")));
								reserveOrderService.update(rOrder);
								// 去库存{若放在微信支付结果通知里，会重复通知，则会删减太多库存}
								int left = product.getInventory();
								product.setInventory(left - 1);
								productService.update(product);
								logger.info("支付成功，更新 预定结果 ...." + rOrder.getReserveId() + " 预定成功");
								map.addAttribute("reserveId", rOrder.getReserveId());
								resultXML.replace("?", "SUCCESS");
								resultXML.replace("$", "OK");
								return resultXML;
							}
						}
					} else {
						logger.info("notify：验签失败");
						resultXML.replace("?", "FAIL");
						resultXML.replace("$", "签名失败");
					}
				} else {
					logger.info("=====返回结果失败....");
					resultXML.replace("?", "FAIL");
					resultXML.replace("$", "通知错误信息");
				}
			}
		} catch (Exception e) {
			logger.error("接收参数失败！！", e);
			resultXML.replace("?", "FAIL");
			resultXML.replace("$", "参数格式校验错误");
		}
		return resultXML;
	}

	@RequestMapping(value = "/gardenHotelReservation")
	public String gardenHotelReservation(String startTime, String endTime, ModelMap map) throws BaseAppException {
		Product holidyHotel = new Product();
		List<Product> list = new ArrayList<Product>();
		holidyHotel.setColumnId(Long.valueOf(configrue.getProp("GARDEN_HOTEL_COLUMNID")));
		holidyHotel.setProductState(Integer.parseInt(configrue.getProp("positive")));
		list = productService.find(holidyHotel);
		checkDate(startTime, endTime);
		map.addAttribute("allProducts", list);
		if (checkinDate == null) {
			checkinDate = new Date();
		}
		map.addAttribute("checkinDate", checkinDate);
		map.addAttribute("nights", nights);
		map.addAttribute("checkoutDate", CommonUtil.calculateDate(checkinDate, nights));
		return "pages/bookroom/gardenHotelReservation";
	}

	@RequestMapping(value = "/lakeVillaReservation")
	public String lakeVillaReservation(String startTime, String endTime, ModelMap map) throws NumberFormatException,
			BaseAppException {
		Product holidyHotel = new Product();
		List<Product> list = new ArrayList<Product>();
		holidyHotel.setColumnId(Long.valueOf(configrue.getProp("LAKE_VILLA_COLUMNID")));
		holidyHotel.setProductState(Integer.parseInt(configrue.getProp("positive")));
		list = productService.find(holidyHotel);
		checkDate(startTime, endTime);
		map.addAttribute("allProducts", list);
		if (checkinDate == null) {
			checkinDate = new Date();
		}
		map.addAttribute("checkinDate", checkinDate);
		map.addAttribute("nights", nights);
		map.addAttribute("checkoutDate", CommonUtil.calculateDate(checkinDate, nights));
		return "pages/bookroom/lakeVillaReservation";
	}

	@RequestMapping(value = "/viewLakeVillaReservation")
	public String viewLakeVillaReservation(String startTime, String endTime, ModelMap map) throws BaseAppException {
		Product holidyHotel = new Product();
		List<Product> list = new ArrayList<Product>();
		holidyHotel.setColumnId(Long.valueOf(configrue.getProp("VIEW_LAKE_COLUMNID")));
		holidyHotel.setProductState(Integer.parseInt(configrue.getProp("positive")));
		list = productService.find(holidyHotel);
		checkDate(startTime, endTime);
		map.addAttribute("allProducts", list);
		if (checkinDate == null) {
			checkinDate = new Date();
		}
		map.addAttribute("checkinDate", checkinDate);
		map.addAttribute("nights", nights);
		map.addAttribute("checkoutDate", CommonUtil.calculateDate(checkinDate, nights));
		return "pages/bookroom/viewLakeVillaReservation";
	}

	@RequestMapping(value = "/viewLakeVillaIntro")
	public String viewLakeVillaIntro() {
		return "pages/bookroom/viewLakeVillaIntro";
	}

	@RequestMapping(value = "/lakeVillaIntro")
	public String lakeVillaIntro() {
		return "pages/bookroom/lakeVillaIntro";
	}

	@RequestMapping(value = "/gardenHotelIntro")
	public String gardenHotelIntro() {
		return "pages/bookroom/gardenHotelIntro";
	}

	@RequestMapping(value = "/holidayHotelIntro")
	public String holidayHotelIntro() {
		return "pages/bookroom/holidayHotelIntro";
	}

	/**
	 * jsapi resign
	 * 
	 * @param url
	 * @return
	 */
	@RequestMapping(value = "/jsapi")
	@ResponseBody
	public Map<String, Object> jsapi(String urlStr, HttpSession session) {
		String ticketMem = (String) session.getAttribute("ticket");
		if (ticketMem == null) {// 防止请求api次数过多受限制
			Token token = CommonUtil.getToken(configrue.getProp("appId"), configrue.getProp("appSecret"));
			Ticket ticket = CommonUtil.getJSapiTicket(token.getAccessToken());
			ticketMem = ticket.getTicket();
			session.setAttribute("ticket", ticketMem);
		}
		Map<String, Object> paramMap = new TreeMap<String, Object>();
		paramMap.put("noncestr", CommonUtil.getRandomString(32));
		paramMap.put("jsapi_ticket", ticketMem);
		paramMap.put("timestamp", new Date().getTime());
		paramMap.put("url", urlStr);
		String string1 = SignUtil.createLinkString(paramMap);
		String sign = DigestUtils.shaHex(string1);
		paramMap.put("signature", sign);
		paramMap.put("appid", configrue.getProp("appId"));
		return paramMap;
	}
}
