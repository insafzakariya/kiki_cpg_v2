package org.kiki_cpg_v2.entity.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.kiki_cpg_v2.entity.custom.ViewerSubscriptionCustomEntity;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

public class ViewerSubscriptionCustomRowMapper implements RowMapper<ViewerSubscriptionCustomEntity> {

	@Override
	public ViewerSubscriptionCustomEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
		ViewerSubscriptionCustomEntity entity = new ViewerSubscriptionCustomEntity();
		entity.setViewerId(rs.getInt("Viewer"));
		entity.setMobile(rs.getString("MobileNumber"));
		entity.setSubscriprion(rs.getInt("subscriprion"));
		entity.setDays(rs.getInt("days"));
		return entity;
	}

}
