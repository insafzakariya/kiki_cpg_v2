package com.kiki_cpg.development.repository.Custom.Impl;


import org.springframework.stereotype.Repository;

import com.kiki_cpg.development.repository.Custom.SubscriptionRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Repository
public class SubscriptionRepositoryImpl implements SubscriptionRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

}
