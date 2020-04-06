package com.kiki_cpg.development.service;

import java.util.List;

import com.kiki_cpg.development.dto.SubscriptionPaymentDto;
import com.kiki_cpg.development.entity.PaymentPolicies;
import com.kiki_cpg.development.entity.SubscriptionPayments;
import com.kiki_cpg.development.entity.ViewerTrialPeriodAssociation;
import com.kiki_cpg.development.entity.Viewers;

public interface SubscriptionPaymentService {

	SubscriptionPaymentDto getSubscriptionPaymentByToken(String paymentToken, String type) throws Exception;

	SubscriptionPaymentDto getSubscriptionPaymentDto(SubscriptionPayments subscriptionPayments,
			List<PaymentPolicies> paymentPolicies, boolean isMobitel, Viewers viewer, ViewerTrialPeriodAssociation viewerTrialPeriodAssociation);

	SubscriptionPayments validatePaymentToken(String token);

}
