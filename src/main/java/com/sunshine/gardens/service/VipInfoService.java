package com.sunshine.gardens.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.dance.core.orm.BaseDao;
import com.dance.core.service.BaseServiceImpl;
import com.dance.core.utils.spring.SpringContextHolder;
import com.sunshine.gardens.dao.VipInfoDao;
import com.sunshine.gardens.model.fenghuo.Message;
import com.sunshine.gardens.model.fenghuo.RespMessage;
import com.sunshine.gardens.model.fenghuo.SubmitMessage;
import com.sunshine.gardens.model.po.VipInfo;
import com.sunshine.gardens.model.pojo.MemberInfo;
import com.sunshine.gardens.model.pojo.Token;
import com.sunshine.gardens.model.pojo.WSSmsMessage;
import com.sunshine.gardens.utils.CommonUtil;
import com.sunshine.gardens.utils.HttpAPIClient;
import com.sunshine.gardens.utils.MyPropertyPlaceholderConfigurer;
import com.sunshine.gardens.utils.XmlUtil;
import com.sunshine.gardens.utils.webservice.IMemberServiceProxy;

@Component
public class VipInfoService extends BaseServiceImpl<VipInfo, Integer> {

	private final static MyPropertyPlaceholderConfigurer configrue = SpringContextHolder.getBean("propertyPlaceholder");
	private static IMemberServiceProxy IMemberServiceProxy = new IMemberServiceProxy();

	@Autowired
	private VipInfoDao vipInfoDao;

	@Override
	public BaseDao<VipInfo, Integer> getDao() {
		return vipInfoDao;
	}

	/**
	 * 调用西软接口查询会员卡信息
	 * 
	 * @param vipId
	 * @return
	 * @throws RemoteException
	 */
	public MemberInfo getWSMemberInfo(String vipId) throws RemoteException {// AA004881测试账号
		MemberInfo m = new MemberInfo();
		StringBuffer xml = new StringBuffer();
		xml.append(XmlUtil.xmlWSHeadStr(1003));
		xml.append("<Body><MemberQuery><AccountID>");
		xml.append(vipId);
		xml.append("</AccountID></MemberQuery></Body></Request>");
		String memberObjStr = IMemberServiceProxy.getMemberInfo(xml.toString());
		Map<String, Object> xmlObjMap = new HashMap<String, Object>();
		if (XmlUtil.isXmlStr(memberObjStr, "utf-8")) {
			xmlObjMap = XmlUtil.xmlStr2MapObjParent(memberObjStr);
			logger.info(xmlObjMap.get("retmsg").toString());
			if ("00001".equals(xmlObjMap.get("retcode"))) {// 成功
				m.setAccountID(xmlObjMap.get("accountID").toString());
				m.setGenericName(xmlObjMap.get("genericName").toString());
				m.setProgramCode(Integer.parseInt(xmlObjMap.get("programCode").toString()));
				m.setProgramCodeDescript(xmlObjMap.get("programCodeDescript").toString());
				m.setProgramType(xmlObjMap.get("programType").toString());
				m.setProgramTypeDescript(xmlObjMap.get("programTypeDescript").toString());
				m.setPhoneNumber(xmlObjMap.get("phoneNumber").toString());
				m.setBalance(getBalanceInfo(vipId).getBalance());
			}
		} else {
			logger.error("未获取到会员信息");
		}
		return m;
	}

	/**
	 * 获取VIP会员卡余额信息以及AR账号
	 * 
	 * @param vipId
	 * @return
	 * @throws RemoteException
	 */
	public MemberInfo getBalanceInfo(String vipId) throws RemoteException {
		MemberInfo me = new MemberInfo();
		StringBuffer xml = new StringBuffer();
		xml.append(XmlUtil.xmlWSHeadStr(1019));
		xml.append("<Body><cardNo>");
		xml.append(vipId);
		xml.append("</cardNo></Body></Request>");
		String balanceObjStr = IMemberServiceProxy.getBalanceInfo(xml.toString());
		Map<String, Object> xmlObjMap = new HashMap<String, Object>();
		if (XmlUtil.isXmlStr(balanceObjStr, "utf-8")) {
			xmlObjMap = XmlUtil.xmlStr2MapObjParent(balanceObjStr);
			logger.info(xmlObjMap.get("retmsg").toString());
			if ("00001".equals(xmlObjMap.get("retcode"))) {// 成功
				Double balance = Double.parseDouble(xmlObjMap.get("ArBalance").toString());
				me.setBalance(balance);
				// 修改ar账号接收字段
				String arAccount = xmlObjMap.get("ArAccount").toString();
				me.setArAccount(arAccount);
			}
		}
		return me;
	}

	/**
	 * 调用烽火接口发送短信
	 * 
	 * @param mobielNO
	 * @param content
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public RespMessage sendSMS(String mobielNO, String content) throws UnsupportedEncodingException {
		SubmitMessage send = new SubmitMessage();
		send.setClientID(configrue.getProp("clientID"));
		send.setShare_secret(configrue.getProp("share_secret"));
		List<Message> messageList = new ArrayList<Message>();
		Message m1 = new Message();
		m1.setMobiles(mobielNO);
		m1.setContent(URLEncoder.encode(content, "UTF-8"));
		messageList.add(m1);
		send.setMessageList(messageList);
		String sendStr = "param=" + JSON.toJSONString(send);
		String resultStr = HttpAPIClient.post(sendStr, configrue.getProp("FengHuoURL"));
		RespMessage resp = (RespMessage) JSON.parseObject(resultStr, RespMessage.class);
		return resp;
	}

	/**
	 * 获取待推送会员消费消息
	 * 
	 * @return
	 */
	public List<WSSmsMessage> getMsgPush() {
		StringBuffer xml = new StringBuffer();
		xml.append(XmlUtil.xmlWSHeadStr(1057));
		xml.append("<Body></Body></Request>");
		// TODO 调用西软平台获取待发送信息
		// IMemberServiceProxy
		return null;
	}

}
