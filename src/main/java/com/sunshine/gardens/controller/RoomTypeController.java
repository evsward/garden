package com.sunshine.gardens.controller;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dance.core.utils.BaseAppException;
import com.dance.core.utils.spring.SpringContextHolder;
import com.sunshine.gardens.model.po.RoomType;
import com.sunshine.gardens.model.po.SysRole;
import com.sunshine.gardens.model.po.SysUser;
import com.sunshine.gardens.model.po.SysUserRole;
import com.sunshine.gardens.service.ProductService;
import com.sunshine.gardens.service.SysRoleService;
import com.sunshine.gardens.service.SysUserRoleService;
import com.sunshine.gardens.utils.MyPropertyPlaceholderConfigurer;

@Controller
@RequestMapping("/service/roomtypelist")
public class RoomTypeController {

	private final static Log logger = LogFactory.getLog(RoomTypeController.class);
	private final static MyPropertyPlaceholderConfigurer configrue = SpringContextHolder.getBean("propertyPlaceholder");

	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private ProductService productService;

	@RequestMapping
	public String loadIndex(Integer pageNO, Integer pageSize, ModelMap map, HttpSession session) throws RemoteException, UnsupportedEncodingException, DocumentException {
		SysUser sysUser = (SysUser) session.getAttribute("loginUser");
		if (sysUser == null) {
			return "account/index";
		}
		try {
			SysUserRole sysUserRole = new SysUserRole();
			sysUserRole.setRoleCode(configrue.getProp("RoomTypeList"));
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
			map.addAttribute("roleCode", configrue.getProp("RoomTypeList"));

			List<RoomType> roomList = productService.getRoomTypeList();
			map.addAttribute("roomList", roomList);
		} catch (BaseAppException e) {
			logger.error("find room type list ", e);
		}
		return "service/roomtypelist";
	}
}
