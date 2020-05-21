package org.kiki_cpg_v2.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "viewer_policies")
public class ViewerPolicyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rec_id")
	private Integer id;

	@Column(name = "viewer_id")
	private Integer viewerId;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "status")
	private int status;

	@Column(name = "last_updated")
	private Date lastUpdated;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "policy_id")
	private PolicyEntity policyEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "viewer_package_id")
	private ViewerPackageEntity viewerPackageEntity;

	public PolicyEntity getPolicyEntity() {
		return policyEntity;
	}

	public void setPolicyEntity(PolicyEntity policyEntity) {
		this.policyEntity = policyEntity;
	}

	public ViewerPackageEntity getViewerPackageEntity() {
		return viewerPackageEntity;
	}

	public void setViewerPackageEntity(ViewerPackageEntity viewerPackageEntity) {
		this.viewerPackageEntity = viewerPackageEntity;
	}

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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}



}
