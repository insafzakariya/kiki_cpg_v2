package org.kiki_cpg_v2.client.impl;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.Random;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.json.JSONObject;
import org.kiki_cpg_v2.client.MobitelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.NodeList;

/*import lk.mobitel.esms.message.SMSManager;
import lk.mobitel.esms.session.NullSessionException;
import lk.mobitel.esms.session.SessionManager;
import lk.mobitel.esms.ws.Alias;
import lk.mobitel.esms.ws.SmsMessage;
import lk.mobitel.esms.ws.User;*/

@Component
public class MobitelClientImpl implements MobitelClient {

	final static Logger logger = LoggerFactory.getLogger(MobitelClientImpl.class);

	@Override
	public ResponseEntity<?> createAccessCode() {
		String uri = "https://apphub.mobitel.lk/mobitelint/mapis/developeroauth/oauth2/token";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setCacheControl("no-cache");
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("grant_type", "password");
		map.add("client_id", "79305786-dbcb-4d49-bd31-ce861f1f0fe5");
		map.add("username", "susilatv");
		map.add("password", "susilatv@api");
		map.add("scope", "default");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<?> res = restTemplate.exchange(uri, HttpMethod.POST, request, Object.class);

		return res;
	}

	@Override
	public ResponseEntity<?> mobitelManage(String accessToken, String activationStatus, String mobileNo,
			int lastTransaciontId) {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();

		final String uri1 = "https://apphub.mobitel.lk/mobitelint/mapis/susilatv/manage";

		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("authorization", "Bearer" + accessToken);
		headers.setCacheControl("no-cache");
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("x-ibm-client-id", "79305786-dbcb-4d49-bd31-ce861f1f0fe5");
		JSONObject jsObj = new JSONObject();

		jsObj.put("status", activationStatus);
		jsObj.put("mobileNo", mobileNo);
		jsObj.put("id", ++lastTransaciontId);
		HttpEntity<String> req = new HttpEntity<String>(jsObj.toString(), headers);
		ResponseEntity<?> res = restTemplate.exchange(uri1, HttpMethod.POST, req, Object.class);
		return res;
	}

	@Override
	public Integer updateOneCCTool(boolean isSubscribe, String mobileNumber, Date activatedDate, Date deactivatedDate)
			throws SOAPException, Exception {
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection soapConnection = soapConnectionFactory.createConnection();

		// Send SOAP Message to SOAP Server
		String url = "https://apphub.mobitel.lk/services/Subscriber";
		SOAPMessage soapResponse = soapConnection
				.call(createSOAPRequest(isSubscribe, mobileNumber, activatedDate, deactivatedDate), url);

		SOAPBody body = soapResponse.getSOAPBody();
		int code = 0;
		NodeList returnList = body.getElementsByTagName("ns2:vasSubscriptionLogResponse");
		for (int k = 0; k < returnList.getLength(); k++) {
			NodeList innerResultList1 = returnList.item(k).getChildNodes();
			for (int l = 0; l < innerResultList1.getLength(); l++) {
				NodeList innerResultList2 = innerResultList1.item(l).getChildNodes();
				for (int m = 0; m < innerResultList2.getLength(); m++) {
					if (innerResultList2.item(m).getNodeName().equalsIgnoreCase("responseCode")) {
						code = Integer.valueOf(innerResultList2.item(m).getTextContent().trim());
					}
				}
			}
		}
		logger.info("response code = " + code);
		soapConnection.close();
		return code;
	}

