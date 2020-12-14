/**
 * @DaOct 12, 2020 @HomeController.java
 */
package org.kiki_cpg_v2.controller;

import org.kiki_cpg_v2.dto.SubscriptionPaymentDto;
import org.kiki_cpg_v2.service.SubscriptionPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Anjana Thrishakya
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/rest/home")
public class HomeController {
	
	@Autowired
	private SubscriptionPaymentService subscriptionPaymentService;

	@PostMapping("/auth")
	public SubscriptionPaymentDto login(@RequestParam(name = "token", required = true) String token) {
		System.out.println(token);
		try {
			SubscriptionPaymentDto subscriptionPaymentDto =  subscriptionPaymentService.getSubscriptionPaymentDtoByToken(token, "");
			//System.out.println(subscriptionPaymentDto.toString());
			return subscriptionPaymentDto;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
