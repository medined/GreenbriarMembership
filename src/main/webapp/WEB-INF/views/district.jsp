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
            $('#representative_<c:out value="${districtName}"/>').editable();
            <c:forEach items="${blockService.getBlocks(districtName)}" var="block">
                $('#captain_<c:out value="${block.getBlockName()}"/>').editable();
            </c:forEach>
        });            
        </script>
        </sec:authorize>
        <%@include file="header.jsp" %>

        <table cellpadding="0" cellspacing="0" border="0">
            <tr>
                <td valign="top">
        <table cellpadding="5" cellspacing="0" border="1" style="margin-top: 15px; margin-left: 15px;">
            <tr>
                <td>Blocks</td>
                <td><c:out value="${blockService.getBlocks().size()}" /></td>
            </tr>
            <tr>
                <td>Representative</td>
                <td>
                    <div class='value editable' id='representative_<c:out value="${districtName}"/>' data-type="text" data-url='/district/update_representative' data-pk='<c:out value="${districtName}"/>' data-name='representative'>
                        <c:out value="${districtRepresentative}" />
                    </div>
                </td>
            </tr>
        </table>
                </td>
                <td>

        <table cellpadding="5" cellspacing="0" border="1" style="margin-top: 15px; margin-left: 15px;">
            <tr>
                <th rowspan="2" valign="top">Block</th>
                <th rowspan="2" valign="top">Captain</th>
                <th rowspan="2" valign="top">Houses</th>
                <th colspan="3" valign="top">Membership</th>
            </tr>
            <tr>
                <th>2013</th>
                <th>2014</th>
                <th>2015</th>
            </tr>
            <c:forEach items="${blockService.getBlocks(districtName)}" var="block">
                <tr>
                    <td><a href='/block/${block.getBlockName()}'><c:out value="${block.getBlockName()}"/></a></td>
                    <td>
                        <div class='value editable' id='captain_<c:out value="${block.getBlockName()}"/>' data-type="text" data-url='/block/update_captain' data-pk='<c:out value="${block.getBlockName()}"/>' data-name='captain'>
                        <c:out value='${blockCaptainService.getCaptainName(block.getBlockName())}' />
                        </div>
                    </td>
                    <td align="right"><c:out value='${houseService.getHousesInBlock(block.getBlockName()).size()}' /></td>
                    <td align="right"><c:out value='${houseService.getPercentMembership(block.getBlockName(), "2013")}' />%</td>
                    <td align="right"><c:out value='${houseService.getPercentMembership(block.getBlockName(), "2014")}' />%</td>
                    <td align="right"><c:out value='${houseService.getPercentMembership(block.getBlockName(), "2015")}' />%</td>
                </tr>
            </c:forEach>
        </table>
                </td>
            </tr>
        </table>

        <h3><c:out value="${emails.size()}"/> Email Addresses</h3>
        <c:forEach items="${emails}" var="email">
            <c:if test="${not email.isEmpty()}">
                    <c:out value='${email}' />,
            </c:if>
        </c:forEach>

    </body>
</html>