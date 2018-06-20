package com.shinetech.dalian.mikado.util.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.shinetech.dalian.mikado.entity.UserEntity;
import com.shinetech.dalian.mikado.util.session.UserSession;

public class LoginInterceptor  implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		UserEntity u =UserSession.getUserSession(request);
		
		if(u == null){
			 if (request.getHeader("x-requested-with") != null  
		                && request.getHeader("x-requested-with").equalsIgnoreCase(
		                        "XMLHttpRequest")) { // ajax 超时处理  
		            response.getWriter().print("timeout");  //设置超时标识
		            response.getWriter().close();
		            return false; 
		        } else {
		        	response.sendRedirect(request.getContextPath() + "/login.jsp");
					return false; 
		        }
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
