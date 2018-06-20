package com.shinetech.dalian.mikado.dao;

import java.util.List;
import java.util.Map;

import com.shinetech.dalian.mikado.entity.CustomerEntity;
import com.shinetech.dalian.mikado.entity.SaleManagerEntity;

public interface CustomerDao {
	
	/**
	 * Get customers by simpleName
	 * When return to front end,display customers by pagination function
	 * @param simple_name
	 * @param start
	 * @param maxResult
	 * @return customer list
	 */
	List<CustomerEntity> listCustomerBySimpleName(String simple_name,Integer start,Integer maxResult);
	
	/**
	 * Get customers by city id
	 * When return to front end,display users by pagination function
	 * @param cityId
	 * @param start
	 * @param maxResult
	 * @return customer list
	 */
	List<CustomerEntity> listCustomerByCityId(Integer cityId,Integer start,Integer maxResult);
	
	/**
	 * Get customers by simpleName and city id
	 * When return to front end,display users by pagination function
	 * @param simple_name
	 * @param cityId
	 * @param start
	 * @param maxResult
	 * @return customer list
	 */
	List<CustomerEntity> listCustomerBySimpleNameAndCityId(String simple_name,Integer cityId,Integer start,Integer maxResult);
	
	/**
	 * Get customers from DB
	 * When return to front end,display users by pagination function
	 * @param param
	 * @return customer list
	 */
	List<CustomerEntity> listCustomers(Map<String, Object> param);
	
	/**
	 * Get a customer by customer id
	 * @param id
	 * @return a customer information
	 */
	CustomerEntity getCustomerById(Integer id);
	
	void saveCustomer(CustomerEntity customer);
	
	void updateCustomer(CustomerEntity customer);

	void removeCustomerById(Integer id);
	
	/**
	 * Get all Customers from DB
	 * @return customer list
	 */
	List<CustomerEntity> listAllCustomer();
	
	/**
	 * Get customers by Sale id
	 * @param saleId
	 * @return customer list
	 */
	List<CustomerEntity> listCustomerBySaleId(Integer saleId);
	
	/**
	 * Get saleManager from customer table by customer id
	 * @param customerId
	 * @return a sale manager information
	 */
	SaleManagerEntity getSaleManagerByCustomerId(Integer customerId);
}
