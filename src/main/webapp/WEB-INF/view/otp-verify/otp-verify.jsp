<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
            $val=$this.data("id")+1;
           // alert($val);
            if ($this.val().length >=$this.data("maxlength")) {
              if($this.val().length>$this.data("maxlength")){
                $this.val($this.val().substring(0,1));
              }
             
           /*    document.getElementById("d"+$val).focus(); */
              $('#d'+$val).focus();
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
	   width: 50%;
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
	    -webkit-transform: none;
    	-moz-transform: none;
    	-o-transform: none;
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


<div class="main_container">

	<div class="header_container">
		<h5 class="header">Please confirm your verification code</h5>
		<p class="tagline">A 6 digit confirmation code has been sent to your number. Enter it below to activate it</p>
	</div>
	<div class="body_container">
		
			<div class="main_number_input" >
			 	<input type="number" name="" id="d1" placeholder="# # # # # #" class="inputs" data-maxlength="6" data-id="d1">
			 	
				<!-- <input type="number" min="0" name="" placeholder="#" class="inputs" data-maxlength="1" id="d1" data-id="1">
				<input type="number" min="0" name="" placeholder="#" class="inputs" data-maxlength="1" id="d2" data-id="2">
				<input type="number" min="0" name="" placeholder="#" class="inputs" data-maxlength="1" id="d3" data-id="3">
				</br>
				<input type="number" min="0" name="" placeholder="#" class="inputs" data-maxlength="1" id="d4" data-id="4">
				<input type="number" min="0" name="" placeholder="#" class="inputs" data-maxlength="1" id="d5" data-id="5">
				<input type="number" min="0" name="" placeholder="#" class="inputs" data-maxlength="1" id="d6" data-id="6"> -->
			
			</div>
			
				<input type="hidden" id="serverRef" value="${serverRef}"></>
            	<input type="hidden" id="subscriptionPaymentId" value="${subscriptionPaymentId}"></>
            	<input type="hidden" id="day" value="${day}"></>
			    <button class="form_submit"  id="btn_otp_approve">ACTIVATE</button>
			</input>
			
		
	</div>
	<div class="preloader" style="padding-top: 10px;display: none" id="gif-load"  >
			    <div class="loader_img"><img id="imgSrc" src="<c:url value='/static/img/ajax-loader.gif'/>" alt="loading..." height="64" width="64"></div>
			</div>
</div>
<script src="<c:url value='/static/js/jquery-1.11.3.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/static/js/numberverify.js' />"></script>
</body>
</html>
