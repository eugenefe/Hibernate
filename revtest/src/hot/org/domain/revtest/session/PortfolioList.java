package org.domain.revtest.session;

import org.domain.revtest.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("portfolioList")
public class PortfolioList extends EntityQuery<Portfolio> {

	private static final String EJBQL = "select portfolio from Portfolio portfolio";

	private static final String[] RESTRICTIONS = {
			"lower(portfolio.id) like lower(concat(#{portfolioList.portfolio.id},'%'))",
			"lower(portfolio.name) like lower(concat(#{portfolioList.portfolio.name},'%'))",
			"lower(portfolio.parentPortId) like lower(concat(#{portfolioList.portfolio.parentPortId},'%'))", };

	private Portfolio portfolio = new Portfolio();

	public PortfolioList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Portfolio getPortfolio() {
		return portfolio;
	}
}
