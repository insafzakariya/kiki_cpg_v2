/**
 * @DaSep 2, 2020 @HutchController.java
 */
package org.kiki_cpg_v2.controller;

import java.util.HashMap;
import java.util.Map;

import org.kiki_cpg_v2.dto.PaymentRefDto;
import org.kiki_cpg_v2.dto.request.TransactionBeginDto;
import org.kiki_cpg_v2.service.HutchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Anjana Thrishakya
 */
@RestController
@RequestMapping("/hutch")
public class HutchController {

	@Autowired
	private HutchService hutchService;

	@PostMapping("/transaction")
	public Map<String, String> transaction(@RequestBody Map<String, String> requestMap) throws Exception {
		System.out.println(requestMap.toString());
		Map<String, String> response = new HashMap<String, String>();
		try {
			response = hutchService.transaction(requestMap);
		} catch (Exception e) {

			response.put("ResultCode", "500");
			response.put("Description", e.getMessage());
		}
		return response;
	}

	@PostMapping("/subscribe")
	public ResponseEntity<Object> subscribe(@RequestBody TransactionBeginDto beginDto) throws Exception {

		try {
			PaymentRefDto dto = hutchService.beginSubscribe(beginDto);
			return new ResponseEntity<Object>(dto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

}
