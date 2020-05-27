package org.kiki_cpg_v2.service;

import org.kiki_cpg_v2.dto.ScratchCardPaymentDto;
import org.kiki_cpg_v2.entity.TblScratchCardCodeEntity;

public interface ScratchCardService {

	String setPayment(ScratchCardPaymentDto scratchCardPaymentDto) throws Exception;

	TblScratchCardCodeEntity getValiedCardCodeEntity(String code) throws Exception;

}
