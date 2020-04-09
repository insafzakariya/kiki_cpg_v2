package com.kiki_cpg.development.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiki_cpg.development.entity.ViewerCodes;
import com.kiki_cpg.development.repository.ViewerCodesRepository;
import com.kiki_cpg.development.service.ViewerCodesService;

@Service
public class ViewerCodesServiceImpl implements ViewerCodesService {
	@Autowired
	ViewerCodesRepository viewerCodeRepo;

	@Override
	public boolean isAlreadyUsedViewerScratchCardCode(int viewerID, int scratchCardId) {
		// TODO Auto-generated method stub
		ViewerCodes viewerCodes = viewerCodeRepo.findByViewerIdAndScratchCardId(viewerID, scratchCardId);
		if (viewerCodes != null) {
			return true;
		}
		return false;
	}

	@Override
	public void addViewerCode(int viewerID, Integer cardCodeId) {
		// TODO Auto-generated method stub
		ViewerCodes viewerCodes = new ViewerCodes();
		viewerCodes.setActionTime(new Date());
		viewerCodes.setScratchCardId(cardCodeId);
		viewerCodes.setStatus(1);
		viewerCodes.setViewerId(viewerID);
		viewerCodeRepo.save(viewerCodes);

	}

}
