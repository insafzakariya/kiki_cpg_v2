<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

	<head>
		<title>Validate Screen</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="<c:url value='/static/img/favicon.ico'/>" />
		<link href="<c:url value='/static/css/bootstrap.min.css'/>"	rel="stylesheet">
		<link href="<c:url value='/static/css/buttons_sass.css'/>" rel="stylesheet">
		<link href="<c:url value='/static/css/advbuttons.css'/>" rel="stylesheet">
		<link href="<c:url value='/static/css/lockscreen2.css'/>" rel="stylesheet">
		<link href="<c:url value='/static/vendors/iCheck/css/all.css'/>" rel="stylesheet" />
		<link rel="stylesheet" type="text/css" href="<c:url value='/static/vendors/awesomebootstrapcheckbox/css/awesome-bootstrap-checkbox.css'/>">
	</head>

	<body class="background-img">
		<div class="preloader">
			<div class="loader_img">
				<img src="<c:url value='/static/img/loader.gif' />" alt="loading..." height="64" width="64">
			</div>
		</div>
		<div class="container">
			<div class="row">
				<div class="col-md-6 col-md-offset-3">
					<div class="lockscreen-container">
						<div id="output"></div>
						<div class="user-name">
							<h4 class="text-center">KiKi</h4>
							<small>Mobile Payment Gateway</small>
						</div>
						<div class="avatar"></div>
						<div class="form-box">
	
							<div class="form" id="validateDiv">
	
								<div class="form-box">
									<div id="scratchCardDiv" style="margin-top: 5px;">
										<div>Enter your code</div>
										<input id="inputScratchCard"
											style="color: black; margin-top: 0px; margin-left: 0px; width: 70%;"
											type="number">
										<div id="cardError" class="hide" style="color: red">you
											must enter the code to proceed</div>
									</div>
								</div>
								<div class="panel-body">
									<!-- radio -->
	
									<div class="form-group dropdown-container">
										<button id="confirmPay"
											class="button button-rounded button-primary-flat hvr-hang">Proceed
										</button>
	
									</div>
								</div>
							</div>
						</div>
	
					</div>
				</div>
			</div>
		</div>
		<script src="<c:url value='/static/js/jquery-3.5.1.min.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/static/js/bootstrap.min.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/static/js/lockscreen2.js'/>"></script>
		<script src="<c:url value='/static/vendors/iCheck/js/icheck.js'/>"></script>
		<script src="<c:url value='/static/js/globle.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/static/js/view/scratch-card.js' />"></script>
	</body>
</html>
