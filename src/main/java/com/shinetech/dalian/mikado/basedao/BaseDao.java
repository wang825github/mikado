package com.shinetech.dalian.mikado.basedao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.ehcache.CacheOperationOutcomes.GetAllOutcome;

import org.apache.poi.ss.formula.functions.T;
import org.hibernate.SQLQuery;
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
public class BaseDao extends HibernateDao{

	private static final long serialVersionUID = -6739772796764634585L;
	
	public <E> void save(final E entity){
		Session session = super.getSession();
		session.saveOrUpdate(entity);
		session.flush();
	}
	
	public <E> void save(final List<E> entityList){
		Session session = super.getSession();
		for(int i=0;i<entityList.size();i++){
			session.saveOrUpdate(entityList.get(i));
			if(i %50 == 0){
				session.flush();  
	            session.clear(); 
			}
		}
		
	}
	
//	Session session = sessionFactory.openSession();
//	Transaction tx = session.beginTransaction();
//	for ( int i=0; i<100000; i++ ) {
//		Customer customer = new Customer(.....);
//		session.save(customer);
//		if ( i % 20 == 0 ) { 
//			//20, same as the JDBC batch size //20,与JDBC批量设置相同        
//			//flush a batch of inserts and release memory:          
//			//将本批插入的对象立即写入数据库并释放内存       
//			session.flush();          
//			session.clear();      
//			} 
//		}       
//	tx.commit(); 
//	session.close();
//		}
//	}
	
	public <E> void delete(final Class<E> clazz,final Integer id){
		Session session = super.getSession();
		session.delete(session.get(clazz, id));
	}
	
	public <E> void delete(final E entity){
		Session session = super.getSession();
		session.delete(entity);
	}
	
