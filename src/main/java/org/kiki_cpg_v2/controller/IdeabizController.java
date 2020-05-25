package org.kiki_cpg_v2.controller;

import javax.servlet.http.HttpServletRequest;

import org.kiki_cpg_v2.client.DialogClient;
import org.kiki_cpg_v2.dto.DialogOtpDto;
import org.kiki_cpg_v2.dto.DialogPaymentConfirmDto;
import org.kiki_cpg_v2.dto.request.DialogOtpConfirmDto;
import org.kiki_cpg_v2.service.IdeabizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/rest/ideabiz")
public class IdeabizController {
	
	@Autowired
	private DialogClient dialogClient;
	
	@Autowired
	private IdeabizService ideabizService;
	
	@GetMapping("/otp/send/{mobile_no}/{day}")
	public ResponseEntity<Object> sentOtp(@PathVariable String mobile_no, @PathVariable Integer day) {

		try {
			
			DialogOtpDto dialogOtoDto = dialogClient.sendOtp(mobile_no, day);
			System.out.println(dialogOtoDto.getServerRef());
			
			return new ResponseEntity<Object>(dialogOtoDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/pin_subscription_confirm", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<Object> pin_subscription_confirm(@RequestBody DialogOtpConfirmDto dialogOtpConfirmDto,
			HttpServletRequest request) {
		System.out.println(dialogOtpConfirmDto.toString());
		try {
			DialogPaymentConfirmDto dialogPaymentConfirmDto =ideabizService.pinSubscriptionConfirm(dialogOtpConfirmDto);
			return new ResponseEntity<Object>(dialogPaymentConfirmDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
