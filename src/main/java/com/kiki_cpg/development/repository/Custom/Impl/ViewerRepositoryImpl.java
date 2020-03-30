package com.kiki_cpg.development.repository.Custom.Impl;

import org.springframework.stereotype.Repository;

import com.kiki_cpg.development.entity.ViewerPolicies;
import com.kiki_cpg.development.repository.Custom.ViewerRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ViewerRepositoryImpl implements ViewerRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<ViewerPolicies> getFilteredViewerPoliciesForCurPackage(int viewerID) {
        Query query = entityManager.createNativeQuery("select vp.* from viewer_packages vpk \n" +
                "left join viewer_policies vp on vp.viewer_package_id = vpk.RowID\n" +
                "where vpk.status=1 and vp.viewer_id=:viewerID and vp.status=1 order by vp.end_date desc", ViewerPolicies.class);

        query.setParameter("viewerID", viewerID);
        return query.getResultList();

    }
}
