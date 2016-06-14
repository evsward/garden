package com.sunshine.gardens.message.req;

/**
 * 图片消息
 * @author LiuWenbin
 * @2015年9月5日
 */
public class ImageMessage extends BaseMessage {
	// 图片链接
	private String PicUrl;

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
}
