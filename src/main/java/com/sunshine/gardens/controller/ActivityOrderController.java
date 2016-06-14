package com.sunshine.gardens.controller;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dance.core.orm.Page;
import com.dance.core.utils.BaseAppException;
import com.dance.core.utils.spring.SpringContextHolder;
import com.sunshine.gardens.model.po.Activity;
import com.sunshine.gardens.model.po.ActivityOrder;
import com.sunshine.gardens.model.po.SysRole;
import com.sunshine.gardens.model.po.SysUser;
import com.sunshine.gardens.model.po.SysUserRole;
import com.sunshine.gardens.model.po.UserInfo;
import com.sunshine.gardens.service.ActivityOrderService;
import com.sunshine.gardens.service.ActivityService;
import com.sunshine.gardens.service.SysRoleService;
import com.sunshine.gardens.service.SysUserRoleService;
import com.sunshine.gardens.service.UserInfoService;
import com.sunshine.gardens.utils.MyPropertyPlaceholderConfigurer;

@Controller
@RequestMapping("/service/activityorder")
public class ActivityOrderController {

	private final static Log logger = LogFactory.getLog(ActivityOrderController.class);
	private final static MyPropertyPlaceholderConfigurer configrue = SpringContextHolder.getBean("propertyPlaceholder");

	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private ActivityOrderService activityOrderService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private ActivityService activityService;

	private long totalCount = 1;// 总数据条数，最低为1
	private long totalPages = 1;// 总页数，最低为1

	@RequestMapping
	public String loadIndex(ActivityOrder activityOrder, Integer pageNO, Integer pageSize, ModelMap map,
			HttpSession session) throws RemoteException, UnsupportedEncodingException, DocumentException {
		SysUser sysUser = (SysUser) session.getAttribute("loginUser");
		if (sysUser == null) {
			return "account/index";
		}
		try {
			SysUserRole sysUserRole = new SysUserRole();
			sysUserRole.setRoleCode(configrue.getProp("ActivityOrder"));
			sysUserRole.setUserId(sysUser.getUserId());
			int SysUserRoleCount = (int) sysUserRoleService.get("checkUserRoleByID", sysUserRole);
			if (SysUserRoleCount == 0) {
				map.addAttribute("sysError", "权限不够，请重新登录！");
				return "redirect:/account/index/logout";
			}
			List<SysRole> roles = sysRoleService.find("findUserRoles", sysUser.getUserId());
			logger.info("roles length:" + roles.size());
			map.addAttribute("roles", roles);
			map.addAttribute("user", sysUser);
			map.addAttribute("roleCode", configrue.getProp("ActivityOrder"));

			Page<ActivityOrder> page = new Page<ActivityOrder>();
			if (pageSize == null || pageSize == 0 || pageSize > totalCount) {
				page.setPageSize(10);
				pageSize = 10;
			} else {
				page.setPageSize(pageSize);
			}
			if (pageNO == null || pageNO == 0) {
				page.setPageNo(1);
			} else if (pageNO > totalPages) {// 如果当前页超过了最大页，则显示最后一页
				page.setPageNo((int) totalPages);
			} else {
				page.setPageNo(pageNO);
			}
			page = activityOrderService.find(page, activityOrder);
			List<ActivityOrder> rOrderResultList = new ArrayList<ActivityOrder>();
			List<ActivityOrder> aOrderList = page.getResult();
			for (ActivityOrder ao : aOrderList) {
				UserInfo u = userInfoService.find(new UserInfo(ao.getOpenid())).get(0);
				ao.setNickname(u.getNickname());
				Activity activity = activityService.find(new Activity(ao.getActivityId())).get(0);
				ao.setActivityName(activity.getActivityName());
				rOrderResultList.add(ao);
			}
			totalCount = page.getTotalCount();
			totalPages = page.getTotalPages();
			map.addAttribute("pageNO", page.getPageNo());// 当前第XX页
			map.addAttribute("pageSize", pageSize);// 每页显示XX条
			map.addAttribute("pages", totalPages);// 共XX页
			map.addAttribute("rows", totalCount);// 共XX条
			map.addAttribute("allOrders", rOrderResultList);
		} catch (BaseAppException e) {
			logger.error("find room type list ", e);
		}
		return "service/activityorder";
	}
}
