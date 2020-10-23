/**
 * @DaSep 2, 2020 @HutchServiceImpl.java
 */
package org.kiki_cpg_v2.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.kiki_cpg_v2.client.HutchClient;
import org.kiki_cpg_v2.dto.HutchResponseDto;
import org.kiki_cpg_v2.dto.PaymentRefDto;
import org.kiki_cpg_v2.dto.request.HutchSubscribeDto;
import org.kiki_cpg_v2.dto.request.TransactionBeginDto;
import org.kiki_cpg_v2.entity.PackageConfigEntity;
import org.kiki_cpg_v2.entity.PackageEntity;
import org.kiki_cpg_v2.entity.PaymentMethodPlanEntity;
import org.kiki_cpg_v2.entity.SubscriptionEntity;
import org.kiki_cpg_v2.entity.SubscriptionInvoiceEntity;
import org.kiki_cpg_v2.entity.ViewerEntity;
import org.kiki_cpg_v2.repository.PaymentMethodPlanRepository;
import org.kiki_cpg_v2.repository.SubscriptionInvoiceRepository;
import org.kiki_cpg_v2.repository.SubscriptionRepository;
import org.kiki_cpg_v2.repository.ViewerRepository;
import org.kiki_cpg_v2.service.HNBService;
import org.kiki_cpg_v2.service.HutchService;
import org.kiki_cpg_v2.service.PackageConfigService;
import org.kiki_cpg_v2.service.PaymentDetailService;
import org.kiki_cpg_v2.service.PaymentLogService;
import org.kiki_cpg_v2.service.ViewerPolicyService;
import org.kiki_cpg_v2.service.ViewerService;
import org.kiki_cpg_v2.service.ViewerUnsubscriptionService;
import org.kiki_cpg_v2.util.AppConstant;
import org.kiki_cpg_v2.util.AppUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.bytebuddy.utility.RandomString;

/**
 * @author Anjana Thrishakya
 */
@Service
public class HutchServiceImpl implements HutchService {

	@Autowired
	private PaymentDetailService paymentDetailService;

	@Autowired
	private PackageConfigService packageConfigService;

	@Autowired
	private ViewerPolicyService viewerPolicyService;

	@Autowired
	private HNBService hnbService;

	@Autowired
	private ViewerRepository viewerRepository;

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	private SubscriptionInvoiceRepository subscriptionInvoiceRepository;
	
	@Autowired
	private PaymentMethodPlanRepository paymentMethodPlanRepository;

	@Autowired
	private PaymentLogService paymentLogService;

	@Autowired
	private ViewerUnsubscriptionService viewerUnsubscriptionService;

	@Autowired
	private ViewerService viewerService;

	@Autowired
	private HutchClient hutchClient;

	@Autowired
	private AppUtility appUtility;

	@Override
	public Map<String, String> transaction(Map<String, String> requestMap) throws Exception {
		
		Map<String, String> response = null;
		if (requestMap.containsKey("ResponseCode") && requestMap.get("ResponseCode").equalsIgnoreCase("0")) {
			System.out.println(requestMap.get("ResponseCode"));
			if (requestMap.containsKey("TransactionType")) {
				String transactionType = requestMap.get("TransactionType");
				switch (transactionType) {
				case "6":
					response = autoRenew(requestMap);
					break;
				case "8":
					response = autoRenew(requestMap);
					break;

				default:
					response = new HashMap<String, String>();
					paymentLogService.createPaymentLog(AppConstant.HUTCH, "", "", -1, "", requestMap.toString());
					response.put("resultCode", "103");
					response.put("description", "transactionType Not Supported");
				}
			} else {
				response = new HashMap<String, String>();
				paymentLogService.createPaymentLog(AppConstant.HUTCH, "", "", -1, "", requestMap.toString());
				response.put("resultCode", "102");
				response.put("description", "transactionType Not found in request");
			}
		} else if (requestMap.containsKey("responseCode")) {
			response = new HashMap<String, String>();
			paymentLogService.createPaymentLog(AppConstant.HUTCH, "", "", -1, "", requestMap.toString());
			response.put("resultCode", "100");
			response.put("description", "Log saved | Fail request");
		} else {
			response = new HashMap<String, String>();
			paymentLogService.createPaymentLog(AppConstant.HUTCH, "", "", -1, "", requestMap.toString());
			response.put("resultCode", "101");
			response.put("description", "Log saved");
		}

		return response;
	}

