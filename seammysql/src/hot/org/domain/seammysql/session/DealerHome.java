package org.domain.seammysql.session;

import org.domain.seammysql.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("dealerHome")
public class DealerHome extends EntityHome<Dealer> {

	public void setDealerId(String id) {
		setId(id);
	}

	public String getDealerId() {
		return (String) getId();
	}

	@Override
	protected Dealer createInstance() {
		Dealer dealer = new Dealer();
		return dealer;
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

	public Dealer getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
