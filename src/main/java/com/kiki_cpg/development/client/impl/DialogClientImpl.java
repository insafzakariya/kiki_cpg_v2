package com.kiki_cpg.development.client.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiki_cpg.development.client.DialogClient;
import com.kiki_cpg.development.dto.DialogOtpConfirmDto;
import com.kiki_cpg.development.dto.DialogOtpDto;
import com.kiki_cpg.development.dto.ViewerUnsubcriptionDto;
import com.kiki_cpg.development.entity.IdeabizConfig;
import com.kiki_cpg.development.entity.Viewers;
import com.kiki_cpg.development.repository.IdeabizConfigRepository;
import com.kiki_cpg.development.service.IdeabizService;
import com.kiki_cpg.development.service.InvoiceService;
import com.kiki_cpg.development.service.PaymentCalculation;
import com.kiki_cpg.development.service.PaymentLogService;
import com.kiki_cpg.development.service.PaymentService;
import com.kiki_cpg.development.service.ViewerService;
import com.kiki_cpg.development.service.ViewerUnsubcriptionService;
import com.kiki_cpg.development.service.impl.IdeabizServiceImpl;

@Service
public class DialogClientImpl implements DialogClient {

	@Autowired
	PaymentService paymentService;

	@Autowired
	IdeabizConfigRepository ideabizConfigRepository;

	@Autowired
	PaymentLogService paymentLogService;

	@Autowired
	IdeabizService ideabizService;

	@Autowired
	ViewerUnsubcriptionService viewerUnsubcriptionService;

	@Autowired
	PaymentCalculation paymentCalculation;

	@Autowired
	ViewerService viewerService;

	@Autowired
	InvoiceService invoiceService;

	private static final Logger logger = LoggerFactory.getLogger(DialogClientImpl.class);

	@Override
	public String dialogPaymentConfirm(String server_ref, String mobile_no, Double amount, Integer subscribed_days,
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
				paid = "Success";
				paymentLogService.createPaymentLog("Dialog", "-",
						jsonObjRef.get("transactionOperationStatus").toString(), viwerId, mobile_no, result.toString());
				// cron

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

	@Override
	public String create_access_token() {
		String url = "https://ideabiz.lk/apicall/token";

		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);

		// add header
		post.setHeader("content-type", "application/x-www-form-urlencoded");
		String authorization_token = ideabizService.genarate_authorization_code();
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
		}

		return "Bearer " + access_token;
	}

	@Override
	public DialogOtpDto pinSubscriptionConfirm(DialogOtpConfirmDto dialogOtpConfirmDto, Double amount, String accessToken) throws Exception {

		System.out.println("access-token is: " + accessToken);
		System.out.println("referen-token is: " + dialogOtpConfirmDto.getServerRef());
		System.out.println("subscriptionPaymentId is: " + dialogOtpConfirmDto.getSubscriptionPaymentId());

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
			json.put("pin", dialogOtpConfirmDto.getPin());
			json.put("serverRef", dialogOtpConfirmDto.getServerRef());

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

			JSONObject jsonObjRef = (JSONObject) jsonObj.get("data");

			DialogOtpDto dialogOtpDto = new DialogOtpDto();

			dialogOtpDto.setStatusCode(jsonObj.get("statusCode").toString());
			dialogOtpDto.setServerRef(jsonObjRef.get("serverRef").toString());
			dialogOtpDto.setMsisdn(jsonObjRef.get("msisdn").toString());
			dialogOtpDto.setServiceId(jsonObjRef.get("serviceId").toString());
			dialogOtpDto.setStatus(jsonObjRef.get("status").toString());
			dialogOtpDto.setResult(result.toString());
			dialogOtpDto.setMessage(jsonObj.get("message").toString());
			try {
				dialogOtpDto.setTransactionOperationStatus(jsonObjRef.get("transactionOperationStatus").toString());
			} catch (Exception e) {

			}

			return dialogOtpDto;

		} catch (Exception e) {
			logger.info(e.getMessage());
			throw e;
		}

		
	}

	@Override
	public DialogOtpDto sendOtp(String mobile_no, Integer day, String access_token) throws Exception {
		String serverRef = "";
		String url = "https://ideabiz.lk/apicall/pin/subscription/v1/subscribe";
		try {

			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(url);

			// add header
			post.setHeader("content-type", "application/json");

			post.setHeader("Authorization", access_token);

			System.out.println("start");
			JSONObject json = new JSONObject();

			// Body
			json.put("method", "mobilevisions");
			json.put("msisdn", mobile_no);
			System.out.println("DAY" + day);
			if (day == 1) {
				json.put("serviceId", "BF110848-23CA-4B7D-8A3F-872B59BFD32E");
			} else if (day == 7) {
				json.put("serviceId", "f0ce6a27-7aca-4e12-b121-25eeee7a840a");
			}

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

			JSONObject jsonObjRef = (JSONObject) jsonObj.get("data");

			DialogOtpDto dialogOtpDto = new DialogOtpDto();

			dialogOtpDto.setStatusCode(jsonObj.get("statusCode").toString());
			dialogOtpDto.setServerRef(jsonObjRef.get("serverRef").toString());
			dialogOtpDto.setMsisdn(jsonObjRef.get("msisdn").toString());
			dialogOtpDto.setServiceId(jsonObjRef.get("serviceId").toString());

			System.out.println(serverRef);

			return dialogOtpDto;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			throw e;
		}
	}

}
