package com.kiki_cpg.development.service.impl;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kiki_cpg.development.client.DialogClient;
import com.kiki_cpg.development.dto.DialogOtpConfirmDto;
import com.kiki_cpg.development.dto.DialogOtpDto;
import com.kiki_cpg.development.dto.SubscriptionPaymentDto;
import com.kiki_cpg.development.dto.ViewerUnsubcriptionDto;
import com.kiki_cpg.development.entity.Ideabiz;
import com.kiki_cpg.development.entity.SubscriptionPayments;
import com.kiki_cpg.development.entity.Viewers;
import com.kiki_cpg.development.repository.IdeabizRepository;
import com.kiki_cpg.development.service.ContentService;
import com.kiki_cpg.development.service.IdeabizService;
import com.kiki_cpg.development.service.InvoiceService;
import com.kiki_cpg.development.service.PaymentCalculation;
import com.kiki_cpg.development.service.PaymentLogService;
import com.kiki_cpg.development.service.PaymentService;
import com.kiki_cpg.development.service.ViewerService;
import com.kiki_cpg.development.service.ViewerUnsubcriptionService;
import com.kiki_cpg.development.util.AppUtility;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Transactional
public class IdeabizServiceImpl implements IdeabizService {

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private PaymentLogService paymentLogService;

	@Autowired
	private IdeabizRepository ideabizRepository;

	@Autowired
	private PaymentCalculation paymentCalculation;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private ViewerService viewerService;

	@Autowired
	private ViewerUnsubcriptionService viewerUnsubcriptionService;

	@Autowired
	private ContentService contentService;

	@Autowired
	DialogClient dialogClient;
	
	@Autowired
	private AppUtility appUtility;
	

	@Autowired
	ViewerUnsubcriptionService viewerUnsubService;

	private static final Logger logger = LoggerFactory.getLogger(IdeabizServiceImpl.class);

	@Override
	public void unsubscribe(int viwerId) {
		logger.info("call unsubscribed");
		Ideabiz ideabiz = ideabizRepository.findOneByViwerId(viwerId);
		ideabiz.setSubscribe(0);
		ideabizRepository.save(ideabiz);
	}

	@Override
	public void addIdabiz_subscribe(Integer viewerId, String mobileNo, Integer days) {
		Date date = new Date();
		long time = date.getTime();
		Timestamp ts = new Timestamp(time);

		Ideabiz ideabiz = new Ideabiz();
		ideabiz.setMobile(mobileNo);
		ideabiz.setViwerId(viewerId);
		ideabiz.setCreatedDate(ts);
		ideabiz.setSubscribe(1);
		ideabiz.setSubscribedDays(days);
		ideabizRepository.save(ideabiz);
	}

	@Override
	public Ideabiz findOneByViwerIdAndSubscribe(Integer viewerID, int i) {
		Ideabiz ideabiz = ideabizRepository.findOneByViwerIdAndSubscribe(viewerID, i);
		return ideabiz;
	}

	@Override
	public int proceedPayment(Viewers viewers, Integer subscribed_days, String serviceId, Double amount) {
		try {

			Integer invoiceId = invoiceService.create(serviceId, viewers, subscribed_days, amount);
			System.out.println("Invoice created " + invoiceId);

			String paid = paymentConfirm(String.valueOf(invoiceId), viewers.getMobileNumber(), amount, subscribed_days,
					viewers);
			System.out.println("check paid : " + paid);
			if (paid.equals("Success")) {
				invoiceService.updateInvoice(invoiceId, 1);

				if (subscribed_days == 1) {
					contentService.updateViewerPolicies(viewers.getViewerId(), 81, false);

				} else if (subscribed_days == 7) {
					contentService.updateViewerPolicies(viewers.getViewerId(), 106, false);
				}

				return invoiceId;
			} else {
				return 0;
			}
		} catch (Exception e) {

		}
		return 0;
	}

	@Override
	public String paymentConfirm(String serverRef, String mobileNo, Double amount, Integer subscribedDays,
			Viewers viewers) {
		String paid = dialogClient.dialogPaymentConfirm(serverRef, mobileNo, amount, subscribedDays,
				viewers.getViewerId());
		return paid;
	}

	@Override
	public String create_access_token() {

		String token = dialogClient.create_access_token();
		return token;
	}

	@Override
	public String genarate_authorization_code() {
		byte[] encodedBytes = Base64
				.encodeBase64("JVy3ytG_43x9rDEpJ8Uhj5WzzXUa:9ITz4oPXhD8doL0pYQE4Hz297_ga".getBytes());
		String authorization_code = new String(encodedBytes);
		return "Basic " + authorization_code;
	}

