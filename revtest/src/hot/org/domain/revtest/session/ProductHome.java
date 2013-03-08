package org.domain.revtest.session;

import org.domain.revtest.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("productHome")
public class ProductHome extends EntityHome<Product> {

	public void setProductId(String id) {
		setId(id);
	}

	public String getProductId() {
		return (String) getId();
	}

	@Override
	protected Product createInstance() {
		Product product = new Product();
		return product;
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

	public Product getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
