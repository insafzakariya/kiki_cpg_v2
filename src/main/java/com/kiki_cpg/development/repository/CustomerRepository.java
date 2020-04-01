package com.kiki_cpg.development.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiki_cpg.development.entity.Customer;
import com.kiki_cpg.development.enums.DealerSubscriptionType;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	Customer findOneByViwer_idAndMobile_noAndStatus(Integer viewerId, String mobileNumber,
			DealerSubscriptionType activated);

}
