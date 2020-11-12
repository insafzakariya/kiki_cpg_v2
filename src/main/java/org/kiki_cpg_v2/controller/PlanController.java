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
@RequestMapping("/rest/plan")
public class PlanController {
	
	@Autowired
	private PaymentMethodService paymentMethodService;
	
	@GetMapping("/planlist/{paymentMethodId}")
	public List<PaymantPlanDto> getPaymentListDto( @PathVariable Integer paymentMethodId) {
		System.out.println("paymentMethodId : " + paymentMethodId);
		List<PaymantPlanDto> packageDtos = paymentMethodService.getPaymentPlan(paymentMethodId, null);
		return packageDtos;
	}

	@GetMapping("/planlist/{paymentMethodId}/{lang}")
	public List<PaymantPlanDto> getPaymentListDtoByLang( @PathVariable Integer paymentMethodId, @PathVariable(name = "lang", required = false) String lang) {
		System.out.println("paymentMethodId 12 : " + paymentMethodId);
		List<PaymantPlanDto> packageDtos = paymentMethodService.getPaymentPlan(paymentMethodId, lang);
		return packageDtos;
	}

}
