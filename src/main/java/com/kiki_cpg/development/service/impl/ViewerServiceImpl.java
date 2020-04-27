package com.kiki_cpg.development.service.impl;

import org.apache.commons.codec.binary.Base64;

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

import com.kiki_cpg.development.client.DialogClient;
import com.kiki_cpg.development.dto.ResponseDto;
import com.kiki_cpg.development.entity.Viewers;
import com.kiki_cpg.development.repository.ViewerRepository;
import com.kiki_cpg.development.service.ViewerService;

@Service
public class ViewerServiceImpl implements ViewerService {

	@Autowired
	ViewerRepository repository;

	@Autowired
	private DialogClient dialogClient;

	private static final Logger logger = LoggerFactory.getLogger(ViewerServiceImpl.class);

	@Override
	public ResponseDto vieweBalance(String mobileNumber) {

		try {
			// mobileNumber = "94762492297";
			// Long a=new Long(94762492297l);

			// gearate_Number(mobileNumber);

			String accessToken = dialogClient.create_access_token();
			String url = "https://ideabiz.lk/apicall/balancecheck/v4/" + mobileNumber + "/transactions/amount/balance";

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
			return dto;

		}

	}

	@Override
	public String genarate_authorization_code() {
		byte[] encodedBytes = Base64
				.encodeBase64("JVy3ytG_43x9rDEpJ8Uhj5WzzXUa:9ITz4oPXhD8doL0pYQE4Hz297_ga".getBytes());
		String authorization_code = new String(encodedBytes);
		return "Basic " + authorization_code;
	}

	@Override
	public String gearate_Number(String mobileNumber) {
		try {
			byte[] encodedBytes = Base64.encodeBase64(mobileNumber.getBytes());
			String authorization_code = new String(encodedBytes);
			System.out.println("mobile  " + authorization_code);
			return "mobile " + authorization_code;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Viewers getViewerByid(int viewerID) {
		Viewers viewer=repository.findByViewerId(viewerID);
		return viewer;
	}

	@Override
	public Viewers updateViewerMobileNumber(String mobile_no, Integer viewerId) {
		Viewers viewers=repository.findByViewerId(viewerId);
		viewers.setMobileNumber(mobile_no);
		return repository.save(viewers);
	}

	
}
