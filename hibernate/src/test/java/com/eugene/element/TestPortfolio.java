package com.eugene.element;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eugene.util.HibernateUtil;

public class TestPortfolio {
	private final static Logger logger = LoggerFactory.getLogger(TestPortfolio.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		List<Portfolio> port = new ArrayList<Portfolio>();
		List<PositionHis> posHis = new ArrayList<PositionHis>();
//		Set<PositionHis> posHis = new HashSet<PositionHis>();
		List<String>ids =getPortfolioIds();
		
//		logger.debug("Return Size : {}", ids.size());
//		for( String aa : ids){
//			logger.debug("Ids: {} ", aa.toString());
//		}
		
		/*port = getPortfolio("PORT_IND_15_B2");
		for(Portfolio bb : port){
			logger.debug("Port :{},{}", bb.getId(), bb.getLevel2());
		}*/
		
		
		posHis = getPositionHis("PORT_IND_15_A2");
		for(PositionHis cc : posHis){
			logger.debug("PosiHis :{},{}", cc.getId().getPositionId(), cc.getPosAmt());
		}
		logger.debug("Return Size : {}", posHis.size());
//		setPortfolioLoss();

	}
	private static List<PositionHis> getPositionHis(String id){
		Session s = HibernateUtil.currentSession();

		List<PositionHis> posHis = new ArrayList<PositionHis>();
//		Set<PositionHis> posHis = new HashSet<PositionHis>();

//		Query qr = s.createQuery("select a.positionHises from Portfolio a  " +
////				" where a.portfolio.id = 'PORT_ACC_ROOT' " +
//				" where a.id = :param"
//				);
		Query qr = s.createQuery("select a from PositionHis as a inner join a.portfolios as b  " +
				" where b.id = :param " +
				" order by a.posAmt desc"
				);
		
		qr.setParameter("param", id);
		
		int cnt = 0;
		posHis = qr.list();
		logger.debug("posHis :{}", posHis.size());

		return posHis;
	}
	private static List<Portfolio> getPortfolio(String id){
		Session s = HibernateUtil.currentSession();

		List<Portfolio> port = new ArrayList<Portfolio>();

		Query qr = s.createQuery("select a from Portfolio a  " +
//				" where a.portfolio.id = 'PORT_ACC_ROOT' " +
				" where a.id = :param " 
//				" and a.id like '%PORT_IND_15_%'"
				);
		qr.setParameter("param", id);
		port = qr.list();

		int cnt = 0;
//		logger.debug("{}", port.size());
//		for (Portfolio aa : port) {
//			logger.debug("Position:{},{}", aa.getId(),aa.getPositionHises().size());
//			for(PositionHis kk: aa.getPositionHises()){
//				logger.debug("Position:{},{}", aa.getId(),kk.getPosAmt());
//			}
//		}
		return port;
	}
	private static List<String> getPortfolioIds() {
		Session s = HibernateUtil.currentSession();

		List<String> ids = new ArrayList<String>();

		Query qr = s.createQuery("select distinct a.id from Portfolio a  "
		// + " where a.portfolio.id = 'PORT_ACC_ROOT' "
//				+ " WHERE a.id = 'PORT_IND_15_B2'"
				+ " where a.level2 is not null"
		);
		ids = qr.list();

		return ids;
	}
	private static void setPortfolioLoss(){
		Session s = HibernateUtil.currentSession();
		Transaction tx = s.beginTransaction();
		PortfolioLossId tempId = new PortfolioLossId("20120930", "PORT_IND_15", "loss0");
		
		s.saveOrUpdate(new PortfolioLoss(tempId, new BigDecimal( 100), new BigDecimal(1)));
		tx.commit();
	}
}
