package org.kiki_cpg_v2.repository;

import java.util.Date;

import org.kiki_cpg_v2.entity.CKOTPEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CKOTPRepository extends JpaRepository<CKOTPEntity, Integer>{


	/**
	 * @param pin
	 * @param date
	 * @param date2
	 * @return
	 */
	CKOTPEntity findOneByCodeAndCreatedDateLessThanEqualAndExpireDateGreaterThanEqual(String pin, Date date,
			Date date2);

}
