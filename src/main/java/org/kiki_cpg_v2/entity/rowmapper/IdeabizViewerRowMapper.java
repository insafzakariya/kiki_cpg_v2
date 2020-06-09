package org.kiki_cpg_v2.entity.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.kiki_cpg_v2.entity.custom.IdeabizViewerCusrtomEntity;
import org.springframework.jdbc.core.RowMapper;

public class IdeabizViewerRowMapper implements RowMapper<IdeabizViewerCusrtomEntity>{

	@Override
	public IdeabizViewerCusrtomEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
		IdeabizViewerCusrtomEntity entity = new IdeabizViewerCusrtomEntity();
		entity.setDays(rs.getInt("subscribed_days"));
		entity.setExpireDate(rs.getString("policy_expire_at"));
		entity.setIdeabizId(rs.getInt("id"));
		entity.setMobile(rs.getString("MobileNumber"));
		entity.setSubscriprion(rs.getInt("subscribe"));
		entity.setViewerId(rs.getInt("ViewerID"));
		return entity;
	}

}
