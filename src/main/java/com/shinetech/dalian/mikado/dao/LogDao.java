package com.shinetech.dalian.mikado.dao;

import java.util.List;
import java.util.Map;

import com.shinetech.dalian.mikado.entity.LogEntity;

public interface LogDao {
	
	/**
	 * Get all logs from DB
	 * @param params
	 * @return
	 */
	List<LogEntity> listLogs(Map<String, Object> params);
	
	/**
	 * Get logs by date
	 * @param date
	 * @param start
	 * @param maxResult
	 * @return
	 */
	List<LogEntity> listLogsByDate(String date, Integer start, Integer maxResult);
	
	/**
	 * Get logs by Content
	 * @param content
	 * @param start
	 * @param maxResult
	 * @return
	 */
	List<LogEntity> listLogsByContent(String content, Integer start,
			Integer maxResult);
	
	/**
	 * Get logs by content and date
	 * @param content
	 * @param date
	 * @param start
	 * @param maxResult
	 * @return
	 */
	List<LogEntity> listLogsByContentAndDate(String content, String date,
			Integer start, Integer maxResult);
	
	/**
	 * Get logs by user account
	 * @param account
	 * @param start
	 * @param maxResult
	 * @return
	 */
	List<LogEntity> listLogsByAccount(String account, Integer start,
			Integer maxResult);
	
	/**
	 * Get logs by user account and date
	 * @param account
	 * @param date
	 * @param start
	 * @param maxResult
	 * @return
	 */
	List<LogEntity> listLogsByAccountAndDate(String account, String date,
			Integer start, Integer maxResult);
	
	/**
	 * Get logs by user account and content
	 * @param account
	 * @param content
	 * @param start
	 * @param maxResult
	 * @return
	 */
	List<LogEntity> listLogsByAccountAndContent(String account, String content,
			Integer start, Integer maxResult);
	
	/**
	 * get logs by user account,content and date
	 * @param account
	 * @param content
	 * @param date
	 * @param start
	 * @param maxResult
	 * @return
	 */
	List<LogEntity> listLogsBySearches(String account, String content,
			String date, Integer start, Integer maxResult);
	
	/**
	 * Record log into DB after user do some operations in the system
	 * @param log
	 */
	void addLogInfo(LogEntity log);
	
}
