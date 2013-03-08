package org.domain.revtest.session;

import org.domain.revtest.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("dealerList")
public class DealerList extends EntityQuery<Dealer> {

	private static final String EJBQL = "select dealer from Dealer dealer";

	private static final String[] RESTRICTIONS = {
			"lower(dealer.id) like lower(concat(#{dealerList.dealer.id},'%'))",
			"lower(dealer.name) like lower(concat(#{dealerList.dealer.name},'%'))",
			"lower(dealer.departmetId) like lower(concat(#{dealerList.dealer.departmetId},'%'))", };

	private Dealer dealer = new Dealer();

	public DealerList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Dealer getDealer() {
		return dealer;
	}
}
