package org.kiki_cpg_v2.repository.custom.impl;

import java.util.List;

import org.kiki_cpg_v2.entity.custom.ViewerSubscriptionCustomEntity;
import org.kiki_cpg_v2.entity.rowmapper.ViewerSubscriptionCustomRowMapper;
import org.kiki_cpg_v2.enums.SubscriptionType;
import org.kiki_cpg_v2.repository.custom.ViewerSubscriptionCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ViewerSubscriptionCustomRepositoryImpl implements ViewerSubscriptionCustomRepository{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<ViewerSubscriptionCustomEntity> getViewerSubscriptionCustomEntityExpireBeforeToday() throws Exception {
		List<ViewerSubscriptionCustomEntity> viewerSubscriptionCustomEntities = jdbcTemplate.query("SELECT vs.Viewer, vs.SubscriptionType, v.MobileNumber, 1 as days, 1 as subscriprion FROM viewer_subscription vs inner join viewers v on vs.Viewer = v.ViewerID\r\n" + 
				"where SubscriptionType = '"+SubscriptionType.MOBITEL_ADD_TO_BILL+"'", new ViewerSubscriptionCustomRowMapper());
		return viewerSubscriptionCustomEntities;
	}

}
