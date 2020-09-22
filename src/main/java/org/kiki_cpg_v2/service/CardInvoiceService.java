package org.kiki_cpg_v2.service;

import org.kiki_cpg_v2.entity.CardInvoiceEntity;

public interface CardInvoiceService {
	CardInvoiceEntity getInvoiceByIdAndDicision(Integer invoiceId, String Accept);
}
