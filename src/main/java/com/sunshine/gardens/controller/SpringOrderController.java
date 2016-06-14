package com.sunshine.gardens.controller;

import java.util.ArrayList;
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
import com.sunshine.gardens.model.po.ReserveOrder;
import com.sunshine.gardens.model.po.SysRole;
import com.sunshine.gardens.model.po.SysUser;
import com.sunshine.gardens.model.po.SysUserRole;
import com.sunshine.gardens.service.ColumnService;
import com.sunshine.gardens.service.ProductService;
import com.sunshine.gardens.service.ReserveOrderService;
import com.sunshine.gardens.service.SysRoleService;
import com.sunshine.gardens.service.SysUserRoleService;
import com.sunshine.gardens.utils.MyPropertyPlaceholderConfigurer;

@Controller
@RequestMapping("/service/springOrder")
public class SpringOrderController {

	private final static Log logger = LogFactory.getLog(SpringOrderController.class);
	private final static MyPropertyPlaceholderConfigurer configrue = SpringContextHolder.getBean("propertyPlaceholder");

	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysRoleService sysRoleService;

	private long totalCount = 1;// 总数据条数，最低为1
	private long totalPages = 1;// 总页数，最低为1

	@Autowired
	private ProductService productService;
	@Autowired
	private ColumnService columnService;
	@Autowired
	private ReserveOrderService reserveOrderService;

	@RequestMapping
	public String loadIndex(ReserveOrder reserveOrder, Integer pageNO, Integer pageSize, ModelMap map,
			HttpSession session) throws BaseAppException {
		SysUser sysUser = (SysUser) session.getAttribute("loginUser");
		if (sysUser == null) {
			return "account/index";
		}
		SysUserRole sysUserRole = new SysUserRole();
		sysUserRole.setRoleCode(configrue.getProp("SpringOrderCode"));
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
		map.addAttribute("roleCode", configrue.getProp("SpringOrderCode"));

		Page<ReserveOrder> page = new Page<ReserveOrder>();
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
		reserveOrder.setProductType(2);// 只查询温泉会产品的订单
		page = reserveOrderService.find(page, reserveOrder);
		List<ReserveOrder> rOrderResultList = new ArrayList<ReserveOrder>();
		List<ReserveOrder> rOrderList = page.getResult();
		for (ReserveOrder ro : rOrderList) {
			Product p = productService.find(new Product(ro.getProductId())).get(0);
			CmsColumn cc = columnService.find(new CmsColumn(p.getColumnId())).get(0);
			ro.setProductName(p.getProductName());
			ro.setColumnName(cc.getColumnName());
			rOrderResultList.add(ro);
		}
		totalCount = page.getTotalCount();
		totalPages = page.getTotalPages();
		map.addAttribute("pageNO", page.getPageNo());// 当前第XX页
		map.addAttribute("pageSize", pageSize);// 每页显示XX条
		map.addAttribute("pages", totalPages);// 共XX页
		map.addAttribute("rows", totalCount);// 共XX条
		map.addAttribute("allOrders", rOrderResultList);
		return "service/springorder";
	}
}
