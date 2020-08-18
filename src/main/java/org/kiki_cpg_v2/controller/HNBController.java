/**
 * @DaJul 29, 2020 @HNBController.java
 */
package org.kiki_cpg_v2.controller;

import org.kiki_cpg_v2.dto.PaymentRefDto;
import org.kiki_cpg_v2.dto.request.HNBBeginDto;
import org.kiki_cpg_v2.dto.request.HNBVerifyDto;
import org.kiki_cpg_v2.service.HNBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
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
@RequestMapping("/rest/hnb")
public class HNBController {

	@Autowired
	private HNBService hnbService;
	
	@PostMapping("/begin")
	public ResponseEntity<Object> beginTransaction(@RequestBody HNBBeginDto hnbBeginDto) {
		try {
			System.out.println(hnbBeginDto);
			PaymentRefDto dto = hnbService.beginTransaction(hnbBeginDto, true);
			return new ResponseEntity<Object>(dto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/transaction/callback")
	public ResponseEntity<Object> transaction(@RequestBody MultiValueMap<String, String> formData) {
		try {
			System.out.println(formData.toString());
			hnbService.saveTransaction(formData);
			System.out.println("done");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@PostMapping("/verify")
	public ResponseEntity<Object> verify(@RequestBody HNBVerifyDto hnbVerifyDto) {
		try {
			System.out.println(hnbVerifyDto.toString());
			PaymentRefDto dto = hnbService.verifyTransaction(hnbVerifyDto);
			return new ResponseEntity<Object>(dto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			PaymentRefDto dto = new PaymentRefDto();
			dto.setStatus(e.getMessage());
			return new ResponseEntity<Object>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
