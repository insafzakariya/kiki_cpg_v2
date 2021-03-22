/**
 * @DaFeb 5, 2021 @AirletController.java
 */
package org.kiki_cpg_v2.controller;

import org.kiki_cpg_v2.dto.PaymentRefDto;
import org.kiki_cpg_v2.dto.request.AirtelClientRequestDto;
import org.kiki_cpg_v2.dto.request.TransactionBeginDto;
import org.kiki_cpg_v2.service.AirtelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * @author Anjana Thrishakya
 */
@RestController
@RequestMapping("/rest/airlet")
@CrossOrigin(origins = "*")
public class AirletController {
	
	@Autowired
	private AirtelService airtelService;

//	@GetMapping
//	public String test() {
//		AirtelClientRequestDto airtelClientRequestDto = new AirtelClientRequestDto();
//		 XmlMapper xmlMapper = new XmlMapper();
//		    try {
//				String xml = xmlMapper.writeValueAsString(airtelClientRequestDto);
//				System.out.println(xml);
//			} catch (JsonProcessingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		    
//		return "work";
//	}
//	
	@PostMapping("/subscribe")
	public ResponseEntity<Object> subscribe(@RequestBody TransactionBeginDto beginDto) throws Exception {

		System.out.println("calle to subscribe");
		System.out.println(beginDto.toString());
		try {
			PaymentRefDto dto = airtelService.beginSubscribe(beginDto);
			return new ResponseEntity<Object>(dto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
}
