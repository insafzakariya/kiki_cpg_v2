<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
    <head>
        <title>Payment method selection</title>
        <meta name="viewport"
              content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link href="<c:url value='/static/css/number-verification.css'/>"
              rel="stylesheet">
    </head>
    <body onload="initialize()">
        <div class="main_container">
            <div class="header_container">
                <h5 class="header">Please enter your number below</h5>
                <!-- <p class="tagline">You will get a verification code to given mobile number after you this form</p> -->
            </div>
            <div class="body_container">

                <div class="main_number_input">
                    <input type="text" name=""
                           placeholder="Please enter your mobile number" id="mobile_no">
                </div>
                <input class="form_submit" type="button" id="btn_proceed"
                       value="PROCEED">

                <div class="error-field">
                    <div id="dialogIncorrect" class="hide errorField" style="color:red">Mobile number is incorrect (Ex:77******* Or 76*******)</div>
                    <div id="mobitelIncorrect" class="hide errorField" style="color:red">Mobile number is incorrect (Ex:71******* Or 70*******)</div>
                    <div id="numbernotvalied" class="hide errorField" style="color:red">Enter valid mobile number to proceed</div>
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
            var mobile = sessionStorage.getItem("mobile");
            $("#mobile_no").val(mobile);
        }

        $("#mobile_no").keyup(function () {
            $("#numbernotvalied").addClass("hide");
            $("#mobitelIncorrect").addClass("hide");
            $("#dialogIncorrect").addClass("hide");
        })

        $("#btn_proceed").click(
                function () {
                    var method = sessionStorage.getItem("methodId");
                    var mobile_no = $('#mobile_no').val();
                    console.log(mobile_no);
                    console.log(mobile_no.length);
                    if (mobile_no == null || mobile_no.length < 9) {
                    	console.log("incorrect");
                        $("#numbernotvalied").removeClass("hide");
                        return;
                    }

                    $.get(baseURL + "/rest/common/verifyNumber/" + method + "/" + mobile_no, function (resp, status) {
                        console.log(resp);
                        console.log(status);
                        if (status == "success") {
                            if (method == 4 && resp == "success") {
                                var day = sessionStorage.getItem("day");
                                $.get(baseURL + "/rest/ideabiz/otp/send/" + mobile_no + "/" + day, function (resp, status) {
                                    sessionStorage.setItem('server_ref', resp.serverRef);
                                    window.location.replace(baseURL + "/otp_verification");
                                });
                            } else if (method == 4 && resp != "success") {
                                $("#dialogIncorrect").removeClass("hide");
                            } else if (method == 5 && resp == "success") {
                                var subscriptionPaymentId = sessionStorage.getItem("subscriptionPaymentId");
                                sessionStorage.setItem("mobile", mobile_no);
                                var url = "https://services.mobitel.lk/MCCPortal/service/?compId=SusilaTV&reqType=ACTIVE&servId=SUWS&returnUrl=" 
                                        + baseURL + "/mobitel/mobitelPay&bridgeID=" + subscriptionPaymentId;
                                window.location.href = url;
                            } else if (method == 5 && resp != "success") {
                                $("#mobitelIncorrect").removeClass("hide");
                            } else {

                            }
                        } else {
                            console.log(status);
                        }
                    });

                });
    </script>

</html>




