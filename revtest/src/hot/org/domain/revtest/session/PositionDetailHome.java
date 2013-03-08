package org.domain.revtest.session;

import org.domain.revtest.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("positionDetailHome")
public class PositionDetailHome extends EntityHome<PositionDetail> {

	public void setPositionDetailId(PositionDetailId id) {
		setId(id);
	}

	public PositionDetailId getPositionDetailId() {
		return (PositionDetailId) getId();
	}

	public PositionDetailHome() {
		setPositionDetailId(new PositionDetailId());
	}

	@Override
	public boolean isIdDefined() {
		if (getPositionDetailId().getBssd() == null
				|| "".equals(getPositionDetailId().getBssd()))
			return false;
		if (getPositionDetailId().getPositionId() == null
				|| "".equals(getPositionDetailId().getPositionId()))
			return false;
		return true;
	}

	@Override
	protected PositionDetail createInstance() {
		PositionDetail positionDetail = new PositionDetail();
		positionDetail.setId(new PositionDetailId());
		return positionDetail;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public PositionDetail getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
