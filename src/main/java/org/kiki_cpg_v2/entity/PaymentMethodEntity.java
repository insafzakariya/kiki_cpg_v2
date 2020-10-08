package org.kiki_cpg_v2.entity;

import java.util.Date;
import java.util.List;

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
public class PaymentMethodEntity {

	@Id
	@Column(name = "PaymentMethodID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "MethodName")
	private String methodName;

	@Column(name = "Status")
	private Integer status;

	@Column(name = "ModifiedBy")
	private Integer modifiedBy;

	@Column(name = "ModifiedOn")
	private Date modifiedOn;

	@Column(name = "Description")
	private String description;

	@Column(name = "Image")
	private String image;

	@OneToMany(mappedBy = "paymentMethodEntity", targetEntity = PaymentPolicyEntity.class)
	private List<PaymentPolicyEntity> paymentPolicyEntities;

	@OneToMany(mappedBy = "paymentMethodEntity", targetEntity = PaymentMethodPlanEntity.class, fetch = FetchType.EAGER)
	private List<PaymentMethodPlanEntity> paymentMethodPlanEntities;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<PaymentPolicyEntity> getPaymentPolicyEntities() {
		return paymentPolicyEntities;
	}

	public void setPaymentPolicyEntities(List<PaymentPolicyEntity> paymentPolicyEntities) {
		this.paymentPolicyEntities = paymentPolicyEntities;
	}

	public List<PaymentMethodPlanEntity> getPaymentMethodPlanEntities() {
		return paymentMethodPlanEntities;
	}

	public void setPaymentMethodPlanEntities(List<PaymentMethodPlanEntity> paymentMethodPlanEntities) {
		this.paymentMethodPlanEntities = paymentMethodPlanEntities;
	}

}
