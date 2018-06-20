package com.shinetech.dalian.mikado.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shinetech.dalian.mikado.config.LogContent;
import com.shinetech.dalian.mikado.entity.UserEntity;
import com.shinetech.dalian.mikado.service.LogManageService;
import com.shinetech.dalian.mikado.service.LoginService;
import com.shinetech.dalian.mikado.service.UserService;
import com.shinetech.dalian.mikado.util.ResultMessage;
import com.shinetech.dalian.mikado.util.session.UserSession;

/**
 * 
 * @author abc
 *
 */
@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	@Autowired
	private LogManageService logService;
	@Autowired
	private LogContent logContent;
	@Autowired
	private LoginService loginService;
	
	/**
	 * Direct to login page
	 * @param request
	 * @param model
	 * @return login modelAndView
	 */
	@RequestMapping(value="/")
	public ModelAndView loginPage(HttpServletRequest request,ModelAndView model){
		return new ModelAndView("/login");
	}
	
	/**
	 * Login to system by user account and password
	 * @param request
	 * @param session
	 * @return direct to data-manage page if the user account and password is correct,else redirect to login page
	 */
	@RequestMapping(value="/login" ,produces="application/json; charset=UTF-8",method ={RequestMethod.POST,RequestMethod.GET})
	public String  loginByAccountAndPassword(
			HttpServletRequest request,HttpSession session){
		String account= request.getParameter("account");
		String password = request.getParameter("password");
		UserEntity user = userService.getUserByAccountAndPwd(account, password);
		
		if(user != null){
			request.getSession().setAttribute("userEntity", user);
			UserSession.setUserSession(request, user);
			logService.logForOperation(logContent.getLOGINSUCCESS(), logContent.getINFOTYPE(), user);
			return "redirect:/data/manage";
		}else{
			request.setAttribute("loginError", "用户名或密码错误！");
			return "/login";
		}
	}
	
	/**
	 * Log out from system
	 * @param request
	 * @return login page
	 */
	@RequestMapping(value="logout")
	public String logout(HttpServletRequest request){
		UserEntity user = (UserEntity) request.getSession().getAttribute("userEntity");
		request.getSession().removeAttribute("userEntity");
		logService.logForOperation(logContent.getLOGOUT(), logContent.getINFOTYPE(), user);
		return "redirect:/";
	}
	/**
	 * Reset user password,if the new password is qualified,return success,else return failed message
	 * @param request
	 * @return result message if the password is reset successfully
	 */
	@RequestMapping(value="/resetPassword")
	public @ResponseBody ResultMessage resetPassword(HttpServletRequest request){
		UserEntity user = (UserEntity) request.getSession().getAttribute("userEntity");
		String result = loginService.resetPassword(request);;
		
		if("success".equalsIgnoreCase(result)){
			logService.logForOperation(logContent.getRESETPWDSUCCESS(), logContent.getINFOTYPE(), user);
			return new ResultMessage("密码重置成功。", true);
		}else{
			logService.logForOperation(logContent.getRESETPWDFAILED(), logContent.getERRORTYPE(), user);
			return new ResultMessage(result, false);
		}
	}
}
