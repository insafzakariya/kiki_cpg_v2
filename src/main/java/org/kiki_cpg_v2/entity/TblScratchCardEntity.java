package org.kiki_cpg_v2.entity;

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
public class TblScratchCardEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CardID")
	private Integer id;

	@Column(name = "PackageID")
	private Integer packageID;

	@Column(name = "CardType")
	private Integer cardType;

	@Column(name = "ActivityStartDate")
	private Date activityStartDate;

	@Column(name = "ActivityEndDate")
	private Date activityEndDate;

	@Column(name = "Status")
	private Integer status;

	@OneToMany(mappedBy = "tblScratchCardEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TblScratchCardCodeEntity> tblScratchCardCodeEntities = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPackageID() {
		return packageID;
	}

	public void setPackageID(Integer packageID) {
		this.packageID = packageID;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public Date getActivityStartDate() {
		return activityStartDate;
	}

	public void setActivityStartDate(Date activityStartDate) {
		this.activityStartDate = activityStartDate;
	}

	public Date getActivityEndDate() {
		return activityEndDate;
	}

	public void setActivityEndDate(Date activityEndDate) {
		this.activityEndDate = activityEndDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<TblScratchCardCodeEntity> getTblScratchCardCodeEntities() {
		return tblScratchCardCodeEntities;
	}

	public void setTblScratchCardCodeEntities(List<TblScratchCardCodeEntity> tblScratchCardCodeEntities) {
		this.tblScratchCardCodeEntities = tblScratchCardCodeEntities;
	}

}
