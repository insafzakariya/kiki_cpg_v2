<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
    <head>
        <title>Send E-Receipt</title>
        <meta name="viewport"
              content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link href="<c:url value='/static/css/number-verification.css'/>"
              rel="stylesheet">
    </head>
    <body onload="initialize()">
        <div class="main_container">
            <div class="header_container">
            <h5 class="header">Send E-Receipt</h5>
                <h5 class="header">Please enter your email address below</h5>
                <!-- <p class="tagline">You will get a verification code to given mobile number after you this form</p> -->
            </div>
            <div class="body_container">

                <div class="main_number_input">
                    <input type="email" name="" class="text_border"
                           placeholder="Please enter your email address" id="email_address">
                </div>
                
                <input class="form_submit" type="button" id="btn_skiped"
                       value="SKIP">
                
                <input class="form_submit" type="button" id="btn_proceed"
                       value="PROCEED">

                <div class="error-field">
                    <div id="emailnotvalied" class="hide errorField" style="color:red">Email address is incorrect, Please enter valid email or skip</div>
                </div>

                <div class="preloader hide" style="padding-top: 10px;"
                     id="gif-load">
                    <div class="loader_img">
                        <img id="imgSrc" src="<c:url value='/static/img/ajax-loader.gif'/>"
                             alt="loading..." height="64" width="64">
                    </div>
                </div>

            </div>
        </div>

    </body>
    <!-- global js -->
    <script src="<c:url value='/static/js/jquery-1.11.3.min.js'/>"
    type="text/javascript"></script>
    <script src="<c:url value='/static/js/globle.js'/>"
    type="text/javascript"></script>

    <script type="text/javascript">
        function initialize() {
        	getViewerEmail(sessionStorage.getItem("viewerId"));
        }

        $("#email_address").keyup(function () {
            $("#emailnotvalied").addClass("hide");
        });
        
        $("#btn_skiped").click(function() {
			var methodId = sessionStorage.getItem("methodId");
            var email_address = $('#email_address').val();
			if (methodId == 4 || methodId == 7 || methodId == 8 || methodId == 9) {
				window.location.replace(baseURL + "/thanks/" + methodId);
			} else {
                $("#error-p").text('Invalied Method');
                $("#error").removeClass("hide");
	        }
		});

        $("#btn_proceed").click(
        function () {
            $("#gif-load").removeClass("hide");
            var method = sessionStorage.getItem("methodId");
            var viewerId = sessionStorage.getItem("viewerId");
			var invoiceId = sessionStorage.getItem("invoiceId");
            var email_address = $('#email_address').val();             
			var isEmailValid = validateViewerEmail(email_address);	
             
            if (email_address == null || isEmailValid == false) {
                console.log("email address incorrect");
                $("#emailnotvalied").removeClass("hide");
                $("#gif-load").addClass("hide");
                return;
            }
	
	    	var methodId = sessionStorage.getItem("methodId");
			if (methodId == 4 || methodId == 7 || methodId == 8 || methodId == 9) {
				saveViewerEmail(email_address,viewerId);
				sendViewerConfirmationEmail(email_address,viewerId,invoiceId,methodId);
				window.location.replace(baseURL + "/thanks/" + methodId);
			} else {
            	$("#error-p").text('Invalied Method');
            	$("#error").removeClass("hide");
	    	}	                
		});
        
        function validateViewerEmail(email) {
            var re = /\S+@\S+\.\S+/;
            return re.test(email);
        }
        
        function saveViewerEmail(emailAddress,viewerId){
        	console.log(emailAddress);
			var data_st = {
	            "emailAddress": emailAddress,
	            "viewerId": viewerId
			};
            $.ajax({
                type: "post",
                contentType: "application/json",
                dataType: 'json',
                url: baseURL + "/rest/viewer/updatevieweremail",
                data: JSON.stringify(data_st),
                success: function (data) {
                    var myJSON = JSON.stringify(data);
                    var myJSON = JSON.parse(myJSON);
                    if (data.status != 'TRUE') {
                        $("#error-p").text(data.message);
                        $("#error").removeClass("hide");
                    }
                },
                error: function (e) {
                    $("#error-p").text('An error occurred while updating email address : ' + e);
                    console.log('An error occurred while updating email address : ', e);
                    $("#error").removeClass("hide");
                }
            });
        }
        
        function sendViewerConfirmationEmail(emailAddress,viewerId,invoiceId,paymentMethodId){
        	console.log(emailAddress);
			var data_st = {
	            "emailAddress": emailAddress,
	            "viewerId": viewerId,
	            "invoiceId": invoiceId,
	            "paymentMethodId":paymentMethodId
			};
            $.ajax({
                type: "post",
                contentType: "application/json",
                dataType: 'json',
                url: baseURL + "/rest/viewer/send-viewer-notification-email",
                data: JSON.stringify(data_st),
                success: function (data) {
                    var myJSON = JSON.stringify(data);
                    var myJSON = JSON.parse(myJSON);
                    if (data.status != 'TRUE') {
                        $("#error-p").text(data.message);
                        $("#error").removeClass("hide");
                    }
                },
                error: function (e) {
                    $("#error-p").text('An error occurred while updating email address : ' + e);
                    console.log('An error occurred while updating email address : ', e);
                    $("#error").removeClass("hide");
                }
            });
        }
        
        function getViewerEmail(viewerId){
			var data_st = {
	            "viewerId": viewerId
			};
            $.ajax({
                type: "post",
                contentType: "application/json",
                dataType: 'json',
                url: baseURL + "/rest/viewer/getvieweremail",
                data: JSON.stringify(data_st),
                success: function (data) {
                    var myJSON = JSON.stringify(data);
                    var myJSON = JSON.parse(myJSON);
                    if (data.status != 'TRUE') {
                        $("#error-p").text(data.message);
                        $("#error").removeClass("hide");
                    } else {
                    	sessionStorage.setItem("emailAddress",data.dataMap.emailaddress);
                        var emailAddress = sessionStorage.getItem("emailAddress");
                        $("#email_address").val(emailAddress);
                    }
                },
                error: function (e) {
                    $("#error-p").text('An error occurred while updating email address : ' + e);
                    console.log('An error occurred while updating email address : ', e);
                    $("#error").removeClass("hide");
                }
            });
        }
        

		
					
				</script>

</html>




