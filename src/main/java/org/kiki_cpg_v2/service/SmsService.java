package org.kiki_cpg_v2.service;

public interface SmsService {
	
	Integer sendSms(String mobile, String message) throws Exception;
	
	Integer sendSms(String [] mobiles, String message) throws Exception;

}
