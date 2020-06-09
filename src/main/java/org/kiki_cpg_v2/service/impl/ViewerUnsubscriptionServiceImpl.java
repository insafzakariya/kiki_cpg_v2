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
	public boolean save(String mobileNo, Integer viewerId, String suscriptionType, String serviceProvider,
			boolean updateIfExist) throws Exception {

		ViewerUnsubcriptionEntity existEntity = viewerUnsubcriptionRepository
				.findByViewerIdAndMobileNumberAndSubcriptionTypeAndServiceProvider(viewerId, mobileNo, suscriptionType,
						serviceProvider);
		ViewerUnsubcriptionEntity entity = null;
		if (updateIfExist) {
			entity = existEntity;
		} else {
			if (existEntity != null) {
				return true;
			} else {
				entity = new ViewerUnsubcriptionEntity();
			}
		}

		entity.setLastUpdatedTime(new Date());
		entity.setMobileNumber(mobileNo);
		entity.setViewerId(viewerId);
		entity.setSubcriptionType(suscriptionType);
		entity.setServiceProvider(serviceProvider);
		entity.setCreatedDateTime(new Date());
		if (viewerUnsubcriptionRepository.save(entity) != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean unubscribe(String mobileNo, Integer viewerId, String suscriptionType, String serviceProvider)
			throws Exception {
		ViewerUnsubcriptionEntity viewerUnsubcriptionEntity = new ViewerUnsubcriptionEntity();
		viewerUnsubcriptionEntity.setLastUpdatedTime(new Date());
		viewerUnsubcriptionEntity.setMobileNumber(mobileNo);
		viewerUnsubcriptionEntity.setViewerId(viewerId);
		viewerUnsubcriptionEntity.setSubcriptionType(suscriptionType);
		viewerUnsubcriptionEntity.setServiceProvider(serviceProvider);
		viewerUnsubcriptionEntity.setCreatedDateTime(new Date());

		if (viewerUnsubcriptionRepository.save(viewerUnsubcriptionEntity) != null) {
			return true;
		}

		return false;
	}

}
