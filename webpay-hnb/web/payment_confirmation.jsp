<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>

<%@ include file="security.jsp" %>

<html>
    <head>
        <title>Secure Acceptance - Payment Form Example</title>
        <link rel="stylesheet" type="text/css" href="payment.css"/>
        <script type="text/javascript" src="jquery-1.7.min.js"></script>
    </head>
    <body onload="submit_form()" style="background: #333333;">
        <div id="loader" class="preloader" style="display: flex; justify-content: center; align-items: center; height: 100%; padding-top: 10px;">
            <div class="loader_img">
                <img id="imgSrc" src="ajax-loader.gif" alt="loading..." height="64" width="64">
            </div>
        </div>
        <div style="display: none;">

            <form id="payment_confirmation" action="https://testsecureacceptance.cybersource.com/pay" method="post"/>
            <%
                request.setCharacterEncoding("UTF-8");
                HashMap params = new HashMap();
                Enumeration paramsEnum = request.getParameterNames();
                while (paramsEnum.hasMoreElements()) {
                    String paramName = (String) paramsEnum.nextElement();
                    String paramValue = request.getParameter(paramName);
                    params.put(paramName, paramValue);
                }
            %>
            <fieldset id="confirmation">
                <legend>Review Payment Details</legend>
                <div>
                    <%
                        Iterator paramsIterator = params.entrySet().iterator();
                        while (paramsIterator.hasNext()) {
                            Map.Entry param = (Map.Entry) paramsIterator.next();
                    %>
                    <div>
                        <span class="fieldName"><%=param.getKey()%>:</span><span class="fieldValue"><%=param.getValue()%></span>
                    </div>
                    <%
                        }
                    %>
                </div>
            </fieldset>
            <%
                paramsIterator = params.entrySet().iterator();
                while (paramsIterator.hasNext()) {
                    Map.Entry param = (Map.Entry) paramsIterator.next();
                    out.print("<input type=\"hidden\" id=\"" + param.getKey() + "\" name=\"" + param.getKey() + "\" value=\"" + param.getValue() + "\"/>\n");
                }
                out.print("<input type=\"hidden\" id=\"signature\" name=\"signature\" value=\"" + sign(params) + "\"/>\n");
            %>
            <input type="submit" id="submit_btn" value="Confirm"/>
        </form>

    </div>


    <script type="text/javascript">
        function submit_form() {
            $("#payment_confirmation").submit();
        }
    </script>

</body>
</html>
