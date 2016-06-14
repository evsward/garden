package com.sunshine.gardens.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

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
import com.sunshine.gardens.utils.MD5Util;
import com.sunshine.gardens.utils.MyPropertyPlaceholderConfigurer;

@Controller
@RequestMapping("/service/hotelOrder")
public class HotelOrderController {

	private final static Log logger = LogFactory.getLog(HotelOrderController.class);
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
		sysUserRole.setRoleCode(configrue.getProp("HotelOrderCode"));
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
		map.addAttribute("roleCode", configrue.getProp("HotelOrderCode"));

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
		reserveOrder.setProductType(1);// 只查询客房产品的订单
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
		return "service/hotelorder";
	}

	/**
	 * 订单消费接口
	 * 
	 * @param reserveOrderId
	 * @return
	 * @throws BaseAppException
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws ParseException
	 */
	@RequestMapping(value = "/cost")
	@ResponseBody
	public JSONObject reserveOrderCost(String reserveOrderId, String timestamp, String sign)
			throws BaseAppException, NoSuchAlgorithmException, UnsupportedEncodingException, ParseException {
		JSONObject jsonObj = new JSONObject();
		if (reserveOrderId == null || timestamp == null || sign == null) {
			jsonObj.put("result", "0");
			jsonObj.put("data", "参数有误");
			return jsonObj;
		}
		String key = configrue.getProp("order.query.key");
		String encriptStr = "reserveOrderId=" + reserveOrderId + "&timestamp=" + timestamp + "&key=" + key;
		logger.info("query order Interface 签名前的明文：" + encriptStr);
		if (MD5Util.validPassword(encriptStr, sign)) {
			// 验签成功
			List<ReserveOrder> list = reserveOrderService.find(new ReserveOrder(reserveOrderId));
			if (list.size() == 0) {
				jsonObj.put("result", "0");
				jsonObj.put("data", "无效订单号");
			}
			ReserveOrder rOrder = list.get(0);
			if (rOrder.getOrderState() == Integer.parseInt(configrue.getProp("order.state.used"))) {
				jsonObj.put("result", "0");
				jsonObj.put("data", "该订单已被使用");
			} else if (rOrder.getOrderState() == Integer.parseInt(configrue.getProp("order.state.reserved"))) {
				// 检查订单是否已过期
				Date checkout = rOrder.getCheckoutDate();
				if (checkout == null) {
					jsonObj.put("result", "0");
					jsonObj.put("data", "该订单有误，没有离店时间");
					return jsonObj;
				}
				Product pd = new Product();
				pd.setProductId(rOrder.getProductId());
				Product product = productService.find(pd).get(0);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date today = sdf.parse(sdf.format(new Date()));
				if (checkout.after(today) || checkout.compareTo(today) == 0) {
					// 离店日期前(包括当天)均可办理消费掉订单
					// 如果是温泉门票，则需要判断产品“工作日”还是“节假日”
					Calendar cal = Calendar.getInstance();
					cal.setTime(today);
					if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
							|| cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
						if ("workday".equals(product.getValidDay())) {
							jsonObj.put("result", "0");
							jsonObj.put("data", "该订单为工作日门票，节假日不可消费！");
							return jsonObj;
						}
					} else {
						if ("holiday".equals(product.getValidDay())) {
							jsonObj.put("result", "0");
							jsonObj.put("data", "该订单为节假日门票，工作日不可消费！");
							return jsonObj;
						}
					}
					logger.info("可消费");
					// 更新订单状态->预定成功
					rOrder.setOrderState(Integer.parseInt(configrue.getProp("order.state.used")));
					reserveOrderService.update(rOrder);
					logger.info("消费成功，更新 订单状态 ...." + rOrder.getReserveId() + " 已消费");
					jsonObj.put("result", "1");
					jsonObj.put("data", "消费成功");
					// 消费成功以后删除二维码
					File f = new File("/home/work/service/garden/web/static/QRCodePics/",
							rOrder.getReserveId() + "qrcodepic.png");
					if (f.isDirectory()) {
						f.delete();
					}
				} else {
					// 离店日期之后不可消费订单，按过期处理。
					logger.info("订单已过期：" + rOrder.getReserveId());
					jsonObj.put("result", "0");
					jsonObj.put("data", "订单已过期");
					rOrder.setSpecialNotes("此笔订单已过期");
					reserveOrderService.update(rOrder);
				}
			} else if (rOrder.getOrderState() == Integer.parseInt(configrue.getProp("order.state.payed"))) {
				jsonObj.put("result", "0");
				jsonObj.put("data", "该订单支付成功，预定失败，请于前台安排房间或办理退款");
			} else {
				jsonObj.put("result", "0");
				jsonObj.put("data", "该订单未支付");
			}
		} else {
			jsonObj.put("result", "0");
			jsonObj.put("data", "验签失败");
		}
		return jsonObj;
	}

	private void processField(Object obj) {
		Class clzz = obj.getClass();
		Field fields[] = clzz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			try {
				Object value = field.get(obj);
				if (value == null) {
					if (field.getType() == List.class) {
						field.set(obj, new ArrayList());
					}
					if (field.getType() == Date.class) {
						field.set(obj, new Date());
					}
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

	}

	@RequestMapping(value = "/queryOrder")
	@ResponseBody
	public JSONObject queryOrder(String reserveOrderId, String timestamp, String sign)
			throws BaseAppException, NoSuchAlgorithmException, UnsupportedEncodingException {
		JSONObject jsonObj = new JSONObject();
		if (reserveOrderId == null || timestamp == null || sign == null) {
			jsonObj.put("result", "0");
			jsonObj.put("data", "参数有误");
			return jsonObj;
		}
		String key = configrue.getProp("order.query.key");
		String encriptStr = "reserveOrderId=" + reserveOrderId + "&timestamp=" + timestamp + "&key=" + key;
		logger.info("query order Interface 签名前的明文：" + encriptStr);
		if (MD5Util.validPassword(encriptStr, sign)) {
			// 验签成功
			List<ReserveOrder> list = reserveOrderService.find(new ReserveOrder(reserveOrderId));
			if (list.size() > 0) {
				ReserveOrder rOrder = list.get(0);
				jsonObj.put("result", "1");
				processField(rOrder);
				jsonObj.put("data", rOrder);
			} else {
				jsonObj.put("result", "0");
				jsonObj.put("data", "没有相应订单号！");
			}

		} else {
			jsonObj.put("result", "0");
			jsonObj.put("data", "验签失败");
		}
		return jsonObj;
	}

	// @RequestMapping(value = "/DESQRcode")
	// @ResponseBody
	// public JSONObject TestDecprptDESQRcode(String qrcodeStr) {
	// Object qrcodeObj = ThreeDES.threeDesDecrypt(qrcodeStr);
	// JSONObject jsonObj = JSONObject.fromObject(qrcodeObj);
	// return jsonObj;
	// }
}
