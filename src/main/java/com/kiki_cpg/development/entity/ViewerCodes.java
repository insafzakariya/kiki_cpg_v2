package com.kiki_cpg.development.entity;

import java.util.Date;

/**
 * ViewerCodes generated by hbm2java
 */
public class ViewerCodes implements java.io.Serializable {

    private Integer recId;
    private int viewerId;
    private int scratchCardId;
    private Date actionTime;
    private int status;

    public ViewerCodes() {
    }

    public ViewerCodes(int viewerId, int scratchCardId, int status) {
        this.viewerId = viewerId;
        this.scratchCardId = scratchCardId;
        this.status = status;
    }

    public ViewerCodes(int viewerId, int scratchCardId, Date actionTime, int status) {
        this.viewerId = viewerId;
        this.scratchCardId = scratchCardId;
        this.actionTime = actionTime;
        this.status = status;
    }

    public Integer getRecId() {
        return this.recId;
    }

    public void setRecId(Integer recId) {
        this.recId = recId;
    }

    public int getViewerId() {
        return this.viewerId;
    }

    public void setViewerId(int viewerId) {
        this.viewerId = viewerId;
    }

    public int getScratchCardId() {
        return this.scratchCardId;
    }

    public void setScratchCardId(int scratchCardId) {
        this.scratchCardId = scratchCardId;
    }

    public Date getActionTime() {
        return this.actionTime;
    }

    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
