/**
 * @DaAug 18, 2020 @IdeabizServiceConfigService.java
 */
package org.kiki_cpg_v2.service;

/**
 * @author Anjana Thrishakya
 */
public interface IdeabizServiceConfigService {

	/**
	 * @param subscribedDays
	 * @return
	 */
	String getServiceId(Integer subscribedDays) throws Exception;

}
