/**
 * 
 */
function initialize(paymentId) {

	$
			.get(
					"http://localhost:8080/susilawebpay/payment-type/4",
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
												inner += "</p></div></div>";

											});
							console.log(inner);
							$("#package_type_container").append(inner);
						} else {
							console.log("Error");
						}

					});

}

function checkNumber(day, methodId, subscriptionId) {
	window.location.replace("http://localhost:8080/susilawebpay/number_verification/" + subscriptionId +"/" + day + "/" + methodId);
}
