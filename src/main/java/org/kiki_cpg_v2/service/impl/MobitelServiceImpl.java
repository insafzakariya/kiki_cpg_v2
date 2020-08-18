package org.kiki_cpg_v2.service.impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.kiki_cpg_v2.client.MobitelClient;
import org.kiki_cpg_v2.dto.request.ViewerPolicyUpdateRequestDto;
import org.kiki_cpg_v2.entity.MerchantAccountEntity;
import org.kiki_cpg_v2.entity.ViewerEntity;
import org.kiki_cpg_v2.entity.ViewerSubscriptionEntity;
import org.kiki_cpg_v2.enums.SubscriptionType;
import org.kiki_cpg_v2.enums.TransactionType;
import org.kiki_cpg_v2.repository.MerchantAccountRepository;
import org.kiki_cpg_v2.repository.ViewerRepository;
import org.kiki_cpg_v2.repository.ViewerSubscriptionRepository;
import org.kiki_cpg_v2.service.CronViewerRepostService;
import org.kiki_cpg_v2.service.MobitelService;
import org.kiki_cpg_v2.service.PaymentLogService;
import org.kiki_cpg_v2.service.SubscriptionService;
import org.kiki_cpg_v2.service.ViewerPolicyService;
import org.kiki_cpg_v2.service.ViewerService;
import org.kiki_cpg_v2.service.ViewerSubscriptionService;
import org.kiki_cpg_v2.service.ViewerUnsubscriptionService;
import org.kiki_cpg_v2.util.AppUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MobitelServiceImpl implements MobitelService {

	final static Logger logger = LoggerFactory.getLogger(MobitelServiceImpl.class);

	@Autowired
	private ViewerRepository viewerRepository;

	@Autowired
	private ViewerPolicyService viewerPolicyService;

	@Autowired
	private ViewerSubscriptionRepository viewerSubscriptionRepository;

	@Autowired
	private PaymentLogService paymentLogService;

	@Autowired
	private AppUtility appUtility;

	@Autowired
	private MerchantAccountRepository merchantAccountRepository;

	@Autowired
	private MobitelClient mobitelClient;

	@Autowired
	private ViewerService viewerService;

	@Autowired
	private ViewerSubscriptionService viewerSubscriptionService;

	@Autowired
	private SubscriptionService subscriptionService;

	@Autowired
	private CronViewerRepostService cronViewerRepostService;

	@Autowired
	private ViewerUnsubscriptionService viewerUnsubscriptionService;

	@Override
	public boolean processUnsubscriptionMobitel(Integer viewerid, String mobile) throws Exception {
		if (updateViewerSubscription(viewerid, SubscriptionType.NONE, new Date(), mobile)) {
			try {
				viewerUnsubscriptionService.save(mobile, viewerid, "UNSUBSCRIBE", "Mobitel", false);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean updateViewerSubscription(Integer viewerid, SubscriptionType type, Date date, String mobile)
			throws Exception {
		ViewerSubscriptionEntity entity = viewerSubscriptionRepository.findOneByViewers(viewerid);
		if (entity == null) {
			entity = new ViewerSubscriptionEntity();
			entity.setDate(new Date());
			entity.setViewers(viewerid);
		}
		entity.setSubscriptionType(type);
		if (viewerSubscriptionRepository.save(entity) != null) {
			return true;
		}

		return false;
	}

	@Override
	@Transactional
	public String pay(String mobileNo, Integer viewerId, String activationStatus, Integer subscriptionPaymentId,
			Integer subscribedDays) throws Exception {
		logger.info("called to pay");
		String resp = activateDataBundle(mobileNo, viewerId, activationStatus, false, null);
		logger.info("activateDataBundle resp : " + resp);
		paymentLogService.createPaymentLog("Mobitel", resp, "-", viewerId, mobileNo, "");

		if (resp.equals("1000")) {
			String paymentResp = proceedPayment(viewerId, subscribedDays, mobileNo, subscriptionPaymentId);
			if (paymentResp.equalsIgnoreCase("success")) {
				try {
					mobitelClient.updateOneCCTool(true, mobileNo, new Date(), null);
				} catch (Exception e) {
					logger.info("updating one cc tool failed");
				}
				try {
					viewerUnsubscriptionService.save(mobileNo, viewerId, "SUBSCRIBE", "Mobitel", false);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return "success";
			} else {
				return paymentResp;
			}
		} else if (resp.equals("0006")) {
			return "Insufficient balance to activate this service";
		} else if (resp.equals("0005")) {
			ViewerSubscriptionEntity entity = viewerSubscriptionRepository.findOneByViewers(viewerId);
			if (entity != null) {
				if (entity.getSubscriptionType().equals(SubscriptionType.MOBITEL_ADD_TO_BILL)) {
					return "Already Subscribed";
				} else {
					if (deactivePreviousViewersByMobile(mobileNo, viewerId, true, subscriptionPaymentId,
							subscribedDays)) {
						if (viewerSubscriptionService.changeStatus(viewerId, SubscriptionType.MOBITEL_ADD_TO_BILL)) {
							try {
								viewerUnsubscriptionService.save(mobileNo, viewerId, "SUBSCRIBE", "Mobitel", false);
							} catch (Exception e) {
								e.printStackTrace();
							}
							return "Activated";
						} else {
							return "Activattion Error";
						}
					} else {
						return "Deactivation Error at Account Transfer.";
					}

				}
			} else {
				if (deactivePreviousViewersByMobile(mobileNo, viewerId, false, subscriptionPaymentId, subscribedDays)) {
					String paymentResp = proceedPayment(viewerId, subscribedDays, mobileNo, subscriptionPaymentId);
					if (paymentResp.equals("success")) {
						try {
							viewerUnsubscriptionService.save(mobileNo, viewerId, "SUBSCRIBE", "Mobitel", false);
						} catch (Exception e) {
							e.printStackTrace();
						}
						return "transfered";
					} else {
						return paymentResp;
					}
				} else {
					return "Deactivation Error at Account Transfer.";
				}
			}

		}
		return "error";
	}

	@Override
	public boolean deactivePreviousViewersByMobile(String mobileNo, Integer viewerId, boolean isTransfer,
			Integer subscriptionPaymentId, Integer subscribedDays) throws Exception {
		List<ViewerEntity> viewerEntities = viewerRepository.findByIdNotAndMobileNumberEndingWith(viewerId, mobileNo);

		Integer packageId = -1;
		if (subscribedDays == 1) {
			packageId = 81;

		} else if (subscribedDays == 7) {
			packageId = 106;
		}

		if (viewerEntities != null && !viewerEntities.isEmpty()) {
			for (ViewerEntity viewerEntity : viewerEntities) {
				if (viewerSubscriptionService.inavtive(viewerEntity.getId(), viewerEntity.getMobileNumber())) {
					viewerPolicyService.deactivatePolicy(viewerEntity.getId(), viewerId, isTransfer, packageId);
					if (!viewerPolicyService.checkStatus(viewerId, packageId)) {
						// proceedPayment(viewerId, subscribedDays, mobileNo, subscriptionPaymentId);
					}
				}
			}
			return true;

		} else {
			return true;
		}
	}

	@Override
	public String proceedPayment(Integer viewerId, Integer subscribedDays, String mobileNo,
			Integer subscriptionPaymentId) throws Exception {
		String resp = "Fail";
		try {

			Integer packageId = -1;
			if (subscribedDays == 1) {
				packageId = 81;

			} else if (subscribedDays == 7) {
				packageId = 106;
			}

			mobileNo = "0" + appUtility.getNineDigitMobileNumber(mobileNo);

			if (packageId > 0) {
				ViewerPolicyUpdateRequestDto dto = new ViewerPolicyUpdateRequestDto();
				dto.setPackageId(packageId);
				dto.setViewerId(viewerId);
				viewerService.updateViewerMobileNumber(mobileNo, viewerId);
				if (viewerPolicyService.updateViewerPolicy(dto).equalsIgnoreCase("success")) {
					if (subscriptionService.updateStatus(subscriptionPaymentId)) {
						if (viewerSubscriptionService.updateViewerSubscription(viewerId,
								SubscriptionType.MOBITEL_ADD_TO_BILL, new Date(), mobileNo)) {
							resp = "success";
						} else {
							resp = "Could not update Viewer Subscription";
						}
					} else {
						resp = "Could Not update session";
					}
				} else {
					resp = "Policy update Error";
				}

			} else {
				resp = "Package not found";
			}

		} catch (Exception e) {
			e.printStackTrace();
			resp = e.getMessage();
		}
		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String activateDataBundle(String mobileNo, Integer viewerId, String activationStatus,
			boolean isUpdateCronViewer, Integer cronId) throws Exception {
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

			String serverResponse = "";

			int lastTransaciontId = 0;
			try {
				LinkedHashMap<String, String> response = (LinkedHashMap<String, String>) res.getBody();
				logger.info("get access token");

				logger.info("access token : " + response.get("access_token"));
				lastTransaciontId = merchantAccountRepository.getLastTransactionId();

				while (returnValue.equals("0002")) {
					ResponseEntity<?> resp = mobitelClient.mobitelManage(response.get("access_token"), activationStatus,
							mobileNo, lastTransaciontId);
					LinkedHashMap<String, String> response2 = (LinkedHashMap<String, String>) resp.getBody();
					serverResponse = String.valueOf((LinkedHashMap<String, String>) resp.getBody());
					returnValue = response2.get("resultCode");
					logger.info("while return value : " + response2.get("resultCode") + ", last transaction id : "
							+ lastTransaciontId);

					if (returnValue.equals("0002")) {
						lastTransaciontId = lastTransaciontId + 1;
						MerchantAccountEntity merchantAccountEntity = getMerchantAccountEntity(lastTransaciontId,
								amount, transactionType, viewerId, false);
						merchantAccountRepository.save(merchantAccountEntity);
					}

				}

			} catch (Exception e) {
				logger.info("activation unsuccessful error occurred with transaction id  " + lastTransaciontId + 1
						+ "Exception = " + e);
				logger.info(e.getMessage());
			}
			String paymentStatus = "Fail";
			String responseMsg = "Fail";
			if (returnValue.equals("1000")) { // add record to merchant account table
				logger.info("add record to merchant account table ");
				MerchantAccountEntity merchantAccountEntity = getMerchantAccountEntity(lastTransaciontId + 1, amount,
						transactionType, viewerId, true);

				merchantAccountRepository.save(merchantAccountEntity);
				paymentStatus = "Success";
				responseMsg = "Activation Successful";
				logger.info(paymentStatus + " : " + responseMsg);
			} else {
				logger.info("activation unsuccessful error occurred with transaction id " + lastTransaciontId);
				System.out.println("Activation Unsuccessful Error Occurred With Transaction Id " + lastTransaciontId);
				logger.info("viewer id " + viewerId);
				MerchantAccountEntity merchantAccountEntity = getMerchantAccountEntity(lastTransaciontId + 1, amount,
						transactionType, viewerId, false);

				merchantAccountRepository.save(merchantAccountEntity);
				paymentStatus = "Fail";
				responseMsg = "Activation Unsuccessful Error Occurred With Transaction Id";

				logger.info(paymentStatus + " : " + responseMsg);
			}

			if (isUpdateCronViewer) {
				cronViewerRepostService.save(viewerId, paymentStatus, amount, responseMsg, serverResponse, cronId);
			}

			return returnValue;
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@Override
	public MerchantAccountEntity getMerchantAccountEntity(int lastTransaciontId, double amount,
			TransactionType transactionType, Integer viewerId, boolean isSuccess) throws Exception {
		MerchantAccountEntity entity = new MerchantAccountEntity();
		entity.setId(0);
		entity.setServiceId("KIKI");
		entity.setTransactionId(lastTransaciontId);
		entity.setAmount(amount);
		entity.setDate(new Date());
		entity.setViewerId(viewerId);
		entity.setSuccess(isSuccess);
		entity.setTransactionType(transactionType);
		return entity;
	}

	@Override
	@Transactional
	public String cronPay(String mobileNo, Integer viewerId, String activationStatus, Integer subscribedDays,
			boolean isUpdateCronViewer, Integer cronId) throws Exception {

		logger.info("called to pay");
		String resp = activateDataBundle(mobileNo, viewerId, activationStatus, isUpdateCronViewer, cronId);
		logger.info("activateDataBundle resp : " + resp);
		paymentLogService.createPaymentLog("Mobitel", resp, "-", viewerId, mobileNo, "");

		if (resp.equals("1000")) {
			Integer packageId = -1;
			if (subscribedDays == 1) {
				packageId = 81;

			} else if (subscribedDays == 7) {
				packageId = 106;
			}

			String paymentResp = cronProceedPayment(viewerId, packageId, mobileNo);
			return paymentResp;

		} else {
			/*
			 * try { if (!viewerPolicyService.findViewerPoliceExist(viewerId, 1)) {
			 * cronProceedPayment(viewerId, 1, mobileNo); } } catch (Exception e) {
			 * e.printStackTrace(); }
			 */
			logger.info("payment is not successful");

			return "payment is not successful";
		}
	}

	@Override
	public String cronProceedPayment(Integer viewerId, Integer packageId, String mobileNo) throws Exception {
		String resp = "Fail";
		try {
			mobileNo = "0" + appUtility.getNineDigitMobileNumber(mobileNo);

			if (packageId > 0) {
				ViewerPolicyUpdateRequestDto dto = new ViewerPolicyUpdateRequestDto();
				dto.setPackageId(packageId);
				dto.setViewerId(viewerId);
				// viewerService.updateViewerMobileNumber(mobileNo, viewerId);
				if (viewerPolicyService.updateViewerPolicy(dto).equalsIgnoreCase("success")) {
					resp = "success";
				} else {
					resp = "Policy update Error";
				}

			} else {
				resp = "Package not found";
			}

		} catch (Exception e) {
			e.printStackTrace();
			resp = e.getMessage();
		}
		return resp;
	}

}
