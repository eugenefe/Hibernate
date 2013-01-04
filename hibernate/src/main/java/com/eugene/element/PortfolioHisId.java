package com.eugene.element;

// Generated 2012. 12. 31 ���� 6:19:47 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PortfolioHisId generated by hbm2java
 */
@Embeddable
public class PortfolioHisId implements java.io.Serializable {

	private String bssd;
	private String portfolioId;

	public PortfolioHisId() {
	}

	public PortfolioHisId(String bssd, String portfolioId) {
		this.bssd = bssd;
		this.portfolioId = portfolioId;
	}

	@Column(name = "BSSD", nullable = false, length = 8)
	public String getBssd() {
		return this.bssd;
	}

	public void setBssd(String bssd) {
		this.bssd = bssd;
	}

	@Column(name = "PORTFOLIO_ID", nullable = false, length = 50)
	public String getPortfolioId() {
		return this.portfolioId;
	}

	public void setPortfolioId(String portfolioId) {
		this.portfolioId = portfolioId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PortfolioHisId))
			return false;
		PortfolioHisId castOther = (PortfolioHisId) other;

		return ((this.getBssd() == castOther.getBssd()) || (this.getBssd() != null
				&& castOther.getBssd() != null && this.getBssd().equals(castOther.getBssd())))
				&& ((this.getPortfolioId() == castOther.getPortfolioId()) || (this.getPortfolioId() != null
						&& castOther.getPortfolioId() != null && this.getPortfolioId().equals(
						castOther.getPortfolioId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getBssd() == null ? 0 : this.getBssd().hashCode());
		result = 37 * result + (getPortfolioId() == null ? 0 : this.getPortfolioId().hashCode());
		return result;
	}

}