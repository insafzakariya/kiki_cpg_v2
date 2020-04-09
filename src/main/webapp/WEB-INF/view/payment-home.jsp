<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>


<!-- Mirrored from dev.lorvent.com/core_plus/lockscreen.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 07 Dec 2016 10:59:01 GMT -->
<head>

<style type="text/css">
.stv-radio-button {
	position: absolute;
	left: -9999em;
	top: -9999em;
}

.stv-radio-button+label {
	padding: .5em 1em;
	cursor: pointer;
	border: 1px solid #707070;
	color: #fff;
	background-color: #707070;
}

*, *:before, *:after {
	box-sizing: inherit;
}

.stv-radio-button:checked+label {
	background-color: #ffffff;
	color: #000;
}

.row>label {
	width: 70%;
}

.button-primary-flat {
	background-color: #00999D;
	border-color: #00999D;
	color: #FFF;
}

.logo {
	width: 72px;
	height: 72px;
	border: 2px solid #ffffff;
	background-size: cover;
	margin: auto;
}

@media ( min-width :340px) {
	.col-sm-4 {
		width: 33.33333333%;
	}
	.col-sm-8 {
		width: 66.66666666%;
	}
}

p {
	margin: 0 0 0px !important;
}
</style>



<title>Lockscreen</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="shortcut icon"
	href="<c:url value='/static/img/favicon.ico'/>" />
<!-- Bootstrap -->
<link href="<c:url value='/static/css/bootstrap.min.css'/>"
	rel="stylesheet">
<link href="<c:url value='/static/css/buttons_sass.css'/>"
	rel="stylesheet">
<link href="<c:url value='/static/css/advbuttons.css'/>"
	rel="stylesheet">
<!-- styles -->
<!--page level css -->

<link href="<c:url value='/static/css/lockscreen2.css'/>"
	rel="stylesheet">


<link href="<c:url value='/static/vendors/iCheck/css/all.css'/>"
	rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="<c:url value='/static/vendors/awesomebootstrapcheckbox/css/awesome-bootstrap-checkbox.css'/>">

<!--end page level css-->
</head>

