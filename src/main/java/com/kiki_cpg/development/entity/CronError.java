package com.kiki_cpg.development.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cron_error")
public class CronError {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "errorDate")
	private Date errorDate;

	@Column(name = "viewerId")
	private Integer viewerId;

	@Column(name = "errorDesc", length = 5000)
	private String errorDesc;
	
	@Column(name = "errorMsg", length = 10000)
	private String errorMsg;

	@Column(name = "systemPage")
	private String systemPage;

	@Column(name = "cronId")
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
