package org.kiki_cpg_v2.repository;

import org.kiki_cpg_v2.entity.ViewerSubscriptionEntity;
import org.kiki_cpg_v2.enums.SubscriptionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewerSubscriptionRepository extends JpaRepository<ViewerSubscriptionEntity, Integer>{

	ViewerSubscriptionEntity findOneBySubscriptionTypeAndViewers(SubscriptionType mobitelAddToBill, Integer viewerID);

	ViewerSubscriptionEntity findOneByViewers(Integer viewerid);

}
