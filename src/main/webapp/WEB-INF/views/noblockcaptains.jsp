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
        <script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
        <link href="//cdnjs.cloudflare.com/ajax/libs/x-editable/1.5.0/jquery-editable/css/jquery-editable.css" rel="stylesheet"/>
        <script src="//cdnjs.cloudflare.com/ajax/libs/x-editable/1.5.0/jquery-editable/js/jquery-editable-poshytip.min.js"></script>
        <link rel="stylesheet" type="text/css" href="/resources/block.css">
        <title>Greenbriar Membership Management</title>
    </head>
    <body>

        <h1><fmt:formatNumber value="${blocks.size()}" /> Blocks Without Captains</h1>

        <p>If you live on one of the listed blocks, please consider becoming a Block Captain.</p>

        <table cellspacing="3" cellpadding="3" border="0">
            <tr>
                <th>District</th>
                <th>Block</th>
                <th>Houses</th>
            </tr>
            <c:forEach items="${blocks}" var="block">
                <tr>
                    <td valign='top'><c:out value="${block.value.getDistrictName()}"/></td>
                    <td valign='top'><c:out value="${block.key}"/></td>
                    <td valign='top'>
                        <ul>
                        <c:forEach items="${block.value.getHouses()}" var="house">
                            <li><c:out value="${house.getHouseNumber()}"/> <c:out value="${house.getStreetName()}"/></li>
                        </c:forEach>
                        </ul>
                    </td>
                </tr>
            </c:forEach>
    </body>
</html>