/**
 * @DaAug 19, 2020 @PackageConfigService.java
 */
package org.kiki_cpg_v2.service;

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

}
