package org.kiki_cpg_v2.controller;

import javax.servlet.http.HttpServletRequest;

import org.kiki_cpg_v2.dto.ResponseMapDto;
import org.kiki_cpg_v2.dto.request.MobitelActivationDto;
import org.kiki_cpg_v2.service.MobitelService;
import org.kiki_cpg_v2.service.SubscriptionPaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin("*")
@RestController()
@RequestMapping("/mobitel")
public class MobitelController {
	final static Logger logger = LoggerFactory.getLogger(MobitelController.class);
	
	@Autowired
	private MobitelService mobitelService;
	
	@Autowired
	private SubscriptionPaymentService subscriptionService;
	
	@PostMapping(value = "/payment")
	public ResponseEntity<Object> activateDataBundle(@RequestBody MobitelActivationDto mobitelActivationDto, HttpServletRequest request){
		logger.info("called Payment : " + mobitelActivationDto.toString());
		try{
			ResponseMapDto dto = new ResponseMapDto();
			boolean isValied = subscriptionService.validateSubscriptionPayment(mobitelActivationDto.getSubscriptionPaymentId());
			if(isValied) {
				String resp = mobitelService.pay(mobitelActivationDto.getMobileNo(), mobitelActivationDto.getViewerId(), mobitelActivationDto.getActivationStatus(),mobitelActivationDto.getSubscriptionPaymentId(), mobitelActivationDto.getDays());
				logger.info("response : " + resp);
				
				dto.setStatus(resp);
				return new ResponseEntity<Object>(dto, HttpStatus.OK);
			} else {
				dto.setStatus("Subscription Token Expired");
				return new ResponseEntity<Object>(dto, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
