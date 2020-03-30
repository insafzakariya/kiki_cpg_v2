package com.kiki_cpg.development.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mobitel_registration")
public class MobitelRegistration {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "registrationId")
	private Integer registrationId;

	@Column(name = "ViewerID")
	private int ViewerID;

	@Column(name = "PackageID")
	private int PackageID;

	@Column(name = "CreatedDate")
	private Date CreatedDate;

	@Column(name = "expireDate")
	private Date expireDate;

	@Column(name = "regCode")
	private String regCode;

	@Column(name = "status")
	private int status;

	@Column(name = "MobileNo")
	private String MobileNo;

	public Integer getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(Integer registrationId) {
		this.registrationId = registrationId;
	}

	public int getViewerID() {
		return ViewerID;
	}

	public void setViewerID(int viewerID) {
		ViewerID = viewerID;
	}

	public int getPackageID() {
		return PackageID;
	}

	public void setPackageID(int packageID) {
		PackageID = packageID;
	}

	public Date getCreatedDate() {
		return CreatedDate;
	}

	public void setCreatedDate(Date createdDate) {
		CreatedDate = createdDate;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public String getRegCode() {
		return regCode;
	}

	public void setRegCode(String regCode) {
		this.regCode = regCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMobileNo() {
		return MobileNo;
	}

	public void setMobileNo(String mobileNo) {
		MobileNo = mobileNo;
	}

	

}
