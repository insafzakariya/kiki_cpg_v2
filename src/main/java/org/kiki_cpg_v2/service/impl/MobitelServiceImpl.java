package org.kiki_cpg_v2.service.impl;

import java.util.Date;
import java.util.LinkedHashMap;
//import java.util.List;

import org.kiki_cpg_v2.client.MobitelClient;
import org.kiki_cpg_v2.dto.PaymentRefDto;
import org.kiki_cpg_v2.dto.request.TransactionBeginDto;
import org.kiki_cpg_v2.dto.request.ViewerPolicyUpdateRequestDto;
//import org.kiki_cpg_v2.entity.MerchantAccountEntity;
import org.kiki_cpg_v2.entity.PackageConfigEntity;
import org.kiki_cpg_v2.entity.SubscriptionEntity;
import org.kiki_cpg_v2.entity.SubscriptionInvoiceEntity;
import org.kiki_cpg_v2.entity.ViewerEntity;
//import org.kiki_cpg_v2.entity.ViewerEntity;
import org.kiki_cpg_v2.entity.ViewerSubscriptionEntity;
import org.kiki_cpg_v2.enums.SubscriptionType;
//import org.kiki_cpg_v2.enums.TransactionType;
/*import org.kiki_cpg_v2.repository.MerchantAccountRepository;*/
import org.kiki_cpg_v2.repository.SubscriptionInvoiceRepository;
import org.kiki_cpg_v2.repository.SubscriptionRepository;
import org.kiki_cpg_v2.repository.ViewerRepository;
//import org.kiki_cpg_v2.repository.ViewerRepository;
import org.kiki_cpg_v2.repository.ViewerSubscriptionRepository;
import org.kiki_cpg_v2.service.CronViewerRepostService;
import org.kiki_cpg_v2.service.IDGeneratorService;
import org.kiki_cpg_v2.service.MobitelService;
import org.kiki_cpg_v2.service.NotificationService;
import org.kiki_cpg_v2.service.PackageConfigService;
import org.kiki_cpg_v2.service.PaymentLogService;
import org.kiki_cpg_v2.service.SubscriptionPaymentService;
import org.kiki_cpg_v2.service.SubscriptionService;
import org.kiki_cpg_v2.service.ViewerNotificationService;
import org.kiki_cpg_v2.service.ViewerPolicyService;
import org.kiki_cpg_v2.service.ViewerService;
import org.kiki_cpg_v2.service.ViewerUnsubscriptionService;
import org.kiki_cpg_v2.util.AppConstant;
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

	/*
	 * @Autowired private ViewerRepository viewerRepository;
	 */

	@Autowired
	private ViewerPolicyService viewerPolicyService;

	@Autowired
	private ViewerSubscriptionRepository viewerSubscriptionRepository;

	@Autowired
	private PaymentLogService paymentLogService;

	@Autowired
	private AppUtility appUtility;

	/*
	 * @Autowired private MerchantAccountRepository merchantAccountRepository;
	 */

	@Autowired
	private MobitelClient mobitelClient;

	@Autowired
	private ViewerService viewerService;

	@Autowired
	private SubscriptionPaymentService subscriptionPaymentService;

	@Autowired
	private CronViewerRepostService cronViewerRepostService;

	@Autowired
	private ViewerUnsubscriptionService viewerUnsubscriptionService;
	
	@Autowired
	private SubscriptionService subscriptionService;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private SubscriptionRepository subscriptionRepository;
	
	@Autowired
	private SubscriptionInvoiceRepository subscriptionInvoiceRepository;
	
	@Autowired
	private IDGeneratorService idGeneratorService;
	
	@Autowired
	private ViewerNotificationService viewerNotificationService;
	
	@Autowired
	private ViewerRepository viewerRepository;
	
	@Autowired
	private PackageConfigService packageConfigService;
	
	

	@Override
	public boolean processUnsubscriptionMobitel(Integer viewerid, String mobile) throws Exception {
		/*
		 * if (updateViewerSubscription(viewerid, SubscriptionType.NONE, new Date(),
		 * mobile)) { try { viewerUnsubscriptionService.save(mobile, viewerid,
		 * "UNSUBSCRIBE", "Mobitel", false); } catch (Exception e) {
		 * e.printStackTrace(); } return true; } return false;
		 */
		
		if (subscriptionService.inavtive(viewerid, AppConstant.MOBITEL)) {
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
			 Integer planId, boolean trial) throws Exception {
		logger.info("called to pay");
		TransactionBeginDto transactionBeginDto = new TransactionBeginDto(mobileNo, viewerId, planId); 
		PaymentRefDto paymentRefDto = subscriptionService.getPaymentRefDto(transactionBeginDto, -1, -1);
		SubscriptionEntity subscriptionEntity = subscriptionService.getSubsctiptionEntity(transactionBeginDto, paymentRefDto, AppConstant.MOBITEL); 
		subscriptionEntity = subscriptionRepository.save(subscriptionEntity);
		ViewerEntity viewerEntity = viewerRepository.findById(viewerId).get();
		Integer subscribedDays = paymentRefDto.getDays();
		if(subscriptionEntity != null) {
			if(trial) {
				SubscriptionInvoiceEntity subscriptionInvoiceEntity = subscriptionService.getSubscriptionInvoiceEntity(subscriptionEntity.getMobile(), "1", subscriptionEntity, AppConstant.MOBITEL);
				subscriptionInvoiceEntity.setAmount(0.0);
				subscriptionInvoiceEntity.setExpireDate(appUtility.getbeforeDay(AppConstant.TRIAL_DAYS_MOBITEL, new Date()));
				subscriptionInvoiceRepository.save(subscriptionInvoiceEntity);
				
				viewerService.updateViewerMobileNumberAndTrial(mobileNo, viewerId,
						false);
				String paymentResp = proceedPayment(viewerId, subscribedDays, mobileNo, subscriptionPaymentId, planId, subscriptionEntity, trial);
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
					
					Double amount = subscriptionEntity.getAmount();
					String body = "";
					if (trial) {
						body = "Welcome to KiKi. You will be charged Rs "
									+ amount + "+tax/ "
									+ appUtility.getHutchPackageFrequance(
											paymentRefDto.getDays())
									+ " with a 3-day free trial";
						
					} else {
						body = "Welcome to KiKi. You will be charged Rs " + amount
								+ " + tax/ " + appUtility.getHutchPackageFrequance(
										paymentRefDto.getDays());
					}
					notificationService.sendSubscriptionNotification(body, viewerEntity.getDeviceId());
					
					return "success";
				} else {
					return paymentResp;
				}
			} else {
				String resp = activateDataBundle(mobileNo, subscriptionEntity, false, null);
				logger.info("activateDataBundle resp : " + resp);
				if (resp.equals("1000")) {
					String paymentResp = proceedPayment(viewerId, subscribedDays, mobileNo, subscriptionPaymentId, planId, subscriptionEntity, trial);
					if (paymentResp.equalsIgnoreCase("success")) {
						Double amount = subscriptionEntity.getAmount();
						String body = "";
						if (trial) {
							body = "Welcome to KiKi. You will be charged Rs "
										+ amount + "+tax/ "
										+ appUtility.getHutchPackageFrequance(
												paymentRefDto.getDays())
										+ " with a 3-day free trial";
							
						} else {
							body = "Welcome to KiKi. You will be charged Rs " + amount
									+ " + tax/ " + appUtility.getHutchPackageFrequance(
											paymentRefDto.getDays());
						}
						try {
							notificationService.sendSubscriptionNotification(body, viewerEntity.getDeviceId());
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						try {
							viewerNotificationService.save(body, viewerEntity.getId());
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						
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
					System.out.println("005");
					if(subscriptionEntity.getPolicyExpDate() == null || subscriptionEntity.getPolicyExpDate().getTime() < new Date().getTime()) {
						String paymentResp = proceedPayment(viewerId, subscribedDays, mobileNo, subscriptionPaymentId, planId, subscriptionEntity, trial);
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
						return "Already Subscribed";
					}

				}
			}
			
			return "error";
		} else {
			return "error";
		}
		
	}

	/*
	 * @Override public boolean deactivePreviousViewersByMobile(String mobileNo,
	 * Integer viewerId, boolean isTransfer, Integer subscriptionPaymentId, Integer
	 * subscribedDays) throws Exception { List<ViewerEntity> viewerEntities =
	 * viewerRepository.findByIdNotAndMobileNumberEndingWith(viewerId, mobileNo);
	 * 
	 * Integer packageId = packageConfigService.getPackageId(subscribedDays,
	 * AppConstant.MOBITEL);
	 * 
	 * if (viewerEntities != null && !viewerEntities.isEmpty()) { for (ViewerEntity
	 * viewerEntity : viewerEntities) { if
	 * (subscriptionService.inavtive(viewerEntity.getId(), AppConstant.MOBITEL)) {
	 * viewerPolicyService.deactivatePolicy(viewerEntity.getId(), viewerId,
	 * isTransfer, packageId); if (!viewerPolicyService.checkStatus(viewerId,
	 * packageId)) { // proceedPayment(viewerId, subscribedDays, mobileNo,
	 * subscriptionPaymentId); } } } return true;
	 * 
	 * } else { return true; } }
	 */

	@Override
	public String proceedPayment(Integer viewerId, Integer subscribedDays, String mobileNo,
			Integer subscriptionPaymentId, Integer planId, SubscriptionEntity subscriptionEntity, boolean trial) throws Exception {
		String resp = "Fail";
		try {

			Integer packageId = -1;
			
			if(trial) {
				PackageConfigEntity packageConfigEntity = packageConfigService.getFreeTrialPackageId(subscribedDays, AppConstant.TRIAL);
				packageId = packageConfigEntity.getPackageId();
				subscribedDays = packageConfigEntity.getDays();
			} else {
				System.out.println(subscribedDays);
				System.out.println(AppConstant.MOBITEL);
				packageId = packageConfigService.getPackageId(subscribedDays, AppConstant.MOBITEL);
			}

			mobileNo = "0" + appUtility.getNineDigitMobileNumber(mobileNo);
			
			System.out.println(packageId);

			if (packageId > 0) {
				ViewerPolicyUpdateRequestDto dto = new ViewerPolicyUpdateRequestDto();
				dto.setPackageId(packageId);
				dto.setViewerId(viewerId);
				viewerService.updateViewerMobileNumber(mobileNo, viewerId);
				if (viewerPolicyService.updateViewerPolicy(dto, subscribedDays).equalsIgnoreCase("success")) {
					if (subscriptionPaymentService.updateStatus(subscriptionPaymentId)) {
						
						subscriptionEntity.setPolicyExpDate(appUtility.getbeforeDay(subscribedDays, appUtility.getLastMinitue()));
						subscriptionEntity.setUpdateDate(new Date());
						
						if (subscriptionRepository.save(subscriptionEntity)!= null) {
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
	public String activateDataBundle(String mobileNo, SubscriptionEntity subscriptionEntity,
			boolean isUpdateCronViewer, Integer cronId) throws Exception {
		mobileNo = "0" + appUtility.getNineDigitMobileNumber(mobileNo);
		logger.info("called to activate Data");
		try {

			ResponseEntity<?> res = mobitelClient.createAccessCode();
			String returnValue = "0002";

			/*double amount = 5;
			TransactionType transactionType = TransactionType.ACTIVATE;
			if (activationStatus.equals("2")) {
				transactionType = TransactionType.DEACTIVATE;
				amount = 0;
			}*/

			String serverResponse = "";

			System.out.println(AppConstant.MOBITEL+(int)subscriptionEntity.getAmount().doubleValue());
			int lastTransaciontId = idGeneratorService.generateId(AppConstant.MOBITEL+(int)subscriptionEntity.getAmount().doubleValue());
			
			System.out.println(lastTransaciontId);
			
			try {
				LinkedHashMap<String, String> response = (LinkedHashMap<String, String>) res.getBody();
				logger.info("get access token");

				logger.info("access token : " + response.get("access_token"));

				while (returnValue.equals("0002")) {
					ResponseEntity<?> resp = mobitelClient.mobitelManage(response.get("access_token"), AppConstant.ACTIVE.toString(),
							mobileNo, lastTransaciontId);
					LinkedHashMap<String, String> response2 = (LinkedHashMap<String, String>) resp.getBody();
					serverResponse = String.valueOf((LinkedHashMap<String, String>) resp.getBody());
					returnValue = response2.get("resultCode");
					logger.info("while return value : " + response2.get("resultCode") + ", last transaction id : "
							+ lastTransaciontId);
					paymentLogService.createPaymentLog("Mobitel", returnValue, "-", subscriptionEntity.getViewerId(), mobileNo, AppConstant.DECLINE);
					if (returnValue.equals("0002")) {
						lastTransaciontId = idGeneratorService.generateId(AppConstant.MOBITEL+(int)subscriptionEntity.getAmount().doubleValue());
						SubscriptionInvoiceEntity subscriptionInvoiceEntity=  subscriptionService.getSubscriptionInvoiceEntity(mobileNo, "1", subscriptionEntity, AppConstant.MOBITEL);
						subscriptionInvoiceEntity.setDecision(AppConstant.DECLINE);
						subscriptionInvoiceEntity.setSuccess(AppConstant.INACTIVE);
						
						subscriptionInvoiceRepository.save(subscriptionInvoiceEntity);
						/*MerchantAccountEntity merchantAccountEntity = getMerchantAccountEntity(lastTransaciontId,
								amount, transactionType, viewerId, false);
						merchantAccountRepository.save(merchantAccountEntity);*/
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
				logger.info("add record to SubscriptionInvoice table ");
				/*MerchantAccountEntity merchantAccountEntity = getMerchantAccountEntity(lastTransaciontId + 1, amount,
						transactionType, viewerId, true);

				merchantAccountRepository.save(merchantAccountEntity);*/
				paymentLogService.createPaymentLog("Mobitel", returnValue, "-", subscriptionEntity.getViewerId(), mobileNo, AppConstant.ACCEPT);
				SubscriptionInvoiceEntity subscriptionInvoiceEntity=  subscriptionService.getSubscriptionInvoiceEntity(mobileNo, "1", subscriptionEntity, AppConstant.MOBITEL);
				subscriptionEntity.setPolicyExpDate(appUtility.getbeforeDay(subscriptionEntity.getSubscribedDays(), appUtility.getLastMinitue()));
				subscriptionInvoiceRepository.save(subscriptionInvoiceEntity);
				paymentStatus = "Success";
				responseMsg = "Activation Successful";
				logger.info(paymentStatus + " : " + responseMsg);
			} else {
				logger.info("activation unsuccessful error occurred with transaction id " + lastTransaciontId);
				System.out.println("Activation Unsuccessful Error Occurred With Transaction Id " + lastTransaciontId);
				logger.info("viewer id " + subscriptionEntity.getViewerId());
				/*MerchantAccountEntity merchantAccountEntity = getMerchantAccountEntity(lastTransaciontId + 1, amount,
						transactionType, viewerId, false);

				merchantAccountRepository.save(merchantAccountEntity);*/
				paymentLogService.createPaymentLog("Mobitel", returnValue, "-", subscriptionEntity.getViewerId(), mobileNo, AppConstant.DECLINE);
				SubscriptionInvoiceEntity subscriptionInvoiceEntity=  subscriptionService.getSubscriptionInvoiceEntity(mobileNo, "1", subscriptionEntity, AppConstant.MOBITEL);
				subscriptionInvoiceEntity.setDecision(AppConstant.DECLINE);
				subscriptionInvoiceEntity.setSuccess(AppConstant.INACTIVE);
				
				subscriptionInvoiceRepository.save(subscriptionInvoiceEntity);
				paymentStatus = "Fail";
				responseMsg = "Activation Unsuccessful Error Occurred With Transaction Id";

				logger.info(paymentStatus + " : " + responseMsg);
			}

			if (isUpdateCronViewer) {
				cronViewerRepostService.save(subscriptionEntity.getViewerId(), paymentStatus, subscriptionEntity.getAmount(), responseMsg, serverResponse, cronId);
			}

			return returnValue;
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	/*
	 * @Override public MerchantAccountEntity getMerchantAccountEntity(int
	 * lastTransaciontId, double amount, TransactionType transactionType, Integer
	 * viewerId, boolean isSuccess) throws Exception { MerchantAccountEntity entity
	 * = new MerchantAccountEntity(); entity.setId(0); entity.setServiceId("KIKI");
	 * entity.setTransactionId(lastTransaciontId); entity.setAmount(amount);
	 * entity.setDate(new Date()); entity.setViewerId(viewerId);
	 * entity.setSuccess(isSuccess); entity.setTransactionType(transactionType);
	 * return entity; }
	 */

	@Override
	@Transactional
	public String cronPay(SubscriptionEntity subscriptionEntity, String activationStatus, 
			boolean isUpdateCronViewer, Integer cronId) throws Exception {

		logger.info("called to pay");
		String resp = activateDataBundle(subscriptionEntity.getMobile(),subscriptionEntity, isUpdateCronViewer, cronId);
		logger.info("activateDataBundle resp : " + resp);
		paymentLogService.createPaymentLog("Mobitel", resp, "-", subscriptionEntity.getViewerId(), subscriptionEntity.getMobile(), "");

		if (resp.equals("1000")) {
			Integer packageId = packageConfigService.getPackageId(subscriptionEntity.getSubscribedDays(), AppConstant.MOBITEL);

			String paymentResp = cronProceedPayment(subscriptionEntity.getViewerId(), packageId, subscriptionEntity.getMobile(), subscriptionEntity.getSubscribedDays());
			
			if(paymentResp.equals("success")) {
				subscriptionEntity.setPolicyExpDate(appUtility.getbeforeDay(subscriptionEntity.getSubscribedDays(), appUtility.getLastMinitue()));
				subscriptionEntity.setUpdateDate(new Date());
				
				if(subscriptionRepository.save(subscriptionEntity) != null) {
					return paymentResp;
				}
				
			}

			logger.info("Error at package Updating");
			return "Error at package Updating";
			
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
	public String cronProceedPayment(Integer viewerId, Integer packageId, String mobileNo, Integer subscribeDays) throws Exception {
		String resp = "Fail";
		try {
			mobileNo = "0" + appUtility.getNineDigitMobileNumber(mobileNo);

			if (packageId > 0) {
				ViewerPolicyUpdateRequestDto dto = new ViewerPolicyUpdateRequestDto();
				dto.setPackageId(packageId);
				dto.setViewerId(viewerId);
				if (viewerPolicyService.updateViewerPolicy(dto, subscribeDays).equalsIgnoreCase("success")) {
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
