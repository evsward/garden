package com.sunshine.gardens.controller.task;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dance.core.utils.BaseAppException;
import com.dance.core.utils.spring.SpringContextHolder;
import com.sunshine.gardens.model.po.ReserveOrder;
import com.sunshine.gardens.model.po.VipInfo;
import com.sunshine.gardens.model.pojo.RespMsg;
import com.sunshine.gardens.model.pojo.Token;
import com.sunshine.gardens.model.pojo.WSSmsMessage;
import com.sunshine.gardens.service.ReserveOrderService;
import com.sunshine.gardens.service.VipInfoService;
import com.sunshine.gardens.utils.AdvancedUtil;
import com.sunshine.gardens.utils.CommonUtil;
import com.sunshine.gardens.utils.MyPropertyPlaceholderConfigurer;

/**
 * 基于注解的定时任务
 * 
 * @author LiuWenbin
 * @2015年11月22日
 */
@Component
public class MyTaskAnnotation {
	private final static Log logger = LogFactory.getLog(MyTaskAnnotation.class);
	private final static MyPropertyPlaceholderConfigurer configrue = SpringContextHolder.getBean("propertyPlaceholder");

	@Autowired
	private ReserveOrderService reserveOrderService;
	@Autowired
	private VipInfoService vipInfoService;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	/**
	 * 1 Seconds (0-59) 2 Minutes (0-59) 3 Hours (0-23) 4 Day of month (1-31) 5
	 * Month (1-12 or JAN-DEC) 6 Day of week (1-7 or SUN-SAT) 7 Year (1970-2099)
	 * 取值：可以是单个值，如6； 也可以是个范围，如9-12； 也可以是个列表，如9,11,13 也可以是任意取值，使用
	 * 
	 * @throws BaseAppException
	 * @throws ParseException
	 */
	@Scheduled(cron = "0 0 1 * * *")
	public void clearInValidResources() {
		try {
			List<ReserveOrder> orderList = reserveOrderService.find("hasQRCodeOrders");
			if (orderList.size() <= 0) {
				logger.error("未查询到订单！");
				return;
			}
			for (ReserveOrder ro : orderList) {
				if (ro.getOrderState() == Integer.parseInt(configrue.getProp("order.state.reserved"))) {
					File f = new File("/home/work/service/garden/web/static/QRCodePics/", ro.getReserveId()
							+ "qrcodepic.png");
					if (f.isDirectory()) {
						f.delete();
					}
				}
			}
		} catch (BaseAppException e) {
			logger.error("查询订单出错！");
			e.printStackTrace();
		}
		// File qrFolder = new
		// File("/home/work/service/garden/web/static/QRCodePics/");
		// File qrFiles[] = qrFolder.listFiles();
		// logger.info("定时删除二维码图片开始...");
		// for (int i = 0; i < qrFiles.length; i++) {
		// if (qrFiles[i].isDirectory()) {
		// File f = qrFiles[i];
		// String fN = f.getName();
		// logger.info("检查文件："+fN);
		// Date fD = sdf.parse(fN.substring(0, 8));
		// int period= daysBetween(new Date(), fD);
		// if (Math.abs(period) > 7) {
		// f.delete();
		// logger.info("已删除文件："+fN);
		// }
		// }
		// }
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) throws ParseException {
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 心跳更新。启动时执行一次，之后每隔5分钟执行一次
	 * 
	 * @throws BaseAppException
	 * 
	 * @throws ParseException
	 */
//	@Scheduled(fixedRate = 1000 * 60 * 5)
	public void pushConsumeMsg(HttpSession session) throws BaseAppException {
		// TODO 定时请求西软待发送信息，推送到微信
		String accessToken = (String) session.getAttribute("accessToken");
		if (accessToken == null) {
			Token token = CommonUtil.getToken(configrue.getProp("appId"), configrue.getProp("appSecret"));
			// 将accessToken存入
			accessToken = token.getAccessToken();
			session.setAttribute("accessToken", accessToken);
		}
		List<WSSmsMessage> msgList = vipInfoService.getMsgPush();
		for (WSSmsMessage wsMsg : msgList) {
			VipInfo info = new VipInfo();
			info.setVipId(wsMsg.getVipAccount());
			List<VipInfo> infos = vipInfoService.find(info);
			if (infos.size() == 1) {
				RespMsg resp = AdvancedUtil.sendTemplateMsg(infos.get(0).getOpenid(),accessToken, wsMsg);
				if (resp.getErrcode() == 0) {
					logger.info(wsMsg.getVipAccount() + ",会员消费信息推送成功！");
				} else {
					logger.error("返回码：" + resp.getErrcode() + "，" + resp.getErrmsg());
				}
			} else if (infos.size() > 1) {
				logger.error(wsMsg.getVipAccount() + "异常：会员卡号绑定了两个微信号，错误！");
			} else {
				logger.info(wsMsg.getVipAccount() + ",会员未绑定微信公众号！");
			}
		}
	}

	public static void main(String[] args) throws ParseException {
		// String fN = "201511271334177YntBzBqAXJMBqrcodepic.png";
		// Date fD = sdf.parse(fN.substring(0, 8));
		// int period = daysBetween(new Date(), fD);
		// if (Math.abs(period) > 7) {
		// System.out.println(1);
		// }
	}
}
