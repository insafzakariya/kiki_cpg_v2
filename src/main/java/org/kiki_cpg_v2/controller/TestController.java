package org.kiki_cpg_v2.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.kiki_cpg_v2.client.AppleClient;
import org.kiki_cpg_v2.dto.NotificationDto;
import org.kiki_cpg_v2.entity.ViewerEntity;
import org.kiki_cpg_v2.repository.ViewerRepository;
import org.kiki_cpg_v2.scheduler.CronScheduler;
import org.kiki_cpg_v2.service.CronService;
import org.kiki_cpg_v2.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/test")
public class TestController {
	
	
	@Autowired
	private AppleClient appleClient;

	@Autowired
	private CronScheduler cronScheduler;
	
	@Autowired
	private CronService cronService;

	
	@GetMapping("/dialog-cron")
	public String dialogCron() {
		System.out.println("hit");
		String ipAddress = getIpAddress();

		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
		
		Thread thread = new Thread() {
			public void run() {
				cronService.startDialogCron("user-started-dialog-cron" ,ipAddress, date, time);
			}
		};
		
		//thread.start();
		
		return "work";
	}

	
	@GetMapping("/mobitel-cron")
	public String mobitelCron() {
		String ipAddress = getIpAddress();

		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
		
		Thread thread = new Thread() {
			public void run() {
				cronService.startMobitelCron("user-started-mobitel-cron" ,ipAddress, date, time);
			}
		};
		
		thread.start();
		
		return "work";
	}

	private String getIpAddress() {
		String ipAddress = "";
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			ipAddress = inetAddress.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return ipAddress;
	}
}
