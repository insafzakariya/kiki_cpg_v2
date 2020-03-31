package com.kiki_cpg.development.service;

public interface PaymentLogService {

	public void createPaymentLog(String serviceProvider,String responseNo,String responseMsg,int viwerId,String mobileNo,String serverResponse);
	
}
