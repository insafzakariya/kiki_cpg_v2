package com.kiki_cpg.development.repository.Custom;

import java.util.List;

import com.kiki_cpg.development.entity.ViewerPolicies;

public interface ViewerPoliciesRepositoryCustom {
	List<ViewerPolicies> getFilteredViewerPoliciesForCurPackage(int viewerID);
}
