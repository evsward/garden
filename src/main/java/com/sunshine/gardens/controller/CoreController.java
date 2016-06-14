package com.sunshine.gardens.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunshine.gardens.service.CoreService;
import com.sunshine.gardens.utils.SignUtil;

@Controller
@RequestMapping("core")
public class CoreController {
	
	@Autowired
	private CoreService coreService;

	/**
	 * 请求校验（确认请求来自微信服务器）
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		// 请求校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			return echostr;
		}
		return null;
	}

	/**
	 * 处理微信服务器发来的消息
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public String doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return coreService.processRequest(request);
	}
}
