<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="ISO-8859-1">
        <title>ALREADY SUBSCRIBED</title>
        <link href="<c:url value='/static/css/bootstrap.min.css'/>" rel="stylesheet">
        <link rel="shortcut icon"  href="<c:url value='/static/img/favicon.ico'/>" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link href="<c:url value='/static/css/already-subscribed.css'/>" rel="stylesheet">
    </head>
    <body onload="initialize()" class="container main_container ">
        <div class="header_container">
            <h5 class="header">Your Subscriptions</h5>
        </div>

        <div class="body_container">

            <c:forEach items="${subscriptionTypeList}" var="subscriptionType">

                <c:choose>
                    <c:when test="${subscriptionType eq 'CREDIT_CARD'}">
                        <a id="card">
                            <div class="body_frame">
                                <div class="row">
                                    <div class="col-xs-4 col-md-4" style="padding-top: 10px;">
                                        <div >
                                            <img style="width: 40%; display: inline-block;" src="/susilawebpay/static/assets/images/Visa.png"> 
                                            <img style="width: 40%; display: inline-block;" src="/susilawebpay/static/assets/images/Master.png">
                                        </div>

                                    </div>
                                    <div class="col-xs-8 col-md-8" style="padding-right: 20px;" >
                                        <p style=" text-align: right; "> Card </p>
                                        <p style=" text-align: right; margin-top: -13px;"> <span style="color: red;"> Click to unsubscribe</span> </p>
                                        <p style=" text-align: right; margin-top: -15px;"> <span style="margin-top: 20px;">Last Payment: ${lastSubscribeDay}</span> </p>

                                        </p>
                                    </div>


                                </div>
                            </div>
                        </a>
                    </c:when>
                    <c:when test="${subscriptionType eq 'IDEABIZ'}">
                        <a id="dviu">

                            <div class="body_frame">
                                <div class="row">
                                    <div class="col-xs-4 col-md-4" >
                                        <div >
                                            <img  src="/susilawebpay/static/assets/images/dialog-new.jpg" style="height: 40px; margin-top: 5px;"> 
                                        </div>

                                    </div>
                                    <div class="col-xs-8 col-md-8" style="padding-right: 20px;" >
                                        <p style=" text-align: right; "> Dialog </p>
                                        <p style=" text-align: right; margin-top: -13px;"> <span style="color: red;"> Click to unsubscribe</span> </p>
                                        <p style=" text-align: right; margin-top: -15px;"> <span style="margin-top: 20px;">Last Payment: ${lastSubscribeDay}</span> </p>
                                    </div>


                                </div>
                            </div>

                        </a>
                    </c:when>
                    <c:when test="${subscriptionType eq 'MOBITEL'}">
                        <a id="mobitel">

                            <div class="body_frame">
                                <div class="row">
                                    <div class="col-xs-4 col-md-4" style="padding-top: 10px;">
                                        <div >
                                            <img  src="/susilawebpay/static/assets/images/mobitel.png" style="height: 40px; margin-top: 5px;"> 
                                        </div>

                                    </div>
                                    <div class="col-xs-8 col-md-8" style="padding-right: 20px;" >
                                        <p style=" text-align: right; "> Mobitel - Add to my bill </p>
                                        <p style=" text-align: right; margin-top: -13px;"> <span style="color: red;"> Click to unsubscribe</span> </p>
                                        <p style=" text-align: right; margin-top: -15px;"> <span style="margin-top: 20px;">Last Payment: ${lastSubscribeDay}</span> </p>
                                    </div>


                                </div>
                            </div>
                        </a>
                    </c:when>
                    <c:when test="${subscriptionType eq 'HUTCH'}">
                        <a id="hutch">

                            <div class="body_frame">
                                <div class="row">
                                    <div class="col-xs-4 col-md-4" >
                                        <div >
                                            <img  src="/susilawebpay/static/assets/images/dialog-new.jpg" style="height: 40px; margin-top: 5px;"> 
                                        </div>

                                    </div>
                                    <div class="col-xs-8 col-md-8" style="padding-right: 20px;" >
                                        <p style=" text-align: right; "> Hutch </p>
                                        <p style=" text-align: right; margin-top: -13px;"> <span style="color: red;"> Click to unsubscribe</span> </p>
                                        <p style=" text-align: right; margin-top: -15px;"> <span style="margin-top: 20px;">Last Payment: ${lastSubscribeDay}</span> </p>
                                    </div>


                                </div>
                            </div>

                        </a>
                    </c:when>
                </c:choose>
            </c:forEach>
            <button type="button" id="btnnewsubscription" class="form_submit">
                ADD NEW SUBSCRIPTION</button>


        </div>



        <script src="<c:url value='/static/js/jquery-1.11.3.min.js'/>"
        type="text/javascript"></script>
        <script src="<c:url value='/static/js/globle.js'/>"
        type="text/javascript"></script>
        <script type="text/javascript">
