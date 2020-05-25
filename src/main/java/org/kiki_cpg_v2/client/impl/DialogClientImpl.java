package org.kiki_cpg_v2.client.impl;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.kiki_cpg_v2.client.DialogClient;
import org.kiki_cpg_v2.dto.DialogOtpDto;
import org.kiki_cpg_v2.dto.request.DialogOtpConfirmDto;
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
	
	@Override
	public String dialogPaymentConfirm(String serverRef, String mobileNo, Double amount, Integer subscribedDays,
			int viwerId) {
		// TODO Auto-generated method stub
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
			if (day == 1) {
				map.put("serviceID", AppConstant.IDEABIZ_SERVICE_1);
			} else if (day == 7) {
				map.put("serviceID", AppConstant.IDEABIZ_SERVICE_7);
			}

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
			
			System.out.println(dialogOtpDto.toString() );

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
			if (dialogOtpConfirmDto.getDay() == 1) {
				map.put("serviceID", AppConstant.IDEABIZ_SERVICE_1);
			} else if (dialogOtpConfirmDto.getDay() == 7) {
				map.put("serviceID", AppConstant.IDEABIZ_SERVICE_7);
			}

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
				//e.printStackTrace();
			}

			return dialogOtpDto;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
