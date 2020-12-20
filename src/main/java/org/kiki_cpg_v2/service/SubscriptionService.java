/**
 * @DaSep 9, 2020 @SubscriptionService.java
 */
package org.kiki_cpg_v2.service;

import org.kiki_cpg_v2.dto.PaymantPlanDto;
import org.kiki_cpg_v2.dto.PaymentRefDto;
import org.kiki_cpg_v2.dto.request.TransactionBeginDto;
import org.kiki_cpg_v2.entity.SubscriptionEntity;
import org.kiki_cpg_v2.entity.SubscriptionInvoiceEntity;

/**
 * @author Anjana Thrishakya
 */
public interface SubscriptionService {

	/**
	 * @param transactionBeginDto
	 * @param paymentRefDto
	 * @param type
	 */
	SubscriptionEntity getSubsctiptionEntity(TransactionBeginDto transactionBeginDto, PaymentRefDto paymentRefDto, String type) throws Exception;

	/**
	 * @param transactionBeginDto
	 * @param i
	 * @param d
	 * @return
	 */
	PaymentRefDto getPaymentRefDto(TransactionBeginDto transactionBeginDto, int days, double value);

	/**
	 * @param mobile
	 * @param transactionType
	 * @param subscriptionEntity
	 * @return
	 * @throws Exception
	 */
	SubscriptionInvoiceEntity getSubscriptionInvoiceEntity(String transactionType,
			SubscriptionEntity subscriptionEntity) throws Exception;

	/**
	 * @param viewerId
	 * @param type
	 * @return
	 */
	boolean inavtive(Integer viewerId, String type) throws Exception;

	/**
	 * @param viewerId
	 * @param type
	 * @return
	 */
	Integer generateSequenceId(Integer viewerId, String type) throws Exception;

	/**
	 * @param mobileNo
	 * @param viewerId
	 * @param paymentPlanDto
	 * @param type
	 * @return
	 * @throws Exception
	 */
	SubscriptionEntity generateSubsctiptionEntity(String mobileNo, Integer viewerId, PaymantPlanDto paymentPlanDto,
			String type) throws Exception;

	/**
	 * @param mobileNo
	 * @param dialog
	 * @return
	 */
	boolean unsubscribeAllByMobileAndType(String mobileNo, String type) throws Exception;

}