	@Override
	@Transactional
	public Map<String, String> autoRenew(Map<String, String> requestMap) throws Exception {

		Map<String, String> response = new HashMap<String, String>();
		if (requestMap.containsKey("MSISDN")) {

			ViewerEntity viewerEntity = viewerRepository
					.findFirstByMobileNumberEndingWithOrderByIdDesc(requestMap.get("MSISDN"));
			if (viewerEntity != null) {
				viewerService.updateViewerMobileNumberAndTrial("+94"+appUtility.getNineDigitMobileNumber(requestMap.get("MSISDN")), viewerEntity.getId(),
						false);
				paymentLogService.createPaymentLog(AppConstant.HUTCH, "", "", viewerEntity.getId(),
						requestMap.get("MSISDN"), requestMap.toString());
				
				SubscriptionEntity subscriptionEntity = subscriptionRepository.findFirstByViewerIdAndStatusAndSubscribeAndType(
						viewerEntity.getId(), AppConstant.ACTIVE, AppConstant.ACTIVE, AppConstant.HUTCH);

				if (subscriptionEntity != null) {
					SubscriptionInvoiceEntity invoiceEntity = getSubscriptionInvoiceEntity(requestMap,
							subscriptionEntity);
					subscriptionEntity.setPolicyExpDate(
							appUtility.getbeforeDay(subscriptionEntity.getSubscribedDays(), new Date()));
					subscriptionEntity.setUpdateDate(new Date());

					if (subscriptionRepository.save(subscriptionEntity) != null) {
						invoiceEntity = subscriptionInvoiceRepository.save(invoiceEntity);
						if (invoiceEntity != null) {
							if (paymentDetailService.save(subscriptionEntity.getAmount(),
									subscriptionEntity.getSubscribedDays(), subscriptionEntity.getPolicyExpDate(),
									invoiceEntity.getId(), AppConstant.HUTCH) != null) {
								Integer packageId = packageConfigService
										.getPackageId(subscriptionEntity.getSubscribedDays(), AppConstant.HUTCH);
								if (packageId > 0) {
									if (viewerPolicyService
											.updateViewerPolicy(viewerPolicyService.getViewerPolicyUpdateRequestDto(
													subscriptionEntity.getViewerId(), packageId), -1)
											.equalsIgnoreCase("success")) {
										response.put("resultCode", "0");
										response.put("description", "Success");
									} else {
										response.put("resultCode", "1");
										response.put("description", "Policy Not Updated");
									}
								} else {
									response.put("resultCode", "2");
									response.put("description", "Package Not Found");
								}
							} else {
								response.put("resultCode", "3");
								response.put("description", "Payment Details save error");
							}
						} else {
							response.put("resultCode", "4");
							response.put("description", "subscriptionInvoice save error");
						}
					} else {
						response.put("resultCode", "5");
						response.put("description", "subscription save error");
					}
				} else {
					response.put("resultCode", "6");
					response.put("description", "Subscription Not Fund");
				}

			} else {
				paymentLogService.createPaymentLog(AppConstant.HUTCH, "", "", -1, requestMap.get("MSISDN"),
						requestMap.toString());
				response.put("resultCode", "7");
				response.put("description", "Viewer Not Fund");
			}
		} else {
			paymentLogService.createPaymentLog(AppConstant.HUTCH, "", "", -1, "", requestMap.toString());
			response.put("resultCode", "8");
			response.put("description", "MSISDN Not Fund in request");
		}
		return response;
	}

