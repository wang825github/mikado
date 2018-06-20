package com.shinetech.dalian.mikado.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shinetech.dalian.mikado.config.LogContent;
import com.shinetech.dalian.mikado.entity.AreaEntity;
import com.shinetech.dalian.mikado.entity.CityEntity;
import com.shinetech.dalian.mikado.entity.CustomerEntity;
import com.shinetech.dalian.mikado.entity.ProvinceEntity;
import com.shinetech.dalian.mikado.entity.SaleManagerEntity;
import com.shinetech.dalian.mikado.entity.UserEntity;
import com.shinetech.dalian.mikado.service.CustomerService;
import com.shinetech.dalian.mikado.service.DictionaryService;
import com.shinetech.dalian.mikado.service.LogManageService;
import com.shinetech.dalian.mikado.util.ResultMessage;

@Controller
@RequestMapping(value="/customer")
public class CustomerManageController {
	
	@Autowired
	private CustomerService customerService;
	@Autowired
	private LogManageService logManageService;
	@Autowired
	private LogContent logContent;
	@Autowired
	private DictionaryService dictionaryService;
	
	/**
	 * Direct to customer page
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/manage")
	public String customerManagePage(HttpServletRequest request){
		UserEntity user = (UserEntity) request.getSession().getAttribute("userEntity");
		if(user == null){
			return "redirect:/";
		}
		
		List<SaleManagerEntity> saleManagerList = dictionaryService.getSaleManagerList();
		List<AreaEntity> areaList = dictionaryService.getAreaList();
		List<CityEntity> cityList = dictionaryService.getCityLists();
		
		request.setAttribute("saleManagerList", saleManagerList);
		request.setAttribute("areaList", areaList);
		request.setAttribute("cityList", cityList);
		return "jsp/customer-manage";
	}
	
	/**
	 * Get customers by search conditions
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getCustomerData")
	public @ResponseBody Map<String, Object> getCustomerData(HttpServletRequest request){
		int limit = Integer.parseInt(request.getParameter("limit"));
		int offset = Integer.parseInt(request.getParameter("offset"));
		String simple_name = request.getParameter("simple_name");
		String cityId = request.getParameter("city.id");
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("start", offset);
		param.put("maxResult", limit);
		param.put("simple_name", simple_name);
		param.put("cityId", cityId);
		
		return customerService.getCustomersByFy(param);
	}
	
	/**
	 * Get province by area id
	 * @param request
	 * @param areaId
	 * @return
	 */
	@RequestMapping(value="/getProvince")
	public @ResponseBody List<ProvinceEntity> getProvince(HttpServletRequest request,Integer areaId){
		List<ProvinceEntity> provinceList = dictionaryService.getProvinceByAreaId(areaId);
		
		return provinceList;
		
	}
	
	@RequestMapping(value="/getCity")
	public @ResponseBody List<CityEntity> getCity(HttpServletRequest request,Integer provinceId){
		List<CityEntity> cityList = dictionaryService.getCityByProvinceId(provinceId);
		
		return cityList;
	}
	
	/**
	 * Get saleManager by area id
	 * @param request
	 * @param areaId
	 * @return
	 */
	@RequestMapping(value="/getSaleManager")
	public @ResponseBody List<SaleManagerEntity> getSaleManager(HttpServletRequest request,Integer areaId){
		List<SaleManagerEntity> saleManagerList = dictionaryService.getSaleManagerByAreaId(areaId);
		return saleManagerList;
	}
	
	/**
	 * @param binder
	 */
	@InitBinder    
	public void initBinder(WebDataBinder binder) {    
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");    
	        dateFormat.setLenient(false);    
	        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));    
	} 
	
	@RequestMapping(value="/add")
	public @ResponseBody CustomerEntity addCustomer(HttpServletRequest request,CustomerEntity customer){
		UserEntity user = (UserEntity) request.getSession().getAttribute("userEntity");
		
		customerService.saveCustomer(customer);
		//Add user log
		logManageService.logForOperation(logContent.getADDCUSTOMER(), logContent.getINFOTYPE(), user);
		return customer;
	}
	
	@RequestMapping(value="/edit")
	public @ResponseBody ResultMessage editCustomer(HttpServletRequest request,CustomerEntity customer){
		UserEntity user = (UserEntity) request.getSession().getAttribute("userEntity");
		
		customerService.updateCustomer(customer);
		//Edit user log
		logManageService.logForOperation(logContent.getEDITCUSTOMER(), logContent.getINFOTYPE(), user);
		
		return new ResultMessage("编辑成功。", true);
	}
	
	@RequestMapping(value="/delete")
	public @ResponseBody ResultMessage deleteCustomer(HttpServletRequest request,@RequestBody List<Integer> lists){
		UserEntity user = (UserEntity) request.getSession().getAttribute("userEntity");
		String result= "删除成功。";
		
		int i = 0;
		for(Integer customerId:lists){
			if(customerService.removeCustomerById(customerId) == false){
				i++;
				//Delete user failed log
				logManageService.logForOperation(logContent.getDELETEUSERFAILED(), logContent.getERRORTYPE(), user);
			}else{
				//Delete user success log
				logManageService.logForOperation(logContent.getDELETECUSTOMER(), logContent.getINFOTYPE(), user);
			}
		}
		if(lists.size() == 1 && i ==1){
			result = "客户信息已关联订单，不能删除！";
		}
		
		if(lists.size() >1 && i >1){
			result = "删除完成。有" + i +"位客户已关联订单，未删除。";
		}
		
		return new ResultMessage(result, true);
	}
}
