package com.kiki_cpg.development.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "viewer_policies")
public class ViewerPolicies implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rec_id")
    private Integer recId;
    @Column(name = "viewer_id")
    private int viewerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_id")
    private Policies policy;

    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @Column(name = "status")
    private int status;
    @Column(name = "last_updated")
    private Date lastUpdated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viewer_package_id")
    private ViewerPackages viewerPackage;

    public ViewerPolicies() {
    }

    public ViewerPolicies(int viewerId, Policies policy, int status) {
        this.viewerId = viewerId;
        this.policy = policy;
        this.status = status;
    }

    public ViewerPolicies(int viewerId, Policies policy, Date startDate, Date endDate, int status, ViewerPackages viewerPackage) {
        this.viewerId = viewerId;
        this.policy = policy;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.viewerPackage = viewerPackage;
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

    public Policies getPolicy() {
        return policy;
    }

    public void setPolicy(Policies policy) {
        this.policy = policy;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    public ViewerPackages getViewerPackage() {
        return viewerPackage;
    }

    public void setViewerPackage(ViewerPackages viewerPackage) {
        this.viewerPackage = viewerPackage;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
