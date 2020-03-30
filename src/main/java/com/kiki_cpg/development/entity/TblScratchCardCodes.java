package com.kiki_cpg.development.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_scratch_card_codes")
public class TblScratchCardCodes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RecordID")
	private Integer recordId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CardID")
	private TblScratchCards tblScratchCards;
	
	@Column(name = "CardCode")
	private String CardCode;
	
	@Column(name = "CardStatus")
	private int CardStatus;

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public TblScratchCards getTblScratchCards() {
		return tblScratchCards;
	}

	public void setTblScratchCards(TblScratchCards tblScratchCards) {
		this.tblScratchCards = tblScratchCards;
	}

	public String getCardCode() {
		return CardCode;
	}

	public void setCardCode(String cardCode) {
		CardCode = cardCode;
	}

	public int getCardStatus() {
		return CardStatus;
	}

	public void setCardStatus(int cardStatus) {
		CardStatus = cardStatus;
	}
	
	
}
