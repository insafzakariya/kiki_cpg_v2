<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Payment method selection</title>
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<link href="<c:url value='/static/css/otp.css'/>" rel="stylesheet">
	</head>
	<body onload="pay()">
		<div class="main_container">
	
			<div class="header_container">
				<h5 class="header"></h5>
				<p class="tagline"></p>
			</div>
			<div id="loader" class="preloader" style="padding-top: 10px;"gif-load">
				<div class="loader_img">
					<img id="imgSrc" src="<c:url value='/static/img/ajax-loader.gif'/>" alt="loading..." height="64" width="64">
				</div>
			</div>
			<div id="mobitelConnectionError" class="hide errorField"
				style="color: red">Please use a mobitel Data connection to subscribe</div>
		</div>
		
		<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
		<script src="<c:url value='/static/js/jquery-1.11.3.min.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/static/js/globle.js'/>" type="text/javascript"></script>
		<script type="text/javascript">
			function pay() {
				console.log("load");
				
	
				var mobile = sessionStorage.getItem("mobile");
				var subscriptionPaymentId = sessionStorage.getItem("subscriptionPaymentId");
				var day = sessionStorage.getItem("day");
				var viewerId = sessionStorage.getItem("viewerId");
				
				console.log(mobile);
				var data = {
					viewerId : viewerId,
					activationStatus : 1,
					mobileNo : mobile,
					subscriptionPaymentId : subscriptionPaymentId,
					days : day
				}
				console.log(data);
	
				/*$.post(baseURL + "/mobitel/payment", data,
						function(result) {
							console.log(result);
							if (result == 'success') {
								console.log("success");
								window.location.replace(baseURL + "/success/5");
							} else {
								console.log("error");
								alert("");
								$("#loader").addClass("hide");
								$("#mobitelConnectionError").removeClass("hide");
								window.location.replace(baseURL + "/error-page");
							}
						}).fail(function(result) {
							console.log(result);
							alert("");
					window.location.replace(baseURL + "/error-page");
				});*/
	
				$.ajax({
					type : "post",
					contentType : "application/json",
					dataType : 'json',
					url : baseURL + "/mobitel/payment",
					data : JSON.stringify(data),
					success : function(resp) {
						console.log(resp);
						if(resp.status == 'success'){
							console.log("success");
							window.location.replace(baseURL + "/thanks/5");
						} else if(resp.status == 'transfered'){
							console.log("transfered");
							window.location.replace(baseURL + "/thanks/11");
						}else if(resp.status == 'Activated'){
							console.log("Activated");
							window.location.replace(baseURL + "/thanks/12");
						} else{
							console.log("error");
							$("#loader").addClass("hide");
							$("#mobitelConnectionError").removeClass("hide");
							window.location.replace(baseURL + "/error-page?message=" + resp.status);
						}
					},
					error : function(e) {
						console.log(e);
					}
				});
			}
		</script>
	</body>
</html>
