package com.eugene.process;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eugene.element.Portfolio;
import com.eugene.element.PortfolioLoss;
import com.eugene.element.PortfolioLossId;
import com.eugene.element.PositionHis;
import com.eugene.element.PositionLoss;
import com.eugene.element.PositionLossId;
import com.eugene.element.TestPortfolio;
import com.eugene.math.Complex;
import com.eugene.math.Fft;
import com.eugene.util.HibernateUtil;
import com.eugene.util.LossCartProduct;
import com.eugene.util.LossComparator;
import com.eugene.util.LossFftProduct;
import com.eugene.util.PosAmtComparator;

public class TestPortfolioLoss {
	private final static Logger logger = LoggerFactory.getLogger(TestPortfolioLoss.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int cnt = 0;
		int outCnt = 0;
		double totAmt = 0;
		Complex[] temp;
		long unitSize =10000000;
		
//		List<Portfolio> port = getPortfolio();
//		List<PositionHis> posiHis = getPosition();
		List<String> ids = getPortfolioIds();

//		 List<PositionLoss> posLoss = new ArrayList<PositionLoss>();

//		logger.debug("Port Size: {},{}", port.size(), posiHis.size());

		LossCartProduct cartProduct = new LossCartProduct();
		LossFftProduct fftProduct = new LossFftProduct((int)unitSize);

		for (String aa : ids) {
			List<PositionHis> posiHis = new ArrayList<PositionHis>();
			posiHis = getPositionHis(aa);
			logger.debug("Size :{} {}", aa, posiHis.size());
			 fftProduct = new LossFftProduct((int)unitSize);
			for (PositionHis kk : posiHis) {
				cnt = cnt + 1;
				totAmt = totAmt + kk.getPosAmt().doubleValue();
				if (cnt < 10) {
					cartProduct.cartProduct(kk.getPositionLosses());
				} else {
					fftProduct.convolution(cartProduct.getLossDist());
					cnt = 0;
					outCnt = outCnt + 1;
					cartProduct = new LossCartProduct();
					// cartProduct.getLossDist().clear();
					logger.debug("Posi: {},{}", outCnt, totAmt);
					logger.debug("Posi: {},{}", fftProduct.getImLoss().length);
				}
				// logger.debug("Posi: {},{}", outCnt,kk.getId().getPositionId());
			}
			outCnt =0;
			totAmt =0;
			setPortfolioLoss(fftProduct.getImLoss(), aa, unitSize);
			
		}

		double probSum = 0;
		double mean = 0;

		logger.debug("Separter: {}, {}", cnt );

//		for (int i = 0; i < fftProduct.getImLoss().length; i++) {
//			mean = mean + i * fftProduct.getImLoss()[i].re();
//			probSum = probSum + fftProduct.getImLoss()[i].re();
//			logger.info("{},{}", i, probSum);
//		}
//		logger.info("Mean : {},{}", mean, totAmt);
	}

	private static List<Portfolio> getPortfolio() {
		Session s = HibernateUtil.currentSession();

		List<Portfolio> port = new ArrayList<Portfolio>();

		// Query qr =
		// s.createQuery("from Portfolio a  where a.poftfolio.id = 'PORT_ACC_ROOT'"
		Query qr = s.createQuery(" from Portfolio a  "
		// + " where a.portfolio.id = 'PORT_ACC_ROOT' "
				+ " WHERE a.id = 'PORT_IND_20'");
		port = qr.list();

		return port;
	}

	private static List<PositionHis> getPosition() {
		Session s = HibernateUtil.currentSession();

		List<PositionHis> posi = new ArrayList<PositionHis>();

		Query qr = s.createQuery("select a.positionHises from Portfolio a  "
		// + " where a.portfolio.id = 'PORT_ACC_ROOT' "
				+ " WHERE a.id = 'PORT_IND_15_B2'");
		posi = qr.list();

		return posi;
	}

	private static List<PositionHis> getPositionHis(String id) {
		Session s = HibernateUtil.currentSession();

		List<PositionHis> posHis = new ArrayList<PositionHis>();

//		Query qr = s.createQuery("select a.positionHises from Portfolio a  " +
//		// " where a.portfolio.id = 'PORT_ACC_ROOT' " +
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

	private static List<Portfolio> getPortfolio(String id) {
		Session s = HibernateUtil.currentSession();

		List<Portfolio> port = new ArrayList<Portfolio>();

		Query qr = s.createQuery("select a from Portfolio a  " +
				" where a.id = :param");
		qr.setParameter("param", id);
		port = qr.list();

		int cnt = 0;
		return port;
	}

	private static List<String> getPortfolioIds() {
		Session s = HibernateUtil.currentSession();

		List<String> ids = new ArrayList<String>();

		Query qr = s.createQuery("select distinct a.id from Portfolio a  "
		// + " where a.portfolio.id = 'PORT_ACC_ROOT' "
		// + " WHERE a.id = 'PORT_IND_15_B2'"
				+ " where 1=1 " 
//				+ " AND a.level2 is not null" 
				+ " and a.id LIKE 'PORT_IND_20'" 
				+ " ORDER BY a.id"
				);
		ids = qr.list();

		return ids;
	}
	
	private static void setPortfolioLoss(Complex[] imLoss, String portId, long unitSize){
		double prob =0;
		Session s = HibernateUtil.currentSession();
		Transaction tx = s.beginTransaction();
		logger.debug("Start Save");
		for(int i=0; i< imLoss.length; i++){
//			prob = Math.round(100000000 * imLoss[i].re()) /100000000;
			prob = prob + imLoss[i].re();
//			logger.debug("Prob:{}", prob);
			PortfolioLossId tempId = new PortfolioLossId("20120930", portId, "Loss"+i);
			PortfolioLoss temp = new PortfolioLoss(tempId, new BigDecimal( i* unitSize), new BigDecimal(prob));
			s.saveOrUpdate(temp);
			
		}
		tx.commit();
	}
	
}
