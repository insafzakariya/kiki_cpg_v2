/**
 * @author Anjana Thrishakya
 * Nov 3, 2020
 * 10:09:23 AM
 */
package org.kiki_cpg_v2.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import org.kiki_cpg_v2.client.AppleClient;
import org.kiki_cpg_v2.dto.ApplePayDto;
import org.kiki_cpg_v2.dto.PaymentRefDto;
import org.kiki_cpg_v2.entity.PackageConfigEntity;
import org.kiki_cpg_v2.entity.PaymentMethodPlanEntity;
import org.kiki_cpg_v2.entity.SubscriptionEntity;
import org.kiki_cpg_v2.entity.SubscriptionInvoiceEntity;
import org.kiki_cpg_v2.entity.ViewerEntity;
import org.kiki_cpg_v2.repository.PaymentMethodPlanRepository;
import org.kiki_cpg_v2.repository.SubscriptionInvoiceRepository;
import org.kiki_cpg_v2.repository.SubscriptionRepository;
import org.kiki_cpg_v2.repository.ViewerRepository;
import org.kiki_cpg_v2.service.AppleService;
import org.kiki_cpg_v2.service.PackageConfigService;
import org.kiki_cpg_v2.service.PaymentLogService;
import org.kiki_cpg_v2.service.ViewerPolicyService;
import org.kiki_cpg_v2.service.ViewerService;
import org.kiki_cpg_v2.service.ViewerUnsubscriptionService;
import org.kiki_cpg_v2.util.AppConstant;
import org.kiki_cpg_v2.util.AppUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;

/**
 *
 */
@Service
public class AppleServiceImpl implements AppleService {

	@Autowired
	private AppleClient appleClient;

	@Autowired
	private PaymentLogService paymentLogService;

	@Autowired
	private PackageConfigService packageConfigService;
	
	@Autowired
	private ViewerPolicyService viewerPolicyService;
	
	@Autowired
	private ViewerService viewerService;
	
	@Autowired
	private ViewerUnsubscriptionService viewerUnsubscriptionService;

	@Autowired
	private ViewerRepository viewerRepository;

	@Autowired
	private PaymentMethodPlanRepository paymentMethodPlanRepository;
	
	@Autowired
	private SubscriptionInvoiceRepository subscriptionInvoiceRepository;

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	private AppUtility appUtility;

