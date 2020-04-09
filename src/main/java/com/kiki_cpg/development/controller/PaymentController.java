package com.kiki_cpg.development.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kiki_cpg.development.dto.PaymentTypeDto;

@Controller
public class PaymentController {
	private static final Logger logger = LoggerFactory.getLogger(IdeabizController.class);
	
	@GetMapping(value = "/paymentselection")
	public ModelAndView select_payment_type(@RequestParam(value = "subscriptionPaymentId", required = false, defaultValue = "") int subscriptionPaymentId) {

		//Double one_day_amount = paymentCalculation.getAmountByDays(1);
		//Double one_week_amount = paymentCalculation.getAmountByDays(7);

		//PaymentTypeDto pdto = new PaymentTypeDto();
		//pdto.setOne_day_amount(one_day_amount);
		//pdto.setOne_week_amount(one_week_amount);
		//return pdto;
		return null;
	}
}
