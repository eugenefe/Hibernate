package com.eugene.element;

// Generated 2012. 12. 20 ���� 5:18:49 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BaseDate generated by hbm2java
 */
@Entity
@Table(name = "BASE_DATE")
public class BaseDate implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String bssd;
	private String prevBssd;
	private Integer nextBssd;
	private String eomBssd;

	public BaseDate() {
	}

	public BaseDate(String bssd) {
		this.bssd = bssd;
	}

	public BaseDate(String bssd, String prevBssd, Integer nextBssd,
			String eomBssd) {
		this.bssd = bssd;
		this.prevBssd = prevBssd;
		this.nextBssd = nextBssd;
		this.eomBssd = eomBssd;
	}

	@Id
	@Column(name = "BSSD", unique = true, nullable = false, length = 8)
	public String getBssd() {
		return this.bssd;
	}

	public void setBssd(String bssd) {
		this.bssd = bssd;
	}

	@Column(name = "PREV_BSSD", length = 8)
	public String getPrevBssd() {
		return this.prevBssd;
	}

	public void setPrevBssd(String prevBssd) {
		this.prevBssd = prevBssd;
	}

	@Column(name = "NEXT_BSSD", precision = 8, scale = 0)
	public Integer getNextBssd() {
		return this.nextBssd;
	}

	public void setNextBssd(Integer nextBssd) {
		this.nextBssd = nextBssd;
	}

	@Column(name = "EOM_BSSD", length = 8)
	public String getEomBssd() {
		return this.eomBssd;
	}

	public void setEomBssd(String eomBssd) {
		this.eomBssd = eomBssd;
	}

}