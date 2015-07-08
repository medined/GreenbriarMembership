<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ page import="java.util.Set" %>
<%@ page import="org.egreenbriar.model.House" %>
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
    <c:forEach items="${houses}" var="house">
        <c:forEach items="${peopleService.getPeopleInHouse(house.getHouseNumber(), house.getStreetName(), false)}" var="person" varStatus="loop">
            $('#lastname_<c:out value="${person.getPk()}"/>').editable();
            $('#firstname_<c:out value="${person.getPk()}"/>').editable();
            $('#phone_<c:out value="${person.getPk()}"/>').editable();
            $('#email_<c:out value="${person.getPk()}"/>').editable();
            $('#comment_<c:out value="${person.getPk()}"/>').editable();
        </c:forEach>
    </c:forEach>
});
</script>
</sec:authorize>
        
        <%@include file="header.jsp" %>

        <h2 style='margin: 0px'>House Number <c:out value="${houseNumber}"/> Search Results</h2>
        <p style='font-size: 10pt; margin: 0px'>Hover over Street Number for District and Block information. Click Street Number for Block page.</p>
        
        <table border="0" cellpadding="3" cellspacing="3">
            <c:forEach items="${houses}" var="house">
                <%@include file="one_house.jsp" %>
            </c:forEach>
        </table>            
    </body>
</html>