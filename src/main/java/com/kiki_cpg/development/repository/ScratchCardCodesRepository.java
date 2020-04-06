package com.kiki_cpg.development.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiki_cpg.development.entity.TblScratchCardCodes;
import com.kiki_cpg.development.entity.TblScratchCards;
import com.kiki_cpg.development.repository.Custom.ScratchCardCodesRepositoryCustom;

public interface ScratchCardCodesRepository extends JpaRepository<TblScratchCardCodes,Integer>,ScratchCardCodesRepositoryCustom{

	

}
