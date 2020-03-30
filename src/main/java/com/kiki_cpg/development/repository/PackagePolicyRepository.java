package com.kiki_cpg.development.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kiki_cpg.development.entity.PackagePolicies;
import com.kiki_cpg.development.repository.Custom.PackagePolicyRepositoryCustom;

import java.util.List;

@Repository
public interface PackagePolicyRepository extends JpaRepository<PackagePolicies, Integer>, PackagePolicyRepositoryCustom {
}
