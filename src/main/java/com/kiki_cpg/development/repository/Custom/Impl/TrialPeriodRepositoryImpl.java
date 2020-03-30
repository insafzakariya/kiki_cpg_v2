package com.kiki_cpg.development.repository.Custom.Impl;


import org.springframework.stereotype.Repository;

import com.kiki_cpg.development.entity.ViewerTrialPeriodAssociation;
import com.kiki_cpg.development.repository.Custom.TrialPeriodRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class TrialPeriodRepositoryImpl implements TrialPeriodRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public ViewerTrialPeriodAssociation getOnGoingViewerTrialPeriodAssociation(Integer viewerId) {
        Query query = entityManager.createNativeQuery("select * from viewer_trial_period_association mr where mr.viewerId = :viewerId and mr.isOnGoing = 1", ViewerTrialPeriodAssociation.class);
        query.setParameter("viewerId", viewerId);
        // query.setLockMode("c", LockMode.READ);
        try {
            return (ViewerTrialPeriodAssociation) query.getResultList().get(0);
        } catch (IndexOutOfBoundsException i) {
            return null;
        }

    }

    @Override
    public boolean updateViewerTrialPeriodAssociationOnGoingStatus(Integer viewerId, boolean isOngoing) {
        Query query = entityManager.createNativeQuery("update viewer_trial_period_association vt set vt.isOnGoing = :isOnGoing where vt.viewerId = :viewerId");
        query.setParameter("viewerId", viewerId);
        query.setParameter("isOnGoing", isOngoing);
        int result = query.getFirstResult();
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }
}
