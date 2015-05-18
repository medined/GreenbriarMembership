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
        <link rel="stylesheet" type="text/css" href="/resources/person.css">
        <title>Greenbriar Membership Management</title>
    </head>
    <body>

<sec:authorize access="hasRole('ROLE_ADMIN')">  
<script>
 function toggleListed( personUuid ) {
  $.get( "/person/toggle_listed/" + personUuid, function( data ) {
   $("#listed_" + personUuid).toggleClass('red-border');
   $("#listed_" + personUuid).toggleClass('green-border');
   $("#listed_" + personUuid).html(data);
  });
}
function toggleDirectory( personUuid ) {
  $.get( "/person/toggle_directory/" + personUuid, function( data ) {
   $("#dir_" + personUuid).toggleClass('red-border');
   $("#dir_" + personUuid).toggleClass('green-border');
   $("#dir_" + personUuid).html(data);
  });
}
function toggle2015Membership( houseUuid ) {
  $.get( "/house/toggle_2015_membership/" + houseUuid, function( data ) {
   $("#2015_" + houseUuid).toggleClass('red-border');
   $("#2015_" + houseUuid).toggleClass('green-border');
  });
}

$.fn.editable.defaults.mode = 'inline';

$(document).ready(function() {
    <c:forEach items="${peopleService.getPeople()}" var="personId">
        $('#lastname_<c:out value="${personId}"/>').editable();
        $('#firstname_<c:out value="${personId}"/>').editable();
        $('#phone_<c:out value="${personId}"/>').editable();
        $('#email_<c:out value="${personId}"/>').editable();
        $('#comment_<c:out value="${personId}"/>').editable();
    </c:forEach>
});
</script>
</sec:authorize>

        <%@include file="header.jsp" %>

    <h1>Edit Every House</h1>

    <table border="1" cellpadding="3" cellspacing="3">
        <tr><th>District</th><th>Blocks</th></tr>
        <c:forEach items="${districtService.getDistricts()}" var="district">
        <tr>
            <td><a href="#<c:out value="${district.getName()}" />"><c:out value="${district.getName()}" /></a></td>
            <td>
            <c:forEach items="${blockService.getBlocks(district.getName())}" var="block">
                <a href="#<c:out value="${block.getBlockName()}" />"><c:out value="${block.getBlockName()}" /></a>&nbsp;&nbsp;
            </c:forEach>
            </td>
        </tr>
        </c:forEach>
    </table>
                    
            <c:forEach items="${districtService.getDistricts()}" var="district">
                <a name="<c:out value="${district.getName()}" />"></a>
                <h2>District <c:out value="${district.getName()}" /></h2>
                <c:forEach items="${blockService.getBlocks(district.getName())}" var="block">
                    <a name="<c:out value="${block.getBlockName()}" />"></a>
                    <h3>Block <c:out value="${block.getBlockName()}" /></h3>
        <table border="0" cellpadding="3" cellspacing="3">
            <c:forEach items="${houseService.getHousesInBlock(block.getBlockName())}" var="house">
                <%@include file="one_house.jsp" %>
            </c:forEach>
        </table>                
                </c:forEach>
            </c:forEach>
    
    </body>
</html>