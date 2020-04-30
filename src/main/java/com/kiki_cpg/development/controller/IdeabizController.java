package com.kiki_cpg.development.controller;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kiki_cpg.development.dto.SubscriptionPaymentDto;
import com.kiki_cpg.development.service.IdeabizService; 

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin("")
@RestController()
@RequestMapping("kiki-cpg/api/v1/ideabiz")
public class IdeabizController {

	private static final Logger logger = LoggerFactory.getLogger(IdeabizController.class);

	@Autowired
	IdeabizService ideabizService;
	
	@RequestMapping(value = "/ideabiz/create_access_token", method = RequestMethod.POST)
	@ResponseBody
	public String create_access_token() {
		String token = ideabizService.create_access_token();
		return token;
	}

	@RequestMapping(value = "/ideabiz/genarate_authorization_code", method = RequestMethod.GET)
	@ResponseBody
	private String genarate_authorization_code() {
		String authorization_code = ideabizService.genarate_authorization_code();
		return authorization_code;

	}

	@RequestMapping(value = "/ideabiz/pin_subscription", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public HashMap<String, String> pinSubscription(@RequestParam String mobile_no,
			@RequestParam String subscriptionPaymentId, @RequestParam String day) {
		
		HashMap<String, String> result_obj = null;
		try {
			result_obj = ideabizService.pinSubcription(mobile_no, subscriptionPaymentId, day);
		} catch (Exception e) {
			e.printStackTrace();
		}

		

		return result_obj;
	}

	@RequestMapping(value = "/ideabiz/pin_subscription_confirm", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<Object> pin_subscription_confirm(@RequestBody Map<String, String> userMap,
			HttpServletRequest request) {
		SubscriptionPaymentDto subscriptionPaymentDto = (SubscriptionPaymentDto) request.getSession()
				.getAttribute("subscriptionPaymentDto");
		
		 String systemToken = (String) request.getSession().getAttribute("token");
		try {
			HashMap<String, String> result_obj = ideabizService.pinSubscriptionConfirm(userMap, subscriptionPaymentDto, systemToken);
			return new ResponseEntity<Object>(result_obj, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
