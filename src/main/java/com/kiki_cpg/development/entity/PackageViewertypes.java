package com.kiki_cpg.development.entity;

import java.util.Date;

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
@Table(name = "package_viewertypes")
public class PackageViewertypes {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RID")
	private Integer RID;
	
	@Column(name = "ViewerTypeId")
	private int ViewerTypeId;
	
	@Column(name = "CreatedDate")
	private Date CreatedDate;
	
	@Column(name = "Status")
	private int Status;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PackageID")
    private Packages packages;
	
	public Integer getRID() {
		return RID;
	}

	public void setRID(Integer rID) {
		RID = rID;
	}

	public int getViewerTypeId() {
		return ViewerTypeId;
	}

	public void setViewerTypeId(int viewerTypeId) {
		ViewerTypeId = viewerTypeId;
	}

	public Date getCreatedDate() {
		return CreatedDate;
	}

	public void setCreatedDate(Date createdDate) {
		CreatedDate = createdDate;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public Packages getPackages() {
		return packages;
	}

	public void setPackages(Packages packages) {
		this.packages = packages;
	}
	
	
	
}
