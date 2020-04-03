package com.kiki_cpg.development.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kiki_cpg.development.entity.ViewerSubscription;
import com.kiki_cpg.development.enums.SubscriptionType;
import com.kiki_cpg.development.repository.Custom.SubscriptionRepositoryCustom;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<ViewerSubscription, Integer> , SubscriptionRepositoryCustom {

	ViewerSubscription findOneBySubscriptionTypeAndViewers(SubscriptionType mobitelAddToBill, Integer viewerID);

}
