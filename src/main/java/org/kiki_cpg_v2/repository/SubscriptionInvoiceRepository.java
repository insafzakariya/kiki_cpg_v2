/**
 * @DaSep 2, 2020 @SubscriptionInvoiceRepository.java
 */
package org.kiki_cpg_v2.repository;

import org.kiki_cpg_v2.entity.SubscriptionInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Anjana Thrishakya
 */
public interface SubscriptionInvoiceRepository extends JpaRepository<SubscriptionInvoiceEntity, Integer>{

	/**
	 * @param id
	 * @param hutch
	 * @param active
	 * @param inactive
	 * @return
	 */
	SubscriptionInvoiceEntity findByViewerIdAndTypeAndStatusAndSuccess(Integer id, String hutch, Integer active,
			Integer inactive);

	/**
	 * @param id
	 * @param active
	 * @param active2
	 * @param hutch
	 * @return
	 */
	SubscriptionInvoiceEntity findFirstByViewerIdAndStatusAndSuccessAndType(Integer id, Integer active, Integer active2,
			String hutch);

}
