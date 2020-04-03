<%--
  Created by IntelliJ IDEA.
  User: Chamil
  Date: 12/23/2018
  Time: 7:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>


<!-- Mirrored from dev.lorvent.com/core_plus/lockscreen.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 07 Dec 2016 10:59:01 GMT -->
<head>
    <title>Trial Period</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="<c:url value='/static/img/favicon.ico'/>"/>
    <!-- Bootstrap -->
    <link href="<c:url value='/static/css/bootstrap.min.css'/>" rel="stylesheet">
    <link href="<c:url value='/static/css/buttons_sass.css'/>" rel="stylesheet">
    <link href="<c:url value='/static/css/advbuttons.css'/>" rel="stylesheet">
    <!-- styles -->
    <!--page level css -->

    <link href="<c:url value='/static/css/lockscreen2.css'/>" rel="stylesheet">


    <link href="<c:url value='/static/vendors/iCheck/css/all.css'/>" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/vendors/awesomebootstrapcheckbox/css/awesome-bootstrap-checkbox.css'/>">
    <!--end page level css-->
    <style>
        input::-webkit-outer-spin-button,
        input::-webkit-inner-spin-button {
            /* display: none; <- Crashes Chrome on hover */
            -webkit-appearance: none;
            margin: 0; /* <-- Apparently some margin are still there even though it's hidden */
        }
    </style>

</head>

<body class="background-img">
<div class="preloader">
    <div class="loader_img"><img src="img/loader.gif" alt="loading..." height="64" width="64"></div>
</div>
<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="lockscreen-container">
                <div id="output"></div>

                <div class="form-box">
                    <div style="margin-top: 20%">
                        <h4 class="text-center">Activate your 14 day trial</h4>
                        <div><small>Select your payment method to activate</small> </div>
                        <div><small>You will be charged after 14 days when the trial ends</small> </div>
                    </div>
                    <div id="dialogBox" class="row payment-trial selected-trial-box">
                        <div  class="col-xs-3">
                            <label class="labelOveride">
                                <div class="payment2 payment-image"></div>
                            </label>
                        </div>
                        <div  class="col-xs-9 payemnt-div">
                            <div> Dialog Viu </div>
                            <div> <small><small></small> <span>${dialogViuPaymentAmmount}</span> <span>+Tax</span>  <span> ${dialogViuPaymentType}</span> </small>  </div>
                        </div>

                    </div>

                    <div id="mobitelBox" class="row payment-trial">
                        <div  class="col-xs-3">
                            <label class="labelOveride">
                                <div class="payment3 payment-image"></div>
                            </label>
                        </div>
                        <div  class="col-xs-9 payemnt-div">
                            <div> Mobitel - Add to my bill </div>
                            <div> <small><small></small> <span>${mobitelAddToBillPaymentAmount}</span> <span>+Tax</span>  <span> ${mobitelAddToBillPaymentType}</span> </small>  </div>
                        </div>
                    </div>
                     <div class="row trial-button-div form-group dropdown-container">
                         <button id="confirmPay" class="button button-rounded button-primary-flat hvr-hang trial-button">Pay
                         </button>
                     </div>
                    <input id="subscriptionPaymentId" type="hidden" value="${subscriptionPaymentId}" name="subscriptionPaymentId">
                </div>
            </div>
        </div>
    </div>
</div>

<!-- global js -->
<script src="<c:url value='/static/js/jquery-1.11.3.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/static/js/bootstrap.min.js'/>" type="text/javascript"></script>

<!-- end of global js -->
<!-- page css -->
<script src="<c:url value='/static/js/lockscreen2.js'/>"></script>

<script src="<c:url value='/static/js/trialPeriod.js' />"></script>
<!-- end of global js -->
<!-- page css -->
<!-- end of page css -->
</body>


<!-- Mirrored from dev.lorvent.com/core_plus/lockscreen.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 07 Dec 2016 10:59:03 GMT -->
</html>
<script>
   var trialPeriodGlobal = {
        dialogPaymentUrl :'${dialogPaymentUrl}',
        subscriptionPaymentId : ${subscriptionPaymentId}
    }

</script>