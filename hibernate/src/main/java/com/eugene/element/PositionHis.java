package com.eugene.element;

// Generated 2012. 12. 31 ���� 6:19:47 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * PositionHis generated by hbm2java
 */
@Entity
@Table(name = "POSITION_HIS")
public class PositionHis implements java.io.Serializable {

	private PositionHisId id;
	private Position position;
	private BigDecimal holdQty;
	private BigDecimal posAmt;
//	private Set<PositionLoss> positionLosses = new HashSet<PositionLoss>(0);
	private List<PositionLoss> positionLosses = new ArrayList<PositionLoss>();
	private Set<Portfolio> portfolios = new HashSet<Portfolio>(0);

	public PositionHis() {
	}

	public PositionHis(PositionHisId id, Position position) {
		this.id = id;
		this.position = position;
	}

	public PositionHis(PositionHisId id, Position position, BigDecimal holdQty, BigDecimal posAmt,
			List<PositionLoss> positionLosses, Set<Portfolio> portfolios) {
		this.id = id;
		this.position = position;
		this.holdQty = holdQty;
		this.posAmt = posAmt;
		this.positionLosses = positionLosses;
		this.portfolios = portfolios;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "bssd", column = @Column(name = "BSSD", nullable = false, length = 8)),
			@AttributeOverride(name = "positionId", column = @Column(name = "POSITION_ID", nullable = false, length = 100)) })
	public PositionHisId getId() {
		return this.id;
	}

	public void setId(PositionHisId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "POSITION_ID", nullable = false, insertable = false, updatable = false)
	public Position getPosition() {
		return this.position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	@Column(name = "HOLD_QTY", scale = 4)
	public BigDecimal getHoldQty() {
		return this.holdQty;
	}

	public void setHoldQty(BigDecimal holdQty) {
		this.holdQty = holdQty;
	}

	@Column(name = "POS_AMT", precision = 20)
	public BigDecimal getPosAmt() {
		return this.posAmt;
	}

	public void setPosAmt(BigDecimal posAmt) {
		this.posAmt = posAmt;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "positionHis")
	public List<PositionLoss> getPositionLosses() {
		return this.positionLosses;
	}

	public void setPositionLosses(List<PositionLoss> positionLosses) {
		this.positionLosses = positionLosses;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "PORTFOLIO_DETAIL", joinColumns = {
			@JoinColumn(name = "BSSD", nullable = false, updatable = false),
			@JoinColumn(name = "POSITION_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "PORTFOLIO_ID", nullable = false, updatable = false) })
	public Set<Portfolio> getPortfolios() {
		return this.portfolios;
	}

	public void setPortfolios(Set<Portfolio> portfolios) {
		this.portfolios = portfolios;
	}

}
