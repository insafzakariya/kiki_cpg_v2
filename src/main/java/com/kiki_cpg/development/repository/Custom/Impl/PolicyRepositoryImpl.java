package com.kiki_cpg.development.repository.Custom.Impl;

import org.springframework.stereotype.Repository;

import com.kiki_cpg.development.entity.Policies;
import com.kiki_cpg.development.repository.Custom.PolicyRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class PolicyRepositoryImpl implements PolicyRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Policies> getPoliciesByPackageID(int newPackageID) {
        Query query=entityManager.createNativeQuery("SELECT po.* FROM policies po, package_policies pp " +
                "WHERE po.PolicyID = pp.PolicyID AND pp.PackageID =:newPackageID", Policies.class);
        query.setParameter("newPackageID", newPackageID);
        return query.getResultList();
    }
}
