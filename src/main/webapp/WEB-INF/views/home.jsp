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
            <li><a href='/districts'>District List</a></li>
            <li><a href='/blockcaptains'>Block Captain List</a></li>
            <sec:authorize access="hasRole('ROLE_USER')">
                    <li><a href='/blockcaptains/pdf'>Block Captain Renewal Report</a></li>
            </sec:authorize>
            <li><a href='/noblockcaptains'>Blocks without Captain List</a></li>
            <li><a href='/person/emails'>Email List</a></li>
            <li><a href='/person/bad_emails'>Bad Email List</a></li>
            <sec:authorize access="isAuthenticated()">  
                <li><a href="/j_spring_security_logout">Logout</a></li>
            </sec:authorize>
        </ul>
    </body>
</html>
