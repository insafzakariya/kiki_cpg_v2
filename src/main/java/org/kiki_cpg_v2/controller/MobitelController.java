package org.kiki_cpg_v2.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.kiki_cpg_v2.dto.ResponseMapDto;
import org.kiki_cpg_v2.dto.request.MobitelActivationDto;
import org.kiki_cpg_v2.service.MobitelService;
import org.kiki_cpg_v2.service.SubscriptionPaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin("*")
@RestController()
@RequestMapping("/mobitel")
public class MobitelController {
	final static Logger logger = LoggerFactory.getLogger(MobitelController.class);
	
	@Autowired
	private MobitelService mobitelService;
	
	@Autowired
	private SubscriptionPaymentService subscriptionService;
	
	@PostMapping(value = "/payment")
	public ResponseEntity<Object> activateDataBundle(@RequestBody MobitelActivationDto mobitelActivationDto, HttpServletRequest request){
		logger.info("called Payment : " + mobitelActivationDto.toString());
		try{
			ResponseMapDto dto = new ResponseMapDto();
			boolean isValied = subscriptionService.validateSubscriptionPayment(mobitelActivationDto.getSubscriptionPaymentId());
			if(isValied) {
				String resp = mobitelService.pay(mobitelActivationDto.getMobileNo(), mobitelActivationDto.getViewerId(), mobitelActivationDto.getActivationStatus(),mobitelActivationDto.getSubscriptionPaymentId(), mobitelActivationDto.getDays());
				logger.info("response : " + resp);
				
				dto.setStatus(resp);
				return new ResponseEntity<Object>(dto, HttpStatus.OK);
			} else {
				dto.setStatus("Subscription Token Expired");
				return new ResponseEntity<Object>(dto, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/concent", method = RequestMethod.POST)
    public ResponseEntity<?> mobitelConcent(@RequestBody Map<String, Map<String, String>> requestBody,
                                            HttpServletRequest request, ModelMap model) {

        Map<String, String> responseHeader = new HashMap<>();
        responseHeader.put("responseCode", "0500");
        responseHeader.put("transactionId", "9999");
        responseHeader.put("responseDesc", "update failed");
        Map<String, Map<String, String>> returnMap = new HashMap<>();

        try {

            returnMap.put("responseHeader", responseHeader);
            /// get data from requestBody
            Map<String, String> customer = requestBody.get("customer");
            Map<String, String> requestHeader = requestBody.get("requestHeader");
            responseHeader.put("transactionId", requestHeader.get("transactionId"));
            // headers.add("Location", "https://payment.kiki.lk/susilawebpay/success");
            // response.sendRedirect("https://payment.kiki.lk/susilawebpay/success");
            // ResponseEntity<Map<String,Map<String,String>>> res = new ResponseEntity<Map<String, Map<String, String>>>();
            if (customer.get("customerConcent").equals("GIVEN")) {
                logger.info("Customer concent updating....");
                // String trasactionToken = globleToken;

                responseHeader.put("responseCode", "1111");
                responseHeader.put("responseDesc", "updated successfully");
            } else {

                logger.info("Customer concent updating unsuccessful");

            }

        } catch (Exception e) {// uri exception, null exception and io exceptions
            logger.info("Customer concent updating unsuccessful");
            logger.info("Exception =" + e);
        }
        return new ResponseEntity<Map<String, Map<String, String>>>(returnMap, HttpStatus.OK);
    }

	
}
