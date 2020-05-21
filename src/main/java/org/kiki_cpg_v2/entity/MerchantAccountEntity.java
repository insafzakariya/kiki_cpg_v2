package org.kiki_cpg_v2.entity;


import javax.persistence.*;

import org.kiki_cpg_v2.enums.TransactionType;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "merchant_account")
public class MerchantAccountEntity implements Serializable {

    private static final long serialVersionUID = -3287739685304933866L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
    
    @Column(name = "ServiceId")
    private String serviceId;
    
    @Column(name = "Date")
    private Date date;
    
    @Column(name = "Amount")
    private Double amount; // amount in Rs
    
    @Column(name = "ViewerId")
    private Integer viewerId;
    
    @Column(name = "IsSuccess")
    private boolean isSuccess;
    
    @Column(name = "TransactionId")
    private Integer transactionId;
    
    @Column(name = "TransactionType")
    private TransactionType transactionType;

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getViewerId() {
        return viewerId;
    }

    public void setViewerId(Integer viewerId) {
        this.viewerId = viewerId;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
