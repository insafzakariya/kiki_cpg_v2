package com.kiki_cpg.development.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "invoice")
public class Invoice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "serviceId")
	private String serviceId;

	@Column(name = "viwer_id")
	private Integer viewerId;

	@Column(name = "subscribed_days")
	private Integer subscribedDays;

	@Column(name = "amount")
	private Double amount;

	@Column(name = "mobile")
	private String mobile;

	@Column(name = "success")
	private Integer success;

	@Column(name = "createdDate")
	private Date createdDate;

	/**
	 *
	 */
	public Invoice() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param serviceId
	 * @param viwer_id
	 * @param subscribed_days
	 * @param amount
	 * @param mobile
	 * @param success
	 * @param createdDate
	 */
	public Invoice(Integer id, String serviceId, Integer viewerId, Integer subscribedDays, Double amount, String mobile,
			Integer success, Date createdDate) {
		super();
		this.id = id;
		this.serviceId = serviceId;
		this.viewerId = viewerId;
		this.subscribedDays = subscribedDays;
		this.amount = amount;
		this.mobile = mobile;
		this.success = success;
		this.createdDate = createdDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
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

	public Integer getSuccess() {
		return success;
	}

	public void setSuccess(Integer success) {
		this.success = success;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getViewerId() {
		return viewerId;
	}

	public void setViewerId(Integer viewerId) {
		this.viewerId = viewerId;
	}

	public Integer getSubscribedDays() {
		return subscribedDays;
	}

	public void setSubscribedDays(Integer subscribedDays) {
		this.subscribedDays = subscribedDays;
	}

}
