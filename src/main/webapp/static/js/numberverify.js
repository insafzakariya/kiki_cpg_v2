$('#btn_proceed')
		.click(
				function() {
					var mobile_no = $('#mobile_no').val();
					var subscriptionPaymentId = $('#subscriptionPaymentId').val();
					var day = $('#day').val();
					var method = $('#methodId').val();
					
					var data = {
						mobile_no : mobile_no,
						subscriptionPaymentId : subscriptionPaymentId,
						day : day,
						method : method
					}
					if(method == 4){
						$.post("/susilawebpay/kiki-cpg/kiki-cpg/api/v1/ideabiz/ideabiz/pin_subscription", data, function(resp, status) {
							console.log(resp);
							console.log(status);
							if (status == "success") {
								if(resp.IS_DIALOG == "dialog"){
									if(resp.OTP_SEND == "SUCCESS" ){
										var url = "http://localhost:8080/susilawebpay/otp_verify?serverRef="+ resp.SERVER_REF+ "&subscriptionPaymentId="+ subscriptionPaymentId+ "&day=" + day;
										window.location.replace(url);
									} else{
										alert("Server Error");
									}
								} else{
									alert("Please Enter valied Dialog No ex:+9477*******");
								}
							} else {
								console.log(status);
							}
						});
					} else if (method == 5){
						$.post("http://localhost:8080/susilawebpay/kiki-cpg/api/v1/mobitel/verifyMobitelNo", data, function(resp, status) {
							console.log(resp);
							console.log(status);
							if(resp == 'true'){
								redirectToMobitelPayMent(method);
							}
							
						});
					}
					
				});

function redirectToMobitelPayMent(paymentMethodId){

	console.log("call");
	
   reAnimateLoadingIcon();
 
   var url = "";
   var parameters = {};
   $.ajax({
       type : "get",
       //  contentType: "application/json",
       url : "http://220.247.201.206:90/indexm.php",
       data: parameters,
       success: function (data) {
    	   
    	   data = {"mobno":"94703300399"}
       	
           if(typeof data != 'undefined' && data != "") {
               var numberObject = JSON.parse(data);
               var number = numberObject.mobno;
               if (number !=null) {
               	number=number.replace("+", "");
				}
               alert("1");
               url = "http://localhost:8080/susilawebpay/mobitel-verify?paymentMethodId="+paymentMethodId+"&subscriptionPaymentId="+$("#subscriptionPaymentId").val()+"&numberString="+number;
           }else{
        	   alert("2");
               url = "http://localhost:8080/susilawebpay/mobitel-verify?paymentMethodId="+paymentMethodId+"&subscriptionPaymentId="+$("#subscriptionPaymentId").val();
           }
           console.log(url);
           window.location.href = url;
       },
       error: function(e){
           // console.log('An error occurred while updating payment transaction : ', data);
    	   alert("3");
           url = "http://localhost:8080/susilawebpay/mobitel-verify?paymentMethodId="+paymentMethodId+"&subscriptionPaymentId="+$("#subscriptionPaymentId").val();
           console.log(url);
           window.location.href = url;
       }
   });
}


$('#btn_otp_approve')
		.click(
				function() {
					/*
					 * var
					 * otp=$('#d1').val()+$('#d2').val()+$('#d3').val()+$('#d4').val()+$('#d5').val()+$('#d6').val();
					 */
					var otp = $('#d1').val();
					var otp_length = otp.toString().length;

					if (otp_length == 6) {
						var serverRef = $('#serverRef').val();
						var subscriptionPaymentId = $('#subscriptionPaymentId')
								.val();
						var day = $('#day').val();

						var data_st = {
							"pin" : otp,
							"serverRef" : serverRef,
							"subscriptionPaymentId" : subscriptionPaymentId,
							"day" : day
						};
						var x = document.getElementById("gif-load");
						x.style.display = "block";
						$
								.ajax({
									type : "post",
									contentType : "application/json",
									dataType : 'json',
									url : "/susilawebpay/ideabiz/pin_subscription_confirm",
									data : JSON.stringify(data_st),
									success : function(data) {
										x.style.display = "none";
										var myJSON = JSON.stringify(data);
										var myJSON = JSON.parse(myJSON);
										if (myJSON['PIN-SUBMIT'] == 'FAIL') {
											alert(myJSON['MESSAGE']);
											// console.log(myJSON);
											// document.open();
											// document.write(myJSON['MESSAGE']);
											// document.close();
										} else {
											if (myJSON['MESSAGE'] == 'ALREADY SUBSCRIBED') {
												already_subscribed(
														myJSON['SYSTEM-TOKEN'],
														data_st);
												;
											} else {
												var url = "http://localhost:8080/susilawebpay/thanks_ideabiz";
												window.location.replace(url);
											}

										}
										// window.location.href =
										// "/susilawebpay/thanks_ideabiz";

									},
									error : function(e) {
										console
												.log(
														'An error occurred while updating payment transaction : ',
														e);
									}
								});
					} else {
						alert("invalied OTP");
					}

				});

function already_subscribed(token, data_st) {

	var data_token = {
		"token" : token
	};
	$
			.ajax({
				type : "post",
				contentType : "application/json",
				dataType : 'json',
				url : "/susilawebpay/ideabiz/ideabiz_unsubscribe/day",
				data : JSON.stringify(data_token),
				success : function(data) {
					$
							.ajax({
								type : "post",
								contentType : "application/json",
								dataType : 'json',
								url : "/susilawebpay/ideabiz/ideabiz_unsubscribe/week",
								data : JSON.stringify(data_token),
								success : function(data) {
									window.location.href = "ideabiz_device_unsubcribe?token="
											+ token;
								},
								error : function(e) {
									console
											.log(
													'An error occurred while updating payment transaction : ',
													e);
								}
							});
				},
				error : function(e) {
					console
							.log(
									'An error occurred while updating payment transaction : ',
									e);
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
