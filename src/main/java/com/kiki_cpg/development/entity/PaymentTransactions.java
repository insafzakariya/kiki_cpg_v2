package com.kiki_cpg.development.entity;

import java.util.Date;

/**
 * PaymentTransactions generated by hbm2java
 */
public class PaymentTransactions implements java.io.Serializable {

    private Integer tid;
    private int subscriptionPaymentId;
    private int paymentMethodId;
    private String transactionId;
    private Date createdDate;
    private Date lastUpdatedDate;
    private int status;
    private String ipgresponse;
    private String ipgerror;
    private String token;

    public PaymentTransactions() {
    }

    public PaymentTransactions(int subscriptionPaymentId, int paymentMethodId, String transactionId, int status, String ipgresponse, String ipgerror, String token) {
        this.subscriptionPaymentId = subscriptionPaymentId;
        this.paymentMethodId = paymentMethodId;
        this.transactionId = transactionId;
        this.status = status;
        this.ipgresponse = ipgresponse;
        this.ipgerror = ipgerror;
        this.createdDate = new Date();
        this.lastUpdatedDate = new Date();
        this.token = token;
    }

    public PaymentTransactions(int subscriptionPaymentId, int paymentMethodId, String transactionId, Date createdDate, Date lastUpdatedDate, int status, String ipgresponse, String ipgerror) {
        this.subscriptionPaymentId = subscriptionPaymentId;
        this.paymentMethodId = paymentMethodId;
        this.transactionId = transactionId;
        this.createdDate = createdDate;
        this.lastUpdatedDate = lastUpdatedDate;
        this.status = status;
        this.ipgresponse = ipgresponse;
        this.ipgerror = ipgerror;
    }

    public Integer getTid() {
        return this.tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public int getSubscriptionPaymentId() {
        return this.subscriptionPaymentId;
    }

    public void setSubscriptionPaymentId(int subscriptionPaymentId) {
        this.subscriptionPaymentId = subscriptionPaymentId;
    }

    public int getPaymentMethodId() {
        return this.paymentMethodId;
    }

    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastUpdatedDate() {
        return this.lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getIpgresponse() {
        return this.ipgresponse;
    }

    public void setIpgresponse(String ipgresponse) {
        this.ipgresponse = ipgresponse;
    }

    public String getIpgerror() {
        return this.ipgerror;
    }

    public void setIpgerror(String ipgerror) {
        this.ipgerror = ipgerror;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
