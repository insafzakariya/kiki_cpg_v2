package com.kiki_cpg.development.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kiki_cpg.development.dto.MobitelActivationDto;
import com.kiki_cpg.development.dto.ResponseMapDto;
import com.kiki_cpg.development.dto.SubscriptionPaymentDto;
import com.kiki_cpg.development.service.MobitelService;

@CrossOrigin("*")
@RestController()
@RequestMapping("/mobitel")
public class MobitelController {
	
	final static Logger logger = LoggerFactory.getLogger(MobitelController.class);

	@Autowired
	MobitelService mobitelService;
	
	
	@PostMapping(value = "/payment")
	public ResponseEntity<Object> activateDataBundle(@RequestBody MobitelActivationDto mobitelActivationDto, HttpServletRequest request){
		logger.info("called Payment : " + mobitelActivationDto.toString());
		try{
			SubscriptionPaymentDto subscriptionPaymentDto = (SubscriptionPaymentDto) request.getSession().getAttribute("subscriptionPaymentDto");
			logger.info("subscription valied in session : " + subscriptionPaymentDto);
			String resp = mobitelService.pay(mobitelActivationDto.getMobileNo(), mobitelActivationDto.getViewerId(), mobitelActivationDto.getActivationStatus(), subscriptionPaymentDto);
			logger.info("response : " + resp);
			
			ResponseMapDto dto = new ResponseMapDto();
			dto.setStatus("success");
			return new ResponseEntity<Object>(dto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
