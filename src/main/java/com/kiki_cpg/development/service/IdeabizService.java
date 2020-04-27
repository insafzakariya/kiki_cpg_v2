package com.kiki_cpg.development.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kiki_cpg.development.dto.SubscriptionPaymentDto;
import com.kiki_cpg.development.dto.ViewerUnsubcriptionDto;
import com.kiki_cpg.development.entity.Ideabiz;
import com.kiki_cpg.development.entity.Viewers;

public interface IdeabizService {

	public void unsubscribe(int viwerId);

	public void addIdabiz_subscribe(Integer viewerId, String mobile_no, Integer days);

	Ideabiz findOneByViwerIdAndSubscribe(Integer viewerID, int i);
	
	public int proceedPayment(Viewers viewers, Integer subscribedDays, String serviceId,
			Double amount);
	
	public String paymentConfirm(String serverRef, String mobileNo, Double amount, Integer subscribedDay,
			Viewers viewers);
	
	public String create_access_token();
	
	public String genarate_authorization_code();
	
	//public HashMap<String, String> pin_subscription_confirm(Map<String, String> data,SubscriptionPaymentDto subscriptionPaymentDto, String systemToken) throws Exception;
	
	public Viewers get_viwer_id_by_sID(Integer subscriptionPaymentId);
	
	public String idabiz_subscribe(Integer viewerId, String mobile_no, Integer days);

	public HashMap<String, String> pinSubscriptionConfirm(Map<String, String> userMap,
			SubscriptionPaymentDto subscriptionPaymentDto, String systemToken) throws Exception;

	Ideabiz getIdeabiz(Integer viewerId, String mobileNo, Integer day);

	ViewerUnsubcriptionDto getViewerUnsubcriptionDto(String mobileNo, Integer viewerId);

}
