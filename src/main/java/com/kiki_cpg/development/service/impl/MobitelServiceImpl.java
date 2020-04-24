package com.kiki_cpg.development.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiki_cpg.development.controller.MainController;
import com.kiki_cpg.development.dto.SubscriptionPaymentDto;
import com.kiki_cpg.development.entity.ViewerSubscription;
import com.kiki_cpg.development.entity.ViewerTrialPeriodAssociation;
import com.kiki_cpg.development.enums.SubscriptionType;
import com.kiki_cpg.development.repository.SubscriptionRepository;
import com.kiki_cpg.development.repository.ViewerTrialPeriodAssociationRepository;
import com.kiki_cpg.development.service.MobitelService;
import com.kiki_cpg.development.service.MobitelWsClientService;

@Service
public class MobitelServiceImpl implements MobitelService {

	final static Logger logger = LoggerFactory.getLogger(MobitelServiceImpl.class);

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	private MobitelWsClientService mobitelWsClientService;

	@Autowired
	private ViewerTrialPeriodAssociationRepository viewerTrialPeriodAssociationRepository;

	@Override
	public SubscriptionPaymentDto getSubscriptionPaymentDto(int paymentMethodId, int subscriptionPaymentId,
			String numberString, boolean isTrial, Integer viewerId) throws Exception{

		SubscriptionPaymentDto dto = new SubscriptionPaymentDto();

		ViewerSubscription viewerSubscription = subscriptionRepository
				.findOneBySubscriptionTypeAndViewers(SubscriptionType.MOBITEL_ADD_TO_BILL, viewerId);
		if (viewerSubscription != null) {
			dto.setMobitelSubscribe(true);
		} else {
			try {
				if (!numberString.isEmpty() && !numberString.equals("null")) {
					logger.info("mobileNo = " + numberString);
					if (mobitelWsClientService.getIsMobitelNumber(numberString)) {
						if (isTrial) { // add trial period

							ViewerTrialPeriodAssociation viewerTrialPeriodAssociation = viewerTrialPeriodAssociationRepository
									.findOneByViewerId(viewerId);
							if (viewerTrialPeriodAssociation == null) {
								viewerTrialPeriodAssociation = new ViewerTrialPeriodAssociation();
								viewerTrialPeriodAssociation.setOnGoing(false);
								viewerTrialPeriodAssociation.setActivated(false);
								viewerTrialPeriodAssociation.setViewerId(viewerId);

								viewerTrialPeriodAssociationRepository.save(viewerTrialPeriodAssociation);
							}
						}
						dto.setMobitelConnection(true);
					} else {
						dto.setMobitelConnection(false);
					}
				} else {
					dto.setMobitelConnection(false);
				}
			} catch (Exception e) {
				logger.info("exception occures when checking is mobitel number = " + e);
				throw new RuntimeException("exception occures when checking is mobitel number = " + e);
			}
		}
		return dto;
	}

}
