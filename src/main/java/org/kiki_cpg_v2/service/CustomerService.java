/**
 * @DaSep 9, 2020 @CustomerService.java
 */
package org.kiki_cpg_v2.service;

import org.kiki_cpg_v2.entity.CustomerEntity;

/**
 * @author Anjana Thrishakya
 */
public interface CustomerService {


	/**
	 * @param dealerId
	 * @param mobileNo
	 * @param viewerId
	 * @return
	 */
	CustomerEntity getCustomerEntity(Integer dealerId, String mobileNo, Integer viewerId);

}
