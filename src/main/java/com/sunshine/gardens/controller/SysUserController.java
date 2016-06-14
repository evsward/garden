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
import com.sunshine.gardens.service.SysUserService;
import com.sunshine.gardens.utils.MyPropertyPlaceholderConfigurer;

@Controller
@RequestMapping("/account/sysuser")
public class SysUserController {

	private final static Log logger = LogFactory.getLog(SysUserController.class);
	private final static MyPropertyPlaceholderConfigurer configrue = SpringContextHolder.getBean("propertyPlaceholder");

	@Autowired
	private SysUserService sysUserService;
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
			sysUserRole.setRoleCode(configrue.getProp("SysUserRoleCode"));
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
			map.addAttribute("roleCode", configrue.getProp("SysUserRoleCode"));

			SysRole sR = new SysRole();
			sR.setRoleState(1);
			List<SysRole> allRoles = sysRoleService.find(sR);
			map.addAttribute("allRoles", allRoles);

			Page<SysUser> page = new Page<SysUser>();
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
			page = sysUserService.find(page, new SysUser());
			totalCount = page.getTotalCount();
			totalPages = page.getTotalPages();
			map.addAttribute("pageNO", page.getPageNo());// 当前第XX页
			map.addAttribute("pageSize", pageSize);// 每页显示XX条
			map.addAttribute("pages", totalPages);// 共XX页
			map.addAttribute("rows", totalCount);// 共XX条
			map.addAttribute("allUsers", page.getResult());
		} catch (BaseAppException e) {
			logger.error("find user roles resources ", e);
		}
		return "account/sysUser";
	}

	@RequestMapping(value = "/updateSysUser")
	@ResponseBody
	public int updateSysUser(SysUser sysUser) {
		try {
			sysUserService.update(sysUser);
			List<SysUser> sysUsers = sysUserService.find(sysUser);
			return sysUsers.get(0).getUserState();
		} catch (BaseAppException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@RequestMapping(value = "/delSysUser")
	public String delSysUser(SysUser sysUser) {
		try {
			SysUserRole sysUserRole = new SysUserRole();
			sysUserRole.setUserId(sysUser.getUserId());
			sysUserRoleService.delete(sysUserRole);
			sysUserService.delete(sysUser);
			return "redirect:/account/sysuser";
		} catch (BaseAppException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/addSysUser")
	public String addSysUser(SysUser sysUser, HttpSession session) {
		try {
			sysUser.setCreateTime(new Date());
			sysUser.setUserState(Integer.parseInt(configrue.getProp("positive")));
			sysUserService.insert(sysUser);
			return "redirect:/account/sysuser";
		} catch (BaseAppException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/updateMySysUserInfo")
	public String updateMySysUserInfo(SysUser sysUser, ModelMap map) {
		try {
			sysUserService.update(sysUser);
			map.addAttribute("sysError", "您已修改个人信息，请重新登录！");
			return "redirect:/account/index/logout";
		} catch (BaseAppException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/getSysUserRoles")
	@ResponseBody
	public List<SysRole> getSysUserRoles(SysUser sysUser) {
		try {
			List<SysRole> list = sysRoleService.find("configMyRoles", sysUser.getUserId());
			return list;
		} catch (BaseAppException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/updateSysUserRoles")
	@ResponseBody
	public String updateSysUserRoles(String roleCodes[], int userId, HttpSession session) {
		try {
			SysUser sysUser = (SysUser) session.getAttribute("loginUser");
			SysUserRole operator = new SysUserRole();
			operator.setRoleCode(configrue.getProp("SysUserRoleCode"));
			operator.setUserId(sysUser.getUserId());
			sysUserRoleService.delete("deleteRolesByUserId", userId);
			if (userId == sysUser.getUserId()) {// 不能自杀
				sysUserRoleService.insert(operator);
			}
			if (roleCodes == null) {
				return "empty";
			}
			for (String roleCode : roleCodes) {
				if (configrue.getProp("SysUserRoleCode").equals(roleCode) && userId == sysUser.getUserId()) {
					continue;
				}
				SysUserRole sysUserRole = new SysUserRole();
				sysUserRole.setRoleCode(roleCode);
				sysUserRole.setUserId(userId);
				sysUserRoleService.insert(sysUserRole);
			}
			return "success";
		} catch (BaseAppException e) {
			e.printStackTrace();
		}
		return null;
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
			return "redirect:/account/sysuser";
		} catch (BaseAppException e) {
			e.printStackTrace();
		}
		return null;
	}
}
