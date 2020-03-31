package com.kiki_cpg.development.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiki_cpg.development.entity.ViewerPackages;
import com.kiki_cpg.development.repository.ViewerPackagesRepository;
import com.kiki_cpg.development.service.ViewerPackagesService;

import javax.transaction.Transactional;
import java.util.Date;

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

	
}
