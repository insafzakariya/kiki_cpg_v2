package org.kiki_cpg_v2.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.kiki_cpg_v2.client.AppleClient;
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

	
	@GetMapping
	public String test() {
		cronScheduler.cronStart("Test");
		
		return "work";
	}

}
