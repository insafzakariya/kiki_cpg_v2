package com.kiki_cpg.development.repository.Custom;


import java.util.List;

import com.kiki_cpg.development.entity.InvoiceDetails;

public interface InvoiceDetailsRepositoryCustom {
    List<InvoiceDetails> findByInvoiceId(int invoice_id);
}
