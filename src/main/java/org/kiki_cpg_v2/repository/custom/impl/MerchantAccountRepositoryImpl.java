package org.kiki_cpg_v2.repository.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kiki_cpg_v2.repository.custom.MerchantAccountRepositoryCustom;
import org.springframework.stereotype.Repository;

@Repository
public class MerchantAccountRepositoryImpl implements MerchantAccountRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public Integer getLastTransactionId() {
		Query query = entityManager.createNativeQuery("select max(TransactionId) from merchant_account ");
		List<Integer> resultList = query.getResultList();
		return resultList.get(0);
	}

}
