 package com.kiki_cpg.development.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiki_cpg.development.dto.ViewerUnsubcriptionDto;
import com.kiki_cpg.development.entity.ViewerUnsubcription;
import com.kiki_cpg.development.repository.ViewerUnsubcriptionRepository;
import com.kiki_cpg.development.service.ViewerUnsubcriptionService;

@Service
public class ViewerUnsubcriptionServiceImpl implements ViewerUnsubcriptionService{
	@Autowired
	ViewerUnsubcriptionRepository repo;

	@Override
	public void addViewerUnsubcription(ViewerUnsubcriptionDto dto) {
		// TODO Auto-generated method stub
		ViewerUnsubcription viewerUnsubcription = new ViewerUnsubcription();
		viewerUnsubcription.setLastUpdatedTime(dto.getLastUpdatedTime());
		viewerUnsubcription.setCreatedDateTime(dto.getCreatedDateTime());
		viewerUnsubcription.setDeletedDateTime(dto.getDeletedDateTime());
		viewerUnsubcription.setMobileNumber(dto.getMobileNumber());
		viewerUnsubcription.setViewerId(dto.getViewerId());
		viewerUnsubcription.setSubcriptionType(dto.getSubcriptionType());
		viewerUnsubcription.setServiceProvider(dto.getServiceProvider());
		repo.save(viewerUnsubcription);
	}

}
