/**
 * @DaSep 9, 2020 @SubscriptionService.java
 */
package org.kiki_cpg_v2.service;

import org.kiki_cpg_v2.dto.PaymentRefDto;
import org.kiki_cpg_v2.dto.request.TransactionBeginDto;
import org.kiki_cpg_v2.entity.SubscriptionEntity;

/**
 * @author Anjana Thrishakya
 */
public interface SubscriptionService {

	/**
	 * @param transactionBeginDto
	 * @param paymentRefDto
	 * @param keells
	 */
	SubscriptionEntity getSubsctiptionEntity(TransactionBeginDto transactionBeginDto, PaymentRefDto paymentRefDto, String keells);

	/**
	 * @param transactionBeginDto
	 * @param i
	 * @param d
	 * @return
	 */
	PaymentRefDto getPaymentRefDto(TransactionBeginDto transactionBeginDto, int i, double d);

}
