<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Payment method selection</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet" type="text/css" href="static/css/main.css">
</head>
<body onload="initialize(${subscriptionPaymentId})">
	<div class="main_container">
		<div class="header_container">
			<h5 class="header">Choose package</h5>
		</div>
		<div class="body_container">
			<div id="package_type_container"></div>

		</div>
	</div>
	<script
		src="${pageContext.request.contextPath}/static/js/jquery-1.11.3.min.js"
		type="text/javascript"></script>
	<script
		src="${pageContext.request.contextPath}/static/js/view/payment-type.js"
		type="text/javascript"></script>

	<!-- <script type="text/javascript" language=javascript>
		function selectID(IDS) {
			var IDxx = document.getElementById(IDS);
			IDxx.checked = true;
			var value = $("input:radio[name=payMethod]:checked").val();
			if (value) {
				window.location.replace(value);
			} else {
				alert('Please select your package to unsubscribe.');
			}
		}

		$("#form_submit_unsubscrib").click(function() {
			var value = $("input:radio[name=payMethod]:checked").val();
			if (value) {
				window.location.replace(value);
			} else {
				alert('Please select your package to unsubscribe.');
			}

		})
	</script> -->


</body>
</html>