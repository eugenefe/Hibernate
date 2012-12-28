package com.eugene.element;

// Generated 2012. 12. 20 ���� 5:18:49 by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * PortfolioDetail generated by hbm2java
 */
@Entity
@Table(name = "PORTFOLIO_DETAIL")
public class PortfolioDetail implements java.io.Serializable {

	private PortfolioDetailId id;

	public PortfolioDetail() {
	}

	public PortfolioDetail(PortfolioDetailId id) {
		this.id = id;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "bssd", column = @Column(name = "BSSD", nullable = false, length = 8)),
			@AttributeOverride(name = "portfolioId", column = @Column(name = "PORTFOLIO_ID", nullable = false, length = 30)),
			@AttributeOverride(name = "positionId", column = @Column(name = "POSITION_ID", nullable = false, length = 30)) })
	public PortfolioDetailId getId() {
		return this.id;
	}

	public void setId(PortfolioDetailId id) {
		this.id = id;
	}

}