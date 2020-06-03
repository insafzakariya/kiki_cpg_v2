package org.kiki_cpg_v2.repository;

import java.util.Date;
import java.util.List;

import org.kiki_cpg_v2.entity.ViewerPolicyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewerPolicyRepository extends JpaRepository<ViewerPolicyEntity, Integer> {

	List<ViewerPolicyEntity> findByViewerIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndStatus(Integer viewerId,
			Date date, Date date2, Integer status);

	List<ViewerPolicyEntity> findByViewerIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Integer viewerId,
			Date curDate, Date curDate2);

}
