package org.kiki_cpg_v2.service.impl;

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
public class PaymentMethodServiceImpl implements PaymentMethodService{

	@Autowired
	private PaymentMethodRepository paymentMethodRepository;
	
	@Override
	public List<PaymantPlanDto> getPaymentPlan(Integer paymentMethodId) {
		PaymentMethodEntity paymentMethodEntity = paymentMethodRepository.findById(paymentMethodId).get();
		System.out.println(paymentMethodEntity == null);
		List<PaymantPlanDto> paymantPlanDtos = new ArrayList<PaymantPlanDto>();
		paymentMethodEntity.getPaymentMethodPlanEntities().forEach(e -> {
			paymantPlanDtos.add(getPaymantPlanDto(e, paymentMethodId));
		});
		return paymantPlanDtos;
	}

	private PaymantPlanDto getPaymantPlanDto(PaymentMethodPlanEntity e, Integer paymentMethodId) {
		PaymantPlanDto dto = new PaymantPlanDto();
		dto.setName(e.getName());
		dto.setValue("Rs. " + e.getValue() + " + Tax per day");
		dto.setOffer(e.getOffer());
		dto.setPaymentMethodId(paymentMethodId);
		dto.setDay(e.getDays());

		return dto;
	}

	@Override
	public Double getPaymentPlanAmount(Integer day, Integer paymentMethodId) {
		PaymentMethodEntity paymentMethodEntity = paymentMethodRepository.findById(paymentMethodId).get();
		System.out.println(paymentMethodEntity == null);
		for (PaymentMethodPlanEntity entity : paymentMethodEntity.getPaymentMethodPlanEntities()) {
			if(entity.getDays() == day) {
				return entity.getValue();
			}
		}
		
		return -1.0;
		
	}

}
