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

import com.dance.core.orm.Page;
import com.dance.core.utils.BaseAppException;
import com.dance.core.utils.spring.SpringContextHolder;
import com.sunshine.gardens.model.po.CmsColumn;
import com.sunshine.gardens.model.po.Product;
import com.sunshine.gardens.model.po.SysRole;
import com.sunshine.gardens.model.po.SysUser;
import com.sunshine.gardens.model.po.SysUserRole;
import com.sunshine.gardens.service.ColumnService;
import com.sunshine.gardens.service.ProductService;
import com.sunshine.gardens.service.SysRoleService;
import com.sunshine.gardens.service.SysUserRoleService;
import com.sunshine.gardens.utils.MyPropertyPlaceholderConfigurer;

@Controller
@RequestMapping("/service/hotSpring")
public class HotSpringProductController {
	private final static Log logger = LogFactory.getLog(HotSpringProductController.class);
	private final static MyPropertyPlaceholderConfigurer configrue = SpringContextHolder.getBean("propertyPlaceholder");

	@Autowired
	private ProductService productService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private ColumnService columnService;

	private long totalCount = 1;// 总数据条数，最低为1
	private long totalPages = 1;// 总页数，最低为1

	@RequestMapping
	public String loadIndex(Integer pageNO, Integer pageSize, ModelMap map, HttpSession session)
			throws BaseAppException {
		SysUser sysUser = (SysUser) session.getAttribute("loginUser");
		if (sysUser == null) {
			return "account/index";
		}
		SysUserRole sysUserRole = new SysUserRole();
		sysUserRole.setRoleCode(configrue.getProp("HotSpringProductRoleCode"));
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
		map.addAttribute("roleCode", configrue.getProp("HotSpringProductRoleCode"));
		Product condition = new Product();
		condition.setProductType(2);
		List<CmsColumn> allColumn = columnService.find(new CmsColumn());
		map.addAttribute("allColumn", allColumn);

		Page<Product> page = new Page<Product>();
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
		page = productService.find(page, condition);
		totalCount = page.getTotalCount();
		totalPages = page.getTotalPages();
		map.addAttribute("pageNO", page.getPageNo());// 当前第XX页
		map.addAttribute("pageSize", pageSize);// 每页显示XX条
		map.addAttribute("pages", totalPages);// 共XX页
		map.addAttribute("rows", totalCount);// 共XX条
		map.addAttribute("allProducts", page.getResult());
		map.addAttribute("allColumn", allColumn);
		return "service/hotSpring";

	}

	@RequestMapping(value = "/addproductItem")
	public String addproductItem(Product product) throws BaseAppException {
		if (product.getProductType() == 1) {
			product.setValidDay("0");
		}
		product.setCreateTime(new Date());
		productService.insert(product);
		return "redirect:/service/hotSpring";
	}

}