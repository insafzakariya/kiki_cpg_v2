/**
 * @author Anjana Thrishakya
 * Nov 2, 2020
 * 4:54:07 PM
 */
package org.kiki_cpg_v2.service;

import java.util.HashMap;

import org.kiki_cpg_v2.dto.ApplePayDto;
import org.kiki_cpg_v2.dto.PaymentRefDto;
import org.kiki_cpg_v2.dto.request.TransactionBeginDto;
import org.kiki_cpg_v2.entity.PaymentMethodPlanEntity;
import org.kiki_cpg_v2.entity.SubscriptionEntity;
import org.kiki_cpg_v2.entity.SubscriptionInvoiceEntity;
import org.kiki_cpg_v2.entity.ViewerEntity;

/**
 *
 */
public interface AppleService {

	/**
	 * @param applePayDto
	 * @return
	 */
	PaymentRefDto pay(ApplePayDto applePayDto) throws Exception;

	/**
	 * @param applePayDto
	 * @param paymentMethodPlanEntity
	 * @param viewerEntity
	 * @return
	 * @throws Exception
	 */
	SubscriptionEntity getSubsctiptionEntity(ApplePayDto applePayDto, PaymentMethodPlanEntity paymentMethodPlanEntity,
			ViewerEntity viewerEntity) throws Exception;

	/**
	 * @param applePayDto
	 * @param paymentMethodPlanEntity
	 * @param viewerEntity
	 * @param subscriptionEntity
	 * @return
	 */
	SubscriptionInvoiceEntity getSubscriptionInvoiceEntity(ApplePayDto applePayDto,
			PaymentMethodPlanEntity paymentMethodPlanEntity, ViewerEntity viewerEntity,
			SubscriptionEntity subscriptionEntity);

	/**
	 * @param viewerId
	 * @return
	 * @throws Exception
	 */
	boolean checkSubsciption(Integer viewerId) throws Exception;

	/**
	 * @param data
	 * @return
	 */
	void callback(HashMap<String, Object> data);

}
