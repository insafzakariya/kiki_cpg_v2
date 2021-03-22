package org.kiki_cpg_v2.client;

import java.util.Date;

import javax.xml.soap.SOAPException;

import org.springframework.http.ResponseEntity;

public interface MobitelClient {

	ResponseEntity<?> createAccessCode();

	ResponseEntity<?> mobitelManage(String accessToken, String activationStatus, String mobileNo, int lastTransaciontId);

	Integer updateOneCCTool(boolean b, String mobileNo, Date date, Date deactivatedDate) throws SOAPException, Exception;

//	void logInToMobitelESMS(String userName, String password);
//
//	Integer sendSms(String aliasMsg, String mobile, String message);
//
//	void logOutFromMobitelESMS();
//
//	void testMobitelConnection();

}