	@Override
	public SubscriptionInvoiceEntity getSubscriptionInvoiceEntity(Map<String, String> requestMap,
			SubscriptionEntity subscriptionEntity) throws Exception {

		String transactionNo = new SimpleDateFormat("yyMMdd").format(new Date()) + RandomString.make(6);

		SubscriptionInvoiceEntity invoiceEntity = new SubscriptionInvoiceEntity();
		invoiceEntity.setAmount(subscriptionEntity.getAmount());
		invoiceEntity.setCreatedDate(new Date());
		invoiceEntity.setDecision(AppConstant.ACCEPT);
		invoiceEntity.setMobile("+94" + appUtility.getNineDigitMobileNumber(requestMap.get("MSISDN")));
		invoiceEntity.setReferanceNo(UUID.randomUUID().toString());
		invoiceEntity.setStatus(AppConstant.ACTIVE);
		invoiceEntity.setSubscribedDays(subscriptionEntity.getSubscribedDays());
		invoiceEntity.setSuccess(AppConstant.ACTIVE);
		invoiceEntity.setTransactionNo(transactionNo);
		invoiceEntity.setTransactionType(requestMap.get("TransactionType"));
		invoiceEntity.setViewerId(subscriptionEntity.getViewerId());
		invoiceEntity.setType(AppConstant.HUTCH);
		return invoiceEntity;
	}

	@Override
	public Map<String, String> subscribe(Map<String, String> requestMap) throws Exception {
		return null;
//		Map<String, String> response = new HashMap<String, String>();
//		if (requestMap.containsKey("MSISDN")) {
//			ViewerEntity viewerEntity = viewerRepository
//					.findFirstByMobileNumberEndingWithOrderByIdDesc(requestMap.get("MSISDN"));
//			if (viewerEntity != null) {
//				paymentLogService.createPaymentLog(AppConstant.HUTCH, "", "" , viewerEntity.getId(), requestMap.get("MSISDN"), requestMap.toString());
//				SubscriptionEntity subscriptionEntity = subscriptionRepository.findFirstByViewerIdAndStatusAndSubscribe(
//						viewerEntity.getId(), AppConstant.ACTIVE, AppConstant.ACTIVE);
//
//				if (subscriptionEntity != null) {
//					SubscriptionInvoiceEntity invoiceEntity = subscriptionInvoiceRepository.findByViewerIdAndTypeAndStatusAndSuccess
//							(viewerEntity.getId(), AppConstant.HUTCH, AppConstant.ACTIVE, AppConstant.INACTIVE);
//
//					if(invoiceEntity != null) {
//
//						invoiceEntity.setDecision(AppConstant.ACCEPT);
//						invoiceEntity.setSuccess(AppConstant.ACTIVE);
//						
//						if (subscriptionRepository.save(subscriptionEntity) != null) {
//							invoiceEntity = subscriptionInvoiceRepository.save(invoiceEntity);
//							if (invoiceEntity != null) {
//								if (paymentDetailService.save(subscriptionEntity.getAmount(),
//										subscriptionEntity.getSubscribedDays(), subscriptionEntity.getPolicyExpDate(),
//										invoiceEntity.getId(), AppConstant.HUTCH) != null) {
//									Integer packageId = packageConfigService
//											.getPackageId(subscriptionEntity.getSubscribedDays(), AppConstant.HUTCH);
//									if (packageId > 0) {
//										if (viewerPolicyService
//												.updateViewerPolicy(viewerPolicyService.getViewerPolicyUpdateRequestDto(
//														subscriptionEntity.getViewerId(), packageId), -1)
//												.equalsIgnoreCase("success")) {
//											response.put("resultCode", "0");
//											response.put("description", "Success");
//										} else {
//											response.put("resultCode", "1");
//											response.put("description", "Policy Not Updated");
//										}
//									} else {
//										response.put("resultCode", "2");
//										response.put("description", "Package Not Found");
//									}
//								} else {
//									response.put("resultCode", "3");
//									response.put("description", "Payment Details save error");
//								}
//							} else {
//								response.put("resultCode", "4");
//								response.put("description", "subscriptionInvoice save error");
//							}
//						} else {
//							response.put("resultCode", "5");
//							response.put("description", "subscription save error");
//						}
//					} else {
//						response.put("resultCode", "9");
//						response.put("description", "SubscriptionInvoice not found");
//					}
//					
//				} else {
//					response.put("resultCode", "6");
//					response.put("description", "Subscription Not Fund");
//				}
//
//			}else {
//				paymentLogService.createPaymentLog(AppConstant.HUTCH, "", "" , -1, requestMap.get("MSISDN"), requestMap.toString());
//				response.put("resultCode", "7");
//				response.put("description", "Viewer Not Fund");
//			}
//		} else {
//			paymentLogService.createPaymentLog(AppConstant.HUTCH, "", "" , -1, "", requestMap.toString());
//			response.put("resultCode", "8");
//			response.put("description", "MSISDN Not Found in request");
//		}
//		return response;
	}

