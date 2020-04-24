package com.kiki_cpg.development.controller.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kiki_cpg.development.client.DialogClient;
import com.kiki_cpg.development.dto.DialogOtpDto;
import com.kiki_cpg.development.util.AppUtility;

@RestController
@CrossOrigin("*")
@RequestMapping("/otp")
public class OtpControllerRest {

	@Autowired
	private AppUtility appUtility;

	@Autowired
	private DialogClient dialogClient;

	@GetMapping("/send/{mobile_no}/{day}")
	public ResponseEntity<Object> sentOtp(@PathVariable String mobile_no, @PathVariable Integer day) {

		try {
			boolean isdialog = appUtility.getIsDialogNumber(mobile_no);
			String access_token = dialogClient.create_access_token();
			
			HashMap<String, String> result_obj = new LinkedHashMap<String, String>();
			DialogOtpDto dialogOtoDto = dialogClient.sendOtp(mobile_no, day, access_token);
			System.out.println(dialogOtoDto.getServerRef());
			
			if (isdialog) {
				result_obj.put("OTP_SEND", dialogOtoDto.getStatusCode());
				result_obj.put("SERVER_REF", dialogOtoDto.getServerRef());
				result_obj.put("MSISDN", dialogOtoDto.getMsisdn());
				result_obj.put("SERVICE_ID", dialogOtoDto.getServiceId());
				result_obj.put("ACCESS_TOKEN", access_token);
				result_obj.put("IS_DIALOG", "dialog");
			} else {
				result_obj.put("OTP_SEND", dialogOtoDto.getStatusCode());
				result_obj.put("IS_DIALOG", "none");
			}
			return new ResponseEntity<Object>(result_obj, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
