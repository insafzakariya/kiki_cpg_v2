package com.kiki_cpg.development.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kiki_cpg.development.service.ScratchCardCodesService;


@CrossOrigin("")
@RestController("kiki-cpg/api/v1/scratchCard")
public class DialogScratchCardController {
	
	@Autowired
	ScratchCardCodesService scratchCardCodeService;
	
	private static final Logger logger = LoggerFactory.getLogger(DialogScratchCardController.class);
	
	@RequestMapping(value = { "/payment" }, method = RequestMethod.POST)
	public String validateAndUseCode(@RequestParam("card_code") String cardCode, ModelMap model,
			HttpServletRequest request) {
		//scratchCardCodeService.setPayment(cardCode,);
		return "Success";
	}
}
