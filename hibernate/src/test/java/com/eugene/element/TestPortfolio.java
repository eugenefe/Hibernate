package com.eugene.element;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eugene.util.HibernateUtil;

public class TestPortfolio {
	private final static Logger logger = LoggerFactory.getLogger(TestPortfolio.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String id = "IRKRW_RF";
		Session s = HibernateUtil.currentSession();

		List<Portfolio> port = new ArrayList<Portfolio>();

//		Query qr = s.createQuery("from Portfolio a  where a.poftfolio.id = 'PORT_ACC_ROOT'"
				Query qr = s.createQuery("from Portfolio a  where a.portfolio.id = 'PORT_ACC_ROOT' "
		// "where a.mvDataId =:param "
		// + " and   a.baseDate <= :param3 "

				// + "and a.baseDate =:param2"
				);
		// qr.setParameter("param", id);
		// qr.setParameter("param2", cal);
		// qr.setParameter("param3", cal1);

		port = qr.list();
		
		int cnt =0;
		logger.debug("{}", port.size());
		for (Portfolio aa : port) {
			logger.debug("Position:{},{}", aa.getId(), aa.getPositionHises().size());
			logger.debug("Position:{},{}", aa.getId(), aa.getPortfolio().getId());
			logger.debug("Position:{},{}", aa.getId(), cnt++);
		}
	}
}
