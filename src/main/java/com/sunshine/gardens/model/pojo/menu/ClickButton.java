package com.sunshine.gardens.model.pojo.menu;

/**
 * click type button
 * @author LiuWenbin
 * @2015年9月5日
 */
public class ClickButton extends Button {
	private String type;
	private String key;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}