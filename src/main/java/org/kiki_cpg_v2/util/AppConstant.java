package org.kiki_cpg_v2.util;

import java.net.URI;

public class AppConstant {

//	public static final String IDEABIZ_SERVICE_1 = "bf110848-23ca-4b7d-8a3f-872b59bfd32e";
//	public static final String IDEABIZ_SERVICE_7 = "f0ce6a27-7aca-4e12-b121-25eeee7a840a";

	public static final String URL_IDEABIZ_PAYMENT_CONFIRM_I = "https://ideabiz.lk/apicall/payment/v4/";
	public static final String URL_IDEABIZ_PAYMENT_CONFIRM_II = "/transactions/amount";
	public static final String URL_IDEABIZ_ACCESS_TOKEN = "https://ideabiz.lk/apicall/token";
	public static final String URL_IDEABIZ_SUBMIT_PIN = "https://ideabiz.lk/apicall/pin/subscription/v1/submitPin";
	public static final String URL_IDEABIZ_OTP = "https://ideabiz.lk/apicall/pin/subscription/v1/subscribe";
	public static final String URL_IDEABIZ_UNSUBSCRIBE = "https://ideabiz.lk/apicall/subscription/v3/unsubscribe";
	public static final String URL_IDEABIZ_SUBSCRIBE = "https://ideabiz.lk/apicall/pin/subscription/v1/subscribe";
	public static final Integer ACTIVE = 1;
	public static final Integer INACTIVE = 0;
	public static final String IDEABIZ = "Ideabiz";
	// public static final String [] CRON_NOTIFY_MOBILES = {"+94776497074",
	// "+94773799390", "+94779922990", "+94771485821", "+94767072477"};
	public static final String[] CRON_NOTIFY_MOBILES = { "+94776497074" };
	public static final String DIALOG = "DIALOG";
	public static final String MOBITEL = "MOBITEL";
	public static final String CARD_HNB = "CARD_HNB";
	public static final String URL_NOTIFICATION = "http://35.200.234.252:3000/fcm/v1/message";
	public static final String HNB = "HNB";
	public static final String ACCEPT = "ACCEPT";
	public static final String HUTCH = "HUTCH";

	public static final String URL_HUTCH_BASE = "http://192.168.192.51:8581/prc";
	public static final String URL_HUTCH_SUBSCRIBE = URL_HUTCH_BASE + "/subscribeService";
	public static final String KEELLS = "KEELLS";
	public static final String TRIAL = "TRIAL";
	public static final String DUPLICATE = "DUPLICATE";
	public static final String URL_HUTCH_UNSUBSCRIBE = URL_HUTCH_BASE + "/deactivation";

}
