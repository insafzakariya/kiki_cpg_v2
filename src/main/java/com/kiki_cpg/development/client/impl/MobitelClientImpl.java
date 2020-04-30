package com.kiki_cpg.development.client.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonObject;
import com.kiki_cpg.development.client.MobitelClient;

@Component
public class MobitelClientImpl implements MobitelClient{

	@Override
	public ResponseEntity<?> createAccessCode() throws Exception{
		String uri = "https://apphub.mobitel.lk/mobitelint/mapis/developeroauth/oauth2/token";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setCacheControl("no-cache");
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("grant_type", "password");
		map.add("client_id", "79305786-dbcb-4d49-bd31-ce861f1f0fe5");
		map.add("username", "susilatv");
		map.add("password", "susilatv@api");
		map.add("scope", "default");
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map,
				headers);
		ResponseEntity<?> res = restTemplate.exchange(uri, HttpMethod.POST, request,
				Object.class);
		
		return res;
	}

	@Override
	public ResponseEntity<?> mobitelManage(String accessToken, String activationStatus, String mobileNo,
			int lastTransaciontId) {
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		
		final String uri1 = "https://apphub.mobitel.lk/mobitelint/mapis/susilatv/manage";

		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("authorization", "Bearer" +  accessToken);
		headers.setCacheControl("no-cache");
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("x-ibm-client-id", "79305786-dbcb-4d49-bd31-ce861f1f0fe5");
		JsonObject jsObj = new JsonObject();

		jsObj.addProperty("status", activationStatus);
		jsObj.addProperty("mobileNo", mobileNo);
		jsObj.addProperty("id",++lastTransaciontId);
		HttpEntity<String> req = new HttpEntity<String>(jsObj.toString(), headers);
		ResponseEntity<?> res = restTemplate.exchange(uri1, HttpMethod.POST, req, Object.class);
		return res;
	}

}
