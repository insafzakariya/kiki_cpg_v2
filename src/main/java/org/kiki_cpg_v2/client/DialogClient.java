package org.kiki_cpg_v2.client;

import org.kiki_cpg_v2.dto.DialogOtpDto;
import org.kiki_cpg_v2.dto.PaymantPlanDto;
import org.kiki_cpg_v2.dto.request.DialogOtpConfirmDto;

public interface DialogClient {

	String dialogPaymentConfirm(String serverRef, String mobileNo, Double amount, Integer subscribedDays, int viwerId, boolean isUpdateCronViewer, Integer cronId) throws Exception;

	String createAccessToken() throws Exception;

	String genarateAuthorizationCode() throws Exception;

	// not needed after angular release
	DialogOtpDto sendOtp(String mobileNo, Integer day) throws Exception;

	DialogOtpDto pinSubscriptionConfirm(DialogOtpConfirmDto dialogOtpConfirmDto, Double amount, String accessToken)
			throws Exception;

	String unsubscribe(String access_token, Integer viewerId, Integer day, String mobileNo) throws Exception;

	/**
	 * @param mobile_no
	 * @param methodId
	 * @return
	 */
	DialogOtpDto generateOTP(String mobile_no, Integer methodId) throws Exception;

	/**
	 * @param planDto
	 * @param mobileNo
	 * @return
	 * @throws Exception
	 */
	String unsubscribeByMobile(PaymantPlanDto planDto, String mobileNo) throws Exception;

	/*
	 * JSONObject pinSubscription(String mobile_no, String subscriptionPaymentId,
	 * String day, String accessToken) throws Exception;
	 */
}
