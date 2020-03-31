package com.kiki_cpg.development.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kiki_cpg.development.dto.SubscriptionPaymentDto;
import com.kiki_cpg.development.service.SubscriptionPaymentService;

@Controller
public class MainController {

	final static Logger logger = LoggerFactory.getLogger(MainController.class);

	@Autowired
	private SubscriptionPaymentService subscriptionPaymentService;

	@GetMapping("/home")
	public ModelAndView homePage(HttpServletRequest request, @RequestParam(value = "token") String paymentToken,
			@RequestParam(value = "isFreeTrial", required = false, defaultValue = "false") boolean isFreeTrial,
			@RequestParam(value = "type", required = false, defaultValue = "false") String type) throws IOException {

		logger.info("susilawebpay application started.");
		HttpSession session = request.getSession();

		try {
			SubscriptionPaymentDto subscriptionPaymentDto = subscriptionPaymentService
					.getSubscriptionPaymentByToken(paymentToken);

			if (subscriptionPaymentDto == null) {
				ModelAndView mav = new ModelAndView("error");
				return mav;
			}
			
			

			ModelAndView mav = new ModelAndView("payment-home");

			return mav;
		} catch (Exception e) {
			ModelAndView mav = new ModelAndView("error");
			return mav;
		}

	}

}
