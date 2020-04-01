package com.kiki_cpg.development.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiki_cpg.development.dto.SubscriptionPaymentDto;
import com.kiki_cpg.development.entity.DialogViuSubscriptionDetails;
import com.kiki_cpg.development.repository.DialogViuSubscriptionDetailsRepository;
import com.kiki_cpg.development.service.DialogService;
import com.kiki_cpg.development.util.AppUtility;

@Service
public class DialogServiceImpl implements DialogService {
	
	@Autowired
	private DialogViuSubscriptionDetailsRepository dialogViuSubscriptionDetailsRepository;
	
	@Autowired
	private AppUtility appUtility;

	@Override
	public String getViuSubscriptionMobileNo(SubscriptionPaymentDto subscriptionPaymentDto) {
		String mobile = "";
		DialogViuSubscriptionDetails dialogViuSubscriptionDetails = dialogViuSubscriptionDetailsRepository.findOneByViewerId(subscriptionPaymentDto.getViewerId());
		
		if (dialogViuSubscriptionDetails != null) {
			mobile = dialogViuSubscriptionDetails.getMobileNo();
		} else {
			mobile = subscriptionPaymentDto.getMobile();
		}

		if (mobile != null && !mobile.isEmpty() && mobile.length() >= 10) {
			mobile = appUtility.getNineDigitMobileNumber(mobile);
		} 
		
		return mobile;
	}

}
