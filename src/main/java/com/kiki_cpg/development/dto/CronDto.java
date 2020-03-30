package com.kiki_cpg.development.dto;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CronDto {
    private Integer cronId;
    private String cronType;
    private String startTime;
    private Integer cronViewerReportid;
    private Integer viewerId;
    private String status;
    private String responseMsg;
    private String serverResponse;
    private Double chargeAmount;
    private Date responseDateAndTime;
    private Integer cronReportId;
    private String startedDate;
    private String serverIp;
    private Integer svedCronId;


    public Integer getCronId() {
        return cronId;
    }

    public void setCronId(Integer cronId) {
        this.cronId = cronId;
    }

    public String getCronType() {
        return cronType;
    }

    public void setCronType(String cronType) {
        this.cronType = cronType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Integer getCronViewerReportid() {
        return cronViewerReportid;
    }

    public void setCronViewerReportid(Integer cronViewerReportid) {
        this.cronViewerReportid = cronViewerReportid;
    }

    public Integer getViewerId() {
        return viewerId;
    }

    public void setViewerId(Integer viewerId) {
        this.viewerId = viewerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public String getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(String serverResponse) {
        this.serverResponse = serverResponse;
    }

    public Double getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(Double chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public Date getResponseDateAndTime() {
        return responseDateAndTime;
    }

    public void setResponseDateAndTime(Date responseDateAndTime) {
        this.responseDateAndTime = responseDateAndTime;
    }

    public Integer getCronReportId() {
        return cronReportId;
    }

    public void setCronReportId(Integer cronReportId) {
        this.cronReportId = cronReportId;
    }

    public String getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(String startedDate) {
        this.startedDate = startedDate;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

	public Integer getSvedCronId() {
		return svedCronId;
	}

	public void setSvedCronId(Integer svedCronId) {
		this.svedCronId = svedCronId;
	}
    
}
