package com.kiki_cpg.development.repository.Custom.Impl;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.kiki_cpg.development.repository.Custom.PackagePolicyRepositoryCustom;

import java.util.Date;
import java.util.List;

public class PackagePolicyRepositoryImpl implements PackagePolicyRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List getPoliciesByPackageID(int packageId) {

        List result = null;

        try {

            // String hql = "from Tokens as t left join t.ViewerTokens as vt where t.tokenHash= :tokenHash and t.status= :status and t.type= :type and t.expireDate > :currDate";


            Query query = entityManager.createNativeQuery("SELECT pp FROM package_policies pp join pp.packages p join pp.policy pl where p.packageId = :packageId and pl.validFrom <= :todayDate and pl.validTo >= :todayDate");
            //vp.startDate <= :todayDate and vp.endDate >= :todayDate and vp.status=1
            //pp.validFrom <= :todayDate and pp.validTo >= :todayDate and pp.status=1
            //cp.status=1
            //e.startDate <= :todayDate and e.endDate >= :todayDate and e.status=1
            //pg.status = 1

            query.setParameter("packageId", packageId);
            query.setParameter("todayDate", new Date());

            result = query.getResultList();


        } catch (Exception e) {

        } finally {
//            session.close();
            return result;
        }
    }
}
