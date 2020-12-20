/**
 * @author Anjana Thrishakya
 * Dec 16, 2020
 * 11:42:38 AM
 */
package org.kiki_cpg_v2.service.impl;

import org.kiki_cpg_v2.dto.PaymantPlanDto;
import org.kiki_cpg_v2.entity.PaymentMethodPlanEntity;
import org.kiki_cpg_v2.repository.PaymentMethodPlanRepository;
import org.kiki_cpg_v2.service.PaymentPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Service
@Transactional
public class PaymentPlanServiceImpl implements PaymentPlanService{
	
	@Autowired
	private PaymentMethodPlanRepository paymentMethodPlanRepository;

	@Override
	public String getServiceId(Integer methodId) throws Exception {
		PaymentMethodPlanEntity entity = paymentMethodPlanRepository.findById(methodId).get();
		if(entity != null) {
			return entity.getServiceCode();
		}
		return null;
	}

	@Override
	public PaymantPlanDto getPaymentPlan(Integer planId) {
		PaymentMethodPlanEntity entity = paymentMethodPlanRepository.findById(planId).get();
		if(entity != null) {
			return getPaymantPlanDto(entity, null);
		}
		return null;
	}
	
	@Override
	public PaymantPlanDto getPaymantPlanDto(PaymentMethodPlanEntity e, String lang) {
		PaymantPlanDto dto = new PaymantPlanDto();
		dto.setId(e.getId());

		if (lang != null && !lang.isEmpty()) {
			if (lang.equalsIgnoreCase("si")) {
				dto.setName(e.getNameSinhala());
				dto.setValue("රු . " + e.getValue() + " + බදු");
			}

			if (lang.equalsIgnoreCase("en")) {
				dto.setName(e.getName());
				dto.setValue("Rs. " + e.getValue() + " + Tax");
			}

			if (lang.equalsIgnoreCase("ta")) {
				dto.setName(e.getNameTamil());
				dto.setValue("Rs. " + e.getValue() + " + Tax");
			}
		} else {
			dto.setName(e.getName());
			dto.setValue("Rs. " + e.getValue() + " + Tax");
		}

		dto.setAmount(e.getValue());
		dto.setOffer(e.getOffer());
		dto.setPaymentMethodId(e.getPaymentMethodEntity().getId());
		dto.setDay(e.getDays());
		dto.setTrialDays(e.getTrialDays());
		dto.setTrialText(e.getTrialText());
		dto.setServiceId(e.getServiceCode());

		System.out.println(dto.getAmount());

		return dto;
	}

}
