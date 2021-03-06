<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Payment method selection</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet" type="text/css"
	href="<c:url value= 'static/css/main.css'/>">
<style type="text/css">
ul {
	list-style: none;
}

ul li::before {
	content: "\2022";
	color: #24A9A6;
	font-weight: bold;
	display: inline-block;
	width: 1em;
	margin-left: -1em;
}
</style>
</head>
<body onload="initialize()">
	<div class="main_container">
		<div class="header_container">
			<h5 id="title" class="header">Choose package</h5>

		</div>
		<div class="body_container" style="padding: 10px;">
			<div id="package_type_container">
				<div style="background: #24A9A6; height: 2px; margin-bottom: 20px;"></div>



			</div>
			<div id="div-trial" class="hide" style="margin-top: 20px;">

				<div id="div-telco" class="hide"
					style="color: white; text-align: left;">
					<ul>
						<li id="li-1">Subscribe to enjoy 3 days free trial</li>
						<li id="li-2">You won't be charged any subscription fees until the end
							of your free trial period</li>
						<li id="li-3">Cancel anytime</li>
					</ul>
				</div>
				<div id="div-hnb" class="hide">
					<span style="color: red; text-align: center;" id="warning-1">Please note
						that free trial for HNB is currently unavailable.</span>
				</div>
				<div id="div-keells" class="hide">
					<span style="color: red; text-align: center;" id="warning-2">Please note
						that free trial is not available for Keells pay.</span>
				</div>

				<div
					style="background: #24A9A6; height: 2px; margin-bottom: 20px; margin-top: 20px;"></div>

			</div>

		</div>
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
		function initialize() {
			
			$.i18n({
                locale: sessionStorage.getItem('language').toLowerCase()
            });
            $.i18n().load({
                en: './static/language/en.json',
                si: './static/language/si.json',
                ta: './static/language/ta.json'
            }).done(function() {
                console.log('done!')
                $("#title").text($.i18n('plan-title'));
                $("#li-1").text($.i18n('plan-free-trial-1'));
                $("#li-2").text($.i18n('plan-free-trial-2'));
                $("#li-3").text($.i18n('plan-free-trial-3'));
                $("#warning-1").text($.i18n('plan-free-trial-4'));
                $("#warning-2").text($.i18n('plan-free-trial-5'));
            });
			
			var isTrial = false;
			if (sessionStorage.getItem('isFreeTrial') == "true") {
				$("#div-trial").removeClass("hide");
				if (sessionStorage.getItem('paymentId') == '4'
						|| sessionStorage.getItem('paymentId') == '5'
						|| sessionStorage.getItem('paymentId') == '8') {
					$("#div-telco").removeClass("hide");
					isTrial = true;
				}
				if (sessionStorage.getItem('paymentId') == '7') {
					$("#div-hnb").removeClass("hide");
				}
				if (sessionStorage.getItem('paymentId') == '9') {
					$("#div-keells").removeClass("hide");
				}
			}

			var paymentId = sessionStorage.getItem('paymentId');
			var lang = sessionStorage.getItem('language');

			var url = baseURL + "/rest/plan/planlist/" + paymentId;
			if (lang != null) {
				url = url + "/" + lang;
			}

			console.log(url);

			$
					.get(
							url,
							function(data, status) {
								console.log(status);
								if (status == "success") {
									console.log(data);
									var inner = "";
									$
											.each(
													data,
													function(i, item) {

														console.log(item);
														inner += "<div onClick = \"checkNumber("
																+ item.day
																+ ","
																+ item.paymentMethodId
																+ ","
																+ paymentId
																+ ","
																+ item.id
																+ ","
																+ item.amount
																+ ")\" class=\"body_frame\"> "
																+ "<div style=\"width: 100%; display: inline-block;\">"
																+ "<img src= \"\" style=\"flex: 0 0 20%;\"> "
																+ "<p>"
																+ item.name
																+ "<br /> "
																+ "<span>"
																+ item.value
																+ "</span> ";
														if (item.offer != null
																&& item.offer.length > 0) {
															inner += "<br/><span style=\"color: red; font-weight: bold;\">"
																	+ item.offer
																	+ "</span>";
														}
														inner += "</p>"

														if (isTrial) {
															inner += "<p style=\"float: right; color: #FFD800;\">"
																	+ item.trialText
																	+ "<br/><span style=\"color: #FFD800; font-size: 0.8em;\">Free Trial</span></p>"
														}

														inner += "</div></div>";

													});
									console.log(inner);
									$("#package_type_container").append(inner);
								} else {
									console.log("Error");
								}

							});

		}

		function checkNumber(day, methodId, paymentId, planId, amount) {
			sessionStorage.setItem('day', day);
			sessionStorage.setItem('methodId', methodId);
			sessionStorage.setItem('paymentId', paymentId);
			sessionStorage.setItem('planId', planId);
			sessionStorage.setItem('amount', amount);

			window.location.replace(baseURL + "/number_verify");
		}
	</script>

</body>
</html>