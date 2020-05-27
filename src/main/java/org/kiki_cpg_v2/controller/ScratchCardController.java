package org.kiki_cpg_v2.controller;

import org.kiki_cpg_v2.dto.ScratchCardPaymentDto;
import org.kiki_cpg_v2.service.ScratchCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/rest/scratchcard")
public class ScratchCardController {

	@Autowired
	private ScratchCardService scratchCardService;

	@PostMapping(value = { "/payment" })
	public ResponseEntity<Object> validateAndUseCode(@RequestBody ScratchCardPaymentDto scratchCardPaymentDto) {

		String resp;
		try {
			resp = scratchCardService.setPayment(scratchCardPaymentDto);
			return new ResponseEntity<Object>(resp, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
