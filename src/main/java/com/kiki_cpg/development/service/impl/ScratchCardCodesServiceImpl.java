package com.kiki_cpg.development.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiki_cpg.development.entity.TblScratchCardCodes;
import com.kiki_cpg.development.entity.TblScratchCards;
import com.kiki_cpg.development.repository.ScratchCardCodesRepository;
import com.kiki_cpg.development.service.ScratchCardCodesService;

@Service
public class ScratchCardCodesServiceImpl implements ScratchCardCodesService {

	@Autowired
	ScratchCardCodesRepository scratchCardCodeRepo;

	@Override
	public TblScratchCards validateCode(String cardCode) {
		List<TblScratchCardCodes> tblScratchCardCode = null;
		TblScratchCardCodes tblScratchCardCodes = null;
		TblScratchCards tblScratchCard = null;
		TblScratchCardCodes updatetblScratchCardCode = null;
		try {
			tblScratchCardCode = scratchCardCodeRepo.getTblScratchCards(cardCode);
			tblScratchCardCodes = tblScratchCardCode.get(0);
			updatetblScratchCardCode = tblScratchCardCodes;

			if (tblScratchCardCodes != null) {
				if (tblScratchCardCodes.getTblScratchCards() != null) {
					tblScratchCard = tblScratchCardCodes.getTblScratchCards();
					if (tblScratchCard.getCardType() == 2) {
						updatetblScratchCardCode.setCardStatus(2);
						scratchCardCodeRepo.save(updatetblScratchCardCode);
					}
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			return tblScratchCard;
		}

	}

}
