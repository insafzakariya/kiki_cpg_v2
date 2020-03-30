package com.kiki_cpg.development.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.kiki_cpg.development.entity.ViewerTrialPeriodAssociation;
import com.kiki_cpg.development.repository.Custom.TrialPeriodRepositoryCustom;

public interface TrialPeriodRepository extends JpaRepository<ViewerTrialPeriodAssociation,Integer>, TrialPeriodRepositoryCustom {
}
