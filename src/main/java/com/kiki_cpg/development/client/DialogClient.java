package com.kiki_cpg.development.client;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface DialogClient {

	String dialog_payment_confirm(String server_ref, String mobile_no, Double amount, Integer subscribed_days,
			int viwerId);

	String create_access_token();
	
	HashMap<String, String> pin_subscription_confirm(Map<String, String> userMap, HttpServletRequest request);

}
