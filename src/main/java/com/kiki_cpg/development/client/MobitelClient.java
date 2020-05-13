package com.kiki_cpg.development.client;

import java.util.Date;

import javax.xml.soap.SOAPException;

import org.springframework.http.ResponseEntity;

public interface MobitelClient {

	ResponseEntity<?> createAccessCode() throws Exception;

	ResponseEntity<?> mobitelManage(String accessToken, String activationStatus, String mobileNo,
			int lastTransaciontId);

	Integer updateOneCCTool(boolean b, String mobileNo, Date date, Date deactivatedDate) throws SOAPException, Exception;

}
