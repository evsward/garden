package com.sunshine.gardens.model.po;

import java.util.Date;
import java.util.List;

public class ReserveOrder {
	private String reserveId;

	private Integer productId;
	private Integer productType;

	private String openid;

	private String submitterName;

	private String submitterMobile;

	private String payMethod;

	private String vipId;

	private String specialNotes;

	private int amounts;// 房间数
	private int nights;// 入住晚数

	private Integer orderState;

	private Long totalMoney;

	private Date createTime;

	private Date updateTime;

	private String qrCodePath;
	// __________________________________
	private String password;

	private String vipType;
	private String vipTypePriceCode;
	private List<Product> productDetails;
	private Date checkinDate;
	private Date checkoutDate;

	private String productName;
	private String columnName;

	public String getQrCodePath() {
		return qrCodePath;
	}

	public void setQrCodePath(String qrCodePath) {
		this.qrCodePath = qrCodePath;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public ReserveOrder() {
		super();
	}

	public ReserveOrder(String reserveId) {
		super();
		this.reserveId = reserveId;
	}

	public String getVipTypePriceCode() {
		return vipTypePriceCode;
	}

	public void setVipTypePriceCode(String vipTypePriceCode) {
		this.vipTypePriceCode = vipTypePriceCode;
	}

	public Date getCheckinDate() {
		return checkinDate;
	}

	public void setCheckinDate(Date checkinDate) {
		this.checkinDate = checkinDate;
	}

	public Date getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(Date checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public List<Product> getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(List<Product> productDetails) {
		this.productDetails = productDetails;
	}

	public String getVipType() {
		return vipType;
	}

	public void setVipType(String vipType) {
		this.vipType = vipType;
	}

	public String getPassword() {
		return password == null ? "" : password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getReserveId() {
		return reserveId;
	}

	public void setReserveId(String reserveId) {
		this.reserveId = reserveId == "" ? null : reserveId.trim();
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid == null ? "" : openid.trim();
	}

	public String getSubmitterName() {
		return submitterName;
	}

	public void setSubmitterName(String submitterName) {
		this.submitterName = submitterName == "" ? null : submitterName.trim();
	}

	public String getSubmitterMobile() {
		return submitterMobile;
	}

	public void setSubmitterMobile(String submitterMobile) {
		this.submitterMobile = submitterMobile == "" ? null : submitterMobile.trim();
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public String getVipId() {
		return vipId;
	}

	public void setVipId(String vipId) {
		this.vipId = vipId == "" ? null : vipId.trim();
	}

	public String getSpecialNotes() {
		return specialNotes;
	}

	public void setSpecialNotes(String specialNotes) {
		this.specialNotes = specialNotes == null ? null : specialNotes.trim();
	}

	public int getAmounts() {
		return amounts;
	}

	public void setAmounts(int amounts) {
		this.amounts = amounts;
	}

	public int getNights() {
		return nights;
	}

	public void setNights(int nights) {
		this.nights = nights;
	}

	public Integer getOrderState() {
		return orderState;
	}

	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}

	public Long getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Long totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}