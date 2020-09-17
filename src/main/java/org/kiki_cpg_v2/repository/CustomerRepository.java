package org.kiki_cpg_v2.repository;

import org.kiki_cpg_v2.entity.CustomerEntity;
import org.kiki_cpg_v2.enums.DealerSubscriptionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer>{

	CustomerEntity findOneByViewerIdAndMobileNoAndStatus(Integer viewerID, String mobileNumber,
			DealerSubscriptionType activated);

}
