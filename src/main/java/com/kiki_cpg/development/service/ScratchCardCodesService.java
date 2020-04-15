package com.kiki_cpg.development.service;

import com.kiki_cpg.development.entity.TblScratchCards;

public interface ScratchCardCodesService {

	TblScratchCards validateCode(String cardCode);

	String setPayment(String cardCode, Integer viewerId);

	//TblScratchCards validateCode(String cardCode);

}
