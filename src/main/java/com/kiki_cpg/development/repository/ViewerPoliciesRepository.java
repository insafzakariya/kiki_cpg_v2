package com.kiki_cpg.development.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiki_cpg.development.entity.ViewerPolicies;
import com.kiki_cpg.development.repository.Custom.ViewerPoliciesRepositoryCustom;

public interface ViewerPoliciesRepository extends JpaRepository<ViewerPolicies,Integer>,ViewerPoliciesRepositoryCustom {

	

	

}
