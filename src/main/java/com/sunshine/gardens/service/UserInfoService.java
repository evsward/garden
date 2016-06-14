package com.sunshine.gardens.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dance.core.orm.BaseDao;
import com.dance.core.service.BaseServiceImpl;
import com.sunshine.gardens.dao.UserInfoDao;
import com.sunshine.gardens.model.po.UserInfo;

@Service
public class UserInfoService extends BaseServiceImpl<UserInfo, Integer> {

	@Autowired
	private UserInfoDao userInfoDao;

	@Override
	public BaseDao<UserInfo, Integer> getDao() {
		return userInfoDao;
	}
}
