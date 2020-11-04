/**
 * @DaJul 29, 2020 @HNBServiceImpl.java
 */
package org.kiki_cpg_v2.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.kiki_cpg_v2.dto.PaymentRefDto;
import org.kiki_cpg_v2.dto.request.TransactionBeginDto;
import org.kiki_cpg_v2.dto.request.HNBVerifyDto;
import org.kiki_cpg_v2.entity.CardDataEntity;
import org.kiki_cpg_v2.entity.CardInvoiceEntity;
import org.kiki_cpg_v2.entity.PaymentMethodPlanEntity;
import org.kiki_cpg_v2.repository.CardDataReository;
import org.kiki_cpg_v2.repository.CardInvoiceRepository;
import org.kiki_cpg_v2.repository.PaymentMethodPlanRepository;
import org.kiki_cpg_v2.service.CronViewerRepostService;
import org.kiki_cpg_v2.service.HNBService;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.cybersource.ws.client.Client;
import com.cybersource.ws.client.ClientException;
import com.cybersource.ws.client.FaultException;

/**
 * @author Anjana Thrishakya
 */
@Service
public class HNBServiceImpl implements HNBService {

	@Autowired
	private PaymentMethodPlanRepository paymentMethodPlanRepository;

	@Autowired
	private CardInvoiceRepository cardInvoiceRepository;

	@Autowired
	private PaymentLogService paymentLogService;

	@Autowired
	private ViewerService viewerService;

	@Autowired
	private ViewerPolicyService viewerPolicyService;

	@Autowired
	private CardDataReository cardDataReository;

	@Autowired
	private ViewerUnsubscriptionService viewerUnsubscriptionService;

	@Autowired
	private PaymentDetailService paymentDetailService;

	@Autowired
	private PackageConfigService packageConfigService;

	@Autowired
	private CronViewerRepostService cronViewerRepostService;

	@Autowired
	private AppUtility appUtility;

	@Override
	public PaymentRefDto beginTransaction(TransactionBeginDto hnbBeginDto, boolean isNew, Integer days, Double value)
			throws Exception {

		PaymentRefDto paymentRefDto = getPaymentRefDto(hnbBeginDto, days, value);

		CardInvoiceEntity cardInvoiceEntity = getCardInvoiceEntityBegining(hnbBeginDto, paymentRefDto);
		if (isNew) {
			CardDataEntity cardDataEntity = getCardDataEntityBegining(hnbBeginDto, paymentRefDto);
			if(cardDataReository.save(cardDataEntity)!= null) {
				
			}else {
				throw new Exception("save error");
			}
		}
		
		cardInvoiceEntity = cardInvoiceRepository.save(cardInvoiceEntity);
		if (cardInvoiceEntity != null) {
			paymentRefDto.setCardInvoiceId(cardInvoiceEntity.getId());
			return paymentRefDto;
		} else {
			throw new Exception("save error");
		}

	}

	@Override
	public CardDataEntity getCardDataEntityBegining(TransactionBeginDto hnbBeginDto, PaymentRefDto paymentRefDto)
			throws Exception {
		CardDataEntity cardDataEntity = new CardDataEntity();
		cardDataEntity.setPaymentPlan(hnbBeginDto.getPlanId());
		cardDataEntity.setViewerId(hnbBeginDto.getViewerId());
		cardDataEntity.setAmount(paymentRefDto.getAmount());
		cardDataEntity.setMobile(hnbBeginDto.getMobileNo());
		cardDataEntity.setSubscribedDays(paymentRefDto.getDays());
		cardDataEntity.setCreateDate(new Date());
		cardDataEntity.setUpdateDate(new Date());
		cardDataEntity.setUpdateDate(new Date());
		cardDataEntity.setStatus(AppConstant.ACTIVE);
		cardDataEntity.setSubscribe(AppConstant.INACTIVE);

		return cardDataEntity;
	}

