/**
 * Created by VisithaB on 2/4/17.
 */

$(document).ready(function(){

    /*paypal.Button.render({

        env: 'sandbox', // sandbox | production

        // PayPal Client IDs - replace with your own
        // Create a PayPal app: https://developer.paypal.com/developer/applications/create
        client: {
            sandbox:    'Ab22zq4RQ0v2BH9tJAUti5VL89Yi8a8LC-01xCUeFsjT3SSLgnX5a1GEP0A7SOix_FfDeuIGnrgMpYwy',
            production: '<insert production client id>'
        },

        style: {
            label: 'pay',
            fundingicons: true, // optional
            branding: true, // optional
            size:  'small', // small | medium | large | responsive
            shape: 'pill',   // pill | rect
            color: 'gold'   // gold | blue | silve | black
        },
        // Show the buyer a 'Pay Now' button in the checkout flow
        commit: true,

        // payment() is called when the button is clicked
        payment: function(data, actions) {

            // Make a call to the REST api to create the payment
            return actions.payment.create({
                payment: {
                    transactions: [
                        {
                            amount: { total: '0.01', currency: 'USD' }
                        }
                    ]
                },
                experience: {
                    input_fields: {
                        no_shipping: 1,
                        address_override: 1
                    }
                }
            });
        },

        // onAuthorize() is called when the buyer approves the payment
        onAuthorize: function(data, actions) {

            // Make a call to the REST api to execute the payment
            return actions.payment.execute().then(function() {
                window.alert('Payment Complete!');
            });
        }

    }, '#paypal-button-container');*/
    ///////////////////////////////////////
//	IdeaBIZ BUTTON
	 $('#t_btn').click(function(){
		 select_payment_type(4);
		 //mobile_no_capture(4)
	 });
	 function select_payment_type(paymentMethodId){
		    var transactionID = $('#transactionID').val();
		    var parameters = {};
		    parameters.isDialogViuPayment = true;
		    //var url = "/susilawebpay/validatePayment?paymentMethodId="+paymentMethodId+"&subscriptionPaymentId="+$("#subscriptionPaymentId").val();
		    var url = "/susilawebpay/select_payment_type?paymentMethodId="+paymentMethodId+"&subscriptionPaymentId="+$("#subscriptionPaymentId").val();
		    window.location.href = url;
		    
	}
	 function mobile_no_capture(paymentMethodId){
		    var transactionID = $('#transactionID').val();
		    var parameters = {};
		    parameters.isDialogViuPayment = true;
		    //var url = "/susilawebpay/validatePayment?paymentMethodId="+paymentMethodId+"&subscriptionPaymentId="+$("#subscriptionPaymentId").val();
		    var url = "/susilawebpay/checkNumber_ideabiz?subscriptionPaymentId="+$("#subscriptionPaymentId").val();
		    window.location.href = url;
		    
	}
	 function ck_redirect() {
		
		 var url = "/susilawebpay/ck/verify_code";
		    window.location.href = url;
	}
	
	
	
	
	
    // payment submit event
    $('.stv-radio-button').click(function(){

        var url = "";
        var parameters = {};
        var selectedPaymentOption = $('#ipgselectmenu').val();
        selectedPaymentOption = $('input[name=optionsRadios]:checked', '#paymentMethodForm').val();
        console.log(selectedPaymentOption);
        if(selectedPaymentOption == 2){
            payEZCash(selectedPaymentOption);
        }else if (selectedPaymentOption == 3){
            payMCash(selectedPaymentOption);
        }
        else if(selectedPaymentOption == 4){
        	 select_payment_type(4);

        }else if(selectedPaymentOption == 5){
        	select_payment_type(5);
        }else if(selectedPaymentOption == 6){
            scratchCardPayment(selectedPaymentOption);
        }else if(selectedPaymentOption == 7){
        	ck_redirect();           
        }
    });
    $('#payButtonviu').click(function(){
        var myKeyVals = { A1984 : 1, A9873 : 5, A1674 : 2, A8724 : 1, A3574 : 3, A1165 : 5 }
        //var radioValue = $("input:radio[name=command] :selected").val();
       var var_name = $("input[name='command']:checked").val();
        redirectToPayMent(var_name);



    });
    $('#testMcash').click(function(){
        $.ajax({
            type : "post",
            contentType: "application/json",
            url : "/susilawebpay/mcash/callback",
            success: function (data) {
//                console.log('response=', data);
                alert("suceseqqq");
            },
            error: function(e){
                alert("error : "+e);
            }
        });
    });
    $('#testMcash2').click(function(){
        $.ajax({
            type : "get",
            contentType: "application/json",
            url : "/susilawebpay/mcash/transactionsuccess",
            success: function (data) {
//                console.log('response=', data);
                //alert("suceseqqq");
            },
            error: function(e){
               // alert("error : "+e);
            }
        });
    });
    // remove this
    $('#mobitelPayButton').click(function(){
        redirectToMobitelPayMent();
    });

});

function payEZCash(paymentMethodId){
    getEzcashInvoiceAndRedirectToIPG(paymentMethodId);
    console.log('ezcash form submitted..');
}

