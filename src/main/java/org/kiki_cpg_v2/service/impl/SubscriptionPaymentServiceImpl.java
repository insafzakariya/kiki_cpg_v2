package org.kiki_cpg_v2.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kiki_cpg_v2.dto.PlanDto;
import org.kiki_cpg_v2.dto.SubscriptionDto;
import org.kiki_cpg_v2.dto.SubscriptionPaymentDto;
import org.kiki_cpg_v2.entity.CardDataEntity;
import org.kiki_cpg_v2.entity.CustomerEntity;
import org.kiki_cpg_v2.entity.IdeabizEntity;
import org.kiki_cpg_v2.entity.InvoiceEntity;
import org.kiki_cpg_v2.entity.MerchantAccountEntity;
import org.kiki_cpg_v2.entity.PaymentMethodPlanEntity;
import org.kiki_cpg_v2.entity.SubscriptionEntity;
import org.kiki_cpg_v2.entity.SubscriptionInvoiceEntity;
import org.kiki_cpg_v2.entity.SubscriptionPaymentEntity;
import org.kiki_cpg_v2.entity.ViewerEntity;
import org.kiki_cpg_v2.entity.ViewerSubscriptionEntity;
import org.kiki_cpg_v2.entity.ViewerTrialPeriodAssociationEntity;
import org.kiki_cpg_v2.enums.DealerSubscriptionType;
import org.kiki_cpg_v2.enums.SubscriptionType;
import org.kiki_cpg_v2.repository.CardDataReository;
import org.kiki_cpg_v2.repository.CustomerRepository;
import org.kiki_cpg_v2.repository.IdeabizRepository;
import org.kiki_cpg_v2.repository.InvoiceRepository;
import org.kiki_cpg_v2.repository.MerchantAccountRepository;
import org.kiki_cpg_v2.repository.PaymentMethodPlanRepository;
import org.kiki_cpg_v2.repository.SubscriptionInvoiceRepository;
import org.kiki_cpg_v2.repository.SubscriptionPaymentRepository;
import org.kiki_cpg_v2.repository.SubscriptionRepository;
import org.kiki_cpg_v2.repository.ViewerRepository;
import org.kiki_cpg_v2.repository.ViewerSubscriptionRepository;
import org.kiki_cpg_v2.repository.ViewerTrialPeriodAssociationRepository;
import org.kiki_cpg_v2.service.SubscriptionPaymentService;
import org.kiki_cpg_v2.util.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubscriptionPaymentServiceImpl implements SubscriptionPaymentService {

	@Autowired
	private SubscriptionRepository subscriptionRepository;
	
	@Autowired
	private SubscriptionInvoiceRepository subscriptionInvoiceRepository;

	@Autowired
	private SubscriptionPaymentRepository subscriptionPaymentRepository;

	@Autowired
	private ViewerTrialPeriodAssociationRepository viewerTrialPeriodAssociationRepository;

	@Autowired
	private ViewerSubscriptionRepository viewerSubscriptionRepository;

	@Autowired
	private IdeabizRepository ideabizRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ViewerRepository viewerRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private CardDataReository cardDataReository;

	@Autowired
	private MerchantAccountRepository merchantAccountRepository;
	
	@Autowired
	private PaymentMethodPlanRepository paymentMethodPlanRepository;

	@Override
	@Transactional
	public SubscriptionPaymentDto getSubscriptionPaymentDtoByToken(String token, String type) throws Exception {
		Date curDate = new Date();
		SubscriptionPaymentEntity subscriptionPaymentEntity = subscriptionPaymentRepository
				.findOneByTokenHashAndCreatedDateLessThanEqualAndExpireDateGreaterThanEqualAndStatus(token, curDate,
						curDate, AppConstant.ACTIVE);

		if (subscriptionPaymentEntity != null) {
			ViewerTrialPeriodAssociationEntity viewerTrialPeriodAssociationEntity = viewerTrialPeriodAssociationRepository
					.findOneByViewerId(subscriptionPaymentEntity.getViewerID());

			ViewerEntity viewerEntity = viewerRepository.findById(subscriptionPaymentEntity.getViewerID()).get();

			SubscriptionPaymentDto subscriptionPaymentDto = getSubscriptionPaymentDto(subscriptionPaymentEntity,
					viewerTrialPeriodAssociationEntity, viewerEntity);
			
			subscriptionPaymentDto.setLanguage(viewerEntity.getLanguage());

			if (!type.equalsIgnoreCase("new")) {
				ViewerSubscriptionEntity viewerSubscriptionEntity = viewerSubscriptionRepository
						.findOneBySubscriptionTypeAndViewers(SubscriptionType.MOBITEL_ADD_TO_BILL,
								subscriptionPaymentEntity.getViewerID());
				IdeabizEntity ideabizEntity = ideabizRepository
						.findOneByViwerIdAndSubscribe(subscriptionPaymentEntity.getViewerID(), AppConstant.ACTIVE);
				CustomerEntity customerEntity = customerRepository.findOneByViewerIdAndMobileNoAndStatus(
						subscriptionPaymentEntity.getViewerID(), viewerEntity.getMobileNumber(),
						DealerSubscriptionType.activated);
				CardDataEntity cardDataEntity = cardDataReository.findOneByViewerIdAndStatusAndSubscribeOrderByIdDesc(
						subscriptionPaymentEntity.getViewerID(), AppConstant.ACTIVE, AppConstant.ACTIVE);

				SubscriptionEntity hutchSubscriptionEntity = subscriptionRepository
						.findFirstByViewerIdAndStatusAndSubscribeAndTypeOrderByIdDesc(
								subscriptionPaymentEntity.getViewerID(), AppConstant.ACTIVE, AppConstant.ACTIVE,
								AppConstant.HUTCH);

				if (cardDataEntity != null) {
					subscriptionPaymentDto.getSubscriptionTypeList().add("CREDIT_CARD");
					subscriptionPaymentDto.setSubscriptionType("CREDIT_CARD");
					subscriptionPaymentDto.setAlreadySubscribed(true);
					subscriptionPaymentDto.setLastSubscribeDay(
							new SimpleDateFormat("yyyy-MM-dd").format(cardDataEntity.getUpdateDate()));
				}

				if (ideabizEntity != null) {
					InvoiceEntity invoiceEntity = invoiceRepository.findFirstByViewerIdAndSuccessOrderByIdDesc(viewerEntity.getId(), AppConstant.ACTIVE);

					if (invoiceEntity != null) {
						subscriptionPaymentDto.setLastSubscribeDay(
								new SimpleDateFormat("yyyy-MM-dd").format(invoiceEntity.getCreatedDate()));
					}

					subscriptionPaymentDto.getSubscriptionTypeList().add("IDEABIZ");
					subscriptionPaymentDto.setSubscriptionType("IDEABIZ");
					subscriptionPaymentDto.setAlreadySubscribed(true);

				}

				if (viewerSubscriptionEntity != null) {
					MerchantAccountEntity merchantAccountEntity = merchantAccountRepository
							.findFirstByViewerIdAndIsSuccessOrderByIdDesc(viewerEntity.getId(), true);

					if (merchantAccountEntity != null) {
						subscriptionPaymentDto.setLastSubscribeDay(
								new SimpleDateFormat("yyyy-MM-dd").format(merchantAccountEntity.getDate()));
					}
					subscriptionPaymentDto.getSubscriptionTypeList().add("MOBITEL");
					subscriptionPaymentDto.setSubscriptionType("MOBITEL");
					subscriptionPaymentDto.setAlreadySubscribed(true);
				}

				if (customerEntity != null) {
					SubscriptionInvoiceEntity invoiceEntity = subscriptionInvoiceRepository.findFirstByViewerIdAndStatusAndSuccessAndType(viewerEntity.getId(), AppConstant.ACTIVE, AppConstant.ACTIVE, AppConstant.KEELLS);

					if (invoiceEntity != null) {
						subscriptionPaymentDto.setLastSubscribeDay(
								new SimpleDateFormat("yyyy-MM-dd").format(invoiceEntity.getCreatedDate()));
					}
					
					subscriptionPaymentDto.getSubscriptionTypeList().add("KEELLS");
					subscriptionPaymentDto.setSubscriptionType("KEELLS");
					subscriptionPaymentDto.setAlreadySubscribed(true);
				}

				if (hutchSubscriptionEntity != null) {

					SubscriptionInvoiceEntity invoiceEntity = subscriptionInvoiceRepository.findFirstByViewerIdAndStatusAndSuccessAndType(viewerEntity.getId(), AppConstant.ACTIVE, AppConstant.ACTIVE, AppConstant.HUTCH);

					if (invoiceEntity != null) {
						subscriptionPaymentDto.setLastSubscribeDay(
								new SimpleDateFormat("yyyy-MM-dd").format(invoiceEntity.getCreatedDate()));
					}

					subscriptionPaymentDto.getSubscriptionTypeList().add("HUTCH");
					subscriptionPaymentDto.setSubscriptionType("HUTCH");
					subscriptionPaymentDto.setAlreadySubscribed(true);
				}

			}

			return subscriptionPaymentDto;

		} else {
			return null;
		}
	}

	public SubscriptionPaymentDto getSubscriptionPaymentDto(SubscriptionPaymentEntity subscriptionPaymentEntity,
			ViewerTrialPeriodAssociationEntity viewerTrialPeriodAssociationEntity, ViewerEntity viewerEntity) {
		SubscriptionPaymentDto dto = new SubscriptionPaymentDto();
		dto.setPackageId(subscriptionPaymentEntity.getPackageID());
		dto.setSubscriptionPaymentId(subscriptionPaymentEntity.getId());
		dto.setTokenHash(subscriptionPaymentEntity.getTokenHash());
		dto.setViewerId(subscriptionPaymentEntity.getViewerID());
		dto.setMobile(viewerEntity.getMobileNumber());

		if (viewerTrialPeriodAssociationEntity != null && !viewerTrialPeriodAssociationEntity.isActivated()) {
			dto.setTrialVerify(true);
		} else {
			dto.setTrialVerify(false);
		}

		return dto;
	}

	@Override
	public boolean validateSubscriptionPayment(Integer subscriptionPaymentId) throws Exception {
		Date curDate = new Date();
		SubscriptionPaymentEntity subscriptionPaymentEntity = subscriptionPaymentRepository
				.findOneByIdAndCreatedDateLessThanEqualAndExpireDateGreaterThanEqualAndStatus(subscriptionPaymentId,
						curDate, curDate, AppConstant.ACTIVE);
		if (subscriptionPaymentEntity != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean validateSubscriptionPaymentByToken(String token) throws Exception {
		Date curDate = new Date();
		SubscriptionPaymentEntity subscriptionPaymentEntity = subscriptionPaymentRepository
				.findFirstByTokenHashAndCreatedDateLessThanEqualAndExpireDateGreaterThanEqualAndStatusOrderByIdDesc(
						token, curDate, curDate, AppConstant.ACTIVE);
		if (subscriptionPaymentEntity != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updateStatus(Integer subscriptionPaymentId) {
		SubscriptionPaymentEntity entity = subscriptionPaymentRepository.findById(subscriptionPaymentId).get();
		if (entity != null) {
			entity.setStatus(2);
			subscriptionPaymentRepository.save(entity);
			return true;
		}
		return false;

	}

	@Override
	public SubscriptionDto isAppleSubscribe(Integer viewerId) {
		SubscriptionDto subscriptionDto = new SubscriptionDto();
		subscriptionDto.setSubscribed(false);
		SubscriptionEntity subscriptionEntity = subscriptionRepository.findFirstByViewerIdAndStatusAndSubscribeAndTypeOrderByIdDesc(viewerId, AppConstant.ACTIVE, AppConstant.ACTIVE,AppConstant.APPLE);
		if(subscriptionEntity != null) {
			subscriptionDto.setSubscribed(true);
			subscriptionDto.setId(subscriptionEntity.getId());
			subscriptionDto.setExpireDate(subscriptionEntity.getPolicyExpDate());
			PaymentMethodPlanEntity paymentMethodPlanEntity = paymentMethodPlanRepository.findById(subscriptionEntity.getPaymentPlan()).get();
			System.out.println(paymentMethodPlanEntity== null);
			if(paymentMethodPlanEntity != null) {
				subscriptionDto.setName(paymentMethodPlanEntity.getPaymentMethodEntity().getMethodName());
				PlanDto planDto = new PlanDto();
				planDto.setId(paymentMethodPlanEntity.getId());
				planDto.setAmount(paymentMethodPlanEntity.getValue());
				planDto.setName(paymentMethodPlanEntity.getName());
				List<PlanDto> planDtos = new ArrayList<PlanDto>();
				planDtos.add(planDto);
				subscriptionDto.setPlanDtos(planDtos);
			}
		}
		return subscriptionDto;
	}

}
