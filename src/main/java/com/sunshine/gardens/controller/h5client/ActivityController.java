package com.sunshine.gardens.controller.h5client;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dance.core.utils.BaseAppException;
import com.sunshine.gardens.model.po.ActivityOrder;
import com.sunshine.gardens.service.ActivityOrderService;

@Controller
@RequestMapping("/h5client/activity")
/**
 * @author LiuWenbin
 * @2015年11月05日
 */
public class ActivityController {
	private final static Log logger = LogFactory.getLog(ActivityController.class);
	@Autowired
	private ActivityOrderService activityOrderService;

	@RequestMapping
	public String loadIndex(ModelMap map, HttpSession session) {
		logger.info("进入首页");
		return "pages/activities/activity";
	}

	@RequestMapping(value = "/register")
	public String register() {
		return "pages/activities/register";
	}

	@RequestMapping(value = "/inputFinish")
	public String inputFinish(ActivityOrder activityOrder, HttpSession session) throws BaseAppException {
		String openId = (String) session.getAttribute("openId");
		logger.info("预定订单，openid：" + openId);
		if (openId == null) {
			return "pages/activities/orderFailed";
		}
		activityOrder.setOpenid(openId);
		activityOrderService.insert(activityOrder);
		return "pages/activities/orderFinish";
	}

	@RequestMapping(value = "/findMyActivites")
	public String findMyActivities(ModelMap map, HttpSession session) throws BaseAppException {
		String openId = (String) session.getAttribute("openId");
		logger.info("查找我的活动，openid：" + openId);
		if (openId == null) {
			return "pages/activities/orderFailed";
		}
		List<ActivityOrder> list = activityOrderService.find(new ActivityOrder(openId));
		map.addAttribute("myAOrderList", list);
		return "pages/activities/myOrders";
	}
}
