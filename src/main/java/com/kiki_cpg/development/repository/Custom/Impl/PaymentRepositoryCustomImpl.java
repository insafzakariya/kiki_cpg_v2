package com.kiki_cpg.development.repository.Custom.Impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kiki_cpg.development.entity.PaymentPolicies;
import com.kiki_cpg.development.repository.Custom.PaymentRepositoryCustom;

@Repository
public class PaymentRepositoryCustomImpl implements PaymentRepositoryCustom{
	
	@Autowired
	private EntityManager entityManager;

	@Override
	public List<PaymentPolicies> getPaymentOptions(Integer packageId) {
		@SuppressWarnings("unchecked")
		List<PaymentPolicies> paymentPolicies = entityManager.createQuery("SELECT pp FROM PaymentMethods pm join pm.paymentPolicies pp join pp.policy pl join pl.packagePolicies pkp join pkp.packages pk where pk.packageId= :packageId and pm.status=1 and pp.status=1 and pl.validFrom <= :todayDate and pl.validTo >= :todayDate and pl.status=1 and pk.status=1 and pk.activityStartDate <= :todayDate and pk.activityEndDate >= :todayDate")
				.setParameter("packageId", packageId)
				.setParameter("todayDate", new Date())
				.getResultList();
		return paymentPolicies;
	}

}
