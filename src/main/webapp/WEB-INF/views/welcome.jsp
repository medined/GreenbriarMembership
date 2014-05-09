<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Greenbriar Membership Management</title>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <ul>
            <li><a href='/home'>Home</a></li>
            <sec:authorize access="isAuthenticated()">  
                <li><a href="/j_spring_security_logout">Logout</a></li>
            </sec:authorize>
        </ul>
    </body>
</html>
