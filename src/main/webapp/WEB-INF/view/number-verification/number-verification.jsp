<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
	<title>Payment method selection</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link href="<c:url value='/static/css/number-verification.css'/>" rel="stylesheet">
</head>
<body onload="initialize()">
<div class="main_container">
	<div class="header_container">
		<h5 class="header">Please enter your number below</h5>
		<!-- <p class="tagline">You will get a verification code to given mobile number after you this form</p> -->
	</div>
	<div class="body_container">
		
			<div class="main_number_input">
				<input type="text" name="" placeholder="Please enter your mobile number"  id="mobile_no">
			</div>
			<input class="form_submit" type="button" id="btn_proceed" value="PROCEED">
				
		
	</div>
</div>

</body>
<!-- global js -->
 <script src="<c:url value='/static/js/jquery-1.11.3.min.js'/>" type="text/javascript"></script>
 <script src="<c:url value='/static/js/globle.js'/>" type="text/javascript"></script>
 
 <script type="text/javascript">
 	function initialize() {
 		var obj = JSON.parse(sessionStorage.getItem("generalData"));
		$("#mobile_no").val(obj.mobile);
	}
 	
 	$("#btn_proceed").click(function() {
 		var method = sessionStorage.getItem("methodId");
 		var mobile_no = $('#mobile_no').val();
 		
			$.get(baseURL + "/rest/common/verifyNumber/" + method + "/" + mobile_no, function(resp, status) {
				console.log(resp);
				console.log(status);
				if (status == "success") {
					if(method == 4){
						var day = sessionStorage.getItem("day");
						$.get(baseURL + "/otp/send/" + mobile_no + "/" + day, function(resp, status){
							sessionStorage.setItem('server_ref', resp.SERVER_REF);
							window.location.replace(baseURL + "/otp_verification");
						});	
					}
				} else {
					console.log(status);
				}
			});
		
 	});
 </script>
 
</html>




