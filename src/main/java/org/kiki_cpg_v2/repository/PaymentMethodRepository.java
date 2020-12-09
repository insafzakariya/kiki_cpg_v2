package org.kiki_cpg_v2.repository;

import java.util.List;

import org.kiki_cpg_v2.entity.PaymentMethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethodEntity, Integer>{

	/**
	 * @param active
	 * @return
	 */
	List<PaymentMethodEntity> findByStatus(Integer active);

}
