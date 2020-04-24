<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<title>Payment method selection</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
</head>
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
	.body_frame {
		background-color: #00cc00;
		height: 55px;
		padding: 10px;
		border-radius: 4px;
		text-align: left;
		margin-top: 15px;
	}
	.body_frame:hover {
		background-color: #ffffff;
	}
	.body_frame:hover.body_frame p,.body_frame:hover.body_frame p span {
		color: #000000;
	}
	.body_frame img {
		position: relative;
    	max-height: 55px;
    	left: 0;
	}
	.body_frame p {
		flex: 0 0 70%;
	    display: inline-block;
	    top: 0;
	    bottom: 0;
	    color: #fff;
	    vertical-align: top;
	    font-size: 14px;
	    font-weight: 600;
	    margin-left: 5px;
	}
	.body_frame p span {
		font-size: 12px;
		font-weight: 300;
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
	
	/*radio button styles*/

	input[type="radio"] {
		position: absolute;
		visibility: hidden;
	}
	input:checked + a div.body_frame {
		background-color:#ffffff;
	}
	input:checked + a p {
		color:#000000;
	}
</style>
<body>

<div class="main_container">
	<div class="header_container">
		<h5 class="header">Your Subscriptions</h5>
		<!-- <p class="tagline">Select your payment method to unlock access to exclusive content</p> -->
	</div>
	<div class="body_container">
		<form enctype="multipart/form-data" method="post">
		
			<c:if test="${not empty ideabiz_subscribed}">
				<input id="Dviu" class="Dviu" type="radio" name="payMethod" value="/ideabiz/ideabiz_unsubscribe">
				<a href="" class="Mcash_wrapper_href" onClick="selectID('Dviu'); return false;">
					<div class="body_frame">
						<div style="width: 100%; display: inline-block;">
							<img src="${pageContext.request.contextPath}/static/assets/images/dialog-new.jpg" style="flex: 0 0 20%;">
							<p>Dialog<br/><span style="color: red;"> Click to unsubscribe</span></p>
						</div>
					</div>
				</a>
				
			</c:if>
			
			<c:if test="${not empty mobitel_subscribed}">
				<input id="Mobitel" class="Mobitel" type="radio" name="payMethod" value="${pageContext.request.contextPath}/mobitel_unsubscribe?token=${token}">
				<a href="#" class="Mcash_wrapper_href" onClick="selectID('Mobitel'); return false;">
					<div class="body_frame">
					<div style="width: 100%; display: inline-block;">
						<img src="${pageContext.request.contextPath}/static/assets/images/mobitel.png" style="flex: 0 0 20%;">
						<p>Mobitel - Add to my bill<br/><span>Rs. 5 + Tax per day</span></p>
					</div>
				</div>
				</a>
				
			</c:if>
			
			<button type="button"  onclick="location.href = 'home?token=${token}&type=new';" class="form_submit">
				ADD NEW SUBSCRIPTION5
			</button>
			
			
			
			
			
			
		</form>
	</div>
</div>
<script src="${pageContext.request.contextPath}/static/js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script type="text/javascript" language=javascript> 
var ideabiz= '${ideabiz_subscribed}';;
var mobitel= '${mobitel_subscribed}';;
var token= '${token}';;
/* if(ideabiz=='' && mobitel==''){
	window.location.href = "/susilawebpay/home?token="+token;
} */
function selectID(IDS) {
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
}

</script>


</body>
</html>