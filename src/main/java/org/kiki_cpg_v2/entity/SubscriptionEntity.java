/**
 * @DaSep 2, 2020 @SubscriptionEntity.java
 */
package org.kiki_cpg_v2.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Anjana Thrishakya
 */
@Entity
@Table(name = "subscription_data")
public class SubscriptionEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "subscribed_days")
	private Integer subscribedDays;

	@Column(name = "amount")
	private Double amount;

	@Column(name = "mobile")
	private String mobile;
	
	@Column(name = "token")
	private String token;

	@Column(name = "type")
	private String type;

	@Column(name = "viewer_id")
	private Integer viewerId;

	@Column(name = "policy_exp_date", columnDefinition = "DATETIME")
	private Date policyExpDate;

	@Column(name = "createDate", columnDefinition = "DATETIME")
	private Date createDate;

	@Column(name = "updateDate", columnDefinition = "DATETIME")
	private Date updateDate;

	@Column(name = "subscribe")
	private Integer subscribe;

	@Column(name = "status")
	private Integer status;

	@Column(name = "payment_plan_id")
	private Integer paymentPlan;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSubscribedDays() {
		return subscribedDays;
	}

	public void setSubscribedDays(Integer subscribedDays) {
		this.subscribedDays = subscribedDays;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getViewerId() {
		return viewerId;
	}

	public void setViewerId(Integer viewerId) {
		this.viewerId = viewerId;
	}

	public Date getPolicyExpDate() {
		return policyExpDate;
	}

	public void setPolicyExpDate(Date policyExpDate) {
		this.policyExpDate = policyExpDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(Integer subscribe) {
		this.subscribe = subscribe;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPaymentPlan() {
		return paymentPlan;
	}

	public void setPaymentPlan(Integer paymentPlan) {
		this.paymentPlan = paymentPlan;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
