package com.kiki_cpg.development.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

import com.kiki_cpg.development.entity.TblScratchCards;

public interface ScratchCardCodesService {

	TblScratchCards validateCode(String cardCode);

	String setPayment(String cardCode, ModelMap model, HttpServletRequest request);

	//TblScratchCards validateCode(String cardCode);

}
