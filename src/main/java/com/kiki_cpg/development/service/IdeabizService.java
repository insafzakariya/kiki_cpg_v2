package com.kiki_cpg.development.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kiki_cpg.development.entity.Ideabiz;
import com.kiki_cpg.development.entity.Viewers;

public interface IdeabizService {

	public void unsubscribe(int viwerId);

	public void addIdabiz_subscribe(Integer viewerId, String mobile_no, Integer days);

	Ideabiz findOneByViwerIdAndSubscribe(Integer viewerID, int i);
	
	public int proceed_payment(Integer viwer_id, Integer subscribed_days, String serviceId,
			Double amount);
	
	public String payment_confirm(String server_ref, String mobile_no, Double amount, Integer subscribed_days,
			int viwerId);
	
	public String create_access_token();
	
	public String genarate_authorization_code();
	
	public HashMap<String, String> pin_subscription_confirm(Map<String, String> userMap,HttpServletRequest request);
	
	public Viewers get_viwer_id_by_sID(Integer subscriptionPaymentId);
	
	public String idabiz_subscribe(Viewers viwer, String mobile_no, Integer days);
}
