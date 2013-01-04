package com.eugene.util;

import java.math.BigDecimal;
import java.util.Comparator;

import com.eugene.element.PositionLoss;

public class LossComparator implements Comparator<PositionLoss>{

	@Override
	public int compare(PositionLoss o1, PositionLoss o2) {
		return o1.getLossAmt().compareTo(o2.getLossAmt());
	}
	

}
