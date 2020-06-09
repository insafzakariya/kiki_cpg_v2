package org.kiki_cpg_v2.service.impl;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kiki_cpg_v2.entity.InvoiceDetailEntity;
import org.kiki_cpg_v2.entity.InvoiceEntity;
import org.kiki_cpg_v2.repository.InvoiceDetailRepository;
import org.kiki_cpg_v2.repository.InvoiceRepository;
import org.kiki_cpg_v2.service.CronErrorService;
import org.kiki_cpg_v2.service.InvoiceService;
import org.kiki_cpg_v2.util.AppUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl implements InvoiceService {
	
	@Autowired
	private InvoiceRepository invoiceRepository;
	
	@Autowired
	private InvoiceDetailRepository invoiceDetailRepository;
	
	@Autowired
	private AppUtility appUtility;
	
	@Autowired
	private CronErrorService cronErrorService;
	
	private static DecimalFormat df2 = new DecimalFormat(".##");

	@Override
	public InvoiceEntity createInvoice(String serviceId, Integer viewerId, Integer subscribed_days, Double amount,
			String mobileNo, Integer status, List<Date> dates) {
		try {

			

			Date date = new Date();

			Double day_charge = amount / subscribed_days;

			InvoiceEntity invoiceEntity = invoiceRepository.save(getInvoiceEntity(serviceId, viewerId, mobileNo, subscribed_days, amount
					, status, date));
			

			if (invoiceEntity.getId() != null) {
				
				List<InvoiceDetailEntity> invoiceDetailEntities = new ArrayList<InvoiceDetailEntity>();
				
				dates.forEach(e-> {
					invoiceDetailEntities.add(getInvoiceDetailEntity(invoiceEntity, date, day_charge, e));
				});

				invoiceDetailRepository.saveAll(invoiceDetailEntities);
				
			}

			
			return invoiceEntity;

		} catch (Exception e) {
			e.printStackTrace();
			
			cronErrorService.create(viewerId, e.getMessage(), e.getLocalizedMessage(), "IS crtInv");
			
		}
		return null;
	}

	@Override
	public InvoiceDetailEntity getInvoiceDetailEntity(InvoiceEntity invoiceEntity, Date date, Double day_charge,
			Date valiedDate) {
		InvoiceDetailEntity entity = new InvoiceDetailEntity();
		entity.setInvoiceId(invoiceEntity.getId());
		entity.setAmount(Double.parseDouble(df2.format(day_charge)));
		entity.setCreatedDate(date);
		entity.setValiedDate(valiedDate);
		
		return entity;
	}

	@Override
	public InvoiceEntity getInvoiceEntity(String serviceId, Integer viewerId, String mobileNo, Integer subscribed_days,
			Double amount, Integer status, Date date) {
		InvoiceEntity entity = new InvoiceEntity();
		
		entity.setServiceId(serviceId);
		entity.setViewerId(viewerId);
		entity.setMobile(mobileNo);
		entity.setSubscribedDays(subscribed_days);
		entity.setAmount(amount);
		entity.setSuccess(status);
		entity.setCreatedDate(date);
		
		return entity;
	}

	@Override
	public boolean update(InvoiceEntity invoiceEntity) {
		if(invoiceRepository.save(invoiceEntity) != null) {
			return true;
		}
		return false;
	}

}
