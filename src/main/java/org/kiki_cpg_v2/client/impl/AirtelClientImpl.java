/**
 * @DaFeb 5, 2021 @AirtelClientImpl.java
 */
package org.kiki_cpg_v2.client.impl;

import org.kiki_cpg_v2.client.AirtelClient;
import org.kiki_cpg_v2.dto.AirtelClientResponseDto;
import org.kiki_cpg_v2.dto.HutchResponseDto;
import org.kiki_cpg_v2.dto.request.AirtelClientRequestDto;
import org.kiki_cpg_v2.util.AppConstant;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * @author Anjana Thrishakya
 */
@Component
public class AirtelClientImpl implements AirtelClient {

	@Override
	public AirtelClientResponseDto subscribe(AirtelClientRequestDto airtelClientRequestDto) {
		XmlMapper xmlMapper = new XmlMapper();
		try {
			String xml = xmlMapper.writeValueAsString(airtelClientRequestDto);
			System.out.println(xml);
			
			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_XML);
			
			HttpEntity<?> entity = new HttpEntity<>(xml, headers);
			ResponseEntity<String> response = restTemplate.exchange(AppConstant.URL_AIRLET_SUBSCRIBE, HttpMethod.POST, entity, String.class);
			AirtelClientResponseDto airtelClientResponseDto = xmlMapper.readValue(response.getBody(), AirtelClientResponseDto.class);
			return airtelClientResponseDto;
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
