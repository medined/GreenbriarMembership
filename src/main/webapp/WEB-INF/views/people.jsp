<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Greenbriar Membership Management</title>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <h1>Person Lookup</h1>
        
        <p>Use your browser's search to find the person you seek.</p>
        
        <table border="1" cellpadding="3" cellspacing="3">
        <tr><th colspan='2'>Person</th><th rowspan='2'>District</th><th rowspan='2'>Block</th></tr>
        <tr><th>Last</th><th>First</th></tr>
        <c:forEach items="${people}" var="entry">
            <tr>
                <td><c:out value="${entry.value.getLast()}" /></td>
                <td><c:out value="${entry.value.getFirst()}" /></td>
                <td><a href="/district/<c:out value="${entry.value.getDistrictName()}" />"><c:out value="${entry.value.getDistrictName()}" /></a></td>
                <td><a href="/block/<c:out value="${entry.value.getBlockName()}" />"><c:out value="${entry.value.getBlockName()}" /></a></td>
            </tr>
        </c:forEach>
        </table>
    </body>
</html>