	@Override
	@Transactional
	public PaymentRefDto beginSubscribe(TransactionBeginDto beginDto) throws Exception {
		
		PaymentRefDto paymentRefDto = hnbService.getPaymentRefDto(beginDto, -1, -0.1);

		SubscriptionEntity subscriptionEntity = getSubsctiptionEntity(beginDto, paymentRefDto);
		SubscriptionInvoiceEntity subscriptionInvoiceEntity = getBeginSubscriptionInvoiceEntity(beginDto,
				subscriptionEntity, paymentRefDto);
		subscriptionInvoiceEntity.setAmount(0.0);
		subscriptionEntity = subscriptionRepository.save(subscriptionEntity);
		if (subscriptionEntity != null) {
			subscriptionInvoiceEntity = subscriptionInvoiceRepository.save(subscriptionInvoiceEntity);
			if (subscriptionInvoiceEntity != null) {
				paymentRefDto.setCardInvoiceId(subscriptionInvoiceEntity.getId());
				HutchSubscribeDto hutchSubscribeDto = getHutchSubscribeDto(paymentRefDto, subscriptionEntity,
						subscriptionInvoiceEntity);
				System.out.println(hutchSubscribeDto.toString());
				HutchResponseDto hutchResponseDto = hutchClient.subscribe(hutchSubscribeDto);
				System.out.println(hutchResponseDto.toString());
				if (hutchResponseDto != null) {
					paymentLogService.createPaymentLog(AppConstant.HUTCH, "SUBSCRIPTION", "SUBSCRIPTION",
							subscriptionEntity.getViewerId(), subscriptionEntity.getMobile(),
							hutchResponseDto.toString());
					if (hutchResponseDto.getResponseCode().equals("0")) {

						PackageConfigEntity packageConfigEntity = packageConfigService
								.getFreeTrialPackageId(subscriptionEntity.getSubscribedDays(), AppConstant.HUTCH);
						subscriptionEntity.setSubscribe(AppConstant.ACTIVE);
						subscriptionEntity
								.setPolicyExpDate(appUtility.getbeforeDay(packageConfigEntity.getDays(), new Date()));
						subscriptionInvoiceEntity.setSuccess(AppConstant.ACTIVE);
						if (subscriptionRepository.save(subscriptionEntity) != null) {
							if (subscriptionInvoiceRepository.save(subscriptionInvoiceEntity) != null) {

								if (packageConfigEntity != null && packageConfigEntity.getPackageId() > 0) {
									if (viewerPolicyService
											.updateViewerPolicy(
													viewerPolicyService.getViewerPolicyUpdateRequestDto(
															subscriptionEntity.getViewerId(),
															packageConfigEntity.getPackageId()),
													packageConfigEntity.getDays())
											.equalsIgnoreCase("success")) {
										if (viewerUnsubscriptionService.save(beginDto.getMobileNo(),
												subscriptionEntity.getViewerId(), "SUBSCRIBE", AppConstant.HUTCH,
												true)) {
											viewerService.updateViewerMobileNumberAndTrial(beginDto.getMobileNo(), beginDto.getViewerId(), false);
											paymentRefDto.setStatus(AppConstant.ACCEPT);
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
								throw new RuntimeException("Update Error | subscriptionInvoiceEntity");
							}
						} else {
							throw new RuntimeException("Update Error | Subscription");
						}
					} else if (hutchResponseDto.getResponseCode().equals("41501018")) {
						paymentRefDto.setStatus(AppConstant.DUPLICATE);
						return paymentRefDto;
					} else {
						throw new RuntimeException("Result Not Successfully");
					}
				} else {
					throw new NullPointerException("Hutch Response Null");
				}

			} else {
				throw new RuntimeException("Save Error | Subscription Invoice");
			}
		} else {
			throw new RuntimeException("Save Error | Subscription");
		}
	}

	@Override
	public HutchSubscribeDto getHutchSubscribeDto(PaymentRefDto paymentRefDto, SubscriptionEntity subscriptionEntity,
			SubscriptionInvoiceEntity subscriptionInvoiceEntity) throws Exception {
		HutchSubscribeDto dto = new HutchSubscribeDto();
		dto.setMsisdn("94" + appUtility.getNineDigitMobileNumber(subscriptionEntity.getMobile()));
		dto.setOfferCode(paymentRefDto.getServiceCode());
		dto.setTransactionId(subscriptionInvoiceEntity.getTransactionNo());
		return dto;
	}

	@Override
	public SubscriptionInvoiceEntity getBeginSubscriptionInvoiceEntity(TransactionBeginDto beginDto,
			SubscriptionEntity subscriptionEntity, PaymentRefDto paymentRefDto) throws Exception {

		String transactionNo = new SimpleDateFormat("yyMMdd").format(new Date()) + RandomString.make(6);

		SubscriptionInvoiceEntity invoiceEntity = new SubscriptionInvoiceEntity();
		invoiceEntity.setAmount(subscriptionEntity.getAmount());
		invoiceEntity.setCreatedDate(new Date());
		invoiceEntity.setMobile(subscriptionEntity.getMobile());
		invoiceEntity.setReferanceNo(UUID.randomUUID().toString());
		invoiceEntity.setStatus(AppConstant.ACTIVE);
		invoiceEntity.setSubscribedDays(subscriptionEntity.getSubscribedDays());
		invoiceEntity.setSuccess(AppConstant.INACTIVE);
		invoiceEntity.setTransactionNo(transactionNo);
		invoiceEntity.setTransactionType("1");
		invoiceEntity.setViewerId(subscriptionEntity.getViewerId());
		invoiceEntity.setType(AppConstant.HUTCH);
		return invoiceEntity;
	}

	@Override
	public SubscriptionEntity getSubsctiptionEntity(TransactionBeginDto beginDto, PaymentRefDto paymentRefDto)
			throws Exception {
		SubscriptionEntity entity = new SubscriptionEntity();
		entity.setAmount(paymentRefDto.getAmount());
		entity.setCreateDate(new Date());
		entity.setMobile("+94" + appUtility.getNineDigitMobileNumber(beginDto.getMobileNo()));
		entity.setPaymentPlan(beginDto.getPlanId());
		entity.setStatus(AppConstant.ACTIVE);
		entity.setSubscribedDays(paymentRefDto.getDays());
		entity.setType(AppConstant.HUTCH);
		entity.setViewerId(beginDto.getViewerId());
		return entity;
	}

	@Override
	public boolean processUnsubscription(Integer viewerid, String mobile) throws Exception {
		SubscriptionEntity subscriptionEntity = subscriptionRepository
				.findFirstByViewerIdAndStatusAndSubscribeAndType(viewerid, AppConstant.ACTIVE, AppConstant.ACTIVE, AppConstant.HUTCH);
		System.out.println("Payment Plan : " + subscriptionEntity.getPaymentPlan() );
		PaymentMethodPlanEntity entity = paymentMethodPlanRepository.findById(subscriptionEntity.getPaymentPlan()).get();
		if (subscriptionEntity != null) {
			System.out.println("PaymentMethodId : " + entity.getId());
			subscriptionEntity.setSubscribe(AppConstant.INACTIVE);
			subscriptionEntity.setUpdateDate(new Date());
			
			HutchResponseDto hutchResponseDto = hutchClient.unsubscribe("94" + appUtility.getNineDigitMobileNumber(mobile), entity.getServiceCode());
			System.out.println(hutchResponseDto.toString());
			if(hutchResponseDto.getResponseCode().equals("0") || hutchResponseDto.getResponseCode().equals("41501020") ) {
				if (subscriptionRepository.save(subscriptionEntity) != null) {
					if (viewerUnsubscriptionService.unubscribe(subscriptionEntity.getMobile(), viewerid, "UNSUBSCRIBE", "Dialog")) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
