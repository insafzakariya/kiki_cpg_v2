package org.kiki_cpg_v2.service.impl;

import org.kiki_cpg_v2.dto.SubscriptionPaymentDto;
import org.kiki_cpg_v2.service.SubscriptionPaymentService;
import org.kiki_cpg_v2.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class ViewServiceImpl implements ViewService{

	@Autowired
	private SubscriptionPaymentService subscriptionService;
	

	@Override
	public ModelAndView navigateHome(String paymentToken, String type) throws Exception {
		
		ModelAndView modelAndView = new ModelAndView();
		
		SubscriptionPaymentDto subscriptionPaymentDto =  subscriptionService.getSubscriptionPaymentDtoByToken(paymentToken, type);
		
		if(subscriptionPaymentDto != null) {
			modelAndView.addObject("subscriptionPaymentDto", subscriptionPaymentDto);
			if(subscriptionPaymentDto.isAlreadySubscribed()) {
				modelAndView.setViewName("already-subscribed/already-subscribed");
				modelAndView.addObject("subscriptionType", subscriptionPaymentDto.getSubscriptionType());
				modelAndView.addObject("subscriptionTypeList", subscriptionPaymentDto.getSubscriptionTypeList());
				modelAndView.addObject("lastSubscribeDay", subscriptionPaymentDto.getLastSubscribeDay());
			} else if(subscriptionPaymentDto.isTrialVerify()) {
				modelAndView.setViewName("trial/trial");
			} else {
				modelAndView.setViewName("home/home");
			}
		} else {
			modelAndView.setViewName("error/error");
			modelAndView.addObject("message", "Subscription Token Expired");
		}
		
		return modelAndView;
		
	}

}
