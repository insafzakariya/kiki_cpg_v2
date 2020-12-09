/**
 * @DaOct 12, 2020 @HomeController.java
 */
package org.kiki_cpg_v2.controller;

import org.kiki_cpg_v2.dto.SubscriptionPaymentDto;
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

	@PostMapping("/auth")
	public SubscriptionPaymentDto login(@RequestParam(name = "token", required = true) String token) {
		System.out.println(token);
		return null;
	}
	
}
