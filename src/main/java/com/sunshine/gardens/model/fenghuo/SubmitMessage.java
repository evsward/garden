package com.sunshine.gardens.model.fenghuo;

import java.util.List;

public class SubmitMessage {
	private String clientID;
	private String share_secret;
	List<Message> messageList;

	public String getClientID() {
		return clientID;
	}

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	public String getShare_secret() {
		return share_secret;
	}

	public void setShare_secret(String share_secret) {
		this.share_secret = share_secret;
	}

	public List<Message> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<Message> messageList) {
		this.messageList = messageList;
	}

}
