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
                var serverRef = localStorage.getItem("server_ref");
                var subscriptionPaymentId = localStorage.getItem("subscriptionPaymentId");
                var day = localStorage.getItem("day");
                var viewerId = localStorage.getItem("viewerId");
                var methodId = localStorage.getItem("methodId");
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

                    if (methodId == 4) {
                        $.ajax({
                            type: "post",
                            contentType: "application/json",
                            dataType: 'json',
                            url: baseURL + "/rest/ideabiz/pin_subscription_confirm",
                            data: JSON.stringify(data_st),
                            success: function (data) {

                                console.log(data);

                                x.style.display = "none";
                                var myJSON = JSON.stringify(data);
                                var myJSON = JSON.parse(myJSON);
                                if (data.statusCode == 'FAIL') {
                                    $("#error-p").text(data.message);
                                    $("#error").removeClass("hide");
                                } else {
                                    var url = baseURL + "/thanks/4";
                                    window.location.replace(url);
                                }
                            },
                            error: function (e) {
                                $("#error-p").text('An error occurred while updating payment transaction : ' + e);
                                console.log('An error occurred while updating payment transaction : ', e);
                                $("#error").removeClass("hide");
                            }
                        });
                    } else if (methodId == 7) {
                        $.ajax({
                            type: "post",
                            contentType: "application/json",
                            dataType: 'json',
                            url: baseURL + "/rest/otp/confirm",
                            data: JSON.stringify(data_st),
                            success: function (data) {
                                if (data.status == "Success") {

                                    var data_st2 = {
                                        "mobileNo": localStorage.getItem("mobile"),
                                        "viewerId": localStorage.getItem("viewerId"),
                                        "planId": localStorage.getItem("planId")
                                    };

                                    $.ajax({
                                        type: "post",
                                        contentType: "application/json",
                                        dataType: 'json',
                                        url: baseURL + "/rest/hnb/begin",
                                        data: JSON.stringify(data_st2),
                                        success: function (data) {
                                            localStorage.setItem('transaction_uuid', data.transactionUUID);
                                            localStorage.setItem('referance_no', data.referanceNo);
                                            localStorage.setItem('card_amount', data.amount);
                                            console.log(data);
                                            var url = paymentGatewayRedirectURL + "transaction_uuid=" + data.transactionUUID
                                                    + "&referance=" + data.referanceNo + "&amount=" + data.amount + "&frequency=" + data.frequency
                                                    + "&startdate=" + data.date;
                                            window.location.replace(url);

                                        },
                                        error: function (e) {
                                            $("#error-p").text('An error occurred while connecting Payment Gateway');
                                            console.log('An error occurred while connecting Payment Gateway : ', e);
                                            $("#error").removeClass("hide");
                                        }
                                    });




                                } else {
                                    $("#error-p").text(data.message);
                                    $("#error").removeClass("hide");
                                }

                                /* x.style.display = "none";
                                 var myJSON = JSON.stringify(data);
                                 var myJSON = JSON.parse(myJSON);
                                 if (data.statusCode == 'FAIL') {
                                 $("#error-p").text(data.message);
                                 $("#error").removeClass("hide");
                                 } else {
                                 var url = baseURL +"/thanks/4";
                                 window.location.replace(url);
                                 }*/
                            },
                            error: function (e) {
                                $("#error-p").text('An error occurred while updating payment transaction : ' + e);
                                console.log('An error occurred while updating payment transaction : ', e);
                                $("#error").removeClass("hide");
                            }
                        });
                    } else {
                        $("#error-p").text('Invalied Method');
                        $("#error").removeClass("hide");
                    }
                } else {
                    $("#error-p").text('Invalied OTP');
                    $("#error").removeClass("hide");
                }

            });
        </script>
    </body>
</html>
