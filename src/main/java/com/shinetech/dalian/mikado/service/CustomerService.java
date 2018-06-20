package com.shinetech.dalian.mikado.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinetech.dalian.mikado.dao.CustomerDao;
import com.shinetech.dalian.mikado.dao.DataManageDao;
import com.shinetech.dalian.mikado.dao.DictionaryDao;
import com.shinetech.dalian.mikado.entity.CustomerEntity;
import com.shinetech.dalian.mikado.entity.SaleManagerEntity;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private DataManageDao dataManageDao;
	@Autowired
	private DictionaryDao dictionaryDao;
	
	/**
	 * Get customers by search conditions
	 * When search conditions are empty, get all customers
	 * @param param
	 * @return
	 */
	public Map<String, Object> getCustomersByFy(Map<String, Object> param){
		Map<String, Object> result = new HashMap<String, Object>();
		List<CustomerEntity> rows = new ArrayList<CustomerEntity>();
		String simple_name = (String) param.get("simple_name");
		String cityId = (String) param.get("cityId");
		Integer start = (Integer) param.get("start");
		Integer maxResult = (Integer) param.get("maxResult");
		
		int total = 0;
		
		boolean simple_nameFlag = true,cityIdFlag = true;
		if(simple_name == null || "".equals(simple_name.trim())){
			simple_nameFlag = false;
		}
		if(cityId == null || "0".equals(cityId)){
			cityIdFlag = false;
		}
		
		/*
		 * Get customers by city id
		 */
		if (simple_nameFlag == false && cityIdFlag == true) {
			total = customerDao.listCustomerByCityId(Integer.parseInt(cityId), null, null).size();
			rows = customerDao.listCustomerByCityId(Integer.parseInt(cityId), start, maxResult);
		}

		/*
		 * Get customers by simple name
		 */
		if (simple_nameFlag == true && cityIdFlag == false) {
			total = customerDao.listCustomerBySimpleName(simple_name, null, null).size();
			rows = customerDao.listCustomerBySimpleName(simple_name, start, maxResult);
		}

		/*
		 * Get customers by simple name and city id
		 */
		if (simple_nameFlag == true && cityIdFlag == true) {
			total = customerDao.listCustomerBySimpleNameAndCityId(simple_name, Integer.parseInt(cityId), null, null).size();
			rows = customerDao.listCustomerBySimpleNameAndCityId(simple_name, Integer.parseInt(cityId), start, maxResult);
		}

		/*
		 * Search conditions are empty, get all Customer
		 */
		if (simple_nameFlag == false && cityIdFlag == false) {
			total = customerDao.listCustomers(null).size();
			rows = customerDao.listCustomers(param);
		}
		
		result.put("total", total);
		result.put("rows", rows);
		
		return result;
	}
	
	public void saveCustomer(CustomerEntity customer){
		customerDao.saveCustomer(customer);
	}
	
	public void updateCustomer(CustomerEntity customer){
		customerDao.updateCustomer(customer);
	}

	public boolean removeCustomerById(Integer id) {
		//If customer is associated in table dataManage,then it can not be deleted,and return false,else return true 
		if(dataManageDao.checkCustomerInDataManage(id) == true){
			return false;
		}
		customerDao.removeCustomerById(id);
		return true;
	}
	
	/**
	 * Get all customers from DB
	 * @return
	 */
	public List<CustomerEntity> getCustomerList(){
		return customerDao.listAllCustomer();
	}

	public SaleManagerEntity getSaleManagerByCustomerId(Integer customerId) {
		
		return customerDao.getSaleManagerByCustomerId(customerId);
	}
}
