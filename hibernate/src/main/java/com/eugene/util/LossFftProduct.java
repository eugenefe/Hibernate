package com.eugene.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eugene.element.PositionLoss;
import com.eugene.element.PositionLossId;
import com.eugene.math.Complex;
import com.eugene.math.Fft;

public class LossFftProduct {
	private final static Logger logger = LoggerFactory.getLogger(LossFftProduct.class);
	private double portAmt;
	private double maxLoss;
	private int unitSize;
	private int maxSize;
	private Complex[] imLoss;
	

	public Complex[] getImLoss() {
		return imLoss;
	}

	public void setImLoss(Complex[] imLoss) {
		this.imLoss = imLoss;
	}

	public double getMaxLoss() {
		return maxLoss;
	}

	public void setMaxLoss(double maxLoss) {
		this.maxLoss = maxLoss;
	}

	public int getUnitSize() {
		return unitSize;
	}

	public void setUnitSize(int unitSize) {
		this.unitSize = unitSize;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	private List<PositionLoss> lossDist = new ArrayList<PositionLoss>();

	public LossFftProduct(int unitSize) {
		this.unitSize = unitSize;
		this.lossDist = new ArrayList<PositionLoss>();

		PositionLossId temp = new PositionLossId("20120930", "aaa", "loss0");
		lossDist.add(new PositionLoss(temp, new BigDecimal(0), new BigDecimal(1)));

		this.imLoss = new Complex[1];
		imLoss[0] = new Complex(1.0,0);
	}

	public double getPortAmt() {
		return portAmt;
	}

	public void setPortAmt(double portAmt) {
		this.portAmt = portAmt;
	}

	public List<PositionLoss> getLossDist() {
		return lossDist;
	}

	public void setLossDist(List<PositionLoss> lossDist) {
		this.lossDist = lossDist;
	}
	
	public void convoulution(SortedMap<Double, Double> loss){
		double maxLoss = Math.max(getMaxLoss(), loss.lastKey().doubleValue());
		int maxSize    = (int)Math.max(1.0, Math.pow(2, Math.ceil(Math.log(maxLoss / unitSize) / Math.log(2))));
//		logger.debug("MaxLoss :{},{}", getMaxLoss(), loss.lastKey().doubleValue());
//		logger.debug("Maxsize :{},{}", maxSize, imLoss.length);
		
		Complex ZERO = new Complex(0, 0);
		Complex[] temp1 = new Complex[maxSize];
		Complex[] temp2 = new Complex[maxSize];
		Complex[] temp = new Complex[2*maxSize];
		
		//TODO: imLoss 가  maxSize 보다 1 큰 경우 예외
		for (int i = 0;      		i <   Math.min(maxSize, imLoss.length); i++) {
			temp1[i] = imLoss[i];
		}	
        for (int i =  Math.min(maxSize, imLoss.length); i < maxSize 	   ; i++) {
        	temp1[i] = ZERO;			
        }
        
		temp2 = convertToComplex(loss, maxSize);

		temp = Fft.convolve(temp1,temp2);
		
		int tempSize =0;
		double tempMaxLoss =0;
		double tempProb=0;
		
		for(int i=0; i< 2* maxSize ; i++){
			if(temp[i].re()>0.000000001){
				tempSize = i+1;
				tempMaxLoss = (double)i* unitSize;
			}
//			logger.debug("FFT :{},{}", temp[i].re(), i);
		}
		imLoss = null;
		imLoss = new Complex[tempSize+1];
		setMaxSize(tempSize+1);
		setMaxLoss(tempMaxLoss+ unitSize);
		
		for(int i=0;i<tempSize;i++){
			imLoss[i] = temp[i];
		}
		for(int i=tempSize; i< 2* maxSize; i++){
			tempProb = tempProb + temp[i].re();
		}
		imLoss[tempSize] = new Complex(tempProb,0.0);
		double zzzz=0;
		for(int i=0;i<imLoss.length ; i++){
			zzzz = zzzz + imLoss[i].re();
		}
//		logger.debug("Sum Prob : {}", zzzz);
		temp1 = null;
		temp2 = null;
		temp  = null;
		
//		return imLoss;
	}
	public Complex[] convolution(List<PositionLoss> loss) {
//	public List<PositionLoss> convolution(List<PositionLoss> loss) {
		List<PositionLoss> rst = new ArrayList<PositionLoss>();
		int maxSize = getMaxSizeNew(loss);
		
		Complex ZERO = new Complex(0, 0);
		Complex[] temp1 = new Complex[maxSize];
		Complex[] temp2 = new Complex[maxSize];
		Complex[] temp = new Complex[maxSize];
		
		
		for (int i = 0;      		i <   imLoss.length; i++) {
			temp1[i] = imLoss[i];
		}	
        for (int i = imLoss.length; i < maxSize 	   ; i++) {
        	temp1[i] = ZERO;			
        }
        
		temp2 = convertToComplex(loss, maxSize);

		temp = Fft.convolve(temp1,temp2);
		
		int tempSize =0;
		double tempMaxLoss =0;
		for(int i=0; i<maxSize ; i++){
			if(temp[i].re()>0.000000001){
				tempSize = i+1;
				tempMaxLoss = (double)i* unitSize;
			}
//			logger.debug("FFT :{},{}", temp[i].re(), i);
		}
		imLoss = new Complex[tempSize];
		setMaxSize(tempSize);
		setMaxLoss(tempMaxLoss);
		
		for(int i=0;i<tempSize;i++){
			imLoss[i] = temp[i];
		}
		
		return imLoss;
	}

	private int getMaxSizeNew(List<PositionLoss> loss) {
		int tempSize;
		int maxSize;
		double maxLossOld = getMaxLoss();
		double maxLoss = 0;
		int unitSize = getUnitSize();

		for (PositionLoss aa : loss) {
			if (aa.getLossAmt().doubleValue() > maxLoss) {
				maxLoss = aa.getLossAmt().doubleValue();
			}
		}
		maxLoss = Math.max(maxLoss,  maxLossOld);
		
		maxSize = (int) Math.pow(2, Math.ceil(Math.log(maxLoss / unitSize) / Math.log(2)));
		maxSize = Math.max(maxSize, getMaxSize());
//		tempSize = tempSize + getMaxSize();

		// logger.debug("Math1:{},{}",
		// Math.ceil(Math.log(tempSize)/Math.log(2)));
//		maxSize = (int) Math.pow(2, Math.ceil(Math.log(tempSize) / Math.log(2)));

//		logger.debug("MaxLoss {},{}", maxSize, maxLoss);
//		logger.debug("MaxLoss {},{}", unitSize, maxLoss);

		setMaxSize(maxSize);
		setMaxLoss(maxLoss);
		return maxSize;
	}

	private Complex[] convertToComplex(SortedMap<Double, Double> lossMap, int maxSize) {
		int retSize = 0;
		Complex[] rstTemp = new Complex[maxSize];

		for (int i = 0; i < maxSize; i++) {
			rstTemp[i] = new Complex(0, 0);
		}

		double prob = 0;
		int unitLoss =0;
		
		for(SortedMap.Entry<Double, Double> aa : lossMap.entrySet()){
			if( unitLoss == (int)(aa.getKey().doubleValue()/unitSize)){
				prob = prob + aa.getValue().doubleValue();
			}
			else {
				//직전 loss 저장
				rstTemp[unitLoss] =  new Complex(prob, 0);
				unitLoss = (int)(aa.getKey().doubleValue()/unitSize);
				prob =aa.getValue().doubleValue();
			}
		}
		//마지막 loss 저장
		rstTemp[unitLoss] =new Complex(prob, 0);
		
//		for (int i = 0; i < maxSize; i++) {
//			for(SortedMap.Entry<Double, Double> aa : lossMap.entrySet()){
//				if ((double) i * unitSize <= aa.getKey().doubleValue()
//						&& aa.getKey().doubleValue() < (double) (i + 1) * unitSize) {
//					prob = prob + aa.getValue().doubleValue();
//					retSize = i + 1;
//					// logger.debug("LossAmt : {}",
//					// aa.getLossAmt().doubleValue());
//				}
//			}
//			rstTemp[i] = new Complex(prob, 0);
//			prob = 0;
//		}

//		Complex[] rst = new Complex[retSize];
//		for (int i = 0; i < rstTemp.length; i++) {
//			rst[i] = rstTemp[i];
//			logger.debug("rst:{},{}", rstTemp[i].re(),i+1);
//		}
		return rstTemp;
	}
	
	
	private Complex[] convertToComplex(List<PositionLoss> loss, int maxSize) {

		LossComparator lossComp = new LossComparator();
		Collections.sort(loss, lossComp);
		int retSize = 0;
		Complex[] rstTemp = new Complex[maxSize];

		for (int i = 0; i < maxSize; i++) {
			rstTemp[i] = new Complex(0, 0);
		}

		double prob = 0;
		for (int i = 0; i < maxSize; i++) {
			for (PositionLoss aa : loss) {
				if ((double) i * unitSize <= aa.getLossAmt().doubleValue()
						&& aa.getLossAmt().doubleValue() < (double) (i + 1) * unitSize) {
					prob = prob + aa.getLossProb().doubleValue();
					retSize = i + 1;
					// logger.debug("LossAmt : {}",
					// aa.getLossAmt().doubleValue());
				}
			}
			rstTemp[i] = new Complex(prob, 0);
			prob = 0;
		}

		Complex[] rst = new Complex[retSize];

		for (int i = 0; i < rstTemp.length; i++) {
//			rst[i] = rstTemp[i];
//			logger.debug("rst:{},{}", rstTemp[i].re(),i+1);
		}
//		for (PositionLoss aa : loss) {
//			logger.debug("Loss:{},{}", aa.getLossAmt(), aa.getLossProb());
//		}
	
		return rstTemp;
	}
}
