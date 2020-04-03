package com.kiki_cpg.development.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiki_cpg.development.entity.ViewerTrialPeriodAssociation;

public interface ViewerTrialPeriodAssociationRepository extends JpaRepository<ViewerTrialPeriodAssociation, Integer> {

	ViewerTrialPeriodAssociation findOneByViewerId(Integer viewerID);

}
