package com.shinetech.dalian.mikado.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.shinetech.dalian.mikado.basedao.BaseDao;
import com.shinetech.dalian.mikado.dao.LogDao;
import com.shinetech.dalian.mikado.entity.LogEntity;

/**
 * 
 * @author abc
 *
 */
@Service
@Repository
public class LogDaoImpl implements LogDao {
	@Autowired
	private BaseDao baseDao;
	/**
	 * Get all logs from DB by pagination function
	 */
	@Override
	public List<LogEntity> listLogs(Map<String, Object> params) {
		String hql = " From LogEntity order by date desc";
		if(params == null){
			return baseDao.execute(hql);
		}else{
			return baseDao.executeByLimit(hql, (Integer)params.get("start"), (Integer)params.get("maxResult"));
		}
	}
	/**
	 * Get log list by date by pagination function
	 */
	@Override
	public List<LogEntity> listLogsByDate(String date, Integer start,
			Integer maxResult) {
		String hql = " From LogEntity where date like '" + date + "%' order by date desc";
		if(start == null && maxResult == null){
			return baseDao.execute(hql);
		}else{
			return baseDao.executeByLimit(hql, start, maxResult);
		}
	}
	
	/**
	 * Get log list by content by pagination function
	 */
	@Override
	public List<LogEntity> listLogsByContent(String content, Integer start,
			Integer maxResult) {
		String hql = " From LogEntity where content like '%" + content.trim() + "%' order by date desc";
		if(start == null && maxResult == null){
			return baseDao.execute(hql);
		}else{
			return baseDao.executeByLimit(hql, start, maxResult);
		}
	}
	
	/**
	 * Get log list by content and data by pagination function
	 */
	@Override
	public List<LogEntity> listLogsByContentAndDate(String content,
			String date, Integer start, Integer maxResult) {
		String hql = " From LogEntity where content like '%" + content.trim()
				+ "%' and date like '%" + date + "%' order by date desc";
		if (start == null && maxResult == null) {
			return baseDao.execute(hql);
		} else {
			return baseDao.executeByLimit(hql, start, maxResult);
		}
	}
	
	/**
	 * Get log list by account by pagination function
	 */
	@Override
	public List<LogEntity> listLogsByAccount(String account, Integer start,
			Integer maxResult) {
		String sql = "select * from log where user_account like '%"
				+ account.trim() + "%' order by date desc";
		if (start == null && maxResult == null) {
			return baseDao.executeSQL(sql, LogEntity.class);
		} else {
			return baseDao.executeSQLByLimit(sql, LogEntity.class, start,
					maxResult);
		}
	}
	
	/**
	 * Get log list by account and date by pagination function
	 */
	@Override
	public List<LogEntity> listLogsByAccountAndDate(String account,
			String date, Integer start, Integer maxResult) {
		String sql = "select * from log where date like '" + date
				+ "%' and user_account like '%" + account.trim()
				+ "%' order by date desc";
		if (start == null && maxResult == null) {
			return baseDao.executeSQL(sql, LogEntity.class);
		} else {
			return baseDao.executeSQLByLimit(sql, LogEntity.class, start,
					maxResult);
		}
	}
	
	/**
	 * Get log list by account and content by pagination function
	 */
	@Override
	public List<LogEntity> listLogsByAccountAndContent(String account,
			String content, Integer start, Integer maxResult) {
		String sql = "select * from log where content like '%" + content.trim()
				+ "%' and user_account = '" + account.trim()
				+ "' order by date desc";
		if (start == null && maxResult == null) {
			return baseDao.executeSQL(sql, LogEntity.class);
		} else {
			return baseDao.executeSQLByLimit(sql, LogEntity.class, start,
					maxResult);
		}
	}
	
	/**
	 * Get log list by account and date and content by pagination function
	 */
	@Override
	public List<LogEntity> listLogsBySearches(String account, String content,
			String date, Integer start, Integer maxResult) {
		String sql = "select * from log where content like '%" + content.trim()
				+ "%' and date like '" + date
				+ "%' and user_account like  '%" + account.trim() +"%' order by date desc";
		if (start == null && maxResult == null) {
			return baseDao.executeSQL(sql, LogEntity.class);
		} else {
			return baseDao.executeSQLByLimit(sql, LogEntity.class, start,
					maxResult);
		}
	}
	/**
	 * Insert a log into DB
	 */
	@Override
	public void addLogInfo(LogEntity log) {
		
		baseDao.save(log);
		
	}

}
