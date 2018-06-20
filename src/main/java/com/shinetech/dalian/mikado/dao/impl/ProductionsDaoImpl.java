package com.shinetech.dalian.mikado.dao.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.shinetech.dalian.mikado.basedao.BaseDao;
import com.shinetech.dalian.mikado.dao.ProductionsDao;
import com.shinetech.dalian.mikado.entity.ProductionsEntity;

@Service
@Repository
@Transactional
public class ProductionsDaoImpl implements ProductionsDao{
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public ProductionsEntity getProductionsByIDCode(String IDCode) {
	String hql ="FROM ProductionsEntity PE WHERE PE.packaging.IDCode = '"+IDCode+"'"  ;
		return baseDao.executeGetFirst(hql);
	}
 
}
