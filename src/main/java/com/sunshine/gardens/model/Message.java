package com.sunshine.gardens.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.time.DateFormatUtils;

import com.dance.core.utils.security.DigestUtils;

public abstract class Message {
	protected String sessionKey;
	protected String accessTime;
	protected String message;
	protected int result;
	protected int errorCode;
	protected Map<String, String> params = new HashMap<>();

	public void doEncode() throws IOException {
		accessTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"); 
		String sessionStr = sessionKey + "000000000" + accessTime;
		ByteArrayInputStream in = new ByteArrayInputStream(sessionStr.getBytes());
		sessionKey = DigestUtils.md5Hex(in);
		
		params.put("sessionKey", sessionKey);
		params.put("accessTime", accessTime);
		encode();
	}

	public Map<String, String> getParams() {
		return params;
	}

	public abstract void encode();

	public abstract <T> T decode(String message);

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(String accessTime) {
		this.accessTime = accessTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
