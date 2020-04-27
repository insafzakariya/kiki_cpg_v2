package com.kiki_cpg.development.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiki_cpg.development.entity.Packages;
import com.kiki_cpg.development.entity.Policies;
import com.kiki_cpg.development.entity.ViewerPackages;
import com.kiki_cpg.development.entity.ViewerPolicies;
import com.kiki_cpg.development.repository.PackageRepository;
import com.kiki_cpg.development.repository.PolicyRepository;
import com.kiki_cpg.development.repository.ViewerPackagesRepository;
import com.kiki_cpg.development.repository.ViewerRepository;
import com.kiki_cpg.development.service.ContentService;
import com.kiki_cpg.development.service.ViewerPoliciesService;

@Service
public class ContentServiceImpl implements ContentService{
	
	@Autowired
	private PackageRepository packageRepository;
	
	@Autowired
	ViewerPackagesRepository viewerPackagesRepository;
	
	@Autowired
	private PolicyRepository policyRepository;
	
	@Autowired
	private ViewerRepository viewerRepository;

	@Autowired
	private ViewerPoliciesService viewerPoliciesService;

	@Override
	public boolean updateViewerPolicies(Integer viewerId, Integer packageId, boolean isAddingRemainingDays)
			throws Exception {
		List<ViewerPackages> viewerPackagesList = viewerPackagesRepository.getViewerPackages(viewerId);
		ViewerPackages vp = viewerPackagesList.get(0);
		
		List<Packages> packagesList = packageRepository.findByPackageIdAndActivityStartDateLessThanEqualAndActivityEndDateGreaterThanEqual(packageId, new Date(), new Date());
		// List<Packages> packagesList=null;
		Packages packages = packagesList.get(0);
		
		if (vp.getPackageID() == packageId) {
			List<Policies> policies = policyRepository.getPoliciesByPackageID(packageId);
			
			Integer nofDaysForPolicy = packages.getAvailableDays();
			
			List<ViewerPolicies> viewerPolicies = viewerRepository.getFilteredViewerPoliciesForCurPackage(viewerId);
			
			if (!viewerPolicies.isEmpty()) {
				Iterator<ViewerPolicies> vplistIterator = viewerPolicies.iterator();

				ViewerPolicies vpp = vplistIterator.next();
				

				// Calclate addtional days = Extracted date - Today
				Date date2 = vpp.getEndDate();
				Date date1 = new Date();
				long diff = date2.getTime() - date1.getTime();

				if (diff > 0 && isAddingRemainingDays) {
					// Has more days before expire
					int diffDays = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
					nofDaysForPolicy = nofDaysForPolicy + diffDays;
				}
				
			}
			
			if (viewerPoliciesService.addViewerPolicies(policies, viewerId, nofDaysForPolicy, vp)) {
				return true;
			} else {
				return false;
			}
			
		}
		return false;
	}

}
