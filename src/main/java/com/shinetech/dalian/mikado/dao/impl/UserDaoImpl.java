package com.shinetech.dalian.mikado.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.shinetech.dalian.mikado.basedao.BaseDao;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.shinetech.dalian.mikado.dao.UserDao;
import com.shinetech.dalian.mikado.entity.UserEntity;
/**
 * 
 * @author abc
 *
 */
@Service
@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private BaseDao baseDao;
	
	/**
	 * Get user list from DB
	 */
	@Override
	public List<UserEntity> listUserData() {
		return baseDao.getAll(UserEntity.class);
	}
	
	/**
	 * Insert a user into DB
	 */
	@Override
	public void saveUser(UserEntity user) {
		
		baseDao.save(user);
	}
	
	/**
	 * Get user by id
	 */
	@Override
	public UserEntity getUserById(Integer id) {
		
		return baseDao.get(UserEntity.class, id);
	}
	
	/**
	 * Update a user information
	 */
	@Override
	public void updateUser(UserEntity user) {
		baseDao.save(user);
	}
	
	/**
	 * Remove user from DB
	 */
	@Override
	public void removeUser(UserEntity user) {
		
		baseDao.delete(user);
	}
	
	/**
	 * Get user by account and name
	 * server side pagination function is being used
	 */
	@Override
	public List<UserEntity> listUsersByAccountAndName(String account,
			String name, Integer start, Integer maxResult) {
		String hql = " From UserEntity where account like '%" + account.trim()
				+ "%' and name like '%" + name.trim() + "%' order by id desc";

		if (start == null && maxResult == null) {
			return baseDao.execute(hql);
		} else {
			return baseDao.executeByLimit(hql, start, maxResult);
		}

	}
	
	/**
	 * Get user list by name
	 * server side pagination function is being used
	 */
	@Override
	public List<UserEntity> listUserByName(String name,Integer start,Integer maxResult) {
		String hql = " From UserEntity where name like '%" + name.trim() + "%' order by id desc";
		if(start == null && maxResult == null){
			return baseDao.execute(hql);
		}else{
			return baseDao.executeByLimit(hql, start, maxResult);
		}
	}
	
	/**
	 * Get user list by account
	 * server side pagination function is being used
	 */
	@Override
	public List<UserEntity> listUserByAccount(String account,Integer start,Integer maxResult) {
		String hql = " From UserEntity where account like '%" + account.trim() + "%' order by id desc";
		if(start == null && maxResult == null){
			return baseDao.execute(hql);
		}else{
			return baseDao.executeByLimit(hql, start, maxResult);
		}
	}
	
	/**
	 * Get user list from DB
	 * server side pagination function is being used
	 */
	@Override
	public List<UserEntity> listAllUsers(Map<String, Object> params) {
		String hql = "From UserEntity  order by id desc";
		if(params == null){
			return baseDao.execute(hql);
		}else{
			int start = (int) params.get("start");
			int maxResult = (int) params.get("maxResult");
			return baseDao.executeByLimit(hql, start, maxResult);
		}
	}
	
	/**
	 * Get user by account and password
	 */
	@Override
	public UserEntity getUserByAccountAndPwd(String account, String password) {
		String hql = " From UserEntity where account = '" + account +"' and password = '" + password +"'";
		return baseDao.executeGetFirst(hql);
	}
	
	/**
	 * Get user password by id
	 */
	@Override
	public String getUserPasswordById(Integer id) {
		String hql = " Select password From UserEntity where id = " + id;
		return (String) baseDao.executeGetFirst(hql);
	}
	
	/**
	 * Check if user account is used or not by other user
	 */
	@Override
	public List<UserEntity> checkAccountUnique(UserEntity user) {
		String hql;
		if(user.getId() == null){
			hql = " From UserEntity where account = '" + user.getAccount() +"'";
		}else{
			hql = " From UserEntity where account = '" + user.getAccount() +"' and id <> " + user.getId();
		}
		return baseDao.execute(hql);
	}


}