	private DialogOtpConfirmDto getDialogOtpConfirmDto(Map<String, String> userMap) {
		DialogOtpConfirmDto dto = new DialogOtpConfirmDto();
		dto.setDay(Integer.parseInt(userMap.get("day")));
		dto.setPin(userMap.get("pin"));
		dto.setServerRef(userMap.get("serverRef"));
		dto.setSubscriptionPaymentId(Integer.parseInt(userMap.get("subscriptionPaymentId")));

		return dto;
	}

	@Override
	public Viewers get_viwer_id_by_sID(Integer subscriptionPaymentId) {

		SubscriptionPayments subPay = paymentService.getSubscriptionPayments(subscriptionPaymentId);
		if (subPay != null) {
			Viewers viewer = viewerService.getViewerByid(subPay.getViewerID());
			return viewer;

		}
		return new Viewers();
	}

	@Override
	public String idabiz_subscribe(Integer viewerId, String mobileNo, Integer days) {
		mobileNo = mobileNo.replace("tel:+", "");
		addIdabiz_subscribe(viewerId, mobileNo, days);
		return "success";
	}

	@Override
	public HashMap<String, String> pinSubscriptionConfirm(Map<String, String> userMap,
			SubscriptionPaymentDto subscriptionPaymentDto, String systemToken) throws Exception {
		String message = "";

		String accessToken = dialogClient.create_access_token();
		DialogOtpConfirmDto dialogOtpConfirmDto = getDialogOtpConfirmDto(userMap);
		Double amount = paymentCalculation.getAmountByDays(dialogOtpConfirmDto.getDay());
		DialogOtpDto dialogOtpDto = dialogClient.pinSubscriptionConfirm(dialogOtpConfirmDto, amount, accessToken);

		if (dialogOtpDto.getStatusCode().equals("SUCCESS")) {

			String mobileNo = dialogOtpDto.getMsisdn();
			mobileNo = mobileNo.replace("tel:", "");
			Viewers viewers = viewerService.updateViewerMobileNumber(mobileNo, subscriptionPaymentDto.getViewerId());
			if (dialogOtpDto.getStatus().equals("SUBSCRIBED")) {
				message = "SUBSCRIBED";

				Ideabiz ideabiz = getIdeabiz(subscriptionPaymentDto.getViewerId(), mobileNo,
						dialogOtpConfirmDto.getDay());
				if (ideabizRepository.save(ideabiz) != null) {
					int invoiceId = proceedPayment(viewers, dialogOtpConfirmDto.getDay(), "Ideabiz", amount);
					invoiceService.updatePolicyExpireIdeaBiz(invoiceId, subscriptionPaymentDto.getViewerId());
					ViewerUnsubcriptionDto dto = getViewerUnsubcriptionDto(mobileNo, viewers.getViewerId());
					viewerUnsubcriptionService.addViewerUnsubcription(dto);
				} else {
					message = "SUBSCRIBED SAVE ERROR";
					paymentLogService.createPaymentLog("Dialog", "-", dialogOtpDto.getTransactionOperationStatus(),
							subscriptionPaymentDto.getViewerId(), mobileNo, dialogOtpDto.getResult());

					System.out.println("ALREADY SUBSCRIBED");
				}
			} else {
				message = "ALREADY SUBSCRIBED";
				paymentLogService.createPaymentLog("Dialog", "-", dialogOtpDto.getTransactionOperationStatus(),
						subscriptionPaymentDto.getViewerId(), mobileNo, dialogOtpDto.getResult());
				
				processUnsubscribe(subscriptionPaymentDto);

				message = "SUBSCRIBED";

				Ideabiz ideabiz = getIdeabiz(subscriptionPaymentDto.getViewerId(), mobileNo,
						dialogOtpConfirmDto.getDay());
				if (ideabizRepository.save(ideabiz) != null) {
					int invoiceId = proceedPayment(viewers, dialogOtpConfirmDto.getDay(), "Ideabiz", amount);
					invoiceService.updatePolicyExpireIdeaBiz(invoiceId, subscriptionPaymentDto.getViewerId());
					ViewerUnsubcriptionDto dto = getViewerUnsubcriptionDto(mobileNo, viewers.getViewerId());
					viewerUnsubcriptionService.addViewerUnsubcription(dto);
				} else {
					message = "SUBSCRIBED SAVE ERROR";
					paymentLogService.createPaymentLog("Dialog", "-", dialogOtpDto.getTransactionOperationStatus(),
							subscriptionPaymentDto.getViewerId(), mobileNo, dialogOtpDto.getResult());

					System.out.println("ALREADY SUBSCRIBED");
				}

				System.out.println("ALREADY SUBSCRIBED");
			}

		} else {
			paymentLogService.createPaymentLog("Dialog", "-", dialogOtpDto.getMessage(),
					subscriptionPaymentDto.getViewerId(), dialogOtpDto.getMsisdn(), dialogOtpDto.getResult());
		}

		HashMap<String, String> result_obj = new LinkedHashMap<String, String>();
		result_obj.put("PIN-SUBMIT", dialogOtpDto.getStatusCode());
		result_obj.put("SERVER-REF", dialogOtpDto.getServerRef());
		result_obj.put("STATUS", dialogOtpDto.getStatus());
		result_obj.put("SERVICE-ID", dialogOtpDto.getServiceId());
		result_obj.put("ACCESS-TOKEN", accessToken);
		result_obj.put("MSISDN", dialogOtpDto.getMsisdn());
		result_obj.put("MESSAGE", message);
		result_obj.put("SYSTEM-TOKEN", systemToken);

		return result_obj;
	}

