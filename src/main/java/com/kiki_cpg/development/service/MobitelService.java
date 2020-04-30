package com.kiki_cpg.development.service;

import java.util.LinkedHashMap;

import com.kiki_cpg.development.dto.SubscriptionPaymentDto;

public interface MobitelService {

	SubscriptionPaymentDto getSubscriptionPaymentDto(int paymentMethodId, int subscriptionPaymentId,
			String numberString, boolean isTrial, Integer viewerId) throws Exception;



}
