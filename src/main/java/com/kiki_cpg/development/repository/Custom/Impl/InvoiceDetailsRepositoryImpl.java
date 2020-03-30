package com.kiki_cpg.development.repository.Custom.Impl;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.kiki_cpg.development.entity.InvoiceDetails;
import com.kiki_cpg.development.repository.Custom.InvoiceDetailsRepositoryCustom;

import java.util.List;

public class InvoiceDetailsRepositoryImpl implements InvoiceDetailsRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<InvoiceDetails> findByInvoiceId(int invoice_id) {
        Query query=entityManager.createNativeQuery("SELECT * FROM invoice_details WHERE invoice_id=:invoice_id",InvoiceDetails.class);
        query.setParameter("invoice_id",invoice_id);
        return query.getResultList();
    }
}
