package com.kiki_cpg.development.service.impl;

import java.util.Date;
import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kiki_cpg.development.client.MobitelClient;
import com.kiki_cpg.development.controller.MainController;
import com.kiki_cpg.development.dto.SubscriptionPaymentDto;
import com.kiki_cpg.development.entity.MerchantAccount;
import com.kiki_cpg.development.entity.ViewerSubscription;
import com.kiki_cpg.development.entity.ViewerTrialPeriodAssociation;
import com.kiki_cpg.development.entity.Viewers;
import com.kiki_cpg.development.enums.SubscriptionType;
import com.kiki_cpg.development.enums.TransactionType;
import com.kiki_cpg.development.repository.MerchantAccountRepository;
import com.kiki_cpg.development.repository.SubscriptionRepository;
import com.kiki_cpg.development.repository.ViewerTrialPeriodAssociationRepository;
import com.kiki_cpg.development.service.ContentService;
import com.kiki_cpg.development.service.InvoiceService;
import com.kiki_cpg.development.service.MobitelService;
import com.kiki_cpg.development.service.MobitelWsClientService;
import com.kiki_cpg.development.service.PaymentLogService;
import com.kiki_cpg.development.service.PaymentService;
import com.kiki_cpg.development.service.SubscriptionPaymentService;
import com.kiki_cpg.development.util.AppUtility;

@Service
public class MobitelServiceImpl implements MobitelService {

	final static Logger logger = LoggerFactory.getLogger(MobitelServiceImpl.class);

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	private ViewerTrialPeriodAssociationRepository viewerTrialPeriodAssociationRepository;

	@Autowired
	private MobitelClient mobitelClient;

	@Autowired
	private MerchantAccountRepository merchantAccountRepository;

	@Autowired
	private SubscriptionPaymentService paymentService;

	@Autowired
	private ContentService contentService;

	@Autowired
	private PaymentLogService paymentLogService;

	@Autowired
	private AppUtility appUtility;

	@Override
	public SubscriptionPaymentDto getSubscriptionPaymentDto(int paymentMethodId, int subscriptionPaymentId,
			String numberString, boolean isTrial, Integer viewerId) throws Exception {

		SubscriptionPaymentDto dto = new SubscriptionPaymentDto();

		ViewerSubscription viewerSubscription = subscriptionRepository
				.findOneBySubscriptionTypeAndViewers(SubscriptionType.MOBITEL_ADD_TO_BILL, viewerId);
		if (viewerSubscription != null) {
			dto.setMobitelSubscribe(true);
		} else {
			try {
				if (!numberString.isEmpty() && !numberString.equals("null")) {
					logger.info("mobileNo = " + numberString);
					if (appUtility.getIsMobitelNumber(numberString)) {
						if (isTrial) { // add trial period

							ViewerTrialPeriodAssociation viewerTrialPeriodAssociation = viewerTrialPeriodAssociationRepository
									.findOneByViewerId(viewerId);
							if (viewerTrialPeriodAssociation == null) {
								viewerTrialPeriodAssociation = new ViewerTrialPeriodAssociation();
								viewerTrialPeriodAssociation.setOnGoing(false);
								viewerTrialPeriodAssociation.setActivated(false);
								viewerTrialPeriodAssociation.setViewerId(viewerId);

								viewerTrialPeriodAssociationRepository.save(viewerTrialPeriodAssociation);
							}
						}
						dto.setMobitelConnection(true);
					} else {
						dto.setMobitelConnection(false);
					}
				} else {
					dto.setMobitelConnection(false);
				}
			} catch (Exception e) {
				logger.info("exception occures when checking is mobitel number = " + e);
				throw new RuntimeException("exception occures when checking is mobitel number = " + e);
			}
		}
		return dto;
	}

