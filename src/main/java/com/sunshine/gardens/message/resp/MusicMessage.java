package com.sunshine.gardens.message.resp;

/**
 * 音乐消息
 * @author LiuWenbin
 * @2015年9月5日
 */
public class MusicMessage extends BaseMessage {
	// 音乐
	private Music Music;

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		Music = music;
	}
}
