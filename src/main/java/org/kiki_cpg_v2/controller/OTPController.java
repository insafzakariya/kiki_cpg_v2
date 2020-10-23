/**
 * @DaJul 23, 2020 @OTPController.java
 */
package org.kiki_cpg_v2.controller;

import javax.servlet.http.HttpServletRequest;

import org.kiki_cpg_v2.dto.DialogOtpDto;
import org.kiki_cpg_v2.dto.request.DialogOtpConfirmDto;
import org.kiki_cpg_v2.service.OTPService;
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

/**
 * @author Anjana Thrishakya
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/rest/otp")
public class OTPController {
	
	@Autowired
	private OTPService otpService;

	@GetMapping("/send/{viewer_id}/{mobileNo}")
	public ResponseEntity<Object> sentOtp(@PathVariable Integer viewer_id, @PathVariable String mobileNo) {
		try {
			DialogOtpDto dialogOtoDto = otpService.sendOtp(viewer_id, mobileNo);
			System.out.println(dialogOtoDto.getServerRef());
			
			return new ResponseEntity<Object>(dialogOtoDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/confirm", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<Object> pin_subscription_confirm(@RequestBody DialogOtpConfirmDto dialogOtpConfirmDto,
			HttpServletRequest request) {
		System.out.println(dialogOtpConfirmDto.toString());
		try {
			return new ResponseEntity<Object>(otpService.confirm(dialogOtpConfirmDto), HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("error : " + e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		

	}
}
