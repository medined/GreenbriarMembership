<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page import="java.util.Set" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Greenbriar Membership Management</title>
    </head>
    <body>
        <a href='/'>Home</a> : 
        <a href='/districts'>Districts</a> :
        <a href='/district/<c:out value="${block.getDistrictName()}"/>'><c:out value="${block.getDistrictName()}"/></a> :
        <c:out value="${block.getBlockName()}"/>

        <h1>Greenbriar Membership Management</h1>

        <h1><c:out value="${block.getHouses().size()}" /> Houses for Block <c:out value="${block.getBlockName()}"/></h1>

        <table border="0" cellpadding="3" cellspacing="3">
            <c:forEach items="${block.getHouses()}" var="house">
                <tr height="10px"><td></td></tr>
            <tr>
                    <td valign="top"><c:out value="${house.getHouseNumber()}"/> <c:out value="${house.getStreetName()}"/></td>
                    <td>
                    <c:forEach items="${house.getPeople()}" var="person">
                        Last: <c:out value="${person.getLast()}"/><br/>
                        First: <c:out value="${person.getFirst()}"/><br/>
                        Phone: <c:out value="${person.getPhone()}"/><br/>
                        Email: <c:out value="${person.getEmail()}"/><br/>
                        Unlisted Phone: <c:out value="${person.isUnlisted()}"/><br/>
                        Do Not List: <c:out value="${person.isNoList()}"/><br/>
                        Comment: <c:out value="${person.getComment()}"/><br/>
                        <br/>
                    </c:forEach>
                    </td>
                    <td valign="top">
                    <c:forEach items="${house.getYears()}" var="year">
                        <c:out value="${year.getYear()}"/><br/>
                    </c:forEach>
                    </td>
            </tr>
        </c:forEach>
        </table>
    </body>
</html>