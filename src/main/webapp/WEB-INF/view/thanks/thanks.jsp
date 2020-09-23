<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Payment method selection</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link href="<c:url value='/static/css/thanks.css'/>" rel="stylesheet">

    </head>
    <body>
        <c:choose>
            <c:when test="${type eq 4}">
                <div class="main_container">
                    <div class="header_container">
                        <img src="<c:url value='/static/assets/images/smile.png'/>">
                        <h5 class="header">Thank you for activating KIKI</h5>
                        <p class="tagline">Your payment has been confirmed sucessfully</p>
                    </div>

                </div>
            </c:when>
            <c:when test="${type eq 5}">
                <div class="main_container">
                    <div class="header_container">
                        <img src="<c:url value='/static/assets/images/smile.png'/>">
                        <h5 class="header">Thank you for activating KIKI</h5>
                        <p class="tagline">Your payment has been confirmed sucessfully</p>
                    </div>

                </div>
            </c:when>
            <c:when test="${type eq 6}">
                <div class="main_container">
                    <div class="header_container">
                        <img src="<c:url value='/static/assets/images/smile.png'/>">
                        <h5 class="header">Thank you for activating KIKI</h5>
                        <p class="tagline">Your payment has been confirmed sucessfully</p>
                    </div>

                </div>
            </c:when>
            <c:when test="${type eq 7}">
                <div class="main_container">
                    <div class="header_container">
                        <img src="<c:url value='/static/assets/images/smile.png'/>">
                        <h5 class="header">Thank you for activating KIKI</h5>
                        <p class="tagline">Your payment has been confirmed sucessfully</p>
                    </div>

                </div>
            </c:when>
            
            <c:when test="${type eq 8}">
                <div class="main_container">
                    <div class="header_container">
                        <img src="<c:url value='/static/assets/images/smile.png'/>">
                        <h5 class="header">Thank you for activating KIKI</h5>
                        <p class="tagline">Your payment has been confirmed sucessfully</p>
                    </div>

                </div>
            </c:when>
            
            <c:when test="${type eq 9}">
                <div class="main_container">
                    <div class="header_container">
                        <img src="<c:url value='/static/assets/images/smile.png'/>">
                        <h5 class="header">Thank you for activating KIKI</h5>
                        <p class="tagline">Dear subscriber, <br/>
						Thank you for subscribing to KiKi. Please be informed that your subscription top-up through Keells will at least take 10 minutes to be verified. If failed please do call us via the hotline available on the payment invoice.
 						<br/>KiKi team</p>
                    </div>

                </div>
            </c:when>
            <c:when test="${type eq 10}">
                <div class="main_container">
                    <div class="header_container">
                        <img src="<c:url value='/static/assets/images/smile.png'/>">
                        <h5 class="header">You have sucessfully unsubscribed</h5>
                    </div>

                </div>
            </c:when>

            <c:when test="${type eq 11}">
                <div class="main_container">
                    <div class="header_container">
                        <img src="<c:url value='/static/assets/images/smile.png'/>">
                        <h5 class="header">You have sucessfully Transfered.</h5>
                    </div>

                </div>
            </c:when>
            <c:when test="${type eq 12}">
                <div class="main_container">
                    <div class="header_container">
                        <img src="<c:url value='/static/assets/images/smile.png'/>">
                        <h5 class="header">Your account Activated Successfully</h5>
                    </div>

                </div>
            </c:when>

        </c:choose>

        <div class="body_container">
            <button type="submit"  class="form_submit" id="btn-home"> Back to home </button>
        </div>


        <script src="<c:url value='/static/js/jquery-1.11.3.min.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/static/js/globle.js'/>" type="text/javascript"></script>
        <script type="text/javascript">
            $("#btn-home").click(function () {
                var token = localStorage.getItem("tokenHash");
                window.location.replace(baseURL + "/home?token=" + token);
            });
        </script>
    </body>
</html>