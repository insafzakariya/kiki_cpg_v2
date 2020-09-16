package org.kiki_cpg_v2.repository;

import org.kiki_cpg_v2.entity.MerchantAccountEntity;
import org.kiki_cpg_v2.repository.custom.MerchantAccountRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MerchantAccountRepository extends JpaRepository<MerchantAccountEntity, Integer>, MerchantAccountRepositoryCustom{

	/**
	 * @param b
	 * @return
	 */
	MerchantAccountEntity findFirstByIsSuccessOrderByIdDesc(boolean b);

	/**
	 * @param id
	 * @param b
	 * @return
	 */
	MerchantAccountEntity findFirstByViewerIdAndIsSuccessOrderByIdDesc(Integer id, boolean b);

}
