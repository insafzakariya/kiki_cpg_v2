package com.kiki_cpg.development.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "package_policies")
public class PackagePolicies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RowID")
    private Integer rowId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PolicyID")
    private Policies policies;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PackageID")
    private Packages packages;

    @Column(name = "ModifiedOn")
    private Date modifiedOn;

    @Column(name = "ModifiedBy")
    private Integer modifiedBy;

    public PackagePolicies() {
    }

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

    public Packages getPackages() {
        return packages;
    }

    public void setPackages(Packages packages) {
        this.packages = packages;
    }

    public Policies getPolicies() {
        return policies;
    }

    public void setPolicies(Policies policies) {
        this.policies = policies;
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

}
