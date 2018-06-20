package com.shinetech.dalian.mikado.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.shinetech.dalian.mikado.dao.UserDao;
import com.shinetech.dalian.mikado.entity.UserEntity;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
	/**
	 * Get all Users from DB
	 * @return
	 */
	public List<UserEntity> getUserData(){
		
		return userDao.listUserData();
	}
	
	/**
	 * Get users from DB by search conditions
	 * If the search conditions are empty, then get all users
	 * @param param
	 * @return
	 */
	public Map<String, Object> selectUsers(Map<String, Object> param){
		Map<String, Object> result = new HashMap<String, Object>();
		List<UserEntity> rows = new ArrayList<UserEntity>();
		String account = (String) param.get("account");
		String name = (String) param.get("name");
		Integer start = (Integer) param.get("start");
		Integer maxResult = (Integer) param.get("maxResult");
		
		int total = 0;
		
		/*
		 * Get users by user name
		 */
		if((account == null || "".equals(account.trim()))&&(!(name == null || "".equals(name.trim())))){
			total = userDao.listUserByName(name, null, null).size();
			rows = userDao.listUserByName(name, start, maxResult);
		}
		
		/*
		 * Get users by user account
		 */
		if((!(account == null || "".equals(account.trim())))&&(name == null || "".equals(name.trim()))){
			total = userDao.listUserByAccount(account,null,null).size();
			rows = userDao.listUserByAccount(account, start, maxResult);
		}
		
		/*
		 * Get users by user account and user name
		 */
		if((!(account == null || "".equals(account.trim())))&&(!(name == null || "".equals(name)))){
			total = userDao.listUsersByAccountAndName(account, name,null,null).size();
			rows = userDao.listUsersByAccountAndName(account, name, start, maxResult);
		}
		
		/*
		 * Search conditions are empty, get all users
		 */
		if((account == null || "".equals(account.trim())&&(name == null || "".equals(name.trim())))){
			total = userDao.listAllUsers(null).size();
			rows = userDao.listAllUsers(param);
		}
		
		result.put("total", total);
		result.put("rows", rows);
		return result;
	}
	
	public void saveUser(UserEntity user){
		userDao.saveUser(user);
	}
	
	/**
	 * Check one user account is unique or not in DB
	 * If account is unique,return true, 
	 * If account is not unique,return false
	 * @param user
	 * @return
	 */
	public boolean checkAccountUnique(UserEntity user){
		List<UserEntity> userList = userDao.checkAccountUnique(user);
		if(userList == null || userList.size() == 0){
			return true;
		}else{
			return false;
		}
	}
	
	public UserEntity getUserById(Integer id){
		return userDao.getUserById(id);
	}
	
	public void updateUser(UserEntity user){
		//获取用户密码
		user.setPassword(userDao.getUserPasswordById(user.getId()));
		userDao.updateUser(user);
	}
	
	public void removeUser(UserEntity user){
		userDao.removeUser(user);
	}
	
	/**
	 * Get users by user account and user password
	 * @param account
	 * @param password
	 * @return
	 */
	@Cacheable(value="userEntity")
	public UserEntity getUserByAccountAndPwd(String account,String password){
		return userDao.getUserByAccountAndPwd(account, password);
	}
	
}
