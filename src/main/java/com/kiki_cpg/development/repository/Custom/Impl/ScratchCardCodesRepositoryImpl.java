package com.kiki_cpg.development.repository.Custom.Impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.kiki_cpg.development.entity.Packages;
import com.kiki_cpg.development.entity.TblScratchCardCodes;
import com.kiki_cpg.development.entity.TblScratchCards;
import com.kiki_cpg.development.repository.Custom.ScratchCardCodesRepositoryCustom;

@Repository
public class ScratchCardCodesRepositoryImpl implements ScratchCardCodesRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<TblScratchCardCodes> getTblScratchCards(String cardCode) {
		try {
			Query query = entityManager.createNativeQuery(
					"SELECT tscc.* FROM susila_db.tbl_scratch_card_codes tscc , susila_db.tbl_scratch_cards tsc where tscc.CardID=tsc.CardID and tscc.CardCode=:cardCode \r\n"
							+ "and tsc.ActivityStartDate<=:todayDate and tsc.ActivityEndDate>=:todayDate and tsc.Status=1 and tscc.CardStatus=1",
					TblScratchCardCodes.class);
			//
			query.setParameter("cardCode", cardCode);
			query.setParameter("todayDate", new Date());
			return query.getResultList();
			
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}
