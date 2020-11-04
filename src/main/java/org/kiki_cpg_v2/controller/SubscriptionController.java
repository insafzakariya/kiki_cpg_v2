/**
 * @author Anjana Thrishakya
 * Nov 2, 2020
 * 8:51:35 AM
 */
package org.kiki_cpg_v2.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kiki_cpg_v2.dto.SubscriptionDto;
import org.kiki_cpg_v2.service.SubscriptionPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping("/rest/subscription")
public class SubscriptionController {
	
	@Autowired
	private SubscriptionPaymentService subscriptionPaymentService;

	@GetMapping("/isSubscribe/{viewerId}")
	public ResponseEntity<Object>  isSubscribe(@PathVariable Integer viewerId) {
		
		try {
			SubscriptionDto subscriptionDto = subscriptionPaymentService.isAppleSubscribe(viewerId);
			return new ResponseEntity<Object>(subscriptionDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

}
