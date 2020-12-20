/**
 * @DaSep 2, 2020 @SubscriptionRepository.java
 */
package org.kiki_cpg_v2.repository;

import java.util.Date;
import java.util.List;

import org.kiki_cpg_v2.entity.SubscriptionEntity;
import org.kiki_cpg_v2.entity.SubscriptionInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

	/**
	 * @param id
	 * @param active
	 * @param active2
	 * @param type
	 * @return
	 */
	List<SubscriptionEntity> findByViewerIdAndStatusAndSubscribeAndType(Integer id, Integer active, Integer active2,
			String type);

	/**
	 * @param active
	 * @param active2
	 * @param date
	 * @param mobitel
	 * @return
	 */
	List<SubscriptionEntity> findBySubscribeAndStatusAndPolicyExpDateLessThanEqualAndType(Integer active,
			Integer active2, Date date, String mobitel);

	/**
	 * @param mobileNo
	 * @param active
	 * @param active2
	 * @param hutch
	 * @return
	 */
	SubscriptionEntity findFirstByMobileContainingAndStatusAndSubscribeAndType(String mobileNo, Integer status, Integer subscribe,
			String hutch);

	/**
	 * @param viewerId
	 * @param type
	 * @return
	 */
	List<SubscriptionEntity> findByViewerIdAndType(Integer viewerId, String type) throws Exception;

	/**
	 * @param mobileNo
	 * @param type
	 * @param active
	 * @return
	 */
	List<SubscriptionEntity> findByMobileContainingAndTypeAndStatus(String mobileNo, String type, Integer active);


}
