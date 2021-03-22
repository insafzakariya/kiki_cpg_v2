<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Payment method selection</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link href="<c:url value='/static/css/otp.css'/>" rel="stylesheet">
</head>
<body>
	<div class="main_container">

		<div class="header_container">
			<h5 id="title" class="header">Please confirm your verification
				code</h5>
			<p id="sub-title" class="tagline">A 6 digit confirmation code has
				been sent to your number. Enter it below to activate it</p>
		</div>
		<div class="body_container">

			<div class="main_number_input">
				<input type="number" name="" id="d1" placeholder="# # # # # #"
					class="inputs" data-maxlength="6" data-id="d1">
			</div>
			<button class="form_submit" id="btn_otp_approve">ACTIVATE</button>

			<div class="error-field">
				<div id="error" class="hide errorField" style="color: red">
					<p id="error-p"></p>
				</div>
			</div>


		</div>
		<div class="preloader" style="padding-top: 10px; display: none"
			id="gif-load">
			<div class="loader_img">
				<img id="imgSrc" src="<c:url value='/static/img/ajax-loader.gif'/>"
					alt="loading..." height="64" width="64">
			</div>
		</div>
	</div>
	<script src="<c:url value='/static/js/jquery-3.5.1.min.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/static/js/i18n/CLDRPluralRuleParser.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/static/js/i18n/jquery.i18n.js'/>"
		type="text/javascript"></script>
	<script
		src="<c:url value='/static/js/i18n/jquery.i18n.messagestore.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/static/js/i18n/jquery.i18n.fallbacks.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/static/js/i18n/jquery.i18n.parser.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/static/js/i18n/jquery.i18n.emitter.js'/>"
		type="text/javascript"></script>
	<script
		src="<c:url value='/static/js/i18n/jquery.i18n.emitter.bidi.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/static/js/i18n/jquery.i18n.language.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/static/js/globle.js'/>"
		type="text/javascript"></script>
	<script type="text/javascript">
	
		$.i18n({
			locale : sessionStorage.getItem('language').toLowerCase()
		});
		$.i18n().load({
			en : './static/language/en.json',
			si : './static/language/si.json',
			ta : './static/language/ta.json'
		}).done(function() {
			console.log('done!')
			$("#title").text($.i18n('otp-title'));
			$("#sub-title").text($.i18n('otp-sub-title'));
			$("#btn_otp_approve").text($.i18n('otp-active'));
		});

		$("#d1").keyup(function() {
			$("#error").addClass("hide");
		});

		$('#btn_otp_approve')
				.click(
						function() {
							var serverRef = sessionStorage
									.getItem("server_ref");
							var subscriptionPaymentId = sessionStorage
									.getItem("subscriptionPaymentId");
							var day = sessionStorage.getItem("day");
							var viewerId = sessionStorage.getItem("viewerId");
							var methodId = sessionStorage.getItem("methodId");
							var planId = sessionStorage.getItem("planId");

							var isTrial = sessionStorage.getItem('isFreeTrial');
							var otp = $('#d1').val();
							var otp_length = otp.toString().length;
							sessionStorage.removeItem("invoiceId");

							if (otp_length == 6) {
								var data_st = {
									"pin" : otp,
									"serverRef" : serverRef,
									"subscriptionPaymentId" : subscriptionPaymentId,
									"day" : day,
									"viewerId" : viewerId,
									"planId" : planId,
									"trial" : isTrial
								};

								var x = document.getElementById("gif-load");
								x.style.display = "block";
								if (methodId == 4) {
									$
											.ajax({
												type : "post",
												contentType : "application/json",
												dataType : 'json',
												url : baseURL
														+ "/rest/ideabiz/pin_subscription_confirm",
												data : JSON.stringify(data_st),
												success : function(data) {
													console.log(data);
													x.style.display = "none";
													var myJSON = JSON
															.stringify(data);
													var myJSON = JSON
															.parse(myJSON);
													if (data.statusCode == 'FAIL') {
														$("#error-p").text(
																data.message);
														$("#error")
																.removeClass(
																		"hide");
													}
													if (data.statusCode == 'Expired') {
														var url = baseURL
																+ "/error-page?message="
																+ data.message;
														window.location
																.replace(url);
													} else {
														sessionStorage.setItem(
																"invoiceId",
																data.invoiceId);
														//var url = baseURL + "/notification-email";
														var url = baseURL
																+ "/thanks/4";
														window.location
																.replace(url);
													}
												},
												error : function(e) {
													var x = document
															.getElementById("gif-load");
													x.style.display = "none";
													$("#error-p")
															.text(
																	'An error occurred while updating payment transaction : '
																			+ e.responseText);
													console
															.log(
																	'An error occurred while updating payment transaction : ',
																	e);
													$("#error").removeClass(
															"hide");
												}
											});
								} else if (methodId == 7) {
									$
											.ajax({
												type : "post",
												contentType : "application/json",
												dataType : 'json',
												url : baseURL
														+ "/rest/otp/confirm",
												data : JSON.stringify(data_st),
												success : function(data) {
													if (data.status == "Success") {

														var data_st2 = {
															"mobileNo" : sessionStorage
																	.getItem("mobile"),
															"viewerId" : sessionStorage
																	.getItem("viewerId"),
															"planId" : sessionStorage
																	.getItem("planId")
														};

														$
																.ajax({
																	type : "post",
																	contentType : "application/json",
																	dataType : 'json',
																	url : baseURL
																			+ "/rest/hnb/begin",
																	data : JSON
																			.stringify(data_st2),
																	success : function(
																			data) {
																		sessionStorage
																				.setItem(
																						'transaction_uuid',
																						data.transactionUUID);
																		sessionStorage
																				.setItem(
																						'referance_no',
																						data.referanceNo);
																		sessionStorage
																				.setItem(
																						'card_amount',
																						data.amount);
																		sessionStorage
																				.setItem(
																						'invoiceId',
																						data.cardInvoiceId);
																		console
																				.log(data);
																		var url = paymentGatewayRedirectURL
																				+ "transaction_uuid="
																				+ data.transactionUUID
																				+ "&referance="
																				+ data.referanceNo
																				+ "&amount="
																				+ data.amount
																				+ "&frequency="
																				+ data.frequency
																				+ "&startdate="
																				+ data.date;
																		window.location
																				.replace(url);

																	},
																	error : function(
																			e) {
																		$(
																				"#error-p")
																				.text(
																						'An error occurred while connecting Payment Gateway');
																		console
																				.log(
																						'An error occurred while connecting Payment Gateway : ',
																						e);
																		$(
																				"#error")
																				.removeClass(
																						"hide");
																	}
																});
													} else {
														var x = document
																.getElementById("gif-load");
														x.style.display = "none";
														$("#error-p").text(
																data.status);
														$("#error")
																.removeClass(
																		"hide");
													}
												},
												error : function(e) {
													$("#error-p")
															.text(
																	'An error occurred while updating payment transaction : '
																			+ e.responseText);
													console
															.log(
																	'An error occurred while updating payment transaction : ',
																	e);
													$("#error").removeClass(
															"hide");
												}
											});
								} else if (methodId == 8) {
									$
											.ajax({
												type : "post",
												contentType : "application/json",
												dataType : 'json',
												url : baseURL
														+ "/rest/otp/confirm",
												data : JSON.stringify(data_st),
												success : function(data) {
													var isTrial = sessionStorage
															.getItem('isFreeTrial');
													if (data.status == "Success") {
														var data_st2 = {
															"mobileNo" : sessionStorage
																	.getItem("mobile"),
															"viewerId" : sessionStorage
																	.getItem("viewerId"),
															"planId" : sessionStorage
																	.getItem("planId"),
															"trial" : isTrial
														};
														$
																.ajax({
																	type : "post",
																	contentType : "application/json",
																	dataType : 'json',
																	url : baseURL
																			+ "/hutch/subscribe",
																	data : JSON
																			.stringify(data_st2),
																	success : function(
																			data) {
																		if (data.status == "ACCEPT") {
																			sessionStorage
																					.setItem(
																							'invoiceId',
																							data.cardInvoiceId);
																			var url = baseURL
																					+ "/thanks/8";
																			window.location
																					.replace(url);
																		} else if (data.status == "DUPLICATE") {
																			$(
																					"#error-p")
																					.text(
																							'Already Subscribed for this service');
																			$(
																					"#error")
																					.removeClass(
																							"hide");
																		} else {
																			$(
																					"#error-p")
																					.text(
																							'An error occurred while connecting Payment Gateway');
																			console
																					.log('An error occurred while connecting Payment Gateway : ');
																			$(
																					"#error")
																					.removeClass(
																							"hide");
																		}

																	},
																	error : function(
																			e) {
																		$(
																				"#error-p")
																				.text(
																						'An error occurred while connecting Payment Gateway');
																		console
																				.log(
																						'An error occurred while connecting Payment Gateway : ',
																						e);
																		$(
																				"#error")
																				.removeClass(
																						"hide");
																	}
																});
													} else {
														$("#error-p").text(
																data.status);
														$("#error")
																.removeClass(
																		"hide");
														var x = document
																.getElementById("gif-load");
														x.style.display = "none";
													}
												},
												error : function(e) {
													$("#error-p")
															.text(
																	'An error occurred while updating payment transaction : '
																			+ e.responseText);
													console
															.log(
																	'An error occurred while updating payment transaction : ',
																	e);
													$("#error").removeClass(
															"hide");
												}
											});
								} else if (methodId == 9) {

									$
											.ajax({
												type : "post",
												contentType : "application/json",
												dataType : 'json',
												url : baseURL
														+ "/rest/otp/confirm",
												data : JSON.stringify(data_st),
												success : function(data) {
													console.log(data);
													if (data.status == "Success") {
														var data_st2 = {
															"mobileNo" : sessionStorage
																	.getItem("mobile"),
															"viewerId" : sessionStorage
																	.getItem("viewerId"),
															"planId" : sessionStorage
																	.getItem("planId")
														};
														$
																.ajax({
																	type : "post",
																	contentType : "application/json",
																	dataType : 'json',
																	url : baseURL
																			+ "/keells/begin",
																	data : JSON
																			.stringify(data_st2),
																	success : function(
																			data) {
																		sessionStorage
																				.setItem(
																						'invoiceId',
																						data.cardInvoiceId);
																		var url = baseURL
																				+ "/thanks/9";
																		window.location
																				.replace(url);

																	},
																	error : function(
																			e) {
																		$(
																				"#error-p")
																				.text(
																						'An error occurred while connecting Payment Gateway: ',
																						e.responseText);
																		console
																				.log(
																						'An error occurred while connecting Payment Gateway : ',
																						e.responseText);
																		$(
																				"#error")
																				.removeClass(
																						"hide");
																	}
																});
													} else {
														var x = document
																.getElementById("gif-load");
														x.style.display = "none";
														console
																.log(data.status);
														$("#error-p").text(
																data.status);
														$("#error")
																.removeClass(
																		"hide");
													}
												},
												error : function(e) {
													$("#error-p")
															.text(
																	'An error occurred while updating payment transaction : '
																			+ e.responseText);
													console
															.log(
																	'An error occurred while updating payment transaction : ',
																	e);
													$("#error").removeClass(
															"hide");
												}
											});
								} else if (methodId == 11) {

									$
									.ajax({
										type : "post",
										contentType : "application/json",
										dataType : 'json',
										url : baseURL
												+ "/rest/otp/confirm",
										data : JSON.stringify(data_st),
										success : function(data) {
											var isTrial = sessionStorage
													.getItem('isFreeTrial');
											if (data.status == "Success") {
												var data_st2 = {
													"mobileNo" : sessionStorage
															.getItem("mobile"),
													"viewerId" : sessionStorage
															.getItem("viewerId"),
													"planId" : sessionStorage
															.getItem("planId"),
													"trial" : isTrial
												};
												$
														.ajax({
															type : "post",
															contentType : "application/json",
															dataType : 'json',
															url : baseURL
																	+ "/rest/airlet/subscribe",
															data : JSON
																	.stringify(data_st2),
															success : function(
																	data) {
																if (data.status == "ACCEPT") {
																	sessionStorage
																			.setItem(
																					'invoiceId',
																					data.cardInvoiceId);
																	var url = baseURL
																			+ "/thanks/8";
																	window.location
																			.replace(url);
																} else if (data.status == "DUPLICATE") {
																	$(
																			"#error-p")
																			.text(
																					'Already Subscribed for this service');
																	$(
																			"#error")
																			.removeClass(
																					"hide");
																} else {
																	$(
																			"#error-p")
																			.text(
																					'An error occurred while connecting Payment Gateway');
																	console
																			.log('An error occurred while connecting Payment Gateway : ');
																	$(
																			"#error")
																			.removeClass(
																					"hide");
																}

															},
															error : function(
																	e) {
																$(
																		"#error-p")
																		.text(
																				'An error occurred while connecting Payment Gateway');
																console
																		.log(
																				'An error occurred while connecting Payment Gateway : ',
																				e);
																$(
																		"#error")
																		.removeClass(
																				"hide");
															}
														});
											} else {
												$("#error-p").text(
														data.status);
												$("#error")
														.removeClass(
																"hide");
												var x = document
														.getElementById("gif-load");
												x.style.display = "none";
											}
										},
										error : function(e) {
											$("#error-p")
													.text(
															'An error occurred while updating payment transaction : '
																	+ e.responseText);
											console
													.log(
															'An error occurred while updating payment transaction : ',
															e);
											$("#error").removeClass(
													"hide");
										}
									});
								} else {
									$("#error-p").text('Invalied Method');
									$("#error").removeClass("hide");
								}
							} else {
								$("#error-p").text('Invalied OTP');
								$("#error").removeClass("hide");
							}

						});
	</script>
</body>
</html>
