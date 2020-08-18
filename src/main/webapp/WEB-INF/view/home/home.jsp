<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="ISO-8859-1">
        <link rel="shortcut icon"
              href="<c:url value='/static/img/favicon.ico'/>" />
        <link href="<c:url value='/static/css/bootstrap.min.css'/>"
              rel="stylesheet">
        <link href="<c:url value='/static/css/buttons_sass.css'/>"
              rel="stylesheet">
        <link href="<c:url value='/static/css/advbuttons.css'/>"
              rel="stylesheet">
        <link href="<c:url value='/static/css/lockscreen2.css'/>"
              rel="stylesheet">
        <link href="<c:url value='/static/vendors/iCheck/css/all.css'/>"
              rel="stylesheet" />
        <link rel="stylesheet" type="text/css"
              href="<c:url value='/static/vendors/awesomebootstrapcheckbox/css/awesome-bootstrap-checkbox.css'/>">
        <link href="<c:url value='/static/css/common.css'/>" rel="stylesheet">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>HOME</title>
    </head>
    <body onload="initialize()">

        <div class="container">
            <div class="col-md-6 col-md-offset-3">
                <div class="lockscreen-container">
                    <div class="form-box">
                        <div class="form">
                            <div class="locked " style="font-weight: bold; font-size: 20">
                                Choose your preferred method.<br>
                            </div>
                            <div>
                                <small class="locked ">select your payment method to
                                    unlock<br>
                                </small>
                            </div>
                            <div>
                                <small class="locked ">access to exclusive content.<br></small>
                            </div>
                            <div id="paymentOptions">

                                <div class="form-box">
                                <div style="background: #24A9A6; height: 2px; margin-bottom: 20px;"></div>
                                    <form id="paymentMethodForm">
                                    	<div class="row" style="margin-left: 0px; margin-right: 0px;">
                                            <input type="radio" class="stv-radio-button"
                                                   name="optionsRadios" value="7" id="button6" /> <label
                                                   for="button6">
                                                <div class="row">
                                                    <div class="col-md-4 pull-left" style="margin-top: 8px;">
                                                        <img
                                                            src="/susilawebpay/static/assets/images/Visa.png"
                                                            class="img-rounded" alt="Cinque Terre" width="50"
                                                            >
                                                            <img
                                                            src="/susilawebpay/static/assets/images/Master.png"
                                                            class="img-rounded" alt="Cinque Terre" width="50"
                                                            >
                                                    </div>
                                                    <div class="col-md-5 pull-right">
                                                        <p>
                                                            <strong>Card</strong>
                                                        </p>
                                                        <p>Add to bill</p>
                                                    </div>
                                                </div>
                                            </label>
                                        </div>
                                    
                                        <div class="row" style="margin-left: 0px; margin-right: 0px;">
                                            <input type="radio" class="stv-radio-button"
                                                   name="optionsRadios" value="4" id="button3" /> <label
                                                   for="button3" >
                                                <div class="row" >
                                                    <div class="col-md-4 pull-left">
                                                        <img
                                                            src="/susilawebpay/static/assets/images/dialog-new.jpg"
                                                            class="img-rounded" alt="Cinque Terre" width="50"
                                                            height="50">
                                                    </div>
                                                    <div class="col-md-5 pull-right">
                                                        <p>
                                                            <strong>Dialog</strong>
                                                        </p>
                                                        <p>Add to bill</p>
                                                    </div>
                                                </div>
                                            </label>
                                        </div>
                                        <div class="row" style="margin-left: 0px; margin-right: 0px;">
                                            <input type="radio" class="stv-radio-button"
                                                   name="optionsRadios" value="5" id="button4" /> <label
                                                   for="button4">
                                                <div class="row">
                                                    <div class="col-md-4 pull-left">
                                                        <img src="/susilawebpay/static/assets/images/MOBITEL_new.png"
                                                             class="img-rounded" alt="Cinque Terre" width="50"
                                                             height="50">
                                                    </div>
                                                    <div class="col-md-5 pull-right">
                                                        <p>
                                                            <strong>Mobitel</strong>
                                                        </p>
                                                        <p>
                                                            <strong>Add to bill</strong>
                                                        </p>
                                                    </div>
                                                </div>

                                            </label>
                                        </div>


                                        <div class="row" style="margin-left: 0px; margin-right: 0px;">
                                            <input type="radio" class="stv-radio-button"
                                                   name="optionsRadios" value="6" id="button5" /> <label
                                                   for="button5">
                                                <div class="row">
                                                    <div class="col-md-4 pull-left">
                                                        <img src="/susilawebpay/static/img/authors/scratchcard.jpg"
                                                             class="img-rounded" alt="Cinque Terre" width="50"
                                                             height="50">
                                                    </div>
                                                    <div class="col-md-5 pull-right">
                                                        <p>
                                                            <strong>Use Scratch </strong>
                                                        </p>
                                                        <p>
                                                            <strong>Card code</strong>
                                                        </p>
                                                    </div>
                                                </div>
                                            </label>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="<c:url value='/static/js/jquery-1.11.3.min.js'/>"
        type="text/javascript"></script>
        <script src="<c:url value='/static/js/bootstrap.min.js'/>"
        type="text/javascript"></script>
        <script src="<c:url value='/static/vendors/iCheck/js/icheck.js'/>"></script>
        <script src="https://www.paypalobjects.com/api/checkout.js"></script>
        <script src="<c:url value='/static/js/globle.js'/>" type="text/javascript"></script>
        <script>

        function initialize() {
            console.log("initialize");
            localStorage.setItem('subscriptionPaymentId', ${subscriptionPaymentDto.getSubscriptionPaymentId()});
            localStorage.setItem('viewerId', ${subscriptionPaymentDto.getViewerId()});
            localStorage.setItem('packageId', ${subscriptionPaymentDto.getPackageId()});
            localStorage.setItem('tokenHash', "${subscriptionPaymentDto.getTokenHash()}");
            localStorage.setItem('mobile', "${subscriptionPaymentDto.getMobile()}");
        }

        $('#button3').click(function () {
            localStorage.setItem('paymentId', $('#button3').val());
            window.location.replace(baseURL + "/plan");
        });

        $('#button4').click(function () {
            localStorage.setItem('paymentId', $('#button4').val());
            window.location.replace(baseURL + "/plan");
        });

        $('#button5').click(function () {
            localStorage.setItem('paymentId', $('#button5').val());
            window.location.replace(baseURL + "/scratch-card");
        });
        
        $('#button6').click(function () {
            localStorage.setItem('paymentId', $('#button6').val());
            window.location.replace(baseURL + "/plan");
        });

        </script>    
    </body>
</html>