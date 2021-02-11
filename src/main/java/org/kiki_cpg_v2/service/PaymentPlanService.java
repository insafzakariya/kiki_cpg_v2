/**
 * @DaJan 4, 2021 @PaymentPlanService.java
 */
package org.kiki_cpg_v2.service;

import org.kiki_cpg_v2.dto.PaymantPlanDto;
import org.kiki_cpg_v2.entity.PaymentMethodPlanEntity;

/**
 * @author Anjana Thrishakya
 */
public interface PaymentPlanService {

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
