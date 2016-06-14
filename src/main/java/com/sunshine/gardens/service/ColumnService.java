package com.sunshine.gardens.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dance.core.orm.BaseDao;
import com.dance.core.service.BaseServiceImpl;
import com.sunshine.gardens.dao.CmsColumnDao;
import com.sunshine.gardens.model.po.CmsColumn;

@Service
public class ColumnService extends BaseServiceImpl<CmsColumn, Integer>{

	@Autowired
	private CmsColumnDao baseDao;
	
	@Override
	public BaseDao<CmsColumn, Integer> getDao() {
		return baseDao;
	}

}
