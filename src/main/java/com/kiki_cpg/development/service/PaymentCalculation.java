package com.kiki_cpg.development.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiki_cpg.development.controller.IdeabizController;
import com.kiki_cpg.development.entity.Config;
import com.kiki_cpg.development.entity.Ideabiz;
import com.kiki_cpg.development.entity.Viewers;
import com.kiki_cpg.development.repository.ConfigRepository;
import com.kiki_cpg.development.repository.IdeabizRepository;
import com.kiki_cpg.development.repository.ViewerRepository;

import javax.transaction.Transactional;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class PaymentCalculation {
	@Autowired
	IdeabizController ideabizController;

	@Autowired
	IdeabizRepository ideabizRepository;

	@Autowired
	ViewerRepository viewerRepository;

	@Autowired
	ConfigRepository configRepository;

	@Autowired
	InvoiceService invoiceService;

	@Autowired
	OTPService OTPService;


	// @Transactional
	public void proceedPendingIdiabizPayment(String cronStartTime, List<Ideabiz> getSubList, List<Viewers> getViewers,
			Integer cronId) {
		Integer id = 0;
		String ipAddress = "";
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			ipAddress = inetAddress.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		try {

			int totalSubscribedUser = getSubList.size();
			int transactioncount = 0;

			OTPService.sendMsg("+94773799390", "Ideabiz Pending Subscribtion Count Is : " + totalSubscribedUser
					+ " -Cron Started :" + cronStartTime + " -Server Ip : " + ipAddress);
			OTPService.sendMsg("+94779922990", "Ideabiz Pending Subscribtion Count Is : " + totalSubscribedUser
					+ " -Cron Started :" + cronStartTime + " -Server Ip : " + ipAddress);
			OTPService.sendMsg("+94771485821", "Ideabiz Pending Subscribtion Count Is : " + totalSubscribedUser
					+ " -Cron Started :" + cronStartTime + " -Server Ip : " + ipAddress);
			OTPService.sendMsg("+94767072477", "Ideabiz Pending Subscribtion Count Is : " + totalSubscribedUser
					+ " -Cron Started :" + cronStartTime + " -Server Ip : " + ipAddress);

			try {
				for (int i = 0; i < getViewers.size(); i++) {

					System.out.println("viewer id " + getViewers.get(i).getViewerId());

					if (getViewers.get(i) != null) {
						if (getSubList.get(i) != null) {
							Double amount = getAmountByDays(getSubList.get(i).getSubscribed_days());

							int invoice_id = ideabizController.proceed_payment(cronStartTime,
									getSubList.get(i).getViwer_id(), getSubList.get(i).getSubscribed_days(), "Ideabiz",
									amount, cronId);
							id = getViewers.get(i).getViewerId();
							transactioncount = transactioncount + 1;
							if (invoice_id > 0) {
								invoiceService.updatePolicyExpireIdeaBiz(invoice_id, getSubList.get(i).getViwer_id());
							}

						}
//						Thread.sleep(3000);
					}

				}
			} catch (Exception e) {
//				CronErrorDto cronErrorDto = new CronErrorDto();
//				cronErrorDto.setErrorDate(new Date());
//				cronErrorDto.setViewerId(id);
//				cronErrorDto.setErrorDesc(e.getMessage());
//				cronErrorDto.setErrorMsg(e.getStackTrace().toString());
//				cronErrorDto.setSystemPage("PC");
//				cronErrorDto.setCronId(cronId);
//
//				cronErrorService.addCronError(cronErrorDto);

//				OTPService.sendMsg("+94773799390",
//						"Exception :  " + e.getMessage() + "Class : PC ForLoop" + "Viewer Id : " + id);
//				OTPService.sendMsg("+94767072477",
//						"Exception :  " + e.getMessage() + "Class : PC ForLoop" + "Viewer Id : " + id);
			}
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

			OTPService.sendMsg("+94773799390", "Ideabiz Done Subscribtion Count Is :" + transactioncount + " / "
					+ totalSubscribedUser + " -Cron Stoped :" + sdf.format(date) + " -Server Ip : " + ipAddress);
			OTPService.sendMsg("+94779922990", "Ideabiz Done Subscribtion Count Is :" + transactioncount + " / "
					+ totalSubscribedUser + " -Cron Stoped :" + sdf.format(date) + " -Server Ip : " + ipAddress);
			OTPService.sendMsg("+94771485821", "Ideabiz Done Subscribtion Count Is :" + transactioncount + " / "
					+ totalSubscribedUser + " -Cron Stoped :" + sdf.format(date) + " -Server Ip : " + ipAddress);
			OTPService.sendMsg("+94767072477", "Ideabiz Done Subscribtion Count Is :" + transactioncount + " / "
					+ totalSubscribedUser + " -Cron Stoped :" + sdf.format(date) + " -Server Ip : " + ipAddress);

		} catch (Exception e) {
//			e.getStackTrace();
//			CronErrorDto cronErrorDto = new CronErrorDto();
//			cronErrorDto.setErrorDate(new Date());
//			cronErrorDto.setViewerId(id);
//			cronErrorDto.setErrorDesc(e.getMessage());
//			cronErrorDto.setErrorMsg(e.getStackTrace().toString());
//			cronErrorDto.setSystemPage("PC");
//			cronErrorDto.setCronId(cronId);
//
//			cronErrorService.addCronError(cronErrorDto);

//			OTPService.sendMsg("+94773799390",
//					"Exception :  " + e.getMessage() + "Class : PC " + "Viewer Id : " + id);
//			OTPService.sendMsg("+94767072477",
//					"Exception :  " + e.getMessage() + "Class : PC " + "Viewer Id : " + id);
		}

	}

	public Double getAmountByDays(Integer day) {
		Double amount = 0.0;
		List<Config> configList = configRepository.findAll();
		Config config = configList.get(0);

		switch (day) {
		case 1:
			amount = config.getDay_charge();
			break;
		case 7:
			amount = config.getWeek_charge();
			break;

		default:
			amount = config.getDay_charge();
			break;
		}
		return amount;

	}
}
