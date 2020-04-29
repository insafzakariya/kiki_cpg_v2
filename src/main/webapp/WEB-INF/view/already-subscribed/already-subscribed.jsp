<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<title>Payment method selection</title>
	<link rel="shortcut icon" href="<c:url value='/static/img/favicon.ico'/>" />
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link href="<c:url value='/static/css/already-subscribed.css'/>" rel="stylesheet">
</head>

<body onload="initialize()">

<div class="main_container">
	<div class="header_container">
		<h5 class="header">Your Subscriptions</h5>
		<!-- <p class="tagline">Select your payment method to unlock access to exclusive content</p> -->
	</div>
	<div class="body_container">
		<form enctype="multipart/form-data" method="post">
		
				<!-- <input id="Dviu" class="Dviu" type="radio" name="payMethod" value="/ideabiz/ideabiz_unsubscribe"> -->
				<a href="" class="Mcash_wrapper_href" id="dviu">
					<div class="body_frame">
						<div style="width: 100%; display: inline-block;">
							<img src="/susilawebpay/static/assets/images/dialog-new.jpg" style="flex: 0 0 20%;">
							<p>Dialog<br/><span style="color: red;"> Click to unsubscribe</span></p>
						</div>
					</div>
				</a>
			
			<!-- <input id="Mobitel" class="Mobitel" type="radio" name="payMethod" > -->
				<%-- <input id="Mobitel" class="Mobitel" type="radio" name="payMethod" value="${pageContext.request.contextPath}/mobitel_unsubscribe?token=${token}"> --%>
				<a href="#" class="Mcash_wrapper_href" id="mobitel">
					<div class="body_frame">
					<div style="width: 100%; display: inline-block;">
						<img src="/susilawebpay/static/assets/images/mobitel.png" style="flex: 0 0 20%;">
						<p>Mobitel - Add to my bill<br/><span>Rs. 5 + Tax per day</span></p>
					</div>
				</div>
				</a>
				
			
			<button type="button" id="btnnewsubscription"  class="form_submit">
				ADD NEW SUBSCRIPTION5
			</button>
			
		</form>
	</div>
</div>
<script src="${pageContext.request.contextPath}/static/js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="<c:url value='/static/js/globle.js'/>" type="text/javascript"></script>

<script type="text/javascript" language=javascript> 

$("#btnnewsubscription").click(function(){
	window.location.replace(baseURL + "/load_payment_methods");
});

function initialize(){
	var generalData = JSON.parse(sessionStorage.getItem("generalData"));
	console.log(generalData);
	
	if(generalData.ideabizSubscription == 'subscribed'){
		$("#mobitel").addClass("hide");
	} else{
		$("#dviu").addClass("hide");
	}
	
}


$("#dviu").click(function () {
	var generalData = JSON.parse(sessionStorage.getItem("generalData"));
	var data = {
			token : generalData.tokenHash
	}
	$.post(baseURL + "/unsubscribed/ideabiz_unsubscribe", data, function (resp, error) {
		if(resp == true){
			window.location.replace(baseURL + "/unsubscribed");
		} else{
			window.location.replace(baseURL + "/unsubscribed");
		}
	});
});
/*var ideabiz= '${ideabiz_subscribed}';;
var mobitel= '${mobitel_subscribed}';;
var token= '${token}';;
*/
/* if(ideabiz=='' && mobitel==''){
	window.location.href = "/susilawebpay/home?token="+token;
} */
/*function selectID(IDS) {
  var IDxx = document.getElementById(IDS); 
  IDxx.checked = true;
  var value=$("input:radio[name=payMethod]:checked").val();
	if (value) {
		console.log(value);
		
		var data = {
				token : '${token}'
		}
		
		console.log(data);
		
		$.ajax({
	        type : "post",
	        //  contentType: "application/json",
	        url : "http://localhost:8080" +value,
	        data: data,
	        success: function (data) {
	            // alert('success');
	            document.open();
	            document.write(data);
	            document.close();
	        },
	        error: function(e){
	            // console.log('An error occurred while updating payment transaction : ', data);
	        }
	    });
	}else{
		alert('Please select one.');
	}
}*/

</script>


</body>
</html>