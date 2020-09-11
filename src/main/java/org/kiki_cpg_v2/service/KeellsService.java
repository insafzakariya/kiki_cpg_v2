/**
 * @DaSep 9, 2020 @KeellsService.java
 */
package org.kiki_cpg_v2.service;

import org.kiki_cpg_v2.dto.PaymentRefDto;
import org.kiki_cpg_v2.dto.request.PaymentRequestDto;
import org.kiki_cpg_v2.dto.request.TransactionBeginDto;
import org.springframework.http.ResponseEntity;

/**
 * @author Anjana Thrishakya
 */
public interface KeellsService {

	/**
	 * @param transactionBeginDto
	 * @return
	 * @throws Exception 
	 */
	PaymentRefDto beginTransaction(TransactionBeginDto transactionBeginDto) throws Exception;
	
	/**
	 * @param paymentRequestDto
	 * @return
	 * @throws Exception 
	 */
	ResponseEntity<Object> pay(PaymentRequestDto paymentRequestDto) throws Exception;

}
