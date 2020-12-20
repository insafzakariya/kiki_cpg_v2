package org.kiki_cpg_v2.service;

import java.util.Date;
import java.util.List;

import org.kiki_cpg_v2.dto.DialogPaymentConfirmDto;
import org.kiki_cpg_v2.dto.PaymantPlanDto;
import org.kiki_cpg_v2.dto.ResponseMapDto;
import org.kiki_cpg_v2.dto.request.DialogOtpConfirmDto;
import org.kiki_cpg_v2.entity.IdeabizEntity;
import org.kiki_cpg_v2.entity.SubscriptionEntity;

public interface IdeabizService {
	
	
	/**
	 * @param dialogOtpConfirmDto
	 * @return
	 */
	DialogPaymentConfirmDto payment(DialogOtpConfirmDto dialogOtpConfirmDto) throws Exception;
	

	// Not use after angular release
	DialogPaymentConfirmDto pinSubscriptionConfirm(DialogOtpConfirmDto dialogOtpConfirmDto) throws Exception;

	String paymentConfirm(String serverRef, String mobileNo, Double amount, Integer subscribedDays, Integer viewerId, boolean isUpdateCronViewer, Integer cronId) throws Exception;

	List<String> processIdeabizPayment(String serverRef, Integer viewerId, Integer day, String mobileNo, Double amount,
			boolean unsubscrideEntityUpdate, boolean isUpdateCronViewer, Integer cronId) throws Exception;

	IdeabizEntity getIdeabizEntity(Integer viewerId, String mobileNo, Integer day) throws Exception;

	IdeabizEntity updateIdeabizPolicyExpDate(Integer viewerId, Integer valiedDate, Date createDate) throws Exception;

	boolean processUnsubscriptionIdeabiz(String accessToken, Integer viewerId, String mobileNo, boolean unsubscribeFromDialog) throws Exception;

	/**
	 * @param viewerId
	 * @param mobile
	 * @throws Exception
	 */
	void processTrial(Integer viewerId, String mobile) throws Exception;


	/**
	 * @param serverRef
	 * @param viewerId
	 * @param day
	 * @param mobileNo
	 * @param amount
	 * @param unsubscrideEntityUpdate
	 * @param isUpdateCronViewer
	 * @param cronId
	 * @return
	 * @throws Exception
	 */
	ResponseMapDto makePayment(SubscriptionEntity subscriptionEntity, String serverRef, Integer viewerId, Integer day, String mobileNo, Double amount,
			boolean unsubscrideEntityUpdate, boolean isUpdateCronViewer, Integer cronId) throws Exception;



	/**
	 * @param mobileNo
	 * @param planDto
	 * @param viewerId
	 * @throws Exception
	 */
	void processUnsubscription(String mobileNo, PaymantPlanDto planDto, Integer viewerId) throws Exception;


	/**
	 * @param confirmDto
	 * @param message
	 * @param mobileNo
	 * @param dialogOtpConfirmDto
	 * @param paymentPlanDto
	 * @throws Exception
	 */
	void initializePayment(DialogPaymentConfirmDto confirmDto, String message, String mobileNo,
			DialogOtpConfirmDto dialogOtpConfirmDto, PaymantPlanDto paymentPlanDto) throws Exception;

	

	

}
