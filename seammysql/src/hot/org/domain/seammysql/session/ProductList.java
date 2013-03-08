package org.domain.seammysql.session;

import org.domain.seammysql.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("productList")
public class ProductList extends EntityQuery<Product> {

	private static final String EJBQL = "select product from Product product";

	private static final String[] RESTRICTIONS = {
			"lower(product.id) like lower(concat(#{productList.product.id},'%'))",
			"lower(product.name) like lower(concat(#{productList.product.name},'%'))",
			"lower(product.type) like lower(concat(#{productList.product.type},'%'))", };

	private Product product = new Product();

	public ProductList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Product getProduct() {
		return product;
	}
}
