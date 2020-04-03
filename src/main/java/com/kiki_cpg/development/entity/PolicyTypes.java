package com.kiki_cpg.development.entity;

import java.util.Date;

public class PolicyTypes implements java.io.Serializable {

    private Integer policyTypeId;
    private String name;
    private int status;
    private Integer modifiedBy;
    private Date modifiedOn;

    public PolicyTypes() {
    }

    public PolicyTypes(String name, int status) {
        this.name = name;
        this.status = status;
    }

    public PolicyTypes(String name, int status, Integer modifiedBy, Date modifiedOn) {
        this.name = name;
        this.status = status;
        this.modifiedBy = modifiedBy;
        this.modifiedOn = modifiedOn;
    }

    public Integer getPolicyTypeId() {
        return this.policyTypeId;
    }

    public void setPolicyTypeId(Integer policyTypeId) {
        this.policyTypeId = policyTypeId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getModifiedBy() {
        return this.modifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedOn() {
        return this.modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

}
