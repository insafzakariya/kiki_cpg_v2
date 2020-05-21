package org.kiki_cpg_v2.entity;

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
public class TblScratchCardCodeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RecordID")
	private Integer id;

	@Column(name = "CardCode")
	private String cardCode;

	@Column(name = "CardStatus")
	private Integer cardStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CardID")
	private TblScratchCardEntity tblScratchCardEntity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public Integer getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(Integer cardStatus) {
		this.cardStatus = cardStatus;
	}

	public TblScratchCardEntity getTblScratchCardEntity() {
		return tblScratchCardEntity;
	}

	public void setTblScratchCardEntity(TblScratchCardEntity tblScratchCardEntity) {
		this.tblScratchCardEntity = tblScratchCardEntity;
	}

}
