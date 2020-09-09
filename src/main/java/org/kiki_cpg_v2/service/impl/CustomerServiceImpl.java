/**
 * @DaSep 9, 2020 @CustomerServiceImpl.java
 */
package org.kiki_cpg_v2.service.impl;

import java.math.BigInteger;

import org.kiki_cpg_v2.entity.CustomerEntity;
import org.kiki_cpg_v2.enums.DealerSubscriptionType;
import org.kiki_cpg_v2.service.CustomerService;
import org.springframework.stereotype.Service;

/**
 * @author Anjana Thrishakya
 */
@Service
public class CustomerServiceImpl implements CustomerService{

	@Override
	public CustomerEntity getCustomerEntity(Integer dealerId, String mobileNo, Integer viewerId) {
		CustomerEntity entity = new CustomerEntity();
		entity.setDealerId(new BigInteger(dealerId.toString()));
		entity.setMobileNo(mobileNo);
		entity.setStatus(DealerSubscriptionType.activated);
		entity.setViewerId(viewerId);
		
		return entity;
	}

}
