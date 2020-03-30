package com.kiki_cpg.development.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiki_cpg.development.entity.PaymentLog;
import com.kiki_cpg.development.repository.PaymentLogRepository;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class PaymentLogService {
    @Autowired
    PaymentLogRepository paymentLogRepository;

    public void createPaymentLog(String serviceProvider,String responseNo,String responseMsg,int viwerId,String mobileNo,String serverResponse) {
       try {
    	Date now = new Date();
        PaymentLog paymentLog=new PaymentLog();
        paymentLog.setServiceProvider(serviceProvider);
        paymentLog.setResponseNo(responseNo);
        paymentLog.setResponseMsg(responseMsg);
        paymentLog.setViwerId(viwerId);
        paymentLog.setMobileNo(mobileNo);
        paymentLog.setCreated_date(now);
        paymentLog.setServerResponse(serverResponse);
        paymentLogRepository.save(paymentLog);
		} catch (Exception e) {
		// TODO: handle exception
	}
       
    }
}
