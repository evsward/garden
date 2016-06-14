package com.sunshine.gardens.message.req;

/**
 * 文本消息
 * @author LiuWenbin
 * @2015年9月5日
 */
public class TextMessage extends BaseMessage {
	// 消息内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}
