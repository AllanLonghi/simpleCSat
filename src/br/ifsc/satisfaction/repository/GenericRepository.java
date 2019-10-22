package br.ifsc.satisfaction.repository;

import java.util.List;

import org.hibernate.Session;

import br.ifsc.satisfaction.entity.FormEntity;

public class GenericRepository implements FormEntity{

	public static void salvar(FormEntity entity) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.save(entity);
		session.getTransaction().commit();
		session.close();
	}
	
	public static <T> List<T> getAll(Class<T> entity){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<T> list = session.createQuery(" from " + entity.getSimpleName(), entity).list();
		return list;
	}
	
	public static <T> List<T> getAll(Class<T> entity, String where){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<T> list = session.createQuery(" from " + entity.getSimpleName() + " where " + where, entity).list();
		return list;
	}
	
	public static void delete(FormEntity entity) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.delete(entity);
		session.getTransaction().commit();
		session.close();
	}
	
}
