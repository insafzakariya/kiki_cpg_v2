package com.kiki_cpg.development.service;

import org.springframework.stereotype.Service;

import com.kiki_cpg.development.entity.SubscriptionPayments;


public interface PaymentService {

	SubscriptionPayments getSubscriptionPayments(Integer subscriptionPaymentId);

}
