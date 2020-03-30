package com.kiki_cpg.development.repository.Custom;


import java.util.List;

import com.kiki_cpg.development.entity.ViewerPackages;

public interface ViewerPackagesRepositoryCustom {
    List<ViewerPackages> getViewerPackages(int viewerID);

    boolean updateViewerPackage(int viewerID);
}
