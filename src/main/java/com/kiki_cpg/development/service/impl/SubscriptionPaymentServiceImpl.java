package com.kiki_cpg.development.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kiki_cpg.development.dto.PaymentMethodDto;
import com.kiki_cpg.development.dto.PaymentPolicyDto;
import com.kiki_cpg.development.dto.PolicyDto;
import com.kiki_cpg.development.dto.SubscriptionPaymentDto;
import com.kiki_cpg.development.entity.Config;
import com.kiki_cpg.development.entity.Customer;
import com.kiki_cpg.development.entity.Ideabiz;
import com.kiki_cpg.development.entity.PaymentMethods;
import com.kiki_cpg.development.entity.PaymentPolicies;
import com.kiki_cpg.development.entity.Policies;
import com.kiki_cpg.development.entity.SubscriptionPayments;
import com.kiki_cpg.development.entity.SystemProperty;
import com.kiki_cpg.development.entity.ViewerSubscription;
import com.kiki_cpg.development.entity.ViewerTrialPeriodAssociation;
import com.kiki_cpg.development.entity.Viewers;
import com.kiki_cpg.development.enums.DealerSubscriptionType;
import com.kiki_cpg.development.enums.SubscriptionType;
import com.kiki_cpg.development.repository.ConfigRepository;
import com.kiki_cpg.development.repository.CustomerRepository;
import com.kiki_cpg.development.repository.IdeabizRepository;
import com.kiki_cpg.development.repository.SubscriptionPaymentRepository;
import com.kiki_cpg.development.repository.SubscriptionRepository;
import com.kiki_cpg.development.repository.SystemPropertyRepository;
import com.kiki_cpg.development.repository.ViewerRepository;
import com.kiki_cpg.development.repository.ViewerTrialPeriodAssociationRepository;
import com.kiki_cpg.development.repository.Custom.PaymentRepositoryCustom;
import com.kiki_cpg.development.service.SubscriptionPaymentService;
import com.kiki_cpg.development.util.AppUtility;

@Service
@Transactional
public class SubscriptionPaymentServiceImpl implements SubscriptionPaymentService {

	@Autowired
	private SubscriptionPaymentRepository subscriptionPaymentRepository;

	@Autowired
	private PaymentRepositoryCustom paymentRepositoryCustom;

	@Autowired
	private ViewerRepository viewerRepository;

	@Autowired
	private SystemPropertyRepository systemPropertyRepository;

	@Autowired
	private ViewerTrialPeriodAssociationRepository viewerTrialPeriodAssociationRepository;

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	private IdeabizRepository ideabizRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ConfigRepository configRepository;

	@Autowired
	private AppUtility appUtility;

	@Override
	public SubscriptionPaymentDto getSubscriptionPaymentByToken(String paymentToken, String type) throws Exception {
		SubscriptionPayments subscriptionPayments = subscriptionPaymentRepository
				.findOneByTokenHashAndCreatedDateLessThanEqualAndExpireDateGreaterThanEqualAndStatus(paymentToken,
						new Date(), new Date(), 1);

		if (subscriptionPayments != null) {

			List<PaymentPolicies> paymentPolicies = paymentRepositoryCustom
					.getPaymentOptions(subscriptionPayments.getPackageID());
			Viewers viewer = viewerRepository.findByViewerId(subscriptionPayments.getViewerID());
			boolean isMobitel = appUtility.getIsMobitelNumber(viewer.getMobileNumber());
			ViewerTrialPeriodAssociation viewerTrialPeriodAssociation = viewerTrialPeriodAssociationRepository
					.findOneByViewerId(subscriptionPayments.getViewerID());

			SubscriptionPaymentDto subscriptionPaymentDto = getSubscriptionPaymentDto(subscriptionPayments,
					paymentPolicies, isMobitel, viewer, viewerTrialPeriodAssociation);

			SystemProperty systemProperty = systemPropertyRepository
					.findOneByKeyValue("susilawebpay.mcash.payment.url");
			subscriptionPaymentDto.setmCashPaymentURL(systemProperty.getValue());

			if (!type.equalsIgnoreCase("new")) {
				ViewerSubscription viewerSubscription = subscriptionPaymentRepository
						.findOneBySubscriptionTypeAndViewers(SubscriptionType.MOBITEL_ADD_TO_BILL,
								subscriptionPayments.getViewerID());
				Ideabiz ideabiz = ideabizRepository.findOneByViwer_idAndSubscribe(subscriptionPayments.getViewerID(),
						1);
				Customer customer = customerRepository.findOneByViwer_idAndMobile_noAndStatus(viewer.getViewerId(),
						viewer.getMobileNumber(), DealerSubscriptionType.activated);

				
				
				if (ideabiz != null) {

					Config config = configRepository.getOne(1);

					if (ideabiz.getSubscribed_days() == 1) {
						subscriptionPaymentDto.setIdeabizDays("Daily");
						subscriptionPaymentDto.setIdeabizAmount(config.getDay_charge());
					} else if (ideabiz.getSubscribed_days() == 7) {
						subscriptionPaymentDto.setIdeabizDays("Weekly");
						subscriptionPaymentDto.setIdeabizAmount(config.getWeek_charge());
					}
					subscriptionPaymentDto.setIdeabizSubscription("subscribed");
					subscriptionPaymentDto.setAlreadySubscribed(true);
					
				} else {
					subscriptionPaymentDto.setIdeabizSubscription("");
				}

				if (viewerSubscription != null) {
					subscriptionPaymentDto.setMobitelSubscription("subscribed");
					subscriptionPaymentDto.setAlreadySubscribed(true);
				} else {
					subscriptionPaymentDto.setMobitelSubscription("");
				}

				if (customer != null) {
					subscriptionPaymentDto.setViewerSubscription("subscribed");
					subscriptionPaymentDto.setAlreadySubscribed(true);
				} else {
					subscriptionPaymentDto.setViewerSubscription("");
				}
			}

			return subscriptionPaymentDto;

		}
		return null;
	}

