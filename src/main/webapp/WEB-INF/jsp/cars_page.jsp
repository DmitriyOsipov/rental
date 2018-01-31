<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="template-head-part.jsp" %>
</head>
<body>

<div class="grid-container">
    <%@ include file="template-static-import.jsp" %>
    <section class="mainSect">
        <%@page import="com.carrental.domain.Status" %>
        <%@page import="com.carrental.domain.Response" %>
        <%@ page import="com.carrental.domain.ResponseKeys" %>
        <%@ page import="com.carrental.model.Car" %>
        <%@page import="java.util.List" %>
            <% Response res = (Response) request.getAttribute("result");
            Status status = (Status)res.get(ResponseKeys.STATUS);
            if (status.getCode() == 200) {%>
                <ol>
                <%for (Car car : (List<Car>) res.get(ResponseKeys.CAR_LIST)) { %>
                    <li><a href="/cars/<%=car.getId()%>"><%=car.getType()%></a></li>
                <%}
            } else {%>
                <jsp:forward page="error_page.jsp"/>
            <%}%>
    </section>
</div>

</body>
</html>
