/**
 * @DaOct 8, 2020 @PaymentMethodController.java
 */
package org.kiki_cpg_v2.controller;

import java.util.List;

import org.kiki_cpg_v2.dto.PaymantPlanDto;
import org.kiki_cpg_v2.dto.request.PaymantMethodDto;
import org.kiki_cpg_v2.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Anjana Thrishakya
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/rest/paymentmethod")
public class PaymentMethodController {

	@Autowired
	private PaymentMethodService paymentMethodService;
	
	@GetMapping("/active")
	public List<PaymantMethodDto> getActivePaymentMethods() {
		List<PaymantMethodDto> paymantMethodDto = paymentMethodService.getActivePaymentMethodes();
		return paymantMethodDto;
	}
	
}
