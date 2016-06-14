package com.sunshine.gardens.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dance.core.orm.BaseDao;
import com.dance.core.service.BaseServiceImpl;
import com.sunshine.gardens.dao.SysRoleDao;
import com.sunshine.gardens.model.po.SysRole;

@Service
public class SysRoleService extends BaseServiceImpl<SysRole, Integer>{
	@Autowired
	private SysRoleDao sysRoleDao;
	
	@Override
	public BaseDao<SysRole, Integer> getDao() {
		return sysRoleDao;
	}

}
