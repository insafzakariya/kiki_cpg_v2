package org.kiki_cpg_v2.controller;

import org.kiki_cpg_v2.dto.ResponseMapDto;
import org.kiki_cpg_v2.dto.UnsubscribeDto;
import org.kiki_cpg_v2.service.IdeabizService;
import org.kiki_cpg_v2.service.SubscriptionService;
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
	private SubscriptionService subscriptionService;

	@Autowired
	private IdeabizService ideabizService;

	@PostMapping(produces = "application/json")
	@ResponseBody
	public ResponseEntity<Object> ideabiz_unsubscribe(@RequestBody UnsubscribeDto unsubscribeDto) {
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

				}

			}
			return new ResponseEntity<Object>(responseMapDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
