package com.sunshine.gardens.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dance.core.orm.BaseDao;
import com.dance.core.service.BaseServiceImpl;
import com.sunshine.gardens.dao.SysUserDao;
import com.sunshine.gardens.model.po.SysUser;

@Service
public class SysUserService extends BaseServiceImpl<SysUser, Integer>{

	@Autowired
	private SysUserDao sysUserDao;
	
	@Override
	public BaseDao<SysUser, Integer> getDao() {
		return sysUserDao;
	}

}
