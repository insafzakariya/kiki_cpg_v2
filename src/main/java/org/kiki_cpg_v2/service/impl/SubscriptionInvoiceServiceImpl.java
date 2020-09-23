package org.kiki_cpg_v2.service.impl;

import org.kiki_cpg_v2.entity.SubscriptionInvoiceEntity;
import org.kiki_cpg_v2.repository.SubscriptionInvoiceRepository;
import org.kiki_cpg_v2.service.SubscriptionInvoiceService;
import org.kiki_cpg_v2.util.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionInvoiceServiceImpl implements SubscriptionInvoiceService {

	@Autowired
	SubscriptionInvoiceRepository subscriptionInvoiceRepository;

	@Override
	public SubscriptionInvoiceEntity getSubscriptionInvoiceById(Integer invoiceId, String accept, String type) {
		return subscriptionInvoiceRepository.findByIdAndTypeAndDecision(invoiceId, accept, type);
	}

}
