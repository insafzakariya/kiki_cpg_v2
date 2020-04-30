package com.kiki_cpg.development.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kiki_cpg.development.service.MobitelWsClientService;
import com.kiki_cpg.development.util.AppUtility;

@CrossOrigin("*")
@RestController()
@RequestMapping("kiki-cpg/api/v1/mobitel")
public class MobitelController {

	@Autowired
	MobitelWsClientService mobitelWsClientService;
	
	
	@RequestMapping(value = "/mobitel/payment", method = RequestMethod.POST)
	public String activateDataBundle(String mobileNo, int viewerId, String activationStatus){
		String resp = mobitelWsClientService.activateDataBundle(mobileNo, viewerId, activationStatus);
		return null;
	}
}
