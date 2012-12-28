package com.eugene.element;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eugene.util.HibernateUtil;

public class TestBaseDate {
	private final static Logger logger = LoggerFactory.getLogger(TestBaseDate.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String id = "IRKRW_RF";
		Session s = HibernateUtil.currentSession();

		List<BaseDate> bssd = new ArrayList<BaseDate>();

		Query qr = s.createQuery("from BaseDate"
		// "where a.mvDataId =:param "
		// + " and   a.baseDate <= :param3 "

				// + "and a.baseDate =:param2"
				);
		// qr.setParameter("param", id);
		// qr.setParameter("param2", cal);
		// qr.setParameter("param3", cal1);

		bssd = qr.list();

		for (BaseDate aa : bssd) {
			logger.debug("BaseDate:{}", aa.getBssd());
		}
	}
}
