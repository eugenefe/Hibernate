package com.eugene.element;

// Generated 2012. 12. 31 ���� 6:19:47 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PositionHisId generated by hbm2java
 */
@Embeddable
public class PositionHisId implements java.io.Serializable {

	private String bssd;
	private String positionId;

	public PositionHisId() {
	}

	public PositionHisId(String bssd, String positionId) {
		this.bssd = bssd;
		this.positionId = positionId;
	}

	@Column(name = "BSSD", nullable = false, length = 8)
	public String getBssd() {
		return this.bssd;
	}

	public void setBssd(String bssd) {
		this.bssd = bssd;
	}

	@Column(name = "POSITION_ID", nullable = false, length = 100)
	public String getPositionId() {
		return this.positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PositionHisId))
			return false;
		PositionHisId castOther = (PositionHisId) other;

		return ((this.getBssd() == castOther.getBssd()) || (this.getBssd() != null
				&& castOther.getBssd() != null && this.getBssd().equals(castOther.getBssd())))
				&& ((this.getPositionId() == castOther.getPositionId()) || (this.getPositionId() != null
						&& castOther.getPositionId() != null && this.getPositionId().equals(
						castOther.getPositionId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getBssd() == null ? 0 : this.getBssd().hashCode());
		result = 37 * result + (getPositionId() == null ? 0 : this.getPositionId().hashCode());
		return result;
	}

}
