package org.kiki_cpg_v2.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.kiki_cpg_v2.service.ViewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@CrossOrigin(origins = "*")
public class ViewController {

	final static Logger logger = LoggerFactory.getLogger(ViewController.class);

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
			mav.addObject("errorMessage", "Error");
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
		HttpSession session = request.getSession();
		String paymentToken = (String) session.getAttribute("token");
		System.out.println(paymentToken);
		view.addObject("paymentToken", paymentToken);
		view.addObject("type", type);
		return view;
	}

	@GetMapping(value = "/screch-card")
	public ModelAndView screchCard(HttpServletRequest request) {
		return new ModelAndView("scratchcard/scratchcard");
	}

}
