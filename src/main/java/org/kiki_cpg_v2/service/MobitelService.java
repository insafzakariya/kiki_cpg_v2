package org.kiki_cpg_v2.service;

import java.util.Date;

import org.kiki_cpg_v2.entity.MerchantAccountEntity;
import org.kiki_cpg_v2.enums.SubscriptionType;
import org.kiki_cpg_v2.enums.TransactionType;

public interface MobitelService {

	boolean processUnsubscriptionMobitel(Integer viewerid, String mobile);

	boolean updateViewerSubscription(Integer viewerid, SubscriptionType none, Date date, String mobile);

	String pay(String mobileNo, Integer viewerId, String activationStatus, Integer subscriptionPaymentId, Integer subscribedDays) throws Exception;

	MerchantAccountEntity getMerchantAccountEntity(int lastTransaciontId, double amount,
			TransactionType transactionType, Integer viewerId, boolean b);

	String activateDataBundle(String mobileNo, Integer viewerId, String activationStatus);

	String proceedPayment(Integer viewerId, Integer subscribedDays, String mobileNo, Integer subscriptionPaymentId);


	boolean deactivePreviousViewersByMobile(String mobileNo, Integer viewerId, boolean isTransfer,
			Integer subscriptionPaymentId, Integer subscribedDays);

}
