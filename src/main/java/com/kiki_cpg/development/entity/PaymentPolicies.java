package com.kiki_cpg.development.entity;

import java.util.Date;

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
@Table(name = "payment_policies")
public class PaymentPolicies implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PaymentPolicyID")
    private Integer paymentPolicyId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PaymentMethodID")
    private PaymentMethods paymentMethod;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PolicyID")
    private Policies policy;
	
	@Column(name = "Status")
    private int status;
	
	@Column(name = "ModifiedBy")
    private Integer modifiedBy;
	
	@Column(name = "ModifiedOn")
    private Date modifiedOn;
	
	@Column(name = "PaymentAmount")
    private float paymentAmount;
	
	@Column(name = "PaymentType")
    private String paymentType;

    public PaymentPolicies() {
    }

    public PaymentPolicies(PaymentMethods paymentMethod, Policies policy, int status) {
        this.paymentMethod = paymentMethod;
        this.policy = policy;
        this.status = status;
    }

    public PaymentPolicies(PaymentMethods paymentMethod, Policies policy, int status, Integer modifiedBy, Date modifiedOn,float paymentAmount) {
        this.paymentMethod = paymentMethod;
        this.policy = policy;
        this.status = status;
        this.modifiedBy = modifiedBy;
        this.modifiedOn = modifiedOn;
        this.paymentAmount=paymentAmount;
    }

    public Integer getPaymentPolicyId() {
        return this.paymentPolicyId;
    }

    public void setPaymentPolicyId(Integer paymentPolicyId) {
        this.paymentPolicyId = paymentPolicyId;
    }    

    public PaymentMethods getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethods paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public Policies getPolicy() {
        return policy;
    }

    public void setPolicy(Policies policy) {
        this.policy = policy;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
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

    public float getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(float paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
}
