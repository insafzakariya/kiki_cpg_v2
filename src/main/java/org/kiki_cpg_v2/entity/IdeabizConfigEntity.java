package org.kiki_cpg_v2.entity;

import javax.persistence.*;

@Entity
@Table(name = "ideabiz_config")
public class IdeabizConfigEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "clientCorrelator")
	private Integer clientCorrelator;

	@Column(name = "amount")
	private Double amount;

	@Column(name = "currency")
	private String currency;

	@Column(name = "description")
	private String description;

	@Column(name = "taxAmount")
	private String taxAmount;

	@Column(name = "serviceID")
	private String serviceID;

	@Column(name = "week_charge")
	private Double weekCharge;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getClientCorrelator() {
		return clientCorrelator;
	}

	public void setClientCorrelator(Integer clientCorrelator) {
		this.clientCorrelator = clientCorrelator;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(String taxAmount) {
		this.taxAmount = taxAmount;
	}

	public String getServiceID() {
		return serviceID;
	}

	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}

	public double getWeekCharge() {
		return weekCharge;
	}

	public void setWeekCharge(double weekCharge) {
		this.weekCharge = weekCharge;
	}

}
