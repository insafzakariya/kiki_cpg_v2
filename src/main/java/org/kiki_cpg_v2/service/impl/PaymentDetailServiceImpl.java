/**
 * @DaAug 21, 2020 @PaymentDetailServiceImpl.java
 */
package org.kiki_cpg_v2.service.impl;

import java.util.Date;

import org.kiki_cpg_v2.entity.PaymentDetailsEntity;
import org.kiki_cpg_v2.repository.PaymentDetailsRepository;
import org.kiki_cpg_v2.service.PaymentDetailService;
import org.kiki_cpg_v2.util.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Anjana Thrishakya
 */
@Service
public class PaymentDetailServiceImpl implements PaymentDetailService{
	
	@Autowired
	private PaymentDetailsRepository paymentDetailsRepository;

	@Override
	public PaymentDetailsEntity save(Double amount, Integer days, Date endDate, Integer refNo, String type) {
		PaymentDetailsEntity entity = new PaymentDetailsEntity();
		
		entity.setAmount(amount);
		entity.setCreateDate(new Date());
		entity.setDays(days);
		entity.setEndDate(endDate);
		entity.setRefNo(refNo);
		entity.setStartDate(new Date());
		entity.setStatus(AppConstant.ACTIVE);
		entity.setType(type);
	
		return paymentDetailsRepository.save(entity);
	}
	
}
