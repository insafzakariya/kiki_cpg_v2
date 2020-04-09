<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<title>Payment method selection</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
    <Script>
        $(document).ready(function(){
            $(".inputs").keyup(function () {
                $this=$(this);
                if ($this.val().length >=$this.data("maxlength")) {
                  if($this.val().length>$this.data("maxlength")){
                    $this.val($this.val().substring(0,1));
                  }
                  $this.next(".inputs").focus();
                }
             });
        });
    </Script>
</head>
<body>

<style type="text/css">
	body {
		background-color: #1a1a1a;
		font-family: Arial, "Helvetica Neue", Helvetica, sans-serif;
		margin-left: auto;
		margin-right: auto;
		max-width: 95%;
		text-align: center;
	}
	.topbar_container {
		text-align: left;
	}
	.main_container {
		margin-top: 35px;
	}
	.header_container {
		text-align: center;
	}
	.header_container img {
		text-align: center;
		width: 80px;
		height: auto;
	}
	.header_container h5.header {
		color: #ffffff;
		font-size: 15px;
		font-weight: 600;
	}
	.header_container p.tagline {
		color: #ffffff;
		font-size: 13px;
	}
	.body_container {
		margin-top: 25px;
	}
	.main_number_input input {
		height: 40px;
	    width: 15%;
	    margin-left: 3px;
	    margin-right: 3px;
	    left: 0;
	    right: 0;
	    display: inline-block;
	    background-color: transparent;
	    border-bottom: 2px solid #ffffff;
	    border-top: none;
	    border-left: none;
	    border-right: none; 
	    opacity: 1;
	    color: #ffffff;
	    font-size: 25px;
	    border-radius: 2px;
	    text-align: center;
	}
	.form_submit {
		background-color: #009b9f;
	    color: #ffffff;
	    margin-top: 25px;
	    width: 100%;
	    text-align: center;
	    border: none;
	    padding-top: 15px;
	    padding-bottom: 15px;
	    border-radius: 2px;
	    font-size: 14px;
	    font-weight: 600;
	    text-transform: uppercase;
	}

	@media screen and (min-width: 360px) {
		body {
			margin-left: auto;
			margin-right: auto;
			max-width: 340px;
			text-align: center;
		}
	}
</style>

<%-- <div class="topbar_container">
	<a href="#" onclick="window.history.back()" >
		<img src="${pageContext.request.contextPath}/static/assets/images/backbtn.png" width="40" height="40" alt="facebook-icon" onmouseout="this.src='<c:url value='/static/assets/images/backbtn.png' />';" onmouseover="this.src='<c:url value='/static/assets/images/backbtnhvr.png' />';" smartload="22"></a>
</div> --%>

<div class="main_container">
	<div class="header_container">
		<img src="${pageContext.request.contextPath}/static/assets/images/smile.png">
		<h5 class="header">Thank you for activating KIKI</h5>
		<p class="tagline">Your payment has been confirmed sucessfully</p>
	</div>
	<div class="body_container">
	<form action="${pageContext.request.contextPath}/mainpg" method="get">
	<input type="hidden" name="token" value="${paymentToken}"></>
	<button type="submit"  class="form_submit">
			Back to home
		</button>
	</form>
		
	</div>
</div>

</body>
</html>