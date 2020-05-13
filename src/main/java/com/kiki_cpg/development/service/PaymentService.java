package com.kiki_cpg.development.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.kiki_cpg.development.entity.SubscriptionPayments;
import com.kiki_cpg.development.enums.SubscriptionType;


public interface PaymentService {

	SubscriptionPayments getSubscriptionPayments(Integer subscriptionPaymentId);

}
