package org.kiki_cpg_v2.service.impl;

import java.util.Date;

import org.kiki_cpg_v2.entity.ViewerSubscriptionEntity;
import org.kiki_cpg_v2.enums.SubscriptionType;
import org.kiki_cpg_v2.repository.ViewerSubscriptionRepository;
import org.kiki_cpg_v2.service.MobitelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MobitelServiceImpl implements MobitelService{

	@Autowired
	private ViewerSubscriptionRepository viewerSubscriptionRepository;
	
	@Override
	public boolean processUnsubscriptionMobitel(Integer viewerid, String mobile) {
		if(updateViewerSubscription(viewerid, SubscriptionType.NONE, new Date(),
				mobile)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updateViewerSubscription(Integer viewerid, SubscriptionType type, Date date, String mobile) {
		ViewerSubscriptionEntity entity = viewerSubscriptionRepository.findOneByViewers(viewerid);
		if(entity == null) {
			entity = new ViewerSubscriptionEntity();
			entity.setDate(new Date());
			entity.setViewers(viewerid);
		}
		entity.setSubscriptionType(type);
		if(viewerSubscriptionRepository.save(entity)!= null) {
			return true;
		}
		
		return false;
	}

	
	
}
