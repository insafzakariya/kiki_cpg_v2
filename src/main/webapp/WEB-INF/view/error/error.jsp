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
    <style type="text/css">
    	.btcli {
			  background-color: #009b9e;
			  color: white;
			  padding: 1em 1.5em;
			  text-decoration: none;
			  text-transform: uppercase;
			  
			}
			.form_submit {
		background-color: #009b9f;
	    color: #ffffff;
	    margin-top: 25px;
	    width: 60%;
	    text-align: center;
	    border: none;
	    padding-top: 15px;
	    padding-bottom: 15px;
	    border-radius: 2px;
	    font-size: 14px;
	    font-weight: 600;
	    text-transform: uppercase;
	}
    </style>
</head>

<body class="background-img">
<div class="preloader">
    <div class="loader_img"><img src="<c:url value='/static/img/loader.gif'/>" alt="loading..." height="64" width="64"></div>
</div>
<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="lockscreen-container">
               
                
                
                        <div class="alert " style="align-items: center; color: white;">
                        	<span>${errorMessage}</span>
                            <p >
                                      Oh snap! Your payment is not successful. Please try again.
                            </p>
                            
                             <input type="button" onclick="redirect_ideabiz()" href="" class="form_submit" VALUE="RETRY" id="btn_otp_approve" style="background:#009b9f">
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
<script src="<c:url value='/static/js/globle.js'/>" type="text/javascript"></script>
<!-- end of page css -->
<script type="text/javascript">
	function redirect_ideabiz() {
		var generalData = JSON.parse(sessionStorage.getItem("generalData"));
		var token = generalData.tokenHash;
		window.location.replace(baseURL + "/loading?token=" + token);
	}

</script>
</body>


<!-- Mirrored from dev.lorvent.com/core_plus/lockscreen.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 07 Dec 2016 10:59:03 GMT -->
</html>