	public SOAPMessage createSOAPRequest(boolean isSubscribe, String mobileNumber, Date activatedDate,
			Date deactivatedDate) throws Exception {
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		// envelope.addNamespaceDeclaration("example", serverURI);

		/*
		 * Constructed SOAP Request Message: <SOAP-ENV:Envelope
		 * xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"
		 * xmlns:example="http://ws.cdyne.com/"> <SOAP-ENV:Header/> <SOAP-ENV:Body>
		 * <example:VerifyEmail> <example:email>mutantninja@gmail.com</example:email>
		 * <example:LicenseKey>123</example:LicenseKey> </example:VerifyEmail>
		 * </SOAP-ENV:Body> </SOAP-ENV:Envelope>
		 */

		// SOAP Body
		SOAPBody soapBody = envelope.getBody();

		QName bodyName = new QName("http://customer/", "vasSubscriptionLog", "m");
		SOAPElement soapBodyElem = soapBody.addBodyElement(bodyName);

		SOAPElement serviceSubscription = soapBodyElem.addChildElement(new QName("serviceSubscription"));
		SOAPElement mobNumber = serviceSubscription.addChildElement(new QName("mobileNo"));
		mobNumber.addTextNode(mobileNumber); // 0703300399
		SOAPElement requstType = serviceSubscription.addChildElement(new QName("requstType"));
		String subscriptionMode = "SUBSCRIBED";
		if (!isSubscribe) {
			subscriptionMode = "UNSUBSCRIBED";
		}

		requstType.addTextNode(subscriptionMode);
		SOAPElement serviceName = serviceSubscription.addChildElement(new QName("serviceName"));
		serviceName.addTextNode("MobitelAddToBill");
		SOAPElement vendorCode = serviceSubscription.addChildElement(new QName("vendorCode"));
		vendorCode.addTextNode("sUSUilatV3");
		SOAPElement vendorName = serviceSubscription.addChildElement(new QName("vendorName"));
		vendorName.addTextNode("KiKi");
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SOAPElement actDate = serviceSubscription.addChildElement(new QName("activatedDate"));
		actDate.addTextNode(dateFormater.format(activatedDate));
		if (deactivatedDate != null) {
			SOAPElement deactDate = serviceSubscription.addChildElement(new QName("deActivatedDate"));
			deactDate.addTextNode(dateFormater.format(deactivatedDate));
		}

		SOAPHeader header = soapMessage.getSOAPHeader();
		QName confirmation = new QName(
				"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Security",
				"wsse"); // SOAPHeaderElement confirmationHeader;

		SOAPHeaderElement security = header.addHeaderElement(confirmation);
		security.addAttribute(new QName("xmlns:wsu"),
				"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
		security.setMustUnderstand(true);

		SOAPElement usernameToken = security.addChildElement("UsernameToken", "wsse");
		usernameToken.addAttribute(new QName("wsu:Id"), "UsernameToken-12349873");
		SOAPElement username = usernameToken.addChildElement("Username", "wsse");
		username.addTextNode("susuilaTV");

		SOAPElement password = usernameToken.addChildElement("Password", "wsse");
		password.setAttribute("Type",
				"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
		password.addTextNode("susui7@tv#");

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		String dateVal = formatter.format(new Date());
		String nonce = Base64.getEncoder().encodeToString(getSaltString().getBytes("utf-8"));

		SOAPElement encode = usernameToken.addChildElement("Nonce", "wsse");
		encode.setAttribute("EncodingType",
				"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary");
		encode.addTextNode(nonce);

		SOAPElement createDate = usernameToken.addChildElement("Created", "wsu");
		createDate.addTextNode(dateVal);

		soapMessage.saveChanges();

		/* Print the request message */
		logger.info("Soap message = " + soapMessage);

		return soapMessage;
	}

	public static String getSaltString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 10) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}
//	@Override
//	public void testMobitelConnection() {
//		User user = new User();
//		user.setUsername("esmsusr_kikitv");
//		user.setPassword("K1K1t7");
//		lk.mobitel.esms.test.ServiceTest st = new lk.mobitel.esms.test.ServiceTest();
//		System.out.println(st.testService(user));
//	}

//	@Override
//	public void logInToMobitelESMS(String userName, String password) {
//		User user = new User();
//		user.setUsername(userName);
//		user.setPassword(password);
//		SessionManager sm = SessionManager.getInstance();
//		sm.login(user);
//	}

//	@Override
//	public Integer sendSms(String aliasMsg, String mobile, String message) {
//		Alias alias = new Alias();
//		alias.setAlias(aliasMsg);
//		SmsMessage msg = new SmsMessage();
//		if (message == null || message.isEmpty()) {
//			msg.setMessage("Thank You for subscribing to KiKi. Please re login to the app");
//		} else {
//			msg.setMessage(message);
//		}
//
//		msg.setSender(alias);
//		msg.getRecipients().add(mobile);
//		try {
//			SMSManager smsMgr = new SMSManager();
//			int resultCode = smsMgr.sendMessage(msg);
//			if (resultCode == 200) {
//				logger.info("sms sent to user successfully");
//				return 200;
//			} else {
//				logger.info("Error Code = " + resultCode);
//				return resultCode;
//			}
//		} catch (NullSessionException ex) {
//			logger.info("Null session exception " + ex);
//			return 0;
//		}
//	}

//	@Override
//	public void logOutFromMobitelESMS() {
//		SessionManager sm = SessionManager.getInstance();
//		sm.logout();
//
//	}

}
