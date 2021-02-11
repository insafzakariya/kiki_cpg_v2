<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="shortcut icon"
	href="<c:url value='/static/img/favicon.ico'/>" />
<link href="<c:url value='/static/css/bootstrap.min.css'/>"
	rel="stylesheet">
<link href="<c:url value='/static/css/buttons_sass.css'/>"
	rel="stylesheet">
<link href="<c:url value='/static/css/advbuttons.css'/>"
	rel="stylesheet">
<link href="<c:url value='/static/css/lockscreen2.css'/>"
	rel="stylesheet">
<link href="<c:url value='/static/vendors/iCheck/css/all.css'/>"
	rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="<c:url value='/static/vendors/awesomebootstrapcheckbox/css/awesome-bootstrap-checkbox.css'/>">
<link href="<c:url value='/static/css/common.css'/>" rel="stylesheet">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<style type="text/css">
body {
	pointer-events: none;
}
</style>

<title>HOME</title>
</head>
<body onload="initialize()">

	<div class="container">
		<div class="col-md-6 col-md-offset-3">
			<div class="lockscreen-container">
				<div class="form-box">
					<div class="form">
						<div id="title" class="locked"
							style="font-weight: bold; font-size: 20">
							Choose your preferred method.<br>
						</div>
						<div>
							<small id="sub-title-1" class="locked ">select your
								payment method to unlock<br>
							</small>
						</div>
						<div>
							<small id="sub-title-2" class="locked ">access to
								exclusive content.<br>
							</small>
						</div>
						<div id="paymentOptions">

							<div class="form-box">
								<div
									style="background: #24A9A6; height: 2px; margin-bottom: 20px;"></div>
								<form id="paymentMethodForm">

									<!-- <div id="button6" class="row body_frame" value="7"
										style="margin-left: 0px; margin-right: 0px;">
										<div class="col-md-4 pull-left center">
											<img src="/susilawebpay/static/assets/images/visamaster.jpg"
												class="img-rounded" alt="Cinque Terre" width="50"
												height="50">
										</div>
										<div class="col-md-5">
											<p>
												<strong>Card</strong>
											</p>
											<p class="type-postfix">Add to bill</p>
										</div>

									</div> -->
									
									<div id="button3" class="row body_frame" value="4"
										style="margin-left: 0px; margin-right: 0px;">
										<div class="col-md-4 pull-left center">
											<img src="/susilawebpay/static/assets/images/dialog-new.jpg"
												class="img-rounded" alt="Cinque Terre" width="50"
												height="50">
										</div>
										<div class="col-md-5">
											<p>
												<strong>Dialog</strong>
											</p>
											<p class="type-postfix">Add to bill</p>
										</div>

									</div>
									
									<div id="button4" class="row body_frame" value="5"
										style="margin-left: 0px; margin-right: 0px;">
										<div class="col-md-4 pull-left center">
											<img src="/susilawebpay/static/assets/images/MOBITEL_new.png"
												class="img-rounded" alt="Cinque Terre" width="50"
												height="50">
										</div>
										<div class="col-md-5">
											<p>
												<strong>Mobitel</strong>
											</p>
											<p class="type-postfix">Add to bill</p>
										</div>

									</div>
									
									<div id="button7" class="row body_frame" value="8"
										style="margin-left: 0px; margin-right: 0px;">
										<div class="col-md-4 pull-left center">
											<img src="/susilawebpay/static/assets/images/hutch.jpg"
												class="img-rounded" alt="Cinque Terre" width="50"
												height="50">
										</div>
										<div class="col-md-5">
											<p>
												<strong>Hutch</strong>
											</p>
											<p class="type-postfix">Add to bill</p>
										</div>

									</div>
									
									<!-- <div id="button10" class="row body_frame" value="10"
										style="margin-left: 0px; margin-right: 0px;">
										<div class="col-md-4 pull-left center">
											<img src="/susilawebpay/static/assets/images/hutch.jpg"
												class="img-rounded" alt="Cinque Terre" width="50"
												height="50">
										</div>
										<div class="col-md-5">
											<p>
												<strong>Airtel</strong>
											</p>
											<p class="type-postfix">Add to bill</p>
										</div>

									</div> -->
									
									<!-- <div id="button8" class="row body_frame" value="9"
										style="margin-left: 0px; margin-right: 0px;">
										<div class="col-md-4 pull-left center">
											<img src="/susilawebpay/static/assets/images/Keells.jpg"
												class="img-rounded" alt="Cinque Terre" width="50"
												height="50">
										</div>
										<div class="col-md-5">
											<p>
												<strong>Keells</strong>
											</p>
											<p class="type-postfix">Add to bill</p>
										</div>

									</div> -->


									<!-- <div class="row" style="margin-left: 0px; margin-right: 0px;">
										<input type="radio" class="stv-radio-button"
											name="optionsRadios" value="7" id="button6" /> <label
											for="button6">
											<div class="row">
												<div class="col-md-4 pull-left">
													<img
														src="/susilawebpay/static/assets/images/visamaster.jpg"
														class="img-rounded" alt="Cinque Terre" width="50"
														height="50">
												</div>
												<div class="col-md-5 pull-right">
													<p>
														<strong>Card</strong>
													</p>
													<p class="type-postfix">Add to bill</p>
												</div>
											</div>
										</label>
									</div> -->

									<!-- <div class="row" style="margin-left: 0px; margin-right: 0px;">
										<input type="radio" class="stv-radio-button"
											name="optionsRadios" value="4" id="button3" /> <label
											for="button3">
											<div class="row">
												<div class="col-md-4 pull-left">
													<img
														src="/susilawebpay/static/assets/images/dialog-new.jpg"
														class="img-rounded" alt="Cinque Terre" width="50"
														height="50">
												</div>
												<div class="col-md-5 pull-right">
													<p>
														<strong>Dialog</strong>
													</p>
													<p class="type-postfix">Add to bill</p>
												</div>
											</div>
										</label>
									</div> -->
									<!-- <div class="row" style="margin-left: 0px; margin-right: 0px;">
										<input type="radio" class="stv-radio-button"
											name="optionsRadios" value="5" id="button4" /> <label
											for="button4">
											<div class="row">
												<div class="col-md-4 pull-left">
													<img
														src="/susilawebpay/static/assets/images/MOBITEL_new.png"
														class="img-rounded" alt="Cinque Terre" width="50"
														height="50">
												</div>
												<div class="col-md-5 pull-right">
													<p>
														<strong>Mobitel</strong>
													</p>
													<p class="type-postfix">Add to bill</p>
												</div>
											</div>

										</label>
									</div> -->

									<!-- <div class="row" style="margin-left: 0px; margin-right: 0px;">
										<input type="radio" class="stv-radio-button"
											name="optionsRadios" value="8" id="button7" /> <label
											for="button7">
											<div class="row">
												<div class="col-md-4 pull-left">
													<img src="/susilawebpay/static/assets/images/hutch.jpg"
														class="img-rounded" alt="Cinque Terre" width="50"
														height="50">
												</div>
												<div class="col-md-5 pull-right">
													<p>
														<strong>Hutch</strong>
													</p>
													<p class="type-postfix">Add to bill</p>
												</div>
											</div>

										</label>
									</div>
 -->
									<!-- <div class="row" style="margin-left: 0px; margin-right: 0px;">
										<input type="radio" class="stv-radio-button"
											name="optionsRadios" value="9" id="button8" /> <label
											for="button8">
											<div class="row">
												<div class="col-md-4 pull-left">
													<img src="/susilawebpay/static/assets/images/Keells.jpg"
														class="img-rounded" alt="Cinque Terre" width="50"
														height="50">
												</div>
												<div class="col-md-5 pull-right">
													<p>
														<strong>Keells</strong>
													</p>
													<p class="type-postfix">Add to bill</p>
												</div>
											</div>

										</label>
									</div> -->

									<!-- <div class="row" style="margin-left: 0px; margin-right: 0px;">
                                            <input type="radio" class="stv-radio-button"
                                                   name="optionsRadios" value="6" id="button5" /> <label
                                                   for="button5">
                                                <div class="row">
                                                    <div class="col-md-4 pull-left">
                                                        <img src="/susilawebpay/static/img/authors/scratchcard.jpg"
                                                             class="img-rounded" alt="Cinque Terre" width="50"
                                                             height="50">
                                                    </div>
                                                    <div class="col-md-5 pull-right">
                                                        <p>
                                                            <strong>Use Scratch </strong>
                                                        </p>
                                                        <p>
                                                            <strong>Card code</strong>
                                                        </p>
                                                    </div>
                                                </div>
                                            </label>
                                        </div> -->
								</form>
							</div>
						</div>
					</div>
				</div>
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


	<script src="<c:url value='/static/js/bootstrap.min.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/static/vendors/iCheck/js/icheck.js'/>"></script>
	<script src="https://www.paypalobjects.com/api/checkout.js"></script>
	<script src="<c:url value='/static/js/globle.js'/>"
		type="text/javascript"></script>
	<script>
        
        function initialize() {
            console.log("initialize");
            sessionStorage.setItem('subscriptionPaymentId', ${subscriptionPaymentDto.getSubscriptionPaymentId()});
            sessionStorage.setItem('viewerId', ${subscriptionPaymentDto.getViewerId()});
            sessionStorage.setItem('language', "${subscriptionPaymentDto.getLanguage()}");
            sessionStorage.setItem('packageId', ${subscriptionPaymentDto.getPackageId()});
            sessionStorage.setItem('tokenHash', "${subscriptionPaymentDto.getTokenHash()}");
            sessionStorage.setItem('mobile', "${subscriptionPaymentDto.getMobile()}");
            sessionStorage.setItem('isFreeTrial', "${isFreeTrial}");
            
            $.i18n({
                locale: sessionStorage.getItem('language').toLowerCase()
            });
            $.i18n().load({
                en: './static/language/en.json',
                si: './static/language/si.json',
                ta: './static/language/ta.json'
            }).done(function() {
                console.log('done!')
                $("#title").text($.i18n('home-title'));
                $("#sub-title-1").text($.i18n('home-sub-title-1'));
                $("#sub-title-2").text($.i18n('home-sub-title-2'));
                $(".type-postfix").text($.i18n('home-type-postfix'));
            });
        }
        
        $(document).ready(() => {
        	  $('body').css('pointer-events', 'all') //activate all pointer-events on body
        })

        $('#button3').click(function () {
            sessionStorage.setItem('paymentId', '4');
            window.location.replace(baseURL + "/plan");
        });

        $('#button4').click(function () {
            sessionStorage.setItem('paymentId', "5");
            window.location.replace(baseURL + "/plan");
        });

        $('#button5').click(function () {
            sessionStorage.setItem('paymentId', $('#button5').val());
            window.location.replace(baseURL + "/scratch-card");
        });
        
        $('#button6').click(function () {
            sessionStorage.setItem('paymentId', '7');
            window.location.replace(baseURL + "/plan");
        });
        
        $('#button7').click(function () {
            sessionStorage.setItem('paymentId', '8');
            window.location.replace(baseURL + "/plan");
        });
        
        $('#button8').click(function () {
            sessionStorage.setItem('paymentId', '9');
            window.location.replace(baseURL + "/plan");
        });
        
        $('#button10').click(function () {
            sessionStorage.setItem('paymentId', '11');
            window.location.replace(baseURL + "/plan");
        });

        </script>
</body>
</html>