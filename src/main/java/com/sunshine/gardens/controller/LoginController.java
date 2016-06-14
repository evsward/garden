package com.sunshine.gardens.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dance.core.utils.BaseAppException;
import com.dance.core.utils.spring.SpringContextHolder;
import com.sunshine.gardens.model.po.SysLog;
import com.sunshine.gardens.model.po.SysRole;
import com.sunshine.gardens.model.po.SysUser;
import com.sunshine.gardens.service.SysLogService;
import com.sunshine.gardens.service.SysRoleService;
import com.sunshine.gardens.service.SysUserService;
import com.sunshine.gardens.utils.MyPropertyPlaceholderConfigurer;

@Controller
@RequestMapping("/account/index")
public class LoginController {
	private final static Log logger = LogFactory.getLog(LoginController.class);
	private final static MyPropertyPlaceholderConfigurer configrue = SpringContextHolder.getBean("propertyPlaceholder");

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private SysRoleService sysRoleService;

	@RequestMapping
	public String loadIndex(HttpSession session, ModelMap map) {
		SysUser sysUser = (SysUser) session.getAttribute("loginUser");
		if (sysUser == null) {
			return "account/index";
		}
		try {
			List<SysRole> roles = sysRoleService.find("findUserRoles", sysUser.getUserId());
			logger.info("roles length:" + roles.size());
			if (roles.size() == 0) {
				map.addAttribute("sysError", "您还没有任何权限，请联系管理员！");
				return "redirect:/account/index/logout";
			}
			return "redirect:" + roles.get(0).getRelaUrl();
		} catch (BaseAppException e) {
			logger.error("find user roles resources ", e);
		}
		return null;
	}

	@RequestMapping(value = "/logout")
	public ModelAndView logout(HttpSession session, String sysError) {
		SysUser user = (SysUser) session.getAttribute("loginUser");
		if (user != null) {
			logger.info("user : " + user.getUsername() + " logout now!");
			insertLog(1, user.getUsername(), 0);
			session.setAttribute("loginUser", null);
			session.invalidate();
		}
		// 解决get方式不能走filter，引发中文乱码，修改tomcat server.xml 仍旧不好使的情况
		if (sysError != null) {
			byte bb[];
			try {
				bb = sysError.getBytes("ISO-8859-1");// 以"ISO-8859-1"方式解析name字符串
				sysError = new String(bb, "UTF-8"); // 再用"utf-8"格式表示name
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return new ModelAndView("account/index").addObject("sysError", sysError);
	}

	@RequestMapping(value = "/checkLogin")
	public ModelAndView checkLogin(SysUser sysUser, ModelMap modelMap, HttpSession session) {
		String uname = sysUser.getUsername();
		String passwd = sysUser.getPassword();

		if (uname == null || passwd == null) {
			logger.info("USERNAME OR PASSWORD IS NULL!");
			Map<String, String> map = new HashMap<String, String>();
			map.put("sysError", "用户名密码不能空！");
			ModelAndView indexModel = new ModelAndView("account/index");
			indexModel.addAllObjects(map);
			insertLog(0, uname, 1);
			return indexModel;
		} else {
			logger.info("USERNAME: " + uname + " PASSWD: " + passwd);
			try {
				sysUser.setUserState(Integer.parseInt(configrue.getProp("positive")));// 只查询有效的用户
				List<SysUser> users = sysUserService.find(sysUser);
				if (users != null && !users.isEmpty()) {
					session.setAttribute("loginUser", users.get(0));
					insertLog(1, uname, 1);
					return new ModelAndView("redirect:/account/index");
				} else {
					Map<String, String> map = new HashMap<String, String>();
					map.put("sysError", "用户名或密码错误！");
					ModelAndView indexModel = new ModelAndView("account/index");
					indexModel.addAllObjects(map);
					insertLog(0, uname, 1);
					return indexModel;
				}
			} catch (BaseAppException e) {
				logger.info("LOGIN ERROR", e);
				Map<String, String> map = new HashMap<String, String>();
				map.put("sysError", "登录失败！");
				ModelAndView indexModel = new ModelAndView("account/index");
				indexModel.addAllObjects(map);
				insertLog(0, uname, 1);
				return indexModel;
			}
		}
	}

	private void insertLog(int result, String uname, int type) {
		SysLog log = new SysLog();
		log.setAccessTime(new Date());
		log.setContent(type == 1 ? "登录" : "注销");
		if (result != 1)
			log.setNote(type == 0 ? "注销失败" : "登录失败");
		else
			log.setNote(type == 0 ? "注销成功" : "登录成功");
		log.setLogState(result);
		log.setUsername(uname == null ? "" : uname);
		log.setLogUrl("checkLogin");
		try {
			sysLogService.insert(log);
		} catch (BaseAppException e) {
			logger.error("insert log error!", e);
		}
	}
}
