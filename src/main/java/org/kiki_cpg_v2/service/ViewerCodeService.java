package org.kiki_cpg_v2.service;

import org.kiki_cpg_v2.entity.ViewerCodesEntity;

public interface ViewerCodeService {

	boolean isAlreadyUsedViewerScratchCardCode(Integer viewerId, Integer scratchCardId) throws Exception;

	boolean addViewerCode(Integer viewerId, Integer scratchCardId) throws Exception;

	ViewerCodesEntity getViewerCodesEntity(Integer viewerId, Integer scratchCardId);

}
