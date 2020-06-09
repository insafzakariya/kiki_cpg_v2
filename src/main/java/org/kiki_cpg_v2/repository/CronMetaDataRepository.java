package org.kiki_cpg_v2.repository;

import org.kiki_cpg_v2.entity.CronMetaDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CronMetaDataRepository extends JpaRepository<CronMetaDataEntity, Integer>{

	CronMetaDataEntity findOneByCronNameAndStatusAndCronStatus(String dialog, Integer active, Integer active2);

}
