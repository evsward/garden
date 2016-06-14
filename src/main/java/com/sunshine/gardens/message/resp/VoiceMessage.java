package com.sunshine.gardens.message.resp;

/**
 * 语音消息
 * @author LiuWenbin
 * @2015年9月5日
 */
public class VoiceMessage extends BaseMessage {
	// 语音
	private Voice Voice;

	public Voice getVoice() {
		return Voice;
	}

	public void setVoice(Voice voice) {
		Voice = voice;
	}
}
