package org.kiki_cpg_v2.controller;

import org.kiki_cpg_v2.dto.SmsDto;
import org.kiki_cpg_v2.service.SmsService;
import org.kiki_cpg_v2.service.impl.CronServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/sms")
public class SmsController {
	
	final static Logger logger = LoggerFactory.getLogger(SmsController.class);

	@Autowired
	private SmsService smsService;

	@PostMapping
	public Integer sendSms(@RequestBody SmsDto smsDto) {
		
		logger.info(smsDto.toString());
		
		System.out.println(smsDto.toString());
		try {
			return smsService.sendSms(smsDto.getMobile(), smsDto.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
}
