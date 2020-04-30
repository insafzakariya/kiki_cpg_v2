package com.kiki_cpg.development.service.impl;

import com.google.gson.JsonObject;
import com.kiki_cpg.development.client.MobitelClient;
import com.kiki_cpg.development.entity.MerchantAccount;
import com.kiki_cpg.development.entity.ViewerSubscription;
import com.kiki_cpg.development.entity.ViewerTrialPeriodAssociation;
import com.kiki_cpg.development.entity.Viewers;
import com.kiki_cpg.development.enums.TransactionType;
import com.kiki_cpg.development.repository.MerchantAccountRepository;
import com.kiki_cpg.development.repository.TrialPeriodRepository;
import com.kiki_cpg.development.repository.ViewerRepository;
import com.kiki_cpg.development.repository.ViewerSubscriptionRepository;
import com.kiki_cpg.development.service.ContentService;
import com.kiki_cpg.development.service.MobitelService;
import com.kiki_cpg.development.service.MobitelWsClientService;
import com.kiki_cpg.development.service.OTPService;
import com.kiki_cpg.development.service.PaymentLogService;

import lk.mobitel.esms.message.SMSManager;
import lk.mobitel.esms.session.NullSessionException;
import lk.mobitel.esms.session.SessionManager;
import lk.mobitel.esms.ws.Alias;
import lk.mobitel.esms.ws.SmsMessage;
import lk.mobitel.esms.ws.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MobitelWsClientServiceImpl implements MobitelWsClientService {
	@Autowired
	ViewerRepository viewerRepository;

	@Autowired
	ViewerSubscriptionRepository viewerSubscriptionRepository;

	@Autowired
	TrialPeriodRepository trialPeriodRepository;

	@Autowired
	MerchantAccountRepository merchantAccountRepository;

	@Autowired
	PaymentLogService paymentLogService;

	@Autowired
	ContentService contentService;

	@Autowired
	OTPService otpService;

	@Autowired
	private MobitelClient mobitelClient;

	// Payment Proceed
	private Integer viewer_id = null;
	private Integer invoiceId = null;
	private Double chargeAmounts = null;
	private String cron_start_time = null;
	private String paymentStatus = null;
	private String cronType = null;
	private String responseMsg = null;
	private String serverResponse = null;
	private Date responseDateAndTime = null;
	Integer transactionId = 460000;
	private List<ViewerSubscription> viewerSubscriptions = new ArrayList<>();
	private List<ViewerSubscription> viewerSubscriptionList = new ArrayList<>();

	private static final Logger logger = LoggerFactory.getLogger(MobitelWsClientServiceImpl.class);

	public static String mobitelKey = "TW9iaXRlbFBheW1lbnRTZWNyZXRLZXk=";

	@Override
	public void midnightTaskToUpdatePoliciess(String startTime, boolean isSecondTime,
			List<ViewerSubscription> viewerSubscriptionList, List<Viewers> viewersList, Integer cronId)
			throws Exception {

		try {
			String ipAddress = "";
			try {
				InetAddress inetAddress = InetAddress.getLocalHost();
				ipAddress = inetAddress.getHostAddress();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}

			logger.info("charge from mobitel users has started");
			System.out.println("charge from mobitel users has started");
			int transactioncount = 0;

			int totalSubscribedUser = viewersList.size();

			otpService.sendMsg("+94773799390", "Mobitel Pending Subscribtion Count Is :" + totalSubscribedUser
					+ " -Cron Started :" + startTime + " -Server Ip : " + ipAddress);
			otpService.sendMsg("+94779922990", "Mobitel Pending Subscribtion Count Is :" + totalSubscribedUser
					+ " -Cron Started :" + startTime + " -Server Ip : " + ipAddress);
			otpService.sendMsg("+94771485821", "Mobitel Pending Subscribtion Count Is :" + totalSubscribedUser
					+ " -Cron Started :" + startTime + " -Server Ip : " + ipAddress);
			otpService.sendMsg("+94767072477", "Mobitel Pending Subscribtion Count Is :" + totalSubscribedUser
					+ " -Cron Started :" + startTime + " -Server Ip : " + ipAddress);

			for (Viewers viewer : viewersList) {
				if (isTrialPeriodActivated(viewer.getViewerId())) {
					continue;
				}
				viewer_id = viewer.getViewerId();
				cron_start_time = startTime;
				cronType = "Mobitel";

				String msisdn = viewer.getMobileNumber();
				msisdn = createMsisdnForDataBundle(msisdn);
				String resultCode = activateDataBundle(msisdn, viewer.getViewerId(), "1");
				paymentLogService.createPaymentLog("Mobitel", resultCode.toString(), "-", viewer.getViewerId(),
						viewer.getMobileNumber(), "");
				transactioncount = transactioncount + 1;
				if (resultCode.equals("1000")) { // operation successful
					// 101 is the mobitel package if the current package is also 101 the it will
					// extend the duration
					contentService.updateViewerPolicies(viewer.getViewerId(), 81, false);

				} else { // if payment is not successful

					contentService.updateViewerPolicies(viewer.getViewerId(), 1, false);

				}
//				Thread.sleep(3000);
			}

			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

			otpService.sendMsg("+94773799390", "Mobitel Cron Done Subscribtion Count Is " + transactioncount + " / "
					+ totalSubscribedUser + " -Cron Stoped :" + sdf.format(date) + " -Server Ip : " + ipAddress);
			otpService.sendMsg("+94779922990", "Mobitel Cron Done Subscribtion Count Is " + transactioncount + " / "
					+ totalSubscribedUser + " -Cron Stoped :" + sdf.format(date) + " -Server Ip : " + ipAddress);
			otpService.sendMsg("+94771485821", "Mobitel Cron Done Subscribtion Count Is " + transactioncount + " / "
					+ totalSubscribedUser + " -Cron Stoped :" + sdf.format(date) + " -Server Ip : " + ipAddress);
			otpService.sendMsg("+94767072477", "Mobitel Cron Done Subscribtion Count Is " + transactioncount + " / "
					+ totalSubscribedUser + " -Cron Stoped :" + sdf.format(date) + " -Server Ip : " + ipAddress);

		} catch (Exception e) {

		}

	}

	@Override
	public boolean isTrialPeriodActivated(Integer viewerId) {
		ViewerTrialPeriodAssociation viewerTrialPeriodAssociation = trialPeriodRepository
				.getOnGoingViewerTrialPeriodAssociation(viewerId);
		if (viewerTrialPeriodAssociation == null) {
			return false;
		}
		Date date = new Date();
		Calendar today = Calendar.getInstance();
		today.setTime(date);
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);
		if (viewerTrialPeriodAssociation.getEndDate().getTime() >= today.getTime().getTime()) {
			return true;
		} else {
			trialPeriodRepository.updateViewerTrialPeriodAssociationOnGoingStatus(viewerId, false);
			return false;
		}
	}

	@Override
	public String createMsisdnForDataBundle(String number) {
		try {
			String prefix = "0";
			number = prefix + number.substring(number.length() - 9, number.length());
			return number;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public String activateDataBundle(String mobileNo, int viewerId, String activationStatus) {
		try {

			ResponseEntity<?> res = mobitelClient.createAccessCode();
			String returnValue = "0002";

			int lastTransaciontId = 0;
			try {
				LinkedHashMap<String, String> response = (LinkedHashMap<String, String>) res.getBody();
				lastTransaciontId = merchantAccountRepository.getLastTransactionId();

				while (returnValue.equals("0002")) {
					ResponseEntity<?> resp = mobitelClient.mobitelManage(response.get("access_token"), activationStatus,
							mobileNo, lastTransaciontId);
					LinkedHashMap<String, String> response2 = (LinkedHashMap<String, String>) resp.getBody();
					returnValue = response2.get("resultCode");
				}

			} catch (Exception e) {
				logger.info("activation unsuccessful error occurred with transaction id  " + lastTransaciontId
						+ "Exception = " + e);
				logger.info(e.getMessage());
			}
			double amount = 5;
			//TransactionType transactionType = TransactionType.ACTIVATE;
			if (activationStatus.equals("2")) {
				//transactionType = TransactionType.DEACTIVATE;
				amount = 0;
			}
			if (returnValue.equals("1000")) { // add record to merchant account table
				System.out.println("add record to merchant account table ");
				MerchantAccount merchantAccount = new MerchantAccount();
				merchantAccount.setId(0);
				merchantAccount.setServiceId("KIKI");
				merchantAccount.setTransactionId(lastTransaciontId);
				merchantAccount.setAmount(amount);
				merchantAccount.setDate(new Date());
				merchantAccount.setViewerId(viewerId);
				merchantAccount.setSuccess(true);
				merchantAccount.setTransactionType(TransactionType.ACTIVATE);
				merchantAccountRepository.save(merchantAccount);
				paymentStatus = "Susccess";
				responseMsg = "Activation Successful";
				// merchantAccountRepository.addMerchantAccountDetails("KiKi",lastTransaciontId,
				// new Date(), amount, viewerId, true, transactionType);
			} else {
				logger.info("activation unsuccessful error occurred with transaction id " + lastTransaciontId);
				System.out.println("Activation Unsuccessful Error Occurred With Transaction Id " + lastTransaciontId);
				logger.info("viewer id " + viewerId);
				MerchantAccount merchantAccount = new MerchantAccount();
				merchantAccount.setId(0);
				merchantAccount.setServiceId("KiKi");
				merchantAccount.setTransactionId(lastTransaciontId);
				merchantAccount.setAmount(amount);
				merchantAccount.setDate(new Date());
				merchantAccount.setViewerId(viewerId);
				merchantAccount.setSuccess(false);
				merchantAccount.setTransactionType(TransactionType.DEACTIVATE);
				merchantAccountRepository.save(merchantAccount);
				paymentStatus = "Fail";
				responseMsg = "Activation Unsuccessful Error Occurred With Transaction Id";
				// merchantAccountModel.addMerchantAccountDetails("KiKi",lastTransaciontId, new
				// Date(), amount, viewerId, false, transactionType);
			}

			return returnValue;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public void testMobitelConnection() {
		User user = new User();
		user.setUsername("esmsusr_kikitv");
		user.setPassword("K1K1t7");
		lk.mobitel.esms.test.ServiceTest st = new lk.mobitel.esms.test.ServiceTest();
		System.out.println(st.testService(user));
	}

	@Override
	public void logInToMobitelESMS(String userName, String password) {
		User user = new User();
		// user.setUsername("esmsusr_kikitv");
		// user.setPassword("K1K1t7");
		user.setUsername(userName);
		user.setPassword(password);
		SessionManager sm = SessionManager.getInstance();
		sm.login(user);
	}

	@Override
	public int sendSms(String aliasMsg, String mobileNo, String message) {
		Alias alias = new Alias();
		alias.setAlias(aliasMsg);
		SmsMessage msg = new SmsMessage();
		if (message == null || message.isEmpty()) {
			msg.setMessage("Thank You for subscribing to KiKi. Please re login to the app");
		} else {
			msg.setMessage(message);
		}

		msg.setSender(alias);
		/** recipients per SMSMessage is limited to 500 **/
		// msg.getRecipients().add("0094711234567");
		msg.getRecipients().add(mobileNo);
		try {
			SMSManager smsMgr = new SMSManager();
			int resultCode = smsMgr.sendMessage(msg);
			if (resultCode == 200) {
				logger.info("sms sent to user successfully");
				return 200;
			} else {
				logger.info("Error Code = " + resultCode);
				return resultCode;
			}
		} catch (NullSessionException ex) {
			logger.info("Null session exception " + ex);
			return 0;
		}
	}

	@Override
	public void logOutFromMobitelESMS() {
		SessionManager sm = SessionManager.getInstance();
		sm.logout();
	}
}
