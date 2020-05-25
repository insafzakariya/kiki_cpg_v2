package org.kiki_cpg_v2.service;

import org.kiki_cpg_v2.dto.SubscriptionPaymentDto;
import org.springframework.web.servlet.ModelAndView;

public interface SubscriptionService {
	
	SubscriptionPaymentDto getSubscriptionPaymentDtoByToken(String token, String type) throws Exception;

}
