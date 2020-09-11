/**
 * @DaSep 9, 2020 @KeellsController.java
 */
package org.kiki_cpg_v2.controller;

import org.kiki_cpg_v2.dto.PaymentRefDto;
import org.kiki_cpg_v2.dto.request.PaymentRequestDto;
import org.kiki_cpg_v2.dto.request.TransactionBeginDto;
import org.kiki_cpg_v2.service.KeellsService;
import org.kiki_cpg_v2.service.SubscriptionPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Anjana Thrishakya
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/keells")
public class KeellsController {
	
	@Autowired
	private KeellsService keellsService;
	
	@Autowired
	private SubscriptionPaymentService subscriptionPaymentService;

	@PostMapping("/begin")
	public ResponseEntity<Object> beginTransaction(@RequestBody TransactionBeginDto transactionBeginDto) {
		
		System.out.println(transactionBeginDto.toString());
		try {
			System.out.println(transactionBeginDto.toString());
			PaymentRefDto dto = keellsService.beginTransaction(transactionBeginDto);
			return new ResponseEntity<Object>(dto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/pay")
	public ResponseEntity<Object> payment(@RequestBody PaymentRequestDto paymentRequestDto){
		
		System.out.println(paymentRequestDto.toString());
		try {
			boolean isValied = subscriptionPaymentService.validateSubscriptionPaymentByToken(paymentRequestDto.getToken());
			if(isValied) {
				return keellsService.pay(paymentRequestDto);
			} else {
				return new ResponseEntity<>("Token Expired", HttpStatus.UNAUTHORIZED);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
