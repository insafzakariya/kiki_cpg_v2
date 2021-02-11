package org.kiki_cpg_v2.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kiki_cpg_v2.client.DialogClient;
import org.kiki_cpg_v2.controller.PaymentPlanController;
import org.kiki_cpg_v2.controller.ViewController;
import org.kiki_cpg_v2.dto.DialogOtpDto;
import org.kiki_cpg_v2.dto.DialogPaymentConfirmDto;
import org.kiki_cpg_v2.dto.PaymantPlanDto;
import org.kiki_cpg_v2.dto.PaymentPlanDto;
import org.kiki_cpg_v2.dto.request.DialogOtpConfirmDto;
import org.kiki_cpg_v2.entity.IdeabizEntity;
import org.kiki_cpg_v2.entity.InvoiceEntity;
import org.kiki_cpg_v2.entity.PackageConfigEntity;
import org.kiki_cpg_v2.entity.PackageEntity;
import org.kiki_cpg_v2.entity.SubscriptionEntity;
import org.kiki_cpg_v2.entity.SubscriptionInvoiceEntity;
import org.kiki_cpg_v2.repository.IdeabizRepository;
import org.kiki_cpg_v2.repository.SubscriptionInvoiceRepository;
import org.kiki_cpg_v2.repository.SubscriptionRepository;
import org.kiki_cpg_v2.service.IdeabizService;
import org.kiki_cpg_v2.service.InvoiceService;
import org.kiki_cpg_v2.service.PackageConfigService;
import org.kiki_cpg_v2.service.PaymentDetailService;
import org.kiki_cpg_v2.service.PaymentLogService;
import org.kiki_cpg_v2.service.PaymentMethodService;
import org.kiki_cpg_v2.service.PaymentPlanService;
import org.kiki_cpg_v2.service.SubscriptionService;
import org.kiki_cpg_v2.service.ViewerPolicyService;
import org.kiki_cpg_v2.service.ViewerService;
import org.kiki_cpg_v2.service.ViewerUnsubscriptionService;
import org.kiki_cpg_v2.util.AppConstant;
import org.kiki_cpg_v2.util.AppUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdeabizServiceImpl implements IdeabizService {

	final static Logger logger = LoggerFactory.getLogger(IdeabizServiceImpl.class);

	@Autowired
	private DialogClient dialogClient;

	@Autowired
	private PaymentMethodService paymentMethodService;

	@Autowired
	private ViewerService viewerService;

	@Autowired
	private PaymentLogService paymentLogService;

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private IdeabizRepository ideabizRepository;

	@Autowired
	private ViewerPolicyService viewerPolicyService;

	@Autowired
	private ViewerUnsubscriptionService viewerUnsubscriptionService;

	@Autowired
	private PackageConfigService packageConfigService;

	@Autowired
	private PaymentDetailService paymentDetailService;
	
	@Autowired
	private SubscriptionService subscriptionService;

	@Autowired
	private PaymentPlanService paymentPlanService;
	
	@Autowired
	private SubscriptionRepository subscriptionRepository;
	
	@Autowired
	private SubscriptionInvoiceRepository subscriptionInvoiceRepository;
	
	@Autowired
	private AppUtility appUtility;

	@Override
	public DialogPaymentConfirmDto pinSubscriptionConfirm(DialogOtpConfirmDto dialogOtpConfirmDto) throws Exception {
		String accessToken = dialogClient.createAccessToken();
		Double amount = paymentMethodService.getPaymentPlanAmount(dialogOtpConfirmDto.getDay(), 4);
		PaymantPlanDto paymentPlanDto = paymentPlanService.getPaymentPlan(dialogOtpConfirmDto.getPlanId());
		String message = "";
		Integer invoiceNo = null;
		if (amount > 0) {
			DialogOtpDto dialogOtpDto = dialogClient.pinSubscriptionConfirm(dialogOtpConfirmDto, amount, accessToken);
			System.out.println(dialogOtpDto.toString());

			if (dialogOtpDto.getStatusCode().equals("SUCCESS")) {
				String mobileNo = dialogOtpDto.getMsisdn();
				mobileNo = mobileNo.replace("tel:", "");

				if (dialogOtpDto.getStatus().equals("SUBSCRIBED")) {
					
					
					
					SubscriptionEntity subscriptionEntity = subscriptionService.generateSubsctiptionEntity(mobileNo, dialogOtpConfirmDto.getViewerId(), paymentPlanDto, AppConstant.DIALOG);

					/*IdeabizEntity ideabizEntity = getIdeabizEntity(dialogOtpConfirmDto.getViewerId(), mobileNo,
							dialogOtpConfirmDto.getDay());
					message = "SUBSCRIBED";
*/
					System.out.println(dialogOtpConfirmDto.toString());
					if (subscriptionRepository.save(subscriptionEntity) != null) {

						if (dialogOtpConfirmDto.isTrial()) {
							System.out.println("TRIAL");
							viewerService.updateViewerMobileNumberAndTrial(mobileNo, dialogOtpConfirmDto.getViewerId(),
									false);
							processTrial(dialogOtpConfirmDto.getViewerId(), mobileNo, subscriptionEntity);
						} else {
							
							List resp = processIdeabizPayment(dialogOtpConfirmDto.getServerRef(),
									dialogOtpConfirmDto.getViewerId(), dialogOtpConfirmDto.getDay(), mobileNo, amount,
									true, false, -1, subscriptionEntity);
							System.out.println("Invoice No " + Integer.valueOf(resp.get(0).toString()));
							invoiceNo = Integer.valueOf(resp.get(0).toString());
							if (resp.get(1).toString().equalsIgnoreCase("Success")) {
								viewerService.updateViewerMobileNumberAndTrial(mobileNo, dialogOtpConfirmDto.getViewerId(),
										false);
							}
						}

					} else {
						message = "SUBSCRIBED SAVE ERROR";
						paymentLogService.createPaymentLog("Dialog", "-", dialogOtpDto.getTransactionOperationStatus(),
								dialogOtpConfirmDto.getViewerId(), mobileNo, dialogOtpDto.getResult());

						System.out.println("ALREADY SUBSCRIBED");
					}

				} else {

					message = "ALREADY SUBSCRIBED";
					paymentLogService.createPaymentLog("Dialog", "-", dialogOtpDto.getTransactionOperationStatus(),
							dialogOtpConfirmDto.getViewerId(), mobileNo, dialogOtpDto.getResult());

					if (processUnsubscriptionIdeabiz(accessToken, dialogOtpConfirmDto.getViewerId(), mobileNo, false)) {
						/*IdeabizEntity ideabizEntity = getIdeabizEntity(dialogOtpConfirmDto.getViewerId(), mobileNo,
								dialogOtpConfirmDto.getDay());
						message = "SUBSCRIBED";*/
						SubscriptionEntity subscriptionEntity = subscriptionService.generateSubsctiptionEntity(mobileNo, dialogOtpConfirmDto.getViewerId(), paymentPlanDto, AppConstant.DIALOG);
						if (subscriptionRepository.save(subscriptionEntity) != null) {
							if (dialogOtpConfirmDto.isTrial()) {
								viewerService.updateViewerMobileNumberAndTrial(mobileNo,
										dialogOtpConfirmDto.getViewerId(), false);
								processTrial(dialogOtpConfirmDto.getViewerId(), mobileNo, subscriptionEntity);
							} else {
								viewerService.updateViewerMobileNumber(mobileNo, dialogOtpConfirmDto.getViewerId());

								List<String> resp = processIdeabizPayment(dialogOtpConfirmDto.getServerRef(),
										dialogOtpConfirmDto.getViewerId(), dialogOtpConfirmDto.getDay(), mobileNo,
										amount, true, false, -1, subscriptionEntity);
								invoiceNo = Integer.valueOf(resp.get(0).toString());
								if (resp.get(1).toString().equalsIgnoreCase("Success")) {
								}
							}

						} else {
							message = "SUBSCRIBED SAVE ERROR";
							paymentLogService.createPaymentLog("Dialog", "-",
									dialogOtpDto.getTransactionOperationStatus(), dialogOtpConfirmDto.getViewerId(),
									mobileNo, dialogOtpDto.getResult());
							System.out.println("ALREADY SUBSCRIBED");
						}
					}

				}

			} else {
				paymentLogService.createPaymentLog("Dialog", "-", dialogOtpDto.getMessage(),
						dialogOtpConfirmDto.getViewerId(), dialogOtpDto.getMsisdn(), dialogOtpDto.getResult());
			}

			DialogPaymentConfirmDto confirmDto = new DialogPaymentConfirmDto();
			confirmDto.setStatusCode(dialogOtpDto.getStatusCode());
			confirmDto.setServerRef(dialogOtpDto.getServerRef());
			confirmDto.setStatus(dialogOtpDto.getStatus());
			confirmDto.setServiceId(dialogOtpDto.getServiceId());
			confirmDto.setAccessCode(accessToken);
			confirmDto.setMsisdn(dialogOtpDto.getMsisdn());
			confirmDto.setMessage(message);
			confirmDto.setInvoiceId(invoiceNo);

			return confirmDto;

		}

		return null;
	}

	@Override
	public void processTrial(Integer viewerId, String mobile, SubscriptionEntity subscriptionEntity) throws Exception {

		System.out.println("TRIAL PROCESSING");

		PackageConfigEntity packageConfigEntity = packageConfigService.getFreeTrialPackageId(3, AppConstant.TRIAL);
		/*
		 * if (day == 7) { packageId = 106; } else if (day == 1) { packageId = 81; }
		 * else if (day == 30) { packageId = 110; } else if (day == 90) { packageId =
		 * 111; }
		 */
		
		SubscriptionInvoiceEntity subscriptionInvoiceEntity = subscriptionService.getSubscriptionInvoiceEntity(subscriptionEntity.getMobile(), "1", subscriptionEntity, AppConstant.DIALOG);
		subscriptionInvoiceEntity.setAmount(0.0);
		subscriptionInvoiceEntity = subscriptionInvoiceRepository.save(subscriptionInvoiceEntity);
		
		if (packageConfigEntity != null && packageConfigEntity.getPackageId() > 0) {
			Integer packageId = packageConfigEntity.getPackageId();
			if (viewerPolicyService
					.updateViewerPolicy(viewerPolicyService.getViewerPolicyUpdateRequestDto(viewerId, packageId),
							packageConfigEntity.getDays())
					.equalsIgnoreCase("success")) {
				
				subscriptionEntity.setPolicyExpDate(appUtility.getbeforeDay(packageConfigEntity.getDays(), appUtility.getLastMinitue()));
				subscriptionEntity.setUpdateDate(new Date());

				
				if (subscriptionRepository.save(subscriptionEntity) != null) {
					if (viewerUnsubscriptionService.save(mobile, viewerId, "SUBSCRIBE", "Dialog", true)) {

						logger.info("Viewer Id : " + viewerId + " Success trial");
						// return "Success";
					} else {
						logger.error("Viewer Id : " + viewerId + " Unsubscription save error");
						// return "Unsubscription save error";
					}
				} else {
					logger.error("Viewer Id : " + viewerId + " Policy Expire Update Error");
					// return "Policy Expire Update Error";
				}
			} else {
				logger.error("Viewer Id : " + viewerId + " Viewer Policy Update Error");
				// return "Viewer Policy Update Error";
			}
		} else {
			logger.error("Viewer Id : " + viewerId + " Unidentified Package");
			// return "Unidentified Package";
		}

	}

	@Override
	public boolean processUnsubscriptionIdeabiz(String accessToken, Integer viewerId, String mobileNo,
			boolean unsubscribeFromDialog) throws Exception {

		if (accessToken == null) {
			accessToken = dialogClient.createAccessToken();
		}

		SubscriptionEntity subscriptionEntity = subscriptionRepository.findFirstByViewerIdAndStatusAndSubscribeAndTypeOrderByIdDesc(viewerId, AppConstant.ACTIVE, AppConstant.ACTIVE, AppConstant.DIALOG);
		if (subscriptionEntity != null) {
			subscriptionEntity.setSubscribe(AppConstant.INACTIVE);
			subscriptionEntity.setUpdateDate(new Date());


			if (unsubscribeFromDialog) {
				dialogClient.unsubscribe(accessToken, viewerId, subscriptionEntity.getSubscribedDays(), mobileNo);
			}
			if (subscriptionRepository.save(subscriptionEntity) != null) {
				if (viewerUnsubscriptionService.unubscribe(mobileNo, viewerId, "UNSUBSCRIBE", "Dialog")) {
					return true;
				}
			}
		} else {
			return true;
		}

		return false;
	}

	@Override
	public List<String> processIdeabizPayment(String serverRef, Integer viewerId, Integer day, String mobileNo,
			Double amount, boolean unsubscrideEntityUpdate, boolean isUpdateCronViewer, Integer cronId, SubscriptionEntity subscriptionEntity)
			throws Exception {
		List<Date> dates = appUtility.getDatesBetweenUsingJava7(day);
		List<String> responce = new ArrayList<String>();

//		InvoiceEntity invoiceEntity = invoiceService.createInvoice(AppConstant.IDEABIZ, viewerId, day, amount, mobileNo,
//				AppConstant.INACTIVE, dates);

		/*
		 * InvoiceEntity invoiceEntity =
		 * invoiceService.createInvoice(AppConstant.IDEABIZ, viewerId, day, amount,
		 * mobileNo, AppConstant.INACTIVE, null);
		 */
		
		SubscriptionInvoiceEntity subscriptionInvoiceEntity = subscriptionService.getSubscriptionInvoiceEntity(mobileNo, "1", subscriptionEntity, AppConstant.DIALOG);
		subscriptionInvoiceEntity.setSuccess(AppConstant.INACTIVE);
		subscriptionInvoiceEntity.setDecision(AppConstant.DECLINE);
		subscriptionInvoiceEntity = subscriptionInvoiceRepository.save(subscriptionInvoiceEntity);
		
		responce.add(subscriptionInvoiceEntity.getId().toString());

		if (serverRef == null) {
			serverRef = subscriptionInvoiceEntity.getId().toString();
		}

		String paid = paymentConfirm(serverRef, mobileNo, amount, day, viewerId, isUpdateCronViewer, cronId);
		System.out.println(paid);

		if (paid.equals("Success")) {

			subscriptionInvoiceEntity.setSuccess(AppConstant.ACTIVE);
			subscriptionInvoiceEntity.setDecision(AppConstant.ACCEPT);
			if (subscriptionInvoiceRepository.save(subscriptionInvoiceEntity)!= null) {

				Integer packageId = packageConfigService.getPackageId(day, AppConstant.DIALOG);
				/*
				 * if (day == 7) { packageId = 106; } else if (day == 1) { packageId = 81; }
				 * else if (day == 30) { packageId = 110; } else if (day == 90) { packageId =
				 * 111; }
				 */
				if (packageId > 0) {
					if (viewerPolicyService
							.updateViewerPolicy(
									viewerPolicyService.getViewerPolicyUpdateRequestDto(viewerId, packageId), -1)
							.equalsIgnoreCase("success")) {
						
						subscriptionEntity.setPolicyExpDate(appUtility.getbeforeDay(day, appUtility.getLastMinitue()));
						subscriptionEntity.setUpdateDate(new Date());

						/*
						 * IdeabizEntity ideabizEntity = updateIdeabizPolicyExpDate(viewerId, day,
						 * invoiceEntity.getCreatedDate());
						 */
						if (subscriptionRepository.save(subscriptionEntity) != null) {

							if (paymentDetailService.save(amount, day, subscriptionEntity.getPolicyExpDate(),
									subscriptionInvoiceEntity.getId(), AppConstant.DIALOG) != null) {
								if (viewerUnsubscriptionService.save(mobileNo, viewerId, "SUBSCRIBE", "Dialog",
										unsubscrideEntityUpdate)) {
									responce.add("Success");
									return responce;
								} else {
									responce.add("Unsubscription save error");
									return responce;
								}
							} else {
								responce.add("Payment Details save error");
								return responce;
							}
						} else {
							responce.add("Policy Expire Update Error");
							return responce;
						}
					} else {
						responce.add("Viewer Policy Update Error");
						return responce;
					}
				} else {
					responce.add("Unidentified Package");
					return responce;
				}
			} else {
				responce.add("Invoice Not Updated");
				return responce;
			}

		} else {
			responce.add("Payment Confirmation Error");
			return responce;
		}
	}

	/*
	 * @Override public IdeabizEntity updateIdeabizPolicyExpDate(Integer viewerId,
	 * Integer valiedDate, Date createDate) throws Exception { IdeabizEntity
	 * ideabizEntity = ideabizRepository.findOneByViwerIdAndSubscribe(viewerId,
	 * AppConstant.ACTIVE); ideabizEntity.setLastPolicyUpdatedAt(createDate);
	 * ideabizEntity.setPolicyExpireAt(appUtility.getbeforeDay(valiedDate,
	 * createDate)); return ideabizRepository.save(ideabizEntity); }
	 */

	@Override
	public String paymentConfirm(String serverRef, String mobileNo, Double amount, Integer subscribedDays,
			Integer viewerId, boolean isUpdateCronViewer, Integer cronId) throws Exception {
		String paid = dialogClient.dialogPaymentConfirm(serverRef, mobileNo, amount, subscribedDays, viewerId,
				isUpdateCronViewer, cronId);
		return paid;
	}

	/*
	 * @Override public IdeabizEntity getIdeabizEntity(Integer viewerId, String
	 * mobileNo, Integer day) throws Exception { IdeabizEntity ideabizEntity = new
	 * IdeabizEntity(); Date date = new Date(); long time = date.getTime();
	 * Timestamp ts = new Timestamp(time);
	 * 
	 * ideabizEntity.setMobile(mobileNo); ideabizEntity.setViwerId(viewerId);
	 * ideabizEntity.setCreatedDate(ts); ideabizEntity.setSubscribe(1);
	 * ideabizEntity.setSubscribedDays(day); return ideabizEntity; }
	 */

}
