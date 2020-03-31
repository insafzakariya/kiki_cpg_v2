package com.kiki_cpg.development.service;

import java.util.List;

import com.kiki_cpg.development.entity.Policies;
import com.kiki_cpg.development.entity.ViewerPackages;

public interface ViewerPoliciesService {
	
	public boolean addViewerPolicies(List<Policies> policies, Integer viewerID, Integer nofDaysForPolicy, ViewerPackages viewerPackages);

}
