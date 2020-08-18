package org.kiki_cpg_v2.service.impl;

import java.util.Date;

import org.kiki_cpg_v2.entity.ViewerSubscriptionEntity;
import org.kiki_cpg_v2.enums.SubscriptionType;
import org.kiki_cpg_v2.repository.ViewerSubscriptionRepository;
import org.kiki_cpg_v2.service.ViewerSubscriptionService;
import org.kiki_cpg_v2.service.ViewerUnsubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViewerSubscriptionServiceImpl implements ViewerSubscriptionService {

	@Autowired
	private ViewerSubscriptionRepository viewerSubscriptionRepository;
	
	@Autowired
	private ViewerUnsubscriptionService viewerUnsubscriptionService;

	@Override
	public boolean updateViewerSubscription(Integer viewerId, SubscriptionType mobitelAddToBill, Date date,
			String mobileNo) {
		ViewerSubscriptionEntity entity = viewerSubscriptionRepository.findOneByViewers(viewerId);
		if (entity == null) {
			entity = new ViewerSubscriptionEntity();
			entity.setDate(new Date());
			entity.setViewers(viewerId);
		}
		entity.setSubscriptionType(mobitelAddToBill);
		if (viewerSubscriptionRepository.save(entity) != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isViewerMatched(Integer viewerId) {
		ViewerSubscriptionEntity entity = viewerSubscriptionRepository.findOneByViewers(viewerId);

		if (entity != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean inavtive(Integer viewerId, String monileNo) {
		ViewerSubscriptionEntity entity = viewerSubscriptionRepository.findOneByViewers(viewerId);
		if (entity != null) {
			entity.setSubscriptionType(SubscriptionType.NONE);
			if (viewerSubscriptionRepository.save(entity) != null) {
				return true;
			}
			
			try {
				viewerUnsubscriptionService.save(monileNo, viewerId, "UNSUBSCRIBE", "Mobitel", false);
			} catch (Exception e) {e.printStackTrace();}

		}
		return false;
	}

	@Override
	public boolean changeStatus(Integer viewerId, SubscriptionType mobitelAddToBill) {
		ViewerSubscriptionEntity entity = viewerSubscriptionRepository.findOneByViewers(viewerId);
		if (entity != null) {
			entity.setSubscriptionType(mobitelAddToBill);
			if (viewerSubscriptionRepository.save(entity) != null) {
				return true;
			}

		}
		return false;
	}

}
