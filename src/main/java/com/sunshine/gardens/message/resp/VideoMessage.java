package com.sunshine.gardens.message.resp;

/**
 * 视频消息
 * @author LiuWenbin
 * @2015年9月5日
 */
public class VideoMessage extends BaseMessage {
	// 视频
	private Video Video;

	public Video getVideo() {
		return Video;
	}

	public void setVideo(Video video) {
		Video = video;
	}
}
