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
import com.kiki_cpg.development.util.AppUtility;

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
	ViewerRepository viewerRepository;

	@Autowired
	InvoiceService invoiceService;

	@Autowired
	PaymentLogService paymentLogService;

	@Autowired
	ContentRepository contentRepository;

	@Autowired
	IdeabizConfigRepository ideabizConfigRepository;

	@Autowired
	IdeabizRepository ideabizRepository;

	@Autowired
	IdeabizService ideabizService;

	@Autowired
	OTPService otpService;

	@Autowired
	PaymentCalculation paymentCalculation;

	@Autowired
	PaymentService paymentService;
	
	@Autowired
	ViewerService viewerService;
	
	@Autowired
	ViewerUnsubcriptionService viewerUnsubcriptionService;

	@Autowired
	private AppUtility appUtility;
	
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

	public int proceed_payment(Integer viwer_id, Integer subscribed_days, String serviceId,
			Double amount) {
		try {

			Viewers viewers = viewerRepository.findByViewerId(viwer_id);
			System.out.println("viewer id " + viewers.getViewerId());

			int invoice_id = invoiceService.create(serviceId, viewers, subscribed_days, amount);
			System.out.println("Invoice created " + invoice_id);

			viewerId = viewers.getViewerId();
			invoiceId = invoice_id;
			chargeAmount = amount;
			//cron_start_time = cronStartTime;

			String paid = payment_confirm(String.valueOf(invoice_id), viewers.getMobileNumber(), amount,
					subscribed_days, viewers.getViewerId());
			System.out.println("check paid : " + paid);
			if (paid.equals("Sucess")) {
				invoiceService.updateInvoice(invoice_id, 1);

				if (subscribed_days == 1) {
					contentRepository.updateViewerPolicies(viewers.getViewerId(), 81, false);

				} else if (subscribed_days == 7) {
					contentRepository.updateViewerPolicies(viewers.getViewerId(), 106, false);
				}

				return invoice_id;
			} else {
				return 0;
			}
		} catch (Exception e) {

		}
		return 0;

	}

	@RequestMapping(value = "/ideabiz/payment", method = RequestMethod.GET)
	@ResponseBody
	private String payment_confirm(String server_ref, String mobile_no, Double amount, Integer subscribed_days,
			int viwerId) {

		try {
			System.out.println("payment confirm");
			String paid = "Fail";
			String accessToken = create_access_token();
			String url = "https://ideabiz.lk/apicall/payment/v4/" + mobile_no + "/transactions/amount";
			System.out.println(url);

			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(url);
			post.setHeader("content-type", "application/json");
			post.setHeader("Authorization", accessToken);

			IdeabizConfig ideabizConfig = new IdeabizConfig();
			List<IdeabizConfig> ideabizConfigList = ideabizConfigRepository.findAll();
			ideabizConfig = ideabizConfigList.get(0);
			System.out.println("check--------------------");
			String serviceID = "";
			if (subscribed_days == 1) {
				serviceID = "bf110848-23ca-4b7d-8a3f-872b59bfd32e";
			} else if (subscribed_days == 7) {
				serviceID = "f0ce6a27-7aca-4e12-b121-25eeee7a840a";
			}

			String payment_json_string = "{\r\n" + "    \"amountTransaction\": {\r\n" + "        \"clientCorrelator\": "
					+ "\"" + server_ref + "\",\r\n" + "        \"endUserId\": " + "\"" + "tel:" + mobile_no + "\",\r\n"
					+ "        \"paymentAmount\": {\r\n" + "            \"chargingInformation\": {\r\n"
					+ "               \"amount\": " + "\"" + amount + "\",\r\n"
					+ "                \"currency\": \"LKR\",\r\n"
					+ "                \"description\": \"Test Charge\"\r\n" + "            },\r\n"
					+ "            \"chargingMetaData\": {\r\n" + "                \"onBehalfOf\": \"KiKi\",\r\n"
					+ "                \"purchaseCategoryCode\": \"Service\",\r\n"
					+ "                \"channel\": \"WAP\",\r\n" + "                \"taxAmount\": \"0\",\r\n"
					+ "                \"serviceID\": \"" + serviceID + "\"\r\n" + "            }  \r\n"
					+ "        },\r\n" + "        \"referenceCode\": " + "\"" + server_ref + "\"" + ",\r\n"
					+ "        \"transactionOperationStatus\": \"Charged\"\r\n" + "    }\r\n" + "}\r\n";

			JSONObject json = new JSONObject(payment_json_string);
			StringEntity params = new StringEntity(json.toString());

			post.setEntity(params);
			HttpResponse response = client.execute(post);

//			System.out.println("Response  : "
//	                + response.getEntity().getContent());
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
//			System.out.println(result);
			JSONObject jsonObj = new JSONObject(result.toString());

			if (jsonObj.has("amountTransaction")) {

				JSONObject jsonObjRef = (JSONObject) jsonObj.get("amountTransaction");
				paid = "Sucess";
				paymentLogService.createPaymentLog("Dialog", "-",
						jsonObjRef.get("transactionOperationStatus").toString(), viwerId, mobile_no, result.toString());
				// cron
				cronType = "Dialog";
				responseMsg = "Amount Transaction";
				serverResponse = result.toString();
				paymentStatus = paid;

			} else if (jsonObj.has("requestError")) {

				JSONObject jsonObjError = (JSONObject) jsonObj.get("requestError");
				JSONObject jsonServiceExeception = (JSONObject) jsonObjError.get("serviceException");
				paymentLogService.createPaymentLog("Dialog", jsonServiceExeception.get("messageId").toString(),
						jsonServiceExeception.get("text").toString(), viwerId, mobile_no, result.toString());
	
			} else if (jsonObj.has("fault")) {

				JSONObject jsonObjfault = (JSONObject) jsonObj.get("fault");
				paymentLogService.createPaymentLog("Dialog", jsonObjfault.get("code").toString(),
						jsonObjfault.get("message").toString(), viwerId, mobile_no, result.toString());

			}
			// 2020-01-31
			// UnsubcribeViwerNotInWhitelisted
			else if (jsonObj.has("fault") && (jsonObj.has("Number is invalid, Number is not whitelisted"))
					&& (jsonObj.has("Not a Whitelisted Number"))) {

			}

			return paid;

		} catch (Exception e) {
			logger.info(e.getMessage());

		}
		return null;

	}

	@RequestMapping(value = "/ideabiz/create_access_token", method = RequestMethod.POST)
	@ResponseBody
	public String create_access_token() {
		String url = "https://ideabiz.lk/apicall/token";

		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);

		// add header
		post.setHeader("content-type", "application/x-www-form-urlencoded");
		String authorization_token = genarate_authorization_code();
		post.setHeader("authorization", authorization_token);

		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("grant_type", "password"));
		urlParameters.add(new BasicNameValuePair("username", "mobilevisionsnew"));
		urlParameters.add(new BasicNameValuePair("password", "Mobile8686"));
		urlParameters.add(new BasicNameValuePair("scope", "PRODUCTION"));
		String access_token = null;
		try {
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
			HttpResponse response = client.execute(post);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			JSONObject jsonObj = new JSONObject(result.toString());
			access_token = jsonObj.get("access_token").toString();
		} catch (Exception e) {
			logger.info(e.getMessage());
			// TODO: handle exception
		}

		return "Bearer " + access_token;
	}

	@RequestMapping(value = "/ideabiz/genarate_authorization_code", method = RequestMethod.GET)
	@ResponseBody
	private String genarate_authorization_code() {
		byte[] encodedBytes = Base64
				.encodeBase64("JVy3ytG_43x9rDEpJ8Uhj5WzzXUa:9ITz4oPXhD8doL0pYQE4Hz297_ga".getBytes());
		String authorization_code = new String(encodedBytes);
		return "Basic " + authorization_code;
	}

	private CronDto addCronReport(CronDto cronDto) {
//        cronDto.setCronType(cronType);
//        cronDto.setStartTime(cron_start_time);
//        cronDto.setViewerId(viewerId);
//        cronDto.setStatus(paymentStatus);
//        cronDto.setChargeAmount(chargeAmount);
//        cronDto.setResponseMsg(responseMsg);
//        cronDto.setServerResponse(serverResponse);
//        cronDto.setResponseDateAndTime(new Date());

		return cronDto;
	}
	
	@RequestMapping(value="/ideabiz/pin_subscription",method=RequestMethod.POST,produces = "application/json")
	@ResponseBody
	public HashMap<String, String> pin_subscription(@RequestParam String mobile_no,@RequestParam String subscriptionPaymentId,@RequestParam String day) {
		
		
		String statusCode="FAIL";
		String serverRef="";
		String msisdn="";
		String serviceId="";
		String url = "https://ideabiz.lk/apicall/pin/subscription/v1/subscribe";
		String is_dialog="dialog";
		
		boolean isdialog=appUtility.getIsDialogNumber(mobile_no);
		HashMap<String, String> result_obj = new LinkedHashMap<String, String>();
		if (isdialog) {
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post =new HttpPost(url);
			
			// add header
			post.setHeader("content-type","application/json");
			
			String access_token=create_access_token();		
			post.setHeader("Authorization",access_token);	

			try {
				System.out.println("start");
				JSONObject json = new JSONObject();
				
				//Body
				json.put("method", "mobilevisions");
				json.put("msisdn", mobile_no);
				System.out.println("DAY"+day);
				if (Integer.parseInt(day)==1) {
					json.put("serviceId", "BF110848-23CA-4B7D-8A3F-872B59BFD32E");
				} else if(Integer.parseInt(day)==7){
					json.put("serviceId", "f0ce6a27-7aca-4e12-b121-25eeee7a840a");
				}
				
				StringEntity params = new StringEntity(json.toString());
				
				post.setEntity(params);
				HttpResponse response = client.execute(post);
				
				System.out.println("Response  : " 
		                + response.getEntity().getContent());
				
				BufferedReader rd = new BufferedReader(
				        new InputStreamReader(response.getEntity().getContent()));
				StringBuffer result = new StringBuffer();
				String line = "";
				while ((line = rd.readLine()) != null) {				
					result.append(line);
				}
				System.out.println(result);
				JSONObject jsonObj = new JSONObject(result.toString());
				JSONObject jsonObjRef=(JSONObject)jsonObj.get("data");
				
				statusCode=jsonObj.get("statusCode").toString();
				serverRef=jsonObjRef.get("serverRef").toString();
				msisdn=jsonObjRef.get("msisdn").toString();
				serviceId=jsonObjRef.get("serviceId").toString();
					
				System.out.println(serverRef);
			} catch (Exception e) {
				logger.info(e.getMessage());
				// TODO: handle exception
			}
			System.out.println(statusCode);
			
			result_obj.put("OTP_SEND", statusCode);
			result_obj.put("SERVER_REF", serverRef);
			result_obj.put("MSISDN", msisdn);
			result_obj.put("SERVICE_ID", serviceId);
			result_obj.put("ACCESS_TOKEN", access_token);
			result_obj.put("IS_DIALOG", is_dialog);
		} else {
			is_dialog="none";
			result_obj.put("OTP_SEND", statusCode);
			result_obj.put("IS_DIALOG", is_dialog);
		}
		
		
		return result_obj;
	}
	

	@RequestMapping(value = "/ideabiz/pin_subscription_confirm", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public HashMap<String, String> pin_subscription_confirm(@RequestBody Map<String, String> userMap,
			HttpServletRequest request) {

		HttpSession session = request.getSession();
		String accessToken = create_access_token();
		String pin = userMap.get("pin");
		String serverRef = userMap.get("serverRef");
		String subscriptionPaymentId = userMap.get("subscriptionPaymentId");
		Integer days = Integer.parseInt(userMap.get("day"));
		Double amount = paymentCalculation.getAmountByDays(days);

		System.out.println("access-token is: " + accessToken);
		System.out.println("referen-token is: " + serverRef);
		System.out.println("subscriptionPaymentId is: " + subscriptionPaymentId);

		int subscriptionPaymentId_int = Integer.parseInt(subscriptionPaymentId);
		Viewers viwer = get_viwer_id_by_sID(subscriptionPaymentId_int);

		String statusCode = "FAIL";
		String message = "";
		String status = "";
		String serverRef_res = "";
		String serviceId = "";
		String msisdn = "";

		String url = "https://ideabiz.lk/apicall/pin/subscription/v1/submitPin";

		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);

		post.setHeader("content-type", "application/json");
		post.setHeader("Authorization", accessToken);

		try {
			System.out.println("start Pin Confirm");
			JSONObject json = new JSONObject();

			// Body
			json.put("pin", pin);
			json.put("serverRef", serverRef);

			StringEntity params = new StringEntity(json.toString());

			post.setEntity(params);
			HttpResponse response = client.execute(post);

			System.out.println("Response  : " + response.getEntity().getContent());

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			System.out.println(result);
			JSONObject jsonObj = new JSONObject(result.toString());
			System.out.println(jsonObj.get("statusCode"));
			if (jsonObj.get("statusCode").equals("SUCCESS")) {

				JSONObject jsonObjRef = (JSONObject) jsonObj.get("data");
				statusCode = jsonObj.get("statusCode").toString();
				serverRef_res = jsonObjRef.get("serverRef").toString();
				status = jsonObjRef.get("status").toString();
				serviceId = jsonObjRef.get("serviceId").toString();
				msisdn = jsonObjRef.get("msisdn").toString();

				String mobile_no = msisdn;
				mobile_no = mobile_no.replace("tel:", "");
				viewerService.updateViewerMobileNumber(mobile_no, viwer.getViewerId());

				if (jsonObjRef.get("status").equals("SUBSCRIBED")) {
					idabiz_subscribe(viwer, msisdn, days);

					System.out.println("MOBILE NEW" + mobile_no);

					message = "SUBSCRIBED";

					int invoice_id = proceed_payment(viwer.getViewerId(), days, "Ideabiz", amount);
					
					invoiceService.updatePolicyExpireIdeaBiz(invoice_id, viwer.getViewerId());
					
					ViewerUnsubcriptionDto dto = new ViewerUnsubcriptionDto();
					dto.setCreatedDateTime(new Date());
					dto.setLastUpdatedTime(new Date());
					dto.setMobileNumber(viwer.getMobileNumber());
					dto.setViewerId(viwer.getViewerId());
					dto.setSubcriptionType("SUBCRIBE");
					dto.setServiceProvider("Dialog");
					
					
					viewerUnsubcriptionService.addViewerUnsubcription(dto);
					
					System.out.println("PAYMENT API");
				} else if (jsonObjRef.get("status").equals("ALREADY_SUBSCRIBED")) {
					message = "ALREADY SUBSCRIBED";
					paymentLogService.createPaymentLog("Dialog", "-",
							jsonObjRef.get("transactionOperationStatus").toString(), viwer.getViewerId(), mobile_no,
							result.toString());

					System.out.println("ALREADY SUBSCRIBED");
				}

			} else {
				message = jsonObj.get("message").toString();
				paymentLogService.createPaymentLog("Dialog", "-", jsonObj.get("message").toString(),
						viwer.getViewerId(), jsonObj.get("msisdn").toString(), result.toString());

			}

		} catch (Exception e) {
			logger.info(e.getMessage());

		}
		
		HashMap<String, String> result_obj = new LinkedHashMap<String, String>();
		result_obj.put("PIN-SUBMIT", statusCode);
		result_obj.put("SERVER-REF", serverRef_res);
		result_obj.put("STATUS", status);
		result_obj.put("SERVICE-ID", serviceId);
		result_obj.put("ACCESS-TOKEN", accessToken);
		result_obj.put("MSISDN", msisdn);
		result_obj.put("MESSAGE", message);
		result_obj.put("SYSTEM-TOKEN", (String) session.getAttribute("token"));

		return result_obj;
	}

	private Viewers get_viwer_id_by_sID(Integer subscriptionPaymentId) {
		
		SubscriptionPayments subPay=paymentService.getSubscriptionPayments(subscriptionPaymentId);
		if(subPay!=null) {
			Viewers viewer=viewerService.getViewerByid(subPay.getViewerID());
			return viewer;
		}
		return new Viewers();
	}
	
	private String idabiz_subscribe(Viewers viwer,String mobile_no,Integer days) {
		mobile_no=mobile_no.replace("tel:+","");
		ideabizService.addIdabiz_subscribe(viwer.getViewerId(), mobile_no,days);
		return "success";
	}
}