	@Override
	public SubscriptionPaymentDto getSubscriptionPaymentDto(SubscriptionPayments subscriptionPayments,
			List<PaymentPolicies> paymentPolicies, boolean isMobitel, Viewers viewer,
			ViewerTrialPeriodAssociation viewerTrialPeriodAssociation) {

		SubscriptionPaymentDto dto = new SubscriptionPaymentDto();
		dto.setCreatedDate(subscriptionPayments.getCreatedDate());
		dto.setExpireDate(subscriptionPayments.getExpireDate());
		dto.setMobitel(isMobitel);
		dto.setPackageId(subscriptionPayments.getPackageID());
		dto.setStatus(subscriptionPayments.getStatus());
		dto.setSubscriptionPaymentId(subscriptionPayments.getSubscriptionPaymentID());
		dto.setTokenHash(subscriptionPayments.getTokenHash());
		dto.setViewerId(subscriptionPayments.getViewerID());
		dto.setMobile(viewer.getMobileNumber());

		if (viewerTrialPeriodAssociation != null && !viewerTrialPeriodAssociation.isActivated()) {
			dto.setTrialVerify(true);
		} else {
			dto.setTrialVerify(false);
		}

		List<PaymentPolicyDto> paymentMethods = new ArrayList<PaymentPolicyDto>();

		paymentPolicies.forEach(e -> {
			paymentMethods.add(getPaymentPolicyDto(e));
		});

		dto.setPaymentMethods(paymentMethods);

		return dto;
	}

	private PaymentPolicyDto getPaymentPolicyDto(PaymentPolicies e) {
		PaymentPolicyDto dto = new PaymentPolicyDto();
		dto.setModifiedBy(e.getModifiedBy());
		dto.setModifiedOn(e.getModifiedOn());
		dto.setPaymentAmount(e.getPaymentAmount());
		dto.setPaymentMethod(getPaymentMethodDto(e.getPaymentMethod()));
		dto.setPaymentPolicyId(e.getPaymentPolicyId());
		dto.setPaymentType(e.getPaymentType());
		dto.setPolicy(getPolicyDto(e.getPolicy()));
		dto.setStatus(e.getStatus());
		return dto;
	}

	private PolicyDto getPolicyDto(Policies policy) {
		PolicyDto dto = new PolicyDto();
		dto.setDescription(policy.getDescription());
		dto.setModifiedBy(policy.getModifiedBy());
		dto.setModifiedOn(policy.getModifiedOn());
		dto.setName(policy.getName());
		dto.setPolicyId(policy.getPolicyId());
		dto.setPolicyType(policy.getPolicyType());
		dto.setStatus(policy.getStatus());
		dto.setValidFrom(policy.getValidFrom());
		dto.setValidTo(policy.getValidTo());
		return dto;
	}

	private PaymentMethodDto getPaymentMethodDto(PaymentMethods paymentMethod) {
		PaymentMethodDto dto = new PaymentMethodDto();
		dto.setMethodName(paymentMethod.getMethodName());
		dto.setModifiedBy(paymentMethod.getModifiedBy());
		dto.setModifiedOn(paymentMethod.getModifiedOn());
		dto.setPaymentMethodId(paymentMethod.getPaymentMethodId());
		dto.setStatus(paymentMethod.getStatus());
		return dto;
	}

}
