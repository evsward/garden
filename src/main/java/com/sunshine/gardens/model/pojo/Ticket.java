package com.sunshine.gardens.model.pojo;

/**
 * Js-api Ticket
 * 
 * @author LiuWenbin
 * @2016年1月9日
 */
public class Ticket {
	private int errcode;
	private String errmsg;
	private String ticket;
	private int expires_in;

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}

}