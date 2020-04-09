package com.kiki_cpg.development.repository.Custom.Impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.kiki_cpg.development.entity.Policies;
import com.kiki_cpg.development.entity.ViewerPolicies;
import com.kiki_cpg.development.repository.Custom.ViewerPoliciesRepositoryCustom;

@Repository
public class ViewerPoliciesRepositoryImpl implements ViewerPoliciesRepositoryCustom {
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<ViewerPolicies> getFilteredViewerPoliciesForCurPackage(int viewerID) {
		// TODO Auto-generated method stub
		try {
			Query query = entityManager.createNativeQuery(
					" SELECT vps.* FROM viewer_packages vp, viewer_policies vps where vp.RowID=vps.viewer_package_id and vp.ViewerID=:viewerID and vp.Status=1 order by vp.ModifiedOn desc",
					ViewerPolicies.class);
			query.setParameter("viewerID", viewerID);
			query.setMaxResults(1);
			return query.getResultList();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}

}
