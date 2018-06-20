package com.shinetech.dalian.mikado.service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.shinetech.dalian.mikado.dao.UserDao;
import com.shinetech.dalian.mikado.entity.UserEntity;

/**
 * 
 * @author abc
 *
 */
@Service
public class LoginService {
	@Autowired
	private UserDao userDao;
	
	
	private static LoginService loginService;
	
	@PostConstruct
	public void init(){
		loginService = this;
		loginService.userDao = this.userDao;
		
	}
	
	/**
	 * Check user loging successfully or not
	 * @param request
	 * @return
	 */
	public static boolean checkLogin(HttpServletRequest request){
		HttpSession session = request.getSession();
		UserEntity user = (UserEntity) session.getAttribute("userEntity");
		if(user == null){
			if(SecurityContextHolder.getContext().getAuthentication() != null){
				Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if(principal instanceof UserDetails){
					String userAccount = ((UserDetails) principal).getUsername();
					user = loginService.userDao.listUserByAccount(userAccount, null, null).get(0);
					session.setAttribute("userEntity", user);
					return true;
				}
			}
			return false;
		}
		return true;
	}
	
	/**
	 * Reset user password
	 * @param request
	 * @return
	 */
	public String resetPassword(HttpServletRequest request){
		UserEntity user = (UserEntity) request.getSession().getAttribute("userEntity");
		String oldPwd = request.getParameter("oldPwd");
		String newPwd = request.getParameter("newPwd");
		String confirmPwd = request.getParameter("confirmPwd");
		
		if (user == null) {
			return "请先登录再重置密码！";
		}
		
		if(oldPwd == null || newPwd == null || confirmPwd == null){
			return "请先输入密码。";
		}

		if (!oldPwd.trim().equalsIgnoreCase(user.getPassword())) {
			return "原密码输入错误！";
		}

		if (newPwd.trim().isEmpty() || confirmPwd.trim().isEmpty()) {
			return "新密码或确认密码为空！";
		}
		
		
		if(newPwd.trim().length() < 6 || confirmPwd.trim().length() < 6){
			return "请输入大于等于6位新密码！";
		}
		
		if(!newPwd.trim().equalsIgnoreCase(confirmPwd.trim())){
			return "新密码与确认密码不一致！";
		}
		
		user.setPassword(newPwd);
		userDao.updateUser(user);
		return "success";
		
	}
}
