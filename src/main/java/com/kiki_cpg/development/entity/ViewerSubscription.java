package com.kiki_cpg.development.entity;

import javax.persistence.*;

import com.kiki_cpg.development.enums.SubscriptionType;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="viewer_subscription")
public class ViewerSubscription implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "SubscriptionType")
    @Enumerated(value = EnumType.STRING)
    private SubscriptionType subscriptionType;

    @Column(name = "Date")
    private Date date;

    @Column(name = "Viewer", unique = true)
    private Integer viewers;

    public ViewerSubscription() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getViewers() {
        return viewers;
    }

    public void setViewers(Integer viewers) {
        this.viewers = viewers;
    }
}
