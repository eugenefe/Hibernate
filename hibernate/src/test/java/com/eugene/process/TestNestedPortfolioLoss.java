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

import com.eugene.element.PortfolioHis;
import com.eugene.element.PortfolioLoss;
import com.eugene.element.PortfolioLossId;
import com.eugene.math.Complex;
import com.eugene.util.HibernateUtil;
import com.eugene.util.LossFftProduct;
import com.eugene.util.PortLossComparator;

public class TestNestedPortfolioLoss {
	private final static Logger logger = LoggerFactory.getLogger(TestNestedPortfolioLoss.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long unitSize = 100000000;
		//측정하고자 하는 portfolio 의 부모 포트폴리오임
//		 String testId = "PORT_IND_ROOT";
		String testId = "PORT_IND_ROOT";
		
		String bssd = "20120930";
		writeNestedPortRst(testId, unitSize, bssd);
	}
	private static void writeNestedPortRst(String testId, long unitSize, String bssd) {
		int cnt = 0;
		int outCnt = 0;
		double totAmt = 0;

		List<String> parentIds = getParentPortfolioIds(testId);
		List<String> ids = new ArrayList<String>();

		for (String parentId : parentIds) {
			ids = getPortfolioIds(parentId);

			LossFftProduct fftProduct = new LossFftProduct((int) unitSize);

			for (String aa : ids) {
				List<PortfolioLoss> portLoss = getPortfolioLoss(aa);

				// for(SortedMap.Entry<Double, Double> xx : convertToMap(portLoss,unitSize).entrySet()){
				// logger.debug("{},{}", xx.getKey(), xx.getValue() );
				// }
				logger.debug("{}", aa);
				fftProduct.convoulution(convertToMap(portLoss, unitSize));

				// logger.debug("Port: {},{}", aa,//
				// fftProduct.getImLoss().length);
			}
			// for (int i = 0; i < fftProduct.getImLoss().length; i++) {
			// logger.debug("LossIM :{},{}", i, fftProduct.getImLoss()[i].re());
			// }

			setPortfolioLoss(fftProduct.getImLoss(), parentId, unitSize);

			double probSum = 0;
			double mean = 0;
			logger.debug("Separter: {}, {}", cnt);
		}

	}

	private static SortedMap<Double, Double> convertToMap(List<PortfolioLoss> loss, long unitSize) {
		SortedMap<Double, Double> rst = new TreeMap<Double, Double>();
		int cnt = 0;
		double prob = 0.0;
		double tempAmt = 0.0;
		PortLossComparator sorter = new PortLossComparator();
		Collections.sort(loss, sorter);

		for (PortfolioLoss aa : loss) {
			// tempAmt = (long)(aa.getLossAmt().doubleValue()/unitSize);
			tempAmt = aa.getLossAmt().doubleValue();
			rst.put(tempAmt, new Double(aa.getLossProb().doubleValue() - prob));
			prob = aa.getLossProb().doubleValue();
			cnt = cnt + 1;
		}

		// for(SortedMap.Entry<Double, Double> zz : rst.entrySet()){
		// logger.debug("SortedMap : {},{}", zz.getKey(), zz.getValue());
		// }
		return rst;
	}

	private static List<PortfolioLoss> getPortfolioLoss(String id) {
		List<PortfolioLoss> portLoss = new ArrayList<PortfolioLoss>();
		Session s = HibernateUtil.currentSession();

		Query qr = s.createQuery("select a.portfolioLosses " + " from PortfolioHis a where a.id.portfolioId = :param");
		qr.setParameter("param", id);

		portLoss = qr.list();
		return portLoss;
	}

	private static List<PortfolioHis> getPortfolioHis(String id, String bssd) {
		List<PortfolioHis> portHis = new ArrayList<PortfolioHis>();
		Session s = HibernateUtil.currentSession();

		Query qr = s.createQuery("select a from PortfolioHis a " + " where a.id.portfolioId = :param"
				+ " and a.id.bssd = :param2");
		qr.setParameter("param", id);
		qr.setParameter("param2", bssd);

		portHis = qr.list();
		return portHis;
	}

	private static List<String> getPortfolioIds(String parentId) {
		Session s = HibernateUtil.currentSession();

		List<String> ids = new ArrayList<String>();

		Query qr = s.createQuery("select distinct a.id from Portfolio a  " + " where a.portfolio.id LIKE :param "
		// + " and a.portfolio.id <> 'PORT_IND_ROOT'"
				+ " and a.id NOT LIKE 'PORT_IND_%D%' " 
				+ " order by a.id"
		// + " WHERE a.id =  'PORT_IND_'"
				);
		qr.setParameter("param", parentId);

		ids = qr.list();

		return ids;
	}

	private static List<String> getParentPortfolioIds(String testId) {
		Session s = HibernateUtil.currentSession();

		List<String> parentIds = new ArrayList<String>();

		Query qr = s.createQuery("select distinct a.id from Portfolio a  " 
				+ " where 1=1 "
				+ " and a.portfolio.id = :param" 
//				+ " and a.portfolio.id is null"
				+ " and a.id like 'PORT_IND_25%'"
				+ " order by a.id"
//		  + " WHERE a.id =  'PORT_IND_ROOT'"
				);
		 qr.setParameter("param", testId);

		parentIds = qr.list();

		return parentIds;
	}

	private static void setPortfolioLoss(Complex[] imLoss, String portId, long unitSize) {
		double prob = 0;
		Session s = HibernateUtil.currentSession();
		Transaction tx = s.beginTransaction();
		logger.debug("Start Save");
		for (int i = 0; i < imLoss.length; i++) {
			prob = prob + imLoss[i].re();
//			logger.debug("Prob:{}, {}", i * unitSize, prob);
			PortfolioLossId tempId = new PortfolioLossId("20120930", portId, "Loss" + i);
			PortfolioLoss temp = new PortfolioLoss(tempId, new BigDecimal(i * unitSize), new BigDecimal(prob));
			s.saveOrUpdate(temp);

		}
		tx.commit();
	}

}
