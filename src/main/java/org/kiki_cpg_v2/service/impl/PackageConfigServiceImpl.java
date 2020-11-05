/**
 * @DaAug 19, 2020 @PackageConfigServiceImpl.java
 */
package org.kiki_cpg_v2.service.impl;

import org.kiki_cpg_v2.entity.PackageConfigEntity;
import org.kiki_cpg_v2.entity.PackageEntity;
import org.kiki_cpg_v2.repository.PackageConfigRepository;
import org.kiki_cpg_v2.service.PackageConfigService;
import org.kiki_cpg_v2.util.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Anjana Thrishakya
 */
@Service
@Transactional
public class PackageConfigServiceImpl implements PackageConfigService {

	@Autowired
	private PackageConfigRepository packageConfigRepository;

	@Override
	public Integer getPackageId(Integer day, String type) throws Exception {
		PackageConfigEntity entity = packageConfigRepository.findFirstByDaysAndType(day, type);
		if (entity != null) {
			return entity.getPackageId();
		}
		return -1;
	}

	@Override
	public PackageConfigEntity getFreeTrialPackageId(Integer subscribedDays, String hutch) throws Exception {
		PackageConfigEntity entity = packageConfigRepository.findFirstByType(AppConstant.TRIAL);
		if (entity != null) {
			return entity;
		}
		return null;
	}

	@Override
	public PackageConfigEntity getPackage(Integer days, String apple) throws Exception {
		PackageConfigEntity entity = packageConfigRepository.findFirstByTypeAndDays(apple, days);
		if (entity != null) {
			return entity;
		}
		return null;
	}
}
