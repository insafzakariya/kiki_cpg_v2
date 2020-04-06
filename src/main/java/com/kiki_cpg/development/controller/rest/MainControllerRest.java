package com.kiki_cpg.development.controller.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.kiki_cpg.development.dto.SubscriptionPaymentDto;
import com.kiki_cpg.development.service.SubscriptionPaymentService;

@RestController("/rest/home")
public class MainControllerRest {

	final static Logger logger = LoggerFactory.getLogger(MainControllerRest.class);

	@Autowired
	private SubscriptionPaymentService subscriptionPaymentService;

	@GetMapping("/initialize")
	public ResponseEntity<Object> homePage(HttpServletRequest request,
			@RequestParam(value = "token") String paymentToken,
			@RequestParam(value = "isFreeTrial", required = false, defaultValue = "false") boolean isFreeTrial,
			@RequestParam(value = "type", required = false, defaultValue = "false") String type) throws IOException {

		logger.info("susilawebpay application started.");

		try {
			SubscriptionPaymentDto subscriptionPaymentDto = subscriptionPaymentService
					.getSubscriptionPaymentByToken(paymentToken, type);
			if (subscriptionPaymentDto != null) {
				return new ResponseEntity<Object>(subscriptionPaymentDto, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(subscriptionPaymentDto, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("404", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
