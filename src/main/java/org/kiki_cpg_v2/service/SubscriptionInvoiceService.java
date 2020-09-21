package org.kiki_cpg_v2.service;

import org.kiki_cpg_v2.entity.SubscriptionInvoiceEntity;

public interface SubscriptionInvoiceService {
	SubscriptionInvoiceEntity getSubscriptionInvoiceById(Integer invoiceId, String accept, String type);
}
