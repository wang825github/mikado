package com.shinetech.dalian.mikado.basedao;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class HibernateDao implements Serializable{

	private static final long serialVersionUID = 6743078344856341964L;
	
	protected final Logger log = Logger.getLogger(getClass());
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public <E> List<E> getAll(final Criteria criteria){
		return criteria.list();
	}
	
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public Session openSession(){
		return sessionFactory.openSession();
	}
	
	public SessionFactory getSessionFactory(){
		return sessionFactory;
	}
}
