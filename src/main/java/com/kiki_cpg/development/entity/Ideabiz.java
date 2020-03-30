package com.kiki_cpg.development.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ideabiz")
public class Ideabiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "viwer_id")
    private Integer viwer_id;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "createdDate")
    private Date createdDate;

    @Column(name = "subscribe")
    private Integer subscribe;

    @Column(name = "subscribed_days")
    private Integer subscribed_days;

    @Column(name = "last_policy_updated_at")
    private Date last_policy_updated_at;

    @Column(name = "policy_expire_at")
    private Date policy_expire_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getViwer_id() {
        return viwer_id;
    }

    public void setViwer_id(Integer viwer_id) {
        this.viwer_id = viwer_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }

    public Integer getSubscribed_days() {
        return subscribed_days;
    }

    public void setSubscribed_days(Integer subscribed_days) {
        this.subscribed_days = subscribed_days;
    }

    public Date getLast_policy_updated_at() {
        return last_policy_updated_at;
    }

    public void setLast_policy_updated_at(Date last_policy_updated_at) {
        this.last_policy_updated_at = last_policy_updated_at;
    }

    public Date getPolicy_expire_at() {
        return policy_expire_at;
    }

    public void setPolicy_expire_at(Date policy_expire_at) {
        this.policy_expire_at = policy_expire_at;
    }
}
