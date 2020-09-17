/**
 * @DaSep 10, 2020 @AuthenticationController.java
 */
package org.kiki_cpg_v2.controller;

import org.kiki_cpg_v2.dto.TokenDto;
import org.kiki_cpg_v2.dto.request.TokenRequestDto;
import org.kiki_cpg_v2.service.AuthenticationService;
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
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping("/generateToken")
	public ResponseEntity<Object> generateToken(@RequestBody TokenRequestDto tokenRquestDto) {
		try {
			TokenDto tokenDto = authenticationService.generateToken(tokenRquestDto);
			return new ResponseEntity<Object>(tokenDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
