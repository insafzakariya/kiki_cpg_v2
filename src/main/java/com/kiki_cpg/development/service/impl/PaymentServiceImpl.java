package com.kiki_cpg.development.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiki_cpg.development.entity.SubscriptionPayments;
import com.kiki_cpg.development.entity.Viewers;
import com.kiki_cpg.development.repository.SubscriptionPaymentRepository;
import com.kiki_cpg.development.service.PaymentService;
import com.kiki_cpg.development.service.ViewerService;

@Service
public class PaymentServiceImpl implements PaymentService{
	@Autowired
	SubscriptionPaymentRepository subPayRepo;
	
	@Autowired
	ViewerService viewerService;
	

	@Override
	public SubscriptionPayments getSubscriptionPayments(Integer subscriptionPaymentId) {
		// TODO Auto-generated method stub
		SubscriptionPayments subPay=subPayRepo.getOne(subscriptionPaymentId);
		return subPay;
	}

}
