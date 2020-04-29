package com.kiki_cpg.development.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.kiki_cpg.development.entity.SubscriptionPayments;
import com.kiki_cpg.development.service.IdeabizService;
import com.kiki_cpg.development.service.SubscriptionPaymentService;

@CrossOrigin("")
@RestController()
public class IdeabizUnsubscribeController {

	@Autowired
	private IdeabizService ideabizService;

	@Autowired
	private SubscriptionPaymentService subscriptioPayService;

	@PostMapping(value = "/unsubscribed/ideabiz_unsubscribe", produces = "application/json")
	@ResponseBody
	public boolean ideabiz_unsubscribe(@RequestBody Map<String, String> userMap) {
		boolean res = false;

		String token = userMap.get("token");
		SubscriptionPayments subscriptionPayment = subscriptioPayService.validatePaymentToken(token);

		if (subscriptionPayment != null) {

			res = ideabizService.processUnsubscribe(subscriptionPayment);

		}
		return res;
	}
}
