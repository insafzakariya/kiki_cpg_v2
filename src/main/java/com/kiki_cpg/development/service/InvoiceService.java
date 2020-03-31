package com.kiki_cpg.development.service;

import com.kiki_cpg.development.entity.Viewers;

public interface InvoiceService {
	
	public Integer create(String serviceId, Viewers viewers, Integer subscribed_days, Double amount);
	
	public void updateInvoice(Integer invoice_id, Integer status);
	
	public void updatePolicyExpireIdeaBiz(Integer invoice_id, Integer viwer_id);

}
