<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.TimeZone" %>
<%@ page import="java.util.UUID" %>
<html>
    <head>
        <title>Secure Acceptance - Payment Form Example</title>
        <link rel="stylesheet" type="text/css" href="payment.css"/>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <script type="text/javascript" src="jquery-1.7.min.js"></script>
    </head>
    <body onload="submit_form()" style="background: #333333;">
        <div id="loader" class="preloader" style="display: flex; justify-content: center; align-items: center; height: 100%; padding-top: 10px;">
            <div class="loader_img">
                <img id="imgSrc" src="ajax-loader.gif" alt="loading..." height="64" width="64">
            </div>
        </div>
        <div style="display: none">
            <form id="payment_form" action="payment_confirmation.jsp" method="post">
                <input type="hidden" name="access_key" value="8e6c56fc3a103ee7a647950f62c42b35">
                <input type="hidden" name="profile_id" value="CE98CC1C-A4F6-459B-8D1D-DD2BA4E48642">
                <input type="hidden" name="transaction_uuid" value="<%=request.getParameter("transaction_uuid")%>">
                <input type="hidden" name="signed_field_names" value="access_key,profile_id,transaction_uuid,signed_field_names,unsigned_field_names,signed_date_time,bill_to_address_line1,bill_to_address_city,bill_to_address_state,bill_to_address_country,bill_to_forename,bill_to_surname,bill_to_email,bill_to_phone,locale,transaction_type,reference_number,amount,currency">
                <input type="hidden" name="unsigned_field_names">
                <input type="hidden" name="signed_date_time" value="<%= getUTCDateTime() %>">
                <input type="hidden" name="bill_to_address_line1" value="Address1">
                <input type="hidden" name="bill_to_address_city" value="Colombo">
                <input type="hidden" name="bill_to_address_state" value="Western">
                <input type="hidden" name="bill_to_address_country" value="LK">
                <input type="hidden" name="bill_to_forename" value="ABCDDS">
                <input type="hidden" name="bill_to_surname" value="sdsdsd">
                <input type="hidden" name="bill_to_email" value="Developer@gmail.com">
                <input type="hidden" name="bill_to_phone" value="0776497074">
                <input type="hidden" name="locale" value="en">
                <fieldset>
                    <legend>Payment Details</legend>
                    <div id="paymentDetailsSection" class="section">
                        <span>transaction_type:</span><input type="text" name="transaction_type" size="25" value="authorization,create_payment_token"><br/>
                        <span>reference_number:</span><input type="text" name="reference_number" size="25" value="<%=request.getParameter("referance")%>"><br/>
                        <span>amount:</span><input type="text" name="amount" size="25" value="<%=request.getParameter("amount")%>"><br/>
                        <span>currency:</span><input type="text" name="currency" size="25" value="USD"><br/>
                    </div>
                </fieldset>
                <input type="submit" id="submit_btn" name="submit_btn" value="Submit"/>
                <script type="text/javascript" src="payment_form.js"></script>
            </form>
        </div>

        <script type="text/javascript">
        function submit_form() {
            $("#payment_form").submit();
        }
        </script>
    </body>

</html>
<%!
    private String getUTCDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(new Date());
    }
%>
