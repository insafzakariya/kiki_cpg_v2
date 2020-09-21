/**
 * @DaJul 29, 2020 @CardInvoiceRepository.java
 */
package org.kiki_cpg_v2.repository;

import org.kiki_cpg_v2.entity.CardInvoiceEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Anjana Thrishakya
 */
public interface CardInvoiceRepository extends CrudRepository<CardInvoiceEntity, Integer> {

	/**
	 * @param first
	 * @return
	 */
	CardInvoiceEntity findFirstByTransactionNo(String first);

	/**
	 * @param referanceNo
	 * @return
	 */
	CardInvoiceEntity findFirstByReferanceNo(String referanceNo);

	CardInvoiceEntity findByIdAndDecision(Integer invoiceId, String accept);

}
