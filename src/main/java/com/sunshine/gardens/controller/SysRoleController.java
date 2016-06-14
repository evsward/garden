package com.sunshine.gardens.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dance.core.orm.Page;
import com.dance.core.utils.BaseAppException;
import com.dance.core.utils.spring.SpringContextHolder;
import com.sunshine.gardens.model.po.SysRole;
import com.sunshine.gardens.model.po.SysUser;
import com.sunshine.gardens.model.po.SysUserRole;
import com.sunshine.gardens.service.SysRoleService;
import com.sunshine.gardens.service.SysUserRoleService;
import com.sunshine.gardens.utils.MyPropertyPlaceholderConfigurer;

@Controller
@RequestMapping("/account/sysrole")
public class SysRoleController {

	private final static Log logger = LogFactory.getLog(SysRoleController.class);
	private final static MyPropertyPlaceholderConfigurer configrue = SpringContextHolder.getBean("propertyPlaceholder");

	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysRoleService sysRoleService;

	private long totalCount = 1;// 总数据条数，最低为1
	private long totalPages = 1;// 总页数，最低为1

	@RequestMapping
	public String loadIndex(Integer pageNO, Integer pageSize, ModelMap map, HttpSession session) {
		SysUser sysUser = (SysUser) session.getAttribute("loginUser");
		if (sysUser == null) {
			return "account/index";
		}
		try {
			SysUserRole sysUserRole = new SysUserRole();
			sysUserRole.setRoleCode(configrue.getProp("SysRoleCode"));
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
			map.addAttribute("roleCode", configrue.getProp("SysRoleCode"));

			Page<SysRole> page = new Page<SysRole>();
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
			page = sysRoleService.find(page, new SysRole());
			totalCount = page.getTotalCount();
			totalPages = page.getTotalPages();
			map.addAttribute("pageNO", page.getPageNo());// 当前第XX页
			map.addAttribute("pageSize", pageSize);// 每页显示XX条
			map.addAttribute("pages", totalPages);// 共XX页
			map.addAttribute("rows", totalCount);// 共XX条
			map.addAttribute("allRoles", page.getResult());
		} catch (BaseAppException e) {
			logger.error("find user roles resources ", e);
		}
		return "account/sysRole";
	}

	@RequestMapping(value = "/updateSysRole")
	@ResponseBody
	public int updateSysRole(SysRole sysRole) {
		try {
			sysRoleService.update(sysRole);
			List<SysRole> sysRoles = sysRoleService.find(sysRole);
			return sysRoles.get(0).getRoleState();
		} catch (BaseAppException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@RequestMapping(value = "/addSysRole")
	public String addSysRole(SysRole sysRole, HttpSession session) {
		try {
			sysRole.setCreateTime(new Date());
			sysRole.setRoleState(Integer.parseInt(configrue.getProp("positive")));
			if (sysRole.getNote() == null) {
				sysRole.setNote("");
			}
			sysRoleService.insert(sysRole);
			return "redirect:/account/sysrole";
		} catch (BaseAppException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/delSysRole")
	public String delSysRole(SysRole sysRole) {
		try {
			List<SysRole> roles = sysRoleService.find(sysRole);
			SysUserRole sysUserRole = new SysUserRole();
			sysUserRole.setRoleCode(roles.get(0).getRoleCode());
			List<SysUserRole> list = sysUserRoleService.find(sysUserRole);
			if (list.size() > 0) {
				sysUserRoleService.delete(sysUserRole);
			}
			sysRoleService.delete(sysRole);
			return "redirect:/account/sysrole";
		} catch (BaseAppException e) {
			e.printStackTrace();
		}
		return null;
	}
}
