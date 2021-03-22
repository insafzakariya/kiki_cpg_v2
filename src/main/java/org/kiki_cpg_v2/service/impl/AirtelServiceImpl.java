/**
 * Mar 19, 2021
 * AirtelServiceImpl.java
 */
package org.kiki_cpg_v2.service.impl;

import java.util.Date;

import org.kiki_cpg_v2.dto.PaymantPlanDto;
import org.kiki_cpg_v2.dto.PaymentRefDto;
import org.kiki_cpg_v2.dto.request.TransactionBeginDto;
import org.kiki_cpg_v2.entity.PackageConfigEntity;
import org.kiki_cpg_v2.entity.SubscriptionEntity;
import org.kiki_cpg_v2.entity.SubscriptionInvoiceEntity;
import org.kiki_cpg_v2.entity.ViewerEntity;
import org.kiki_cpg_v2.repository.SubscriptionInvoiceRepository;
import org.kiki_cpg_v2.repository.SubscriptionRepository;
import org.kiki_cpg_v2.repository.ViewerRepository;
import org.kiki_cpg_v2.service.AirtelService;
import org.kiki_cpg_v2.service.HNBService;
import org.kiki_cpg_v2.service.PackageConfigService;
import org.kiki_cpg_v2.service.PaymentDetailService;
import org.kiki_cpg_v2.service.PaymentLogService;
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

/**
 * @author Anjana Thrishakya
 */
@Service
public class AirtelServiceImpl implements AirtelService {

	final static Logger logger = LoggerFactory.getLogger(AirtelServiceImpl.class);

	@Autowired
	private HNBService hnbService;

	@Autowired
	private ViewerRepository viewerRepository;

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	private SubscriptionService subscriptionService;

	@Autowired
	private PaymentPlanService paymentPlanService;

	@Autowired
	private PaymentLogService paymentLogService;

	@Autowired
	private ViewerService viewerService;

	@Autowired
	private PackageConfigService packageConfigService;

	@Autowired
	private SubscriptionInvoiceRepository subscriptionInvoiceRepository;

	@Autowired
	private ViewerPolicyService viewerPolicyService;

	@Autowired
	private ViewerUnsubscriptionService viewerUnsubscriptionService;
	
	@Autowired
	private PaymentDetailService paymentDetailService;

	@Autowired
	private AppUtility appUtility;

	@Override
	public PaymentRefDto beginSubscribe(TransactionBeginDto beginDto) throws Exception {
		PaymentRefDto paymentRefDto = hnbService.getPaymentRefDto(beginDto, -1, -0.1);

		ViewerEntity viewerEntity = viewerRepository.findById(beginDto.getViewerId()).get();
		PaymantPlanDto paymentPlanDto = paymentPlanService.getPaymentPlan(beginDto.getPlanId());

		SubscriptionEntity subscriptionEntity = subscriptionRepository
				.save(subscriptionService.generateSubsctiptionEntity(beginDto.getMobileNo(), beginDto.getViewerId(),
						paymentPlanDto, AppConstant.AIRTEL));

		if (subscriptionEntity != null) {

			if (beginDto.isTrial()) {
				viewerService.updateViewerMobileNumberAndTrial(beginDto.getMobileNo(), beginDto.getViewerId(), false);
				String status = processTrial(beginDto.getViewerId(), beginDto.getMobileNo(), subscriptionEntity);
				paymentRefDto.setStatus(status);
			} else {
				String status = processPayment(paymentPlanDto, subscriptionEntity, paymentRefDto, viewerEntity,
						beginDto);
			}

		} else {
			paymentRefDto.setStatus("Fail");
			paymentLogService.createPaymentLog("Dialog", "-", "", beginDto.getViewerId(), beginDto.getMobileNo(),
					beginDto.toString());
			System.out.println("ALREADY SUBSCRIBED");
		}

		return paymentRefDto;
	}

