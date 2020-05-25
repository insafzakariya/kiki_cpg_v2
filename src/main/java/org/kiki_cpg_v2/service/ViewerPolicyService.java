package org.kiki_cpg_v2.service;

import org.kiki_cpg_v2.dto.request.ViewerPolicyUpdateRequestDto;

public interface ViewerPolicyService {

	String updateViewerPolicy(ViewerPolicyUpdateRequestDto viewerPolicyUpdateRequestDto);

	ViewerPolicyUpdateRequestDto getViewerPolicyUpdateRequestDto(Integer viewerId, Integer packageId);

}
