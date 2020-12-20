package org.kiki_cpg_v2.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.kiki_cpg_v2.client.AppleClient;
import org.kiki_cpg_v2.client.DialogClient;
import org.kiki_cpg_v2.dto.NotificationDto;
import org.kiki_cpg_v2.entity.ViewerEntity;
import org.kiki_cpg_v2.repository.ViewerRepository;
import org.kiki_cpg_v2.scheduler.CronScheduler;
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
	private DialogClient dialogClient;

	
	@GetMapping
	public String test() {
		try {
			String auth = dialogClient.createAccessToken();
			dialogClient.unsubscribe(auth, 1, 30, "+94776497074");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "work";
	}

}
