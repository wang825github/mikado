package com.shinetech.dalian.mikado.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shinetech.dalian.mikado.dao.UserDao;
import com.shinetech.dalian.mikado.entity.UserEntity;
import com.shinetech.dalian.mikado.service.UserServiceSecurity;
public class UserServiceSecurityImpl implements UserServiceSecurity{
	@Autowired
	private UserDao userDao ;
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		UserEntity user = userDao.listUserByAccount(username, null, null).get(0);
		if(user==null){
			throw new UsernameNotFoundException("") ;
		}
		List<GrantedAuthority> authsList = new ArrayList<GrantedAuthority>();  
		authsList.add(new SimpleGrantedAuthority("ROLE_USER")); 
		User userdetail = new User(user.getAccount(), user.getPassword(), true, true, true,
				true, authsList);
		return userdetail;
	}

}
