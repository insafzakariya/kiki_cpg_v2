/**
 * @DaSep 7, 2020 @HutchClientImpl.java
 */
package org.kiki_cpg_v2.client.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

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

import net.bytebuddy.utility.RandomString;

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
		headers.set("Password", "JGqowwLdRJXAyK+Dql1Ydg==");

		System.out.println(hutchSubscribeDto.toString());
		
		HttpEntity<?> entity = new HttpEntity<>(hutchSubscribeDto, headers);
		ResponseEntity<HutchResponseDto> response = restTemplate.exchange(AppConstant.URL_HUTCH_SUBSCRIBE, HttpMethod.POST, entity, HutchResponseDto.class);

		return response.getBody();
	}

	@Override
	public HutchResponseDto unsubscribe(String mobile, String serviceCode) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type", "application/json");
		headers.set("PartnerCode", "0000000022");
		headers.set("Password", "JGqowwLdRJXAyK+Dql1Ydg==");
		
		HutchSubscribeDto hutchSubscribeDto = new HutchSubscribeDto();
		hutchSubscribeDto.setMsisdn(mobile);
		hutchSubscribeDto.setOfferCode(serviceCode);
		hutchSubscribeDto.setTransactionId(new SimpleDateFormat("yyMMdd").format(new Date()) + RandomString.make(6));

		System.out.println(hutchSubscribeDto.toString());
		HttpEntity<?> entity = new HttpEntity<>(hutchSubscribeDto, headers);
		ResponseEntity<HutchResponseDto> response = restTemplate.exchange(AppConstant.URL_HUTCH_UNSUBSCRIBE, HttpMethod.POST, entity, HutchResponseDto.class);

		return response.getBody();
		
	}
	
	/*@Override
	public HutchResponseDto subscribe(HutchSubscribeDto hutchSubscribeDto) {
		
		try {

			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders headers = new HttpHeaders();
			headers.set("content-type", "application/json");
			headers.set("PartnerCode", "0000000022");
			headers.set("Password", "JGqowwLdRJXAyK+Dql1Ydg==");
	

			HttpEntity<?> entity = new HttpEntity<>(hutchSubscribeDto, headers);

			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(AppConstant.URL_HUTCH_SUBSCRIBE);

			HttpEntity<HutchResponseDto> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity,
					HutchResponseDto.class);

			return response.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}*/

}
