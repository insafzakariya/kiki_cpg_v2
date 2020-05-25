package org.kiki_cpg_v2.repository;

import java.util.Date;

import org.kiki_cpg_v2.entity.SubscriptionPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionPaymentRepository extends JpaRepository<SubscriptionPaymentEntity, Integer>{

	SubscriptionPaymentEntity findOneByTokenHashAndCreatedDateLessThanEqualAndExpireDateGreaterThanEqualAndStatus(
			String token, Date createDate, Date expireDate, Integer status);

}
