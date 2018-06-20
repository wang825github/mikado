package com.shinetech.dalian.mikado.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinetech.dalian.mikado.dao.LogDao;
import com.shinetech.dalian.mikado.entity.LogEntity;
import com.shinetech.dalian.mikado.entity.UserEntity;

@Service
public class LogManageService {
	
	@Autowired
	private LogDao logDao;
	
	/**
	 * Get logs by search conditions
	 * If search conditions are empty,then get all logs
	 * @param params
	 * @return
	 */
	public Map<String, Object> getLogData(Map<String, Object> params){
		Map<String, Object> result = new HashMap<String, Object>();
		List<LogEntity> rows = new ArrayList<LogEntity>();
		String account = (String) params.get("account");
		String content = (String) params.get("content");
		String date = (String) params.get("date");
		Integer start = (Integer) params.get("start");
		Integer maxResult = (Integer) params.get("maxResult");
		int total = 0;
		
		Boolean accountFlag = true,contentFlag = true,dateFlag = true;
		if(account == null || "".equals(account.trim())){
			accountFlag = false;
		}
		if(content == null || "".equals(content.trim())){
			contentFlag = false;
		}
		if(date == null || "".equals(date.trim())){
			dateFlag = false;
		}
		
		/*
		 * Get all logs from DB
		 */
		if( accountFlag == false && contentFlag == false && dateFlag == false){
			total = logDao.listLogs(null).size();
			rows = logDao.listLogs(params);
		}
		/*
		 * Get logs by date
		 */
		if(accountFlag == false && contentFlag == false && dateFlag == true){
			total = logDao.listLogsByDate(date,null,null).size();
			rows = logDao.listLogsByDate(date,start,maxResult);
		}
		/*
		 * Get logs by log content
		 */
		if(accountFlag == false && contentFlag == true && dateFlag == false){
			total = logDao.listLogsByContent(content,null,null).size();
			rows = logDao.listLogsByContent(content,start,maxResult);
		}
		/*
		 * Get logs by log content and date
		 */
		if(accountFlag == false && contentFlag == true && dateFlag == true){
			total = logDao.listLogsByContentAndDate(content,date,null,null).size();
			rows = logDao.listLogsByContentAndDate(content,date,start,maxResult);
		}
		/*
		 * Get logs by user account
		 */
		if(accountFlag == true && contentFlag == false && dateFlag == false){
			total = logDao.listLogsByAccount(account, null, null).size();
			rows = logDao.listLogsByAccount(account,start,maxResult);
		}
		/*
		 * Get logs by user account and date
		 */
		if(accountFlag == true && contentFlag == false && dateFlag == true){
			total = logDao.listLogsByAccountAndDate(account, date, null, null).size();
			rows = logDao.listLogsByAccountAndDate(account,date,start,maxResult);
		}
		/*
		 * Get logs by user account and content
		 */
		if(accountFlag == true && contentFlag == true && dateFlag == false){
			total = logDao.listLogsByAccountAndContent(account, content, start, maxResult).size();
			rows = logDao.listLogsByAccountAndContent(account,content,start,maxResult);
		}
		/*
		 * Get logs by user account,content and date
		 */
		if(accountFlag == true && contentFlag == true && dateFlag == true){
			total = logDao.listLogsBySearches(account, content, date, null, null).size();
			rows = logDao.listLogsBySearches(account,content,date,start,maxResult);
		}
		
		result.put("total", total);
		result.put("rows", rows);
		
		return result;
	}
	
	/**
	 * After a user do some operation in the system,will record the operation log into log table
	 * @param content
	 * @param type
	 * @param user
	 */
	public void logForOperation(String content,String type,UserEntity user){
		LogEntity log = new LogEntity();
		log.setContent(content);
		log.setType(type);
		log.setDate(new Date());
		log.setUserId(user.getId());
		log.setUserName(user.getName());
		log.setUserAccount(user.getAccount());
		logDao.addLogInfo(log);
	}
	
}
