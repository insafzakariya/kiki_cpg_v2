package com.kiki_cpg.development.service;

import com.kiki_cpg.development.dto.SubscriptionPaymentDto;

public interface SubscriptionPaymentService {

	SubscriptionPaymentDto getSubscriptionPaymentByToken(String paymentToken) throws Exception;

}
