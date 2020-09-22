package org.kiki_cpg_v2.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "payment_plan")
public class PaymentMethodPlanEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "offer")
	private String offer;

	@Column(name = "value")
	private Double value;

	@Column(name = "days")
	private Integer days;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "payment_method_id", nullable = false)
	private PaymentMethodEntity paymentMethodEntity;

	private String serviceCode;
	
	private String trialText;
	
	private Integer trialDays;

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOffer() {
		return offer;
	}

	public void setOffer(String offer) {
		this.offer = offer;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public PaymentMethodEntity getPaymentMethodEntity() {
		return paymentMethodEntity;
	}

	public void setPaymentMethodEntity(PaymentMethodEntity paymentMethodEntity) {
		this.paymentMethodEntity = paymentMethodEntity;
	}

	public String getTrialText() {
		return trialText;
	}

	public void setTrialText(String trialText) {
		this.trialText = trialText;
	}

	public Integer getTrialDays() {
		return trialDays;
	}

	public void setTrialDays(Integer trialDays) {
		this.trialDays = trialDays;
	}

}
