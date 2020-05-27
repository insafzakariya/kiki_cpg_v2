package org.kiki_cpg_v2.service.impl;

import java.util.Date;

import org.kiki_cpg_v2.dto.SubscriptionPaymentDto;
import org.kiki_cpg_v2.entity.CustomerEntity;
import org.kiki_cpg_v2.entity.IdeabizEntity;
import org.kiki_cpg_v2.entity.SubscriptionPaymentEntity;
import org.kiki_cpg_v2.entity.ViewerEntity;
import org.kiki_cpg_v2.entity.ViewerSubscriptionEntity;
import org.kiki_cpg_v2.entity.ViewerTrialPeriodAssociationEntity;
import org.kiki_cpg_v2.enums.DealerSubscriptionType;
import org.kiki_cpg_v2.enums.SubscriptionType;
import org.kiki_cpg_v2.repository.CustomerRepository;
import org.kiki_cpg_v2.repository.IdeabizRepository;
import org.kiki_cpg_v2.repository.SubscriptionPaymentRepository;
import org.kiki_cpg_v2.repository.ViewerRepository;
import org.kiki_cpg_v2.repository.ViewerSubscriptionRepository;
import org.kiki_cpg_v2.repository.ViewerTrialPeriodAssociationRepository;
import org.kiki_cpg_v2.service.SubscriptionService;
import org.kiki_cpg_v2.util.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

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

	@Override
	@Transactional
	public SubscriptionPaymentDto getSubscriptionPaymentDtoByToken(String token, String type) throws Exception {
		Date curDate = new Date();
		SubscriptionPaymentEntity subscriptionPaymentEntity = subscriptionPaymentRepository
				.findOneByTokenHashAndCreatedDateLessThanEqualAndExpireDateGreaterThanEqualAndStatus(token, curDate, curDate, AppConstant.ACTIVE);
		
		if(subscriptionPaymentEntity != null) {
			ViewerTrialPeriodAssociationEntity viewerTrialPeriodAssociationEntity = viewerTrialPeriodAssociationRepository
					.findOneByViewerId(subscriptionPaymentEntity.getViewerID());
			
			ViewerEntity viewerEntity = viewerRepository.findById(subscriptionPaymentEntity.getViewerID()).get();
			
			SubscriptionPaymentDto subscriptionPaymentDto = getSubscriptionPaymentDto(subscriptionPaymentEntity, viewerTrialPeriodAssociationEntity, viewerEntity);
			
			if(!type.equalsIgnoreCase("new")) {
				ViewerSubscriptionEntity viewerSubscriptionEntity = viewerSubscriptionRepository.findOneBySubscriptionTypeAndViewers(
						SubscriptionType.MOBITEL_ADD_TO_BILL, subscriptionPaymentEntity.getViewerID());
				IdeabizEntity ideabizEntity = ideabizRepository.findOneByViwerIdAndSubscribe(subscriptionPaymentEntity.getViewerID(), AppConstant.ACTIVE);
				CustomerEntity customerEntity = customerRepository.findOneByViewerIdAndMobileNoAndStatus(subscriptionPaymentEntity.getViewerID(),
						viewerEntity.getMobileNumber(), DealerSubscriptionType.activated);
				
				if (ideabizEntity != null) {
					subscriptionPaymentDto.setSubscriptionType("IDEABIZ");
					subscriptionPaymentDto.setAlreadySubscribed(true);
				} 

				if (viewerSubscriptionEntity != null) {
					subscriptionPaymentDto.setSubscriptionType("MOBITEL");
					subscriptionPaymentDto.setAlreadySubscribed(true);
				}

				if (customerEntity != null) {
					subscriptionPaymentDto.setSubscriptionType("SCRATCH_CARD");
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
				.findOneByIdAndCreatedDateLessThanEqualAndExpireDateGreaterThanEqualAndStatus(subscriptionPaymentId, curDate, curDate, AppConstant.ACTIVE);
		if(subscriptionPaymentEntity != null) {
			return true;
		}
		return false;
	}

}
