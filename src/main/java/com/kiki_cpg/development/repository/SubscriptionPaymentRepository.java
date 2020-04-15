package com.kiki_cpg.development.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiki_cpg.development.entity.SubscriptionPayments;
import com.kiki_cpg.development.entity.ViewerSubscription;
import com.kiki_cpg.development.enums.SubscriptionType;

public interface SubscriptionPaymentRepository extends JpaRepository<SubscriptionPayments, Integer> {

	SubscriptionPayments findOneByTokenHashAndCreatedDateLessThanEqualAndExpireDateGreaterThanEqualAndStatus(
			String tokenHash, Date createDate, Date expireDate, Integer status);

	SubscriptionPayments findByTokenHash(String token);

	SubscriptionPayments findOneBySubscriptionPaymentIDAndCreatedDateLessThanEqualAndExpireDateGreaterThanEqualAndStatus(
			Integer subscriptionPaymentId, Date createdDate, Date expireDate, Integer i);



}
