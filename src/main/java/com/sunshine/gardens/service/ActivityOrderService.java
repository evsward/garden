package com.sunshine.gardens.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dance.core.orm.BaseDao;
import com.dance.core.service.BaseServiceImpl;
import com.sunshine.gardens.dao.ActivityOrderDao;
import com.sunshine.gardens.model.po.ActivityOrder;

@Service
public class ActivityOrderService extends BaseServiceImpl<ActivityOrder, Integer> {

	@Autowired
	private ActivityOrderDao baseDao;

	@Override
	public BaseDao<ActivityOrder, Integer> getDao() {
		return baseDao;
	}

}
