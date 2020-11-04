package org.kiki_cpg_v2.controller;

import org.kiki_cpg_v2.dto.ResponseMapDto;
import org.kiki_cpg_v2.dto.UnsubscribeDto;
import org.kiki_cpg_v2.service.HNBService;
import org.kiki_cpg_v2.service.HutchService;
import org.kiki_cpg_v2.service.IdeabizService;
import org.kiki_cpg_v2.service.MobitelService;
import org.kiki_cpg_v2.service.SubscriptionPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/rest/unsubscribe")
public class UnsubscribeController {

	@Autowired
	private SubscriptionPaymentService subscriptionService;

	@Autowired
	private IdeabizService ideabizService;
	
	@Autowired
	private MobitelService mobitelService;
	
	@Autowired
	private HNBService hnbService;
	
	@Autowired
	private HutchService hutchService;

	@PostMapping(produces = "application/json")
	@ResponseBody
	public ResponseEntity<Object> unsubscribe(@RequestBody UnsubscribeDto unsubscribeDto) {
		System.out.println(unsubscribeDto.toString());
		try {
			boolean res = false;

			boolean isValied = subscriptionService
					.validateSubscriptionPayment(unsubscribeDto.getSubscriptionPaymentId());

			ResponseMapDto responseMapDto = new ResponseMapDto();
			responseMapDto.setStatus("Fail");

			if (isValied) {

				if (unsubscribeDto.getType() == 4) {
					res = ideabizService.processUnsubscriptionIdeabiz(null, unsubscribeDto.getViewerid(),
							unsubscribeDto.getMobile(), true);
					if (res) {
						responseMapDto.setStatus("success");
					}
				}

				if (unsubscribeDto.getType() == 5) {
					res = mobitelService.processUnsubscriptionMobitel(unsubscribeDto.getViewerid(),
							unsubscribeDto.getMobile());
					if (res) {
						responseMapDto.setStatus("success");
					}
				}
				
				if (unsubscribeDto.getType() == 7) {
					res = hnbService.processUnsubscription(unsubscribeDto.getViewerid(),
							unsubscribeDto.getMobile());
					if (res) {
						responseMapDto.setStatus("success");
					}
				}
				
				if (unsubscribeDto.getType() == 8) {
					res = hutchService.processUnsubscription(unsubscribeDto.getViewerid(),
							unsubscribeDto.getMobile());
					if (res) {
						responseMapDto.setStatus("success");
					}
				}

			}
			return new ResponseEntity<Object>(responseMapDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			if(e.getLocalizedMessage().contains("Connection timed out")) {
				return new ResponseEntity<Object>("Connection Time Out", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<Object>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
