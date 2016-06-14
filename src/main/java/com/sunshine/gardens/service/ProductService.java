package com.sunshine.gardens.service;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dance.core.orm.BaseDao;
import com.dance.core.service.BaseServiceImpl;
import com.dance.core.utils.spring.SpringContextHolder;
import com.sunshine.gardens.dao.ProductDao;
import com.sunshine.gardens.model.po.Product;
import com.sunshine.gardens.model.po.ReserveOrder;
import com.sunshine.gardens.model.po.RoomType;
import com.sunshine.gardens.utils.Const;
import com.sunshine.gardens.utils.MyPropertyPlaceholderConfigurer;
import com.sunshine.gardens.utils.XmlUtil;
import com.sunshine.gardens.utils.webservice.IMemberServiceProxy;
import com.sunshine.gardens.utils.webservice.IReservationServiceProxy;

@Service
public class ProductService extends BaseServiceImpl<Product, Integer> {

	private final static MyPropertyPlaceholderConfigurer configrue = SpringContextHolder.getBean("propertyPlaceholder");
	private static IMemberServiceProxy IMemberServiceProxy = new IMemberServiceProxy();
	private static IReservationServiceProxy IReservationServiceProxy = new IReservationServiceProxy();
	@Autowired
	private ProductDao baseDao;

	@Override
	public BaseDao<Product, Integer> getDao() {
		return baseDao;
	}

	public int checkVipService(String vipId, String password) throws Exception {
		StringBuffer xml = new StringBuffer();
		xml.append(XmlUtil.xmlWSHeadStr(1001));
		xml.append("<Body><LoginObject><ID>");
		xml.append(vipId);
		xml.append("</ID><Password>");
		xml.append(password);
		xml.append("</Password><Logintype>LOG_CARD</Logintype><Cardtype>");
		xml.append(configrue.getProp("Cardtype"));
		xml.append("</Cardtype></LoginObject></Body></Request>");
		String checkLogin = IMemberServiceProxy.userLogin(xml.toString());
		Map<String, Object> xmlObjMap = new HashMap<String, Object>();
		if (XmlUtil.isXmlStr(checkLogin, "utf-8")) {
			xmlObjMap = XmlUtil.xmlStr2MapObjParent(checkLogin);
			logger.info(xmlObjMap.toString());
		} else {
			logger.error("未获取到会员信息");
			return Const.NOVIP;
		}
		if ("00001".equals(xmlObjMap.get("retcode"))) {// 成功
			String vipTypeStr = XmlUtil.queryWSDict("class");
			Map<String, Object> xmlVIPMap = new HashMap<String, Object>();
			String vipTypeStrResult = IReservationServiceProxy.getdic(vipTypeStr);
			if (XmlUtil.isXmlStr(checkLogin, "utf-8")) {
				xmlVIPMap = XmlUtil.xmlStr2MapObjParent(vipTypeStrResult);
				logger.info(xmlVIPMap.toString());
			}
			return Const.GOLD;
		} else {
			return Const.NOVIP;
		}
	}

