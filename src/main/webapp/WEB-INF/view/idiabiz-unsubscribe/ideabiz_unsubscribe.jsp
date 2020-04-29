<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<title>Payment method selection</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
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
	.body_frame {
		background-color: #ffffff;
		height: 55px;
		padding: 10px;
		border-radius: 4px;
		text-align: left;
		margin-top: 15px;
	}
	.body_frame img {
		position: relative;
    	max-height: 55px;
    	left: 0;
	}
	.body_frame p {
		flex: 0 0 70%;
	    display: inline-block;
	    top: 0;
	    bottom: 0;
	    color: #000000;
	    vertical-align: top;
	    font-size: 14px;
	    font-weight: 600;
	    margin-left: 5px;
	}
	.body_frame p span {
		font-size: 12px;
		font-weight: 300;
		color: #000000;
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
	.unsubscribe {
		background-color: #c2272d;
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
		<h5 class="header">Your subscription</h5>
		<p class="tagline">&nbsp;</p>
	</div>
	<div class="body_container">
		
			<div class="body_frame">
				<div style="width: 100%; display: inline-block;">
					<img src="/susilawebpaystatic/assets/images/dialog-new.jpg" style="flex: 0 0 20%;">
					<p>Dialog<br/><span>Add to bill</span></p>
				</div>
			</div>
			<button class="form_submit unsubscribe" id="ideabiz_unsubscribe_btn" >UNSUBSCRIBE</button>
				
			</input>
		
	</div>
</div>
<script src="${pageContext.request.contextPath}/static/js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script type="text/javascript">
$('#ideabiz_unsubscribe_btn').click(function(){		
	var token=$('#token').val();
	var data_st={"token":token};
	$.ajax({
        type : "post",
        contentType: "application/json",
        dataType: 'json',
        url : "/susilawebpay/ideabiz/ideabiz_unsubscribe",
        data: JSON.stringify(data_st),
        success: function (data) {
        	var myJSON = JSON.stringify(data);
        	var myJSON=JSON.parse(myJSON);
        	window.location.href = "/susilawebpay/unsubscribed";
        },
        error: function(e){
            console.log('An error occurred while updating payment transaction : ', e);
        }
    });
	
});

</script>
</body>
</html>