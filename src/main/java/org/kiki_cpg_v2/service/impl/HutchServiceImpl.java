/**
 * @DaSep 2, 2020 @HutchServiceImpl.java
 */
package org.kiki_cpg_v2.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.kiki_cpg_v2.client.HutchClient;
import org.kiki_cpg_v2.dto.HutchResponseDto;
import org.kiki_cpg_v2.dto.PaymentRefDto;
import org.kiki_cpg_v2.dto.request.HutchSubscribeDto;
import org.kiki_cpg_v2.dto.request.TransactionBeginDto;
import org.kiki_cpg_v2.entity.SubscriptionEntity;
import org.kiki_cpg_v2.entity.SubscriptionInvoiceEntity;
import org.kiki_cpg_v2.entity.ViewerEntity;
import org.kiki_cpg_v2.repository.SubscriptionInvoiceRepository;
import org.kiki_cpg_v2.repository.SubscriptionRepository;
import org.kiki_cpg_v2.repository.ViewerRepository;
import org.kiki_cpg_v2.service.HNBService;
import org.kiki_cpg_v2.service.HutchService;
import org.kiki_cpg_v2.service.PackageConfigService;
import org.kiki_cpg_v2.service.PaymentDetailService;
import org.kiki_cpg_v2.service.PaymentLogService;
import org.kiki_cpg_v2.service.ViewerPolicyService;
import org.kiki_cpg_v2.util.AppConstant;
import org.kiki_cpg_v2.util.AppUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	private PaymentLogService paymentLogService;
	
	@Autowired
	private HutchClient hutchClient;

	@Autowired
	private AppUtility appUtility;

	@Override
	public Map<String, String> transaction(Map<String, String> requestMap) throws Exception {
		Map<String, String> response = null;
		if (requestMap.containsKey("responseCode") && requestMap.get("responseCode").equalsIgnoreCase("0")) {
			if (requestMap.containsKey("transactionType")) {
				String transactionType = requestMap.get("transactionType");
				switch (transactionType) {
				case "1":
					response = subscribe(requestMap);
					break;
				case "3":
					response = deactivation(requestMap);
					break;
				case "6":
					response = autoRenew(requestMap);
					break;

				default:
					response = new HashMap<String, String>();
					paymentLogService.createPaymentLog(AppConstant.HUTCH, "", "" , -1, "", requestMap.toString());
					response.put("ResultCode", "103");
					response.put("Description", "transactionType Not Supported");
				}
			} else {
				response = new HashMap<String, String>();
				paymentLogService.createPaymentLog(AppConstant.HUTCH, "", "" , -1, "", requestMap.toString());
				response.put("ResultCode", "102");
				response.put("Description", "transactionType Not found in request");
			}
		} else if (requestMap.containsKey("responseCode")) {
			response = new HashMap<String, String>();
			paymentLogService.createPaymentLog(AppConstant.HUTCH, "", "" , -1, "", requestMap.toString());
			response.put("ResultCode", "100");
			response.put("Description", "Log saved | Fail request");
		} else {
			response = new HashMap<String, String>();
			paymentLogService.createPaymentLog(AppConstant.HUTCH, "", "" , -1, "", requestMap.toString());
			response.put("ResultCode", "101");
			response.put("Description", "Log saved");
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
				paymentLogService.createPaymentLog(AppConstant.HUTCH, "", "" , viewerEntity.getId(), requestMap.get("MSISDN"), requestMap.toString());
				SubscriptionEntity subscriptionEntity = subscriptionRepository.findFirstByViewerIdAndStatusAndSubscribe(
						viewerEntity.getId(), AppConstant.ACTIVE, AppConstant.ACTIVE);

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
													subscriptionEntity.getViewerId(), packageId))
											.equalsIgnoreCase("success")) {
										response.put("ResultCode", "0");
										response.put("Description", "Success");
									} else {
										response.put("ResultCode", "1");
										response.put("Description", "Policy Not Updated");
									}
								} else {
									response.put("ResultCode", "2");
									response.put("Description", "Package Not Found");
								}
							} else {
								response.put("ResultCode", "3");
								response.put("Description", "Payment Details save error");
							}
						} else {
							response.put("ResultCode", "4");
							response.put("Description", "subscriptionInvoice save error");
						}
					} else {
						response.put("ResultCode", "5");
						response.put("Description", "subscription save error");
					}
				} else {
					response.put("ResultCode", "6");
					response.put("Description", "Subscription Not Fund");
				}

			}else {
				paymentLogService.createPaymentLog(AppConstant.HUTCH, "", "" , -1, requestMap.get("MSISDN"), requestMap.toString());
				response.put("ResultCode", "7");
				response.put("Description", "Viewer Not Fund");
			}
		} else {
			paymentLogService.createPaymentLog(AppConstant.HUTCH, "", "" , -1, "", requestMap.toString());
			response.put("ResultCode", "8");
			response.put("Description", "MSISDN Not Fund in request");
		}
		return response;
	}

	@Override
	public SubscriptionInvoiceEntity getSubscriptionInvoiceEntity(Map<String, String> requestMap,
			SubscriptionEntity subscriptionEntity) throws Exception {

		String transactionNo = new SimpleDateFormat("yyMMdd").format(new Date()) + RandomStringUtils.random(6);

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
		invoiceEntity.setTransactionType(requestMap.get("transactionType"));
		invoiceEntity.setViewerId(subscriptionEntity.getViewerId());
		invoiceEntity.setType(AppConstant.HUTCH);
		return invoiceEntity;
	}

	@Override
	public Map<String, String> deactivation(Map<String, String> requestMap) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> subscribe(Map<String, String> requestMap) throws Exception {
		Map<String, String> response = new HashMap<String, String>();
		if (requestMap.containsKey("MSISDN")) {
			ViewerEntity viewerEntity = viewerRepository
					.findFirstByMobileNumberEndingWithOrderByIdDesc(requestMap.get("MSISDN"));
			if (viewerEntity != null) {
				paymentLogService.createPaymentLog(AppConstant.HUTCH, "", "" , viewerEntity.getId(), requestMap.get("MSISDN"), requestMap.toString());
				SubscriptionEntity subscriptionEntity = subscriptionRepository.findFirstByViewerIdAndStatusAndSubscribe(
						viewerEntity.getId(), AppConstant.ACTIVE, AppConstant.ACTIVE);

				if (subscriptionEntity != null) {
					SubscriptionInvoiceEntity invoiceEntity = subscriptionInvoiceRepository.findByViewerIdAndTypeAndStatusAndSuccess
							(viewerEntity.getId(), AppConstant.HUTCH, AppConstant.ACTIVE, AppConstant.INACTIVE);

					if(invoiceEntity != null) {

						invoiceEntity.setDecision(AppConstant.ACCEPT);
						invoiceEntity.setSuccess(AppConstant.ACTIVE);
						
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
														subscriptionEntity.getViewerId(), packageId))
												.equalsIgnoreCase("success")) {
											response.put("ResultCode", "0");
											response.put("Description", "Success");
										} else {
											response.put("ResultCode", "1");
											response.put("Description", "Policy Not Updated");
										}
									} else {
										response.put("ResultCode", "2");
										response.put("Description", "Package Not Found");
									}
								} else {
									response.put("ResultCode", "3");
									response.put("Description", "Payment Details save error");
								}
							} else {
								response.put("ResultCode", "4");
								response.put("Description", "subscriptionInvoice save error");
							}
						} else {
							response.put("ResultCode", "5");
							response.put("Description", "subscription save error");
						}
					} else {
						response.put("ResultCode", "9");
						response.put("Description", "SubscriptionInvoice not found");
					}
					
				} else {
					response.put("ResultCode", "6");
					response.put("Description", "Subscription Not Fund");
				}

			}else {
				paymentLogService.createPaymentLog(AppConstant.HUTCH, "", "" , -1, requestMap.get("MSISDN"), requestMap.toString());
				response.put("ResultCode", "7");
				response.put("Description", "Viewer Not Fund");
			}
		} else {
			paymentLogService.createPaymentLog(AppConstant.HUTCH, "", "" , -1, "", requestMap.toString());
			response.put("ResultCode", "8");
			response.put("Description", "MSISDN Not Fund in request");
		}
		return response;
	}

	@Override
	@Transactional
	public PaymentRefDto beginSubscribe(TransactionBeginDto beginDto) throws Exception {
		
		PaymentRefDto paymentRefDto = hnbService.getPaymentRefDto(beginDto, -1, -0.1);
	
		SubscriptionEntity subscriptionEntity = getSubsctiptionEntity(beginDto, paymentRefDto);
		SubscriptionInvoiceEntity subscriptionInvoiceEntity = getBeginSubscriptionInvoiceEntity(beginDto, subscriptionEntity, paymentRefDto );
		
		if(subscriptionRepository.save(subscriptionEntity)!= null) {
			if(subscriptionInvoiceRepository.save(subscriptionInvoiceEntity) != null) {
				HutchSubscribeDto hutchSubscribeDto = getHutchSubscribeDto(paymentRefDto, subscriptionEntity, subscriptionInvoiceEntity);
				HutchResponseDto hutchResponseDto = hutchClient.subscribe(hutchSubscribeDto);
				if(hutchResponseDto != null) {
					paymentLogService.createPaymentLog(AppConstant.HUTCH, "SUBSCRIPTION", "SUBSCRIPTION" , subscriptionEntity.getViewerId(), subscriptionEntity.getMobile(), hutchResponseDto.toString());
					if(hutchResponseDto.getResultCode().equals("0")) {
						subscriptionEntity.setSubscribe(AppConstant.ACTIVE);
						subscriptionInvoiceEntity.setSuccess(AppConstant.ACTIVE);
						if(subscriptionRepository.save(subscriptionEntity)!= null) {
							if(subscriptionInvoiceRepository.save(subscriptionInvoiceEntity) != null) {
								paymentRefDto.setStatus(AppConstant.ACCEPT);
								return paymentRefDto;
							} else {
								throw new RuntimeException("Update Error | subscriptionInvoiceEntity");
							}
						} else {
							throw new RuntimeException("Update Error | subscriptionEntity");
						}
					} else {
						throw new RuntimeException("Result not successfully");
					}
				} else {
					throw new NullPointerException("HutchResponseDto null");
				}
				
			} else {
				throw new RuntimeException("Save Error | subscriptionInvoiceEntity");
			}
		} else {
			throw new RuntimeException("Save Error | subscriptionEntity");
		}
	}


	@Override
	public HutchSubscribeDto getHutchSubscribeDto(PaymentRefDto paymentRefDto, SubscriptionEntity subscriptionEntity, SubscriptionInvoiceEntity subscriptionInvoiceEntity) throws Exception{
		HutchSubscribeDto dto = new  HutchSubscribeDto();
		dto.setMsisdn("+94" + appUtility.getNineDigitMobileNumber(subscriptionEntity.getMobile()));
		dto.setOfferCode(paymentRefDto.getServiceCode());
		dto.setTransactionId(subscriptionInvoiceEntity.getTransactionNo());
		return dto;
	}

	@Override
	public SubscriptionInvoiceEntity getBeginSubscriptionInvoiceEntity(TransactionBeginDto beginDto,
			SubscriptionEntity subscriptionEntity, PaymentRefDto paymentRefDto) throws Exception {
		
		String transactionNo = new SimpleDateFormat("yyMMdd").format(new Date()) + RandomStringUtils.random(6);

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
	public SubscriptionEntity getSubsctiptionEntity(TransactionBeginDto beginDto, PaymentRefDto paymentRefDto) throws Exception{
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

	

}
