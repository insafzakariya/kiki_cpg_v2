<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Mobile Payment Gateway</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="<c:url value='/static/img/favicon.ico'/>"/>
    <!-- Bootstrap -->
    <link href="<c:url value='/static/css/bootstrap.min.css'/>" rel="stylesheet">
    <link href="<c:url value='/static/css/alertmessage.css'/>" rel="stylesheet">
    <!-- styles -->
    <!--page level css -->
    <link href="<c:url value='/static/css/lockscreen2.css'/>" rel="stylesheet">
    <!--end page level css-->
</head>

<body class="background-img">
<div class="preloader">
    <div class="loader_img"><img src="<c:url value='/static/img/loader.gif'/>" alt="loading..." height="64" width="64"></div>
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
                    <div class="panel-body">
                <c:choose>
                    <c:when test="${not empty isUnsubscribe}">
                        <div class="alert alert-success">
                            <p>
                                <c:out value="${successMessage}"/>
                            </p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="alert alert-success">
                            <p>
                                Well done! Your payment is successfully compleated .Click Back Button to view the activated package.
                            </p>
                        </div>
                        </c:otherwise>
                    </c:choose>
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
<!-- end of page css -->
</body>


<!-- Mirrored from dev.lorvent.com/core_plus/lockscreen.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 07 Dec 2016 10:59:03 GMT -->
</html>
