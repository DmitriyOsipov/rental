<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="template-head-part.jsp" %>
</head>
<body>

<div class="grid-container">
    <%@ include file="template-static-import.jsp" %>
    <section class="mainSect">
        Sorry, but something went wrong...
        <%@page import="com.carrental.domain.Status" %>
        <%@page import="com.carrental.domain.Response" %>
        <%@ page import="com.carrental.domain.ResponseKeys" %>
        <% Response res = (Response) request.getAttribute("result");
            Status status = (Status)res.get(ResponseKeys.STATUS);%>
        Here is details:
        <ul>
            <li>Status code: <%=status.getCode()%></li>
            <li>Status message: <%=status.getMessage()%></li>
            <li>Exception type: <%=res.get(ResponseKeys.EXCEPTION_TYPE)%></li>
            <li>Exception message: <%=res.get(ResponseKeys.EXCEPTION_MESSAGE)%></li>
        </ul>
    </section>

</body>
</html>
