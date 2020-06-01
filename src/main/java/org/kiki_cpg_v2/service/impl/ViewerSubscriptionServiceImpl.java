package org.kiki_cpg_v2.service.impl;

import java.util.Date;

import org.kiki_cpg_v2.entity.ViewerSubscriptionEntity;
import org.kiki_cpg_v2.enums.SubscriptionType;
import org.kiki_cpg_v2.repository.ViewerSubscriptionRepository;
import org.kiki_cpg_v2.service.ViewerSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViewerSubscriptionServiceImpl implements ViewerSubscriptionService {
	
	@Autowired
	private ViewerSubscriptionRepository viewerSubscriptionRepository;

	@Override
	public boolean updateViewerSubscription(Integer viewerId, SubscriptionType mobitelAddToBill, Date date,
			String mobileNo) {
		ViewerSubscriptionEntity entity = viewerSubscriptionRepository.findOneByViewers(viewerId);
		if(entity == null) {
			entity = new ViewerSubscriptionEntity();
			entity.setDate(new Date());
			entity.setViewers(viewerId);
		}
		entity.setSubscriptionType(mobitelAddToBill);
		if(viewerSubscriptionRepository.save(entity)!= null) {
			return true;	
		}		
		return false;
	}

}
