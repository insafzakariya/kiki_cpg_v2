package org.kiki_cpg_v2.service;

public interface ViewerUnsubscriptionService {

	boolean unubscribe(String mobileNo, Integer viewerId, String suscriptionType, String serviceProvider) throws Exception;

	boolean save(String mobileNo, Integer viewerId, String suscriptionType, String serviceProvider,
			boolean updateIfExist) throws Exception;

}
