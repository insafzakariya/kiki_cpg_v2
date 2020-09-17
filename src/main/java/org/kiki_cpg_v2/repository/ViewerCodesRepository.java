package org.kiki_cpg_v2.repository;

import org.kiki_cpg_v2.entity.ViewerCodesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewerCodesRepository extends JpaRepository<ViewerCodesEntity, Integer>{

	ViewerCodesEntity findOneByViewerIdAndScratchCardIdAndStatus(Integer viewerId, Integer scratchCardId, Integer status);

}
