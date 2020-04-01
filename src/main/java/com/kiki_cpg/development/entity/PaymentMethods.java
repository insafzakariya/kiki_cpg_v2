package com.kiki_cpg.development.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "payment_methods")
public class PaymentMethods implements java.io.Serializable {

	@Id
	@Column(name = "PaymentMethodID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer paymentMethodId;
	@Column(name = "MethodName")
	private String methodName;
	@Column(name = "Status")
	private Integer status;
	@Column(name = "ModifiedBy")
	private Integer modifiedBy;
	@Column(name = "ModifiedOn")
	private Date modifiedOn;
	
	@OneToMany(mappedBy = "paymentMethod", targetEntity = PaymentPolicies.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<PaymentPolicies> paymentPolicies;

	public PaymentMethods() {
	}

	public PaymentMethods(String methodName, int status) {
		this.methodName = methodName;
		this.status = status;
	}

	public PaymentMethods(String methodName, Integer status, Integer modifiedBy, Date modifiedOn,
			PaymentPolicies paymentPolicies) {
		this.methodName = methodName;
		this.status = status;
		this.modifiedBy = modifiedBy;
		this.modifiedOn = modifiedOn;
	}

	public Integer getPaymentMethodId() {
		return this.paymentMethodId;
	}

	public void setPaymentMethodId(Integer paymentMethodId) {
		this.paymentMethodId = paymentMethodId;
	}

	public String getMethodName() {
		return this.methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedOn() {
		return this.modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public List<PaymentPolicies> getPaymentPolicies() {
		return paymentPolicies;
	}

	public void setPaymentPolicies(List<PaymentPolicies> paymentPolicies) {
		this.paymentPolicies = paymentPolicies;
	}

}
