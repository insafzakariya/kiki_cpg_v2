package com.kiki_cpg.development.service;

import com.kiki_cpg.development.entity.ViewerPackages;

public interface ViewerPackagesService {
	
	public ViewerPackages addViewerPackage(Integer newPackageID, Integer viewerID);

	public ViewerPackages getPackageById(int viewerID);

	public boolean updateViewerPackage(int viewerID);

}
