package com.kiki_cpg.development.repository.Custom.Impl;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.kiki_cpg.development.entity.InvoiceDetails;
import com.kiki_cpg.development.repository.Custom.InvoiceRepositoryCustom;

import java.util.List;

public class InvoiceRepositoryImpl implements InvoiceRepositoryCustom {

    @PersistenceContext
    EntityManager entityManage;

    @Override
    public List<InvoiceDetails> findByInvoiceId(int invoice_id) {
        Query query=entityManage.createNativeQuery("SELECT i.* FROM invoice_details i WHERE i.invoice_id=:invoice_id",InvoiceDetails.class);
        query.setParameter("invoice_id",invoice_id);
        return query.getResultList();
    }
}
