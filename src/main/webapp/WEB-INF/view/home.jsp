<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Lockscreen</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="shortcut icon" href="<c:url value='/static/img/favicon.ico'/>" />
<link href="<c:url value='/static/css/bootstrap.min.css'/>" rel="stylesheet">
<link href="<c:url value='/static/css/lockscreen2.css'/>" rel="stylesheet">
<link href="<c:url value='/static/css/common.css'/>" rel="stylesheet">


<!--end page level css-->
</head>

<body class="background-img" onload="navigate()">
	<div class="preloader">
		<div class="loader_img">
			<img id="imgSrc" src="<c:url value='/static/img/loader.gif'/>"
				alt="loading..." height="64" width="64">
		</div>
	</div>
	
	</div>
	<script src="<c:url value='/static/js/jquery-1.11.3.min.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/static/js/bootstrap.min.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/static/js/globle.js'/>" type="text/javascript"></script>
	<script type="text/javascript">
		function navigate() {
			$.get(baseURL + "/rest/home/initialize",
					function(data, status) {
						console.log(data);
						sessionStorage.setItem('generalData', JSON.stringify(data));
						if(data.alreadySubscribed == false && data.trialVerify == false){
							window.location.replace(baseURL + "/load_payment_methods");
						}
						if(data.alreadySubscribed == true){
							window.location.replace(baseURL + "/already_subscribed");
						}
						if(data.trialVerify == true){
							console.log("trial");	
						}
					}).fail(function (resp){
						console.log("error");
						console.log(resp);
						if(resp.status == '404'){
							window.location.replace(baseURL + "/error-page/1");
						}
					});
		}
	</script>
	</body>
</html>
