package com.eugene.process;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

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
import com.eugene.math.Complex;
import com.eugene.util.HibernateUtil;
import com.eugene.util.LossCartProduct;
import com.eugene.util.LossFftProduct;
import com.eugene.util.PosLossComparator;

public class NewTestPortfolioLoss {
	private final static Logger logger = LoggerFactory.getLogger(NewTestPortfolioLoss.class);

	/**
	 * @param args
	 */
	 
	 public static void main(String[] args) {
		 long unitSize =10000000;
		 String id ="COPY_PORT_CM_IND_22%";
//		 String id ="FLAT_IND_23%";
//		 writeRst(id, unitSize);
		 writeRstAlt(id, unitSize);
	 }
	 private static void writeRst(String id, long unitSize){
		 
		 List<String> ids = getPortfolioIds(id);
		 for(String aa:ids){
			 writeLeafPortLoss(aa, unitSize);
		 }
	 }
	private static void writeLeafPortLoss(String id, long unitSize) {
		int cnt = 0;
		int outCnt = 0;
		double totAmt = 0;

		LossCartProduct cartProduct = new LossCartProduct();
		LossFftProduct fftProduct = new LossFftProduct((int) unitSize);

		List<PositionHis> posiHis = new ArrayList<PositionHis>();
		posiHis = getPositionHis(id);
		logger.debug("Size :{} {}", id, posiHis.size());

		for (PositionHis kk : posiHis) {
			cnt = cnt + 1;
			totAmt = totAmt + kk.getPosAmt().doubleValue();
			if (cnt < 5) {
				cartProduct.cartProductIm(kk.getPositionLosses());
			} else {
				// logger.debug("{}", cartProduct.getLossMap().size());
				fftProduct.convoulution(cartProduct.getLossMap());
//				 fftProduct.convoulution(convertToMap(kk.getPositionLosses(), unitSize));
				cnt = 0;
				outCnt = outCnt + 1;
				cartProduct = null;
				cartProduct = new LossCartProduct();
			}
			logger.debug("Posi: {},{}", outCnt, posiHis.size());
		}
		setPortfolioLoss(fftProduct.getImLoss(), id, unitSize);
		posiHis = null;
		fftProduct = null;
		outCnt = 0;

		logger.debug("Separter: {}, {}", cnt);
	}

	private static void writeRstAlt(String id, long unitSize) {
		int cnt = 0;
		int outCnt = 0;
		double totAmt = 0;

		// List<Portfolio> port = getPortfolio();
		// List<PositionHis> posiHis = getPosition();
		List<String> ids = getPortfolioIds(id);

		// List<PositionLoss> posLoss = new ArrayList<PositionLoss>();

		// logger.debug("Port Size: {},{}", port.size(), posiHis.size());

		LossCartProduct cartProduct = new LossCartProduct();
		LossFftProduct fftProduct = new LossFftProduct((int) unitSize);

		for (String aa : ids) {
			List<PositionHis> posiHis = new ArrayList<PositionHis>();
			posiHis = getPositionHis(aa);
			logger.debug("Size :{} {}", aa, posiHis.size());
			fftProduct = new LossFftProduct((int) unitSize);
			if(posiHis.size() > 0) {
				
			
			for (PositionHis kk : posiHis) {
				cnt = cnt + 1;
				totAmt = totAmt + kk.getPosAmt().doubleValue();
				if (cnt < 4) {
					cartProduct.cartProductIm(kk.getPositionLosses());
				} else {
					// logger.debug("{}", cartProduct.getLossMap().size());
					fftProduct.convoulution(cartProduct.getLossMap());
					// fftProduct.convoulution(convertToMap(kk.getPositionLosses(), unitSize));
					cnt = 0;
					outCnt = outCnt + 1;
					cartProduct = null;
					cartProduct = new LossCartProduct();
					// cartProduct.getLossDist().clear();
					// logger.debug("Posi: {},{}", outCnt, totAmt);
					// logger.debug("Posi: {},{}", fftProduct.getImLoss().length);
				}
//				logger.debug("Posi: {},{}", outCnt, posiHis.size());
			}
			setPortfolioLoss(fftProduct.getImLoss(), aa, unitSize);
			posiHis = null;
			fftProduct = null;
			outCnt = 0;
			}
		}

		double probSum = 0;
		double mean = 0;

		logger.debug("Separter: {}, {}", cnt);

		// for (int i = 0; i < fftProduct.getImLoss().length; i++) {
		// mean = mean + i * fftProduct.getImLoss()[i].re();
		// probSum = probSum + fftProduct.getImLoss()[i].re();
		// logger.info("{},{}", i, probSum);
		// }
		// logger.info("Mean : {},{}", mean, totAmt);
	}

