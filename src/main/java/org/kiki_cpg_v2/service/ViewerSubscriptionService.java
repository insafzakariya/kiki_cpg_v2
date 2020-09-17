package org.kiki_cpg_v2.service;

import java.util.Date;

import org.kiki_cpg_v2.enums.SubscriptionType;

public interface ViewerSubscriptionService {

	boolean updateViewerSubscription(Integer viewerId, SubscriptionType mobitelAddToBill, Date date, String mobileNo);

	boolean isViewerMatched(Integer viewerId);

	boolean inavtive(Integer viewerId, String mobileNo);

	boolean changeStatus(Integer viewerId, SubscriptionType mobitelAddToBill);

}
