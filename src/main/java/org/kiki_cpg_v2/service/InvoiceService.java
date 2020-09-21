package org.kiki_cpg_v2.service;

import java.util.Date;
import java.util.List;

import org.kiki_cpg_v2.entity.InvoiceDetailEntity;
import org.kiki_cpg_v2.entity.InvoiceEntity;

public interface InvoiceService {

	InvoiceEntity getInvoiceEntity(String serviceId, Integer viewerId, String mobileNo, Integer subscribed_days,
			Double amount, Integer status, Date date);

	InvoiceDetailEntity getInvoiceDetailEntity(InvoiceEntity invoiceEntity, Date date, Double day_charge, Date e);

	InvoiceEntity createInvoice(String serviceId, Integer viewerId, Integer subscribed_days, Double amount,
			String mobileNo, Integer status, List<Date> dates);

	InvoiceEntity getInvoiceByIdAndSuccess(Integer invoiceId, Integer success);

	boolean update(InvoiceEntity invoiceEntity);

}
