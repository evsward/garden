package com.sunshine.gardens.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dance.core.orm.BaseDao;
import com.dance.core.service.BaseServiceImpl;
import com.sunshine.gardens.dao.SysUserRoleDao;
import com.sunshine.gardens.model.po.SysUserRole;

@Service
public class SysUserRoleService extends BaseServiceImpl<SysUserRole, Integer> {

	@Autowired
	private SysUserRoleDao sysUserRoleDao;

	@Override
	public BaseDao<SysUserRole, Integer> getDao() {
		return sysUserRoleDao;
	}

}