function getEzcashInvoiceAndRedirectToIPG(paymentMethodId){
    url = "/susilawebpay/ezcash/getezcashinvoice";
    var subscriptionPaymentId = $('#subscriptionPaymentId').val();
    parameters = {
        paymentMethodId: paymentMethodId,
        subscriptionPaymentId: subscriptionPaymentId
    };

    $.ajax({
        type : "get",
        contentType: "application/json",
        url : url,
        data: parameters,
        success: function (data) {
            console.log('Invoice received successfully : ', data);
            if(data != null){
                $("#encryptedMerchantInvoice").val(data);
                $("#ezCashForm").submit();
            }
        },
        error: function(e){
            console.log('Error when getting token : ', e);
        }
    });
}

function payMCash(paymentMethodId){
    getMcashTokenAndRedirectToIPG(paymentMethodId);
}

function getMcashTokenAndRedirectToIPG(paymentMethodId){
    url = "/susilawebpay/mcash/gettoken";
    var subscriptionPaymentId = $('#subscriptionPaymentId').val();
    parameters = {
        paymentMethodId: paymentMethodId,
        subscriptionPaymentId: subscriptionPaymentId
    };

    $.ajax({
        type : "get",
        contentType: "application/json",
        url : url,
        data: parameters,
        success: function (data) {
            console.log('Token received successfully : ', data);
            if(data != "error"){
                $("#mCashToken").val(data);
                $("#mCashForm").submit();
            }
        },
        error: function(e){
            console.log('Error when getting token : ', e);
        }
    });
}

function dialogViu(paymentMethodId){
    var transactionID = $('#transactionID').val();
   var parameters = {};

    /*$.ajax({
        type : "post",
        contentType: "application/json",
        url : "/susilawebpay/DialogViu",
        data: parameters,
        success: function (data) {
            //alert('success');
            document.open();
            document.write(data);
            document.close();
        },
        error: function(e){
            console.log('An error occurred while updating payment transaction : ', data);
        }
    });*/
    parameters.isDialogViuPayment = true;
    var url = "/susilawebpay/validatePayment?paymentMethodId="+paymentMethodId+"&subscriptionPaymentId="+$("#subscriptionPaymentId").val();
    window.location.href = url;
    /*$.ajax({
     type : "post",
     //contentType: "application/json",
     url : "/susilawebpay/validatePayment",
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
*/
}


var redirect = function(url, method) {
    var form = document.createElement('form');
    form.method = method;
    form.action = url;
    form.submit();
};

function redirectToMobitelIPG(mCashToken){
    console.log("redirected to mCash IPG server");
    window.location = "https://www.mcash.lk/ipg/util/payment.html?t_id=" + mCashToken;
}
function redirectToPayMent(var_name)
{
    parameters = {paymentMethodId:var_name};

    $.ajax({
        type : "post",
      //  contentType: "application/json",
        url : "/susilawebpay/DialogViuPay",
        data: parameters,
        success: function (data) {
            alert('success');
            document.open();
            document.write(data);
            document.close();
        },
        error: function(e){
            // console.log('An error occurred while updating payment transaction : ', data);
        }
    });
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

function paymentOptionRadioButtonsChangeEvent(event){
    var optionId = $(this).prop("id");
    if(optionId == "radioScratchCard"){
        // display textFieldToEnterCode
        $("#scratchCardDiv").removeClass("hide");
    }else{
        // hide textField
        $("#scratchCardDiv").addClass("hide");
    }
}



function scratchCardPayment(paymentMethodId){
    var url = "/susilawebpay/validatePayment?paymentMethodId="+paymentMethodId+"&subscriptionPaymentId="+$("#subscriptionPaymentId").val();
    window.location.href = url;
}

function resetErorFields(){
    $("#cardError").addClass("hide");
}
function callbackFunction(data){
    var val = 0;
    alert("calback"+data);

}function httpGetAsync(theUrl, callback)
{
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function() {

            callback(xmlHttp.responseText);
    }
    xmlHttp.open("GET", theUrl, true); // true for asynchronous
    xmlHttp.send(null);
}

function createCORSRequest(method, url) {
    var xhr = new XMLHttpRequest();
    if ("withCredentials" in xhr) {
        // XHR for Chrome/Firefox/Opera/Safari.
        xhr.open(method, url, true);
    } else if (typeof XDomainRequest != "undefined") {
        // XDomainRequest for IE.
        xhr = new XDomainRequest();
        xhr.open(method, url);
    } else {
        // CORS not supported.
        xhr = null;
    }
    return xhr;
}

// Helper method to parse the title tag from the response.
function getTitle(text) {
    return text.match('<title>(.*)?</title>')[1];
}

// Make the actual CORS request.
function makeCorsRequest() {
    // This is a sample server that supports CORS.
    var url = 'http://220.247.201.206:90/';

    var xhr = createCORSRequest('GET', url);
    if (!xhr) {
        alert('CORS not supported');
        return;
    }

    // Response handlers.
    xhr.onload = function() {
        var text = xhr.responseText;
        alert(text);
        var title = getTitle(text);
        alert('Response from CORS request to ' + url + ': ' + title);
    };

    xhr.onerror = function() {
        alert('Woops, there was an error making the request.');
    };

    xhr.send();
}
