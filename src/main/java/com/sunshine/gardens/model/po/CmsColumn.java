package com.sunshine.gardens.model.po;

import java.util.Date;

public class CmsColumn {
	private Long columnId;

	private String columnName;

	private Integer columnLevle;

	private Long parentId;

	private Integer columnType;

	private Integer ifHidden;

	private Integer weight;

	private Date createTime;

	private Date updateTime;

	private String userId;

	private String iconUrl;

	public CmsColumn() {
		super();
	}

	public CmsColumn(Long columnId) {
		super();
		this.columnId = columnId;
	}

	public Long getColumnId() {
		return columnId;
	}

	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName == null ? null : columnName.trim();
	}

	public Integer getColumnLevle() {
		return columnLevle;
	}

	public void setColumnLevle(Integer columnLevle) {
		this.columnLevle = columnLevle;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getColumnType() {
		return columnType;
	}

	public void setColumnType(Integer columnType) {
		this.columnType = columnType;
	}

	public Integer getIfHidden() {
		return ifHidden;
	}

	public void setIfHidden(Integer ifHidden) {
		this.ifHidden = ifHidden;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId == null ? null : userId.trim();
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl == null ? null : iconUrl.trim();
	}
}