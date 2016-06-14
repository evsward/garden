package com.sunshine.gardens.message.resp;

/**
 * 图片消息
 * @author LiuWenbin
 * @2015年9月5日
 */
public class ImageMessage extends BaseMessage {
	// 图片
	private Image Image;

	public Image getImage() {
		return Image;
	}

	public void setImage(Image image) {
		Image = image;
	}
}