	public Boolean reservation(ReserveOrder reserveOrder, Product product) throws RemoteException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer xml = new StringBuffer();
		xml.append(XmlUtil.xmlWSHeadStr(1009));
		xml.append("<Body><Reservation mfShareAction=\"NA\" mfReservationAction=\"ADD\"><ResComments>");
		xml.append("<ResComment reservationActionType=\"CHANGE\"><comment>");
		xml.append(reserveOrder.getSpecialNotes());
		xml.append("</comment></ResComment></ResComments><ResProfiles><ResProfile><Profile profileType=\"GUEST\" gender=\"MALE\"><IndividualName><nameTitle/><nameFirst>");
		xml.append(reserveOrder.getSubmitterName().substring(0, 1));
		xml.append("</nameFirst><nameSur>");
		xml.append(reserveOrder.getSubmitterName().substring(1));
		xml.append("</nameSur><nameTitle/></IndividualName><primaryLanguageID>C</primaryLanguageID></Profile></ResProfile></ResProfiles><RoomStays><RoomStay mfShareAction=\"NA\" mfReservationAction=\"ADD\" reservationActionType=\"CHANGE\" reservationStatusType=\"RESERVED\"><roomStayRPH/><roomInventoryCode>");
		xml.append(product.getTypeCode());
		xml.append("</roomInventoryCode><roomNo></roomNo><TimeSpan timeUnitType=\"DAY\"><startTime>");
		xml.append(sdf.format(reserveOrder.getCheckinDate()));
		xml.append("</startTime><numberOfTimeUnits>");
		xml.append(reserveOrder.getNights());
		xml.append("</numberOfTimeUnits><arrtime>19:38</arrtime></TimeSpan> <GuestCounts><GuestCount><ageQualifyingCode>ADULT</ageQualifyingCode><mfCount>1</mfCount></GuestCount><GuestCount><ageQualifyingCode>CHILD</ageQualifyingCode><mfCount>0</mfCount></GuestCount></GuestCounts><RatePlans><RatePlan reservationActionType=\"CHANGE\"><ratePlanCode>");
		xml.append("RAC");
		xml.append("</ratePlanCode> <mfsourceCode>");
		xml.append(configrue.getProp("mfsourceCode"));
		xml.append("</mfsourceCode><mfMarketCode>");
		xml.append(configrue.getProp("mfMartketCode"));
		xml.append("</mfMarketCode> <numRooms>");
		xml.append(reserveOrder.getAmounts());
		xml.append("</numRooms> </RatePlan></RatePlans><mfchannelCode>OW</mfchannelCode></RoomStay></RoomStays><resCommentRPHs>");
		xml.append(reserveOrder.getSubmitterMobile());
		xml.append("</resCommentRPHs><resProfileRPHs>");
		xml.append(reserveOrder.getSubmitterName());
		xml.append("</resProfileRPHs></Reservation></Body></Request>");
		String xmlMsg = xml.toString();
		logger.info("发起预定，请求西软接口：" + xmlMsg);
		String checkReservation = IReservationServiceProxy.reservation(xmlMsg);
		Map<String, Object> xmlObjMap = new HashMap<String, Object>();
		if (XmlUtil.isXmlStr(checkReservation, "utf-8")) {
			xmlObjMap = XmlUtil.xmlStr2MapObjParent(checkReservation);
			logger.info(xmlObjMap.toString());
		}
		if ("00001".equals(xmlObjMap.get("retcode"))) {
			return true;
		} else {
			return false;
		}
	}

	public List<RoomType> getRoomTypeList() throws RemoteException, UnsupportedEncodingException, DocumentException {
		String xmlStr = "<Request><Head><transcode>10</transcode><reqtime>2011-05-05T20:11:10</reqtime><systype>1017</systype><username>dcRNjbvYrI+1AaqSyBOEig==</username><password>jTxzQkEMqLV4Ov84pxXQsQ==</password><openid>oZU-Rs1c1AuixNLwI1c6aqWT_Tx8</openid></Head><Body></Body></Request>";
		String xmlResultList = IReservationServiceProxy.searchRoomType(xmlStr);
		logger.info("获取参数======>>>" + xmlResultList);
		List<RoomType> list = new ArrayList<RoomType>();
		if (!StringUtils.isEmpty(xmlResultList) && XmlUtil.isXmlStr(xmlResultList, "utf-8")) {
			List<Node> nodeList = XmlUtil.getChildNodeListByName(XmlUtil.getDoucument(xmlResultList),
					"Response/Body/ResponseBodyRmtype/Rmtype");
			for (Node d : nodeList) {
				RoomType r = new RoomType();
				Element a = (Element) d;
				r.setRmtype(a.selectSingleNode("rmtype").getText());
				r.setDescript(a.selectSingleNode("descript").getText());
				list.add(r);
			}
			return list;
		}
		return null;
	}

}