	@Override
	public String processPayment(PaymantPlanDto paymentPlanDto, SubscriptionEntity subscriptionEntity,
			PaymentRefDto paymentRefDto, ViewerEntity viewerEntity, TransactionBeginDto beginDto) throws Exception {

		String response = "Fail";

		SubscriptionInvoiceEntity subscriptionInvoiceEntity = subscriptionService.getSubscriptionInvoiceEntity(
				"+94" + appUtility.getNineDigitMobileNumber(beginDto.getMobileNo()), "1", subscriptionEntity,
				AppConstant.DIALOG);
		subscriptionInvoiceEntity.setSuccess(AppConstant.INACTIVE);
		subscriptionInvoiceEntity.setDecision(AppConstant.DECLINE);
		subscriptionInvoiceEntity = subscriptionInvoiceRepository.save(subscriptionInvoiceEntity);

		String paymentStatus = "Success"; // TODO Airtel call

		if (paymentStatus.equalsIgnoreCase("Success")) {
			subscriptionInvoiceEntity.setSuccess(AppConstant.ACTIVE);
			subscriptionInvoiceEntity.setDecision(AppConstant.ACCEPT);
			if (subscriptionInvoiceRepository.save(subscriptionInvoiceEntity) != null) {
				PackageConfigEntity packageConfigEntity = packageConfigService
						.getFreeTrialPackageId(subscriptionEntity.getSubscribedDays(), AppConstant.HUTCH);
				if (packageConfigEntity != null && packageConfigEntity.getPackageId() != null) {
					if (viewerPolicyService
							.updateViewerPolicy(viewerPolicyService.getViewerPolicyUpdateRequestDto(
									viewerEntity.getId(), packageConfigEntity.getPackageId()), -1)
							.equalsIgnoreCase("success")) {

						subscriptionEntity.setPolicyExpDate(appUtility.getbeforeDay(paymentPlanDto.getDay(), appUtility.getLastMinitue()));
						subscriptionEntity.setUpdateDate(new Date());
						
						if (subscriptionRepository.save(subscriptionEntity) != null) {

							if (paymentDetailService.save(subscriptionEntity.getAmount(), subscriptionEntity.getSubscribedDays(), subscriptionEntity.getPolicyExpDate(),
									subscriptionInvoiceEntity.getId(), AppConstant.DIALOG) != null) {
								if (viewerUnsubscriptionService.save(subscriptionEntity.getMobile(), viewerEntity.getId(), "SUBSCRIBE", "Airtel",
										true)) {
									response = "Success";
									return response;
								} else {
									response = "Unsubscription save error";
									return response;
								}
							} else {
								response = "Payment Details save error";
								return response;
							}
						} else {
							response = "Policy Expire Update Error";
							return response;
						}
						
					} else {
						response = "Viewer Policy Update Error";
						return response;
					}
				} else {
					response = "Unidentified Package";
					return response;
				}
			} else {
				response = "Invoice Not Updated";
				return response;
			}
		} else {
			response = "Payment Confirmation Error";
			return response;
		}

	}

	@Override
	public String processTrial(Integer viewerId, String mobile, SubscriptionEntity subscriptionEntity)
			throws Exception {

		System.out.println("TRIAL PROCESSING");

		PackageConfigEntity packageConfigEntity = packageConfigService.getFreeTrialPackageId(3, AppConstant.TRIAL);

		SubscriptionInvoiceEntity subscriptionInvoiceEntity = subscriptionService.getSubscriptionInvoiceEntity(
				subscriptionEntity.getMobile(), "1", subscriptionEntity, AppConstant.AIRTEL);
		subscriptionInvoiceEntity.setAmount(0.0);
		subscriptionInvoiceEntity = subscriptionInvoiceRepository.save(subscriptionInvoiceEntity);

		if (packageConfigEntity != null && packageConfigEntity.getPackageId() > 0) {
			Integer packageId = packageConfigEntity.getPackageId();
			if (viewerPolicyService
					.updateViewerPolicy(viewerPolicyService.getViewerPolicyUpdateRequestDto(viewerId, packageId),
							packageConfigEntity.getDays())
					.equalsIgnoreCase("success")) {

				subscriptionEntity.setPolicyExpDate(
						appUtility.getbeforeDay(packageConfigEntity.getDays(), appUtility.getLastMinitue()));
				subscriptionEntity.setUpdateDate(new Date());

				if (subscriptionRepository.save(subscriptionEntity) != null) {
					if (viewerUnsubscriptionService.save(mobile, viewerId, "SUBSCRIBE", "Airtel", true)) {

						logger.info("Viewer Id : " + viewerId + " Success trial");
						return "Success";
					} else {
						logger.error("Viewer Id : " + viewerId + " Unsubscription save error");
						return "Unsubscription save error";
					}
				} else {
					logger.error("Viewer Id : " + viewerId + " Policy Expire Update Error");
					return "Policy Expire Update Error";
				}
			} else {
				logger.error("Viewer Id : " + viewerId + " Viewer Policy Update Error");
				return "Viewer Policy Update Error";
			}
		} else {
			logger.error("Viewer Id : " + viewerId + " Unidentified Package");
			return "Unidentified Package";
		}

	}

}
