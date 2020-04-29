package com.kiki_cpg.development.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kiki_cpg.development.dto.PackageDto;
import com.kiki_cpg.development.dto.SubscriptionPaymentDto;
import com.kiki_cpg.development.service.DialogService;
import com.kiki_cpg.development.service.MobitelService;
import com.kiki_cpg.development.service.ScratchCardCodesService;
import com.kiki_cpg.development.service.SubscriptionPaymentService;

@Controller
public class MainController {

	final static Logger logger = LoggerFactory.getLogger(MainController.class);

	@Autowired
	private SubscriptionPaymentService subscriptionPaymentService;

	@Autowired
	private DialogService dialogService;

	@Autowired
	private ScratchCardCodesService scratchCardCodesService;

	@Autowired
	private MobitelService mobitelService;

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
			session.setAttribute("subscriptionPaymentDto", subscriptionPaymentDto);
			view.addObject("ezcashPaymentAmmount", "");
			view.addObject("mCashPaymentAmmount", "");
			view.addObject("dialogViuPaymentAmmount", "");
			view.addObject("mobitelAddToBillPaymentAmount", "");
			view.addObject("ezcashPaymentType", "");
			view.addObject("mCashPaymentType", "");
			view.addObject("dialogViuPaymentType", "");
			view.addObject("mobitelAddToBillPaymentType", "");
			view.addObject("token", paymentToken);

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

	@GetMapping(value = "/select_payment_type")
	public ModelAndView select_payment_type(ModelMap model, HttpServletRequest request,
			@RequestParam Integer paymentMethodId,
			@RequestParam(value = "subscriptionPaymentId", required = false, defaultValue = "") int subscriptionPaymentId) {
		ModelAndView view = new ModelAndView("payment-type/payment-type");
		view.addObject("paymentMethodId", paymentMethodId);
		view.addObject("subscriptionPaymentId", subscriptionPaymentId);
		return view;

	}

	@GetMapping(value = "/number_verification/{subscriptionPaymentId}/{day}/{methodId}")
	public ModelAndView checkNumber(ModelMap model, HttpServletRequest request,
			@PathVariable Integer subscriptionPaymentId, @PathVariable Integer day, @PathVariable Integer methodId) {
		HttpSession session = request.getSession();
		SubscriptionPaymentDto subscriptionPaymentDto = (SubscriptionPaymentDto) session
				.getAttribute("subscriptionPaymentDto");
		ModelAndView view = new ModelAndView("number-verify/number-verify");
		String mobile_no = subscriptionPaymentDto.getMobile();
		view.addObject("mobielNo", mobile_no);
		view.addObject("methodId", methodId);
		view.addObject("subscriptionPaymentId", subscriptionPaymentId);
		view.addObject("day", day);

		return view;

	}

	@GetMapping(value = "/otp_verify")
	public ModelAndView otp_verify(HttpServletRequest request,
			@RequestParam(value = "serverRef", required = false, defaultValue = "") String serverRef,
			@RequestParam(value = "subscriptionPaymentId", required = false, defaultValue = "") String subscriptionPaymentId,
			@RequestParam(value = "accesstoken", required = false, defaultValue = "") String accesstoken,
			@RequestParam(value = "day", required = false, defaultValue = "") String day) {

		ModelAndView view = new ModelAndView("otp-verify/otp-verify");
		view.addObject("serverRef", serverRef);
		view.addObject("accesstoken", accesstoken);
		view.addObject("subscriptionPaymentId", subscriptionPaymentId);
		view.addObject("day", day);
		return view;

	}

	@GetMapping("/payment-type/{subscriptionId}/{paymentMethodId}")
	@ResponseBody
	public List<PackageDto> getPaymentListDto(@PathVariable Integer subscriptionId,
			@PathVariable Integer paymentMethodId) {
		System.out.println("paymentMethodId" + paymentMethodId);
		List<PackageDto> packageDtos = subscriptionPaymentService.getPaymentPlan(paymentMethodId);
		return packageDtos;
	}

	@GetMapping(value = { "/validatePayment" })
	public ModelAndView validatePayment(ModelMap model, HttpServletRequest request,
			@RequestParam(value = "paymentMethodId", required = false, defaultValue = "0") int paymentMethodId,
			@RequestParam(value = "subscriptionPaymentId", required = false, defaultValue = "0") int subscriptionPaymentId,
			@RequestParam(value = "numberString", required = false, defaultValue = "") String numberString,
			@RequestParam(value = "isTrial", required = false, defaultValue = "false") boolean isTrial) {

		System.out.println(paymentMethodId);
		System.out.println(subscriptionPaymentId);

		System.out.println(numberString);
		System.out.println(isTrial);

		boolean isValidate = subscriptionPaymentService.isValidateById(subscriptionPaymentId);
		if (isValidate) {
			return new ModelAndView("strech-card/strech-card");
		} else {
			ModelAndView modelAndView = new ModelAndView("error");
			modelAndView.addObject("errorMessage", "Subscripion payment is null.");
			return modelAndView;
		}
	}

	@PostMapping(value = { "/scratchCardPayment" })
	public ModelAndView validateAndUseCode(@RequestParam("card_code") String cardCode, HttpServletRequest request) {
		HttpSession session = request.getSession();
		SubscriptionPaymentDto subscriptionPaymentDto = (SubscriptionPaymentDto) session
				.getAttribute("subscriptionPaymentDto");
		if (logger.isInfoEnabled()) {
			logger.info("Validate and Use Promo" + subscriptionPaymentDto.getViewerId());
		}

		String resp = scratchCardCodesService.setPayment(cardCode, subscriptionPaymentDto.getViewerId());

		if (resp.equals("Success")) {
			return new ModelAndView("success/success");
		} else {
			ModelAndView modelAndView = new ModelAndView("error");
			modelAndView.addObject("errorMessage", resp);
			return modelAndView;
		}
	}

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

		SubscriptionPaymentDto subscriptionPaymentDtoSesstion = (SubscriptionPaymentDto) request.getSession().getAttribute("subscriptionPaymentDto");
		
		try {
			if (paymentMethodId == 5) {

				SubscriptionPaymentDto subscriptionPaymentDto = mobitelService
						.getSubscriptionPaymentDto(paymentMethodId, subscriptionPaymentId, numberString, isTrial, subscriptionPaymentDtoSesstion.getViewerId());
				
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
		SubscriptionPaymentDto subscriptionPaymentDto = (SubscriptionPaymentDto) request.getSession().getAttribute("subscriptionPaymentDto");
		boolean isValidate = subscriptionPaymentService.isValidateById(subscriptionPaymentDto.getSubscriptionPaymentId());
		if (isValidate) {
			return new ModelAndView("strech-card/strech-card");
		} else {
			ModelAndView modelAndView = new ModelAndView("error");
			modelAndView.addObject("errorMessage", "Subscripion payment is null.");
			return modelAndView;
		}

	}
	
	@GetMapping(value = "/success/{msg}")
	public ModelAndView success(HttpServletRequest request, @PathVariable(name = "msg", required = false) Integer message) {
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
