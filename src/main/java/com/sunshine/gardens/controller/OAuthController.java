package com.sunshine.gardens.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dance.core.utils.BaseAppException;
import com.dance.core.utils.spring.SpringContextHolder;
import com.sunshine.gardens.model.po.UserInfo;
import com.sunshine.gardens.model.pojo.SNSUserInfo;
import com.sunshine.gardens.model.pojo.WeixinOauth2Token;
import com.sunshine.gardens.service.UserInfoService;
import com.sunshine.gardens.utils.AdvancedUtil;
import com.sunshine.gardens.utils.MyPropertyPlaceholderConfigurer;

@Controller
@RequestMapping("/service/oauth")
public class OAuthController {
	private final static Log logger = LogFactory.getLog(OAuthController.class);
	private final static MyPropertyPlaceholderConfigurer configrue = SpringContextHolder.getBean("propertyPlaceholder");
	@Autowired
	private UserInfoService userInfoService;

	@RequestMapping
	public String handler(String code, String state, HttpSession session, HttpServletResponse resp)
			throws ServletException, IOException, BaseAppException {
		logger.info("bases授权，获得code: " + code);
		logger.info("bases授权，获得state: " + state);
		WeixinOauth2Token weixinOauth2Token = (WeixinOauth2Token) session.getAttribute("accessToken");
		long now = System.currentTimeMillis();
		if (weixinOauth2Token == null
				|| now - weixinOauth2Token.getAccessTime() >= weixinOauth2Token.getExpiresIn() * 1000) {
			logger.info("token超时或没有token，重新获取token");
			// 获取网页授权access_token
			weixinOauth2Token = AdvancedUtil.getOauth2AccessToken(configrue.getProp("appId"),
					configrue.getProp("appSecret"), code);
			if (weixinOauth2Token != null)
				weixinOauth2Token.setAccessTime(now);
			session.setAttribute("accessToken", weixinOauth2Token);
		}

		if (weixinOauth2Token == null) {
			logger.info("获取accessToken失败!!! code = " + code + " state =" + state);
		} else {
			// 网页授权接口访问凭证
			String accessToken = weixinOauth2Token.getAccessToken();
			// 用户标识
			String openId = weixinOauth2Token.getOpenId();
			logger.info("----openId:" + openId);
			if (session.getAttribute("openId") == null) {
				session.setAttribute("openId", openId);
			}
			List<UserInfo> list = userInfoService.find(new UserInfo(openId));
			if (list.size() == 0) {
				// 获取用户信息
				SNSUserInfo snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken, openId);
				// 存储用户信息
				UserInfo userInfo = new UserInfo();
				userInfo.setOpenid(openId);
				userInfo.setCity(snsUserInfo.getCity());
				userInfo.setCountry(snsUserInfo.getCountry());
				userInfo.setCreateTime(new Date());
				userInfo.setGroupid(0);
				userInfo.setHeadimgurl(snsUserInfo.getHeadImgUrl());
				userInfo.setNickname(snsUserInfo.getNickname());
				userInfo.setProvince(snsUserInfo.getProvince());
				userInfo.setSex(snsUserInfo.getSex());
				userInfo.setSubscribe(0);
				userInfo.setRemark(null);
				userInfoService.insert(userInfo);
			} else {
				UserInfo uinfo = list.get(0);
				// 获取用户信息
				SNSUserInfo snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken, openId);
				if (uinfo != null && snsUserInfo != null) {
					if (!uinfo.getNickname().equals(snsUserInfo.getNickname()) || uinfo.getSex() != snsUserInfo.getSex()
							|| !uinfo.getHeadimgurl().equals(snsUserInfo.getHeadImgUrl())) {
						uinfo.setNickname(snsUserInfo.getNickname());
						uinfo.setSex(snsUserInfo.getSex());
						uinfo.setHeadimgurl(snsUserInfo.getHeadImgUrl());
						userInfoService.update(uinfo);
						logger.info("用户数据已更新 ，更新数据库！openId=" + uinfo.getOpenid());
					}
				} else {
					logger.info("用户数据获取失败 ==== uinfo=" + uinfo + " snsUserInfo==" + snsUserInfo);
				}
				// 存储用户信息
				// UserInfo userInfo = new UserInfo();
				// userInfo.setOpenid(openId);
				// userInfo.setCity(snsUserInfo.getCity());
				// userInfo.setCountry(snsUserInfo.getCountry());
				// userInfo.setCreateTime(new Date());
				// userInfo.setGroupid(0);
				// userInfo.setHeadimgurl(snsUserInfo.getHeadImgUrl());
				// userInfo.setNickname(snsUserInfo.getNickname());
				// userInfo.setProvince(snsUserInfo.getProvince());
				// userInfo.setSex(snsUserInfo.getSex());
				// userInfo.setSubscribe(0);
				// userInfo.setRemark(null);
			}
		}

		if ("hotel".equals(state)) {
			return "redirect:/h5client/bookroom";
		} else if ("hotspring".equals(state)) {
			return "redirect:/h5client/hotspring";
		} else if ("myOrders".equals(state)) {
			return "redirect:/h5client/bookroom/findMyOrders";
		} else if ("myActivities".equals(state)) {
			return "redirect:/h5client/activity/findMyActivites";
		} else if ("myVip".equals(state)) {// 我的会员卡
			return "redirect:/h5client/bookroom/findVipInfo";
		} else if ("myRoute".equals(state)) {// 路线规划
			return "redirect:/h5client/index/route";
		} else if ("winter".equals(state)) {
			return "redirect:/h5client/activity";
		} else if (state != null && state.startsWith("http://")) {
			state = URLDecoder.decode(state, "UTF-8");
			logger.info("state ======" + state);
			// resp.sendRedirect(state);
			return "redirect:" + state;
		}
		return "redirect:/h5client/index";
	}
}