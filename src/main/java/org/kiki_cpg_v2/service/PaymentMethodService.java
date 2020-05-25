package org.kiki_cpg_v2.service;

import java.util.List;

import org.kiki_cpg_v2.dto.PaymantPlanDto;

public interface PaymentMethodService {

	List<PaymantPlanDto> getPaymentPlan(Integer paymentMethodId);

	Double getPaymentPlanAmount(Integer day, Integer i);

}