	@Override
	public ViewerUnsubcriptionDto getViewerUnsubcriptionDto(String mobileNo, Integer viewerId) {
		ViewerUnsubcriptionDto dto = new ViewerUnsubcriptionDto();
		dto.setCreatedDateTime(new Date());
		dto.setLastUpdatedTime(new Date());
		dto.setMobileNumber(mobileNo);
		dto.setViewerId(viewerId);
		dto.setSubcriptionType("SUBCRIBE");
		dto.setServiceProvider("Dialog");
		return dto;
	}

	@Override
	public Ideabiz getIdeabiz(Integer viewerId, String mobileNo, Integer day) {
		Date date = new Date();
		long time = date.getTime();
		Timestamp ts = new Timestamp(time);

		Ideabiz ideabiz = new Ideabiz();
		ideabiz.setMobile(mobileNo);
		ideabiz.setViwerId(viewerId);
		ideabiz.setCreatedDate(ts);
		ideabiz.setSubscribe(1);
		ideabiz.setSubscribedDays(day);
		return ideabiz;
	}

	@Override
	public boolean processUnsubscribe(SubscriptionPaymentDto subscriptionPayment) {
		Viewers viewers = viewerService.getViewerByid(subscriptionPayment.getViewerId());
		Ideabiz ideabiz = findOneByViwerIdAndSubscribe(subscriptionPayment.getViewerId(), 1);

		String access_token = create_access_token();

		JSONObject jsonObj = dialogClient.unsubscribe(access_token, viewers, ideabiz.getSubscribedDays());

		try {

			if (jsonObj.get("statusCode").equals("SUCCESS")) {
				unsubscribe(viewers.getViewerId());

				ViewerUnsubcriptionDto dto = new ViewerUnsubcriptionDto();
				dto.setCreatedDateTime(new Date());
				dto.setLastUpdatedTime(new Date());
				dto.setMobileNumber(viewers.getMobileNumber());
				dto.setViewerId(viewers.getViewerId());
				dto.setSubcriptionType("UNSUBCRIBE");
				dto.setServiceProvider("Dialog");
				viewerUnsubService.addViewerUnsubcription(dto);

				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public HashMap<String, String> pinSubcription(String mobile_no, String subscriptionPaymentId, String day)
			throws Exception {
		String accessToken = create_access_token();
		
		
		String statusCode = "FAIL";
		String serverRef = "";
		String msisdn = "";
		String serviceId = "";
		String is_dialog = "dialog";
		boolean isdialog = appUtility.getIsDialogNumber(mobile_no);
		HashMap<String, String> result_obj = new LinkedHashMap<String, String>();
		
		if (isdialog) {
			JSONObject jsonObj = dialogClient.pinSubscription(mobile_no, subscriptionPaymentId, day, accessToken);
			try {
				System.out.println("start");
				
				JSONObject jsonObjRef = (JSONObject) jsonObj.get("data");

				statusCode = jsonObj.get("statusCode").toString();
				serverRef = jsonObjRef.get("serverRef").toString();
				msisdn = jsonObjRef.get("msisdn").toString();
				serviceId = jsonObjRef.get("serviceId").toString();

				System.out.println(serverRef);
			} catch (Exception e) {
				logger.info(e.getMessage());
			}
			System.out.println(statusCode);

			result_obj.put("OTP_SEND", statusCode);
			result_obj.put("SERVER_REF", serverRef);
			result_obj.put("MSISDN", msisdn);
			result_obj.put("SERVICE_ID", serviceId);
			result_obj.put("ACCESS_TOKEN", accessToken);
			result_obj.put("IS_DIALOG", is_dialog);
		} else {
			is_dialog = "none";
			result_obj.put("OTP_SEND", statusCode);
			result_obj.put("IS_DIALOG", is_dialog);
		}
		
		return result_obj;
	}

	/*
	 * @Override public HashMap<String, String> pin_subscription_confirm(Map<String,
	 * String> userMap, SubscriptionPaymentDto subscriptionPaymentDto, String
	 * systemToken) throws Exception { String message = "";
	 * 
	 * String accessToken = dialogClient.create_access_token(); DialogOtpConfirmDto
	 * dialogOtpConfirmDto = getDialogOtpConfirmDto(userMap); Double amount =
	 * paymentCalculation.getAmountByDays(dialogOtpConfirmDto.getDay());
	 * DialogOtpDto dialogOtpDto =
	 * dialogClient.pinSubscriptionConfirm(dialogOtpConfirmDto, amount,
	 * accessToken);
	 * 
	 * if (dialogOtpDto.getStatusCode().equals("SUCCESS")) {
	 * 
	 * String mobileNo = dialogOtpDto.getMsisdn(); mobileNo =
	 * mobileNo.replace("tel:", "");
	 * viewerService.updateViewerMobileNumber(mobileNo,
	 * subscriptionPaymentDto.getViewerId());
	 * 
	 * if (dialogOtpDto.getStatus().equals("SUBSCRIBED")) {
	 * ideabizService.idabiz_subscribe(subscriptionPaymentDto.getViewerId(),
	 * dialogOtpDto.getMsisdn(), dialogOtpConfirmDto.getDay());
	 * 
	 * System.out.println("MOBILE NEW" + mobileNo);
	 * 
	 * message = "SUBSCRIBED";
	 * 
	 * int invoice_id =
	 * ideabizService.proceed_payment(subscriptionPaymentDto.getViewerId(),
	 * dialogOtpConfirmDto.getDay(), "Ideabiz", amount);
	 * 
	 * invoiceService.updatePolicyExpireIdeaBiz(invoice_id,
	 * subscriptionPaymentDto.getViewerId());
	 * 
	 * ViewerUnsubcriptionDto dto = new ViewerUnsubcriptionDto();
	 * dto.setCreatedDateTime(new Date()); dto.setLastUpdatedTime(new Date());
	 * dto.setMobileNumber(mobileNo);
	 * dto.setViewerId(subscriptionPaymentDto.getViewerId());
	 * dto.setSubcriptionType("SUBCRIBE"); dto.setServiceProvider("Dialog");
	 * 
	 * viewerUnsubcriptionService.addViewerUnsubcription(dto);
	 * 
	 * System.out.println("PAYMENT API"); } else { message = "ALREADY SUBSCRIBED";
	 * paymentLogService.createPaymentLog("Dialog", "-",
	 * dialogOtpDto.getTransactionOperationStatus(),
	 * subscriptionPaymentDto.getViewerId(), mobileNo, dialogOtpDto.getResult());
	 * 
	 * System.out.println("ALREADY SUBSCRIBED"); } } else {
	 * 
	 * paymentLogService.createPaymentLog("Dialog", "-", dialogOtpDto.getMessage(),
	 * subscriptionPaymentDto.getViewerId(), dialogOtpDto.getMsisdn(),
	 * dialogOtpDto.getResult()); }
	 * 
	 * HashMap<String, String> result_obj = new LinkedHashMap<String, String>();
	 * result_obj.put("PIN-SUBMIT", dialogOtpDto.getStatusCode());
	 * result_obj.put("SERVER-REF", dialogOtpDto.getServerRef());
	 * result_obj.put("STATUS", dialogOtpDto.getStatus());
	 * result_obj.put("SERVICE-ID", dialogOtpDto.getServiceId());
	 * result_obj.put("ACCESS-TOKEN", accessToken); result_obj.put("MSISDN",
	 * dialogOtpDto.getMsisdn()); result_obj.put("MESSAGE", message);
	 * result_obj.put("SYSTEM-TOKEN", systemToken);
	 * 
	 * return result_obj; }
	 */

}
