package com.kiki_cpg.development.client;

import org.springframework.http.ResponseEntity;

public interface MobitelClient {

	ResponseEntity<?> createAccessCode() throws Exception;

	ResponseEntity<?> mobitelManage(String accessToken, String activationStatus, String mobileNo,
			int lastTransaciontId);

}
