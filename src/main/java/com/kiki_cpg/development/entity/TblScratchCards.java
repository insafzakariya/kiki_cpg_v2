package com.kiki_cpg.development.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_scratch_cards")
public class TblScratchCards {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CardID")
	private Integer CardID;

	@Column(name = "PackageID")
	private Integer PackageID;

	@Column(name = "CardType")
	private int CardType;

	@Column(name = "ActivityStartDate")
	private Date ActivityStartDate;

	@Column(name = "ActivityEndDate")
	private Date ActivityEndDate;

	@Column(name = "Status")
	private int Status;

	@OneToMany(mappedBy = "tblScratchCards", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TblScratchCardCodes> tblScratchCardCodes = new ArrayList<>();

	public Integer getCardID() {
		return CardID;
	}

	public void setCardID(Integer cardID) {
		CardID = cardID;
	}

	public Integer getPackageID() {
		return PackageID;
	}

	public void setPackageID(Integer packageID) {
		PackageID = packageID;
	}

	public int getCardType() {
		return CardType;
	}

	public void setCardType(int cardType) {
		CardType = cardType;
	}

	public Date getActivityStartDate() {
		return ActivityStartDate;
	}

	public void setActivityStartDate(Date activityStartDate) {
		ActivityStartDate = activityStartDate;
	}

	public Date getActivityEndDate() {
		return ActivityEndDate;
	}

	public void setActivityEndDate(Date activityEndDate) {
		ActivityEndDate = activityEndDate;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public List<TblScratchCardCodes> getTblScratchCardCodes() {
		return tblScratchCardCodes;
	}

	public void setTblScratchCardCodes(List<TblScratchCardCodes> tblScratchCardCodes) {
		this.tblScratchCardCodes = tblScratchCardCodes;
	}

}
