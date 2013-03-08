package org.domain.seammysql.session;

import org.domain.seammysql.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("baseDateHome")
public class BaseDateHome extends EntityHome<BaseDate> {

	public void setBaseDateBssd(String id) {
		setId(id);
	}

	public String getBaseDateBssd() {
		return (String) getId();
	}

	@Override
	protected BaseDate createInstance() {
		BaseDate baseDate = new BaseDate();
		return baseDate;
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

	public BaseDate getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