	@Override
	public PaymentRefDto pay(ApplePayDto applePayDto) throws Exception {
		if (!checkSubsciption(applePayDto.getViewerId())) {
			throw new RuntimeException("Save Error | Can't Unsubscribe Previous Package");
		}
		
		PaymentRefDto paymentRefDto = new PaymentRefDto();
		paymentRefDto.setStatus("Fail");
		paymentRefDto.setStatus(AppConstant.DECLINE);
		ViewerEntity viewerEntity = viewerRepository.findById(applePayDto.getViewerId()).get();
		HashMap<String, Object> responseMap = appleClient.verify(applePayDto.getReceiptString());
		if (responseMap != null) {
			if (responseMap.containsKey("status")) {
				if (responseMap.get("status").equals(0)) {
					paymentLogService.createPaymentLog(AppConstant.APPLE, "SUBSCRIPTION", "SUBSCRIPTION",
							applePayDto.getViewerId(), viewerEntity.getMobileNumber(), responseMap.toString());

					PaymentMethodPlanEntity paymentMethodPlanEntity = paymentMethodPlanRepository
							.findById(applePayDto.getPlanId()).get();
					PackageConfigEntity packageConfigEntity = packageConfigService
							.getPackage(paymentMethodPlanEntity.getDays(), AppConstant.APPLE);

					SubscriptionEntity subscriptionEntity = getSubsctiptionEntity(applePayDto, paymentMethodPlanEntity,
							viewerEntity);
					ArrayList<Object> lastReceipts = (ArrayList<Object>) responseMap.get("latest_receipt_info");
					HashMap<String, String> info = (HashMap<String, String>) lastReceipts.get(lastReceipts.size()-1);
					System.out.println(info);
					subscriptionEntity.setToken(info.get("original_transaction_id"));
					
					subscriptionEntity = subscriptionRepository.save(subscriptionEntity);

					SubscriptionInvoiceEntity subscriptionInvoiceEntity = getSubscriptionInvoiceEntity(applePayDto,
							paymentMethodPlanEntity, viewerEntity, subscriptionEntity);
					
					subscriptionInvoiceEntity.setReferanceNo(info.get("transaction_id"));
					
					if(subscriptionInvoiceRepository.save(subscriptionInvoiceEntity) != null) {
						if (packageConfigEntity != null && packageConfigEntity.getPackageId() > 0) {
							if (viewerPolicyService
									.updateViewerPolicy(
											viewerPolicyService.getViewerPolicyUpdateRequestDto(
													subscriptionEntity.getViewerId(),
													packageConfigEntity.getPackageId()),
											packageConfigEntity.getDays())
									.equalsIgnoreCase("success")) {
								if (viewerUnsubscriptionService.save(viewerEntity.getMobileNumber(),
										subscriptionEntity.getViewerId(), "SUBSCRIBE", AppConstant.APPLE,
										true)) {
									viewerService.updateViewerMobileNumberAndTrial(viewerEntity.getMobileNumber(),
											viewerEntity.getId(), false);
									paymentRefDto.setStatus("Success");
									paymentRefDto.setStatus(AppConstant.ACCEPT);
									paymentRefDto.setAmount(subscriptionInvoiceEntity.getAmount());
									paymentRefDto.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(subscriptionInvoiceEntity.getCreatedDate()));
									paymentRefDto.setDays(subscriptionEntity.getSubscribedDays());
									paymentRefDto.setReferanceNo(subscriptionInvoiceEntity.getReferanceNo());
									paymentRefDto.setTransactionUUID(subscriptionInvoiceEntity.getTransactionNo());
									System.out.println("payment Complete");
									return paymentRefDto;
								} else {
									throw new RuntimeException("Save Error | Viewer Unsubscribe Save Error");
								}
							} else {
								throw new RuntimeException("Update Error | Policy Not Updated");
							}
						} else {
							throw new NullPointerException("Not Found | Package Not Found");
						}
						
						
					} else {
						throw new RuntimeException("Save Error | Subscription Invoice");
					}

				} else {
					paymentLogService.createPaymentLog(AppConstant.APPLE, "FAIL", "FAIL", applePayDto.getViewerId(),
							viewerEntity.getMobileNumber(), responseMap.toString());
					Integer status = (Integer) responseMap.get("status");
					switch (status) {
					case 21010:
						throw new RuntimeException("Apple Verify Error | The user account cannot be found or has been deleted.");
					case 21009:
						throw new RuntimeException("Apple Verify Error | Internal data access error. Try again later.");
					case 21003:
						throw new RuntimeException("Apple Verify Error | The receipt could not be authenticated.");
					case 21005:
						throw new RuntimeException("Apple Verify Error | The receipt server was temporarily unable to provide the receipt. Try again.");
					default:
						throw new RuntimeException("Apple Verify Error | Internal Server Error.");
					}
				}
			} else{
				throw new NotFoundException("Status Not Found");
			}
		} else {
			throw new TimeoutException("Verify Request Timeout.");
		}
	}

	@Override
	public SubscriptionInvoiceEntity getSubscriptionInvoiceEntity(ApplePayDto applePayDto,
			PaymentMethodPlanEntity paymentMethodPlanEntity, ViewerEntity viewerEntity,
			SubscriptionEntity subscriptionEntity) {
		
		SubscriptionInvoiceEntity invoiceEntity = new SubscriptionInvoiceEntity();
		invoiceEntity.setAmount(subscriptionEntity.getAmount());
		invoiceEntity.setCreatedDate(new Date());
		invoiceEntity.setMobile(subscriptionEntity.getMobile());
		invoiceEntity.setReferanceNo(UUID.randomUUID().toString());
		invoiceEntity.setStatus(AppConstant.ACTIVE);
		invoiceEntity.setSubscribedDays(subscriptionEntity.getSubscribedDays());
		invoiceEntity.setSuccess(AppConstant.ACTIVE);
		invoiceEntity.setTransactionNo(UUID.randomUUID().toString());
		invoiceEntity.setViewerId(subscriptionEntity.getViewerId());
		invoiceEntity.setType(AppConstant.APPLE);
		return invoiceEntity;
	}

	@Override
	public SubscriptionEntity getSubsctiptionEntity(ApplePayDto applePayDto,
			PaymentMethodPlanEntity paymentMethodPlanEntity, ViewerEntity viewerEntity) throws Exception {
		SubscriptionEntity entity = new SubscriptionEntity();
		entity.setAmount(paymentMethodPlanEntity.getValue());
		entity.setCreateDate(new Date());
		entity.setMobile("+94" + appUtility.getNineDigitMobileNumber(viewerEntity.getMobileNumber()));
		entity.setPaymentPlan(paymentMethodPlanEntity.getId());
		entity.setStatus(AppConstant.ACTIVE);
		entity.setSubscribedDays(paymentMethodPlanEntity.getDays());
		entity.setType(AppConstant.APPLE);
		entity.setViewerId(viewerEntity.getId());
		entity.setPolicyExpDate(appUtility.getbeforeDay(paymentMethodPlanEntity.getDays(), new Date()));
		entity.setSubscribe(AppConstant.ACTIVE);
		entity.setUpdateDate(new Date());
		return entity;
	}

	@Override
	public boolean checkSubsciption(Integer viewerId) throws Exception {
		
		List<SubscriptionEntity> subscriptionEntities = subscriptionRepository
				.findByViewerIdAndStatusAndSubscribeAndType(viewerId, AppConstant.ACTIVE,
						AppConstant.ACTIVE, AppConstant.APPLE);
		
		
		if (subscriptionEntities != null && !subscriptionEntities.isEmpty()) {
			subscriptionEntities.forEach(e-> {
				e.setSubscribe(AppConstant.INACTIVE);
			});
			return subscriptionRepository.saveAll(subscriptionEntities) != null;
		} else {
			return true;
		}
	}
	
}
