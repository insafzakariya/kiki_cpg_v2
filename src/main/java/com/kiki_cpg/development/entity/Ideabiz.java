package com.kiki_cpg.development.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ideabiz")
public class Ideabiz {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "viwer_id")
	private Integer viwerId;

	@Column(name = "mobile")
	private String mobile;

	@Column(name = "createdDate")
	private Date createdDate;

	@Column(name = "subscribe")
	private Integer subscribe;

	@Column(name = "subscribed_days")
	private Integer subscribedDays;

	@Column(name = "last_policy_updated_at")
	private Date lastPolicyUpdatedAt;

	@Column(name = "policy_expire_at")
	private Date policyExpireAt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getViwerId() {
		return viwerId;
	}

	public void setViwerId(Integer viwerId) {
		this.viwerId = viwerId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(Integer subscribe) {
		this.subscribe = subscribe;
	}

	public Integer getSubscribedDays() {
		return subscribedDays;
	}

	public void setSubscribedDays(Integer subscribedDays) {
		this.subscribedDays = subscribedDays;
	}

	public Date getLastPolicyUpdatedAt() {
		return lastPolicyUpdatedAt;
	}

	public void setLastPolicyUpdatedAt(Date lastPolicyUpdatedAt) {
		this.lastPolicyUpdatedAt = lastPolicyUpdatedAt;
	}

	public Date getPolicyExpireAt() {
		return policyExpireAt;
	}

	public void setPolicyExpireAt(Date policyExpireAt) {
		this.policyExpireAt = policyExpireAt;
	}

}
