package com.kiki_cpg.development.repository.Custom.Impl;


import org.springframework.stereotype.Repository;

import com.kiki_cpg.development.repository.Custom.MerchantAccountRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class MerchantAccountRepositoryImpl implements MerchantAccountRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public int getLastTransactionId() {
        Query query = entityManager.createNativeQuery("select max(TransactionId) from merchant_account ");
        List<Integer>resultList = query.getResultList();
        return resultList.get(0);
    }

//	@Override
//	public List<MerchantAccount> findByTransactionId(int idd) {
//		idd=45;
//		System.out.println("hit");
//		  Query query = entityManager.createNativeQuery("SELECT * FROM merchant_account m where m.TransactionId=45", MerchantAccount.class);
//	        query.setParameter("idd", idd);
//	        List<MerchantAccount> a=query.getResultList();
//	        System.out.println("hit ");
//			System.out.println("hit "+a.size());
//	        return a;
//	}
    
//
//    @Override
//    public MerchantAccount getSubcribeViwersInMobitel(Integer viewerID) {
//        Date today = new Date();
//        Query query = entityManager.createNativeQuery("select * from merchant_account m where m.IsSuccess=true and m.Date<=:today and m.ViewerId=:viewerID", MerchantAccount.class);
//        query.setParameter("today", today);
//        query.setParameter("viewerID", viewerID);
//        MerchantAccount resultList = (MerchantAccount) query.getResultList();
//        List<MerchantAccount> getCurrent = new ArrayList<>();
//        return resultList;
//    }
}
