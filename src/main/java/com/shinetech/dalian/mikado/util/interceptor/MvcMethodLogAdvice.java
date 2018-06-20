package com.shinetech.dalian.mikado.util.interceptor;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Aspect  
public class MvcMethodLogAdvice {
	private static Logger log = LoggerFactory.getLogger("com.shinetech.dalian.mikado.controller"); 
	
	@Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")  
	  public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {  
	  
	    MethodSignature signature = (MethodSignature) joinPoint.getSignature();  
	    Method method = signature.getMethod();  
	    // join arguments.  
	  System.out.println(method.getDeclaringClass().getName()+"  :"+method.getName() );
	    return joinPoint.proceed();  
	  }  
}
