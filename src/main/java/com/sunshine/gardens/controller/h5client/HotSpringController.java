package com.sunshine.gardens.controller.h5client;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dance.core.utils.BaseAppException;
import com.dance.core.utils.spring.SpringContextHolder;
import com.sunshine.gardens.model.po.Product;
import com.sunshine.gardens.model.po.ReserveOrder;
import com.sunshine.gardens.service.ProductService;
import com.sunshine.gardens.service.ReserveOrderService;
import com.sunshine.gardens.utils.MyPropertyPlaceholderConfigurer;

@Controller
@RequestMapping("/h5client/hotspring")
public class HotSpringController {
	private final static Log logger = LogFactory.getLog(HotSpringController.class);
	private final static MyPropertyPlaceholderConfigurer configrue = SpringContextHolder.getBean("propertyPlaceholder");

	@Autowired
	private ProductService productService;
	@Autowired
	private ReserveOrderService reserveOrderService;
	// private static SimpleDateFormat sdf2 = new
	// SimpleDateFormat("yyyyMMddHHmmSS");

	@RequestMapping
	public String loadIndex(ModelMap map, HttpSession session) {
		logger.info("进入温泉会门票购买界面");
		return "pages/hotspring/intro";
	}

	@RequestMapping(value = "/inputOrder")
	public String inputOrder(ModelMap map, int productId) throws BaseAppException {
		List<Product> list = productService.find(new Product(productId));
		Product p = list.get(0);
		map.addAttribute("ticket", p);
		map.addAttribute("vipType", "门市价");
		if (p.getInventory() < 1) {
			map.addAttribute("msg", "该门票暂已售空！");
			return "pages/bookroom/orderFailed";
		}
		return "pages/hotspring/inputOrder";
	}

	@RequestMapping(value = "/ticketChoosed")
	public String ticketChoosed(ModelMap map) throws BaseAppException {
		Product hotSpring = new Product();
		hotSpring.setProductState(Integer.parseInt(configrue.getProp("positive")));
		hotSpring.setColumnId(Long.valueOf(configrue.getProp("HOT_SPRING_COLUMNID")));
		List<Product> list = productService.find(hotSpring);
		map.addAttribute("allProducts", list);
		return "pages/hotspring/ticketChoosed";
	}

	@RequestMapping(value = "/findMyOrders")
	public String findMyOrders(ModelMap map, HttpSession session) throws BaseAppException {
		String openId = (String) session.getAttribute("openId");
		logger.info("预定订单，openid：" + openId);
		if (openId == null) {
			return "pages/bookroom/orderFailed";
		}
		ReserveOrder ro = new ReserveOrder();
		ro.setOpenid(openId);
		List<ReserveOrder> rOrderList = reserveOrderService.find(ro);
		List<ReserveOrder> rOrderResultList = new ArrayList<ReserveOrder>();
		for (ReserveOrder rOrder : rOrderList) {
			Product product = productService.find(new Product(rOrder.getProductId())).get(0);
			if (product.getProductType() == 2) {// 温泉会产品
				rOrder.setProductName(product.getProductName());
				rOrderResultList.add(rOrder);
			}
		}
		map.addAttribute("rOrderList", rOrderResultList);
		return "pages/hotspring/myOrders";
	}

	@RequestMapping(value = "/myOrders")
	public String myOrders() {
		return "pages/hotspring/myOrders";
	}
}
