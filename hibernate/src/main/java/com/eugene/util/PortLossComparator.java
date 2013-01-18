package com.eugene.util;

import java.math.BigDecimal;
import java.util.Comparator;

import com.eugene.element.PortfolioLoss;
import com.eugene.element.PositionLoss;

public class PortLossComparator implements Comparator<PortfolioLoss>{

	@Override
	public int compare(PortfolioLoss o1, PortfolioLoss o2) {
		return o1.getLossAmt().compareTo(o2.getLossAmt());
	}
	

}
