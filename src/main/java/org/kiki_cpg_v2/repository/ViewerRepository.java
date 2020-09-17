package org.kiki_cpg_v2.repository;

import java.util.List;

import org.kiki_cpg_v2.entity.ViewerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewerRepository extends JpaRepository<ViewerEntity, Integer> {

	List<ViewerEntity> findByIdNotAndMobileNumberEndingWith(Integer viewerId, String mobileNo);

	/**
	 * @param string
	 * @return
	 */
	ViewerEntity findFirstByMobileNumberEndingWithOrderByIdDesc(String string) throws Exception;

}
