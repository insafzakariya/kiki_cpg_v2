package com.kiki_cpg.development.repository;


import com.kiki_cpg.development.entity.ViewerPolicies;
import com.kiki_cpg.development.repository.Custom.ContentRepositoryCustom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepository extends JpaRepository<ViewerPolicies, Integer> , ContentRepositoryCustom {
}
