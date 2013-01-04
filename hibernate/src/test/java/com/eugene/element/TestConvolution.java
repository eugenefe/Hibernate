package com.eugene.element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eugene.math.Complex;
import com.eugene.math.Fft;
import com.eugene.util.LossCartProduct;
import com.eugene.util.LossFftProduct;

public class TestConvolution {
	private final static Logger logger = LoggerFactory.getLogger(TestConvolution.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Complex[] temp1 = new Complex[4];
		Complex[] temp2 = new Complex[4];
		Complex[] temp3 = new Complex[8];
		Complex[] temp = new Complex[8];
		Complex[] rst = new Complex[16];
		Complex[] temp4 = new Complex[16];
		
		Complex[] rst2 ;
		
		temp1[0]= new Complex(0.91,0);
		temp1[1]= new Complex(0.0,0);
		temp1[2]= new Complex(0.0,0);
		temp1[3]= new Complex(0.09,0);
		
		temp2[0]= new Complex(0.95,0);
		temp2[1]= new Complex(0.0,0);
		temp2[2]= new Complex(0.0,0);
		temp2[3]= new Complex(0.05,0);
		
		temp3[0]= new Complex(0.9,0);
		temp3[1]= new Complex(0.0,0);
		temp3[2]= new Complex(0.0,0);
		temp3[3]= new Complex(0.0,0);
		temp3[4]= new Complex(0.0,0);
		temp3[5]= new Complex(0.0,0);
		temp3[6]= new Complex(0.0,0);
		temp3[7]= new Complex(0.1,0);
		
		temp4[0]= new Complex(0.9,0);
		temp4[1]= new Complex(0.0,0);
		temp4[2]= new Complex(0.0,0);
		temp4[3]= new Complex(0.0,0);
		temp4[4]= new Complex(0.0,0);
		temp4[5]= new Complex(0.0,0);
		temp4[6]= new Complex(0.0,0);
		temp4[7]= new Complex(0.0,0);
		temp4[8]= new Complex(0.0,0);
		temp4[9]= new Complex(0.0,0);
		temp4[10]= new Complex(0.0,0);
		temp4[11]= new Complex(0.0,0);
		temp4[12]= new Complex(0.0,0);
		temp4[13]= new Complex(0.0,0);
		temp4[14]= new Complex(0.0,0);
		temp4[15]= new Complex(0.1,0);
		
		LossFftProduct aa = new LossFftProduct(1000);
		temp = Fft.convolve(temp1, temp2);
		
		rst = Fft.convolve(temp, temp3);
		
		rst2 = Fft.convolve(rst, temp4);
		
		
		
		for(int i=0 ; i< rst2.length;i++){
			logger.debug("FFT result :{},{}", rst2[i].re(), rst2[i].im());
		}
		
		


	}

}
