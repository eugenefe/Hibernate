package com.eugene.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eugene.element.PositionLoss;
import com.eugene.element.PositionLossId;
import com.eugene.process.TestPortfolioLoss;

public class LossCartProduct {
	private final static Logger logger = LoggerFactory.getLogger(LossCartProduct.class);
	private List<PositionLoss> lossDist = new ArrayList<PositionLoss>();
	private double portAmt;
	
	public LossCartProduct(){
		this.lossDist = new ArrayList<PositionLoss>();
		PositionLossId temp = new PositionLossId("20120930", "aaa", "loss0");
		lossDist.add(new PositionLoss(temp,new BigDecimal(0),new BigDecimal(1)));
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
	
	public List<PositionLoss> cartProduct(List<PositionLoss> loss){
		List<PositionLoss> rst = new ArrayList<PositionLoss>();
		double tempAmt =0;
		double tempProb = 0;

		int cnt =0;
		
		for(PositionLoss aa: getLossDist()){
			for (PositionLoss bb :loss){
				tempAmt = aa.getLossAmt().doubleValue()+ bb.getLossAmt().doubleValue();
				tempProb = aa.getLossProb().doubleValue()* bb.getLossProb().doubleValue();
//				TODO : Construct Á¶Á¤
				PositionLossId tempId = new PositionLossId("20120930","aaa","Loss"+cnt);
				cnt= cnt+1;
				rst.add(new PositionLoss(tempId, new BigDecimal(tempAmt), new BigDecimal(tempProb)));
			}
		}
		
		Collections.sort(rst,new LossComparator());
		tempProb = 0 ;
		for(int i=0; i<rst.size()-1; i++){
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
