
$(document).ready(function(){
    
	$("#confirmPay").bind("click", scratchCardPayment)
   // $(".radioOptions").bind("ifChecked", paymentOptionRadioButtonsChangeEvent)
    $("#inputScratchCard").bind("keydown", validateScratchCard);
    $("#inputScratchCard").bind("focusin", resetErorFields);

});

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

function resetErorFields(){
    $("#cardError").addClass("hide");
}

function scratchCardPayment(){
	
	var cardCode = $("#inputScratchCard").val();
	if(cardCode == "" || typeof cardCode == 'undefined'){
        $("#cardError").removeClass("hide");
        return;
    } 
	
	
    var data = {
    		code : cardCode,
    		viewerId : sessionStorage.getItem('viewerId'),
    		subscriptionPaymentId : sessionStorage.getItem('subscriptionPaymentId'),
    		mobileNo : sessionStorage.getItem('mobile')
    };
    
    
    $.ajax({
        type : "post",
        contentType: "application/json",
        url : baseURL + "/rest/scratchcard/payment",
        data: data,
        success: function (data) {
        	console.log(data);
            if(data == "success"){
            	window.location.replace(baseURL + "/thanks/6");
            } else{
            	window.location.replace(baseURL + "/error/" + data);
            }
        },
        error: function(e){
            // console.log('An error occurred while updating payment transaction : ', data);
        }
    });
}


