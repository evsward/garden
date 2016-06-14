package com.sunshine.gardens.dao;

import org.springframework.stereotype.Component;

import com.dance.core.orm.mybatis.MyBatisDaoImpl;
import com.sunshine.gardens.model.po.ReserveOrder;

@Component
public class ReserveOrderDao extends MyBatisDaoImpl<ReserveOrder, Integer>{

}
