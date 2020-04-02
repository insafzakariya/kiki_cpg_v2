package com.kiki_cpg.development.repository.Custom.Impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.kiki_cpg.development.entity.Packages;
import com.kiki_cpg.development.entity.Policies;
import com.kiki_cpg.development.entity.ViewerPackages;
import com.kiki_cpg.development.entity.ViewerPolicies;
import com.kiki_cpg.development.repository.PackagePolicyRepository;
import com.kiki_cpg.development.repository.PackageRepository;
import com.kiki_cpg.development.repository.PolicyRepository;
import com.kiki_cpg.development.repository.SubscriptionRepository;
import com.kiki_cpg.development.repository.ViewerPackagesRepository;
import com.kiki_cpg.development.repository.ViewerPoliciesRepository;
import com.kiki_cpg.development.repository.ViewerRepository;
import com.kiki_cpg.development.repository.Custom.ContentRepositoryCustom;
import com.kiki_cpg.development.service.OTPService;
import com.kiki_cpg.development.service.ViewerPackagesService;
import com.kiki_cpg.development.service.ViewerPoliciesService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ContentRepositoryImpl implements ContentRepositoryCustom {

	private static final Logger logger = LoggerFactory.getLogger(ContentRepositoryImpl.class);

	@PersistenceContext
	EntityManager entityManager;
	@Autowired
	ViewerRepository viewerRepository;
	@Autowired
	SubscriptionRepository subscriptionRepository;
	@Autowired
	PackageRepository packageRepository;

	@Autowired
	PolicyRepository policyRepository;

	@Autowired
	ViewerPackagesRepository viewerPackagesRepository;

	@Autowired
	ViewerPoliciesRepository viewerPoliciesRepository;

	@Autowired
	ViewerPoliciesService viewerPoliciesService;

	@Autowired
	ViewerPackagesService viewerPackagesService;

	@Autowired
	PackagePolicyRepository packagePolicyRepository;

	
	@Autowired
	OTPService otpService;

	@Override
	public void updateViewerPolicies(int viewerID, int newPackageID, boolean isAddingRemainingDays) {
		try {
		System.out.println("Viewer Id : " + viewerID);
		List<ViewerPackages> viewerPackagesList = new ArrayList<>();
		ViewerPackages vp = new ViewerPackages();
		// query changed

		logger.info("Current Package==" + vp.getPackageID());
		List<Packages> packagesList = packageRepository.getPackageById(newPackageID);
		// List<Packages> packagesList=null;
		Packages packages = packagesList.get(0);

		viewerPackagesList = viewerPackagesRepository.getViewerPackages(viewerID);
		System.out.println("query check");
		vp = viewerPackagesList.get(0);
		System.out.println("Package Id : " + vp.getPackageID());

		if (vp.getPackageID() == newPackageID) {
			// want to write String query
			List<Policies> policies = policyRepository.getPoliciesByPackageID(newPackageID);
			int nofDaysForPolicy = 0;
			nofDaysForPolicy = packages.getAvailableDays();

			// want to write String query
			List<ViewerPolicies> viewerPolicies = viewerRepository.getFilteredViewerPoliciesForCurPackage(viewerID);

			if (!viewerPolicies.isEmpty()) {
				// Extract date
				Iterator<ViewerPolicies> vplistIterator = viewerPolicies.iterator();

				ViewerPolicies vpp = vplistIterator.next();

				logger.info("Current Latest Date" + vpp.getEndDate());

				// Calclate addtional days = Extracted date - Today
				Date date2 = vpp.getEndDate();
				Date date1 = new Date();
				long diff = date2.getTime() - date1.getTime();

				if (diff > 0 && isAddingRemainingDays) {
					// Has more days before expire
					int diffDays = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
					logger.info("Days: " + diffDays);
					nofDaysForPolicy = nofDaysForPolicy + diffDays;
				}
			}

			// Add Policies >> DATE
			if (viewerPoliciesService.addViewerPolicies(policies, viewerID, nofDaysForPolicy, vp)) {
				logger.info("Successfully Added the polices");
			} else {
				logger.info("Could not Add the polices");
			}

		} else {
			// **New package, asign from today >> LIKE @Path("/promocode/{card_code}")

			// Remove current

			if (viewerPackagesRepository.updateViewerPackage(viewerID)) {

				// / Add new viewer package

				ViewerPackages savedViewerPackage = viewerPackagesService.addViewerPackage(newPackageID, viewerID);

				if (savedViewerPackage.getRowId() != null && savedViewerPackage.getRowId().intValue() > 0) {

					// Add viewer policies [not removing existing policies]

					List packagePolicyList = packagePolicyRepository.getPoliciesByPackageID(newPackageID);

					if (viewerPoliciesService.addViewerPolicies(packagePolicyList, viewerID,
							packages.getAvailableDays(), savedViewerPackage)) {
						logger.info("Successfully Added the polices");

					} else {

						logger.info("Could not add viewer policy");
					}

				} else {
					logger.info("Could not add package");
				}

			} else {
				logger.info("Could not remove existing package");

			}
		}
		}catch(Exception e) {
			e.printStackTrace();
		}

	}
}
