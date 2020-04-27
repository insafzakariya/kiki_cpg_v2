package com.kiki_cpg.development.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiki_cpg.development.client.DialogClient;
import com.kiki_cpg.development.dto.CronErrorDto;
import com.kiki_cpg.development.entity.Ideabiz;
import com.kiki_cpg.development.entity.Invoice;
import com.kiki_cpg.development.entity.InvoiceDetails;
import com.kiki_cpg.development.entity.Viewers;
import com.kiki_cpg.development.repository.IdeabizRepository;
import com.kiki_cpg.development.repository.InvoiceDetailsRepository;
import com.kiki_cpg.development.repository.InvoiceRepository;
import com.kiki_cpg.development.service.ContentService;
import com.kiki_cpg.development.service.InvoiceService;
import com.kiki_cpg.development.service.OTPService;
import com.kiki_cpg.development.service.calculation.DateCalculate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService{
	@Autowired
	InvoiceRepository invoiceRepository;

	@Autowired
	IdeabizRepository ideabizRepository;

	@Autowired
	InvoiceDetailsRepository invoiceDetailsRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	OTPService otpService;
	
	@Autowired
	private DialogClient dialogClient;
	
	@Autowired
	private ContentService contentService;

//	@Autowired
//	CronErrorService cronErrorService;

	private static DecimalFormat df2 = new DecimalFormat(".##");

//	@Transactional
	@Override
	public Integer create(String serviceId, Viewers viewers, Integer subscribed_days, Double amount) {
		try {

			List<Date> dates = DateCalculate.getDatesBetweenUsingJava7(subscribed_days);

			Date date = new Date();
			long time = date.getTime();
			Timestamp ts = new Timestamp(time);

			Double day_charge = amount / subscribed_days;

			Invoice invoice = new Invoice();
			invoice.setServiceId(serviceId);
			invoice.setViewerId(viewers.getViewerId());
			invoice.setMobile(viewers.getMobileNumber());
			invoice.setSubscribedDays(subscribed_days);
			invoice.setAmount(amount);
			invoice.setSuccess(0);
			invoice.setCreatedDate(ts);
			invoice = invoiceRepository.save(invoice);
			// otpService.sendMsg("+94767072477", "invoice id :" +invoice.getId());

			if (invoice.getId() != null) {

				for (Integer i = 0; i < dates.size(); i++) {
					System.out.println("dates" + dates.get(i).toString());
					InvoiceDetails invoiceDetails = new InvoiceDetails();
					invoiceDetails.setInvoiceId(invoice.getId());
					invoiceDetails.setAmount(Double.parseDouble(df2.format(day_charge)));
					invoiceDetails.setCreatedDate(ts);
					invoiceDetails.setValiedDate(dates.get(i));
					invoiceDetails = invoiceDetailsRepository.save(invoiceDetails);
					// otpService.sendMsg("+94767072477", "invoice id :" +invoiceDetails.getId());
				}

			}

			return invoice.getId();

		} catch (Exception e) {
			CronErrorDto cronErrorDto = new CronErrorDto();
			cronErrorDto.setErrorDate(new Date());
			cronErrorDto.setViewerId(viewers.getViewerId());
			cronErrorDto.setErrorDesc(e.getMessage());
			cronErrorDto.setErrorMsg(e.getStackTrace().toString());
			cronErrorDto.setSystemPage("IS crtInv");
			// cronErrorDto.setCronId();

//			cronErrorService.addCronError(cronErrorDto);

//			otpService.sendMsg("+94767072477", "Error in InvoiceService -InvCrt " + e.getMessage());
		}
		return 0;
	}

	@Override
	public boolean updateInvoice(Integer invoiceId, Integer status) {
		try {
			Invoice invoice = invoiceRepository.findById(invoiceId).get();
			invoice.setSuccess(status);
			System.out.println("Invoice before save: " + invoice.getId());
            invoice = invoiceRepository.save(invoice);
			System.out.println("Invoice save: " + invoice.getId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updatePolicyExpireIdeaBiz(Integer invoiceId, Integer viewerId) {
		try {
			List<InvoiceDetails> invoiceDetailsList = invoiceDetailsRepository.findByInvoiceId(invoiceId);
			InvoiceDetails invoiceDetails = invoiceDetailsList.get(invoiceDetailsList.size() - 1);

			Ideabiz ideabiz = ideabizRepository.findOneByViwerId(viewerId);
			ideabiz.setLastPolicyUpdatedAt(invoiceDetails.getCreatedDate());
			ideabiz.setPolicyExpireAt(DateCalculate.getbeforeDay(1, invoiceDetails.getValiedDate()));
			ideabizRepository.save(ideabiz);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public int proceedPayment(Viewers viewers, Integer subscribedDays, String serviceId, Double amount) {
		try {

			System.out.println("viewer id " + viewers.getViewerId());

			int invoiceId = create(serviceId, viewers, subscribedDays, amount);
			System.out.println("Invoice created " + invoiceId);


			String paid = dialogClient.dialogPaymentConfirm(String.valueOf(invoiceId), viewers.getMobileNumber(), amount,
					subscribedDays, viewers.getViewerId());
			System.out.println("check paid : " + paid);
			if (paid.equals("Sucess")) {
				updateInvoice(invoiceId, 1);

				if (subscribedDays == 1) {
					contentService.updateViewerPolicies(viewers.getViewerId(), 81, false);

				} else if (subscribedDays == 7) {
					contentService.updateViewerPolicies(viewers.getViewerId(), 106, false);
				}

				return invoiceId;
			} else {
				return 0;
			}
		} catch (Exception e) {

		}
		return 0;
	}
}
