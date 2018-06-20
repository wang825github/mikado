package com.shinetech.dalian.mikado.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shinetech.dalian.mikado.config.LogContent;
import com.shinetech.dalian.mikado.entity.UserEntity;
import com.shinetech.dalian.mikado.service.LogManageService;
import com.shinetech.dalian.mikado.service.UserService;
import com.shinetech.dalian.mikado.util.ResultMessage;


@Controller
@RequestMapping(value="/user")
public class UserManageController {
	@Autowired
	private UserService userService;
	@Autowired
	private LogManageService logService;
	@Autowired
	public LogContent logContent;
	
	/**
	 * Direct to user page
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/manage")
	public String userManagePage(HttpServletRequest request){
		UserEntity user = (UserEntity) request.getSession().getAttribute("userEntity");
		if(user == null){
			return "redirect:/";
		}
		return "jsp/user-manage";
	}
	
	/**
	 * Get all users data From DB
	 * Search user function
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getUserData")
	public @ResponseBody Map<String, Object> getUserData(HttpServletRequest request){
		
		int limit = Integer.parseInt(request.getParameter("limit"));
		int offset = Integer.parseInt(request.getParameter("offset"));
		String account = request.getParameter("account");
		String name = request.getParameter("name");
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("start", offset);
		param.put("maxResult", limit);
		param.put("account", account);
		param.put("name", name);
		
		return userService.selectUsers(param);
		
	}
	
	@RequestMapping(value = "/addUser")
	public @ResponseBody ResultMessage addUser(HttpServletRequest request,
			UserEntity user) {
		String result = "新增成功。";
		
		//check the user account is unique or not
		if(userService.checkAccountUnique(user) == false){
			logService.logForOperation(logContent.getADDUSERFAIL(), logContent
					.getERRORTYPE(),
					(UserEntity) request.getSession().getAttribute("userEntity"));
			result = "账号重复！";
			return new ResultMessage(result, false);
		}
		
		user.setPassword("123456");
		userService.saveUser(user);
		//record add user log into DB
		logService.logForOperation(logContent.getADDUSER(), logContent
				.getINFOTYPE(),
				(UserEntity) request.getSession().getAttribute("userEntity"));
		return new ResultMessage(result, true);
	}
	
	@RequestMapping(value="/editUser")
	public @ResponseBody ResultMessage editUser(HttpServletRequest request,
			UserEntity user) {
		UserEntity userEntity = (UserEntity) request.getSession().getAttribute("userEntity");
		String result = "编辑成功";

		// check the user account is unique or not
		if (userService.checkAccountUnique(user) == false) {
			logService.logForOperation(logContent.getEDITUSERFAIL(), logContent.getERRORTYPE(),userEntity);
			result = "账号重复！";
			return new ResultMessage(result, false);
		}
		
		userService.updateUser(user);
		//record edit user log into DB
		logService.logForOperation(logContent.getEDITUSER(), logContent.getINFOTYPE(),userEntity);
		return new ResultMessage(result, true);
	}
	
	@RequestMapping(value="/removeUser",headers = {"content-type=application/json"})
	public @ResponseBody ResultMessage removeUser(HttpServletRequest request,@RequestBody List<UserEntity> lists){
		UserEntity userEntity = (UserEntity) request.getSession().getAttribute("userEntity");
		String result= "删除成功。";
		
		for(UserEntity entity:lists){
			userService.removeUser(entity);
			//record delete user log into DB
			logService.logForOperation(logContent.getDELETEUSER(), logContent.getINFOTYPE(),userEntity);
		}
		return new ResultMessage(result, true);
	}
	
}
