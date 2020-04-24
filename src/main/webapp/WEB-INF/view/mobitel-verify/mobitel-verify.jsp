<%--
  Created by IntelliJ IDEA.
  User: Chamil
  Date: 4/1/2018
  Time: 7:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>


<!-- Mirrored from dev.lorvent.com/core_plus/lockscreen.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 07 Dec 2016 10:59:01 GMT -->
<head>
  <title>Validate Screen</title>
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
        <div class="user-name">
          <h4 class="text-center">KiKi</h4>

          <small>Mobile Payment Gateway</small>
        </div>
        <div class="avatar"></div>
        <div class="form-box">

            <div class="form" id="validateDiv">
              <c:if test="${(paymentMethodId == 4) || (paymentMethodId == 5)}">

              <div>    <small class="locked ">Check The Payment Mobile Number<br></small></div>
              <div>  <small class="locked ">ගෙවීම් දුරකථන අංකය පරීක්ෂා කරන්න<br></small></div>
              <div>    <small class="locked ">கட்டண மொபைல் எண் சரிபார்க்கவும்<br></small></div>
              <small class="unlocked hidden">Unlocked</small>


              <div class="form-box">
              	<input class="hide" id="subscriptionPaymentId"  VALUE="${subscriptionPaymentId}" />
                <span id ="countryCode" style="padding:8px;">+94</span><input  id="inputDialogViuMobileNumber" value="${mobielNo}" style="color:black;margin-top:0px; margin-left:0px; width:70%;" type="number">
                <div id="dialogViuMobileNumberIncorrect" class="hide errorField" style="color:red">Mobile number is incorrect (Ex:77******* Or 76*******)</div>
                <div id="mobitelAddToBillNumberIncorrect" class="hide errorField" style="color:red">Mobile number is incorrect (Ex:71******* Or 70*******)</div>
                <div id="dialogViuMobileNumberEmpty" class="hide errorField" style="color:red">Enter the mobile number to proceed</div>
                <div id="dialogViuBackEndError" class="hide errorField" style="color:red"></div>
                <div id="unsubscribeError" class="hide errorField" style="color:red">Could not complete the Unsubscribe process please try again </div>
                <div id="mobitelConnectionError" class="hide errorField" style="color:red">Please use a mobitel Data connection to subscribe </div>

              </div>
              </c:if>
              <div class="panel-body">
                <!-- radio -->

                <div class="form-group dropdown-container">
             <c:if test="${ empty isMobitelSubscribe}">
                  <button id="confirmPay" class="button button-rounded button-primary-flat hvr-hang">Proceed
                  </button>
              </c:if>
                  <c:if test="${not empty isMobitelSubscribe}">
                    <button id="unSubscribe" style="margin-left:10px;" class="button button-rounded button-primary-flat hvr-hang"> Unsubscribe
                    </button>
                  </c:if>

                </div>
              </div>
              </div>
          <div id="mobitelRegistration" style="padding-top: 15px;"class="hide">
            <div  class="form-box" id="mobitelErrorMessage">
              <div class="form-group select2-container">
                <label> Dear customer in order to complete the registration </label>
              </div>
              <div class="form-group select2-container">
                <label> please send a sms to 5455  by saying</label>
              </div>
              <div class="form-group">
                <label id="registrationContent"> </label>
              </div>
              <div><label>Send SMS now</label></div>
              <div><a id="automaticSmsDiv" class="button button-rounded button-primary-flat hvr-hang" href="">Send</a></div>
              </div>
            <div class="form-group">
              <label>Once the payment is successfully made, you will be notified by an SMS </label>
            </div>
            </div>
            <div class="panel-body">
            </div>
          </div>

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

<script src="<c:url value='/static/vendors/iCheck/js/icheck.js'/>"></script>
<script src="<c:url value='/static/js/custom_js/radio_checkbox.js'/>"></script>
<script src="<c:url value='/static/js/view/mobitel-verify.js' />"></script>
<!-- end of global js -->
<!-- page css -->
<!-- end of page css -->
</body>


<!-- Mirrored from dev.lorvent.com/core_plus/lockscreen.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 07 Dec 2016 10:59:03 GMT -->
</html>
<script>

</script>