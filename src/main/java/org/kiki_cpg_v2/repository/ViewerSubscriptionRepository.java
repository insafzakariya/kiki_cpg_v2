package org.kiki_cpg_v2.repository;

import java.util.List;

import org.kiki_cpg_v2.entity.ViewerSubscriptionEntity;
import org.kiki_cpg_v2.entity.custom.ViewerSubscriptionCustomEntity;
import org.kiki_cpg_v2.enums.SubscriptionType;
import org.kiki_cpg_v2.repository.custom.ViewerSubscriptionCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewerSubscriptionRepository extends JpaRepository<ViewerSubscriptionEntity, Integer>, ViewerSubscriptionCustomRepository{

	ViewerSubscriptionEntity findOneBySubscriptionTypeAndViewers(SubscriptionType mobitelAddToBill, Integer viewerID);

	ViewerSubscriptionEntity findOneByViewers(Integer viewerid);

	ViewerSubscriptionEntity findOneByViewersAndSubscriptionType(Integer viewerId, SubscriptionType mobitelAddToBill);

	

}
