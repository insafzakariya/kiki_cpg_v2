<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Payment method selection</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link href="<c:url value='/static/css/otp.css'/>" rel="stylesheet">
    </head>
    <body onload="check()">
        <div class="main_container">
            <div id="loader" class="preloader" style="padding-top: 10px;">
                <div class="loader_img">
                    <img id="imgSrc" src="<c:url value='/static/img/ajax-loader.gif'/>" alt="loading..." height="64" width="64">
                </div>
            </div>

        </div>

        <script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
        <script src="<c:url value='/static/js/jquery-3.5.1.min.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/static/js/globle.js'/>" type="text/javascript"></script>
        <script type="text/javascript">
        function check() {
            console.log("load");

            var viewerId = sessionStorage.getItem("viewerId");
            var mobile = sessionStorage.getItem("mobile");
            var transaction_uuid = sessionStorage.getItem("transaction_uuid");
            var referance_no = sessionStorage.getItem("referance_no");
            var card_amount = sessionStorage.getItem("card_amount");

            console.log(mobile);
            var data = {
                viewerId: viewerId,
                mobile: mobile,
                transaction_uuid: transaction_uuid,
                referance_no: referance_no,
                card_amount: card_amount
            }
            console.log(data);

            $.ajax({
                type: "post",
                contentType: "application/json",
                dataType: 'json',
                url: baseURL + "/rest/hnb/verify",
                data: JSON.stringify(data),
                success: function (resp) {
                    if (resp.status == 'success') {
                        window.location.replace(baseURL + "/thanks/7");
                    } else {
                        window.location.replace(baseURL + "/error-page?message=" + resp.status);
                    }

                },
                error: function (e) {
                    console.log(e);
                    window.location.replace(baseURL + "/error-page?message=" + e);
                }
            });


        }
        </script>
    </body>
</html>
