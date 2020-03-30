package com.kiki_cpg.development.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "invoice_details")
public class InvoiceDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "invoice_id")
    private Integer invoice_id;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "createdDate")
    private Date createdDate;

    @Column(name = "ValiedDate")
    private Date ValiedDate;

    public InvoiceDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Date getValiedDate() {
        return ValiedDate;
    }

    public void setValiedDate(Date valiedDate) {
        ValiedDate = valiedDate;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getInvoice_id() {
        return invoice_id;
    }
    public void setInvoice_id(Integer invoice_id) {
        this.invoice_id = invoice_id;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public Date getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

}
