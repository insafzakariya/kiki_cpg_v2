package com.kiki_cpg.development.repository.Custom;

import java.util.List;

import com.kiki_cpg.development.entity.TblScratchCardCodes;
import com.kiki_cpg.development.entity.TblScratchCards;

public interface ScratchCardCodesRepositoryCustom {
	
	List<TblScratchCardCodes> getTblScratchCards(String cardCode);

}
