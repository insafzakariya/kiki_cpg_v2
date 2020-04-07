<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
	<title>Payment method selection</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
</head>
<body>

<style type="text/css">
	body {
		background-color: #1a1a1a;
		font-family: Arial, "Helvetica Neue", Helvetica, sans-serif;
		margin-left: auto;
		margin-right: auto;
		max-width: 95%;
		text-align: center;
	}
	.topbar_container {
		text-align: left;
	}
	.main_container {
		margin-top: 35px;
	}
	.header_container {
		text-align: center;
	}
	.header_container h5.header {
		color: #ffffff;
		font-size: 15px;
		font-weight: 600;
	}
	.header_container p.tagline {
		color: #ffffff;
		font-size: 13px;
	}
	.body_container {
		margin-top: 25px;
	}
	.main_number_input input {
		height: 40px;
	    width: 100%;
	    margin-left: 0;
	    margin-right: 0;
	    left: 0;
	    right: 0;
	    display: inline-block;
	    background-color: rgb(204, 204, 204, 0.3);
	    border: none;
	    opacity: 1;
	    color: #ffffff;
	    font-size: 15px;
	    border-radius: 2px;
	    text-align: center;
	}
	.form_submit {
		background-color: #009b9f;
	    color: #ffffff;
	    margin-top: 25px;
	    width: 100%;
	    text-align: center;
	    border: none;
	    padding-top: 15px;
	    padding-bottom: 15px;
	    border-radius: 2px;
	    font-size: 14px;
	    font-weight: 600;
	    text-transform: uppercase;
	}

	@media screen and (min-width: 360px) {
		body {
			margin-left: auto;
			margin-right: auto;
			max-width: 340px;
			text-align: center;
		}
	}
</style>
<div class="main_container">
	<div class="header_container">
		<h5 class="header">Please enter your number below</h5>
		<!-- <p class="tagline">You will get a verification code to given mobile number after you this form</p> -->
	</div>
	<div class="body_container">
		
			<div class="main_number_input">
				<input type="text" name="" placeholder="Please enter your mobile number" value="${mobielNo}" id="mobile_no">
				<INPUT TYPE="hidden" VALUE="${subscriptionPaymentId}" id="subscriptionPaymentId">
				<INPUT TYPE="hidden" VALUE="${day}" id="day">
			</div>
			<button class="form_submit"  id="btn_proceed">PROCEED</button>
				
			</input>
		
	</div>
</div>

</body>
<!-- global js -->
 <script src="<c:url value='/static/js/jquery-1.11.3.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/static/js/numberverify.js' />"></script>
</html>




