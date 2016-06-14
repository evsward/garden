package com.sunshine.gardens.dao;

import org.springframework.stereotype.Component;

import com.dance.core.orm.mybatis.MyBatisDaoImpl;
import com.sunshine.gardens.model.po.Product;

@Component
public class ProductDao extends MyBatisDaoImpl<Product, Integer> {

}
