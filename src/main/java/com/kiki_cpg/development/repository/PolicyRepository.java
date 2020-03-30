package com.kiki_cpg.development.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kiki_cpg.development.entity.Policies;
import com.kiki_cpg.development.repository.Custom.PolicyRepositoryCustom;

@Repository
public interface PolicyRepository extends JpaRepository<Policies, Integer>, PolicyRepositoryCustom {

}
