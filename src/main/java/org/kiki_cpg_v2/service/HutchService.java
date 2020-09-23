/**
 * @DaSep 2, 2020 @HutchService.java
 */
package org.kiki_cpg_v2.service;

import java.util.Map;

import org.kiki_cpg_v2.dto.PaymentRefDto;
import org.kiki_cpg_v2.dto.request.HutchSubscribeDto;
import org.kiki_cpg_v2.dto.request.TransactionBeginDto;
import org.kiki_cpg_v2.entity.SubscriptionEntity;
import org.kiki_cpg_v2.entity.SubscriptionInvoiceEntity;

/**
 * @author Anjana Thrishakya
 */
public interface HutchService {

	/**
	 * @param requestMap
	 * @return
	 * @throws Exception 
	 */
	Map<String, String> transaction(Map<String, String> requestMap) throws Exception;

	/**
	 * @param requestMap
	 * @return
	 * @throws Exception
	 */
	Map<String, String> autoRenew(Map<String, String> requestMap) throws Exception;

	/**
	 * @param requestMap
	 * @return
	 * @throws Exception
	 */
	Map<String, String> subscribe(Map<String, String> requestMap) throws Exception;

	/**
	 * @param requestMap
	 * @param subscriptionEntity
	 * @return
	 */
	SubscriptionInvoiceEntity getSubscriptionInvoiceEntity(Map<String, String> requestMap,
			SubscriptionEntity subscriptionEntity) throws Exception;

	/**
	 * @param beginDto
	 * @return
	 * @throws Exception
	 */
	PaymentRefDto beginSubscribe(TransactionBeginDto beginDto) throws Exception;

	/**
	 * @param beginDto
	 * @param paymentRefDto
	 * @return
	 * @throws Exception
	 */
	SubscriptionEntity getSubsctiptionEntity(TransactionBeginDto beginDto, PaymentRefDto paymentRefDto)
			throws Exception;

	/**
	 * @param beginDto
	 * @param subscriptionEntity
	 * @param paymentRefDto
	 * @return
	 * @throws Exception
	 */
	SubscriptionInvoiceEntity getBeginSubscriptionInvoiceEntity(TransactionBeginDto beginDto,
			SubscriptionEntity subscriptionEntity, PaymentRefDto paymentRefDto) throws Exception;

	/**
	 * @param paymentRefDto
	 * @param subscriptionEntity
	 * @param subscriptionInvoiceEntity
	 * @return
	 * @throws Exception
	 */
	HutchSubscribeDto getHutchSubscribeDto(PaymentRefDto paymentRefDto, SubscriptionEntity subscriptionEntity,
			SubscriptionInvoiceEntity subscriptionInvoiceEntity) throws Exception;

	/**
	 * @param viewerid
	 * @param mobile
	 * @return
	 */
	boolean processUnsubscription(Integer viewerid, String mobile) throws Exception;

}
