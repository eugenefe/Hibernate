package com.eugene.element;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eugene.util.HibernateUtil;

public class TestPosition {
	private final static Logger logger = LoggerFactory.getLogger(TestPosition.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String id = "IRKRW_RF";
		Session s = HibernateUtil.currentSession();

		List<PositionHis> posi = new ArrayList<PositionHis>();

		Query qr = s.createQuery("from PositionHis a where a.posAmt < 10000"
		// "where a.mvDataId =:param "
		// + " and   a.baseDate <= :param3 "

				// + "and a.baseDate =:param2"
				);
		// qr.setParameter("param", id);
		// qr.setParameter("param2", cal);
		// qr.setParameter("param3", cal1);

		posi = qr.list();
		
		logger.debug("posi count:{}", posi.size());
		for (PositionHis aa : posi) {
			logger.debug("Position:{},{}", aa.getPositionLosses().size());
		}
	}
}
