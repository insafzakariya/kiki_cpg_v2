package org.kiki_cpg_v2.controller;

import org.kiki_cpg_v2.client.DialogClient;
import org.kiki_cpg_v2.scheduler.CronScheduler;
import org.kiki_cpg_v2.service.CronService;
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
	private DialogClient dialogClient;
	
	@Autowired
	private CronScheduler cronService;

	@GetMapping
	public String test() {
		
		try {
			cronService.cronStart("Test");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "work";
	}
	
	@GetMapping("/sendOTP")
	public String sendOTP() {
		
		try {
			dialogClient.sendOtp("+94776497074", 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "work";
	}
}
