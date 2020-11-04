/**
 * @author Anjana Thrishakya
 * Nov 3, 2020
 * 12:31:01 PM
 */
package org.kiki_cpg_v2.client.impl;

import java.util.HashMap;

import org.kiki_cpg_v2.client.AppleClient;
import org.kiki_cpg_v2.dto.HutchResponseDto;
import org.kiki_cpg_v2.util.AppConstant;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 *
 */
@Component
public class AppleClientImpl implements AppleClient{

	@Override
	public HashMap<String, Object> verify(String receipt) throws Exception {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type", "application/json");
		
		HashMap<String, String> reqData = new HashMap<>();
		reqData.put("receipt-data", receipt);
		reqData.put("password", "99433c08310749cc901db32859b721d8");
		
		HttpEntity<?> entity = new HttpEntity<>(reqData, headers);
		ResponseEntity<HashMap> response = restTemplate.exchange(AppConstant.URL_APPLE_VERIFY, HttpMethod.POST, entity, HashMap.class);

		
		return response.getBody();
	}

}
