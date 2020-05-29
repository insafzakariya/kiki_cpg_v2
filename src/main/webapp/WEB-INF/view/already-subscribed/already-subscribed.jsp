<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="ISO-8859-1">
        <title>ALREADY SUBSCRIBED</title>
        <link rel="shortcut icon" href="<c:url value='/static/img/favicon.ico'/>" />
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<link href="<c:url value='/static/css/already-subscribed.css'/>" rel="stylesheet">
    </head>
    <body onload="initialize()">
	    <div class="header_container">
			<h5 class="header">Your Subscriptions</h5>
		</div>
		
		<div class="body_container">
			<c:choose>
				<c:when test="${subscriptionType eq 'IDEABIZ'}">
					<a href="" class="Mcash_wrapper_href" id="dviu">
						<div class="body_frame">
							<div style="width: 100%; display: inline-block;">
								<img src="/susilawebpay/static/assets/images/dialog-new.jpg" style="flex: 0 0 20%;">
								<p>Dialog<br/><span style="color: red;"> Click to unsubscribe</span></p>
							</div>
						</div>
					</a>
				</c:when>
				<c:when test="${subscriptionType eq 'MOBITEL'}">
					<a href="#" class="Mcash_wrapper_href" id="mobitel">
						<div class="body_frame">
							<div style="width: 100%; display: inline-block;">
								<img src="/susilawebpay/static/assets/images/mobitel.png" style="flex: 0 0 20%;">
								<p>Mobitel - Add to my bill<br/><span>Rs. 5 + Tax per day</span></p>
							</div>
						</div>
					</a>
				</c:when>
			</c:choose>
		
			
			
			
			
			<button type="button" id="btnnewsubscription"  class="form_submit">
				ADD NEW SUBSCRIPTION5
			</button>
			
			
		</div>
        

		
		<script src="<c:url value='/static/js/jquery-1.11.3.min.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/static/js/globle.js'/>" type="text/javascript"></script>
		<script type="text/javascript">
			$("#btnnewsubscription").click(function(){
				var token = "${subscriptionPaymentDto.getTokenHash()}";
				window.location.replace(baseURL + "/home?token="+token+"&type=new");
			});
			
			function initialize() {
	            console.log("initialize");
	            sessionStorage.setItem('subscriptionPaymentId', ${subscriptionPaymentDto.getSubscriptionPaymentId()});
	            sessionStorage.setItem('viewerId', ${subscriptionPaymentDto.getViewerId()});
	            sessionStorage.setItem('packageId', ${subscriptionPaymentDto.getPackageId()});
	            sessionStorage.setItem('tokenHash', "${subscriptionPaymentDto.getTokenHash()}");
	            sessionStorage.setItem('mobile', "${subscriptionPaymentDto.getMobile()}");
	        }
			
			$("#dviu").click(function () {
				var data = {
					subscriptionPaymentId : sessionStorage.getItem('subscriptionPaymentId'),
					viewerid : sessionStorage.getItem('viewerId'),
					type : 4,
					mobile : sessionStorage.getItem('mobile')
				}
				
				console.log("send Req");
				
				$.ajax({
					type : "post",
					contentType : "application/json",
					dataType : 'json',
					url : baseURL + "/rest/unsubscribe",
					data : JSON.stringify(data),
					success : function(resp) {
						console.log(resp);
						if(resp.status == 'success'){
							window.location.replace(baseURL +"/thanks/10");
						} else{
							window.location.replace(baseURL + "/error-page?message="+ resp.status );
						}
						
					},
					error : function(e) {
						console.log(e);
						window.location.replace(baseURL + "/error-page?message=" + e);
					}
				});
			});
			
			$("#mobitel").click(function () {
				var data = {
					subscriptionPaymentId : sessionStorage.getItem('subscriptionPaymentId'),
					viewerid : sessionStorage.getItem('viewerId'),
					type : 5,
					mobile : sessionStorage.getItem('mobile')
				}
				
				$.ajax({
					type : "post",
					contentType : "application/json",
					dataType : 'json',
					url : baseURL + "/rest/unsubscribe",
					data : JSON.stringify(data),
					success : function(resp) {
						console.log(resp);
						if(resp.status == 'success'){
							window.location.replace(baseURL +"/thanks/10");
						} else{
							window.location.replace(baseURL + "/error-page?message=" + resp.status );
						}
						
					},
					error : function(e) {
						console.log(e);
						window.location.replace(baseURL + "/error-page?message="+ e);
					}
				});
			});
			
		</script> 
    </body>
</html>