package org.kiki_cpg_v2.service;

public interface ViewerUnsubscriptionService {


	boolean save(String mobileNo, Integer viewerId, String suscriptionType, String serviceProvider);

}
