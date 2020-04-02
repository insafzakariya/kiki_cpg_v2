package com.kiki_cpg.development.service;

import java.util.List;

import com.kiki_cpg.development.entity.ViewerSubscription;
import com.kiki_cpg.development.entity.Viewers;

public interface MobitelWsClientService {

	public void midnightTaskToUpdatePoliciess(String startTime, boolean isSecondTime,
			List<ViewerSubscription> viewerSubscriptionList, List<Viewers> viewersList, Integer cronId)
			throws Exception;

	public boolean isTrialPeriodActivated(Integer viewerId);

	public String createMsisdnForDataBundle(String number);

	public boolean getIsMobitelNumber(String number);

	public String activateDataBundle(String mobileNo, int viewerId, String activationStatus);

	public void testMobitelConnection();

	public void logInToMobitelESMS(String userName, String password);

	public int sendSms(String aliasMsg, String mobileNo, String message);

	public void logOutFromMobitelESMS();
}
