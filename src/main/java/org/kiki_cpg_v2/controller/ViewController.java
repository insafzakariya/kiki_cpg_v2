package org.kiki_cpg_v2.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.kiki_cpg_v2.service.SubscriptionService;
import org.kiki_cpg_v2.service.ViewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@CrossOrigin(origins = "*")
public class ViewController {

	final static Logger logger = LoggerFactory.getLogger(ViewController.class);

	@Autowired
	private SubscriptionService subscriptionService;
	
	@Autowired
	private ViewService viewService;

	@RequestMapping("/home")
	public ModelAndView home(HttpServletRequest request, @RequestParam(value = "token") String paymentToken,
			@RequestParam(value = "isFreeTrial", required = false, defaultValue = "false") boolean isFreeTrial,
			@RequestParam(value = "type", required = false, defaultValue = "false") String type) {

		logger.info("susilawebpay application started.");

		try {
			ModelAndView view = viewService.navigateHome(paymentToken, type);
			return view;
		} catch (Exception e) {
			e.printStackTrace();
			ModelAndView mav = new ModelAndView("error/error");
			mav.addObject("message", "Error");
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

	@GetMapping(value = "/thanks/{type}")
	public ModelAndView thanks(HttpServletRequest request, @PathVariable Integer type) {
		ModelAndView view = new ModelAndView("thanks/thanks");
		view.addObject("type", type);
		return view;
	}

	@GetMapping(value = "/scratch-card")
	public ModelAndView screchCard(HttpServletRequest request) {
		return new ModelAndView("scratchcard/scratchcard");
	}

	@GetMapping("/error-page")
	public ModelAndView error(@RequestParam(value = "message", required = false, defaultValue = "") String message) {

		ModelAndView view = new ModelAndView("error/error");
		view.addObject("message", message);
		return view;

	}
	
	@RequestMapping(value = "/mobitel/mobitelPay", method = RequestMethod.GET)
	public ModelAndView mobitelPay(
			@RequestParam(value = "bridgeId", required = false, defaultValue = "0") int subscriptionID,
			HttpServletRequest request) throws IOException {
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			if (subscriptionID == 0) {
				logger.info("bridgeID not available");
				modelAndView.addObject("message", "Bridge ID not Available ");
				modelAndView.setViewName("error/error");
				return modelAndView;
			} else if (subscriptionService.validateSubscriptionPayment(subscriptionID)) {
				modelAndView.setViewName("mobitel-payment-load/mobitel-payment-load");
				return modelAndView;
			} else {
				logger.info("Session Expired");
				modelAndView.addObject("message", "Session Expired");
				modelAndView.setViewName("error/error");
				return modelAndView;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Session Expired");
			modelAndView.addObject("message", e.getMessage());
			modelAndView.setViewName("error/error");
			return modelAndView;
		}

		

	}
}
