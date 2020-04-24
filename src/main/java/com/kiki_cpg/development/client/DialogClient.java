package com.kiki_cpg.development.client;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kiki_cpg.development.dto.DialogOtpConfirmDto;
import com.kiki_cpg.development.dto.DialogOtpDto;

public interface DialogClient {

	String dialog_payment_confirm(String server_ref, String mobile_no, Double amount, Integer subscribed_days,
			int viwerId);

	String create_access_token();
	
	DialogOtpDto pinSubscriptionConfirm(DialogOtpConfirmDto dialogOtpConfirmDto, Double amount, String access_token) throws Exception;

	DialogOtpDto sendOtp(String mobile_no, Integer day, String access_token) throws Exception;


}
