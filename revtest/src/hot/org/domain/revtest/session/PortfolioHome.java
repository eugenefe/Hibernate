package org.domain.revtest.session;

import org.domain.revtest.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("portfolioHome")
public class PortfolioHome extends EntityHome<Portfolio> {

	public void setPortfolioId(String id) {
		setId(id);
	}

	public String getPortfolioId() {
		return (String) getId();
	}

	@Override
	protected Portfolio createInstance() {
		Portfolio portfolio = new Portfolio();
		return portfolio;
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

	public Portfolio getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
