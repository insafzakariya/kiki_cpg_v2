package com.kiki_cpg.development.service.impl;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiki_cpg.development.client.DialogClient;
import com.kiki_cpg.development.controller.IdeabizController;
import com.kiki_cpg.development.dto.ViewerUnsubcriptionDto;
import com.kiki_cpg.development.entity.Ideabiz;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class IdeabizServiceImpl implements IdeabizService {

	@Autowired
	ViewerRepository viewerRepository;

	@Autowired
	InvoiceService invoiceService;

	@Autowired
	PaymentLogService paymentLogService;

	@Autowired
	ContentRepository contentRepository;

	@Autowired
	IdeabizConfigRepository ideabizConfigRepository;

	@Autowired
	IdeabizRepository ideabizRepository;

	@Autowired
	IdeabizService ideabizService;

	@Autowired
	OTPService otpService;

	@Autowired
	PaymentCalculation paymentCalculation;

	@Autowired
	PaymentService paymentService;

	@Autowired
	ViewerService viewerService;

	@Autowired
	ViewerUnsubcriptionService viewerUnsubcriptionService;

	@Autowired
	DialogClient dialogClient;

	private static final Logger logger = LoggerFactory.getLogger(IdeabizServiceImpl.class);

	@Override
	public void unsubscribe(int viwerId) {
		List<Ideabiz> ideabizOptional = ideabizRepository.getByViwer_id(viwerId);
		Ideabiz ideabiz = ideabizOptional.get(0);
		ideabiz.setSubscribe(0);
		ideabizRepository.save(ideabiz);
	}

	@Override
	public void addIdabiz_subscribe(Integer viewerId, String mobile_no, Integer days) {
		// TODO Auto-generated method stub
		Date date = new Date();
		long time = date.getTime();
		Timestamp ts = new Timestamp(time);

		Ideabiz ideabiz = new Ideabiz();
		ideabiz.setMobile(mobile_no);
		ideabiz.setViwerId(viewerId);
		ideabiz.setCreatedDate(ts);
		ideabiz.setSubscribe(1);
		ideabiz.setSubscribedDays(days);
		ideabizRepository.save(ideabiz);
	}

	@Override
	public Ideabiz findOneByViwerIdAndSubscribe(Integer viewerID, int i) {
		Ideabiz ideabiz = ideabizRepository.findOneByViwerIdAndSubscribe(viewerID, i);
		return ideabiz;
	}

	@Override
	public int proceed_payment(Integer viwer_id, Integer subscribed_days, String serviceId, Double amount) {
		try {

			Viewers viewers = viewerRepository.findByViewerId(viwer_id);
			System.out.println("viewer id " + viewers.getViewerId());

			int invoice_id = invoiceService.create(serviceId, viewers, subscribed_days, amount);
			System.out.println("Invoice created " + invoice_id);

//			viewerId = viewers.getViewerId();
//			invoiceId = invoice_id;
//			chargeAmount = amount;
			// cron_start_time = cronStartTime;

			String paid = payment_confirm(String.valueOf(invoice_id), viewers.getMobileNumber(), amount,
					subscribed_days, viewers.getViewerId());
			System.out.println("check paid : " + paid);
			if (paid.equals("Sucess")) {
				invoiceService.updateInvoice(invoice_id, 1);

				if (subscribed_days == 1) {
					contentRepository.updateViewerPolicies(viewers.getViewerId(), 81, false);

				} else if (subscribed_days == 7) {
					contentRepository.updateViewerPolicies(viewers.getViewerId(), 106, false);
				}

				return invoice_id;
			} else {
				return 0;
			}
		} catch (Exception e) {

		}
		return 0;
	}

	@Override
	public String payment_confirm(String server_ref, String mobile_no, Double amount, Integer subscribed_days,
			int viwerId) {
		// TODO Auto-generated method stub

		String paid = dialogClient.dialog_payment_confirm(server_ref, mobile_no, amount, subscribed_days, viwerId);
		return paid;
	}

	@Override
	public String create_access_token() {

		String token = dialogClient.create_access_token();
		return token;
	}

	@Override
	public String genarate_authorization_code() {
		byte[] encodedBytes = Base64
				.encodeBase64("JVy3ytG_43x9rDEpJ8Uhj5WzzXUa:9ITz4oPXhD8doL0pYQE4Hz297_ga".getBytes());
		String authorization_code = new String(encodedBytes);
		return "Basic " + authorization_code;
	}

	@Override
	public HashMap<String, String> pin_subscription_confirm(Map<String, String> userMap, HttpServletRequest request) {
		HashMap<String, String> result_obj = dialogClient.pin_subscription_confirm(userMap, request);
		return result_obj;
	}

	@Override
	public Viewers get_viwer_id_by_sID(Integer subscriptionPaymentId) {

		SubscriptionPayments subPay = paymentService.getSubscriptionPayments(subscriptionPaymentId);
		if (subPay != null) {
			Viewers viewer = viewerService.getViewerByid(subPay.getViewerID());
			return viewer;
		}
		return new Viewers();
	}

	@Override
	public String idabiz_subscribe(Viewers viwer, String mobile_no, Integer days) {
		mobile_no = mobile_no.replace("tel:+", "");
		ideabizService.addIdabiz_subscribe(viwer.getViewerId(), mobile_no, days);
		return "success";
	}
}
