package org.kiki_cpg_v2.service;

import org.kiki_cpg_v2.dto.SubscriptionPaymentDto;
import org.springframework.web.servlet.ModelAndView;

public interface SubscriptionPaymentService {
	
	SubscriptionPaymentDto getSubscriptionPaymentDtoByToken(String token, String type) throws Exception;

	boolean validateSubscriptionPayment(Integer subscriptionPaymentId) throws Exception;

	boolean updateStatus(Integer subscriptionPaymentId);

}