/**
 * @DaJul 29, 2020 @HNBServiceImpl.java
 */
package org.kiki_cpg_v2.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.kiki_cpg_v2.dto.PaymentRefDto;
import org.kiki_cpg_v2.dto.request.HNBBeginDto;
import org.kiki_cpg_v2.dto.request.HNBVerifyDto;
import org.kiki_cpg_v2.entity.CardDataEntity;
import org.kiki_cpg_v2.entity.CardInvoiceEntity;
import org.kiki_cpg_v2.entity.PaymentMethodPlanEntity;
import org.kiki_cpg_v2.repository.CardDataReository;
import org.kiki_cpg_v2.repository.CardInvoiceRepository;
import org.kiki_cpg_v2.repository.PaymentMethodPlanRepository;
import org.kiki_cpg_v2.service.HNBService;
import org.kiki_cpg_v2.service.PaymentLogService;
import org.kiki_cpg_v2.service.ViewerPolicyService;
import org.kiki_cpg_v2.service.ViewerService;
import org.kiki_cpg_v2.service.ViewerUnsubscriptionService;
import org.kiki_cpg_v2.util.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

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

	@Override
	public PaymentRefDto beginTransaction(HNBBeginDto hnbBeginDto, boolean isNew) throws Exception {

		PaymentRefDto paymentRefDto = getPaymentRefDto(hnbBeginDto);

		CardInvoiceEntity cardInvoiceEntity = getCardInvoiceEntityBegining(hnbBeginDto, paymentRefDto);
		if (isNew) {
			CardDataEntity cardDataEntity = getCardDataEntityBegining(hnbBeginDto, paymentRefDto);
			cardDataReository.save(cardDataEntity);
		} else {
			throw new Exception("save error");
		}

		if (cardInvoiceRepository.save(cardInvoiceEntity) != null) {
			return paymentRefDto;
		} else {
			throw new Exception("save error");
		}

	}

	@Override
	public CardDataEntity getCardDataEntityBegining(HNBBeginDto hnbBeginDto, PaymentRefDto paymentRefDto)
			throws Exception {
		CardDataEntity cardDataEntity = new CardDataEntity();
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
	public CardInvoiceEntity getCardInvoiceEntityBegining(HNBBeginDto hnbBeginDto, PaymentRefDto paymentRefDto)
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
	public PaymentRefDto getPaymentRefDto(HNBBeginDto hnbBeginDto) throws Exception {

		PaymentRefDto dto = new PaymentRefDto();

		PaymentMethodPlanEntity entity = paymentMethodPlanRepository.findById(hnbBeginDto.getPlanId()).get();

		String ref = hnbBeginDto.getViewerId() + new SimpleDateFormat("yyyyMMddHHmmssZ").format(new Date());
		ref.replace("+", "");
		dto.setReferanceNo(ref);
		dto.setTransactionUUID(UUID.randomUUID().toString());
		dto.setAmount(entity.getValue());
		dto.setDays(entity.getDays());

		return dto;
	}

	@Override
	public void saveTransaction(MultiValueMap<String, String> formData) throws Exception {
		System.out.println(formData.toString());
		System.out.println(formData.getFirst("req_transaction_uuid"));
		System.out.println(formData.getFirst("req_transaction_uuid").trim());
		CardInvoiceEntity cardInvoiceEntity = cardInvoiceRepository
				.findFirstByTransactionNo(formData.getFirst("req_transaction_uuid").trim());
		CardDataEntity cardDataEntity = cardDataReository.findFirstByViewerIdAndStatusOrderByIdDesc(cardInvoiceEntity.getViewerId(),
				AppConstant.ACTIVE);
		boolean unsubscrideEntityUpdate = false;
		if (cardInvoiceEntity != null && cardDataEntity != null) {
			paymentLogService.createPaymentLog("HNB", formData.getFirst("req_transaction_uuid"),
					formData.getFirst("decision"), cardInvoiceEntity.getViewerId(), cardDataEntity.getMobile(),
					formData.toString());

			System.out.println("Log Created");

			if (formData.getFirst("decision").trim().equalsIgnoreCase("ACCEPT")) {
				viewerService.updateViewerMobileNumber(cardDataEntity.getMobile(), cardInvoiceEntity.getViewerId());

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
					if (packageId > 0) {
						if (viewerPolicyService
								.updateViewerPolicy(viewerPolicyService
										.getViewerPolicyUpdateRequestDto(cardInvoiceEntity.getViewerId(), packageId))
								.equalsIgnoreCase("success")) {
							viewerUnsubscriptionService.save(cardDataEntity.getMobile(),
									cardInvoiceEntity.getViewerId(), "SUBSCRIBE", "Card", unsubscrideEntityUpdate);
						}
					}
				}

			}
		} else {
			paymentLogService.createPaymentLog("HNB", formData.getFirst("req_transaction_uuid"),
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
		List<CardDataEntity> cardDataEntities = cardDataReository.findByViewerIdAndStatusAndSubscribe(viewerid, AppConstant.ACTIVE, AppConstant.ACTIVE);
		
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

}
