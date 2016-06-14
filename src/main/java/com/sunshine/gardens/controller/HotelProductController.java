package com.sunshine.gardens.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
@RequestMapping("/service/hotelProduct")
public class HotelProductController {
	private final static Log logger = LogFactory.getLog(HotelProductController.class);
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
		sysUserRole.setRoleCode(configrue.getProp("HotelProductRoleCode"));
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
		map.addAttribute("roleCode", configrue.getProp("HotelProductRoleCode"));
		Product condition = new Product();
		condition.setProductType(1);
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
		return "service/hotelproduct";
	}

	@RequestMapping(value = "/updateProduct")
	@ResponseBody
	public int updateProduct(Product product) throws BaseAppException {
		productService.update(product);
		List<Product> list = productService.find(product);
		return list.get(0).getProductState();
	}

	@RequestMapping(value = "/getProductColumns")
	@ResponseBody
	public List<CmsColumn> getProductColumns(CmsColumn cc) throws BaseAppException {
		List<CmsColumn> list = columnService.find(cc);
		return list;
	}

	@RequestMapping(value = "/addproductItem")
	public String addproductItem(Product product) throws BaseAppException {
		if (product.getProductType() == 1) {
			product.setValidDay("0");
		}
		product.setCreateTime(new Date());
		productService.insert(product);
		return "redirect:/service/hotelProduct";
	}

	@RequestMapping(value = "/uploadIconFile")
	@ResponseBody
	public String uploadIconFile(int productId, HttpServletRequest request) throws IllegalStateException, IOException,
			BaseAppException {
		MyPropertyPlaceholderConfigurer configrue = SpringContextHolder.getBean("propertyPlaceholder");
		String ctxPath = configrue.getProp("garden.program.root.path");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile iconImg = multipartRequest.getFile("iconImg");
		String iconsrc = "/icons/iconImg00" + productId + ".jpg";
		File tempImg = new File(ctxPath + iconsrc);
		if (!tempImg.exists() || !tempImg.isDirectory()) {
			tempImg.mkdirs();
		}
		iconImg.transferTo(tempImg);
		Product p = new Product();
		p.setProductId(productId);
		p.setIconsrc(iconsrc);
		productService.update(p);
		logger.info(tempImg.getAbsolutePath());
		return tempImg.getAbsolutePath();
	}

	@RequestMapping(value = "/showIconImage")
	@ResponseBody
	public String showIconImage(int productId, HttpServletResponse response) throws IllegalStateException, IOException,
			BaseAppException {
		String ctxPath = configrue.getProp("garden.program.root.path");
		Product a = productService.find(new Product(productId)).get(0);
		String iconsrc = a.getIconsrc();
		File tempImg = new File(ctxPath + iconsrc);
		logger.info(tempImg.getAbsolutePath());
		if (!tempImg.exists()) {
			return null;
		}
		FileInputStream hFile = new FileInputStream(tempImg);
		int i = hFile.available(); // 得到文件大小
		byte data[] = new byte[i];
		hFile.read(data); // 读数据
		response.setContentType("image/*"); // 设置返回的文件类型
		OutputStream toClient = response.getOutputStream(); // 得到向客户端输出二进制数据的对象
		toClient.write(data); // 输出数据
		toClient.flush();
		toClient.close();
		hFile.close();
		return null;
	}

}