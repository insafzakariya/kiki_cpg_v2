/**
 * @DaAug 18, 2020 @IdeabizServiceConfigRepository.java
 */
package org.kiki_cpg_v2.repository;

import org.kiki_cpg_v2.entity.IdeabizServiceConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Anjana Thrishakya
 */
public interface IdeabizServiceConfigRepository extends JpaRepository<IdeabizServiceConfigEntity, Integer>{

	/**
	 * @param subscribedDays
	 * @param active
	 * @return
	 */
	IdeabizServiceConfigEntity findFirstByDaysAndStatus(Integer subscribedDays, Integer active);

}
