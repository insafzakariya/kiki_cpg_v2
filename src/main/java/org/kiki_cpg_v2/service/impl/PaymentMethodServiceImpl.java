package org.kiki_cpg_v2.service.impl;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import org.kiki_cpg_v2.dto.PaymantPlanDto;
import org.kiki_cpg_v2.entity.PaymentMethodEntity;
import org.kiki_cpg_v2.entity.PaymentMethodPlanEntity;
import org.kiki_cpg_v2.repository.PaymentMethodRepository;
import org.kiki_cpg_v2.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

	@Autowired
	private PaymentMethodRepository paymentMethodRepository;

	@Override
	public List<PaymantPlanDto> getPaymentPlan(Integer paymentMethodId) {
		PaymentMethodEntity paymentMethodEntity = paymentMethodRepository.findById(paymentMethodId).get();
		System.out.println(paymentMethodEntity == null);
		List<PaymantPlanDto> paymantPlanDtos = new ArrayList<PaymantPlanDto>();
		paymentMethodEntity.getPaymentMethodPlanEntities().forEach(e -> {
			if (e.getStatus() == 1) {
				paymantPlanDtos.add(getPaymantPlanDto(e, paymentMethodId));
			}
		});
		return paymantPlanDtos;
	}

	private PaymantPlanDto getPaymantPlanDto(PaymentMethodPlanEntity e, Integer paymentMethodId) {
		PaymantPlanDto dto = new PaymantPlanDto();
		dto.setId(e.getId());
		dto.setName(e.getName());
		dto.setValue("Rs. " + e.getValue() + " + Tax");
		dto.setAmount(e.getValue());
		dto.setOffer(e.getOffer());
		dto.setPaymentMethodId(paymentMethodId);
		dto.setDay(e.getDays());
		dto.setTrialDays(e.getTrialDays());
		dto.setTrialText(e.getTrialText());

		System.out.println(dto.getAmount());

		return dto;
	}

	@Override
	public Double getPaymentPlanAmount(Integer day, Integer paymentMethodId) {

		System.out.println(paymentMethodId);
		System.out.println(day);
		PaymentMethodEntity paymentMethodEntity = paymentMethodRepository.findById(paymentMethodId).get();
		System.out.println(paymentMethodEntity == null);
		for (PaymentMethodPlanEntity entity : paymentMethodEntity.getPaymentMethodPlanEntities()) {
			System.out.println(entity.getDays().equals(day));
			if (entity.getDays().equals(day)) {
				return entity.getValue();
			}
		}

		return -1.0;

	}

}
