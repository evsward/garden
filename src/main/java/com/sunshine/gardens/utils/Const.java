package com.sunshine.gardens.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Const {
	// 会员类型
	public final static int NOVIP = 0;
	public final static int SILVER = 1;
	public final static int GOLD = 2;
	public final static int INFINITE = 3;

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date checkout = sdf.parse("2015-12-9");
		Date today = sdf.parse(sdf.format(new Date()));
		if (checkout.after(today) || checkout.compareTo(today) == 0) {
			// 离店日期前(包括当天)均可办理消费掉订单
			System.out.println("可入住");
		} else {
			// 离店日期之后不可消费订单，按过期处理。
			System.out.println("已过期");
		}
	}
}
