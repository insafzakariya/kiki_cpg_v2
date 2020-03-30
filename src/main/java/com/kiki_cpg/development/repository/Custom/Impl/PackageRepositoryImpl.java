package com.kiki_cpg.development.repository.Custom.Impl;


import org.springframework.stereotype.Repository;

import com.kiki_cpg.development.entity.Packages;
import com.kiki_cpg.development.repository.Custom.PackageRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Repository
public class PackageRepositoryImpl implements PackageRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Packages> getPackageById(int packageId) {
        Query query=entityManager.createNativeQuery("SELECT * FROM packages where PackageID=:packageId and ActivityStartDate <=:todayDate  and ActivityEndDate >=:todayDate",Packages.class);
//
        query.setParameter("packageId", packageId);
        query.setParameter("todayDate", new Date());
        return query.getResultList();
    }
}
