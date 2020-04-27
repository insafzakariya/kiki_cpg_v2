package com.kiki_cpg.development.controller.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kiki_cpg.development.controller.MainController;
import com.kiki_cpg.development.dto.SubscriptionPaymentDto;
import com.kiki_cpg.development.service.ScratchCardCodesService;

@RestController
@CrossOrigin("*")
@RequestMapping("/scratchcard")
public class ScratchCardControllerRest {
	
	@Autowired
	private ScratchCardCodesService scratchCardCodesService;
	
	final static Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@PostMapping(value = { "/scratchCardPayment" })
	public ResponseEntity<Object> validateAndUseCode(@RequestParam("card_code") String cardCode, HttpServletRequest request) {
		HttpSession session = request.getSession();
		SubscriptionPaymentDto subscriptionPaymentDto = (SubscriptionPaymentDto) session
				.getAttribute("subscriptionPaymentDto");
		if (logger.isInfoEnabled()) {
			logger.info("Validate and Use Promo" + subscriptionPaymentDto.getViewerId());
		}

		String resp = scratchCardCodesService.setPayment(cardCode, subscriptionPaymentDto.getViewerId());

		if (resp.equals("Success")) {
			return new ResponseEntity<Object>(resp, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
