package org.kiki_cpg_v2.service;

public interface PaymentLogService {

	void createPaymentLog(String serviceProvider,String responseNo,String responseMsg,int viwerId,String mobileNo,String serverResponse);

}