$("#btnnewsubscription").click(function () {
    var token = "${subscriptionPaymentDto.getTokenHash()}";
    window.location.replace(baseURL + "/home?token=" + token + "&type=new");
});

function initialize() {
    console.log("initialize");
    localStorage.setItem('subscriptionPaymentId', ${subscriptionPaymentDto.getSubscriptionPaymentId()});
    localStorage.setItem('viewerId', ${subscriptionPaymentDto.getViewerId()});
    localStorage.setItem('packageId', ${subscriptionPaymentDto.getPackageId()});
    localStorage.setItem('tokenHash', "${subscriptionPaymentDto.getTokenHash()}");
    localStorage.setItem('mobile', "${subscriptionPaymentDto.getMobile()}");
}

$("#dviu").click(function () {
    var data = {
        subscriptionPaymentId: localStorage.getItem('subscriptionPaymentId'),
        viewerid: localStorage.getItem('viewerId'),
        type: 4,
        mobile: localStorage.getItem('mobile')
    }

    console.log("send Req");
    console.log(data);

    $.ajax({
        type: "post",
        contentType: "application/json",
        dataType: 'json',
        url: baseURL + "/rest/unsubscribe",
        data: JSON.stringify(data),
        success: function (resp) {
            console.log(resp);
            if (resp.status == 'success') {
                window.location.replace(baseURL + "/thanks/10");
            } else {
                window.location.replace(baseURL + "/error-page?message=" + resp.status);
            }

        },
        error: function (e) {
            console.log(e);
            window.location.replace(baseURL + "/error-page?message=" + e);
        }
    });
});

$("#mobitel").click(function () {
    var data = {
        subscriptionPaymentId: localStorage.getItem('subscriptionPaymentId'),
        viewerid: localStorage.getItem('viewerId'),
        type: 5,
        mobile: localStorage.getItem('mobile')
    }

    $.ajax({
        type: "post",
        contentType: "application/json",
        dataType: 'json',
        url: baseURL + "/rest/unsubscribe",
        data: JSON.stringify(data),
        success: function (resp) {
            console.log(resp);
            if (resp.status == 'success') {
                window.location.replace(baseURL + "/thanks/10");
            } else {
                window.location.replace(baseURL + "/error-page?message=" + resp.status);
            }

        },
        error: function (e) {
            console.log(e);
            window.location.replace(baseURL + "/error-page?message=" + e);
        }
    });
});

$("#card").click(function () {
    var data = {
        subscriptionPaymentId: localStorage.getItem('subscriptionPaymentId'),
        viewerid: localStorage.getItem('viewerId'),
        type: 7,
        mobile: localStorage.getItem('mobile')
    }

    $.ajax({
        type: "post",
        contentType: "application/json",
        dataType: 'json',
        url: baseURL + "/rest/unsubscribe",
        data: JSON.stringify(data),
        success: function (resp) {
            console.log(resp);
            if (resp.status == 'success') {
                window.location.replace(baseURL + "/thanks/10");
            } else {
                window.location.replace(baseURL + "/error-page?message=" + resp.status);
            }

        },
        error: function (e) {
            console.log(e);
            window.location.replace(baseURL + "/error-page?message=" + e);
        }
    });
});

$("#hutch").click(function () {
    var data = {
        subscriptionPaymentId: localStorage.getItem('subscriptionPaymentId'),
        viewerid: localStorage.getItem('viewerId'),
        type: 8,
        mobile: localStorage.getItem('mobile')
    }

    $.ajax({
        type: "post",
        contentType: "application/json",
        dataType: 'json',
        url: baseURL + "/rest/unsubscribe",
        data: JSON.stringify(data),
        success: function (resp) {
            console.log(resp);
            if (resp.status == 'success') {
                window.location.replace(baseURL + "/thanks/10");
            } else {
                window.location.replace(baseURL + "/error-page?message=" + resp.status);
            }

        },
        error: function (e) {
            console.log(e);
            window.location.replace(baseURL + "/error-page?message=" + e);
        }
    });
});

        </script>
    </body>
</html>