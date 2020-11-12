package org.kiki_cpg_v2.service;

import java.util.List;

import org.kiki_cpg_v2.dto.PaymantPlanDto;
import org.kiki_cpg_v2.dto.request.PaymantMethodDto;
import org.kiki_cpg_v2.entity.PaymentMethodEntity;

public interface PaymentMethodService {

	List<PaymantPlanDto> getPaymentPlan(Integer paymentMethodId, String lang);

	Double getPaymentPlanAmount(Integer day, Integer i);

	/**
	 * @return
	 */
	List<PaymantMethodDto> getActivePaymentMethodes();

	/**
	 * @param e
	 * @return
	 */
	PaymantMethodDto getPaymantMethodDto(PaymentMethodEntity e);

	/**
	 * @param paymentMethodId
	 * @param lang
	 * @return
	 */
	

}
