package com.sunshine.gardens.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sunshine.gardens.model.Message;

public class HttpAPIClient {
	private final static Log logger = LogFactory.getLog(HttpAPIClient.class);
	HttpClient client = null;

	public HttpAPIClient(String host, int port) {
		client = new HttpClient();
		client.getHostConfiguration().setHost(host, port);
		if (logger.isInfoEnabled()) {
			logger.info("host:" + host + " port:" + port);
		}
	}

	public static String sendMsg(String host, int port, String uri, Message message) {
		HttpAPIClient client = new HttpAPIClient(host, port);
		return client.sendMessage(uri, message);
	}

	public String sendMessage(String uri, Message message) {
		try {
			message.doEncode();
			PostMethod method = new PostMethod(uri);
			Map<String, String> params = message.getParams();
			Set<Entry<String, String>> entrySet = params.entrySet();
			for (Entry<String, String> entry : entrySet) {
				method.addParameter(entry.getKey(), entry.getValue());
			}
			int state = client.executeMethod(method);
			if (state != 200) {
				return null;
			}
			return method.getResponseBodyAsString();
		} catch (Exception e) {
			logger.error("send error!", e);
			return null;
		}
	}

	public static String sendPost(String content, String url) {
		logger.info("发送Post请求，数据信息：" + content);
		PostMethod post = new PostMethod(url);
		post.setRequestEntity(new StringRequestEntity(content));
		HttpClient httpclient = new HttpClient();
		int result = 0;
		try {
			result = httpclient.executeMethod(post);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String resultStr = "";
		logger.info("Response status code: " + result);
		logger.info("Response body: ");
		try {
			byte[] b = post.getResponseBody();
			resultStr = new String(b, "utf-8");
			logger.info(resultStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		post.releaseConnection();
		return resultStr;
	}

	/**
	 * 发送HTTP post请求
	 * 
	 * @param param
	 * @param urls
	 * @return
	 */
	public static String post(String param, String urls) {
		logger.info("发送 Post请求， 数据信息：" + param);
		String result = "";
		URL url = null;
		HttpURLConnection httpurl = null;
		try {
			url = new URL(urls);
			httpurl = (HttpURLConnection) url.openConnection();
			httpurl.setConnectTimeout(50000);
			httpurl.setReadTimeout(50000);
			httpurl.setRequestMethod("POST");
			httpurl.setDoOutput(true);
			OutputStream out = httpurl.getOutputStream();
			out.write(param.getBytes("UTF-8"));
			out.flush();
			out.close();
			BufferedReader bufferreader = new BufferedReader(new InputStreamReader(httpurl.getInputStream(), "UTF-8"));
			StringBuffer stringbuffer = new StringBuffer();
			int ch;
			while ((ch = bufferreader.read()) > -1) {
				stringbuffer.append((char) ch);
			}
			result = stringbuffer.toString().trim();
			logger.info(result);
			bufferreader.close();
			httpurl.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String sendXMLPost(String content, String url) {
		logger.info("发送XML Post请求，XML 数据信息：" + content);
		PostMethod post = new PostMethod(url);
		post.setRequestEntity(new StringRequestEntity(content));
		// 指定请求内容的类型
		post.setRequestHeader("Content-type", "text/xml; charset=utf-8");
		HttpClient httpclient = new HttpClient();
		int result = 0;
		try {
			result = httpclient.executeMethod(post);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String resultStr = "";
		logger.info("Response status code: " + result);
		logger.info("Response body: ");
		try {
			byte[] b = post.getResponseBody();
			resultStr = new String(b, "utf-8");
			logger.info(resultStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		post.releaseConnection();
		return resultStr;
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("http_client_ip");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		// 如果是多级代理，那么取第一个ip为客户ip
		if (ip != null && ip.indexOf(",") != -1) {
			ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
		}
		if ("0:0:0:0:0:0:0:1".equals(ip)) {
			ip = "127.0.0.1";
		}
		return ip;
	}

}
