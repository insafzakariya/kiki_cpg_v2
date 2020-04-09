package com.kiki_cpg.development.controller;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.hibernate.internal.CriteriaImpl.Subcriteria;
//import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.util.LinkedHashMap;

import com.kiki_cpg.development.dto.CronDto;
import com.kiki_cpg.development.dto.PaymentTypeDto;
import com.kiki_cpg.development.dto.ViewerUnsubcriptionDto;
import com.kiki_cpg.development.entity.IdeabizConfig;
import com.kiki_cpg.development.entity.SubscriptionPayments;
import com.kiki_cpg.development.entity.Viewers;
import com.kiki_cpg.development.repository.ContentRepository;
import com.kiki_cpg.development.repository.IdeabizConfigRepository;
import com.kiki_cpg.development.repository.IdeabizRepository;
import com.kiki_cpg.development.repository.ViewerRepository;
import com.kiki_cpg.development.service.IdeabizService;
import com.kiki_cpg.development.service.InvoiceService;
import com.kiki_cpg.development.service.OTPService;
import com.kiki_cpg.development.service.PaymentCalculation;
import com.kiki_cpg.development.service.PaymentLogService;
import com.kiki_cpg.development.service.PaymentService;
import com.kiki_cpg.development.service.ViewerService;
import com.kiki_cpg.development.service.ViewerUnsubcriptionService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@CrossOrigin("")
@RestController("kiki-cpg/api/v1/ideabiz")
public class IdeabizController {

	@Autowired
	IdeabizService ideabizService;

	@Autowired
	PaymentCalculation paymentCalculation;

	@Autowired
	ViewerUnsubcriptionService viewerUnsubcriptionService;

	// Payment Proceed
	private Integer viewerId = null;
	private Integer invoiceId = null;
	private Double chargeAmount = null;
	private String cron_start_time = null;
	private String paymentStatus = null;
	private String cronType = null;
	private String responseMsg = null;
	private String serverResponse = null;
	private Date responseDateAndTime = null;

	private static final Logger logger = LoggerFactory.getLogger(IdeabizController.class);

	@RequestMapping(value = "/select_payment_type", method = RequestMethod.GET)
	public PaymentTypeDto select_payment_type(ModelMap model,
			@RequestParam(value = "subscriptionPaymentId", required = false, defaultValue = "") int subscriptionPaymentId) {

		Double one_day_amount = paymentCalculation.getAmountByDays(1);
		Double one_week_amount = paymentCalculation.getAmountByDays(7);

		PaymentTypeDto pdto = new PaymentTypeDto();
		pdto.setOne_day_amount(one_day_amount);
		pdto.setOne_week_amount(one_week_amount);
		return pdto;
	}

	@RequestMapping(value = "/ideabiz/payment", method = RequestMethod.GET)
	@ResponseBody
	private String payment_confirm(String server_ref, String mobile_no, Double amount, Integer subscribed_days,
			int viwerId) {

		ideabizService.payment_confirm(server_ref, mobile_no, amount, subscribed_days, viwerId);
		return "Paid";

	}

	@RequestMapping(value = "/ideabiz/create_access_token", method = RequestMethod.POST)
	@ResponseBody
	public String create_access_token() {
		String token = ideabizService.create_access_token();
		return token;
	}

	@RequestMapping(value = "/ideabiz/genarate_authorization_code", method = RequestMethod.GET)
	@ResponseBody
	private String genarate_authorization_code() {
		String authorization_code = ideabizService.genarate_authorization_code();
		return authorization_code;

	}

	private CronDto addCronReport(CronDto cronDto) {
		return cronDto;
	}

	@RequestMapping(value = "/ideabiz/pin_subscription_confirm", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public HashMap<String, String> pin_subscription_confirm(@RequestBody Map<String, String> userMap,
			HttpServletRequest request) {
		HashMap<String, String> result_obj = ideabizService.pin_subscription_confirm(userMap, request);
		return result_obj;
	}

}
