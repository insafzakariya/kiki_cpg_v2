package org.kiki_cpg_v2.client;

import org.kiki_cpg_v2.dto.DialogOtpDto;
import org.kiki_cpg_v2.dto.request.DialogOtpConfirmDto;

public interface DialogClient {

	String dialogPaymentConfirm(String serverRef, String mobileNo, Double amount, Integer subscribedDays, int viwerId, boolean isUpdateCronViewer, Integer cronId) throws Exception;

	String createAccessToken() throws Exception;

	String genarateAuthorizationCode() throws Exception;

	DialogOtpDto sendOtp(String mobileNo, Integer day) throws Exception;

	DialogOtpDto pinSubscriptionConfirm(DialogOtpConfirmDto dialogOtpConfirmDto, Double amount, String accessToken)
			throws Exception;

	String unsubscribe(String access_token, Integer viewerId, Integer day, String mobileNo) throws Exception;

	/*
	 * JSONObject pinSubscription(String mobile_no, String subscriptionPaymentId,
	 * String day, String accessToken) throws Exception;
	 */
}
