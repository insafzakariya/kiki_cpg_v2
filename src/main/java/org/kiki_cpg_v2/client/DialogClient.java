package org.kiki_cpg_v2.client;

import org.kiki_cpg_v2.dto.DialogOtpDto;
import org.kiki_cpg_v2.dto.request.DialogOtpConfirmDto;

public interface DialogClient {

	String dialogPaymentConfirm(String serverRef, String mobileNo, Double amount, Integer subscribedDays,
			int viwerId);

	String createAccessToken() throws Exception;

	String genarateAuthorizationCode() throws Exception;
	
	DialogOtpDto sendOtp(String mobileNo, Integer day) throws Exception;
	
	DialogOtpDto pinSubscriptionConfirm(DialogOtpConfirmDto dialogOtpConfirmDto, Double amount, String accessToken) throws Exception;

	

	/*JSONObject unsubscribe(String access_token, Viewers viewers, Integer day);

	JSONObject pinSubscription(String mobile_no, String subscriptionPaymentId, String day, String accessToken) throws Exception;*/
}
