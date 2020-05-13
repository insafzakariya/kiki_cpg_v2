package com.kiki_cpg.development.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kiki_cpg.development.dto.ResponseMapDto;
import com.kiki_cpg.development.dto.SubscriptionPaymentDto;
import com.kiki_cpg.development.entity.SubscriptionPayments;
import com.kiki_cpg.development.service.IdeabizService;
import com.kiki_cpg.development.service.MobitelService;
import com.kiki_cpg.development.service.SubscriptionPaymentService;

@CrossOrigin("")
@RestController()
@RequestMapping("/unsubscribed")
public class UnsubscribeController {

	@Autowired
	private IdeabizService ideabizService;

	@Autowired
	private MobitelService mobitelService;

	@Autowired
	private SubscriptionPaymentService subscriptioPayService;

	@PostMapping(value = "/ideabiz_unsubscribe", produces = "application/json")
	@ResponseBody
	public ResponseEntity<Object> ideabiz_unsubscribe(@RequestBody Map<String, String> userMap) {
		boolean res = false;

		String token = userMap.get("token");
		SubscriptionPaymentDto subscriptionPayment = subscriptioPayService.validatePaymentToken(token);

		ResponseMapDto responseMapDto = new ResponseMapDto();
		responseMapDto.setStatus("Fail");

		if (subscriptionPayment != null) {

			res = ideabizService.processUnsubscribe(subscriptionPayment);
			if (res) {
				responseMapDto.setStatus("success");
			}
		}
		return new ResponseEntity<Object>(responseMapDto, HttpStatus.OK);
	}

	@PostMapping(value = "/mobitel_unsubscribe", produces = "application/json")
	@ResponseBody
	public ResponseEntity<Object> mobitel_unsubscribe(@RequestBody Map<String, String> userMap) {
		boolean res = false;

		String token = userMap.get("token");
		SubscriptionPaymentDto subscriptionPayment = subscriptioPayService.validatePaymentToken(token);

		ResponseMapDto responseMapDto = new ResponseMapDto();
		responseMapDto.setStatus("Fail");

		if (subscriptionPayment != null) {
			res = mobitelService.processUnsubscribe(subscriptionPayment);
			if (res) {
				responseMapDto.setStatus("success");
			}
		}
		return new ResponseEntity<Object>(responseMapDto, HttpStatus.OK);
	}
}
