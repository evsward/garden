package com.sunshine.gardens.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
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
import com.sunshine.gardens.model.po.Menu;
import com.sunshine.gardens.model.po.SysRole;
import com.sunshine.gardens.model.po.SysUser;
import com.sunshine.gardens.model.po.SysUserRole;
import com.sunshine.gardens.model.pojo.Token;
import com.sunshine.gardens.model.pojo.TreeNode;
import com.sunshine.gardens.model.pojo.menu.Button;
import com.sunshine.gardens.model.pojo.menu.ClickButton;
import com.sunshine.gardens.model.pojo.menu.ComplexButton;
import com.sunshine.gardens.model.pojo.menu.ViewButton;
import com.sunshine.gardens.service.MenuService;
import com.sunshine.gardens.service.SysRoleService;
import com.sunshine.gardens.service.SysUserRoleService;
import com.sunshine.gardens.utils.CommonUtil;
import com.sunshine.gardens.utils.MenuUtil;
import com.sunshine.gardens.utils.MyPropertyPlaceholderConfigurer;

@Controller
@RequestMapping("/service/menu")
public class MenuController {
	private final static Log logger = LogFactory.getLog(MenuController.class);
	private final static MyPropertyPlaceholderConfigurer configrue = SpringContextHolder.getBean("propertyPlaceholder");

	@Autowired
	private MenuService menuService;
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
			sysUserRole.setRoleCode(configrue.getProp("MenuRoleCode"));
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
			map.addAttribute("roleCode", configrue.getProp("MenuRoleCode"));

