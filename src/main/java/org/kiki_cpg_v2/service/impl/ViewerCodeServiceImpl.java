package org.kiki_cpg_v2.service.impl;

import java.util.Date;

import org.kiki_cpg_v2.entity.ViewerCodesEntity;
import org.kiki_cpg_v2.repository.ViewerCodesRepository;
import org.kiki_cpg_v2.service.ViewerCodeService;
import org.kiki_cpg_v2.util.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViewerCodeServiceImpl implements ViewerCodeService {

	@Autowired
	private ViewerCodesRepository viewerCodesRepository;

	@Override
	public boolean isAlreadyUsedViewerScratchCardCode(Integer viewerId, Integer scratchCardId) throws Exception {
		ViewerCodesEntity viewerCodesEntity = viewerCodesRepository.findOneByViewerIdAndScratchCardIdAndStatus(viewerId,
				scratchCardId, AppConstant.ACTIVE);
		if (viewerCodesEntity != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean addViewerCode(Integer viewerId, Integer scratchCardId) throws Exception {
		ViewerCodesEntity codesEntity = getViewerCodesEntity(viewerId, scratchCardId);
		if(viewerCodesRepository.save(codesEntity) != null) {
			return true;
		}
		return false;
	}

	@Override
	public ViewerCodesEntity getViewerCodesEntity(Integer viewerId, Integer scratchCardId) {
		ViewerCodesEntity entity = new ViewerCodesEntity();
		entity.setActionTime(new Date());
		entity.setScratchCardId(scratchCardId);
		entity.setStatus(AppConstant.ACTIVE);
		entity.setViewerId(viewerId);
		return entity;
	}

}
