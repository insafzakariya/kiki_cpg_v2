<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Payment method selection</title>
        <meta name="viewport"
              content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link href="<c:url value='/static/css/otp.css'/>" rel="stylesheet">
    </head>
    <body>
        <div class="main_container">

            <div class="header_container">
                <h5 class="header">Please confirm your verification code</h5>
                <p class="tagline">A 6 digit confirmation code has been sent to
                    your number. Enter it below to activate it</p>
            </div>
            <div class="body_container">

                <div class="main_number_input">
                    <input type="number" name="" id="d1" placeholder="# # # # # #"
                           class="inputs" data-maxlength="6" data-id="d1">
                </div>
                <button class="form_submit" id="btn_otp_approve">ACTIVATE</button>

                <div class="error-field">
                    <div id="error" class="hide errorField" style="color:red"><p id="error-p"></p></div>
                </div>


            </div>
            <div class="preloader" style="padding-top: 10px; display: none"
                 id="gif-load">
                <div class="loader_img">
                    <img id="imgSrc" src="<c:url value='/static/img/ajax-loader.gif'/>"
                         alt="loading..." height="64" width="64">
                </div>
            </div>
        </div>
        <script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
        <script src="<c:url value='/static/js/jquery-1.11.3.min.js'/>"
        type="text/javascript"></script>
        <script src="<c:url value='/static/js/globle.js'/>"
        type="text/javascript"></script>
        <script type="text/javascript">

            $("#d1").keyup(function () {
                $("#error").addClass("hide");
            });

            $('#btn_otp_approve').click(function () {
                var serverRef = sessionStorage.getItem("server_ref");
                var subscriptionPaymentId = sessionStorage.getItem("subscriptionPaymentId");
                var day = sessionStorage.getItem("day");
                var viewerId = sessionStorage.getItem("viewerId");
                var otp = $('#d1').val();
                var otp_length = otp.toString().length;

                if (otp_length == 6) {
                    var data_st = {
                        "pin": otp,
                        "serverRef": serverRef,
                        "subscriptionPaymentId": subscriptionPaymentId,
                        "day": day,
                        "viewerId": viewerId
                    };

                    var x = document.getElementById("gif-load");
                    x.style.display = "block";

                    $.ajax({
                        type: "post",
                        contentType: "application/json",
                        dataType: 'json',
                        url: "/susilawebpay/rest/ideabiz/pin_subscription_confirm",
                        data: JSON.stringify(data_st),
                        success: function (data) {
                            x.style.display = "none";
                            var myJSON = JSON.stringify(data);
                            var myJSON = JSON.parse(myJSON);
                            if (myJSON['PIN-SUBMIT'] == 'FAIL') {
                                $("#error-p").text((myJSON['MESSAGE']));
                                $("#error").removeClass("hide");
                            } else {
                                var url = baseURL + +"/thanks_ideabiz";
                                window.location.replace(url);
                            }
                        },
                        error: function (e) {
                            $("#error-p").text('An error occurred while updating payment transaction : ' + e);
                            console.log('An error occurred while updating payment transaction : ', e);
                            $("#error").removeClass("hide");
                        }
                    });
                } else {
                    $("#error-p").text('Invalied OTP');
                    $("#error").removeClass("hide");
                }

            });
        </script>
    </body>
</html>
