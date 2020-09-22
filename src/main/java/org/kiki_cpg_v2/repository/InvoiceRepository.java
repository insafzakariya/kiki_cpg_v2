package org.kiki_cpg_v2.repository;

import org.kiki_cpg_v2.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Integer> {

	/**
	 * @param active
	 * @return
	 */
	InvoiceEntity findFirstBySuccessOrderByIdDesc(Integer active);

	/**
	 * @param id
	 * @param active
	 * @return
	 */
	InvoiceEntity findFirstByViewerIdAndSuccessOrderByIdDesc(Integer id, Integer active);

	InvoiceEntity findByIdAndSuccess(Integer invoiceId, Integer success);

}
