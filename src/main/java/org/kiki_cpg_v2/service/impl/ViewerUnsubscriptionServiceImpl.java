package org.kiki_cpg_v2.service.impl;

import java.util.Date;

import org.kiki_cpg_v2.entity.ViewerUnsubcriptionEntity;
import org.kiki_cpg_v2.repository.ViewerUnsubcriptionRepository;
import org.kiki_cpg_v2.service.ViewerUnsubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViewerUnsubscriptionServiceImpl implements ViewerUnsubscriptionService {

	@Autowired
	private ViewerUnsubcriptionRepository viewerUnsubcriptionRepository;

	@Override
	public boolean save(String mobileNo, Integer viewerId, String suscriptionType, String serviceProvider) {
		ViewerUnsubcriptionEntity entity = new ViewerUnsubcriptionEntity();
		entity.setLastUpdatedTime(new Date());
		entity.setMobileNumber(mobileNo);
		entity.setViewerId(viewerId);
		entity.setSubcriptionType(suscriptionType);
		entity.setServiceProvider(serviceProvider);
		if (viewerUnsubcriptionRepository.save(entity) != null) {
			return true;
		}
		return false;
	}

}