	private static SortedMap<Double, Double> convertToMap(List<PositionLoss> loss, long unitSize) {
		SortedMap<Double, Double> rst = new TreeMap<Double, Double>();
		int cnt = 0;
		double prob = 0.0;
		double tempAmt = 0.0;
		PosLossComparator sorter = new PosLossComparator();
		Collections.sort(loss, sorter);

		for (PositionLoss aa : loss) {
			tempAmt = aa.getLossAmt().doubleValue();
			rst.put(tempAmt, new Double(aa.getLossProb().doubleValue()));
			cnt = cnt + 1;
		}

		return rst;
	}

	private static List<Portfolio> getPortfolio() {
		Session s = HibernateUtil.currentSession();

		List<Portfolio> port = new ArrayList<Portfolio>();

		// Query qr =
		// s.createQuery("from Portfolio a  where a.poftfolio.id = 'PORT_ACC_ROOT'"
		Query qr = s.createQuery(" from Portfolio a  "
		// + " where a.portfolio.id = 'PORT_ACC_ROOT' "
				+ " WHERE a.id = 'PORT_IND_15'");
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

		// Query qr = s.createQuery("select a.positionHises from Portfolio a  " +
		// // " where a.portfolio.id = 'PORT_ACC_ROOT' " +
		// " where a.id = :param"
		// );
		Query qr = s.createQuery("select a from PositionHis as a inner join a.portfolios as b  "
				+ " where b.id = :param " + " order by a.posAmt desc");

		qr.setParameter("param", id);

		int cnt = 0;
		posHis = qr.list();
		logger.debug("posHis :{}", posHis.size());

		return posHis;
	}

	private static List<Portfolio> getPortfolio(String id) {
		Session s = HibernateUtil.currentSession();

		List<Portfolio> port = new ArrayList<Portfolio>();

		Query qr = s.createQuery("select a from Portfolio a  " + " where a.id = :param");
		qr.setParameter("param", id);
		port = qr.list();

		int cnt = 0;
		return port;
	}

	private static List<String> getPortfolioIds(String id) {
		Session s = HibernateUtil.currentSession();

		List<String> ids = new ArrayList<String>();

		Query qr = s.createQuery("select distinct a.id from Portfolio a  "
				// + " where a.portfolio.id = 'PORT_ACC_ROOT' "
				// + " WHERE a.id = 'PORT_IND_15_B2'"
				+ " where 1=1 "
				// + " AND a.level2 is not null"
				// + " and a.id LIKE 'PORT_IND_'"
				+ " AND a.level2 is not null" 
				+ " AND a.id not like 'COPY_PORT_CM_IND%D%'" 
				+ " AND a.id like :param"
//				+ " AND a.id not like 'PORT_CM_IND_06%'" 
//				+ " AND a.id not like 'PORT_CM_IND_10%'"
//				+ " AND a.id not like 'PORT_CM_IND_12%'"
//				+ " AND a.id not like 'PORT_CM_IND_16%'"
//				+ " AND a.id not like 'PORT_CM_IND_20%'"
//				+ " AND a.id not like 'PORT_CM_IND_21%'"
				+ " ORDER BY a.id");
		qr.setParameter("param", id);
		ids = qr.list();

		return ids;
	}

	private static void setPortfolioLoss(Complex[] imLoss, String portId, long unitSize) {
		double prob = 0;
		Session s = HibernateUtil.currentSession();
		Transaction tx = s.beginTransaction();
		logger.debug("Start Save");
		for (int i = 0; i < imLoss.length; i++) {
			prob = prob + imLoss[i].re();
//			 logger.debug("Prob:{},{}", i, prob);
			PortfolioLossId tempId = new PortfolioLossId("20120930", portId, "Loss" + i);
			PortfolioLoss temp = new PortfolioLoss(tempId, new BigDecimal(i * unitSize), new BigDecimal(prob));
			s.saveOrUpdate(temp);

		}
		tx.commit();
	}

}
