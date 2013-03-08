package org.domain.seammysql.session;

import org.domain.seammysql.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("baseDateList")
public class BaseDateList extends EntityQuery<BaseDate> {

	private static final String EJBQL = "select baseDate from BaseDate baseDate";

	private static final String[] RESTRICTIONS = {
			"lower(baseDate.bssd) like lower(concat(#{baseDateList.baseDate.bssd},'%'))",
			"lower(baseDate.prevBssd) like lower(concat(#{baseDateList.baseDate.prevBssd},'%'))",
			"lower(baseDate.nextBssd) like lower(concat(#{baseDateList.baseDate.nextBssd},'%'))",
			"lower(baseDate.eomBssd) like lower(concat(#{baseDateList.baseDate.eomBssd},'%'))", };

	private BaseDate baseDate = new BaseDate();

	public BaseDateList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public BaseDate getBaseDate() {
		return baseDate;
	}
}
