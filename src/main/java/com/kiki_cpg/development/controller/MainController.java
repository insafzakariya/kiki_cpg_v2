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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kiki_cpg.development.dto.PackageDto;
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
			session.setAttribute("subscriptionPaymentDto", subscriptionPaymentDto);
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
			@PathVariable Integer subscriptionPaymentId,
			@PathVariable Integer day, @PathVariable Integer methodId) {
		HttpSession session = request.getSession();
		SubscriptionPaymentDto subscriptionPaymentDto = (SubscriptionPaymentDto) session.getAttribute("subscriptionPaymentDto");
		ModelAndView view = new ModelAndView("number-verify/number-verify");
		String mobile_no = subscriptionPaymentDto.getMobile();
		view.addObject("mobielNo", mobile_no);
		view.addObject("subscriptionPaymentId", subscriptionPaymentId);
		view.addObject("day", day);

		return view;

	}
	
	@GetMapping(value="/otp_verify")
	public ModelAndView otp_verify(HttpServletRequest request,
			@RequestParam(value="serverRef",required=false,defaultValue="") String serverRef,
			@RequestParam(value="subscriptionPaymentId",required=false,defaultValue="") String subscriptionPaymentId,
			@RequestParam(value="accesstoken",required=false,defaultValue="") String accesstoken,
			@RequestParam(value="day",required=false,defaultValue="") String day) {
		
		ModelAndView view = new ModelAndView("otp-verify/otp-verify");
		view.addObject("serverRef", serverRef);
		view.addObject("accesstoken", accesstoken);
		view.addObject("subscriptionPaymentId", subscriptionPaymentId);
		view.addObject("day", day);
		return view;
		
	}

	@GetMapping("/payment-type/{paymentMethodId}")
	@ResponseBody
	public List<PackageDto> getPaymentListDto(@PathVariable Integer paymentMethodId) {
		List<PackageDto> packageDtos = new ArrayList<PackageDto>();
		if (paymentMethodId == 4) {
			PackageDto packageDto1 = new PackageDto();
			packageDto1.setName("Daily");
			packageDto1.setValue("Rs. 5 + Tax per day");
			packageDto1.setPaymentMethodId(paymentMethodId);
			packageDto1.setDay(1);
			packageDtos.add(packageDto1);

			PackageDto packageDto2 = new PackageDto();
			packageDto2.setName("Weekly");
			packageDto2.setValue("Rs. 25+ Tax per day");
			packageDto2.setOffer("Limited offer Rs 10 off");
			packageDto2.setPaymentMethodId(paymentMethodId);
			packageDto2.setDay(7);
			packageDtos.add(packageDto2);

		}

		return packageDtos;
	}
	
	@GetMapping(value="/thanks_ideabiz")
	public String thanks(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("thanks/thanks");
		
		HttpSession session= request.getSession();
		String paymentToken = (String) session.getAttribute("token");
		System.out.println(paymentToken);
		view.addObject("paymentToken", paymentToken);
		
		return "Thanks";
		
	}

}
