/**
 * @DaAug 19, 2020 @PackageConfigRepository.java
 */
package org.kiki_cpg_v2.repository;

import org.kiki_cpg_v2.entity.PackageConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Anjana Thrishakya
 */
public interface PackageConfigRepository extends JpaRepository<PackageConfigEntity, Integer>{

	/**
	 * @param day
	 * @param type
	 * @return
	 */
	PackageConfigEntity findFirstByDaysAndType(Integer day, String type) throws Exception;

}
