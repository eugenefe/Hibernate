package com.eugene.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eugene.element.PositionLoss;
import com.eugene.element.PositionLossId;
import com.eugene.math.Complex;
import com.eugene.process.TestPortfolioLoss;

public class LossCartProduct {
	private final static Logger logger = LoggerFactory.getLogger(LossCartProduct.class);
	private List<PositionLoss> lossDist = new ArrayList<PositionLoss>();
	private double portAmt;
	private Complex[] imLoss;
	private SortedMap<Double, Double> lossMap  = new TreeMap<Double, Double>();
	
	
	public SortedMap<Double, Double> getLossMap() {
		return lossMap;
	}


	public void setLossMap(SortedMap<Double, Double> lossMap) {
		this.lossMap = lossMap;
	}


	public Complex[] getImLoss() {
		return imLoss;
	}


	public void setImLoss(Complex[] imLoss) {
		this.imLoss = imLoss;
	}


	public LossCartProduct(){
		this.lossDist = new ArrayList<PositionLoss>();
		PositionLossId temp = new PositionLossId("20120930", "aaa", "loss0");
		lossDist.add(new PositionLoss(temp,new BigDecimal(0),new BigDecimal(1)));
		lossMap.put(0.0, 1.0);
	};


	public List<PositionLoss> getLossDist() {
		return lossDist;
	}


	public void setLossDist(List<PositionLoss> lossDist) {
		this.lossDist = lossDist;
	}
	
	public double getPortAmt() {
		return portAmt;
	}


	public void setPortAmt(double portAmt) {
		this.portAmt = portAmt;
	}
	
//	public SortedMap<Double, Double> cartProductIm(List<PositionLoss> loss){
		public void cartProductIm(List<PositionLoss> loss){	
		double tempAmt =0;
		double tempProb = 0;
		SortedMap<Double, Double> tempMap = new TreeMap<Double, Double>();
		
		for(SortedMap.Entry<Double, Double> aa : lossMap.entrySet())	{
			for (PositionLoss bb :loss){
				tempAmt = aa.getKey().doubleValue() + bb.getLossAmt().doubleValue();
				tempProb = aa.getValue().doubleValue() * bb.getLossProb().doubleValue();

//				TODO: MAP PUT 확인
				if(tempMap.containsKey(tempAmt)){
					tempProb = tempProb + tempMap.get(tempAmt).doubleValue();
					tempMap.put(tempAmt, tempProb);
				}
				else {
					tempMap.put(tempAmt, tempProb);
				}
			}
		}
		
		lossMap.clear();
		lossMap = tempMap;
		
//		for(SortedMap.Entry<Double, Double> aa : lossMap.entrySet()){
//			logger.debug("lossMap: {},{}", aa.getKey(), aa.getValue());
//		}
//		return lossMap;
	}
	
	public List<PositionLoss> cartProduct(List<PositionLoss> loss){
		List<PositionLoss> rst = new ArrayList<PositionLoss>();
		double tempAmt =0;
		double tempProb = 0;

		int cnt =0;
		
		for(PositionLoss aa: getLossDist()){
			for (PositionLoss bb :loss){
				tempAmt = aa.getLossAmt().doubleValue()+ bb.getLossAmt().doubleValue();
				tempProb = aa.getLossProb().doubleValue()* bb.getLossProb().doubleValue();
//				TODO : Construct 조정
				PositionLossId tempId = new PositionLossId("20120930","aaa","Loss"+cnt);
				cnt= cnt+1;
				rst.add(new PositionLoss(tempId, new BigDecimal(tempAmt), new BigDecimal(tempProb)));
			}
		}
		
		Collections.sort(rst,new LossComparator());
		tempProb = 0 ;
		for(int i=0; i<rst.size(); i++){
			if(rst.get(i+1).getLossAmt().doubleValue()==
					rst.get(i).getLossAmt().doubleValue()){
				tempProb = rst.get(i+1).getLossProb().doubleValue() + rst.get(i).getLossProb().doubleValue();
				rst.get(i).setLossProb(new BigDecimal(tempProb));
				rst.remove(i+1);
			}
		}
		lossDist.clear();
				
		tempProb =0;
		for( PositionLoss aa:  rst){
			if(tempProb < 0.9991){
				lossDist.add(aa);
			}
			else{
				aa.setLossProb(new BigDecimal(1-tempProb));
				lossDist.add(aa);
				break ;
			}
			tempProb = tempProb + aa.getLossProb().doubleValue();
		}
		
//		tempProb=0;
//		for(PositionLoss xx: lossDist){
//			tempProb = tempProb + xx.getLossProb().doubleValue();
//			logger.debug("Cartisian : {},{}",xx.getLossAmt().doubleValue(), xx.getLossProb().doubleValue());
//		}
		
		return lossDist;
	}
	

}
