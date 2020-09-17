package org.kiki_cpg_v2.repository.custom.impl;

import java.util.List;

import org.kiki_cpg_v2.entity.custom.IdeabizViewerCusrtomEntity;
import org.kiki_cpg_v2.entity.rowmapper.IdeabizViewerRowMapper;
import org.kiki_cpg_v2.repository.custom.IdeabizCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class IdeabizCustomRepositoryImpl implements IdeabizCustomRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<IdeabizViewerCusrtomEntity> getIdeabizViewerCusrtomEntityExpireBeforeToday() {
		List<IdeabizViewerCusrtomEntity> ideabizViewerCusrtomEntities = jdbcTemplate.query(
				"select i.id,v.ViewerID, v.MobileNumber, i.subscribed_days, i.subscribe, i.policy_expire_at  from ideabiz i inner join viewers v on i.viwer_id = v.ViewerID where i.subscribe = 1 and (i.policy_expire_at <= now())",
				new IdeabizViewerRowMapper());
		return ideabizViewerCusrtomEntities;
	}

}
