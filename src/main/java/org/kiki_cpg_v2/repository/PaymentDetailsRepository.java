/**
 * @DaAug 21, 2020 @PaymentDetailsRepository.java
 */
package org.kiki_cpg_v2.repository;

import org.kiki_cpg_v2.entity.PaymentDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Anjana Thrishakya
 */
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetailsEntity, Integer> {

}
