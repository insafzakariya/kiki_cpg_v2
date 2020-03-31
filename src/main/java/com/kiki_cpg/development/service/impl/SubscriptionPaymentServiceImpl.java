package com.kiki_cpg.development.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kiki_cpg.development.dto.SubscriptionPaymentDto;
import com.kiki_cpg.development.service.SubscriptionPaymentService;

@Service
@Transactional
public class SubscriptionPaymentServiceImpl implements SubscriptionPaymentService {

	@Override
	public SubscriptionPaymentDto getSubscriptionPaymentByToken(String paymentToken) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
