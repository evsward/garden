package com.sunshine.gardens.model.pojo;

/**
 * WS GetMemberInfo
 * 
 * @author LiuWenbin
 * @2016年1月13日
 */
public class MemberInfo {
	private String accountID;// 卡号
	private String genericName;// 卡名称
	private int programCode;// 卡等级
	private String programCodeDescript;
	private String programType;// 卡类型
	private String programTypeDescript;
	private String phoneNumber;

	private Double balance;// 余额

	private String arAccount;// 储值卡AR账号

	private String verifyCode;// 绑定时的短信验证码

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getArAccount() {
		return arAccount;
	}

	public void setArAccount(String arAccount) {
		this.arAccount = arAccount;
	}

	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	public String getGenericName() {
		return genericName;
	}

	public void setGenericName(String genericName) {
		this.genericName = genericName;
	}

	public int getProgramCode() {
		return programCode;
	}

	public void setProgramCode(int programCode) {
		this.programCode = programCode;
	}

	public String getProgramCodeDescript() {
		return programCodeDescript;
	}

	public void setProgramCodeDescript(String programCodeDescript) {
		this.programCodeDescript = programCodeDescript;
	}

	public String getProgramType() {
		return programType;
	}

	public void setProgramType(String programType) {
		this.programType = programType;
	}

	public String getProgramTypeDescript() {
		return programTypeDescript;
	}

	public void setProgramTypeDescript(String programTypeDescript) {
		this.programTypeDescript = programTypeDescript;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

}