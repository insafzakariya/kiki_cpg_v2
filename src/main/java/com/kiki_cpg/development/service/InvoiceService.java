package com.kiki_cpg.development.service;

import com.kiki_cpg.development.entity.Viewers;

public interface InvoiceService {
	
	public Integer create(String serviceId, Viewers viewers, Integer subscribedDay, Double amount);
	
	public boolean updateInvoice(Integer invoiceId, Integer status);
	
	public boolean updatePolicyExpireIdeaBiz(Integer invoiceId, Integer viwerId);
	
	public int proceedPayment(Viewers viewers, Integer subscribedDays, String serviceId,
			Double amount);

}
