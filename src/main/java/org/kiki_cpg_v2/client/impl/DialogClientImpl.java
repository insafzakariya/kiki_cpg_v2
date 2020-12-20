package org.kiki_cpg_v2.client.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.kiki_cpg_v2.client.DialogClient;
import org.kiki_cpg_v2.dto.DialogOtpDto;
import org.kiki_cpg_v2.dto.PaymantPlanDto;
import org.kiki_cpg_v2.dto.request.DialogOtpConfirmDto;
import org.kiki_cpg_v2.service.CronViewerRepostService;
import org.kiki_cpg_v2.service.IdeabizServiceConfigService;
import org.kiki_cpg_v2.service.PaymentLogService;
import org.kiki_cpg_v2.service.PaymentPlanService;
import org.kiki_cpg_v2.util.AppConstant;
import org.kiki_cpg_v2.util.AppUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class DialogClientImpl implements DialogClient {

	@Autowired
	private AppUtility appUtility;

	@Autowired
	private PaymentLogService paymentLogService;

	@Autowired
	private CronViewerRepostService cronViewerRepostService;

	@Autowired
	private IdeabizServiceConfigService ideabizServiceConfigService;

	@Autowired
	private PaymentPlanService paymentPlanService;

	@Override
	public String dialogPaymentConfirm(String serverRef, String mobileNo, Double amount, Integer subscribedDays,
			int viwerId, boolean isUpdateCronViewer, Integer cronId) throws Exception {

		try {
			System.out.println("payment confirm");
			String paid = "Fail";
			String accessToken = createAccessToken();
			String url = AppConstant.URL_IDEABIZ_PAYMENT_CONFIRM_I + mobileNo
					+ AppConstant.URL_IDEABIZ_PAYMENT_CONFIRM_II;
			System.out.println(url);

			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(url);
			post.setHeader("content-type", "application/json");
			post.setHeader("Authorization", accessToken);

			String serviceID = ideabizServiceConfigService.getServiceId(subscribedDays);

			String payment_json_string = "{\r\n" + "    \"amountTransaction\": {\r\n" + "        \"clientCorrelator\": "
					+ "\"" + serverRef + "\",\r\n" + "        \"endUserId\": " + "\"" + "tel:" + mobileNo + "\",\r\n"
					+ "        \"paymentAmount\": {\r\n" + "            \"chargingInformation\": {\r\n"
					+ "               \"amount\": " + "\"" + amount + "\",\r\n"
					+ "                \"currency\": \"LKR\",\r\n"
					+ "                \"description\": \"Test Charge\"\r\n" + "            },\r\n"
					+ "            \"chargingMetaData\": {\r\n" + "                \"onBehalfOf\": \"KiKi\",\r\n"
					+ "                \"purchaseCategoryCode\": \"Service\",\r\n"
					+ "                \"channel\": \"WAP\",\r\n" + "                \"taxAmount\": \"0\",\r\n"
					+ "                \"serviceID\": \"" + serviceID + "\"\r\n" + "            }  \r\n"
					+ "        },\r\n" + "        \"referenceCode\": " + "\"" + serverRef + "\"" + ",\r\n"
					+ "        \"transactionOperationStatus\": \"Charged\"\r\n" + "    }\r\n" + "}\r\n";
			System.out.println(mobileNo);
			System.out.println(payment_json_string);
			JSONObject json = new JSONObject(payment_json_string);
			StringEntity params = new StringEntity(json.toString());

			post.setEntity(params);
			HttpResponse response = client.execute(post);

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			System.out.println(result.toString());

			JSONObject jsonObj = new JSONObject(result.toString());

			String respMessage = "";

			if (jsonObj.has("amountTransaction")) {
				respMessage = "amountTransaction";
				JSONObject jsonObjRef = (JSONObject) jsonObj.get("amountTransaction");
				if (jsonObjRef.get("transactionOperationStatus").toString().equalsIgnoreCase("Charged")) {
					paid = "Success";
				}
				paymentLogService.createPaymentLog("Dialog", "-",
						jsonObjRef.get("transactionOperationStatus").toString(), viwerId, mobileNo, result.toString());

			} else if (jsonObj.has("requestError")) {
				respMessage = "Request Error";
				JSONObject jsonObjError = (JSONObject) jsonObj.get("requestError");
				JSONObject jsonServiceExeception = (JSONObject) jsonObjError.get("serviceException");
				paymentLogService.createPaymentLog("Dialog", jsonServiceExeception.get("messageId").toString(),
						jsonServiceExeception.get("text").toString(), viwerId, mobileNo, result.toString());

			} else if (jsonObj.has("fault") && (jsonObj.has("Number is invalid, Number is not whitelisted"))
					&& (jsonObj.has("Not a Whitelisted Number"))) {
				respMessage = "Fault";
				paymentLogService.createPaymentLog("Dialog", "", "", viwerId, mobileNo, result.toString());
			} else if (jsonObj.has("fault")) {

				respMessage = "Fault : Whitelisted Number Unsubcribe Successfull";

				JSONObject jsonObjfault = (JSONObject) jsonObj.get("fault");
				paymentLogService.createPaymentLog("Dialog", jsonObjfault.get("code").toString(),
						jsonObjfault.get("message").toString(), viwerId, mobileNo, result.toString());

			}

			if (isUpdateCronViewer) {
				String status = "Fail";
				if (paid.equalsIgnoreCase("Success")) {
					status = paid;
				}
				cronViewerRepostService.save(viwerId, status, amount, respMessage, result.toString(), cronId);
			}

			return paid;

		} catch (Exception e) {
			e.printStackTrace();
			// logger.info(e.getMessage());

		}
		return null;
	}

	@Override
	public String createAccessToken() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		String authorizationToken = genarateAuthorizationCode();

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(AppConstant.URL_IDEABIZ_ACCESS_TOKEN)
				.queryParam("grant_type", "password").queryParam("username", "mobilevisionsnew")
				.queryParam("password", "Mobile8686").queryParam("scope", "PRODUCTION");

		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type", "application/x-www-form-urlencoded");
		headers.set("authorization", authorizationToken);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		HttpEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity,
				String.class);

		System.out.println(response.getBody().toString());

		JSONObject jsonObj = new JSONObject(response.getBody().toString());
		String accessToken = jsonObj.get("access_token").toString();

		System.out.println("Bearer " + accessToken);

		return "Bearer " + accessToken;
	}

	@Override
	public String genarateAuthorizationCode() throws Exception {
		byte[] encodedBytes = Base64.getEncoder()
				.encode("JVy3ytG_43x9rDEpJ8Uhj5WzzXUa:9ITz4oPXhD8doL0pYQE4Hz297_ga".getBytes());
		String authorization_code = new String(encodedBytes);
		return "Basic " + authorization_code;
	}

	@Override
	public DialogOtpDto sendOtp(String mobileNo, Integer day) throws Exception {
		try {

			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders headers = new HttpHeaders();
			headers.set("content-type", "application/json");
			headers.set("authorization", createAccessToken());

			Map<String, Object> map = new HashMap<>();

			mobileNo = "+94" + appUtility.getNineDigitMobileNumber(mobileNo);

			// Body
			map.put("method", "mobilevisions");
			map.put("msisdn", mobileNo);

			String serviceID = ideabizServiceConfigService.getServiceId(day);

			map.put("serviceID", serviceID);

//			if (day == 1) {
//				map.put("serviceID", AppConstant.IDEABIZ_SERVICE_1);
//			} else if (day == 7) {
//				map.put("serviceID", AppConstant.IDEABIZ_SERVICE_7);
//			}

			HttpEntity<?> entity = new HttpEntity<>(map, headers);

			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(AppConstant.URL_IDEABIZ_OTP);

			HttpEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity,
					String.class);

			System.out.println(response.getBody().toString());

			JSONObject jsonObj = new JSONObject(response.getBody().toString());
			JSONObject jsonObjRef = new JSONObject(jsonObj.get("data").toString());

			DialogOtpDto dialogOtpDto = new DialogOtpDto();

			dialogOtpDto.setStatusCode(jsonObj.get("statusCode").toString());
			dialogOtpDto.setServerRef(jsonObjRef.get("serverRef").toString());
			dialogOtpDto.setMsisdn(jsonObjRef.get("msisdn").toString());
			dialogOtpDto.setServiceId(jsonObjRef.get("serviceId").toString());

			System.out.println(dialogOtpDto.toString());

			return dialogOtpDto;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public DialogOtpDto pinSubscriptionConfirm(DialogOtpConfirmDto dialogOtpConfirmDto, Double amount,
			String accessToken) throws Exception {
		try {

			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders headers = new HttpHeaders();
			headers.set("content-type", "application/json");
			headers.set("authorization", accessToken);
			Map<String, Object> map = new HashMap<>();

			// Body
			map.put("pin", dialogOtpConfirmDto.getPin());
			map.put("serverRef", dialogOtpConfirmDto.getServerRef());

			String serviceID = ideabizServiceConfigService.getServiceId(dialogOtpConfirmDto.getDay());

			map.put("serviceID", serviceID);

//			if (dialogOtpConfirmDto.getDay() == 1) {
//				map.put("serviceID", AppConstant.IDEABIZ_SERVICE_1);
//			} else if (dialogOtpConfirmDto.getDay() == 7) {
//				map.put("serviceID", AppConstant.IDEABIZ_SERVICE_7);
//			}

			HttpEntity<?> entity = new HttpEntity<>(map, headers);

			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(AppConstant.URL_IDEABIZ_SUBMIT_PIN);

			HttpEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity,
					String.class);

			System.out.println(response.getBody().toString());

			JSONObject jsonObj = new JSONObject(response.getBody().toString());
			JSONObject jsonObjRef = new JSONObject(jsonObj.get("data").toString());

			DialogOtpDto dialogOtpDto = new DialogOtpDto();

			dialogOtpDto.setStatusCode(jsonObj.get("statusCode").toString());
			dialogOtpDto.setServerRef(jsonObjRef.get("serverRef").toString());
			dialogOtpDto.setMsisdn(jsonObjRef.get("msisdn").toString());
			dialogOtpDto.setServiceId(jsonObjRef.get("serviceId").toString());
			dialogOtpDto.setStatus(jsonObjRef.get("status").toString());
			dialogOtpDto.setResult(response.getBody());
			dialogOtpDto.setMessage(jsonObj.get("message").toString());
			try {
				dialogOtpDto.setTransactionOperationStatus(jsonObjRef.get("transactionOperationStatus").toString());
			} catch (Exception e) {
				// e.printStackTrace();
			}

			return dialogOtpDto;
		} catch (Exception e) {
			e.printStackTrace();

			if (e.getLocalizedMessage().contains("Wrong Pin")) {
				System.out.println("Wrong Pin");
				throw new RuntimeException("Wrong Pin");
			}

			if (e.getLocalizedMessage().contains("Max attempt exceeded")) {
				System.out.println("Max Attempt Exceeded");
				throw new RuntimeException("Max Attempt Exceeded");
			} else {
				throw new RuntimeException("OTP Fail");
			}
		}
	}

	// NotUse After Angular Release
	@Override
	public String unsubscribe(String access_token, Integer viewerId, Integer day, String mobileNo) throws Exception {
		try {

			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders headers = new HttpHeaders();
			headers.set("content-type", "application/json");
			headers.set("authorization", createAccessToken());

			Map<String, Object> map = new HashMap<>();

			mobileNo = "+94" + appUtility.getNineDigitMobileNumber(mobileNo);
			String tel = "tel:" + mobileNo;
			// Body
			map.put("method", "WEB");
			map.put("msisdn", tel);

			String serviceID = ideabizServiceConfigService.getServiceId(day);

			map.put("serviceID", serviceID);

//			if (day == 1) {
//				map.put("serviceID", AppConstant.IDEABIZ_SERVICE_1);
//			} else if (day == 7) {
//				map.put("serviceID", AppConstant.IDEABIZ_SERVICE_7);
//			}

			HttpEntity<?> entity = new HttpEntity<>(map, headers);

			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(AppConstant.URL_IDEABIZ_UNSUBSCRIBE);

			HttpEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity,
					String.class);

			System.out.println(response.getBody().toString());

			JSONObject jsonObj = new JSONObject(response.getBody().toString());
			// JSONObject jsonObjRef = new JSONObject(jsonObj.get("data").toString());

			return jsonObj.get("statusCode").toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public String unsubscribeByMobile(PaymantPlanDto planDto, String mobileNo) throws Exception {
		try {

			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders headers = new HttpHeaders();
			headers.set("content-type", "application/json");
			headers.set("authorization", createAccessToken());

			Map<String, Object> map = new HashMap<>();

			mobileNo = "+94" + appUtility.getNineDigitMobileNumber(mobileNo);
			String tel = "tel:" + mobileNo;
			// Body
			map.put("method", "WEB");
			map.put("msisdn", tel);

			map.put("serviceID", planDto.getServiceId());

//			if (day == 1) {
//				map.put("serviceID", AppConstant.IDEABIZ_SERVICE_1);
//			} else if (day == 7) {
//				map.put("serviceID", AppConstant.IDEABIZ_SERVICE_7);
//			}

			HttpEntity<?> entity = new HttpEntity<>(map, headers);

			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(AppConstant.URL_IDEABIZ_UNSUBSCRIBE);

			HttpEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity,
					String.class);

			System.out.println(response.getBody().toString());

			JSONObject jsonObj = new JSONObject(response.getBody().toString());
			// JSONObject jsonObjRef = new JSONObject(jsonObj.get("data").toString());

			return jsonObj.get("statusCode").toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public DialogOtpDto generateOTP(String mobileNo, Integer methodId) throws Exception {
		try {

			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders headers = new HttpHeaders();
			headers.set("content-type", "application/json");
			headers.set("authorization", createAccessToken());

			Map<String, Object> map = new HashMap<>();

			mobileNo = "+94" + appUtility.getNineDigitMobileNumber(mobileNo);

			map.put("method", "mobilevisions");
			map.put("msisdn", mobileNo);

			String serviceID = paymentPlanService.getServiceId(methodId);

			map.put("serviceID", serviceID);

			HttpEntity<?> entity = new HttpEntity<>(map, headers);

			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(AppConstant.URL_IDEABIZ_OTP);

			HttpEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity,
					String.class);

			System.out.println(response.getBody().toString());

			JSONObject jsonObj = new JSONObject(response.getBody().toString());
			JSONObject jsonObjRef = new JSONObject(jsonObj.get("data").toString());

			DialogOtpDto dialogOtpDto = new DialogOtpDto();

			dialogOtpDto.setStatusCode(jsonObj.get("statusCode").toString());
			dialogOtpDto.setServerRef(jsonObjRef.get("serverRef").toString());
			dialogOtpDto.setMsisdn(jsonObjRef.get("msisdn").toString());
			dialogOtpDto.setServiceId(jsonObjRef.get("serviceId").toString());

			System.out.println(dialogOtpDto.toString());

			return dialogOtpDto;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
