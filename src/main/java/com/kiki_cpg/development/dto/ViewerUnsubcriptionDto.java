package com.kiki_cpg.development.dto;

import java.io.Serializable;
import java.util.Date;

public class ViewerUnsubcriptionDto implements Serializable {

	private Integer id;
	private Date lastUpdatedTime;
	private Date createdDateTime;
	private Date deletedDateTime;
	private String mobileNumber;
    private Integer viewerId;
	private String subcriptionType;
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
