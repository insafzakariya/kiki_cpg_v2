<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Payment method selection</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
<link href="<c:url value='/static/css/unsubscribed.css'/>"
	rel="stylesheet">

</head>
<body>

	<div class="main_container">
		<div class="header_container">
			<img src="/susilawebpay/static/assets/images/smile.png">
			<h5 class="header">You have sucessfully unsubscribed</h5>

		</div>
		<div class="body_container">
			<input type="hidden" name="token" value="${paymentToken}"></>
			<button type="submit" class="form_submit" id="btn-home">Back to home</button>
		</div>
	</div>
<script src="<c:url value='/static/js/globle.js'/>" type="text/javascript"></script>
<script type="text/javascript">
	$("#btn-home").click(function(){
		var generalData = JSON.parse(sessionStorage.getItem("generalData"));
		var token = generalData.tokenHash;
		window.location.replace(baseURL + "/loading?token=" + token);
	});
</script>
</body>
</html>