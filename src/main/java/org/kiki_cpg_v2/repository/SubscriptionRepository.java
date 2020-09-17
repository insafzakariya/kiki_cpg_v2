/**
 * @DaSep 2, 2020 @SubscriptionRepository.java
 */
package org.kiki_cpg_v2.repository;

import org.kiki_cpg_v2.entity.SubscriptionEntity;
import org.kiki_cpg_v2.entity.SubscriptionInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Anjana Thrishakya
 */
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Integer>{

	/**
	 * @param id
	 * @param active
	 * @param active2
	 * @return
	 */
	SubscriptionEntity findFirstByViewerIdAndStatusAndSubscribe(Integer id, Integer active, Integer active2);

	/**
	 * @param viewerID
	 * @param active
	 * @param active2
	 * @param hutch
	 * @return
	 */
	SubscriptionEntity findFirstByViewerIdAndStatusAndSubscribeAndTypeOrderByIdDesc(Integer viewerID, Integer active,
			Integer active2, String hutch);

	/**
	 * @param viewerid
	 * @param active
	 * @param active2
	 * @param hutch
	 * @return
	 */
	SubscriptionEntity findFirstByViewerIdAndStatusAndSubscribeAndType(Integer viewerid, Integer active,
			Integer active2, String hutch);


}
