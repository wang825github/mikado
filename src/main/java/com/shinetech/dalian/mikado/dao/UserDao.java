package com.shinetech.dalian.mikado.dao;

import java.util.List;
import java.util.Map;

import com.shinetech.dalian.mikado.entity.UserEntity;


public interface UserDao {
	/**
	 * Get all user data from DB
	 * @return
	 */
	List<UserEntity> listUserData();
	
	/**
	 * Get a user by user id
	 * @param id
	 * @return
	 */
	UserEntity getUserById(Integer id);
	
	void saveUser(UserEntity user);
	
	void updateUser(UserEntity user);
	
	void removeUser(UserEntity user);
	
	/**
	 * Get users by user account and user name
	 * When return to front end,display users by pagination function
	 * @param account
	 * @param name
	 * @param start
	 * @param maxResult
	 * @return
	 */
	List<UserEntity> listUsersByAccountAndName(String account,String name,Integer start,Integer maxResult);
	
	/**
	 * Get users by user name
	 * When return to front end,display users by pagination function
	 * @param name
	 * @param start
	 * @param maxResult
	 * @return
	 */
	List<UserEntity> listUserByName(String name,Integer start,Integer maxResult);
	
	/**
	 * Get users by user account
	 * When return to front end,display users by pagination function
	 * @param account
	 * @param start
	 * @param maxResult
	 * @return
	 */
	List<UserEntity> listUserByAccount(String account,Integer start,Integer maxResult);
	
	/**
	 * Get all user from DB,there is not search condition
	 * When return to front end,display users by pagination function
	 * @param params
	 * @return
	 */
	List<UserEntity> listAllUsers(Map<String, Object> params);
	
	/**
	 * Get user by user account and user password
	 * @param account
	 * @param password
	 * @return
	 */
	UserEntity getUserByAccountAndPwd(String account,String password);
	
	/**
	 * Get user password by user id
	 * @param id
	 * @return
	 */
	String getUserPasswordById(Integer id);
	
	/**
	 * Check a user's account is unique or not
	 * @param user
	 * @return
	 */
	List<UserEntity> checkAccountUnique(UserEntity user);
	
}
