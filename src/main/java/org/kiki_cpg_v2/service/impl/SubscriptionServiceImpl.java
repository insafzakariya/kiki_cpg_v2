/**
 * @DaSep 9, 2020 @SubscriptionServiceImpl.java
 */
package org.kiki_cpg_v2.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.kiki_cpg_v2.dto.PaymentRefDto;
import org.kiki_cpg_v2.dto.request.TransactionBeginDto;
import org.kiki_cpg_v2.entity.PaymentMethodPlanEntity;
import org.kiki_cpg_v2.entity.SubscriptionEntity;
import org.kiki_cpg_v2.repository.PaymentMethodPlanRepository;
import org.kiki_cpg_v2.service.SubscriptionService;
import org.kiki_cpg_v2.util.AppConstant;
import org.kiki_cpg_v2.util.AppUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Anjana Thrishakya
 */
@Service
public class SubscriptionServiceImpl implements SubscriptionService{
	
	@Autowired
	private AppUtility appUtility;

	@Autowired
	private PaymentMethodPlanRepository paymentMethodPlanRepository;
	
	@Override
	public SubscriptionEntity getSubsctiptionEntity(TransactionBeginDto transactionBeginDto,
			PaymentRefDto paymentRefDto, String type) {
		SubscriptionEntity entity = new SubscriptionEntity();
		entity.setAmount(paymentRefDto.getAmount());
		entity.setCreateDate(new Date());
		entity.setMobile("+94" + appUtility.getNineDigitMobileNumber(transactionBeginDto.getMobileNo()));
		entity.setPaymentPlan(transactionBeginDto.getPlanId());
		entity.setStatus(AppConstant.ACTIVE);
		entity.setSubscribedDays(paymentRefDto.getDays());
		entity.setType(type);
		entity.setViewerId(transactionBeginDto.getViewerId());
		
		return entity;
	}

	@Override
	public PaymentRefDto getPaymentRefDto(TransactionBeginDto transactionBeginDto, int days, double value) {
		PaymentRefDto dto = new PaymentRefDto();
		PaymentMethodPlanEntity entity = paymentMethodPlanRepository.findById(transactionBeginDto.getPlanId()).get();
		if (days < 0 && value < 0.0) {
			
			days = entity.getDays();
			value = entity.getValue();
		}

		String ref = transactionBeginDto.getViewerId() + new SimpleDateFormat("yyyyMMddHHmmssZ").format(new Date());
		ref.replace("+", "");
		dto.setReferanceNo(ref);
		dto.setTransactionUUID(UUID.randomUUID().toString());
		dto.setAmount(value);
		dto.setDays(days);
		dto.setServiceCode(entity.getServiceCode());

		String frequency = AppUtility.getHnbFrequency(days);
		dto.setFrequency(frequency);

		dto.setDate(new SimpleDateFormat("yyyyMMdd").format(appUtility.getbeforeDay(30, new Date())));

		return dto;
	}

}
