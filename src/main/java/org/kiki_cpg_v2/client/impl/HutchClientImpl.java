/**
 * @DaSep 7, 2020 @HutchClientImpl.java
 */
package org.kiki_cpg_v2.client.impl;

import org.kiki_cpg_v2.client.HutchClient;
import org.kiki_cpg_v2.dto.HutchResponseDto;
import org.kiki_cpg_v2.dto.request.HutchSubscribeDto;
import org.kiki_cpg_v2.util.AppConstant;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Anjana Thrishakya
 */
@Component
public class HutchClientImpl implements HutchClient{

	@Override
	public HutchResponseDto subscribe(HutchSubscribeDto hutchSubscribeDto) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type", "application/json");
		headers.set("PartnerCode", "0000000022");
		headers.set("PartnerCode", "JGqowwLdRJXAyK+Dql1Ydg==");

		HttpEntity<?> entity = new HttpEntity<>(hutchSubscribeDto, headers);
		ResponseEntity<HutchResponseDto> response = restTemplate.exchange(AppConstant.URL_HUTCH_SUBSCRIBE, HttpMethod.POST, entity, HutchResponseDto.class);

		return response.getBody();
	}

}
