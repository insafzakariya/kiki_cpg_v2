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
                <h5 id="title" class="header">Please enter your number below</h5>
                <!-- <p class="tagline">You will get a verification code to given mobile number after you this form</p> -->
            </div>
            <div class="body_container">

                <div class="main_number_input">
                    <input type="text" name="" class="text_border"
                           placeholder="Please enter your mobile number" id="mobile_no">
                </div>
                <input class="form_submit" type="button" id="btn_proceed"
                       value="PROCEED">

                <div class="error-field">
                    <div id="dialogIncorrect" class="hide errorField" style="color:red">Mobile number is incorrect (Ex:77******* Or 76*******)</div>
                    <div id="hutchIncorrect" class="hide errorField" style="color:red">Mobile number is incorrect (Ex:78******* Or 72*******)</div>
                    <div id="mobitelIncorrect" class="hide errorField" style="color:red">Mobile number is incorrect (Ex:71******* Or 70*******)</div>
                    <div id="mobileIncorrest" class="hide errorField" style="color:red">Mobile number is incorrect</div>
                    <div id="numbernotvalied" class="hide errorField" style="color:red">Enter valid mobile number to proceed</div>
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
    <script src="<c:url value='/static/js/jquery-3.5.1.min.js'/>"
    type="text/javascript"></script>
    <script src="<c:url value='/static/js/i18n/CLDRPluralRuleParser.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/static/js/i18n/jquery.i18n.js'/>"
		type="text/javascript"></script>
	<script
		src="<c:url value='/static/js/i18n/jquery.i18n.messagestore.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/static/js/i18n/jquery.i18n.fallbacks.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/static/js/i18n/jquery.i18n.parser.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/static/js/i18n/jquery.i18n.emitter.js'/>"
		type="text/javascript"></script>
	<script
		src="<c:url value='/static/js/i18n/jquery.i18n.emitter.bidi.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/static/js/i18n/jquery.i18n.language.js'/>"
		type="text/javascript"></script>
    <script src="<c:url value='/static/js/globle.js'/>"
    type="text/javascript"></script>

    <script type="text/javascript">
        function initialize() {
            var mobile = sessionStorage.getItem("mobile");
            $("#mobile_no").val(mobile);
            $.i18n({
                locale: sessionStorage.getItem('language').toLowerCase()
            });
            $.i18n().load({
                en: './static/language/en.json',
                si: './static/language/si.json',
                ta: './static/language/ta.json'
            }).done(function() {
                console.log('done!')
                $("#title").text($.i18n('num-verify-title'));
                $("#dialogIncorrect").text($.i18n('num-verify-error-1'));
                $("#hutchIncorrect").text($.i18n('num-verify-error-2'));
                $("#mobitelIncorrect").text($.i18n('num-verify-error-3'));
                $("#mobileIncorrest").text($.i18n('num-verify-error-4'));
                $("#numbernotvalied").text($.i18n('num-verify-error-5'));
                
            });
        }

        $("#mobile_no").keyup(function () {
            $("#numbernotvalied").addClass("hide");
            $("#mobitelIncorrect").addClass("hide");
            $("#dialogIncorrect").addClass("hide");
            $("#hutchIncorrect").addClass("hide");
        });

        $("#btn_proceed").click(
               
                function () {
                     $("#gif-load").removeClass("hide");
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
                        sessionStorage.setItem("mobile", mobile_no);
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
                            } else if (method == 7 && resp == "success") {
                                var viewerId = sessionStorage.getItem("viewerId");
                                $.get(baseURL + "/rest/otp/send/" + viewerId + "/" + mobile_no, function (resp, status) {
                                    window.location.replace(baseURL + "/otp_verification");
                                });
                            } else if (method == 7 && resp != "success") {
                                $("#mobileIncorrest").removeClass("hide");
                            } else if (method == 8 && resp == "success") {
                                var viewerId = sessionStorage.getItem("viewerId");
                                $.get(baseURL + "/rest/otp/send/" + viewerId + "/" + mobile_no, function (resp, status) {
                                    window.location.replace(baseURL + "/otp_verification");
                                });
                            } else if (method == 8 && resp != "success") {
                                $("#hutchIncorrect").removeClass("hide");
                            } else if (method == 9 && resp == "success") {
                                var viewerId = sessionStorage.getItem("viewerId");
                                $.get(baseURL + "/rest/otp/send/" + viewerId + "/" + mobile_no, function (resp, status) {
                                    window.location.replace(baseURL + "/otp_verification");
                                });
                            } else if (method == 9 && resp != "success") {
                                $("#mobileIncorrest").removeClass("hide");
                            } else {

                            }
                        } else {
                            console.log(status);
                        }
                    });

                });
    </script>

</html>




