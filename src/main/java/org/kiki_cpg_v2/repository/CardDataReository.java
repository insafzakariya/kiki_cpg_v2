/**
 * @DaAug 6, 2020 @CardDataReository.java
 */
package org.kiki_cpg_v2.repository;

import java.util.Date;
import java.util.List;

import org.kiki_cpg_v2.entity.CardDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Anjana Thrishakya
 */
public interface CardDataReository extends JpaRepository<CardDataEntity, Integer> {


	/**
	 * @param viewerID
	 * @param active
	 * @return
	 */
	CardDataEntity findOneByViewerIdAndStatus(Integer viewerID, Integer active);

	/**
	 * @param viewerID
	 * @param active
	 * @param active2
	 * @return
	 */
	CardDataEntity findOneByViewerIdAndStatusAndSubscribeOrderByIdDesc(Integer viewerID, Integer active, Integer active2);

	/**
	 * @param viewerId
	 * @param active
	 * @return
	 */
	CardDataEntity findFirstByViewerIdAndStatusOrderByIdDesc(Integer viewerId, Integer active);

	/**
	 * @param viewerid
	 * @param active
	 * @param active2
	 * @return
	 */
	List<CardDataEntity> findByViewerIdAndStatusAndSubscribe(Integer viewerid, Integer active, Integer active2);

	/**
	 * @param active
	 * @param active2
	 * @param date
	 * @return
	 */
	List<CardDataEntity> findByStatusAndSubscribeAndPolicyExpDateLessThanEqual(Integer active, Integer active2,
			Date date);

}
