$(document).ready(function(){
        var url = "https://services.mobitel.lk/MCCPortal/service/?compId=SusilaTV&reqType=ACTIVE&servId=SUWS&returnUrl=https://payment.kiki.lk/susilawebpay/mobitel/mobitelPay&bridgeID="+localStorage.getItem("subscriptionPaymentId");
        console.log(url);
        window.location.href = url;
        $("#confirmPay").addClass("hide");
        $("#inputDialogViuMobileNumber").addClass("hide");
        $("#mobitelConnectionError").removeClass("hide");
        $("#countryCode").addClass("hide");

    $("#confirmPay").bind("click", numberCheck)
    $("#unSubscribe").bind("click", unsubscribeButtonEvent);

});

function numberCheck(event) {
	var parameters = {};
	var mobileNo = $("#inputDialogViuMobileNumber").val();
	if (mobileNo == "") {
		$("#dialogViuMobileNumberEmpty").removeClass("hide");
		return;
	} else {
		mobileNo = mobileNo.trim(mobileNo);
		if (!mobileNoValidations(mobileNo)) {
			if (numberCheckGlobal.paymentMethodId == 4) {
				$("#dialogViuMobileNumberIncorrect").removeClass("hide");
			} else {
				$("#mobitelAddToBillNumberIncorrect").removeClass("hide");
			}
			return;
		}
	}
	reAnimateLoadingIcon();
	parameters.mobileNo = mobileNo;
	parameters.isTrial = numberCheckGlobal.isTrial;
	var url = "";

	url = "/susilawebpay/mobitel/registration";

	$.ajax({
		type : "post",
		// contentType: "application/json",
		url : url,
		data : parameters,
		success : function(data) {

			hidePreLoader();
			$("#validateDiv").addClass("hide");
			$("#mobitelRegistration").removeClass("hide");
			var message = "";
			if (data.success) {

				var message = "KiKi " + data.code;
				$("#registrationContent").append(message);
				var messageBody = "sms:5455?body=Kiki ";
				messageBody += data.code;
				$("#automaticSmsDiv").attr("href", messageBody);

			} else {
				message = data.message;
				$("#mobitelErrorMessage").empty();
				$("#mobitelErrorMessage").append(message);
			}

		},
		error : function(e) {
			console.log(
					'An error occurred while updating payment transaction : ',
					e);
		}
	});
}

function mobileNoValidations(mobileNo) {

	var pattern = /^\d{9}$/; // /^0\d{9}$/;
	if (pattern.test(mobileNo)) {
		var mobileSubstring = mobileNo.substring(0, 2);
		if (numberCheckGlobal.paymentMethodId == 4) {
			if (mobileSubstring == "77" || mobileSubstring == "76") {
				return true;
			}
		} else {
			if (mobileSubstring == "70" || mobileSubstring == "71") {
				return true;
			}
		}
		return false;
	} else {
		return false;
	}

}

function reAnimateLoadingIcon() {
	$(".preloader").css('display', 'block');
	$("#imgSrc").css('display', 'block');
	var imgsrc = $('#imgSrc').attr('src');
	$('#imgSrc').attr('src', '');
	$('#imgSrc').attr('src', imgsrc);
}

function hidePreLoader() {
	$(".preloader").css('display', 'none');
}

function unsubscribeButtonEvent(){
    var parameters = {};
    var mobileNo = $("#inputDialogViuMobileNumber").val();
    if(mobileNo == ""){
        $("#dialogViuMobileNumberEmpty").removeClass("hide");
        return;
    }else{
        mobileNo = mobileNo.trim(mobileNo);
        if(!mobileNoValidations(mobileNo)){
            if(numberCheckGlobal.paymentMethodId == 4) {
                $("#dialogViuMobileNumberIncorrect").removeClass("hide");
            }else{
                $("#mobitelAddToBillNumberIncorrect").removeClass("hide");
            }
            return;
        }
    }
    reAnimateLoadingIcon();
    parameters.mobileNo = mobileNo;
    $.ajax({
        type : "post",
        //contentType: "application/json",
        url : "/susilawebpay/mobitel/user/unsubscribe",
        data: parameters,
        success: function (data) {
                document.open();
                document.write(data);
                document.close();

        },
        error: function(e){
            console.log('An error occurred while updating payment transaction : ', e);
        }
    });
}