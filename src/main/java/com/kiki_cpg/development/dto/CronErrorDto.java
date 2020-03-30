package com.kiki_cpg.development.dto;

import java.util.Date;

import javax.persistence.Column;

public class CronErrorDto {
	private Integer id;
	private Date errorDate;
	private Integer viewerId;
	private String errorDesc;
	private String errorMsg;
	private String systemPage;
	private Integer cronId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getErrorDate() {
		return errorDate;
	}
	public void setErrorDate(Date errorDate) {
		this.errorDate = errorDate;
	}
	public Integer getViewerId() {
		return viewerId;
	}
	public void setViewerId(Integer viewerId) {
		this.viewerId = viewerId;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getSystemPage() {
		return systemPage;
	}
	public void setSystemPage(String systemPage) {
		this.systemPage = systemPage;
	}
	public Integer getCronId() {
		return cronId;
	}
	public void setCronId(Integer cronId) {
		this.cronId = cronId;
	}
	

}
