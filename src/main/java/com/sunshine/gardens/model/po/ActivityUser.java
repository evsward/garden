package com.sunshine.gardens.model.po;

import java.util.Date;

public class ActivityUser {
    private Integer activityUserId;

    private String openid;

    private Integer activityId;

    private Date createTime;

    public Integer getActivityUserId() {
        return activityUserId;
    }

    public void setActivityUserId(Integer activityUserId) {
        this.activityUserId = activityUserId;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}