	/**
	 * @param hnbBeginDto
	 * @param paymentRefDto
	 * @return
	 */
	@Override
	public CardInvoiceEntity getCardInvoiceEntityBegining(TransactionBeginDto hnbBeginDto, PaymentRefDto paymentRefDto)
			throws Exception {

		CardInvoiceEntity entity = new CardInvoiceEntity();
		entity.setAmount(paymentRefDto.getAmount());
		entity.setSubscribedDays(paymentRefDto.getDays());
		entity.setCreatedDate(new Date());
		entity.setReferanceNo(paymentRefDto.getReferanceNo());
		entity.setStatus(AppConstant.ACTIVE);
		entity.setTransactionNo(paymentRefDto.getTransactionUUID());
		entity.setViewerId(hnbBeginDto.getViewerId());
		return entity;
	}

	/**
	 * @param hnbBeginDto
	 * @return
	 */
	@Override
	public PaymentRefDto getPaymentRefDto(TransactionBeginDto hnbBeginDto, Integer days, Double value) throws Exception {

		PaymentRefDto dto = new PaymentRefDto();
		PaymentMethodPlanEntity entity = paymentMethodPlanRepository.findById(hnbBeginDto.getPlanId()).get();
		if (days < 0 && value < 0.0) {
			
			days = entity.getDays();
			value = entity.getValue();
		}

		String ref = hnbBeginDto.getViewerId() + new SimpleDateFormat("yyyyMMddHHmmssZ").format(new Date());
		ref.replace("+", "");
		dto.setReferanceNo(ref);
		dto.setTransactionUUID(UUID.randomUUID().toString());
		dto.setAmount(value);
		dto.setDays(days);
		dto.setServiceCode(entity.getServiceCode());

		String frequency = AppUtility.getHnbFrequency(days);
		dto.setFrequency(frequency);

		dto.setDate(new SimpleDateFormat("yyyyMMdd").format(appUtility.getbeforeDay(30, new Date())));

		return dto;
	}

	@Override
	public void saveTransaction(MultiValueMap<String, String> formData) throws Exception {
		System.out.println(formData.toString());
		System.out.println(formData.getFirst("req_transaction_uuid"));
		System.out.println(formData.getFirst("req_transaction_uuid").trim());
		CardInvoiceEntity cardInvoiceEntity = cardInvoiceRepository
				.findFirstByTransactionNo(formData.getFirst("req_transaction_uuid").trim());
		CardDataEntity cardDataEntity = cardDataReository
				.findFirstByViewerIdAndStatusOrderByIdDesc(cardInvoiceEntity.getViewerId(), AppConstant.ACTIVE);
		boolean unsubscrideEntityUpdate = false;
		if (cardInvoiceEntity != null && cardDataEntity != null) {
			paymentLogService.createPaymentLog("HNB", formData.getFirst("req_reference_number"),
					formData.getFirst("decision"), cardInvoiceEntity.getViewerId(), cardDataEntity.getMobile(),
					formData.toString());

			System.out.println("Log Created");

			if (formData.getFirst("decision").trim().equalsIgnoreCase("ACCEPT")) {
				viewerService.updateViewerMobileNumberAndTrial(cardDataEntity.getMobile(), cardInvoiceEntity.getViewerId(), false);

				System.out.println("Accept");

				System.out.println(formData.getFirst("req_transaction_type").trim());
				if (formData.getFirst("req_transaction_type").trim()
						.equalsIgnoreCase("authorization,create_payment_token")) {
					System.out.println("authorization,create_payment_token");
					cardDataEntity = getUpdatedCardDataEntity(cardDataEntity, formData);
					cardDataReository.save(cardDataEntity);
					System.out.println("save");
					unsubscrideEntityUpdate = true;
				}

				Integer packageId = -1;

				switch (formData.getFirst("auth_amount")) {
				case "99.00":
					packageId = 107;
					break;
				case "499.00":
					packageId = 108;
					break;
				case "999.00":
					packageId = 109;
					break;

				default:
					break;
				}

				cardInvoiceEntity = getUpdatedCardInvoiceEntity(cardInvoiceEntity, formData);
				if (cardInvoiceRepository.save(cardInvoiceEntity) != null) {
					if (paymentDetailService.save(cardInvoiceEntity.getAmount(), cardDataEntity.getSubscribedDays(),
							cardDataEntity.getPolicyExpDate(), cardInvoiceEntity.getId(),
							AppConstant.CARD_HNB) != null) {
						if (packageId > 0) {
							if (viewerPolicyService.updateViewerPolicy(viewerPolicyService
									.getViewerPolicyUpdateRequestDto(cardInvoiceEntity.getViewerId(), packageId), -1)
									.equalsIgnoreCase("success")) {
								viewerUnsubscriptionService.save(cardDataEntity.getMobile(),
										cardInvoiceEntity.getViewerId(), "SUBSCRIBE", "Card", unsubscrideEntityUpdate);
							}
						}
					}
				}

			}
		} else {
			paymentLogService.createPaymentLog("HNB", formData.getFirst("req_reference_number"),
					formData.getFirst("decision"), -1, "-", formData.toString());
		}

	}

