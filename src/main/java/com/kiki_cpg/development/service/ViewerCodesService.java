package com.kiki_cpg.development.service;

public interface ViewerCodesService {

	boolean isAlreadyUsedViewerScratchCardCode(int viewerID, int intValue);

	void addViewerCode(int viewerID, Integer recordId);

}
