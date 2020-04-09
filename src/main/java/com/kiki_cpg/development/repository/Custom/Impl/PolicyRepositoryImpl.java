package com.kiki_cpg.development.repository.Custom.Impl;

import org.springframework.stereotype.Repository;

import com.kiki_cpg.development.entity.Policies;
import com.kiki_cpg.development.repository.Custom.PolicyRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.Date;
import java.util.List;

@Repository
public class PolicyRepositoryImpl implements PolicyRepositoryCustom {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Policies> getPoliciesByPackageID(int newPackageID) {
		Query query = entityManager.createNativeQuery("SELECT po.* FROM policies po, package_policies pp "
				+ "WHERE po.PolicyID = pp.PolicyID AND pp.PackageID =:newPackageID", Policies.class);
		query.setParameter("newPackageID", newPackageID);
		return query.getResultList();
	}

	@Override
	public List<Policies> getPoliciesByPackageIDAndValidDate(int newPackageID) {
		try {
			Query query = entityManager.createNativeQuery(
					"SELECT pp.* FROM package_policies pp , packages p, policies ps where p.PackageID=pp.PackageID and ps.PolicyID=pp.PolicyID and p.PackageID=:newPackageID and ps.ValidFrom <=:todayDate and ps.ValidTo >=:todayDate",
					Policies.class);
			query.setParameter("newPackageID", newPackageID);
			query.setParameter("todayDate", new Date());
			return query.getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// TODO Auto-generated method stub

		return null;
	}

}
