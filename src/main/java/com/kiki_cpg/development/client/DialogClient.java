package com.kiki_cpg.development.client;



import org.json.JSONObject;

import com.kiki_cpg.development.dto.DialogOtpConfirmDto;
import com.kiki_cpg.development.dto.DialogOtpDto;
import com.kiki_cpg.development.entity.Viewers;

public interface DialogClient {

	String dialogPaymentConfirm(String serverRef, String mobileNo, Double amount, Integer subscribedDays,
			int viwerId);

	String create_access_token();
	
	DialogOtpDto pinSubscriptionConfirm(DialogOtpConfirmDto dialogOtpConfirmDto, Double amount, String accessToken) throws Exception;

	DialogOtpDto sendOtp(String mobile_no, Integer day, String accessToken) throws Exception;

	JSONObject unsubscribe(String access_token, Viewers viewers, Integer day);

	JSONObject pinSubscription(String mobile_no, String subscriptionPaymentId, String day, String accessToken) throws Exception;


}
