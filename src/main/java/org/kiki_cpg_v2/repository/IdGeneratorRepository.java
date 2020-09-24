/**
 * @DaSep 23, 2020 @IdGeneratorRepository.java
 */
package org.kiki_cpg_v2.repository;

import org.kiki_cpg_v2.entity.IdGeneratorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Anjana Thrishakya
 */
public interface IdGeneratorRepository extends JpaRepository<IdGeneratorEntity, Integer>{

	/**
	 * @param type
	 * @param active
	 * @return
	 */
	IdGeneratorEntity findFirstByTypeAndStatus(String type, Integer active);

}
