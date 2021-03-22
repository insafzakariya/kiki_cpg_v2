/**
 * Mar 19, 2021
 * AirtelService.java
 */
package org.kiki_cpg_v2.service;

import org.kiki_cpg_v2.dto.PaymantPlanDto;
import org.kiki_cpg_v2.dto.PaymentRefDto;
import org.kiki_cpg_v2.dto.request.TransactionBeginDto;
import org.kiki_cpg_v2.entity.SubscriptionEntity;
import org.kiki_cpg_v2.entity.ViewerEntity;

/**
 * @author Anjana Thrishakya
 */
public interface AirtelService {

	/**
	 * @param beginDto
	 * @return
	 */
	PaymentRefDto beginSubscribe(TransactionBeginDto beginDto) throws Exception;

	/**
	 * @param viewerId
	 * @param mobile
	 * @param subscriptionEntity
	 * @return 
	 * @throws Exception
	 */
	String processTrial(Integer viewerId, String mobile, SubscriptionEntity subscriptionEntity) throws Exception;

	/**
	 * @param paymentPlanDto
	 * @param subscriptionEntity
	 * @param paymentRefDto
	 * @param viewerEntity
	 * @return
	 * @throws Exception 
	 */
	String processPayment(PaymantPlanDto paymentPlanDto, SubscriptionEntity subscriptionEntity,
			PaymentRefDto paymentRefDto, ViewerEntity viewerEntity, TransactionBeginDto beginDto) throws Exception;

}
