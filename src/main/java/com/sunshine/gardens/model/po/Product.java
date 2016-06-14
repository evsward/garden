package com.sunshine.gardens.model.po;

import java.util.Date;

public class Product {
	private Integer productId;

	private String productName;

	private Integer productType;// 1：客房 2：温泉会

	private Long columnId;

	private String typeCode;

	private String codeDescription;

	private Long unitPrice;
	private Long goldPrice;
	private Long infinitePrice;
	private Long workdayPrice;
	private Long holidayPrice;
	private Long silverPrice;

	private Integer productState;//1上架 2下架

	private Date createTime;

	private Date updateTime;

	private Date validFrom;

	private Date validTo;

	private String validDay;

	private Integer inventory;

	private Long duration;

	private String notes;
	private String iconsrc;

	public Product() {
		super();
	}

	public Product(Integer productId) {
		super();
		this.productId = productId;
	}

	public String getIconsrc() {
		return iconsrc;
	}

	public void setIconsrc(String iconsrc) {
		this.iconsrc = iconsrc;
	}

	public Integer getProductId() {
		return productId;
	}

	public Long getGoldPrice() {
		return goldPrice;
	}

	public void setGoldPrice(Long goldPrice) {
		this.goldPrice = goldPrice;
	}

	public Long getInfinitePrice() {
		return infinitePrice;
	}

	public void setInfinitePrice(Long infinitePrice) {
		this.infinitePrice = infinitePrice;
	}

	public Long getWorkdayPrice() {
		return workdayPrice;
	}

	public void setWorkdayPrice(Long workdayPrice) {
		this.workdayPrice = workdayPrice;
	}

	public Long getHolidayPrice() {
		return holidayPrice;
	}

	public void setHolidayPrice(Long holidayPrice) {
		this.holidayPrice = holidayPrice;
	}

	public Long getSilverPrice() {
		return silverPrice;
	}

	public void setSilverPrice(Long silverPrice) {
		this.silverPrice = silverPrice;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName == null ? null : productName.trim();
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public Long getColumnId() {
		return columnId;
	}

	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode == null ? null : typeCode.trim();
	}

	public String getCodeDescription() {
		return codeDescription;
	}

	public void setCodeDescription(String codeDescription) {
		this.codeDescription = codeDescription == null ? null : codeDescription.trim();
	}

	public Long getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Long unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getProductState() {
		return productState;
	}

	public void setProductState(Integer productState) {
		this.productState = productState;
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

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	public String getValidDay() {
		return validDay;
	}

	public void setValidDay(String validDay) {
		this.validDay = validDay == null ? null : validDay.trim();
	}

	public Integer getInventory() {
		return inventory;
	}

	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes == null ? null : notes.trim();
	}
}