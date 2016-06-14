package com.sunshine.gardens.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dance.core.orm.BaseDao;
import com.dance.core.service.BaseServiceImpl;
import com.sunshine.gardens.dao.ActivityDao;
import com.sunshine.gardens.model.po.Activity;

@Service
public class ActivityService extends BaseServiceImpl<Activity, Integer> {

	@Autowired
	private ActivityDao baseDao;

	@Override
	public BaseDao<Activity, Integer> getDao() {
		return baseDao;
	}

}
