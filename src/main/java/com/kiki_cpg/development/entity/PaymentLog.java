package com.kiki_cpg.development.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payment_log")
public class PaymentLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    @Column(name = "viwerId")
    int viwerId;
    @Column(name = "mobileNo")
    String mobileNo;
    @Column(name = "responseNo")
    String responseNo;
    @Column(name = "responseMsg")
    String responseMsg;
    @Column(name = "serviceProvider")
    String serviceProvider;
    @Column(name = "created_date")
    private Date created_date;
    @Column(name = "serverResponse")
    String serverResponse;

    public PaymentLog() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getViwerId() {
        return viwerId;
    }

    public void setViwerId(int viwerId) {
        this.viwerId = viwerId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getResponseNo() {
        return responseNo;
    }

    public void setResponseNo(String responseNo) {
        this.responseNo = responseNo;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public String getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public String getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(String serverResponse) {
        this.serverResponse = serverResponse;
    }
}
