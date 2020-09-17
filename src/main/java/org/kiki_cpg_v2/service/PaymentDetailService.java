/**
 * @DaAug 21, 2020 @PaymentDetailService.java
 */
package org.kiki_cpg_v2.service;

import java.util.Date;

import org.kiki_cpg_v2.entity.PaymentDetailsEntity;

/**
 * @author Anjana Thrishakya
 */
public interface PaymentDetailService {

	/**
	 * @param amount
	 * @param days
	 * @param endDate
	 * @param refNo
	 * @param type
	 * @return
	 */
	PaymentDetailsEntity save(Double amount, Integer days, Date endDate, Integer refNo, String type);

}
