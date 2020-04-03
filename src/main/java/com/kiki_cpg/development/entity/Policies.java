package com.kiki_cpg.development.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "policies")
public class Policies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PolicyID")
    private Integer policyId;
    @Column(name = "Name")
    private String name;
    @Column(name = "PolicyType")
    private int policyType;
    @Column(name = "Status")
    private int status;
    @Column(name = "ModifiedOn")
    private Date modifiedOn;
    @Column(name = "ModifiedBy")
    private Integer modifiedBy;
    @Column(name = "Description")
    private String description;
    @Column(name = "ValidFrom")
    private Date validFrom;
    @Column(name = "ValidTo")
    private Date validTo;

    @OneToMany(mappedBy = "policies", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PackagePolicies>packagePolicies = new ArrayList<>();
    
    @OneToMany(mappedBy = "policy", targetEntity = PaymentPolicies.class)
    private List<PaymentPolicies>paymentPolicies = new ArrayList<>();

    public Policies() {
    }

    public Integer getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Integer policyId) {
        this.policyId = policyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPolicyType() {
        return policyType;
    }

    public void setPolicyType(int policyType) {
        this.policyType = policyType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Integer getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public List<PackagePolicies> getPackagePolicies() {
        return packagePolicies;
    }

    public void setPackagePolicies(List<PackagePolicies> packagePolicies) {
        this.packagePolicies = packagePolicies;
    }
}
