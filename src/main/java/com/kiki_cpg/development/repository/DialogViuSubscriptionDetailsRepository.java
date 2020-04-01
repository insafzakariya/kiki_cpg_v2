package com.kiki_cpg.development.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiki_cpg.development.entity.DialogViuSubscriptionDetails;

public interface DialogViuSubscriptionDetailsRepository extends JpaRepository<DialogViuSubscriptionDetails, Integer>{

	DialogViuSubscriptionDetails findOneByViewerId(Integer viewerId);

}
