/**
 * Created by Chamil on 12/23/2018.
 */
$(document).ready(function(){
    // this bind is related to trial period jsp
    $(".payment-trial").bind("click", trialPeriodSelectionEvent);
    $("#confirmPay").bind("click", proceedEvent);
});

// select the relavent trial period
function trialPeriodSelectionEvent(event){
    $(".payment-trial").removeClass("selected-trial-box");
    $(this).addClass("selected-trial-box");
}

function proceedEvent(event){

    var selectedElement = $(".selected-trial-box").prop("id");

    if(selectedElement == "dialogBox"){
        initiateDilalogPayment();
    }else{
        initiateMobitelTrialPeriod();
    }

}

function initiateDilalogPayment() {
    var paymentMethodId = 4;
    //parameters.isDialogViuPayment = true;
    var url = "/susilawebpay/validatePayment?paymentMethodId=" + paymentMethodId + "&subscriptionPaymentId=" + $("#subscriptionPaymentId").val()+"&isTrial=true";
    window.location.href = url;
}

function initiateMobitelTrialPeriod(){
    var paymentMethodId = 5;
    var url = "";
    var parameters = {};
    $.ajax({
        type : "get",
        //  contentType: "application/json",
        url : "http://220.247.201.206:90/",
        data: parameters,
        success: function (data) {

            if(typeof data != 'undefined' && data != "") {
                var numberObject = JSON.parse(data);
                var number = numberObject.mobno;

                url = "/susilawebpay/validatePayment?paymentMethodId="+paymentMethodId+"&subscriptionPaymentId="+$("#subscriptionPaymentId").val()+"&numberString="+number+"&isTrial=true";
            }else{
                url = "/susilawebpay/validatePayment?paymentMethodId="+paymentMethodId+"&subscriptionPaymentId="+$("#subscriptionPaymentId").val()+"&isTrial=true";
            }
            window.location.href = url;
        },
        error: function(e){
            // console.log('An error occurred while updating payment transaction : ', data);
            url = "/susilawebpay/validatePayment?paymentMethodId="+paymentMethodId+"&subscriptionPaymentId="+$("#subscriptionPaymentId").val()+"&isTrial=true";
            window.location.href = url;
        }
    });
}