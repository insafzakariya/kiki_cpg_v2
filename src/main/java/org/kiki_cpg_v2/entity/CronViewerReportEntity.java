package org.kiki_cpg_v2.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cron_viewer_report")
public class CronViewerReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "viewerId")
    private Integer viewerId;

    @Column(name = "status")
    private String status;

    @Column(name = "responseMsg")
    private String responseMsg;

    @Column(name = "serverResponse",length = 5000)
    private String serverResponse;

    @Column(name = "chargeAmount")
    private Double chargeAmount;

    @Column(name = "responseDateAndTime")
    private Date responseDateAndTime;

    @Column(name = "cronId")
    private Integer cronId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getCronId() {
        return cronId;
    }

    public void setCronId(Integer cronId) {
        this.cronId = cronId;
    }
}
