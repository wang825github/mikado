package com.shinetech.dalian.mikado.util.session;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.shinetech.dalian.mikado.entity.UserEntity;


public  class UserSession {
	public static void  setUserSession(HttpServletRequest request,UserEntity u){
		
		request.getSession().setAttribute("userSession", u);
	}
	public static UserEntity  getUserSession(HttpServletRequest request){
		return (UserEntity) request.getSession().getAttribute("userSession");
	}
	
	public static String  getSessionID(HttpServletRequest request){
		return request.getSession().getId();
	}
	
	public static void cleanAllSession(HttpServletRequest request){
		@SuppressWarnings("rawtypes")
		Enumeration em = request.getSession().getAttributeNames();
		  while(em.hasMoreElements()){
		   request.getSession().removeAttribute(em.nextElement().toString());
		  }
	}
}
