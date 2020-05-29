package org.kiki_cpg_v2.service;

import java.util.Date;

import org.kiki_cpg_v2.enums.SubscriptionType;

public interface MobitelService {

	boolean processUnsubscriptionMobitel(Integer viewerid, String mobile);

	boolean updateViewerSubscription(Integer viewerid, SubscriptionType none, Date date, String mobile);

}
