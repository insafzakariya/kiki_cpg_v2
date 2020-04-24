$(document).ready(function(){
    if(numberCheckGlobal.isMobitelConnection == "true"){
        var url = "https://services.mobitel.lk/MCCPortal/service/?compId=SusilaTV&reqType=ACTIVE&servId=SUWS&returnUrl=https://payment.kiki.lk/susilawebpay/mobitel/mobitelPay&bridgeID="+numberCheckGlobal.subscriptionPaymentId;
        window.location.href = url;
    }else if(numberCheckGlobal.isMobitelConnection == "false"){
        $("#confirmPay").addClass("hide");
        $("#inputDialogViuMobileNumber").addClass("hide");
        $("#mobitelConnectionError").removeClass("hide");
        $("#countryCode").addClass("hide");

        


    }

    $("#confirmPay").bind("click", payButtonClickEvent)
   // $(".radioOptions").bind("ifChecked", paymentOptionRadioButtonsChangeEvent)
    $("#inputScratchCard").bind("keydown", validateScratchCard);
    $("#inputScratchCard").bind("focusin", resetErorFields);
    $("#inputDialogViuMobileNumber").bind("focusin", resetMobileNoErrorFields);
    $("#unSubscribe").bind("click", unsubscribeButtonEvent);

});

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
function resetErorFields(){
    $("#cardError").addClass("hide");
}

function resetMobileNoErrorFields(){
    $(".errorField").addClass("hide");
}

function payButtonClickEvent(event){
   if(numberCheckGlobal.paymentMethodId == 6){
        scratchCardPayment();
   }else{
       numberCheck();
   }
}

function scratchCardPayment(){
    var data = {};
    data.card_code = $("#inputScratchCard").val();
    if(data.card_code == "" || typeof data.card_code == 'undefined'){
        $("#cardError").removeClass("hide");
        return;
    }
    $.ajax({
        type : "post",
        //  contentType: "application/json",
        url : "/susilawebpay/scratchCardPayment",
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
}

function validateScratchCard(e){
    if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
            // Allow: Ctrl/cmd+A
        (e.keyCode == 65 && (e.ctrlKey === true || e.metaKey === true)) ||
            // Allow: Ctrl/cmd+C
        (e.keyCode == 67 && (e.ctrlKey === true || e.metaKey === true)) ||
            // Allow: Ctrl/cmd+X
        (e.keyCode == 88 && (e.ctrlKey === true || e.metaKey === true)) ||
            // Allow: home, end, left, right
        (e.keyCode >= 35 && e.keyCode <= 39)) {
        // let it happen, don't do anything
        return;
    }
    // Ensure that it is a number and stop the keypress
    if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
        e.preventDefault();
    }
}

function numberCheck(event){
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
    parameters.isTrial = numberCheckGlobal.isTrial;
    var url = "";
    if(numberCheckGlobal.paymentMethodId == 4){
        url = "/susilawebpay/ajax/dialog/dialogViu/subscribe";
    }else{
        url = "/susilawebpay/mobitel/registration";
    }
    $.ajax({
        type : "post",
        //contentType: "application/json",
        url : url,
        data: parameters,
        success: function (data) {
            if(numberCheckGlobal.paymentMethodId == 4){
                dialogViuSubscribeSuccess(data);
            }else{
                hidePreLoader();
                $("#validateDiv").addClass("hide");
                $("#mobitelRegistration").removeClass("hide");
                var message = "";
                if(data.success) {

                    var message="KiKi "+data.code;
                    $("#registrationContent").append(message);
                    var messageBody = "sms:5455?body=Kiki ";
                    messageBody+=data.code;
                    $("#automaticSmsDiv").attr("href", messageBody);

                }else{
                    message = data.message;
                    $("#mobitelErrorMessage").empty();
                    $("#mobitelErrorMessage").append(message);
                }

            }

        },
        error: function(e){
            console.log('An error occurred while updating payment transaction : ', e);
        }
    });
}

function mobileNoValidations(mobileNo){

    var pattern =  /^\d{9}$/;  ///^0\d{9}$/;
    if(pattern.test(mobileNo)){
        var mobileSubstring = mobileNo.substring(0,2);
        if(numberCheckGlobal.paymentMethodId == 4) {
            if(mobileSubstring == "77" || mobileSubstring == "76") {
                return true;
            }
        }else{
            if(mobileSubstring == "70" || mobileSubstring == "71") {
                return true;
            }
        }
        return false;
    }else{
        return false;
    }

}

function dialogViuSubscribeSuccess(data){

    if(data.success){
        window.location.href = numberCheckGlobal.dialogPaymentUrl;//http://www.viu.lk/webSubscription/4";
    }else{
        var mobileNoError = $("#dialogViuBackEndError");
        $(mobileNoError).empty();
        $(mobileNoError).append(data.message);
        $(mobileNoError).removeClass("hide");
    }
    hidePreLoader();
}

function reAnimateLoadingIcon(){
    $(".preloader").css('display','block');
    $("#imgSrc").css('display','block');
    var imgsrc = $('#imgSrc').attr('src');
    $('#imgSrc').attr('src', '');
    $('#imgSrc').attr('src', imgsrc);
}

function hidePreLoader(){
    $(".preloader").css('display','none');
}