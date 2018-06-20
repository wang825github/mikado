package com.shinetech.dalian.mikado.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinetech.dalian.mikado.basedao.BaseDao;
import com.shinetech.dalian.mikado.basedao.PublicDao;
import com.shinetech.dalian.mikado.entity.VarietyEntity;
 
@Service
public class PublicService<E> {
	@Autowired
	BaseDao baseDao;
	@SuppressWarnings("rawtypes")
	@Autowired
	private PublicDao publicDao;
	
	/**
	 * check obj  by attribute
	 * @author Justin
	 * @param clazz
	 * @param attribute
	 * @param attributeValue
	 * @param entityId
	 * @return
	 */
	public boolean checkEntitys(Class<E> clazz,String attribute,String attributeValue,Integer entityId) {
		String hql = null;
		if(entityId == null){
			hql = " From "+clazz.getName()+" where "+attribute+" = '" + StringUtils.trim(attributeValue)+"'";
		}else{
			hql = " From "+clazz.getName()+" where "+attribute+" = '" + StringUtils.trim(attributeValue) +"' and id <>" +entityId;
		}
		List<E> EntitysList = baseDao.execute(hql);
		if(EntitysList == null || EntitysList.size() == 0){
			return false;
		}
		return true;
	}
	/**
	 * @author Justin
	 * @param clazz
	 * @param attribute
	 * @param attributeValue
	 * @param entityId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getEntitysByFy(Map<String, Object> param,Class<E> clazz) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<E> rows = new ArrayList<E>();
		rows = publicDao.listEntityByParam(param, clazz);
		List<E> total =publicDao.listEntityByParam(null, clazz);
		result.put("total", total.size());
		result.put("rows", rows);
		return result;
	}
	
	public void saveEntity(final E entity) {
		baseDao.save(entity);
	}
	public void delEntity(final Class<E> clazz,int id) {
		baseDao.delete(clazz, id);
	}
}
