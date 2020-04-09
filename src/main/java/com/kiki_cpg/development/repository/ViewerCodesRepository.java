package com.kiki_cpg.development.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiki_cpg.development.entity.Config;
import com.kiki_cpg.development.entity.ViewerCodes;

public interface ViewerCodesRepository extends JpaRepository<ViewerCodes,Integer> {

	ViewerCodes findByViewerIdAndScratchCardId(int viewerID, int intValue);

}
