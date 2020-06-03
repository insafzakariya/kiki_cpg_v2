package org.kiki_cpg_v2.service;

import org.kiki_cpg_v2.dto.request.ViewerPolicyUpdateRequestDto;
import org.kiki_cpg_v2.entity.ViewerPackageEntity;
import org.kiki_cpg_v2.entity.ViewerPolicyEntity;

public interface ViewerPolicyService {

	String updateViewerPolicy(ViewerPolicyUpdateRequestDto viewerPolicyUpdateRequestDto);

	ViewerPolicyUpdateRequestDto getViewerPolicyUpdateRequestDto(Integer viewerId, Integer packageId);

	boolean checkStatus(Integer viewerId, Integer packageId);

	String deactivatePolicy(Integer viewerId, Integer newViewerId, boolean isTranfser, Integer packageId);

	ViewerPolicyEntity getCopyViewerPolicyEntity(ViewerPolicyEntity viewerPolicyEntity, Integer viewerId,
			ViewerPackageEntity viewerPackageEntity);

}
