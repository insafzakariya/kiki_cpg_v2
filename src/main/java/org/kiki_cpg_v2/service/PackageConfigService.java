/**
 * @DaAug 19, 2020 @PackageConfigService.java
 */
package org.kiki_cpg_v2.service;

import org.kiki_cpg_v2.entity.PackageConfigEntity;
import org.kiki_cpg_v2.entity.PackageEntity;

/**
 * @author Anjana Thrishakya
 */
public interface PackageConfigService {

	/**
	 * @param day
	 * @param dialog
	 * @return
	 */
	Integer getPackageId(Integer day, String type) throws Exception;

	/**
	 * @param subscribedDays
	 * @param hutch
	 * @return
	 * @throws Exception 
	 */
	PackageConfigEntity getFreeTrialPackageId(Integer subscribedDays, String trial) throws Exception;
	
	/**
	 * @param days
	 * @param apple
	 * @return
	 */
	PackageConfigEntity getPackage(Integer days, String apple) throws Exception;

}
