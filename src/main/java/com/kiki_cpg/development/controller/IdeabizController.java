package com.kiki_cpg.development.controller;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.hibernate.internal.CriteriaImpl.Subcriteria;
//import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.util.LinkedHashMap;

import com.kiki_cpg.development.dto.CronDto;
import com.kiki_cpg.development.dto.PaymentTypeDto;
import com.kiki_cpg.development.dto.ViewerUnsubcriptionDto;
import com.kiki_cpg.development.entity.IdeabizConfig;
import com.kiki_cpg.development.entity.SubscriptionPayments;
import com.kiki_cpg.development.entity.Viewers;
import com.kiki_cpg.development.repository.ContentRepository;
import com.kiki_cpg.development.repository.IdeabizConfigRepository;
import com.kiki_cpg.development.repository.IdeabizRepository;
import com.kiki_cpg.development.repository.ViewerRepository;
import com.kiki_cpg.development.service.IdeabizService;
import com.kiki_cpg.development.service.InvoiceService;
import com.kiki_cpg.development.service.OTPService;
import com.kiki_cpg.development.service.PaymentCalculation;
import com.kiki_cpg.development.service.PaymentLogService;
import com.kiki_cpg.development.service.PaymentService;
import com.kiki_cpg.development.service.ViewerService;
import com.kiki_cpg.development.service.ViewerUnsubcriptionService;
import com.kiki_cpg.development.util.AppUtility;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@CrossOrigin("")
@RestController("kiki-cpg/api/v1/ideabiz")
public class IdeabizController {

	@Autowired
	IdeabizService ideabizService;

	@Autowired
	PaymentCalculation paymentCalculation;

	@Autowired
	ViewerUnsubcriptionService viewerUnsubcriptionService;

	@Autowired
	private AppUtility appUtility;
	
	// Payment Proceed
	private Integer viewerId = null;
	private Integer invoiceId = null;
	private Double chargeAmount = null;
	private String cron_start_time = null;
	private String paymentStatus = null;
	private String cronType = null;
	private String responseMsg = null;
	private String serverResponse = null;
	private Date responseDateAndTime = null;

	private static final Logger logger = LoggerFactory.getLogger(IdeabizController.class);

	public int proceed_payment(Integer viwer_id, Integer subscribed_days, String serviceId,
			Double amount) {
		int invoice_id=ideabizService.proceed_payment(viwer_id, subscribed_days, serviceId, amount);	
		return invoice_id;
	}

	@RequestMapping(value = "/ideabiz/payment", method = RequestMethod.GET)
	@ResponseBody
	private String payment_confirm(String server_ref, String mobile_no, Double amount, Integer subscribed_days,
			int viwerId) {

		ideabizService.payment_confirm(server_ref, mobile_no, amount, subscribed_days, viwerId);
		return "Paid";

	}

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

	private CronDto addCronReport(CronDto cronDto) {
		return cronDto;
	}
	
	@RequestMapping(value="/ideabiz/pin_subscription",method=RequestMethod.POST,produces = "application/json")
	@ResponseBody
	public HashMap<String, String> pin_subscription(@RequestParam String mobile_no,@RequestParam String subscriptionPaymentId,@RequestParam String day) {
		
		
		String statusCode="FAIL";
		String serverRef="";
		String msisdn="";
		String serviceId="";
		String url = "https://ideabiz.lk/apicall/pin/subscription/v1/subscribe";
		String is_dialog="dialog";
		
		boolean isdialog=appUtility.getIsDialogNumber(mobile_no);
		HashMap<String, String> result_obj = new LinkedHashMap<String, String>();
		if (isdialog) {
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post =new HttpPost(url);
			
			// add header
			post.setHeader("content-type","application/json");
			
			String access_token=create_access_token();		
			post.setHeader("Authorization",access_token);	

			try {
				System.out.println("start");
				JSONObject json = new JSONObject();
				
				//Body
				json.put("method", "mobilevisions");
				json.put("msisdn", mobile_no);
				System.out.println("DAY"+day);
				if (Integer.parseInt(day)==1) {
					json.put("serviceId", "BF110848-23CA-4B7D-8A3F-872B59BFD32E");
				} else if(Integer.parseInt(day)==7){
					json.put("serviceId", "f0ce6a27-7aca-4e12-b121-25eeee7a840a");
				}
				
				StringEntity params = new StringEntity(json.toString());
				
				post.setEntity(params);
				HttpResponse response = client.execute(post);
				
				System.out.println("Response  : " 
		                + response.getEntity().getContent());
				
				BufferedReader rd = new BufferedReader(
				        new InputStreamReader(response.getEntity().getContent()));
				StringBuffer result = new StringBuffer();
				String line = "";
				while ((line = rd.readLine()) != null) {				
					result.append(line);
				}
				System.out.println(result);
				JSONObject jsonObj = new JSONObject(result.toString());
				JSONObject jsonObjRef=(JSONObject)jsonObj.get("data");
				
				statusCode=jsonObj.get("statusCode").toString();
				serverRef=jsonObjRef.get("serverRef").toString();
				msisdn=jsonObjRef.get("msisdn").toString();
				serviceId=jsonObjRef.get("serviceId").toString();
					
				System.out.println(serverRef);
			} catch (Exception e) {
				logger.info(e.getMessage());
				// TODO: handle exception
			}
			System.out.println(statusCode);
			
			result_obj.put("OTP_SEND", statusCode);
			result_obj.put("SERVER_REF", serverRef);
			result_obj.put("MSISDN", msisdn);
			result_obj.put("SERVICE_ID", serviceId);
			result_obj.put("ACCESS_TOKEN", access_token);
			result_obj.put("IS_DIALOG", is_dialog);
		} else {
			is_dialog="none";
			result_obj.put("OTP_SEND", statusCode);
			result_obj.put("IS_DIALOG", is_dialog);
		}
		
		
		return result_obj;
	}
	

	@RequestMapping(value = "/ideabiz/pin_subscription_confirm", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public HashMap<String, String> pin_subscription_confirm(@RequestBody Map<String, String> userMap,
			HttpServletRequest request) {
		HashMap<String, String> result_obj = ideabizService.pin_subscription_confirm(userMap, request);
		return result_obj;
	}

}
