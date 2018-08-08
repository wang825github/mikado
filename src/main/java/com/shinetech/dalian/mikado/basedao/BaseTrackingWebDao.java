package com.shinetech.dalian.mikado.basedao;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.persister.entity.Loadable;
import org.hibernate.query.Query;
import org.hibernate.sql.Delete;
import org.hibernate.type.IntegerType;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import com.google.gson.Gson;
import com.shinetech.dalian.mikado.config.BsTablePagination;


@Repository
@Transactional
public class BaseTrackingWebDao extends HibernateTrackingDao{

	private static final long serialVersionUID = -6739772796764634585L;
	
	public <E> void saveAll(final List<E> entity){
		
		Session session = super.openSession();
		Transaction tx = session.beginTransaction();  
		for (int i = 0; i < entity.size(); i++) {
			session.saveOrUpdate(entity.get(i));
			if(i %500 == 0 ){
				 session.flush();  
			}
		}
		tx.commit(); 
		session.close();
	}
	
	public <E> void save(final E entity){
		
		Session session = super.openSession();
		Transaction tx = session.beginTransaction();  
		session.saveOrUpdate(entity);
		tx.commit(); 
		session.close();
	}
	
	public <E> void delete(final Class<E> clazz,final Integer id){
		Session session = super.openSession();
		session.delete(session.get(clazz, id));
		session.close();
	}
	
	public <E> void delete(final E entity){
		Session session = super.openSession();
		Transaction tx = session.beginTransaction();  
		session.delete(entity);
		tx.commit(); 
		session.close();
	}
	public  int  createNativeQueryTx(String  sql) {
		Session session = super.openSession();
		Transaction tx = session.beginTransaction();
		int  res = session.createNativeQuery(sql).executeUpdate();
		tx.commit();
		session.close();
		return  res;
	}
	
	public  int createNativeQuery(String  sql) {
		Session session = super.openSession();
		
		return session.createNativeQuery(sql).executeUpdate();
	}
	public <E> int deleteEntity(final Class<E> clazz,final Integer id){
		Session session = super.openSession();
		String hqlString = "DELETE FROM " + clazz.getName() + " o WHERE o.id = " + id;
		return session.createQuery(hqlString).executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public <E> E get(final Class<E> clazz,final Integer id){
		Session session = super.openSession();
		return (E) session.get(clazz, id);
	}
	
	/**
	 *
	 * @param <E>
	 * @param hql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <E> List<E> execute(final String hql) {
		Session session = super.openSession();
		Query query = session.createQuery(hql);
		List<E> Li = query.getResultList();
		session.close();
		return Li;
	}
	
	@SuppressWarnings("unchecked")
	public <E> List<E> executeByLimit(final String hql,final int start,final int maxResult){
		Session session = super.openSession();
		Query query = session.createQuery(hql);
		query.setFirstResult(start);
		query.setMaxResults(maxResult);
		List<E> Li = query.getResultList();
		session.close();
		return  Li;
	}
	
 
	
	@SuppressWarnings("unchecked")
	public <E> List<E> executeSQL(final String hql,final Class<E> clazz) {
		Session session = super.openSession();
		Query query = session.createSQLQuery(hql).addEntity(clazz);
		List<E> list = query.list();
		session.close();
		return list;
	}
	
	/**
	 *
	 * @param <E>
	 * @param hql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <E> E executeGetFirst(final String hql) {
		Session session = super.openSession();
		Query query = session.createQuery(hql);
		E e = (E)query.uniqueResult();
		session.close();
		return e;
	}
	
 
	 
	/**
	 *
	 * @param hql
	 */
	public int executeUpdate(final String hql) {
		Session session = super.openSession();
		Transaction tx = session.beginTransaction();  
		session.createQuery(hql).executeUpdate();
		tx.commit(); 
		session.close();
		return 0;
	}
 
	 
	
 
	 
		
}
