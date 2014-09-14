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
   $("#listed_" + personUuid).toggleClass('greend-border');
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
function toggle2014Membership( houseUuid ) {
  $.get( "/house/toggle_2014_membership/" + houseUuid, function( data ) {
   $("#2014_" + houseUuid).toggleClass('red-border');
   $("#2014_" + houseUuid).toggleClass('green-border');
  });
}

$.fn.editable.defaults.mode = 'inline';

$(document).ready(function() {
    $('#captain_<c:out value="${blockName}"/>').editable();
    $('#representative_<c:out value="${districtName}"/>').editable();
    <c:forEach items="${peopleService.getPeopleInBlock(blockName)}" var="person">
        $('#lastname_<c:out value="${person.getPk()}"/>').editable();
        $('#firstname_<c:out value="${person.getPk()}"/>').editable();
        $('#phone_<c:out value="${person.getPk()}"/>').editable();
        $('#email_<c:out value="${person.getPk()}"/>').editable();
        $('#comment_<c:out value="${person.getPk()}"/>').editable();
    </c:forEach>
});
</script>
</sec:authorize>
    <%@include file="header.jsp" %>

        <table cellpadding="5" cellspacing="0" border="1" style="margin-top: 15px; margin-left: 15px;">
            <tr>
                <td>Houses</td>
                <td><c:out value="${houseService.getHousesInBlock(blockName).size()}" /></td>
            </tr>
            <tr>
                <td>Captain</td>
                <td>
                    <div class='value editable' id='captain_<c:out value="${blockName}"/>' data-type="text" data-url='/block/update_captain' data-pk='<c:out value="${blockName}"/>' data-name='captain'>
                    <c:out value='${blockCaptain}' />
                    </div>
                </td>
            </tr>
            <tr>
                <td>Representative</td>
                <td>
                    <div class='value editable' id='representative_<c:out value="${districtName}"/>' data-type="text" data-url='/district/update_representative' data-pk='<c:out value="${districtName}"/>' data-name='representative'>
                    <c:out value='${districtRepresentative}' />
                    </div>
                </td>
            </tr>
            <tr>
                <td>Percent Membership</td>
                <td>
                    <table cellpadding="5" cellspacing="0" border="1" style="margin-top: 15px; margin-left: 15px;">
                        <tr>
                            <th>2012</th>
                            <th>2013</th>
                            <th>2014</th>
                        </tr>
                        <tr>
                            <td align="right"><c:out value='${houseService.getPercentMembership(block.getBlockName(), "2012")}' />%</td>
                            <td align="right"><c:out value='${houseService.getPercentMembership(block.getBlockName(), "2013")}' />%</td>
                            <td align="right"><c:out value='${houseService.getPercentMembership(block.getBlockName(), "2014")}' />%</td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>

        <table border="0" cellpadding="3" cellspacing="3">
            <c:forEach items="${houseService.getHousesInBlock(blockName)}" var="house">
                <%@include file="one_house.jsp" %>
            </c:forEach>
        </table>
    </body>
</html>