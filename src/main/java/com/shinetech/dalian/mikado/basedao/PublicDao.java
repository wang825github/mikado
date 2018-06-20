package com.shinetech.dalian.mikado.basedao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PublicDao<E>{
	@Autowired
	BaseDao baseDao;
	public   List<E>  listEntityByParam(Map<String, Object> param,Class<E> clazz) {
	String hql = " From  "+clazz.getName()+ " order by id desc";
	if(StringUtils.equals(clazz.getName(), "com.shinetech.dalian.mikado.entity.CityEntity")){
		hql = " From  "+clazz.getName()+ " order by id asc";
	}
		if(param == null){
			return baseDao.execute(hql);
		}else{
			return baseDao.executeByLimit(hql, (int)param.get("start"), (int)param.get("maxResult"));
		}
	}

}
