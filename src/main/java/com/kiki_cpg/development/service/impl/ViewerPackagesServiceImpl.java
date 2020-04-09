package com.kiki_cpg.development.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiki_cpg.development.entity.ViewerPackages;
import com.kiki_cpg.development.repository.ViewerPackagesRepository;
import com.kiki_cpg.development.service.ViewerPackagesService;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ViewerPackagesServiceImpl implements ViewerPackagesService {

	@Autowired
	ViewerPackagesRepository viewerPackagesRepository;

	@Override
	public ViewerPackages addViewerPackage(Integer newPackageID, Integer viewerID) {
		// boolean actionStatus = false;

		ViewerPackages viewerPackage = new ViewerPackages();
		viewerPackage.setModifiedOn(new Date());
//        Packages packages = new Packages();
//        packages.setPackageId(newPackageID);
//        viewerPackage.setPackages(packages);
		viewerPackage.setPackageID(newPackageID);
		viewerPackage.setStatus(1);
		viewerPackage.setViewerId(viewerID);

		return viewerPackagesRepository.save(viewerPackage);
	}

	@Override
	public ViewerPackages getPackageById(int viewerID) {
		// TODO Auto-generated method stub
		List<ViewerPackages> viewerPackages = viewerPackagesRepository.getViewerPackages(viewerID);
		if (viewerPackages != null) {
			ViewerPackages viewerPackage = viewerPackages.get(0);
			return viewerPackage;
		}
		return null;
	}

	@Override
	public boolean updateViewerPackage(int viewerID) {
		// TODO Auto-generated method stub
		ViewerPackages viewerPackage =viewerPackagesRepository.findByViewerId(viewerID);
		viewerPackage.setStatus(0);
		viewerPackage.setModifiedOn(new Date());
		viewerPackagesRepository.save(viewerPackage);
		return true;
	}

	
}
