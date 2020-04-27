package com.kiki_cpg.development.service;

public interface ContentService {

	boolean updateViewerPolicies(Integer viewerId, Integer packageId, boolean isAddingRemainingDays) throws Exception;

}