	public <E> int deleteEntity(final Class<E> clazz,final Integer id){
		Session session = super.getSession();
		String hqlString = "DELETE FROM " + clazz.getName() + " o WHERE o.id = " + id;
		return session.createQuery(hqlString).executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public <E> E get(final Class<E> clazz,final Integer id){
		Session session = super.getSession();
		return (E) session.get(clazz, id);
	}
	
	@SuppressWarnings("unchecked")
	public <E> E load(final Class<E> clazz,final Integer id){
		Session session = super.getSession();
		return (E) session.load(clazz, id);
	}
	
	@SuppressWarnings("unchecked")
	public <E> List<E> getAll(final Class<E> clazz){
		Session session = super.getSession();
		String hql = "SELECT o FROM " + clazz.getName() + " o";
		Query query = session.createQuery(hql);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public <E> List<E> getAllByIds(Class<T> clazz,List<Integer> ids){
		Session session = super.getSession();
		String hql = "SELECT o FROM " + clazz.getName() + " o WHERE o.id in (:ids)";
		Query query = session.createQuery(hql);
		query.setParameterList("ids", ids);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public <E> List<E> getAll(final Class<E> clazz, String orderColumn, String order) {
		Session session = super.getSession();
		String hql = "SELECT o FROM " + clazz.getName() + " o ORDER BY o." + orderColumn + " " + order;
		Query query = session.createQuery(hql);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public <E> List<E> getAllByStringField(final Class<E> clazz, String column, String value) {
		Session session = super.getSession();
		
		String hql = "SELECT o FROM " + clazz.getName() + " o where o."+column+ "='"+value +"'" ;
		Query query = session.createQuery(hql);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public <E> List<E> getAllByObject(final Class<E> clazz, String column, Object value) {
		Session session = super.getSession();
		String hql = "SELECT o FROM " + clazz.getName() + " o where o."+column+ "="+value  ;
		Query query = session.createQuery(hql);
		return query.list();
	}
	
	/**
	 * 分页功能获取 page获取 数据列表
	 * @param clazz
	 * @param bsTablePage
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <E> List<E> getAllByPage(final Class<E> clazz, BsTablePagination bsTablePage) {
		Session session = super.getSession();
		String hql = "FROM " + clazz.getName()  ;
		Query query = session.createQuery(hql);
		query.setFirstResult(bsTablePage.getOffset()).setMaxResults(bsTablePage.getLimit());
		return query.list();
	}
	/**
	 *
	 * @param <E>
	 * @param hql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <E> List<E> execute(final String hql) {
		Session session = super.getSession();
		Query query = session.createQuery(hql);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public <E> List<E> executeByLimit(final String hql,final int start,final int maxResult){
		Session session = super.getSession();
		Query query = session.createQuery(hql);
		query.setFirstResult(start);
		query.setMaxResults(maxResult);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public <E> List<E> executeSQLByLimit(final String hql,final Class<E> clazz,int start,int maxResult) {
		Session session = super.getSession();
		Query query = session.createSQLQuery(hql).addEntity(clazz);
		query.setFirstResult(start);
		query.setMaxResults(maxResult);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public <E> List<E> executeSQL(final String hql,final Class<E> clazz) {
		Session session = super.getSession();
		Query query = session.createSQLQuery(hql).addEntity(clazz);
		return query.list();
	}
	
	/**
	 *
	 * @param <E>
	 * @param hql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <E> E executeGetFirst(final String hql) {
		Session session = super.getSession();
		Query query = session.createQuery(hql);
		return (E)query.uniqueResult();
	}
	
	
	/**
	 * 获取表中记录数
	 * @param clazz
	 * @return
	 */
	public <E> int getCount( final Class<E> clazz) {
		Session session = super.getSession();
		String hql = "SELECT  count(*) FROM  " +clazz.getName() ;
		Query query = session.createQuery(hql);
		return ((Number)query.uniqueResult()).intValue(); 
	}
	
	/**
	 * 
	 * 根据条件获取表记录
	 * @param clazz
	 * @param varables
	 * @return
	 */
	public <E> int getCountByCondition( final Class<E> clazz,Map<String,Object> varables) {
		Query query = selectStatement(clazz, varables);
		return ((Number)query.uniqueResult()).intValue(); 
	}
	  
 
    /**
     * 鎷兼帴SQL鏌ヨ瀛楃涓�,寰楀埌Query骞惰祴鍊兼煡璇㈡潯浠�
     * @param className
     * @param varables
     * @param session
     * @return Query
     */
    @SuppressWarnings("unused")
	private <T> Query selectStatement(Class<T> className, Map<String,Object> varables) {
    	Session session = super.getSession();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT  count(*)  FROM  " + className.getName());
        stringBuilder.append(" WHERE  1=1 ");
        /*
         * 鍔ㄦ�佺殑鎷兼帴sql璇彞,濡傛灉涓�涓睘鎬х殑鍊间负"", 鍒欎笉寰�鏉′欢涓坊鍔�.
         */
        for(Entry<String, Object> entry : varables.entrySet()){
            if(!ObjectUtils.isEmpty(entry.getValue())){
                stringBuilder.append(" and " + entry.getKey()+"=:" + entry.getKey());
            }
        }
        Query query = session.createQuery(stringBuilder.toString());
        /*
         * 鍔ㄦ�佺殑缁欐潯浠惰祴鍊�
         */
        for(Entry<String, Object> entry1 : varables.entrySet()){
        	 if(!ObjectUtils.isEmpty(entry1.getValue())){
                query.setParameter(entry1.getKey(), entry1.getValue());
            }
        }
        return query;
    }
	/**
	 *
	 * @param hql
	 */
	public int executeUpdate(final String hql) {
		Session session = super.getSession();
		return session.createQuery(hql).executeUpdate();
	}
	
	@SuppressWarnings("deprecation")
	public int executeSqlUpdate(final String hql) {
		Session session = super.getSession();
		return session.createSQLQuery(hql).executeUpdate();
	}
	
	public <E> int executeUpdateByProperty(final Class<E> clazz, String property,String value) {
		Session session = super.getSession();
		String  hql = "delete "+clazz.getName()+" where "+property+" = :property";
		  int deleteEntities = session.createQuery(hql)
		      .setString("property",value)
		      .executeUpdate();

		  return deleteEntities;
	}

	
	/** 检查是否重复
	 */
	public <E> boolean checkDuplicate(final Class<E> clazz, String orderColumn, Object value){
		Session session = super.getSession();
		String hql = "SELECT o FROM " + clazz.getName() + " o where o." + orderColumn + " = ";
		if(value instanceof String){
			hql += ("'"+value+"'");
		}else{
			hql += value;
		}
		Query query = session.createQuery(hql);
		return query.list().size()>0;
	}
	
	@SuppressWarnings("unchecked")
	public Integer getMaxSeqByTableName(String tableName){
		List<Integer> list = new ArrayList<Integer>();
		
		String sql = "select max(seq) from "+tableName;
		
		SQLQuery query = super.getSession().createSQLQuery(sql);
		
		query.addScalar("max(seq)", IntegerType.INSTANCE);
		list = (List<Integer>) query.list();
		if(list.get(0) == null){
			return 0;
		}
		return list.get(0);
	}
	
	public List<String> getFieldByTableName(String tableName,String field){
		List<String> list = new ArrayList<String>();
		String sql = "select distinct " + field + " from " + tableName;
		Query query = super.getSession().createNativeQuery(sql);
		list = query.list();
		return list;
	}
	
		
}
