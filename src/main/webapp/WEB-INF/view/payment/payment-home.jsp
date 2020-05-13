<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>

<title>Payment Methodes</title>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
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

<!--end page level css-->
</head>

<body class="background-img">
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
									<div class="row" style="margin-left: 0px; margin-right: 0px;">
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
													<p>Add to bill</p>
												</div>
											</div>
										</label>
									</div>
									<div class="row" style="margin-left: 0px; margin-right: 0px;">
										<input type="radio" class="stv-radio-button"
											name="optionsRadios" value="5" id="button4" /> <label
											for="button4">
											<div class="row">
												<div class="col-md-4 pull-left">
													<img src="/susilawebpay/static/img/authors/madd.jpg"
														class="img-rounded" alt="Cinque Terre" width="50"
														height="50">
												</div>
												<div class="col-md-5 pull-right">
													<p>
														<strong>Mobitel</strong>
													</p>
													<p>
														<strong>Add to bill</strong>
													</p>
												</div>
											</div>

										</label>
									</div>


									<div class="row" style="margin-left: 0px; margin-right: 0px;">
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
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
	<script src="<c:url value='/static/js/jquery-1.11.3.min.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/static/js/bootstrap.min.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/static/vendors/iCheck/js/icheck.js'/>"></script>
	<script src="https://www.paypalobjects.com/api/checkout.js"></script>
	<script src="<c:url value='/static/js/globle.js'/>" type="text/javascript"></script>
	<script>
	
	 	$('#button3').click(function(){
	 		sessionStorage.setItem('paymentId', $('#button3').val());
	 		window.location.replace(baseURL + "/plan");
	 	});
	 	
	 	$('#button4').click(function(){
	 		sessionStorage.setItem('paymentId', $('#button4').val());
	 		window.location.replace(baseURL + "/plan");
	 	});
	 	
	 	$('#button5').click(function(){
	 		sessionStorage.setItem('paymentId', $('#button5').val());
	 		window.location.replace(baseURL + "/screch-card");
	 	});
		
	</script>

</body>

</html>
