package com.sunshine.gardens.model.pojo;

import java.util.Map;

public class TemplateData {
	private Map<String, String> productType;
	private Map<String, String> name;
	private Map<String, String> accountType;
	private Map<String, String> account;
	private Map<String, String> time;
	private Map<String, String> remark;

	public Map<String, String> getProductType() {
		return productType;
	}

	public void setProductType(Map<String, String> productType) {
		this.productType = productType;
	}

	public Map<String, String> getName() {
		return name;
	}

	public void setName(Map<String, String> name) {
		this.name = name;
	}

	public Map<String, String> getAccountType() {
		return accountType;
	}

	public void setAccountType(Map<String, String> accountType) {
		this.accountType = accountType;
	}

	public Map<String, String> getAccount() {
		return account;
	}

	public void setAccount(Map<String, String> account) {
		this.account = account;
	}

	public Map<String, String> getTime() {
		return time;
	}

	public void setTime(Map<String, String> time) {
		this.time = time;
	}

	public Map<String, String> getRemark() {
		return remark;
	}

	public void setRemark(Map<String, String> remark) {
		this.remark = remark;
	}

}
