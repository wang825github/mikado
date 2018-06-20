package com.shinetech.dalian.mikado.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shinetech.dalian.mikado.entity.UserEntity;
import com.shinetech.dalian.mikado.service.LogManageService;

/**
 * 
 * @author abc
 *
 */
@Controller
@RequestMapping(value="/log")
public class LogManageController {
	
	@Autowired
	private LogManageService logService;
	
	/**
	 * Direct to log manage page
	 * @param request
	 * @return return login page if the user is null,else return log manage page
	 */
	@RequestMapping(value="/manage")
	public String logManagePage(HttpServletRequest request){
		UserEntity user = (UserEntity) request.getSession().getAttribute("userEntity");
		if(user == null){
			return "redirect:/";
		}
		return "jsp/log-manage";
	}
	
	/**
	 * Get log data from DB by search conditions
	 * if search conditions are empty, then search all log data
	 * @param request
	 * @return map which contains the searched log data by search conditions and pagination parameters
	 */
	@RequestMapping(value="/getLogData")
	public @ResponseBody Map<String, Object> getLogData(HttpServletRequest request){
		int limit = Integer.parseInt(request.getParameter("limit"));
		int offset = Integer.parseInt(request.getParameter("offset"));
		String account = request.getParameter("account");
		String content = request.getParameter("content");
		String date = request.getParameter("date");
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("start", offset);
		param.put("maxResult", limit);
		param.put("account", account);
		param.put("content", content);
		param.put("date", date);
		
		return logService.getLogData(param);
	}
}
