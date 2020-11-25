<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>ALREADY SUBSCRIBED</title>
<link href="<c:url value='/static/css/bootstrap.min.css'/>"
	rel="stylesheet">
<link rel="shortcut icon"
	href="<c:url value='/static/img/favicon.ico'/>" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link href="<c:url value='/static/css/already-subscribed.css'/>"
	rel="stylesheet">
</head>
<body onload="initialize()" class="container main_container ">
	<div class="header_container">
		<h5 class="header" id="title">Your Subscriptions</h5>
	</div>

	<div class="body_container">

		<c:forEach items="${subscriptionTypeList}" var="subscriptionType">

			<c:choose>
				<c:when test="${subscriptionType eq 'CREDIT_CARD'}">
					<a id="card" data-toggle="modal" data-target="#confirmation_card">
						<div class="body_frame">
							<div class="row">
								<div class="col-xs-4 col-md-4" style="padding-top: 10px;">
									<div>
										<img style="width: 40%; display: inline-block;"
											src="/susilawebpay/static/assets/images/Visa.png"> <img
											style="width: 40%; display: inline-block;"
											src="/susilawebpay/static/assets/images/Master.png">
									</div>

								</div>
								<div class="col-xs-8 col-md-8" style="padding-right: 20px;">
									<p style="text-align: right;">Card</p>
									<p style="text-align: right; margin-top: -13px;">
										<span style="color: red;" class="body-text-1"> Click to unsubscribe</span>
									</p>
									<p style="text-align: right; margin-top: -15px;">
										<span style="margin-top: 20px;" class="body-text-2">Last Payment:</span><span style="margin-top: 20px;">
											${lastSubscribeDay}</span>
									</p>

									</p>
								</div>


							</div>
						</div>
					</a>
				</c:when>
				<c:when test="${subscriptionType eq 'IDEABIZ'}">
					<a id="dviu" data-toggle="modal" data-target="#confirmation_dialog">

						<div class="body_frame">
							<div class="row">
								<div class="col-xs-4 col-md-4">
									<div>
										<img src="/susilawebpay/static/assets/images/dialog-new.jpg"
											style="height: 40px; margin-top: 8px;">
									</div>

								</div>
								<div class="col-xs-8 col-md-8" style="padding-right: 20px;">
									<p style="text-align: right;">Dialog</p>
									<p style="text-align: right; margin-top: -13px;">
										<span style="color: red;" class="body-text-1"> Click to unsubscribe</span>
									</p>
									<p style="text-align: right; margin-top: -15px;">
										<span style="margin-top: 20px;" class="body-text-2">Last Payment:</span><span style="margin-top: 20px;">
											${lastSubscribeDay}</span>
									</p>
								</div>


							</div>
						</div>

					</a>
				</c:when>
				<c:when test="${subscriptionType eq 'MOBITEL'}">
					<a id="mobitel" data-toggle="modal"
						data-target="#confirmation_mobitel">

						<div class="body_frame">
							<div class="row">
								<div class="col-xs-4 col-md-4" style="padding-top: 10px;">
									<div>
										<img src="/susilawebpay/static/assets/images/mobitel.png"
											style="height: 40px; margin-top: 8px;">
									</div>

								</div>
								<div class="col-xs-8 col-md-8" style="padding-right: 20px;">
									<p style="text-align: right;">Mobitel - Add to my bill</p>
									<p style="text-align: right; margin-top: -13px;">
										<span style="color: red;" class="body-text-1"> Click to unsubscribe</span>
									</p>
									<p style="text-align: right; margin-top: -15px;">
										<span style="margin-top: 20px;" class="body-text-2">Last Payment:</span><span style="margin-top: 20px;">
											${lastSubscribeDay}</span>
									</p>
								</div>


							</div>
						</div>
					</a>
				</c:when>
				<c:when test="${subscriptionType eq 'HUTCH'}">
					<a id="hutch" data-toggle="modal" data-target="#confirmation_hutch">

						<div class="body_frame">
							<div class="row">
								<div class="col-xs-4 col-md-4">
									<div>
										<img src="/susilawebpay/static/assets/images/hutch.jpg"
											style="height: 40px; margin-top: 8px;">
									</div>

								</div>
								<div class="col-xs-8 col-md-8" style="padding-right: 20px;">
									<p style="text-align: right;">Hutch</p>
									<p style="text-align: right; margin-top: -13px;">
										<span style="color: red;" class="body-text-1"> Click to unsubscribe</span>
									</p>
									<p style="text-align: right; margin-top: -15px;">
										<span style="margin-top: 20px;" class="body-text-2">Last Payment:</span><span style="margin-top: 20px;">
											${lastSubscribeDay}</span>
									</p>
								</div>


							</div>
						</div>

					</a>
				</c:when>

				<c:when test="${subscriptionType eq 'KEELLS'}">
					<a id="keells">

						<div class="body_frame">
							<div class="row">
								<div class="col-xs-4 col-md-4">
									<div>
										<img src="/susilawebpay/static/assets/images/Keells.jpg"
											style="height: 40px; margin-top: 8px;">
									</div>

								</div>
								<div class="col-xs-8 col-md-8" style="padding-right: 20px;">
									<p style="text-align: right;">KEELLS</p>
									<br>
									<!-- <p style=" text-align: right; margin-top: -13px;"> <span style="color: red;"> Click to unsubscribe</span> </p> -->
									<p style="text-align: right; margin-top: -15px;">
										<span style="margin-top: 20px;" class="body-text-2">Last Payment:</span><span style="margin-top: 20px;">
											${lastSubscribeDay}</span>
									</p>
								</div>


							</div>
						</div>

					</a>
				</c:when>
			</c:choose>
		</c:forEach>
		<button type="button" id="btnnewsubscription" class="form_submit">ADD
			NEW SUBSCRIPTION</button>

		<div id="confirmation_dialog" class="modal fade">
			<div class="modal-dialog modal-sm">
				<div class="modal-content" style="border: solid 3px #24A9A6">
					<div class="modal-body">
						<h4 class="modal-title">Would you like to Unsubscribe?</h4>
						<button id="btn_dviu" type="button" class="btn form_btn">Yes</button>

						<button type="button" class="btn form_btn btn-no" data-dismiss="modal">No</button>
					</div>
				</div>
			</div>
		</div>
		<div id="confirmation_mobitel" class="modal fade">
			<div class="modal-dialog modal-sm">
				<div class="modal-content" style="border: solid 3px #24A9A6">
					<div class="modal-body">
						<h4 class="modal-title">Would you like to Unsubscribe?</h4>
						<button id="btn_mobitel" type="button" class="btn form_btn">Yes</button>

						<button type="button" class="btn form_btn btn-no" data-dismiss="modal">No</button>
					</div>
				</div>
			</div>
		</div>
		<div id="confirmation_hutch" class="modal fade">
			<div class="modal-dialog modal-sm">
				<div class="modal-content" style="border: solid 3px #24A9A6">
					<div class="modal-body">
						<h4 class="modal-title">Would you like to Unsubscribe?</h4>
						<button id="btn_hutch" type="button" class="btn form_btn">Yes</button>

						<button type="button" class="btn form_btn btn-no" data-dismiss="modal">No</button>
					</div>
				</div>
			</div>
		</div>
		<div id="confirmation_card" class="modal fade">
			<div class="modal-dialog modal-sm">
				<div class="modal-content" style="border: solid 3px #24A9A6">
					<div class="modal-body">
						<h4 class="modal-title">Would you like to Unsubscribe?</h4>
						<button id="btn_card" type="button" class="btn form_btn">Yes</button>

						<button type="button" class="btn form_btn btn-no" data-dismiss="modal">No</button>
					</div>
				</div>
			</div>
		</div>


	</div>


	<script src="<c:url value='/static/js/jquery-3.5.1.min.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/static/js/bootstrap.min.js'/>"
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
			$("#btnnewsubscription").click(function () {
			    var token = "${subscriptionPaymentDto.getTokenHash()}";
			    window.location.replace(baseURL + "/home?token=" + token + "&type=new"); 
			   
			});
			
			function initialize() {
			    console.log("initialize");
			    sessionStorage.setItem('subscriptionPaymentId', ${subscriptionPaymentDto.getSubscriptionPaymentId()});
			    sessionStorage.setItem('viewerId', ${subscriptionPaymentDto.getViewerId()});
			    sessionStorage.setItem('packageId', ${subscriptionPaymentDto.getPackageId()});
			    sessionStorage.setItem('language', "${subscriptionPaymentDto.getLanguage()}");
			    sessionStorage.setItem('tokenHash', "${subscriptionPaymentDto.getTokenHash()}");
			    sessionStorage.setItem('mobile', "${subscriptionPaymentDto.getMobile()}");
			    
			    
			    $.i18n({
	                locale: sessionStorage.getItem('language').toLowerCase()
	            });
	            $.i18n().load({
	                en: './static/language/en.json',
	                si: './static/language/si.json',
	                ta: './static/language/ta.json'
	            }).done(function() {
	                console.log('done!')
	                $("#title").text($.i18n('already-sub-title'));
	                $("#btnnewsubscription").text($.i18n('already-sub-add-new-btn'));
	                $(".modal-title").text($.i18n('already-sub-pop-up'));
	                $(".btn-no").text($.i18n('already-sub-pop-up-no'));
					$("#btn_card").text($.i18n('already-sub-pop-up-yes'));
					$("#btn_hutch").text($.i18n('already-sub-pop-up-yes'));
					$("#btn_mobitel").text($.i18n('already-sub-pop-up-yes'));
					$("#btn_dviu").text($.i18n('already-sub-pop-up-yes'));
					$(".body-text-1").text($.i18n('already-sub-button-body-1'));
					$(".body-text-2").text($.i18n('already-sub-button-body-2'));
	            });
	            
			}
			
			$("#btn_dviu").click(function () {
			    var data = {
			        subscriptionPaymentId: sessionStorage.getItem('subscriptionPaymentId'),
			        viewerid: sessionStorage.getItem('viewerId'),
			        type: 4,
			        mobile: sessionStorage.getItem('mobile')
			    }
			
			    console.log("send Req");
			    console.log(data);
			
			    $.ajax({
			        type: "post",
			        contentType: "application/json",
			        dataType: 'json',
			        url: baseURL + "/rest/unsubscribe",
			        data: JSON.stringify(data),
			        success: function (resp) {
			            console.log(resp);
			            if (resp.status == 'success') {
			                window.location.replace(baseURL + "/thanks/10");
			            } else {
			                window.location.replace(baseURL + "/error-page?message=" + resp.status);
			            }
			
			        },
			        error: function (e) {
			            console.log(e);
			            window.location.replace(baseURL + "/error-page?message=" + e);
			        }
			    });
			});
			
			$("#btn_mobitel").click(function () {
			    var data = {
			        subscriptionPaymentId: sessionStorage.getItem('subscriptionPaymentId'),
			        viewerid: sessionStorage.getItem('viewerId'),
			        type: 5,
			        mobile: sessionStorage.getItem('mobile')
			    }
			
			    $.ajax({
			        type: "post",
			        contentType: "application/json",
			        dataType: 'json',
			        url: baseURL + "/rest/unsubscribe",
			        data: JSON.stringify(data),
			        success: function (resp) {
			            console.log(resp);
			            if (resp.status == 'success') {
			                window.location.replace(baseURL + "/thanks/10");
			            } else {
			                window.location.replace(baseURL + "/error-page?message=" + resp.status);
			            }
			
			        },
			        error: function (e) {
			            console.log(e);
			            window.location.replace(baseURL + "/error-page?message=" + e);
			        }
			    });
			});
			
			$("#btn_card").click(function () {
			    var data = {
			        subscriptionPaymentId: sessionStorage.getItem('subscriptionPaymentId'),
			        viewerid: sessionStorage.getItem('viewerId'),
			        type: 7,
			        mobile: sessionStorage.getItem('mobile')
			    }
			
			    $.ajax({
			        type: "post",
			        contentType: "application/json",
			        dataType: 'json',
			        url: baseURL + "/rest/unsubscribe",
			        data: JSON.stringify(data),
			        success: function (resp) {
			            console.log(resp);
			            if (resp.status == 'success') {
			                window.location.replace(baseURL + "/thanks/10");
			            } else {
			                window.location.replace(baseURL + "/error-page?message=" + resp.status);
			            }
			
			        },
			        error: function (e) {
			            console.log(e);
			            window.location.replace(baseURL + "/error-page?message=" + e);
			        }
			    });
			});
			
			$("#btn_hutch").click(function () {
			    var data = {
			        subscriptionPaymentId: sessionStorage.getItem('subscriptionPaymentId'),
			        viewerid: sessionStorage.getItem('viewerId'),
			        type: 8,
			        mobile: sessionStorage.getItem('mobile')
			    }
			
			    $.ajax({
			        type: "post",
			        contentType: "application/json",
			        dataType: 'json',
			        url: baseURL + "/rest/unsubscribe",
			        data: JSON.stringify(data),
			        success: function (resp) {
			            console.log(resp);
			            if (resp.status == 'success') {
			                window.location.replace(baseURL + "/thanks/10");
			            } else {
			                window.location.replace(baseURL + "/error-page?message=" + resp.status);
			            }
			
			        },
			        error: function (e) {
			            console.log(e);
			            window.location.replace(baseURL + "/error-page?message=" + e.responseText);
			        }
			    });
			});

        </script>
</body>
</html>