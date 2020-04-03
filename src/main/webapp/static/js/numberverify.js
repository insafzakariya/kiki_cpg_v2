 $('#btn_proceed').click(function(){		 
		var mobile_no=$('#mobile_no').val();
		var subscriptionPaymentId=$('#subscriptionPaymentId').val();
		var day=$('#day').val();
		 var parameters = {};
		 parameters.mobileNo = mobile_no;
		 
		$.ajax({
	        type : "post",
	        //contentType: "application/json",
	        url : "/susilawebpay/ideabiz/pin_subscription",
	        data: {mobile_no:mobile_no,subscriptionPaymentId:subscriptionPaymentId,day:day},
	        success: function (data) {
	        	var myJSON = JSON.stringify(data);
	        	var myJSON=JSON.parse(myJSON);
	        	console.log(myJSON);
//	                document.open();
//	                document.write(myJSON['SERVER-REF']);
//	                document.close();
	        	if (myJSON['IS-DIALOG']=="dialog") {
	        		if (myJSON['OTP-SEND']=="SUCCESS") {
	                	window.location.href = "/susilawebpay/otp_verify_ideabiz?serverRef="+myJSON['SERVER-REF']+"&subscriptionPaymentId="+subscriptionPaymentId+"&day="+day;
					}else if(myJSON['OTP-SEND']=="FAIL"){
						
					}
				} else {
					alert("Please Enter valied Dialog No ex:+9477*******");
				}
	                
//	                

	        },
	        error: function(e){
	            console.log('An error occurred while updating payment transaction : ', e);
	        }
	    });
});
 
 $('#btn_otp_approve').click(function(){	
	/* var otp=$('#d1').val()+$('#d2').val()+$('#d3').val()+$('#d4').val()+$('#d5').val()+$('#d6').val();		*/
	 var otp=$('#d1').val();		
	 var otp_length=otp.toString().length;

	    if (otp_length==6) {
	    	var serverRef=$('#serverRef').val();		
			var subscriptionPaymentId=$('#subscriptionPaymentId').val();		
			var day=$('#day').val();		
			
			var data_st={"pin":otp,"serverRef":serverRef,"subscriptionPaymentId":subscriptionPaymentId,"day":day};
			var x = document.getElementById("gif-load");
			x.style.display = "block";
			$.ajax({
		        type : "post",
		        contentType: "application/json",
		        dataType: 'json',
		        url : "/susilawebpay/ideabiz/pin_subscription_confirm",
		        data: JSON.stringify(data_st),
		        success: function (data) {
		        	x.style.display = "none";
		        	var myJSON = JSON.stringify(data);
		        	var myJSON=JSON.parse(myJSON);
		        	if (myJSON['PIN-SUBMIT']=='FAIL') {
		        		alert(myJSON['MESSAGE']);
//		        		console.log(myJSON);
//		                document.open();
//		                document.write(myJSON['MESSAGE']);
//		                document.close();
					} else {
						if(myJSON['MESSAGE']=='ALREADY SUBSCRIBED'){
							already_subscribed(myJSON['SYSTEM-TOKEN'],data_st);
							;
						}else{
							window.location.href = "/susilawebpay/thanks_ideabiz";
						}
						
					}
//		        	window.location.href = "/susilawebpay/thanks_ideabiz";
		        	
		        },
		        error: function(e){
		            console.log('An error occurred while updating payment transaction : ', e);
		        }
		    });
		}else{
			alert("invalied OTP");
		}
	 
				
		
});
 
 function already_subscribed(token,data_st) {
	 
	 var data_token={"token":token};
		$.ajax({
	        type : "post",
	        contentType: "application/json",
	        dataType: 'json',
	        url : "/susilawebpay/ideabiz/ideabiz_unsubscribe/day",
	        data: JSON.stringify(data_token),
	        success: function (data) {
	        	$.ajax({
	    	        type : "post",
	    	        contentType: "application/json",
	    	        dataType: 'json',
	    	        url : "/susilawebpay/ideabiz/ideabiz_unsubscribe/week",
	    	        data: JSON.stringify(data_token),
	    	        success: function (data) {
	    	        	window.location.href = "ideabiz_device_unsubcribe?token="+token;
	    	        },
	    	        error: function(e){
	    	            console.log('An error occurred while updating payment transaction : ', e);
	    	        }
	    	    });
	        },
	        error: function(e){
	            console.log('An error occurred while updating payment transaction : ', e);
	        }
	    });
		
		
		
		
	
}