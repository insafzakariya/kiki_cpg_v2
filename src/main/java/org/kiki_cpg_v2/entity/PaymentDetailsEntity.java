/**
 * @DaAug 21, 2020 @PaymentDetailsEntity.java
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
@Table(name = "payment_details")
public class PaymentDetailsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Double amount;
	private Integer days;
	private String type;
	private Integer refNo;
	@Column(columnDefinition = "DATETIME")
	private Date startDate;
	@Column(columnDefinition = "DATETIME")
	private Date endDate;
	@Column(columnDefinition = "DATETIME")
	private Date createDate;
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getRefNo() {
		return refNo;
	}

	public void setRefNo(Integer refNo) {
		this.refNo = refNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
