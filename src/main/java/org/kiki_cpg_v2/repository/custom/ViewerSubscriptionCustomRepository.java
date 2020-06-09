package org.kiki_cpg_v2.repository.custom;

import java.util.List;

import org.kiki_cpg_v2.entity.custom.ViewerSubscriptionCustomEntity;

public interface ViewerSubscriptionCustomRepository {

	List<ViewerSubscriptionCustomEntity> getViewerSubscriptionCustomEntityExpireBeforeToday() throws Exception;
	
}
