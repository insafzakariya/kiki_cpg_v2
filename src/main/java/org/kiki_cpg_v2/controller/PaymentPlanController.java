package org.kiki_cpg_v2.controller;

import java.util.List;

import org.kiki_cpg_v2.dto.PaymantPlanDto;
import org.kiki_cpg_v2.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/rest/paymentplan")
public class PaymentPlanController {
	
	@Autowired
	private PaymentMethodService paymentMethodService;
	
	@GetMapping(value = {"/planlist/{paymentMethodId}/{lang}, /planlist/{paymentMethodId}"})
	public List<PaymantPlanDto> getPaymentListDto( @PathVariable Integer paymentMethodId, @PathVariable(name = "lang", required = false) String lang) {
		System.out.println("paymentMethodId" + paymentMethodId);
		System.out.println("paymentMethodId" + lang);
		//List<PaymantPlanDto> packageDtos = paymentMethodService.getPaymentPlan(paymentMethodId);
		return null;
	}

}
