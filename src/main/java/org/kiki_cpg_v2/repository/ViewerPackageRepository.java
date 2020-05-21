package org.kiki_cpg_v2.repository;

import java.util.List;

import org.kiki_cpg_v2.entity.ViewerPackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewerPackageRepository extends JpaRepository<ViewerPackageEntity, Integer> {

	ViewerPackageEntity findFirstByViewerIdAndStatus(Integer viewerId, Integer status);

	List<ViewerPackageEntity> findByViewerIdAndStatus(Integer viewerId, Integer active);

}
