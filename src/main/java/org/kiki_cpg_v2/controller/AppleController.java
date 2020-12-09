/**
 * @author Anjana Thrishakya
 * Nov 2, 2020
 * 4:46:12 PM
 */
package org.kiki_cpg_v2.controller;

import org.kiki_cpg_v2.dto.ApplePayDto;
import org.kiki_cpg_v2.dto.PaymentRefDto;
import org.kiki_cpg_v2.service.AppleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping("/rest/apple")
public class AppleController {

	@Autowired
	private AppleService appleService;

	@PostMapping("/pay")
	public ResponseEntity<Object> pay(@RequestBody ApplePayDto applePayDto) {
		try {
			PaymentRefDto paymentRefDto = appleService.pay(applePayDto);
			return new ResponseEntity<Object>(paymentRefDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
