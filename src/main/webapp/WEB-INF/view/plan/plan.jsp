<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Payment method selection</title>
        <meta name="viewport"
              content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" type="text/css" href="<c:url value= 'static/css/main.css'/>">
    </head>
    <body onload="initialize()">
        <div class="main_container">
            <div class="header_container">
                <h5 class="header">Choose package</h5>
                
            </div>
            <div class="body_container" style="padding: 10px;">
                <div id="package_type_container">
                 <div style="background: #24A9A6; height: 2px; margin-bottom: 20px;"></div>
               
                
                </div>

            </div>
        </div>
        <script src="<c:url value='/static/js/jquery-1.11.3.min.js'/>"
        type="text/javascript"></script>
        <script src="<c:url value='/static/js/globle.js'/>"
        type="text/javascript"></script>
        <script type="text/javascript">
        function initialize() {

            var paymentId = localStorage.getItem('paymentId');

            $.get(baseURL + "/rest/plan/planlist/" + paymentId, function (data, status) {
                console.log(status);
                if (status == "success") {
                    console.log(data);
                    var inner = "";
                    $.each(data, function (i, item) {

                        console.log(item);
                        inner += "<div onClick = \"checkNumber("
                                + item.day
                                + ","
                                + item.paymentMethodId
                                + ","
                                + paymentId
                                + ","
                                + item.id
                                + ","
                                + item.amount
                                + ")\" class=\"body_frame\"> "
                                + "<div style=\"width: 100%; display: inline-block;\">"
                                + "<img src= \"\" style=\"flex: 0 0 20%;\"> "
                                + "<p>"
                                + item.name
                                + "<br /> "
                                + "<span>"
                                + item.value
                                + "</span> ";
                        if (item.offer != null
                                && item.offer.length > 0) {
                            inner += "<br/><span style=\"color: red; font-weight: bold;\">"
                                    + item.offer
                                    + "</span>";
                        }
                        inner += "</p></div></div>";

                    });
                    console.log(inner);
                    $("#package_type_container").append(inner);
                } else {
                    console.log("Error");
                }

            });

        }

        function checkNumber(day, methodId, paymentId, planId, amount) {
            localStorage.setItem('day', day);
            localStorage.setItem('methodId', methodId);
            localStorage.setItem('paymentId', paymentId);
            localStorage.setItem('planId', planId);
            localStorage.setItem('amount', amount);

            window.location.replace(baseURL + "/number_verify");
        }

        </script>

    </body>
</html>