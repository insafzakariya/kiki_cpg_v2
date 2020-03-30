package com.kiki_cpg.development.repository.Custom;


import com.kiki_cpg.development.entity.ViewerSubscription;
import com.kiki_cpg.development.enums.SubscriptionType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ViewerSubscriptionRepositoryCustom  {
    List<ViewerSubscription> getViewersByViewerTypeAndSubscriptionStatus(SubscriptionType mobitelAddToBill);


    List<ViewerSubscription> getUsersWhoDidNotUpdateByTaskScheduler(SubscriptionType mobitelAddToBill);
}
