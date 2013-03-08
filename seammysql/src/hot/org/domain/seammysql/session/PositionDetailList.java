package org.domain.seammysql.session;

import org.domain.seammysql.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("positionDetailList")
public class PositionDetailList extends EntityQuery<PositionDetail> {

	private static final String EJBQL = "select positionDetail from PositionDetail positionDetail";

	private static final String[] RESTRICTIONS = {
			"lower(positionDetail.id.bssd) like lower(concat(#{positionDetailList.positionDetail.id.bssd},'%'))",
			"lower(positionDetail.id.positionId) like lower(concat(#{positionDetailList.positionDetail.id.positionId},'%'))", };

	private PositionDetail positionDetail;

	public PositionDetailList() {
		positionDetail = new PositionDetail();
		positionDetail.setId(new PositionDetailId());
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public PositionDetail getPositionDetail() {
		return positionDetail;
	}
}
