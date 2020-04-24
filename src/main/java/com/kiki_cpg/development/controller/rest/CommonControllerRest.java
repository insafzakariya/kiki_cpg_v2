package com.kiki_cpg.development.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kiki_cpg.development.service.CommonService;

@RestController
@RequestMapping("/rest/common")
@CrossOrigin(origins = "*")
public class CommonControllerRest {
	
	@Autowired
	private CommonService commonService;
	
	@GetMapping("/verifyNumber/{method}/{number}")
	public ResponseEntity<Object> verifyNumber(@PathVariable Integer method, @PathVariable String number){
		try {
			String resp = commonService.verifyNumber(method, number);
			return new ResponseEntity<Object>(resp , HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("Fail : " + e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
