package org.domain.revtest.session;

import org.domain.revtest.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("positionList")
public class PositionList extends EntityQuery<Position> {

	private static final String EJBQL = "select position from Position position";

	private static final String[] RESTRICTIONS = {
			"lower(position.id) like lower(concat(#{positionList.position.id},'%'))",
			"lower(position.name) like lower(concat(#{positionList.position.name},'%'))",
			"lower(position.productId) like lower(concat(#{positionList.position.productId},'%'))",
			"lower(position.dealerId) like lower(concat(#{positionList.position.dealerId},'%'))", };

	private Position position = new Position();

	public PositionList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Position getPosition() {
		return position;
	}
}
