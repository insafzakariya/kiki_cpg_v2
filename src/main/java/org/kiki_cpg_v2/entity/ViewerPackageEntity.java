package org.kiki_cpg_v2.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "viewer_packages")
public class ViewerPackageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RowID")
    private Integer id;
    
    @Column(name = "ViewerID")
    private Integer viewerId;
    
    @Column(name = "Status")
    private Integer status;
    
    @Column(name = "ModifiedOn")
    private Date modifiedOn;
    
    @Column(name = "PackageID")
    private Integer packageId;

    @OneToMany(mappedBy = "viewerPackageEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ViewerPolicyEntity> viewerPolicyEntities = new ArrayList<>();

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

	public Integer getPackageId() {
		return packageId;
	}

	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}
   
	public List<ViewerPolicyEntity> getViewerPolicyEntities() {
		return viewerPolicyEntities;
	}

	public void setViewerPolicyEntities(List<ViewerPolicyEntity> viewerPolicyEntities) {
		this.viewerPolicyEntities = viewerPolicyEntities;
	}

	
}
