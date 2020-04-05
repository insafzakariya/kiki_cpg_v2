package com.kiki_cpg.development.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kiki_cpg.development.dto.SubscriptionPaymentDto;
import com.kiki_cpg.development.service.DialogService;
import com.kiki_cpg.development.service.SubscriptionPaymentService;

@Controller
public class MainController {

	final static Logger logger = LoggerFactory.getLogger(MainController.class);

	@Autowired
	private SubscriptionPaymentService subscriptionPaymentService;

	@Autowired
	private DialogService dialogService;

	@GetMapping("/home")
	public ModelAndView homePage(HttpServletRequest request, @RequestParam(value = "token") String paymentToken,
			@RequestParam(value = "isFreeTrial", required = false, defaultValue = "false") boolean isFreeTrial,
			@RequestParam(value = "type", required = false, defaultValue = "false") String type) throws IOException {

		logger.info("susilawebpay application started.");
		HttpSession session = request.getSession();

		try {
			SubscriptionPaymentDto subscriptionPaymentDto = subscriptionPaymentService
					.getSubscriptionPaymentByToken(paymentToken, type);

			ModelAndView view = new ModelAndView();
			view.addObject("paymentMethods", subscriptionPaymentDto.getPaymentMethods());
			session.setAttribute("paymentMethods", subscriptionPaymentDto.getPaymentMethods());
			view.addObject("ezcashPaymentAmmount", "");
			view.addObject("mCashPaymentAmmount", "");
			view.addObject("dialogViuPaymentAmmount", "");
			view.addObject("mobitelAddToBillPaymentAmount", "");
			view.addObject("ezcashPaymentType", "");
			view.addObject("mCashPaymentType", "");
			view.addObject("dialogViuPaymentType", "");
			view.addObject("mobitelAddToBillPaymentType", "");

			subscriptionPaymentDto.getPaymentMethods().forEach(e -> {
				if (e.getPaymentMethod().getPaymentMethodId() == 2 && !subscriptionPaymentDto.isMobitel()) {
					view.addObject("ezcashPaymentAmmount", "Rs." + e.getPaymentAmount() + "/=");
					view.addObject("ezcashPaymentType", e.getPaymentType());
				} else if (e.getPaymentMethod().getPaymentMethodId() == 3 && !subscriptionPaymentDto.isMobitel()) {
					view.addObject("mCashPaymentAmmount", "Rs." + e.getPaymentAmount() + "/=");
					view.addObject("mCashPaymentType", e.getPaymentType());
				} else if (e.getPaymentMethod().getPaymentMethodId() == 4 && !subscriptionPaymentDto.isMobitel()) {
					view.addObject("dialogViuPaymentType", e.getPaymentType());
					view.addObject("dialogViuPaymentAmmount", "Rs." + e.getPaymentAmount() + "/=");
					view.addObject("mobielNo", dialogService.getViuSubscriptionMobileNo(subscriptionPaymentDto));

				} else if (e.getPaymentMethod().getPaymentMethodId() == 5) {
					view.addObject("mobitelAddToBillPaymentType", e.getPaymentType());
					view.addObject("mobitelAddToBillPaymentAmount", "Rs." + e.getPaymentAmount() + "/=");

				} else if (e.getPaymentMethod().getPaymentMethodId() == 6) {
					view.addObject("isScratchCardPayment", true);
				}
			});

			view.addObject("mcashPaymentUrl", subscriptionPaymentDto.getmCashPaymentURL());
			view.addObject("subscriptionPaymentId", subscriptionPaymentDto.getSubscriptionPaymentId());

			if (isFreeTrial && subscriptionPaymentDto.isTrialVerify()) { /// this is users trial period
				view.setViewName("trialPeriod");
			} else {
				if (type.equals("new")) {
					view.setViewName("payment-home");
				} else {
					view.addObject("days", subscriptionPaymentDto.getIdeabizDays());
					view.addObject("amount", subscriptionPaymentDto.getIdeabizAmount());
					view.addObject("ideabiz_subscribed", subscriptionPaymentDto.getIdeabizSubscription());
					view.addObject("mobitel_subscribed", subscriptionPaymentDto.getMobitelSubscription());
					view.addObject("ck_subscribed", subscriptionPaymentDto.getViewerSubscription());
					if (!subscriptionPaymentDto.isAlreadySubscribed()) {
						view.setViewName("payment-home");
					} else {
						view.setViewName("already-subscribed");
					}
				}
			}

			return view;

		} catch (Exception e) {
			e.printStackTrace();
			ModelAndView mav = new ModelAndView("error");
			return mav;
		}

	}
	
	


}
