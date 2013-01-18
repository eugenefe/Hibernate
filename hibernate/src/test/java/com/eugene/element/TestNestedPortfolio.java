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

import com.eugene.math.Complex;
import com.eugene.util.HibernateUtil;

public class TestNestedPortfolio {
	private final static Logger logger = LoggerFactory.getLogger(TestNestedPortfolio.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		List<Portfolio> port = new ArrayList<Portfolio>();
		List<PositionHis> posHis = new ArrayList<PositionHis>();
		List<PortfolioHis> portHis = new ArrayList<PortfolioHis>();
		List<PortfolioLoss> portLoss = new ArrayList<PortfolioLoss>();
		
		
//		Set<PositionHis> posHis = new HashSet<PositionHis>();
		List<String>ids =getPortfolioIds();
		String testId  = "PORT_IND_ROOT";
//		TODO:
		long unitSize = 10000000;
		
		logger.debug("Return Size : {}", ids.size());
		for( String aa : ids){
			logger.debug("Ids: {} ", aa.toString());
		}
		
		/*port = getPortfolio("PORT_IND_15_B2");
		for(Portfolio bb : port){
			logger.debug("Port :{},{}", bb.getId(), bb.getLevel2());
			logger.debug("Port :{},{}", bb.getPortfolioHises().size());
			for(PortfolioHis cc : bb.getPortfolioHises()){
				logger.debug("{},{}", cc.getId().getPortfolioId(), cc.getPortfolioLosses().size());
			}
		}*/
		
		
		/*portHis = getPortfolioHis(testId);
		for(PortfolioHis cc : portHis){
			logger.debug("{},{}", cc.getId().getPortfolioId(), cc.getPortAmt());
			for(PortfolioLoss zz : cc.getPortfolioLosses()){
				logger.debug("Loss Dist : {},{}", zz.getLossAmt(), zz.getLossProb());
			}
		}*/
		
//		portLoss = getPortfolioLoss(testId);
//		for(PortfolioLoss xx : portLoss	){
//			logger.debug("{},{}", xx.getId().getPortfolioId(), xx.getId().getLossId());
//			logger.debug("{},{}", xx.getLossAmt(), xx.getLossProb());
//		}
//		setPortfolioLoss();
//		setPortfolioLoss(testId, unitSize);

	}
	private static List<PortfolioHis> getPortfolioHis(String id){
		List<PortfolioHis>	portHis = new ArrayList<PortfolioHis>();
		Session s = HibernateUtil.currentSession();

		Query qr = s.createQuery("select a from PortfolioHis a where a.id.portfolioId = :param"
				);
		qr.setParameter("param", id);
		
		portHis = qr.list();
		return portHis;
	}
	
	private static List<PortfolioLoss> getPortfolioLoss(String id){
		List<PortfolioLoss>	portLoss = new ArrayList<PortfolioLoss>();
		Session s = HibernateUtil.currentSession();

		Query qr = s.createQuery("select a.portfolioLosses " +
				" from PortfolioHis a where a.id.portfolioId = :param"
				);
		qr.setParameter("param", id);
		
		portLoss = qr.list();
		return portLoss;
	}
	private static List<Portfolio> getPortfolio(String id){
		List<Portfolio>	port = new ArrayList<Portfolio>();
		Session s = HibernateUtil.currentSession();

		Query qr = s.createQuery("select a from Portfolio a"
				);
		
		port = qr.list();
		return port;
	}
	
	private static List<String> getPortfolioIds() {
		Session s = HibernateUtil.currentSession();

		List<String> ids = new ArrayList<String>();

//		Query qr = s.createQuery("select distinct a.id from Portfolio a  "
//		// + " where a.portfolio.id = 'PORT_ACC_ROOT' "
////				+ " WHERE a.id = 'PORT_IND_15_B2'"
//				+ " where a.level2 is null"
//		);
		Query qr = s.createQuery("select distinct a.id from Portfolio a  "
				+ " where a.portfolio.id LIKE 'PORT_IND_01' " 
				+ " and a.portfolio.id <> 'PORT_IND_ROOT'" 
				+ " order by a.id"
		// + " WHERE a.id =  'PORT_IND_'"
				);
		ids = qr.list();

		return ids;
	}


	private static void setPortfolioLoss(){
		Session s = HibernateUtil.currentSession();
		Transaction tx = s.beginTransaction();
	
		PortfolioLossId tempId = new PortfolioLossId("20120930", "PORT_IND_ROOT", "Loss0");
		
		s.saveOrUpdate(new PortfolioLoss(tempId, new BigDecimal( 100), new BigDecimal(1)));
		tx.commit();
	}
	
	private static void setPortfolioLoss( String portId, long unitSize){
		double prob =0;
		Complex[] imLoss = new Complex[2];
		imLoss[0] = new Complex(0.0, 0.0);
		imLoss[1] = new Complex(1.0, 0.0);
		
		Session s = HibernateUtil.currentSession();
		Transaction tx = s.beginTransaction();
		
		for(int i=0; i< imLoss.length; i++){
			prob = prob + imLoss[i].re();
//			logger.debug("Prob:{}", prob);
			PortfolioLossId tempId = new PortfolioLossId("20120930", portId, "Loss"+i);
			PortfolioLoss temp = new PortfolioLoss(tempId, new BigDecimal( i* unitSize), new BigDecimal(prob));
			s.saveOrUpdate(temp);
			
		}
		tx.commit();
	}
}
