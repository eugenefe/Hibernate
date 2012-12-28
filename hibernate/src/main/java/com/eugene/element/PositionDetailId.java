package com.eugene.element;

// Generated 2012. 12. 20 ���� 5:18:49 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PositionDetailId generated by hbm2java
 */
@Embeddable
public class PositionDetailId implements java.io.Serializable {

	private String bssd;
	private String id;

	public PositionDetailId() {
	}

	public PositionDetailId(String bssd, String id) {
		this.bssd = bssd;
		this.id = id;
	}

	@Column(name = "BSSD", nullable = false, length = 8)
	public String getBssd() {
		return this.bssd;
	}

	public void setBssd(String bssd) {
		this.bssd = bssd;
	}

	@Column(name = "ID", nullable = false, length = 30)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PositionDetailId))
			return false;
		PositionDetailId castOther = (PositionDetailId) other;

		return ((this.getBssd() == castOther.getBssd()) || (this.getBssd() != null
				&& castOther.getBssd() != null && this.getBssd().equals(
				castOther.getBssd())))
				&& ((this.getId() == castOther.getId()) || (this.getId() != null
						&& castOther.getId() != null && this.getId().equals(
						castOther.getId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getBssd() == null ? 0 : this.getBssd().hashCode());
		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		return result;
	}

}