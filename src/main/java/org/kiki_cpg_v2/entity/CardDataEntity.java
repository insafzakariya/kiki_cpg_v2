/**
 * @DaAug 6, 2020 @CardDataEntity.java
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
@Table(name = "card_data")
public class CardDataEntity {

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

	@Column(name = "viewer_id")
	private Integer viewerId;

	@Column(name = "payment_token")
	private String paymentToken;

	@Column(name = "signature")
	private String signature;

	@Column(name = "card_type")
	private Integer cardType;

	@Column(name = "card_type_name")
	private String cardTypeName;

	@Column(name = "card_expiry_date")
	private String cardExpiryDate;

	@Column(name = "card_number")
	private String cardNumber;

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

	public Integer getViewerId() {
		return viewerId;
	}

	public void setViewerId(Integer viewerId) {
		this.viewerId = viewerId;
	}

	public String getPaymentToken() {
		return paymentToken;
	}

	public void setPaymentToken(String paymentToken) {
		this.paymentToken = paymentToken;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public String getCardTypeName() {
		return cardTypeName;
	}

	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
	}

	public String getCardExpiryDate() {
		return cardExpiryDate;
	}

	public void setCardExpiryDate(String cardExpiryDate) {
		this.cardExpiryDate = cardExpiryDate;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(Integer subscribe) {
		this.subscribe = subscribe;
	}

}
