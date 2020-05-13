package com.kiki_cpg.development.service;

import java.util.Date;
import java.util.List;

import com.kiki_cpg.development.dto.NavigationDto;
import com.kiki_cpg.development.dto.PackageDto;
import com.kiki_cpg.development.dto.SubscriptionPaymentDto;
import com.kiki_cpg.development.entity.PaymentPolicies;
import com.kiki_cpg.development.entity.SubscriptionPayments;
import com.kiki_cpg.development.entity.ViewerTrialPeriodAssociation;
import com.kiki_cpg.development.entity.Viewers;
import com.kiki_cpg.development.enums.SubscriptionType;

public interface SubscriptionPaymentService {

	SubscriptionPaymentDto getSubscriptionPaymentByToken(String paymentToken, String type) throws Exception;

	SubscriptionPaymentDto getSubscriptionPaymentDto(SubscriptionPayments subscriptionPayments,
			List<PaymentPolicies> paymentPolicies, boolean isMobitel, Viewers viewer, ViewerTrialPeriodAssociation viewerTrialPeriodAssociation);

	SubscriptionPaymentDto validatePaymentToken(String token);

	boolean isValidateById(Integer subscriptionPaymentId);

	List<PackageDto> getPaymentPlan(Integer paymentMethodId);

	SubscriptionPaymentDto getSubscriptionPaymentByTokenRest(String paymentToken, String type) throws Exception;

	boolean updateViewerSubscription(Integer viewerId, SubscriptionType mobitelAddToBill, Date date, String mobileNo);

	boolean updateStatus(Integer subscriptionPaymentId);


}
