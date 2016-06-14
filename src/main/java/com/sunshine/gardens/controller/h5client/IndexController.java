package com.sunshine.gardens.controller.h5client;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dance.core.utils.BaseAppException;
import com.sunshine.gardens.model.fenghuo.RespMessage;
import com.sunshine.gardens.model.po.VipInfo;
import com.sunshine.gardens.model.pojo.MemberInfo;
import com.sunshine.gardens.service.VipInfoService;

@Controller
@RequestMapping("/h5client/index")
public class IndexController {
	private final static Log logger = LogFactory.getLog(IndexController.class);

	@Autowired
	private VipInfoService vipInfoService;

	@RequestMapping
	public String loadIndex(ModelMap map, HttpSession session) {
		logger.info("进入首页");
		return "pages/index";
	}

	@RequestMapping(value = "/hotelIntro")
	public String hotelIntro() {
		return "pages/hotelIntro";
	}

	@RequestMapping(value = "/foodspotting")
	public String foodspotting() {
		return "pages/foodspotting";
	}

	@RequestMapping(value = "/conference")
	public String conference() {
		return "pages/conference";
	}

	@RequestMapping(value = "/MingHuiGe")
	public String MingHuiGe() {
		return "pages/MingHuiGe";
	}

	@RequestMapping(value = "/LvYinGe")
	public String LvYinGe() {
		return "pages/LvYinGe";
	}

	@RequestMapping(value = "/MingShiGe")
	public String MingShiGe() {
		return "pages/MingShiGe";
	}

	@RequestMapping(value = "/SiJi")
	public String SiJi() {
		return "pages/SiJi";
	}

	@RequestMapping(value = "/user")
	public String user(HttpSession session) {
		return "pages/user";
	}

	@RequestMapping(value = "/route")
	public String route(HttpSession session) {
		return "pages/route";
	}

	@RequestMapping(value = "/bind")
	public String bind(HttpSession session) {
		return "pages/bind";
	}

	@RequestMapping(value = "/bindForm")
	public String bindForm(ModelMap map, MemberInfo member, HttpSession session) throws RemoteException,
			BaseAppException {// AA004881测试账号
		String openId = (String) session.getAttribute("openId");
		if (openId == null) {
			// openId = "oZU-Rs1c1AuixNLwI1c6aqWT_Tx8";// 本地调试
			logger.error("系统无法获得微信ID，无法查看！");
			map.addAttribute("msg", "系统无法获得您的微信ID，无法查看！");
			return "pages/bookroom/orderFailed";
		}
		String verifyMsg = (String) session.getAttribute(openId);
		if (verifyMsg == null) {
			map.addAttribute("msg", "验证码已失效，请重试！");
			logger.error("验证码已失效！");
			return "pages/bookroom/orderFailed";
		}
		if (!member.getVerifyCode().equals(verifyMsg)) {
			map.addAttribute("msg", "验证码不匹配，请检查！");
			logger.error("验证码不匹配！");
			return "pages/bookroom/orderFailed";
		}
		MemberInfo m = vipInfoService.getWSMemberInfo(member.getAccountID());
		if (m == null) {
			map.addAttribute("msg", "VIP卡号不存在，请检查！");
			logger.error("VIP卡号不存在！");
			return "pages/bookroom/orderFailed";
		}
		if (!member.getPhoneNumber().equals(m.getPhoneNumber())) {
			map.addAttribute("msg", "VIP卡号与手机号不匹配，请检查！");
			logger.error("VIP卡号与手机号不匹配！");
			return "pages/bookroom/orderFailed";
		}
		VipInfo v = new VipInfo();
		v.setVipId(member.getAccountID());
		v.setMobile(member.getPhoneNumber());
		v.setOpenid(openId);
		v.setVipName(m.getGenericName());
		v.setCreateTime(new Date());
		try {
			vipInfoService.insert(v);// 保存VIP与OPENID关联关系
		} catch (Exception e) {
			logger.error("关联失败：", e);
			map.addAttribute("msg", "关联失败:一张会员卡只能绑定一次！");
			logger.error("关联失败:一张会员卡只能绑定一次！");
			return "pages/bookroom/orderFailed";
		}
		// 绑定成功以后直接跳转到“我的会员卡”页面。
		return "redirect:/h5client/bookroom/findVipInfo";
	}

	@RequestMapping(value = "/sendVerifyMsg")
	public String sendVerifyMsg(ModelMap map, String phoneNumber, HttpSession session)
			throws UnsupportedEncodingException {
		String openId = (String) session.getAttribute("openId");
		if (openId == null) {
			// openId = "oZU-Rs1c1AuixNLwI1c6aqWT_Tx8";// 本地调试
			logger.error("系统无法获得微信ID，无法查看！");
			return null;
		}
		logger.info("发短信，openid：" + openId);
		logger.info("发短信，手机号：" + phoneNumber);
		int verifyMsg = (int) (Math.random() * 1000000);
		session.setAttribute(openId, String.valueOf(verifyMsg));
		String SMSTemplet = "【春晖园度假酒店】本次验证码为%s，您正在春晖园温泉度假酒店微信公众平台中将VIP卡与手机绑定，如非本人操作请及时与我方客服联系。咨询热线：010-69454433";
		RespMessage resp = vipInfoService.sendSMS(phoneNumber, String.format(SMSTemplet, verifyMsg));
		logger.warn("发短信，结果：" + resp.getDescription());
		return null;
	}
}
