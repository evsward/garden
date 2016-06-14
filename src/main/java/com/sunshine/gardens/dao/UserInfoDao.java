package com.sunshine.gardens.dao;

import org.springframework.stereotype.Component;

import com.dance.core.orm.mybatis.MyBatisDaoImpl;
import com.sunshine.gardens.model.po.UserInfo;

@Component
public class UserInfoDao extends MyBatisDaoImpl<UserInfo, Integer> {

}
