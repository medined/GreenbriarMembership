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
        <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
        <script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
        <link href="//cdnjs.cloudflare.com/ajax/libs/x-editable/1.5.0/jquery-editable/css/jquery-editable.css" rel="stylesheet"/>
        <script src="//cdnjs.cloudflare.com/ajax/libs/x-editable/1.5.0/jquery-editable/js/jquery-editable-poshytip.min.js"></script>
        <link rel="stylesheet" type="text/css" href="/resources/block.css">
        <title>Greenbriar Membership Management</title>
<style type="text/css" media="print">
      div.page
      {
        page-break-after: always;
        page-break-inside: avoid;
      }
    </style>
    </head>
    <body>
        <%@include file="header.jsp" %>

            <c:forEach items="${districtService.getDistricts()}" var="district">
                <div class="page">
                <h2>Representative <c:out value="${officerService.getDistrictRepresentative(district.getName())}"/></h2>

                <table cellspacing="2" cellpadding="3" border="1" width="100%">
                    <tr>
                        <th width="150px">District</th>
                        <th width="150px">Block</th>
                        <th width="175px">Number of Houses</th>
                        <th width="275px">Captain</th>
                        <th width="225px">Phone</th>
                        <th width="300px">House Info</th>
                    </tr>
                    <c:forEach items="${blockService.getBlocks(district.getName())}" var="block">
                    <tr>
                        <td style="font-size:small"><c:out value="${district.getName()}"/></td>
                        <td style="font-size:small"><c:out value="${block.getBlockName()}"/></td>
                        <td style="font-size:small" align="right"><c:out value="${houseService.getNumberOfHousesInBlock(block.getBlockName())}"/></td>
                        <td style="font-size:small"><c:out value="${blockCaptainService.getCaptainName(block.getBlockName())}"/></td>
                        <td style="font-size:small">&nbsp;</td>
                        <td style="font-size:small">&nbsp;</td>
                    </tr>
                    </c:forEach>
                </table>
                </div>
            </c:forEach>
    </body>
</html>