package org.kiki_cpg_v2.service;

import java.util.Date;

import org.kiki_cpg_v2.entity.MerchantAccountEntity;
import org.kiki_cpg_v2.entity.SubscriptionEntity;
import org.kiki_cpg_v2.enums.SubscriptionType;
import org.kiki_cpg_v2.enums.TransactionType;

public interface MobitelService {

	boolean processUnsubscriptionMobitel(Integer viewerid, String mobile) throws Exception;

	boolean updateViewerSubscription(Integer viewerid, SubscriptionType none, Date date, String mobile)
			throws Exception;

	String pay(String mobileNo, Integer viewerId, String activationStatus, Integer subscriptionPaymentId, Integer planId, boolean trial) throws Exception;

	/*
	 * MerchantAccountEntity getMerchantAccountEntity(int lastTransaciontId, double
	 * amount, TransactionType transactionType, Integer viewerId, boolean b) throws
	 * Exception;
	 */

	String activateDataBundle(String mobileNo, SubscriptionEntity subscriptionEntity, boolean isUpdateCronViewer,
			Integer cronId) throws Exception;

	/*
	 * boolean deactivePreviousViewersByMobile(String mobileNo, Integer viewerId,
	 * boolean isTransfer, Integer subscriptionPaymentId, Integer subscribedDays)
	 * throws Exception;
	 */

	String cronPay(SubscriptionEntity subscriptionEntity, String activationStatus,
			boolean isUpdateCronViewer, Integer cronId) throws Exception;

	String cronProceedPayment(Integer viewerId, Integer packageId, String mobileNo, Integer subscribeDays) throws Exception;

	String proceedPayment(Integer viewerId, Integer subscribedDays, String mobileNo, Integer subscriptionPaymentId,
			Integer planId, SubscriptionEntity subscriptionEntity, boolean trial) throws Exception;

}
