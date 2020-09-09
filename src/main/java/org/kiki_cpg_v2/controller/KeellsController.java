/**
 * @DaSep 9, 2020 @KeellsController.java
 */
package org.kiki_cpg_v2.controller;

import org.kiki_cpg_v2.dto.PaymentRefDto;
import org.kiki_cpg_v2.dto.request.TransactionBeginDto;
import org.kiki_cpg_v2.service.KeellsService;
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

	@PostMapping("/begin")
	public ResponseEntity<Object> beginTransaction(@RequestBody TransactionBeginDto transactionBeginDto) {
		try {
			System.out.println(transactionBeginDto.toString());
			PaymentRefDto dto = keellsService.beginTransaction(transactionBeginDto);
			return new ResponseEntity<Object>(dto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
