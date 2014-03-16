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
        <link rel="stylesheet" type="text/css" href="/resources/block.css">
        <title>Greenbriar Membership Management</title>
    </head>
    <body>
        <h1>Greenbriar Membership Management</h1>

        <a href='/'>Home</a> : 
        <a href='/districts'>Districts</a> :
        <c:out value="${district.name}"/>

        <table cellpadding="5" cellspacing="0" border="1" style="margin-top: 15px; margin-left: 15px;">
            <tr>
                <td>Blocks</td>
                <td><c:out value="${district.getBlocks().size()}" /></td>
            </tr>
        </table>

        <table cellpadding="5" cellspacing="0" border="1" style="margin-top: 15px; margin-left: 15px;">
            <tr>
                <th rowspan="2" valign="top">Block</th>
                <th rowspan="2" valign="top">Captain</th>
                <th rowspan="2" valign="top">Houses</th>
                <th colspan="3" valign="top">Membership</th>
            </tr>
            <tr>
                <th>2012</th>
                <th>2013</th>
                <th>2014</th>
            </tr>
            <c:forEach items="${district.getBlocks()}" var="block">
                <tr>
                    <td><a href='/block/${block.getBlockName()}'><c:out value="${block.getBlockName()}"/></a></td>
                    <td><c:out value='${block.getCaptainName()}' /></td>
                    <td align="right"><c:out value='${block.getHouses().size()}' /></td>
                    <td align="right"><c:out value='${block.getPercentMembership("2012")}' />%</td>
                    <td align="right"><c:out value='${block.getPercentMembership("2013")}' />%</td>
                    <td align="right"><c:out value='${block.getPercentMembership("2014")}' />%</td>
                </tr>
            </c:forEach>
        </table>

    </body>
</html>