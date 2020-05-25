package org.kiki_cpg_v2.service.impl;

import org.kiki_cpg_v2.entity.ViewerEntity;
import org.kiki_cpg_v2.repository.ViewerRepository;
import org.kiki_cpg_v2.service.ViewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ViewerServiceImpl implements ViewerService{

	@Autowired
	private ViewerRepository viewerRepository;
	
	@Override
	public ViewerEntity updateViewerMobileNumber(String mobileNo, Integer viewerId) {
		ViewerEntity viewerEntity=viewerRepository.findById(viewerId).get();
		viewerEntity.setMobileNumber(mobileNo);
		return viewerRepository.save(viewerEntity);
	}

}
