package org.kiki_cpg_v2.service;

import org.kiki_cpg_v2.entity.ViewerEntity;

public interface ViewerService {

	ViewerEntity updateViewerMobileNumber(String mobileNo, Integer viewerId);
	
	ViewerEntity updateViewerMobileNumberAndTrial(String mobileNo, Integer viewerId, boolean trial);

}
