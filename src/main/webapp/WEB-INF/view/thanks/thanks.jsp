<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Payment method selection</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link href="<c:url value='/static/css/thanks.css'/>" rel="stylesheet">

</head>
<body>
	<c:choose>
		<c:when test="${type eq 4}">
			<div class="main_container">
				<div class="header_container">
					<img src="<c:url value='/static/assets/images/smile.png'/>">
					<h5 class="header header-type-1">Thank you for activating KIKI</h5>
					<p class="tagline tagline-type-1">Your payment has been confirmed successfully</p>
				</div>

			</div>
		</c:when>
		<c:when test="${type eq 5}">
			<div class="main_container">
				<div class="header_container">
					<img src="<c:url value='/static/assets/images/smile.png'/>">
					<h5 class="header header-type-1">Thank you for activating KIKI</h5>
					<p class="tagline tagline-type-1">Your payment has been confirmed successfully</p>
				</div>

			</div>
		</c:when>
		<c:when test="${type eq 6}">
			<div class="main_container">
				<div class="header_container">
					<img src="<c:url value='/static/assets/images/smile.png'/>">
					<h5 class="header header-type-1">Thank you for activating KIKI</h5>
					<p class="tagline tagline-type-1">Your payment has been confirmed successfully</p>
				</div>

			</div>
		</c:when>
		<c:when test="${type eq 7}">
			<div class="main_container">
				<div class="header_container">
					<img src="<c:url value='/static/assets/images/smile.png'/>">
					<h5 class="header header-type-1">Thank you for activating KIKI</h5>
					<p class="tagline tagline-type-1">Your payment has been confirmed successfully</p>
				</div>

			</div>
		</c:when>

		<c:when test="${type eq 8}">
			<div class="main_container">
				<div class="header_container">
					<img src="<c:url value='/static/assets/images/smile.png'/>">
					<h5 class="header header-type-1">Thank you for activating KIKI</h5>
					<p class="tagline tagline-type-1">Your payment has been confirmed successfully</p>
				</div>

			</div>
		</c:when>

		<c:when test="${type eq 9}">
			<div class="main_container">
				<div class="header_container">
					<img src="<c:url value='/static/assets/images/smile.png'/>">
					<h5 class="header header-type-1">Thank you for activating KIKI</h5>
					<p class="tagline">
						<span id="span-1">Dear subscriber, </span> <br /> 
						<span id="span-2">
						Thank you for subscribing to KiKi. Please
						be informed that your subscription top-up through Keells will at
						least take 10 minutes to be verified. If failed please do call us
						via the hotline available on the payment invoice.
						</span>  <br />
						<span id="span-3">KiKi team</span>
					</p>
				</div>

			</div>
		</c:when>
		<c:when test="${type eq 10}">
			<div class="main_container">
				<div class="header_container">
					<img src="<c:url value='/static/assets/images/smile.png'/>">
					<h5 class="header header-type-2">You have successfully unsubscribed</h5>
				</div>

			</div>
		</c:when>

		<c:when test="${type eq 11}">
			<div class="main_container">
				<div class="header_container">
					<img src="<c:url value='/static/assets/images/smile.png'/>">
					<h5 class="header header-type-3">You have successfully Transfered.</h5>
				</div>

			</div>
		</c:when>
		<c:when test="${type eq 12}">
			<div class="main_container">
				<div class="header_container">
					<img src="<c:url value='/static/assets/images/smile.png'/>">
					<h5 class="header header-type-4">Your account Activated Successfully</h5>
				</div>

			</div>
		</c:when>

	</c:choose>

	<div class="body_container">
		<button type="submit" class="form_submit" id="btn-home">Back
			to home</button>
	</div>


	<script src="<c:url value='/static/js/jquery-3.5.1.min.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/static/js/i18n/CLDRPluralRuleParser.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/static/js/i18n/jquery.i18n.js'/>"
		type="text/javascript"></script>
	<script
		src="<c:url value='/static/js/i18n/jquery.i18n.messagestore.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/static/js/i18n/jquery.i18n.fallbacks.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/static/js/i18n/jquery.i18n.parser.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/static/js/i18n/jquery.i18n.emitter.js'/>"
		type="text/javascript"></script>
	<script
		src="<c:url value='/static/js/i18n/jquery.i18n.emitter.bidi.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/static/js/i18n/jquery.i18n.language.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/static/js/globle.js'/>"
		type="text/javascript"></script>
	<script type="text/javascript">
		$.i18n({
			locale : sessionStorage.getItem('language').toLowerCase()
		});
		$.i18n().load({
			en : './../static/language/en.json',
			si : './../static/language/si.json',
			ta : './../static/language/ta.json'
		}).done(function() {
			console.log('done!')
			$("#span-1").text($.i18n('thanks-span-1'));
			$("#span-2").text($.i18n('thanks-span-2'));
			$("#span-3").text($.i18n('thanks-span-3'));
			$(".header-type-1").text($.i18n('thanks-header-1'));
			$(".header-type-2").text($.i18n('thanks-header-2'));
			$(".header-type-3").text($.i18n('thanks-header-3'));
			$(".header-type-4").text($.i18n('thanks-header-4'));
			$(".tagline-type-1").text($.i18n('thanks-tagline-type-1'));
			$("#btn-home").text($.i18n('thanks-btn-home'));
		});

		$("#btn-home").click(function() {
			var token = sessionStorage.getItem("tokenHash");
			window.location.replace(baseURL + "/thanks/redirecthome");
		});
	</script>
</body>
</html>