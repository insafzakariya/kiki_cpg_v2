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
//import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.kiki_cpg.development.dto.CronDto;
import com.kiki_cpg.development.entity.IdeabizConfig;
import com.kiki_cpg.development.entity.Viewers;
import com.kiki_cpg.development.repository.ContentRepository;
import com.kiki_cpg.development.repository.IdeabizConfigRepository;
import com.kiki_cpg.development.repository.IdeabizRepository;
import com.kiki_cpg.development.repository.ViewerRepository;
import com.kiki_cpg.development.service.IdeabizService;
import com.kiki_cpg.development.service.InvoiceService;
import com.kiki_cpg.development.service.OTPService;
import com.kiki_cpg.development.service.PaymentLogService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

	public int proceed_payment(String cronStartTime, Integer viwer_id, Integer subscribed_days, String serviceId,
			Double amount, Integer cronId) {
		try {

			Viewers viewers = viewerRepository.findByViewerId(viwer_id);
			System.out.println("viewer id " + viewers.getViewerId());

			int invoice_id = invoiceService.create(serviceId, viewers, subscribed_days, amount);
			System.out.println("Invoice created " + invoice_id);

			viewerId = viewers.getViewerId();
			invoiceId = invoice_id;
			chargeAmount = amount;
			cron_start_time = cronStartTime;

			String paid = payment_confirm(String.valueOf(invoice_id), viewers.getMobileNumber(), amount,
					subscribed_days, viewers.getViewerId(), cronId);
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
//			CronErrorDto cronErrorDto = new CronErrorDto();
//			cronErrorDto.setErrorDate(new Date());
//			cronErrorDto.setViewerId(viewerId);
//			cronErrorDto.setErrorDesc(e.getMessage());
//			cronErrorDto.setErrorMsg(e.getStackTrace().toString());
//			cronErrorDto.setSystemPage("IdC prospay");
//			cronErrorDto.setCronId(cronId);
//
//			cronErrorService.addCronError(cronErrorDto);

//			otpService.sendMsg("+94773799390",
//					"Exception :  " + e.getMessage() + "Class : IdC " + "Viewer Id : " + viewerId);
//			otpService.sendMsg("+94773799390",
//					"Exception :  " + e.getMessage() + "Class : IdC " + "Viewer Id : " + viewerId);
		}
		return 0;

	}

	@RequestMapping(value = "/ideabiz/payment", method = RequestMethod.GET)
	@ResponseBody
	private String payment_confirm(String server_ref, String mobile_no, Double amount, Integer subscribed_days,
			int viwerId, Integer cronId) {

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
//			System.out.println((JSONObject)jsonObj.get("requestError"));

//			String statusCode=jsonObjRef.get("transactionOperationStatus").toString();
//			String clientCorrelator=jsonObjRef.get("clientCorrelator").toString();

//			System.out.println(statusCode +"->"+clientCorrelator);
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
				// cron
				cronType = "Dialog";
				responseMsg = "Request Error";
				serverResponse = result.toString();
				paymentStatus = "Fail";

				//// } else if (jsonObj.has("fault") && (jsonObj.has("Number is invalid, Number
				//// is not whitelisted"))) {
			} else if (jsonObj.has("fault")) {

				JSONObject jsonObjfault = (JSONObject) jsonObj.get("fault");
				paymentLogService.createPaymentLog("Dialog", jsonObjfault.get("code").toString(),
						jsonObjfault.get("message").toString(), viwerId, mobile_no, result.toString());
				// cron

				// IdeabizModel ideabizModel=new IdeabizModel();
				//// ideabizService.unsubscribe(viwerId);
				// ideabizModel.unsubscribe(viwerId);
				//// System.out.println("Unsubcribe Successfull");

				// cron
				cronType = "Dialog";
				//// responseMsg = "Fault : Whitelisted Number Unsubcribe Successfull";
				responseMsg = "Fault : Whitelisted Number Unsubcribe Successfull";
				serverResponse = result.toString();
				paymentStatus = "Fail";

			}
			// 2020-01-31
			// UnsubcribeViwerNotInWhitelisted
			else if (jsonObj.has("fault") && (jsonObj.has("Number is invalid, Number is not whitelisted"))
					&& (jsonObj.has("Not a Whitelisted Number"))) {

			}
//			idabiz_subscribe(subscriptionPaymentId,mobile_no);
			// update_polices(subscriptionPaymentId);

			// cron
		

			return paid;

		} catch (Exception e) {
//			logger.info(e.getMessage());
//
//			CronErrorDto cronErrorDto = new CronErrorDto();
//			cronErrorDto.setErrorDate(new Date());
//			cronErrorDto.setViewerId(viewerId);
//			cronErrorDto.setErrorDesc(e.getMessage());
//			cronErrorDto.setErrorMsg(e.getStackTrace().toString());
//			cronErrorDto.setSystemPage("IdC payconf");
//			cronErrorDto.setCronId(cronId);
//
//			cronErrorService.addCronError(cronErrorDto);

//			otpService.sendMsg("+94773799390",
//					"Exception :  " + e.getMessage() + "Class : IdC" + "Viewer Id : " + viwerId);
//			otpService.sendMsg("+94773799390",
//					"Exception :  " + e.getMessage() + "Class : IdC" + "Viewer Id : " + viwerId);
		}
		return null;

	}

	@RequestMapping(value = "/ideabiz/create_access_token", method = RequestMethod.POST)
	@ResponseBody
	private String create_access_token() {
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
}
