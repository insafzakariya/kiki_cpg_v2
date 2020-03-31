package com.kiki_cpg.development.service;

public interface OTPService {
	
	public String genarateOTP(String mobile_no);
	
	public String sendMsg(String mobile_no,String code);

}
