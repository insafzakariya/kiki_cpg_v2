package org.kiki_cpg_v2.service;

import java.util.Date;
import java.util.List;

import org.kiki_cpg_v2.dto.DialogPaymentConfirmDto;
import org.kiki_cpg_v2.dto.request.DialogOtpConfirmDto;
import org.kiki_cpg_v2.entity.IdeabizEntity;
import org.kiki_cpg_v2.entity.SubscriptionEntity;

public interface IdeabizService {

	DialogPaymentConfirmDto pinSubscriptionConfirm(DialogOtpConfirmDto dialogOtpConfirmDto) throws Exception;

	String paymentConfirm(String serverRef, String mobileNo, Double amount, Integer subscribedDays, Integer viewerId, boolean isUpdateCronViewer, Integer cronId) throws Exception;

	/*
	 * List<String> processIdeabizPayment(String serverRef, Integer viewerId,
	 * Integer day, String mobileNo, Double amount, boolean unsubscrideEntityUpdate,
	 * boolean isUpdateCronViewer, Integer cronId) throws Exception;
	 */

	//IdeabizEntity getIdeabizEntity(Integer viewerId, String mobileNo, Integer day) throws Exception;

	//IdeabizEntity updateIdeabizPolicyExpDate(Integer viewerId, Integer valiedDate, Date createDate) throws Exception;

	boolean processUnsubscriptionIdeabiz(String accessToken, Integer viewerId, String mobileNo, boolean unsubscribeFromDialog) throws Exception;

	/**
	 * @param viewerId
	 * @param mobile
	 * @param subscriptionEntity
	 * @throws Exception
	 */
	void processTrial(Integer viewerId, String mobile, SubscriptionEntity subscriptionEntity) throws Exception;
	
	/**
	 * @param serverRef
	 * @param viewerId
	 * @param day
	 * @param mobileNo
	 * @param amount
	 * @param unsubscrideEntityUpdate
	 * @param isUpdateCronViewer
	 * @param cronId
	 * @param subscriptionEntity
	 * @return
	 * @throws Exception
	 */
	List<String> processIdeabizPayment(String serverRef, Integer viewerId, Integer day, String mobileNo, Double amount,
			boolean unsubscrideEntityUpdate, boolean isUpdateCronViewer, Integer cronId,
			SubscriptionEntity subscriptionEntity) throws Exception;

	

	

}
