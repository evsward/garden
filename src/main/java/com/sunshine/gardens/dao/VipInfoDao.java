package com.sunshine.gardens.dao;

import org.springframework.stereotype.Component;

import com.dance.core.orm.mybatis.MyBatisDaoImpl;
import com.sunshine.gardens.model.po.VipInfo;
@Component
public class VipInfoDao extends MyBatisDaoImpl<VipInfo, Integer>{

}