			Page<Menu> page = new Page<Menu>();
			if (pageSize == null || pageSize == 0 || pageSize > totalCount) {
				page.setPageSize(10);
				pageSize = 10;
			} else {
				page.setPageSize(pageSize);
			}
			if (pageNO == null || pageNO == 0) {
				page.setPageNo(1);
				pageNO = 1;
			} else if (pageNO > totalPages) {// 如果当前页超过了最大页，则显示最后一页
				page.setPageNo((int) totalPages);
			} else {
				page.setPageNo(pageNO);
			}
			page = menuService.find(page, new Menu());
			totalCount = page.getTotalCount();
			totalPages = page.getTotalPages();
			map.addAttribute("pageNO", page.getPageNo());// 当前第XX页
			map.addAttribute("pageSize", pageSize);// 每页显示XX条
			map.addAttribute("pages", totalPages);// 共XX页
			map.addAttribute("rows", totalCount);// 共XX条
			logger.info("==========" + page.getResultCount());
			map.addAttribute("allMenus", page.getResult());
		} catch (BaseAppException e) {
			logger.error("menu index", e);
		}
		return "service/menu";
	}

	@RequestMapping(value = "/getMenus")
	@ResponseBody
	public List<TreeNode> getMenus(Menu menu) {
		try {
			if (menu.getParentMenuId() == null || menu.getParentMenuId() == 0) {
				menu = new Menu();
				menu.setMenuLevel(1);
			} else {
				menu.setParentMenuId(menu.getParentMenuId());
			}
			menu.setMenuState(Integer.parseInt(configrue.getProp("positive")));
			List<Menu> menus = menuService.find(menu);
			List<TreeNode> list = new ArrayList<TreeNode>();
			for (Menu m : menus) {
				TreeNode tn = new TreeNode();
				tn.setId(m.getMenuId());
				tn.setName(m.getMenuName());
				if (m.getParentMenuId() == 0) {
					tn.setIsParent(true);
				} else {
					tn.setIsParent(false);
				}
				list.add(tn);
			}
			return list;
		} catch (BaseAppException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/getMenuList")
	@ResponseBody
	public List<Menu> getMenuList(Menu menu) {
		try {
			if (menu.getParentMenuId() == null || menu.getParentMenuId() == 0) {
				menu = new Menu();
				menu.setMenuLevel(1);
			} else {
				menu.setParentMenuId(menu.getParentMenuId());
			}
			menu.setMenuState(Integer.parseInt(configrue.getProp("positive")));
			List<Menu> list = menuService.find(menu);
			return list;
		} catch (BaseAppException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/updateMenu")
	@ResponseBody
	public int updateMenu(Menu menu, HttpSession session) {
		try {
			String urlStr = menu.getParamValue();
			// 对带参数Url进行编码，例如图文信息
			if (urlStr.indexOf("?") > -1) {
				String strp[] = urlStr.split("[?]");
				String paramStr = URLEncoder.encode("?" + strp[1], "UTF-8");
				menu.setParamValue(strp[0] + paramStr);
			}
			menuService.update(menu);
			List<Menu> list = menuService.find(menu);
			return list.get(0).getMenuState();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@RequestMapping(value = "/delMenu")
	public String delMenu(Menu menu) {
		try {
			Menu cond = new Menu();
			if (menu.getParentMenuId() == 0) {
				cond.setParentMenuId(menu.getMenuId());
				menuService.delete(menu);
			} else {
				cond.setMenuId(menu.getMenuId());
			}
			menuService.delete(cond);
			return "redirect:/service/menu";
		} catch (BaseAppException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/addMenuItem")
	public String addMenuItem(Menu menu) {
		try {
			if (menu.getParentMenuId() == null) {
				menu.setParentMenuId(0);
			}
			menu.setCreateTime(new Date());
			menuService.insert(menu);
			return "redirect:/service/menu";
		} catch (BaseAppException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/syncRemoteMenu")
	@ResponseBody
	public int syncRemoteMenu() throws UnsupportedEncodingException {
		int rsp = 0;
		try {
			Menu menu = new Menu();
			menu.setMenuLevel(1);
			menu.setMenuState(Integer.parseInt(configrue.getProp("positive")));
			List<Menu> list = menuService.find(menu);
			int loopTime = list.size();
			if (loopTime > 3) {// 最多只能显示三个一级菜单
				loopTime = 3;
			}
			Button button[] = new Button[loopTime];
			for (int i = 0; i < loopTime; i++) {
				Menu pMenu = list.get(i);
				ComplexButton cb = new ComplexButton();
				cb.setName(pMenu.getMenuName());
				Menu subMenu = new Menu();
				subMenu.setMenuLevel(2);
				subMenu.setMenuState(Integer.parseInt(configrue.getProp("positive")));
				subMenu.setParentMenuId(pMenu.getMenuId());
				List<Menu> sublist = menuService.find(subMenu);
				Button subButton[] = new Button[sublist.size()];
				for (int j = 0; j < sublist.size(); j++) {
					Menu m = sublist.get(j);
					if (m.getMenuType() == 1) {// view
						ViewButton b = new ViewButton();
						b.setName(m.getMenuName());
						b.setType("view");
						String oauthUrl = configrue.getProp("oauth");
						oauthUrl = StringUtils.replace(oauthUrl, "APPID", configrue.getProp("appId"));
						oauthUrl = StringUtils.replace(oauthUrl, "REDIRECT_URI",
								URLEncoder.encode(configrue.getProp("oauth.return.url"), "GBK"));
						oauthUrl = StringUtils.replace(oauthUrl, "SCOPE", configrue.getProp("snsapi_userinfo"));
						oauthUrl = StringUtils.replace(oauthUrl, "STATE", m.getParamValue());// 先均跳转到首页，之后根据m.getParamValue()跳转。
						b.setUrl(oauthUrl);
						logger.info(m.getMenuName() + "，oauthUrl：" + oauthUrl);
						subButton[j] = b;
					} else {// click
						ClickButton b = new ClickButton();
						b.setName(m.getMenuName());
						b.setType("click");
						b.setKey(m.getParamValue());
						subButton[j] = b;
					}
				}
				cb.setSub_button(subButton);
				button[i] = cb;
			}
			com.sunshine.gardens.model.pojo.menu.Menu getMenu = new com.sunshine.gardens.model.pojo.menu.Menu();
			getMenu.setButton(button);
			// 调用接口获取凭证
			Token token = CommonUtil.getToken(configrue.getProp("appId"), configrue.getProp("appSecret"));
			if (null != token) {
				// 创建菜单
				boolean result = MenuUtil.createMenu(getMenu, token.getAccessToken());

				// 判断菜单创建结果
				if (result) {
					rsp = 1;
					logger.info("菜单创建成功！");
				} else
					logger.info("菜单创建失败！");
			}
		} catch (BaseAppException e) {
			e.printStackTrace();
		}
		return rsp;
	}
}