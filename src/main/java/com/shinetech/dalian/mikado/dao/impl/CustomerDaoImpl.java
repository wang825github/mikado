package com.shinetech.dalian.mikado.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.shinetech.dalian.mikado.basedao.BaseDao;
import com.shinetech.dalian.mikado.dao.CustomerDao;
import com.shinetech.dalian.mikado.entity.CustomerEntity;
import com.shinetech.dalian.mikado.entity.SaleManagerEntity;

/**
 * 
 * @author abc
 *
 */
@Service
@Repository
public class CustomerDaoImpl implements CustomerDao{
	
	@Autowired
	private BaseDao baseDao;
	
	/**
	 * Get customer list by simple name
	 */
	@Override
	public List<CustomerEntity> listCustomerBySimpleName(String simple_name,
			Integer start, Integer maxResult) {
		String hql = " From CustomerEntity where simple_name like '%"
				+ simple_name.trim() + "%' order by id desc";
		if (start == null && maxResult == null) {
			return baseDao.execute(hql);
		} else {
			return baseDao.executeByLimit(hql, start, maxResult);
		}
	}
	
	/**
	 * Get customer list by city id
	 */
	@Override
	public List<CustomerEntity> listCustomerByCityId(Integer cityId, Integer start,
			Integer maxResult) {
		String hql = " From CustomerEntity where city_id = " + cityId + " order by id desc";
		if(start == null && maxResult == null){
			return baseDao.execute(hql);
		}else{
			return baseDao.executeByLimit(hql, start, maxResult);
		}
	}
	
	/**
	 * Get customer list by simple name and city id
	 */
	@Override
	public List<CustomerEntity> listCustomerBySimpleNameAndCityId(
			String simple_name, Integer cityId, Integer start, Integer maxResult) {
		String hql = " From CustomerEntity where simple_name like '%"
				+ simple_name.trim() + "%' and city_id =" + cityId
				+ " order by id desc";
		if (start == null && maxResult == null) {
			return baseDao.execute(hql);
		} else {
			return baseDao.executeByLimit(hql, start, maxResult);
		}
	}
	
	/**
	 * Get all customers from DB
	 */
	@Override
	public List<CustomerEntity> listCustomers(Map<String, Object> param) {
		String hql = " From CustomerEntity order by id desc";
		if(param == null){
			return baseDao.execute(hql);
		}else{
			return baseDao.executeByLimit(hql, (int)param.get("start"), (int)param.get("maxResult"));
		}
	}
	
	/**
	 * Insert a customer into DB
	 */
	public void saveCustomer(CustomerEntity customer) {
		
		baseDao.save(customer);
		
	}
	
	/**
	 * Update a customer information
	 */
	@Override
	public void updateCustomer(CustomerEntity customer) {
		
		baseDao.save(customer);
	}
	
	/**
	 * Delete a customer information from DB
	 */
	@Override
	public void removeCustomerById(Integer id) {
		baseDao.delete(CustomerEntity.class, id);
	}
	
	/**
	 * Get customer information by id
	 */
	@Override
	public CustomerEntity getCustomerById(Integer id) {
		
		return baseDao.get(CustomerEntity.class, id);
	}
	
	/**
	 * Get all customers from DB
	 */
	@Override
	public List<CustomerEntity> listAllCustomer() {
		
		return baseDao.getAll(CustomerEntity.class);
	}
	
	/**
	 * Get customer list by sale id
	 */
	@Override
	public List<CustomerEntity> listCustomerBySaleId(Integer saleId) {
		String hql = " From CustomerEntity where sale_manager_id = " + saleId;
		return baseDao.execute(hql);
	}
	
	/**
	 * Get sale manager list from customer table by customer id
	 */
	@Override
	public SaleManagerEntity getSaleManagerByCustomerId(Integer customerId) {
		CustomerEntity customer = baseDao.get(CustomerEntity.class, customerId);
		return customer.getSaleManager();
	}

}
