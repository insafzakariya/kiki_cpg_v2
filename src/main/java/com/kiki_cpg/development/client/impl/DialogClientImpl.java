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
	public String dialog_payment_confirm(String server_ref, String mobile_no, Double amount, Integer subscribed_days,
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
			// TODO: handle exception
		}

		return "Bearer " + access_token;
	}

	@Override
	public HashMap<String, String> pin_subscription_confirm(Map<String, String> userMap, HttpServletRequest request) {
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
		Viewers viwer = ideabizService.get_viwer_id_by_sID(subscriptionPaymentId_int);

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
					ideabizService.idabiz_subscribe(viwer, msisdn, days);

					System.out.println("MOBILE NEW" + mobile_no);

					message = "SUBSCRIBED";

					int invoice_id = ideabizService.proceed_payment(viwer.getViewerId(), days, "Ideabiz", amount);

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

}
