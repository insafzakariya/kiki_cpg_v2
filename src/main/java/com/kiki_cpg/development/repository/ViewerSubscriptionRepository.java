package com.kiki_cpg.development.repository;

import com.kiki_cpg.development.entity.ViewerSubscription;
import com.kiki_cpg.development.repository.Custom.ViewerSubscriptionRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewerSubscriptionRepository extends JpaRepository<ViewerSubscription,Integer>, ViewerSubscriptionRepositoryCustom {
//List<ViewerSubscription>findBySubscriptionType(SubscriptionType mobitelAddToBill);

}
