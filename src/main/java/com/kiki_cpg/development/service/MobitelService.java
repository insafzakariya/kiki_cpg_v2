package com.kiki_cpg.development.service;

import com.kiki_cpg.development.dto.SubscriptionPaymentDto;
import com.kiki_cpg.development.entity.MerchantAccount;
import com.kiki_cpg.development.entity.Viewers;
import com.kiki_cpg.development.enums.TransactionType;

public interface MobitelService {

	SubscriptionPaymentDto getSubscriptionPaymentDto(int paymentMethodId, int subscriptionPaymentId,
			String numberString, boolean isTrial, Integer viewerId) throws Exception;

	String activateDataBundle(String mobileNo, Integer viewerId, String activationStatus);

	MerchantAccount getMerchantAccount(int lastTransaciontId, double amount, TransactionType transactionType,
			Integer viewerId, boolean isSuccess);

	String pay(String mobileNo, Integer viewerId, String activationStatus,
			SubscriptionPaymentDto subscriptionPaymentDto);

	int proceedPayment(Integer viewerId, Integer subscribed_days, String mobileNo,
			SubscriptionPaymentDto subscriptionPaymentDto);

	boolean processUnsubscribe(SubscriptionPaymentDto subscriptionPayment);

}