<body class="background-img">
	<div class="preloader">
		<div class="loader_img">
			<img id="imgSrc" src="<c:url value='/static/img/loader.gif'/>"
				alt="loading..." height="64" width="64">
		</div>
	</div>
	<div class="container">


		<div class="col-md-6 col-md-offset-3">
			<div class="lockscreen-container">

				<div class="form-box">
					<div class="form">
						<div class="locked " style="font-weight: bold; font-size: 20">
							Choose your preferred method.<br>
						</div>
						<div>
							<small class="locked ">select your payment method to
								unlock<br>
							</small>
						</div>
						<div>
							<small class="locked ">access to exclusive content.<br></small>
						</div>
						<div id="paymentOptions">

							<div class="form-box">
								<form id="paymentMethodForm">

									<!-- 	<div class="row" style="margin-left:0px; margin-right:0px;">

	                                <c:if test="${not empty mCashPaymentAmmount}">
	                                <input type="radio" class="stv-radio-button radioOptions" name="optionsRadios" value="3" id="button1" /> 
	                                <label for="button1"> 
	                                <div class="row">
	                                <div class="col-md-4 pull-left"><img src="/susilawebpay/static/img/authors/mcash.jpg" class="img-rounded" alt="Cinque Terre" width="50" height="50"> </div>
	                                <div class="col-md-8 pull-right"><p><strong>M Cash</strong></p><p>${mCashPaymentAmmount} </p><p>+ Tax ${mCashPaymentType}</p></div>
	                                </div>
	                                </label>
	                                </c:if>
	                                </div> -->

									<!-- 
	                                <div class="row" style="margin-left:0px; margin-right:0px;">
	                                <c:if test="${not empty ezcashPaymentAmmount}">
	                                <input type="radio" class="stv-radio-button" name="optionsRadios" value="2" id="button2" /> 
	                                <label for="button2"> 
	                                <div class="row">
	                                <div class="col-md-4 pull-left"><img src="/susilawebpay/static/img/authors/mcash.jpg" class="img-rounded" alt="Cinque Terre" width="50" height="50"> </div>
	                                <div class="col-md-8 pull-right"><p><strong>Ez Cash</strong></p><p>${ezcashPaymentAmmount} </p><p>+ Tax ${ezcashPaymentType}</p></div>
	                                </div>
	                               
	                                </label>
	                                </c:if>
	                                </div>
	                                 -->
									<c:if test="${empty ideabiz_subscribed}">

										<div class="row" style="margin-left: 0px; margin-right: 0px;">
											<c:if test="${not empty dialogViuPaymentAmmount}">
												<input type="radio" class="stv-radio-button"
													name="optionsRadios" value="4" id="button3" />
												<label for="button3">
													<div class="row">
														<div class="col-md-4 pull-left">
															<img
																src="/susilawebpay/static/assets/images/dialog-new.jpg"
																class="img-rounded" alt="Cinque Terre" width="50"
																height="50">
														</div>
														<div class="col-md-8 pull-right">
															<p>
																<strong>Dialog</strong>
															</p>
															<p>Add to bill</p>
														</div>
													</div>
												</label>
											</c:if>
										</div>
									</c:if>
									<c:if test="${empty mobitel_subscribed}">
										<div class="row" style="margin-left: 0px; margin-right: 0px;">
											<c:if test="${not empty mobitelAddToBillPaymentAmount}">
												<input type="radio" class="stv-radio-button"
													name="optionsRadios" value="5" id="button4" />
												<label for="button4">
													<div class="row">
														<div class="col-md-4 pull-left">
															<img src="/susilawebpay/static/img/authors/madd.jpg"
																class="img-rounded" alt="Cinque Terre" width="50"
																height="50">
														</div>
														<div class="col-md-8 pull-right">
															<p>
																<strong>Mobitel</strong>
															</p>
															<p>
																<strong>Add to bill</strong>
															</p>
														</div>
													</div>

												</label>
											</c:if>
										</div>
									</c:if>


									<div class="row" style="margin-left: 0px; margin-right: 0px;">
										<c:if test="${not empty isScratchCardPayment}">
											<input type="radio" class="stv-radio-button"
												name="optionsRadios" value="6" id="button5" />
											<label for="button5">
												<div class="row">
													<div class="col-md-4 pull-left">
														<img
															src="/susilawebpay/static/img/authors/scratchcard.jpg"
															class="img-rounded" alt="Cinque Terre" width="50"
															height="50">
													</div>
													<div class="col-md-8 pull-right">
														<p>
															<strong>Use Scratch </strong>
														</p>
														<p>
															<strong>Card code</strong>
														</p>
													</div>
												</div>
											</label>
										</c:if>
									</div>


									<%-- <c:if test="${empty ck_subscribed}">
	                            	
	                            		<div class="row" style="margin-left:0px; margin-right:0px;">
		                                
		                                <input type="radio" class="stv-radio-button" name="optionsRadios" value="7" id="button7" />
		                                <label for="button7">
		                                <div class="row">
		                                <div class="col-md-4 pull-left"><img src="/susilawebpay/static/assets/images/cargilskeels.png" class="img-rounded" alt="Cinque Terre" width="50" height="50"> </div>
		                                <div class="col-md-8 pull-right"><p><strong>Cargills & Keells</strong></p><p><strong>Add to my bill</strong></p></div>
		                                </div>	                                
		                                </label>
		                              
		                            	</div>
	                            	
	                            	 </c:if> --%>
							</div>

							<div class="reg-code"></div>

							<!--  <div class="panel-body">
		                            radio
									<div class="form-group select2-container">
										<label>Proceed To Subscribe/Unsubscribe:</label>

									</div>
		                            <div class="form-group">
		                            <input type="button" id="payButton" class="button button-rounded button-primary-flat hvr-hang" value="Proceed" style="width: 75%; text-align: center; background: #009b9f; border:0px; border-radius:0px; padding:0px; margin-left:0%; margin-top: 0px;">
		                                
		                            </div>
		                        </div> -->
							<form id="ezCashForm"
								action="https://ipg.dialog.lk/ezCashIPGExtranet/servlet_sentinal"
								method="post">
								<input id="encryptedMerchantInvoice" type="hidden"
									value="${merchantInvoice}" name="merchantInvoice">
							</form>

							<form id="mCashForm" action="${mcashPaymentUrl}" method="get">
								<input id="mCashToken" type="hidden" value="" name="t_id">
							</form>
							<input id="subscriptionPaymentId" type="hidden"
								value="${subscriptionPaymentId}" name="subscriptionPaymentId">
						</div>
						<div id="mobitelRegistration" style="padding-top: 15px;"
							class="hide">
							<div class="form-box">
								<div class="form-group select2-container">
									<label> Dear customer in order to complete the
										registration </label>
								</div>
								<div class="form-group select2-container">
									<label> please send a sms to 5455 by saying</label>
								</div>
								<div class="form-group">
									<label id="registrationContent"> </label>
								</div>
							</div>
							<div class="panel-body"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
	<!-- global js -->
	<script src="<c:url value='/static/js/jquery-1.11.3.min.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/static/js/bootstrap.min.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/static/js/main.js' />"></script>
	<!-- end of global js -->
	<!-- page css -->
	<script src="<c:url value='/static/js/lockscreen2.js'/>"></script>

	<script src="<c:url value='/static/vendors/iCheck/js/icheck.js'/>"></script>
	<script src="<c:url value='/static/js/custom_js/radio_checkbox.js'/>"></script>
	<script src="https://www.paypalobjects.com/api/checkout.js"></script>
	<!-- Hotjar Tracking Code for https://payment.kiki.lk/ -->
	<script>
		(function(h, o, t, j, a, r) {
			h.hj = h.hj || function() {
				(h.hj.q = h.hj.q || []).push(arguments)
			};
			h._hjSettings = {
				hjid : 1633284,
				hjsv : 6
			};
			a = o.getElementsByTagName('head')[0];
			r = o.createElement('script');
			r.async = 1;
			r.src = t + h._hjSettings.hjid + j + h._hjSettings.hjsv;
			a.appendChild(r);
		})(window, document, 'https://static.hotjar.com/c/hotjar-', '.js?sv=');
	</script>


	<!-- end of page css -->
</body>


<!-- Mirrored from dev.lorvent.com/core_plus/lockscreen.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 07 Dec 2016 10:59:03 GMT -->

</html>
