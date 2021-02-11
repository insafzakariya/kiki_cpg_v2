/**
 * @DaFeb 5, 2021 @AirletController.java
 */
package org.kiki_cpg_v2.controller;

import org.kiki_cpg_v2.dto.request.AirtelClientRequestDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * @author Anjana Thrishakya
 */
@RestController
@RequestMapping("/rest/airlet")
public class AirletController {

	@GetMapping
	public String test() {
		AirtelClientRequestDto airtelClientRequestDto = new AirtelClientRequestDto();
		 XmlMapper xmlMapper = new XmlMapper();
		    try {
				String xml = xmlMapper.writeValueAsString(airtelClientRequestDto);
				System.out.println(xml);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		return "work";
	}
	
	
}
