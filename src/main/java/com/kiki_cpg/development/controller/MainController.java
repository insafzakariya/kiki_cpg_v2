package com.kiki_cpg.development.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kiki_cpg.development.dto.SubscriptionPaymentDto;

import com.kiki_cpg.development.service.MobitelService;
import com.kiki_cpg.development.service.SubscriptionPaymentService;

@Controller
public class MainController {

	final static Logger logger = LoggerFactory.getLogger(MainController.class);

	@Autowired
	private SubscriptionPaymentService subscriptionPaymentService;

	@Autowired
	private MobitelService mobitelService;

	@GetMapping(value = "/thanks_ideabiz")
	public String thanks(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("thanks/thanks");
		HttpSession session = request.getSession();
		String paymentToken = (String) session.getAttribute("token");
		System.out.println(paymentToken);
		view.addObject("paymentToken", paymentToken);
		return "Thanks";
	}

	@GetMapping(value = { "/mobitel-verify" })
	public ModelAndView MObitelVerify(HttpServletRequest request,
			@RequestParam(value = "paymentMethodId", required = false, defaultValue = "0") int paymentMethodId,
			@RequestParam(value = "subscriptionPaymentId", required = false, defaultValue = "0") int subscriptionPaymentId,
			@RequestParam(value = "numberString", required = false, defaultValue = "") String numberString,
			@RequestParam(value = "isTrial", required = false, defaultValue = "false") boolean isTrial) {

		ModelAndView model = new ModelAndView();

		SubscriptionPaymentDto subscriptionPaymentDtoSesstion = (SubscriptionPaymentDto) request.getSession()
				.getAttribute("subscriptionPaymentDto");

		try {
			if (paymentMethodId == 5) {

				SubscriptionPaymentDto subscriptionPaymentDto = mobitelService.getSubscriptionPaymentDto(
						paymentMethodId, subscriptionPaymentId, numberString, isTrial,
						subscriptionPaymentDtoSesstion.getViewerId());

				model.addObject("isMobitelSubscribe", subscriptionPaymentDto.isMobitelSubscribe());
				model.addObject("IsMobitelConnection", subscriptionPaymentDto.isMobitelConnection());
				model.addObject("mobielNo", subscriptionPaymentDto.getMobile());
				model.addObject("subscriptionPaymentId", subscriptionPaymentId);

				model.setViewName("mobitel-verify/mobitel-verify");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("could not validate number exit with exception  = " + e);
			model.setViewName("error");
		}
		return model;

	}

	///// V2

	@GetMapping("/loading")
	public ModelAndView loadingPage(HttpServletRequest request, @RequestParam(value = "token") String paymentToken,
			@RequestParam(value = "isFreeTrial", required = false, defaultValue = "false") boolean isFreeTrial,
			@RequestParam(value = "type", required = false, defaultValue = "false") String type) throws IOException {

		logger.info("susilawebpay application started.");

		try {
			HttpSession session = request.getSession();
			session.setAttribute("paymentToken", paymentToken);
			session.setAttribute("isFreeTrial", isFreeTrial);
			session.setAttribute("type", type);

			ModelAndView view = new ModelAndView("home");
			return view;
		} catch (Exception e) {
			e.printStackTrace();
			ModelAndView mav = new ModelAndView("error");
			return mav;
		}

	}

	@GetMapping("/load_payment_methods")
	public ModelAndView loadPaymentMethods(HttpServletRequest request) throws IOException {

		try {
			ModelAndView view = new ModelAndView("payment/payment-home");
			return view;
		} catch (Exception e) {
			e.printStackTrace();
			ModelAndView mav = new ModelAndView("error");
			return mav;
		}

	}

	@GetMapping(value = "/plan")
	public ModelAndView getPlan(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("plan/plan");
		return view;

	}

	@GetMapping(value = "/number_verify")
	public ModelAndView numberVerify(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("number-verification/number-verification");
		return view;

	}

	@GetMapping(value = "/otp_verification")
	public ModelAndView otpVerify(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("otp-verification/otp-verification");
		return view;

	}

	@GetMapping(value = "/screch-card")
	public ModelAndView screchCard(HttpServletRequest request) {
		SubscriptionPaymentDto subscriptionPaymentDto = (SubscriptionPaymentDto) request.getSession()
				.getAttribute("subscriptionPaymentDto");
		boolean isValidate = subscriptionPaymentService
				.isValidateById(subscriptionPaymentDto.getSubscriptionPaymentId());
		if (isValidate) {
			return new ModelAndView("strech-card/strech-card");
		} else {
			ModelAndView modelAndView = new ModelAndView("error");
			modelAndView.addObject("errorMessage", "Subscripion payment is null.");
			return modelAndView;
		}

	}

	@GetMapping(value = "/success/{msg}")
	public ModelAndView success(HttpServletRequest request,
			@PathVariable(name = "msg", required = false) Integer message) {
		ModelAndView modelAndView = new ModelAndView("success/success");
		switch (message) {
		case 4:
			modelAndView.addObject("successMessage", "Well done! Your payment is successfully compleated.");
			break;
		case 5:
			modelAndView.addObject("successMessage", "Well done! Your payment is successfully compleated.");
			break;
		case 6:
			modelAndView.addObject("successMessage", "Well done! Your payment is successfully compleated.");
			break;
		case 7:
			modelAndView.addObject("successMessage", "Well done! Your unsubscription completed.");
			break;

		default:
			break;
		}
		return modelAndView;
	}

	@GetMapping(value = "/already_subscribed")
	public ModelAndView AlreadySubscribed(HttpServletRequest request) {
		return new ModelAndView("already-subscribed/already-subscribed");
	}

	@GetMapping(value = "/error")
	public ModelAndView error(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("error");
		modelAndView.addObject("errorMessage", "Error ");
		return modelAndView;
	}

}
