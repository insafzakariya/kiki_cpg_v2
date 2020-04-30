package com.kiki_cpg.development.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiki_cpg.development.entity.Packages;
import com.kiki_cpg.development.entity.Policies;
import com.kiki_cpg.development.entity.TblScratchCardCodes;
import com.kiki_cpg.development.entity.TblScratchCards;
import com.kiki_cpg.development.entity.ViewerPackages;
import com.kiki_cpg.development.entity.ViewerPolicies;
import com.kiki_cpg.development.repository.ScratchCardCodesRepository;
import com.kiki_cpg.development.service.PackagesService;
import com.kiki_cpg.development.service.PolicyService;
import com.kiki_cpg.development.service.ScratchCardCodesService;
import com.kiki_cpg.development.service.SubscriptionPaymentService;
import com.kiki_cpg.development.service.ViewerCodesService;
import com.kiki_cpg.development.service.ViewerPackagesService;
import com.kiki_cpg.development.service.ViewerPoliciesService;
import com.kiki_cpg.development.service.ViewerService;

@Service
public class ScratchCardCodesServiceImpl implements ScratchCardCodesService {

	@Autowired
	ScratchCardCodesRepository scratchCardCodeRepo;

	@Autowired
	SubscriptionPaymentService subscriptionPayService;

	@Autowired
	ViewerCodesService viewerCodesService;

	@Autowired
	ViewerPackagesService viewerPackagesService;

	@Autowired
	PackagesService packagesService;

	@Autowired
	PolicyService policyService;

	@Autowired
	ViewerPoliciesService viewerPoliciesService;

	@Autowired
	ViewerService viewerService;

	private static final Logger logger = LoggerFactory.getLogger(ScratchCardCodesServiceImpl.class);

	@Override
	public TblScratchCards validateCode(String cardCode) {
		TblScratchCards tblScratchCard = null;
		TblScratchCardCodes updatetblScratchCardCode = null;
		try {
			updatetblScratchCardCode = scratchCardCodeRepo.getTblScratchCards(cardCode).get(0);

			if (updatetblScratchCardCode != null) {
				if (updatetblScratchCardCode.getTblScratchCards() != null) {
					tblScratchCard = updatetblScratchCardCode.getTblScratchCards();
					if (tblScratchCard.getCardType() == 2) {
						updatetblScratchCardCode.setCardStatus(2);
						scratchCardCodeRepo.save(updatetblScratchCardCode);

						return tblScratchCard;
					}
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public String setPayment(String cardCode, Integer viewerID) {

		if (logger.isInfoEnabled()) {
			logger.info("Validate and Use Promo" + viewerID);
		}

		TblScratchCards tblScratchCard = null;
		TblScratchCardCodes tblScratchCardCode = null;

		try {
			tblScratchCard = validateCode(cardCode);

		} catch (NumberFormatException nf) {
			if (logger.isInfoEnabled()) {
				logger.info("Could not parse card code");
			}
		}

		if (tblScratchCard != null) {
			Iterator listIterator = tblScratchCard.getTblScratchCardCodes().iterator();
			tblScratchCardCode = (TblScratchCardCodes) listIterator.next();
			if (!viewerCodesService.isAlreadyUsedViewerScratchCardCode(viewerID,
					tblScratchCardCode.getRecordId().intValue()) || tblScratchCard.getCardType() == 2) {

				viewerCodesService.addViewerCode(viewerID, tblScratchCardCode.getRecordId());
				ViewerPackages viewerPackages = viewerPackagesService.getPackageById(viewerID);
				List<Packages> packagesList = packagesService.getPackageById(tblScratchCard.getPackageID());

				if (packagesList != null && !packagesList.isEmpty()) {

					Packages vPackage = (Packages) packagesList.get(0);
					int newPackageID = vPackage.getPackageId();
					List<Policies> policiesList = policyService.getPoliciesByPackageIDAndValidDate(newPackageID);

					if (viewerPackages.getPackageID() == newPackageID) {

						if (logger.isInfoEnabled()) {
							logger.info("Payment for existing package");
						}

						List<ViewerPolicies> viewerPolicies = viewerPoliciesService
								.getFilteredViewerPoliciesForCurPackage(viewerID);
						int nofDaysForPolicy = vPackage.getAvailableDays();

						if (!viewerPolicies.isEmpty()) {
							Iterator vplistIterator = viewerPolicies.iterator();
							ViewerPolicies vpp = (ViewerPolicies) vplistIterator.next();

							// Calclate addtional days = Extracted date - Today
							Date date2 = vpp.getEndDate();
							Date date1 = new Date();
							long diff = date2.getTime() - date1.getTime();

							if (diff > 0) {
								// IF addtional days > 0 => New days to function = addtional days +
								// vPackage.getAvailableDays()
								// Has more days before expire
								int diffDays = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
								nofDaysForPolicy = nofDaysForPolicy + diffDays;
							}

						}
						if (viewerPoliciesService.addViewerPolicies(policiesList, viewerID, nofDaysForPolicy,
								viewerPackagesService.getPackageById(viewerID))) {
							if (logger.isInfoEnabled()) {
								logger.info("Successfully Added the polices");
							}
						} else {

							if (logger.isInfoEnabled()) {
								logger.info("Could not Add the polices");
							}
							return "Could not update the package";
						}
					} else {
						if (logger.isInfoEnabled()) {
							logger.info("New Package payment");
						}
						if (viewerPackagesService.updateViewerPackage(viewerID)) {
							ViewerPackages savedViewerPackage = viewerPackagesService.addViewerPackage(newPackageID,
									viewerID);

							if (savedViewerPackage.getRowId() != null && savedViewerPackage.getRowId().intValue() > 0) {

								if (viewerPoliciesService.addViewerPolicies(policiesList, viewerID,
										vPackage.getAvailableDays(), savedViewerPackage)) {
								} else {

									if (logger.isInfoEnabled()) {
										logger.info("Could not add viewer policy");
									}
									return "Could not update the package";
								}

							} else {
								if (logger.isInfoEnabled()) {
									logger.info("Could not add package");
								}
								return "Could not update the package";
							}
						} else {
							if (logger.isInfoEnabled()) {
								logger.info("Could not remove existing package");
							}
							return "Could not update the package";
						}
					}
				} else {
					if (logger.isInfoEnabled()) {
						logger.info("Could not get package");
					}
					return "Could not update the package";
				}

			} else {
				if (logger.isInfoEnabled()) {
					logger.info("User already used same code");
				}
				return "Already used code";
			}
		} else {
			if (logger.isInfoEnabled()) {
				logger.info("Invalid  code");
			}
			return "Invalid card code";
		}
		return "Success";
	}

}
