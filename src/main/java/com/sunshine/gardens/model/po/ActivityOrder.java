package com.sunshine.gardens.model.po;

public class ActivityOrder {
	private int activityOrderId;
	private String openid;
	private int activityId;
	private String submitterName1;
	private String submitterName2;
	private String submitterMobile;
	private String submitterDate1;
	private String specialNotes;
	private int submitterAge1;
	private int submitterAge2;

	private String activityName;

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	private String nickname;

	public ActivityOrder() {
		super();
	}

	public ActivityOrder(String openid) {
		super();
		this.openid = openid;
	}

	public int getSubmitterAge1() {
		return submitterAge1;
	}

	public void setSubmitterAge1(int submitterAge1) {
		this.submitterAge1 = submitterAge1;
	}

	public int getSubmitterAge2() {
		return submitterAge2;
	}

	public void setSubmitterAge2(int submitterAge2) {
		this.submitterAge2 = submitterAge2;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSubmitterDate1() {
		return submitterDate1;
	}

	public void setSubmitterDate1(String submitterDate1) {
		this.submitterDate1 = submitterDate1;
	}

	public int getActivityOrderId() {
		return activityOrderId;
	}

	public void setActivityOrderId(int activityOrderId) {
		this.activityOrderId = activityOrderId;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid == "" ? null : openid.trim();
	}

	public int getActivityId() {
		return activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public String getSubmitterName1() {
		return submitterName1;
	}

	public void setSubmitterName1(String submitterName1) {
		this.submitterName1 = submitterName1 == "" ? null : submitterName1.trim();
	}

	public String getSubmitterName2() {
		return submitterName2;
	}

	public void setSubmitterName2(String submitterName2) {
		this.submitterName2 = submitterName2;
	}

	public String getSubmitterMobile() {
		return submitterMobile;
	}

	public void setSubmitterMobile(String submitterMobile) {
		this.submitterMobile = submitterMobile == "" ? null : submitterMobile.trim();
	}

	public String getSpecialNotes() {
		return specialNotes;
	}

	public void setSpecialNotes(String specialNotes) {
		this.specialNotes = specialNotes;
	}

}