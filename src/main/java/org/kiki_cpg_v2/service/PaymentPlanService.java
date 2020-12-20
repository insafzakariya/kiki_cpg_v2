/**
 * @author Anjana Thrishakya
 * Dec 16, 2020
 * 11:41:56 AM
 */
package org.kiki_cpg_v2.service;

import org.kiki_cpg_v2.dto.PaymantPlanDto;
import org.kiki_cpg_v2.entity.PaymentMethodPlanEntity;

/**
 *
 */
public interface PaymentPlanService {

	/**
	 * @param methodId
	 * @return
	 */
	String getServiceId(Integer methodId) throws Exception;

	/**
	 * @param planId
	 * @return
	 */
	PaymantPlanDto getPaymentPlan(Integer planId);

	/**
	 * @param e
	 * @param lang
	 * @return
	 */
	PaymantPlanDto getPaymantPlanDto(PaymentMethodPlanEntity e, String lang);

}