	@Override
	public CardDataEntity getUpdatedCardDataEntity(CardDataEntity entity, MultiValueMap<String, String> formData)
			throws Exception {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, entity.getSubscribedDays());
		entity.setCardExpiryDate(formData.getFirst("req_card_expiry_date").trim());
		entity.setCardNumber(formData.getFirst("req_card_number").trim());
		entity.setCardType(Integer.parseInt(formData.getFirst("req_card_type").trim()));
		entity.setCardTypeName(formData.getFirst("card_type_name").trim());
		entity.setPaymentToken(formData.getFirst("payment_token").trim());
		entity.setSignature(formData.getFirst("signature").trim());
		entity.setUpdateDate(new Date());
		entity.setSubscribe(AppConstant.ACTIVE);
		entity.setPolicyExpDate(c.getTime());
		return entity;
	}

	@Override
	public CardInvoiceEntity getUpdatedCardInvoiceEntity(CardInvoiceEntity entity,
			MultiValueMap<String, String> formData) throws Exception {
		entity.setAuthResponse(formData.getFirst("reason_code").trim());
		entity.setDecision(formData.getFirst("decision").trim());
		entity.setTransactionType(formData.getFirst("req_transaction_type").trim());
		if (formData.getFirst("decision").trim().equalsIgnoreCase("ACCEPT")) {
			entity.setSuccess(1);
		} else {
			entity.setSuccess(0);
		}
		return entity;
	}

	@Override
	public boolean processUnsubscription(Integer viewerid, String mobile) throws Exception {
		if (updateViewerSubscription(viewerid, 0, new Date(), mobile)) {
			try {
				viewerUnsubscriptionService.save(mobile, viewerid, "UNSUBSCRIBE", "Card", false);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean updateViewerSubscription(Integer viewerid, int i, Date date, String mobile) throws Exception {
		List<CardDataEntity> cardDataEntities = cardDataReository.findByViewerIdAndStatusAndSubscribe(viewerid,
				AppConstant.ACTIVE, AppConstant.ACTIVE);

		if (cardDataEntities != null) {

			for (CardDataEntity cardDataEntity : cardDataEntities) {
				cardDataEntity.setUpdateDate(date);
				cardDataEntity.setSubscribe(i);
				cardDataEntity.setStatus(i);
				cardDataReository.save(cardDataEntity);

			}
			return true;
		}

		return false;
	}

	@Override
	public PaymentRefDto verifyTransaction(HNBVerifyDto hnbVerifyDto) throws Exception {
		PaymentRefDto dto = new PaymentRefDto();
		dto.setStatus("Transaction not successfully completed");
		CardInvoiceEntity cardInvoiceEntity = cardInvoiceRepository
				.findFirstByTransactionNo(hnbVerifyDto.getTransaction_uuid());
		if (cardInvoiceEntity != null) {
			if (cardInvoiceEntity.getReferanceNo().equalsIgnoreCase(hnbVerifyDto.getReferance_no())
					&& cardInvoiceEntity.getSuccess() != null && cardInvoiceEntity.getSuccess() == 1
					&& cardInvoiceEntity.getDecision() != null
					&& cardInvoiceEntity.getDecision().equalsIgnoreCase("ACCEPT")) {
				dto.setStatus("success");
			}
		}
		return dto;
	}

	@Override
	public String processSimpleOrderPayment(CardDataEntity cardDataEntity, Integer cronId) throws Exception {

		TransactionBeginDto hnbBeginDto = new TransactionBeginDto();
		hnbBeginDto.setViewerId(cardDataEntity.getViewerId());
		hnbBeginDto.setMobileNo(cardDataEntity.getMobile());
		hnbBeginDto.setPlanId(cardDataEntity.getPaymentPlan());

		PaymentRefDto paymentRefDto = beginTransaction(hnbBeginDto, false, cardDataEntity.getSubscribedDays(),
				cardDataEntity.getAmount());

		Properties properties = new Properties();
		try (InputStream is = getClass().getResourceAsStream("/cybs.properties")) {
			properties.load(is);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("ccAuthService_run", "true");
		hashMap.put("ccCaptureService_run", "true");
		hashMap.put("recurringSubscriptionInfo_subscriptionID", cardDataEntity.getPaymentToken());
		hashMap.put("merchantReferenceCode", paymentRefDto.getReferanceNo());
		hashMap.put("purchaseTotals_currency", "USD");
		hashMap.put("purchaseTotals_grandTotalAmount", paymentRefDto.getAmount().toString());
		hashMap.put("ccAuthService_commerceIndicator", "recurring");

		try {
			Map<String, String> responseMap = Client.runTransaction(hashMap, properties);
			if (responseMap.containsKey("merchantReferenceCode")) {
				paymentLogService.createPaymentLog("HNB", responseMap.get("merchantReferenceCode"),
						responseMap.get("decision"), cardDataEntity.getViewerId(), cardDataEntity.getMobile(),
						responseMap.toString());
				cronViewerRepostService.save(cardDataEntity.getViewerId(),
						responseMap.containsKey("decision") && responseMap.get("decision").equalsIgnoreCase("ACCEPT")
								? "Success"
								: "Fail",
						cardDataEntity.getAmount(), responseMap.get("decision"), responseMap.toString(), cronId);
			} else {
				paymentLogService.createPaymentLog("HNB", "-", responseMap.get("decision"),
						cardDataEntity.getViewerId(), cardDataEntity.getMobile(), responseMap.toString());
			}

			if (responseMap.containsKey("decision") && responseMap.get("decision").equalsIgnoreCase("ACCEPT")) {
				CardInvoiceEntity cardInvoiceEntity = cardInvoiceRepository
						.findFirstByReferanceNo(paymentRefDto.getReferanceNo());

				if (cardInvoiceEntity != null) {
					MultiValueMap<String, String> formData = new LinkedMultiValueMap<String, String>();
					formData.add("decision", responseMap.get("decision"));
					formData.add("req_transaction_type", "simple order, recurring");
					formData.add("reason_code", responseMap.get("reasonCode"));
					cardInvoiceEntity = getUpdatedCardInvoiceEntity(cardInvoiceEntity, formData);
					cardDataEntity = updateCardDataEntityExpireDate(cardDataEntity);

					if (paymentDetailService.save(cardInvoiceEntity.getAmount(), cardDataEntity.getSubscribedDays(),
							cardDataEntity.getPolicyExpDate(), cardInvoiceEntity.getId(),
							AppConstant.CARD_HNB) != null) {

						Integer packageId = packageConfigService.getPackageId(cardDataEntity.getSubscribedDays(),
								AppConstant.CARD_HNB);
					

						if (packageId > 0) {
							if (viewerPolicyService.updateViewerPolicy(viewerPolicyService
									.getViewerPolicyUpdateRequestDto(cardInvoiceEntity.getViewerId(), packageId), -1)
									.equalsIgnoreCase("success")) {
								return "Success";
							}
						}
					}

				}
			}

		} catch (FaultException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}

		return "Fail";
	}

	@Override
	public CardDataEntity updateCardDataEntityExpireDate(CardDataEntity cardDataEntity) throws Exception {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, cardDataEntity.getSubscribedDays());
		cardDataEntity.setPolicyExpDate(c.getTime());
		return cardDataReository.save(cardDataEntity);
	}

}
