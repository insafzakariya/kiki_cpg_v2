package org.kiki_cpg_v2.entity;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.kiki_cpg_v2.enums.DealerSubscriptionType;

@Entity
@Table(name = "customer")
public class CustomerEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "viwer_id")
	private Integer viewerId;

	@Column(name = "mobile_no")
	private String mobileNo;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private DealerSubscriptionType status;

	@Column(name = "dealer_id")
	private BigInteger dealerId;

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

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public DealerSubscriptionType getStatus() {
		return status;
	}

	public void setStatus(DealerSubscriptionType status) {
		this.status = status;
	}

	public BigInteger getDealerId() {
		return dealerId;
	}

	public void setDealerId(BigInteger dealerId) {
		this.dealerId = dealerId;
	}

}
