package org.kiki_cpg_v2.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "viewer_unsubcription")
public class ViewerUnsubcriptionEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "lastUpdatedTime")
	private Date lastUpdatedTime;
	
	@Column(name = "createdDateTime")
	private Date createdDateTime;
	
	@Column(name = "deletedDateTime")
	private Date deletedDateTime;
	
	@Column(name = "mobileNumber")
	private String mobileNumber;
	
	@Column(name = "viewerId")
	private Integer viewerId;
	
	@Column(name = "subcriptionType")
	private String subcriptionType;
	
	@Column(name = "serviceProvider")
	private String serviceProvider;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(Date lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public Date getDeletedDateTime() {
		return deletedDateTime;
	}

	public void setDeletedDateTime(Date deletedDateTime) {
		this.deletedDateTime = deletedDateTime;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Integer getViewerId() {
		return viewerId;
	}

	public void setViewerId(Integer viewerId) {
		this.viewerId = viewerId;
	}

	public String getSubcriptionType() {
		return subcriptionType;
	}

	public void setSubcriptionType(String subcriptionType) {
		this.subcriptionType = subcriptionType;
	}

	public String getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(String serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

}
