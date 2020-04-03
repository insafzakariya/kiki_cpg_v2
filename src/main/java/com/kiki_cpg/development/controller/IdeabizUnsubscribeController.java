package com.kiki_cpg.development.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kiki_cpg.development.dto.ViewerUnsubcriptionDto;
import com.kiki_cpg.development.dto.ViewersDto;
import com.kiki_cpg.development.entity.Ideabiz;
import com.kiki_cpg.development.entity.Viewers;
import com.kiki_cpg.development.service.IdeabizService;
import com.kiki_cpg.development.service.ViewerService;
import com.kiki_cpg.development.service.ViewerUnsubcriptionService;

@CrossOrigin("")
@RestController("kiki-cpg/api/v1/ideabiz/unsubscribe")
public class IdeabizUnsubscribeController {

	@Autowired
	ViewerService viewerService;
	
	@Autowired
	IdeabizService ideabizService;
	
	@Autowired
	IdeabizController ideabizController;
	
	@Autowired
	ViewerUnsubcriptionService viewerUnsubService;
	
	@RequestMapping(value="/ideabiz/ideabiz_unsubscribe",method=RequestMethod.POST,produces = "application/json")
	@ResponseBody
	public boolean ideabiz_unsubscribe(@RequestParam Integer viewerID) {
		boolean res=false;
		
//		String token=userMap.get("token");
//		SubscriptionPayments subscriptionPayment = paymentService.validatePaymentToken(token);
//		ViewerModel viewerModel=new ViewerModel();
//		Viewers viewer = viewerModel.getViewerFromID(subscriptionPayment.getViewerId());
//		IdeabizModel ideabizModel=new IdeabizModel();
//		Ideabiz ideabiz=ideabizModel.get_ideabiz_subscrption_by_viwerID(subscriptionPayment.getViewerId(), 1);
		
		Viewers viewers=viewerService.getViewerByid(viewerID);
		Ideabiz ideabiz=ideabizService.findOneByViwerIdAndSubscribe(viewerID, 1);
		
		String url = "https://ideabiz.lk/apicall/subscription/v3/unsubscribe";
		
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post =new HttpPost(url);
	
		post.setHeader("content-type","application/json");
		
		String access_token=ideabizController.create_access_token();
		post.setHeader("Authorization",access_token);	
		
		JSONObject json = new JSONObject();

		try {
			String tel="tel:"+viewers.getMobileNumber();
			System.out.println(tel);
			json.put("method", "WEB");
			json.put("msisdn", tel);
			System.out.println("ussubscribe==="+ideabiz.getSubscribedDays());
			if (ideabiz.getSubscribedDays()==1) {
				json.put("serviceID", "bf110848-23ca-4b7d-8a3f-872b59bfd32e");
				System.out.println("SUB"+ideabiz.getSubscribedDays());
			} else if(ideabiz.getSubscribedDays()==7) {
				System.out.println("sdhchsd"+ideabiz.getSubscribedDays());
				json.put("serviceID","f0ce6a27-7aca-4e12-b121-25eeee7a840a");
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
			JSONObject jsonObj = new JSONObject(result.toString());		
			System.out.println(jsonObj);
			if (jsonObj.get("statusCode").equals("SUCCESS")) {
				ideabizService.unsubscribe(viewers.getViewerId());
				
				ViewerUnsubcriptionDto dto = new ViewerUnsubcriptionDto();
				dto.setCreatedDateTime(new Date());
				dto.setLastUpdatedTime(new Date());
				dto.setMobileNumber(viewers.getMobileNumber());
				dto.setViewerId(viewers.getViewerId());
				dto.setSubcriptionType("UNSUBCRIBE");
				dto.setServiceProvider("Dialog");
				viewerUnsubService.addViewerUnsubcription(dto);
				
				res=true;
			} else {
				res=false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
}
