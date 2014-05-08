<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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
        <sec:authorize access="hasRole('ROLE_ADMIN')">  
        <script>
        $.fn.editable.defaults.mode = 'inline';

        $(document).ready(function() {
            <c:forEach items="${captains}" var="block">
            $('#captain_<c:out value="${block.key}"/>').editable();
            </c:forEach>
         });
        </script>
        </sec:authorize>
        <%@include file="header.jsp" %>

        <table cellspacing="3" cellpadding="3" border="0">
            <tr>
                <th>District</th>
                <th>Block</th>
                <th>Captain</th>
            </tr>
            <c:forEach items="${captains}" var="block">
                <tr>
                    <td><c:out value="${blockService.getDistrictName(block.key)}"/></td>
                    <td><c:out value="${block.key}"/></td>
                    <td>
                        <div class='value editable' id='captain_<c:out value="${block.key}"/>' data-type="text" data-url='/block/update_captain' data-pk='<c:out value="${block.key}"/>' data-name='captain'>
                        <c:out value="${block.value}"/>
                        </div>
                    </td>
                </tr>
            </c:forEach>
    </body>
</html>