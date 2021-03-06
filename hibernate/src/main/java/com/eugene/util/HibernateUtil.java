package com.eugene.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;



public class HibernateUtil {
	private static SessionFactory sessionFactory;
	static {
		try {
			sessionFactory =new Configuration().configure().buildSessionFactory();
			
		}catch(Throwable ex) {
			System.out.println(ex.getMessage()); 
		}
	}
	public static final ThreadLocal local = new ThreadLocal();
	
	public static Session currentSession() throws HibernateException{
		Session s = (Session)local.get();
		//Session s;
		if(s==null) {
			s = sessionFactory.openSession();
			local.set(s);
		}
		return s;
	}
	
	public static void closeSession() throws HibernateException{
		Session s =(Session)local.get();
		local.set(null);
		if(s!=null) {
			s.close();
		}
	}

	public static int getFlushSize(){
		int flushSize =1000;		//default flush size
//		Property prop =Property.getProperity("FlushSize");
//		if(prop!=null){
//			flushSize = prop.getIntValue();
//		}
		return flushSize;
	}
}
