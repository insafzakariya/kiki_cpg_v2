package com.kiki_cpg.development.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "viewer_packages")
public class ViewerPackages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RowID")
    private Integer rowId;
    @Column(name = "ViewerID")
    private Integer viewerId;
    @Column(name = "Status")
    private Integer status;
    @Column(name = "ModifiedOn")
    private Date modifiedOn;
    @Column(name = "PackageID")
    private Integer packageID;

    @OneToMany(mappedBy = "viewerPackage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ViewerPolicies> viewerPolicies = new ArrayList<>();

    public ViewerPackages() {
    }

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

    public Integer getViewerId() {
        return viewerId;
    }

    public void setViewerId(Integer viewerId) {
        this.viewerId = viewerId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Integer getPackageID() {
        return packageID;
    }

    public void setPackageID(Integer packageID) {
        this.packageID = packageID;
    }

    public List<ViewerPolicies> getViewerPolicies() {
        return viewerPolicies;
    }

    public void setViewerPolicies(List<ViewerPolicies> viewerPolicies) {
        this.viewerPolicies = viewerPolicies;
    }
}
