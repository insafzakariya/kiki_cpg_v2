/**
 * @DaAug 18, 2020 @IdeabizServiceConfigServiceImpl.java
 */
package org.kiki_cpg_v2.service.impl;

import org.kiki_cpg_v2.entity.IdeabizServiceConfigEntity;
import org.kiki_cpg_v2.repository.IdeabizServiceConfigRepository;
import org.kiki_cpg_v2.service.IdeabizServiceConfigService;
import org.kiki_cpg_v2.util.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Anjana Thrishakya
 */
@Service
public class IdeabizServiceConfigServiceImpl implements IdeabizServiceConfigService{

	@Autowired
	private IdeabizServiceConfigRepository ideabizServiceConfigRepository;
	@Override
	public String getServiceId(Integer subscribedDays) throws Exception {
		IdeabizServiceConfigEntity entity = ideabizServiceConfigRepository.findFirstByDaysAndStatus(subscribedDays, AppConstant.ACTIVE);
		if(entity != null) {
			return entity.getServiceId();
		};
		
		return null;
	}

}