	@Override
	public String activateDataBundle(String mobileNo, Integer viewerId, String activationStatus) {
		mobileNo = "0" + appUtility.getNineDigitMobileNumber(mobileNo);
		logger.info("called to activate Data");
		try {

			ResponseEntity<?> res = mobitelClient.createAccessCode();
			String returnValue = "0002";

			double amount = 5;
			TransactionType transactionType = TransactionType.ACTIVATE;
			if (activationStatus.equals("2")) {
				transactionType = TransactionType.DEACTIVATE;
				amount = 0;
			}

			int lastTransaciontId = 0;
			try {
				@SuppressWarnings("unchecked")
				LinkedHashMap<String, String> response = (LinkedHashMap<String, String>) res.getBody();
				logger.info("get access token");

				logger.info("access token : " + response.get("access_token"));
				lastTransaciontId = merchantAccountRepository.getLastTransactionId();

				while (returnValue.equals("0002")) {
					ResponseEntity<?> resp = mobitelClient.mobitelManage(response.get("access_token"), activationStatus,
							mobileNo, lastTransaciontId);
					@SuppressWarnings("unchecked")
					LinkedHashMap<String, String> response2 = (LinkedHashMap<String, String>) resp.getBody();
					returnValue = response2.get("resultCode");
					logger.info("while return value : " + response2.get("resultCode") + ", last transaction id : "
							+ lastTransaciontId);

					if (returnValue.equals("0002")) {
						lastTransaciontId = lastTransaciontId + 1;
						MerchantAccount merchantAccount = getMerchantAccount(lastTransaciontId, amount, transactionType,
								viewerId, false);
						merchantAccountRepository.save(merchantAccount);
					}

				}

			} catch (Exception e) {
				logger.info("activation unsuccessful error occurred with transaction id  " + lastTransaciontId + 1
						+ "Exception = " + e);
				logger.info(e.getMessage());
			}

			if (returnValue.equals("1000")) { // add record to merchant account table
				logger.info("add record to merchant account table ");
				MerchantAccount merchantAccount = getMerchantAccount(lastTransaciontId + 1, amount, transactionType,
						viewerId, true);

				merchantAccountRepository.save(merchantAccount);
				String paymentStatus = "Success";
				String responseMsg = "Activation Successful";
				logger.info(paymentStatus + " : " + responseMsg);
			} else {
				logger.info("activation unsuccessful error occurred with transaction id " + lastTransaciontId);
				System.out.println("Activation Unsuccessful Error Occurred With Transaction Id " + lastTransaciontId);
				logger.info("viewer id " + viewerId);
				MerchantAccount merchantAccount = getMerchantAccount(lastTransaciontId + 1, amount, transactionType,
						viewerId, false);

				merchantAccountRepository.save(merchantAccount);
				String paymentStatus = "Fail";
				String responseMsg = "Activation Unsuccessful Error Occurred With Transaction Id";

				logger.info(paymentStatus + " : " + responseMsg);
			}

			return returnValue;
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@Override
	public MerchantAccount getMerchantAccount(int lastTransaciontId, double amount, TransactionType transactionType,
			Integer viewerId, boolean isSuccess) {
		MerchantAccount merchantAccount = new MerchantAccount();
		merchantAccount.setId(0);
		merchantAccount.setServiceId("KIKI");
		merchantAccount.setTransactionId(lastTransaciontId);
		merchantAccount.setAmount(amount);
		merchantAccount.setDate(new Date());
		merchantAccount.setViewerId(viewerId);
		merchantAccount.setSuccess(isSuccess);
		merchantAccount.setTransactionType(transactionType);
		return merchantAccount;
	}

	@Override
	public String pay(String mobileNo, Integer viewerId, String activationStatus,
			SubscriptionPaymentDto subscriptionPaymentDto) {
		logger.info("called to pay");
		String resp = activateDataBundle(mobileNo, viewerId, activationStatus);
		logger.info("activateDataBundle resp : " + resp);
		paymentLogService.createPaymentLog("Mobitel", resp, "-", viewerId, mobileNo, "");
		if (resp.equals("1000")) {
			if (proceedPayment(viewerId, 1, mobileNo, subscriptionPaymentDto) > 0) {
				try {
					int responseCode = mobitelClient.updateOneCCTool(true, mobileNo, new Date(), null);
				} catch (Exception e) {
					logger.info("updating one cc tool failed");
				}
				return "success";
			} else {
				return "error";
			}
		}
		return "error";
	}

	@Override
	public int proceedPayment(Integer viewerId, Integer subscribed_days, String mobileNo,
			SubscriptionPaymentDto subscriptionPaymentDto) {
		try {

			if (subscribed_days == 1) {
				contentService.updateViewerPolicies(viewerId, 81, false);

			} else if (subscribed_days == 7) {
				contentService.updateViewerPolicies(viewerId, 106, false);
			}

			mobileNo = "0" + appUtility.getNineDigitMobileNumber(mobileNo);
			paymentService.updateStatus(subscriptionPaymentDto.getSubscriptionPaymentId());
			paymentService.updateViewerSubscription(viewerId, SubscriptionType.MOBITEL_ADD_TO_BILL, new Date(),
					mobileNo);
			return 1;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public boolean processUnsubscribe(SubscriptionPaymentDto subscriptionPayment) {
		if(paymentService.updateViewerSubscription(subscriptionPayment.getViewerId(), SubscriptionType.NONE, new Date(),
				subscriptionPayment.getMobile())) {
			return true;
		}
		return false;
	}

}
