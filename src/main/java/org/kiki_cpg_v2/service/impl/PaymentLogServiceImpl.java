package org.kiki_cpg_v2.service.impl;

import java.util.Date;

import org.kiki_cpg_v2.entity.PaymentLogEntity;
import org.kiki_cpg_v2.repository.PaymentLogRepository;
import org.kiki_cpg_v2.service.PaymentLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentLogServiceImpl implements PaymentLogService {

	@Autowired
	PaymentLogRepository paymentLogRepository;

	@Override
	public void createPaymentLog(String serviceProvider, String responseNo, String responseMsg, int viwerId,
			String mobileNo, String serverResponse) {
		try {
			Date now = new Date();
			PaymentLogEntity paymentLog = new PaymentLogEntity();
			paymentLog.setServiceProvider(serviceProvider);
			paymentLog.setResponseNo(responseNo);
			paymentLog.setResponseMsg(responseMsg);
			paymentLog.setViwerId(viwerId);
			paymentLog.setMobileNo(mobileNo);
			paymentLog.setCreatedDate(now);
			paymentLog.setServerResponse(serverResponse);
			paymentLogRepository.save(paymentLog);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
