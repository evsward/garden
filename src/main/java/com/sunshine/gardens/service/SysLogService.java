package com.sunshine.gardens.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dance.core.orm.BaseDao;
import com.dance.core.service.BaseServiceImpl;
import com.sunshine.gardens.dao.SysLogDao;
import com.sunshine.gardens.model.po.SysLog;

@Service
public class SysLogService extends BaseServiceImpl<SysLog, Integer> {

	@Autowired
	private SysLogDao sysLogDao;

	@Override
	public BaseDao<SysLog, Integer> getDao() {
		return sysLogDao;
	}

}
