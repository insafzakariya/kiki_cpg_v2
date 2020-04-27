package com.kiki_cpg.development.client;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kiki_cpg.development.dto.DialogOtpConfirmDto;
import com.kiki_cpg.development.dto.DialogOtpDto;

public interface DialogClient {

	String dialogPaymentConfirm(String serverRef, String mobileNo, Double amount, Integer subscribedDays,
			int viwerId);

	String create_access_token();
	
	DialogOtpDto pinSubscriptionConfirm(DialogOtpConfirmDto dialogOtpConfirmDto, Double amount, String accessToken) throws Exception;

	DialogOtpDto sendOtp(String mobile_no, Integer day, String accessToken) throws Exception;


}
