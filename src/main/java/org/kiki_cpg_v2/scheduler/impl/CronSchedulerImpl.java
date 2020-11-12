package org.kiki_cpg_v2.scheduler.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.kiki_cpg_v2.scheduler.CronScheduler;
import org.kiki_cpg_v2.service.CronService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CronSchedulerImpl implements CronScheduler {

	final static Logger logger = LoggerFactory.getLogger(CronSchedulerImpl.class);

	@Autowired
	private CronService cronService;

	@Scheduled(cron = "0 30 10 * * *") // 10.30
	public void cronSetup1() {
		logger.info("Cron Started");
		System.out.println("Cron Started");
		cronStart("C-1");
	}

	@Scheduled(cron = "0 00 19 * * *") // 19.00
	public void cronSetup2() {
		cronStart("C-2");
	}

	@Override
	public void cronStart(String name) {

		String ipAddress = getIpAddress();

		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String time = new SimpleDateFormat("HH:mm:ss").format(new Date());

		Thread dialogThread = new Thread() {
			public void run() {
				cronService.startDialogCron(name ,ipAddress, date, time);
			}
		};

		dialogThread.start();

		Thread mobitelThread = new Thread() {
			public void run() {
				cronService.startMobitelCron(name, ipAddress, date, time);
			}
		};

		mobitelThread.start();
		
		/*Thread hnbThread = new Thread() {
			public void run() {
				
			}
		};

		hnbThread.start();*/
		
		//cronService.startHnbCron(name, ipAddress, date, time);

	}

	private String getIpAddress() {
		String ipAddress = "";
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			ipAddress = inetAddress.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}

		return ipAddress;
	}
}
