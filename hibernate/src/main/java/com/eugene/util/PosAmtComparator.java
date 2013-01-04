package com.eugene.util;

import java.util.Comparator;

import com.eugene.element.PositionHis;

public class PosAmtComparator implements Comparator<PositionHis> {

	@Override
	public int compare(PositionHis o1, PositionHis o2) {
		return -1* o1.getPosAmt().compareTo(o2.getPosAmt());
	}
	

}
