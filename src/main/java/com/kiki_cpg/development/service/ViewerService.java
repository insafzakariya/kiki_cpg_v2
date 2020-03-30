package com.kiki_cpg.development.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.kiki_cpg.development.controller.IdeabizController;
import com.kiki_cpg.development.dto.ResponseDto;
import com.kiki_cpg.development.repository.ViewerRepository;


@Service
public class ViewerService {

	@Autowired
	ViewerRepository repository;

	@Autowired
	IdeabizController ideabizController;

	private static final Logger logger = LoggerFactory.getLogger(ViewerService.class);

	public ResponseDto vieweBalance(String mobileNumber) {

		try {
			//mobileNumber = "94762492297";
			//Long a=new Long(94762492297l);

			//gearate_Number(mobileNumber);

			String accessToken = create_access_token();
			String url = "https://ideabiz.lk/apicall/balancecheck/v4/" + mobileNumber
					+ "/transactions/amount/balance";

			HttpHeaders headers = new HttpHeaders();
			RestTemplate restTemplate = new RestTemplate();
			MediaType mediaType = MediaType.APPLICATION_JSON;
			headers.setContentType(mediaType);
			headers.add("Authorization", accessToken);

			HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
			ResponseEntity<ResponseDto> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
					ResponseDto.class, 1);
			ResponseDto topic = responseEntity.getBody();
			System.out.println(topic.getAccountInfo().getBalance());
			return topic;

		} catch (HttpClientErrorException e) {
			ResponseDto dto = new ResponseDto();
			dto.setEndUserId(mobileNumber.toString());
			dto.setReferenceCode("Mobile Number Type Invalide");
			System.out.println(dto.getReferenceCode());
			return  dto;
			// TODO Auto-generated catch block
//			return new ResponseDto(mobileNumber,"Mobile Number Type Invalide",new AccountInfo("","",0,0));

		}

	}

	private String create_access_token() {

		String url = "https://ideabiz.lk/apicall/token";

		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);

		// add header
		post.setHeader("content-type", "application/x-www-form-urlencoded");
		String authorization_token = genarate_authorization_code();
		System.out.println("authorization_token " + authorization_token);
		post.setHeader("Authorization", authorization_token);

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

		System.out.println("Bearer " + access_token);

		return "Bearer " + access_token;
	}

	private String genarate_authorization_code() {
		byte[] encodedBytes = Base64
				.encodeBase64("JVy3ytG_43x9rDEpJ8Uhj5WzzXUa:9ITz4oPXhD8doL0pYQE4Hz297_ga".getBytes());
		String authorization_code = new String(encodedBytes);
		return "Basic " + authorization_code;
	}

	private String gearate_Number(String mobileNumber) {
		try {
			byte[] encodedBytes = Base64
					.encodeBase64(mobileNumber.getBytes());
			String authorization_code = new String(encodedBytes);
			System.out.println("mobile  "+authorization_code);
			return "mobile " + authorization_code;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
