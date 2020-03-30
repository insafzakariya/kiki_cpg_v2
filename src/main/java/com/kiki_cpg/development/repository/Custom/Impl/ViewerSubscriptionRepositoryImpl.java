package com.kiki_cpg.development.repository.Custom.Impl;

import com.kiki_cpg.development.entity.ViewerSubscription;
import com.kiki_cpg.development.enums.SubscriptionType;
import com.kiki_cpg.development.repository.Custom.ViewerSubscriptionRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
public class ViewerSubscriptionRepositoryImpl implements ViewerSubscriptionRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ViewerSubscription> getViewersByViewerTypeAndSubscriptionStatus(SubscriptionType mobitelAddToBill) {
        String mobitel= String.valueOf(mobitelAddToBill);
        Query query=entityManager.createNativeQuery("SELECT * FROM viewer_subscription  where SubscriptionType=:mobitel", ViewerSubscription.class);
        query.setParameter("mobitel", mobitel);
        return query.getResultList();
    }

    @Override
    public List<ViewerSubscription> getUsersWhoDidNotUpdateByTaskScheduler(SubscriptionType subscriptionType) {
        String mobitel= String.valueOf(subscriptionType);

        Calendar startDate = Calendar.getInstance();
        startDate.setTime(new Date());
        startDate.set(Calendar.MINUTE,0);
        startDate.set(Calendar.HOUR_OF_DAY,0);
        startDate.set(Calendar.MILLISECOND,0);

        Query query=entityManager.createNativeQuery("select * from viewer_subscription vs where  vs.Viewer not in(select mc.ViewerId from merchant_account mc where mc.Date >= :startDate and mc.Date <= :endDate ) and vs.SubscriptionType = :mobitel", ViewerSubscription.class);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        query.setParameter("mobitel", mobitel);
        query.setParameter("startDate", dateFormat.format(startDate.getTime()));
        query.setParameter("endDate", dateFormat.format(new Date()));
        return query.getResultList();
    }
}
