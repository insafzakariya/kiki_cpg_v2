package com.kiki_cpg.development.repository.Custom;

import org.springframework.stereotype.Repository;

import com.kiki_cpg.development.entity.ViewerPolicies;

import java.util.List;

@Repository
public interface ViewerRepositoryCustom {
    List<ViewerPolicies> getFilteredViewerPoliciesForCurPackage(int viewerID);
}
