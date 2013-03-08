package org.domain.revtest.session;

import org.domain.revtest.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("portfolioDetailHome")
public class PortfolioDetailHome extends EntityHome<PortfolioDetail> {

	public void setPortfolioDetailId(PortfolioDetailId id) {
		setId(id);
	}

	public PortfolioDetailId getPortfolioDetailId() {
		return (PortfolioDetailId) getId();
	}

	public PortfolioDetailHome() {
		setPortfolioDetailId(new PortfolioDetailId());
	}

	@Override
	public boolean isIdDefined() {
		if (getPortfolioDetailId().getBssd() == null
				|| "".equals(getPortfolioDetailId().getBssd()))
			return false;
		if (getPortfolioDetailId().getPortfolioId() == null
				|| "".equals(getPortfolioDetailId().getPortfolioId()))
			return false;
		if (getPortfolioDetailId().getPositionId() == null
				|| "".equals(getPortfolioDetailId().getPositionId()))
			return false;
		return true;
	}

	@Override
	protected PortfolioDetail createInstance() {
		PortfolioDetail portfolioDetail = new PortfolioDetail();
		portfolioDetail.setId(new PortfolioDetailId());
		return portfolioDetail;
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

	public PortfolioDetail getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
