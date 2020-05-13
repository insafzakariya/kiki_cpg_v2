package com.kiki_cpg.development.controller.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kiki_cpg.development.dto.PackageDto;
import com.kiki_cpg.development.dto.SubscriptionPaymentDto;
import com.kiki_cpg.development.service.SubscriptionPaymentService;

@RestController()
@RequestMapping("/rest/home")
@CrossOrigin(origins = "*")
public class MainControllerRest {

	final static Logger logger = LoggerFactory.getLogger(MainControllerRest.class);

	@Autowired
	private SubscriptionPaymentService subscriptionPaymentService;

	@GetMapping("/initialize")
	public ResponseEntity<Object> homePage(HttpServletRequest request) throws IOException {
		System.out.println("called");

		HttpSession session = request.getSession();
		
		String paymentToken = (String) session.getAttribute("paymentToken");
		
		System.out.println(paymentToken);
		
		String type = (String) session.getAttribute("type");
		
		logger.info("susilawebpay application started.");

		try {
			SubscriptionPaymentDto subscriptionPaymentDto = subscriptionPaymentService
					.getSubscriptionPaymentByTokenRest(paymentToken, type);
			session.setAttribute("subscriptionPaymentDto", subscriptionPaymentDto);
			if (subscriptionPaymentDto != null) {
				return new ResponseEntity<Object>(subscriptionPaymentDto, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(subscriptionPaymentDto, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("500", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GetMapping("/planlist/{paymentMethodId}")
	public List<PackageDto> getPaymentListDto( @PathVariable Integer paymentMethodId) {
		System.out.println("paymentMethodId" + paymentMethodId);
		List<PackageDto> packageDtos = subscriptionPaymentService.getPaymentPlan(paymentMethodId);
		return packageDtos;
	}
}
