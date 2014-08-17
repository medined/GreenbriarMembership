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
function toggle2014Membership( houseUuid ) {
  $.get( "/house/toggle_2014_membership/" + houseUuid, function( data ) {
   $("#2014_" + houseUuid).toggleClass('red-border');
   $("#2014_" + houseUuid).toggleClass('green-border');
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
                <tr height="1px"><td colspan="2" bgcolor="#00CC99"></td></tr>
                <tr>
                    <td valign="top" height="10px"><c:out value="${house.getHouseNumber()}"/> <c:out value="${house.getStreetName()}"/></td>
                    <td height="10px">

<sec:authorize access="hasRole('ROLE_ADMIN')">  
                        <div style="height: 20px;"><a href="/house/add_person/<c:out value="${house.getId()}"/>">Add Person</a></div>
</sec:authorize>
                    </td>
                </tr>
                <tr><td colspan="2">

                        <c:forEach items="${peopleService.getPeopleInHouse(house.getHouseNumber(), house.getStreetName())}" var="person" varStatus="loop">
                            
                            <script>
                            </script>
                            
                            <table cellpadding="0" border="0" cellspacing="0">
                                <tr>
                                    <td class='last'>
                                        <c:if test="${loop.index == 0}">
                                            <div class='heading'>&nbsp;</div>
                                        </c:if>
                                        <div class="editable-click">
<sec:authorize access="hasRole('ROLE_ADMIN')">  
<a onclick="return confirm('Do you really want to remove [<c:out value="${person.getFirst()}"/> <c:out value="${person.getLast()}"/>]?')" href="/person/delete/<c:out value="${person.getPk()}"/>"><img src="/resources/remove-icon.png" height="15" width="25"></a>
</sec:authorize>
                                        <span id="listed_<c:out value="${person.getPk()}"/>" onclick="toggleListed('<c:out value="${person.getPk()}"/>'); return false;" class="<c:out value="${person.listedStyle()}"/>">
                                            <c:out value="${person.listed()}"/>
                                        </span>
                                        <span id="dir_<c:out value="${person.getPk()}"/>" onclick="toggleDirectory('<c:out value="${person.getPk()}"/>'); return false;" class="<c:out value="${person.directoryStyle()}"/>">
                                            <c:out value="${person.directory()}"/>
                                        </span>
                                        </div>
                                    </td>

                                    <td class='last'>
                                        <c:if test="${loop.index == 0}">
                                            <div class='heading'>Last Name</div>
                                        </c:if>
                                        <div class='value editable' id='lastname_<c:out value="${person.getPk()}"/>' data-type="text" data-url='/person/update_last' data-pk='<c:out value="${person.getPk()}"/>' data-name='last'>
                                            <c:out value="${person.getLast()}"/>
                                        </div>
                                    </td>
                                    <td class='first'>
                                        <c:if test="${loop.index == 0}">
                                            <div class='heading'>First Name</div>
                                        </c:if>
                                        <div class='value editable' id='firstname_<c:out value="${person.getPk()}"/>' data-type="text" data-url='/person/update_first' data-pk='<c:out value="${person.getPk()}"/>' data-name='first'>
                                            <c:out value="${person.getFirst()}"/>
                                        </div>
                                    </td>
                                    <td class='phone'>
                                        <c:if test="${loop.index == 0}">
                                            <div class='heading'>Phone</div>
                                        </c:if>
                                        <div class='value editable' id='phone_<c:out value="${person.getPk()}"/>' data-type="text" data-url='/person/update_phone' data-pk='<c:out value="${person.getPk()}"/>' data-name='phone'>
                                            <sec:authorize access="hasRole('ROLE_USER')">  
                                                <c:if test="${person.listed().equals('Listed') == false}">
                                                    UNLISTED
                                                </c:if>
                                                <c:if test="${person.listed().equals('Listed')}">
                                                    <c:out value="${person.getPhone()}"/>
                                                </c:if>
                                            </sec:authorize>
                                            <sec:authorize access="hasRole('ROLE_ADMIN')">  
                                                <c:out value="${person.getPhone()}"/>
                                            </sec:authorize>
                                        </div>
                                    </td>
                                    <td class='email'>
                                        <c:if test="${loop.index == 0}">
                                            <div class='heading'>Email</div>
                                        </c:if>
                                        <div class='value editable' id='email_<c:out value="${person.getPk()}"/>' data-type="text" data-url='/person/update_email' data-pk='<c:out value="${person.getPk()}"/>' data-name='email'>
                                            <c:out value="${person.getEmail()}"/>
                                        </div>
                                    </td>
                                    <td class='comment'>
                                        <c:if test="${loop.index == 0}">
                                            <div class='heading'>Comment</div>
                                        </c:if>
                                        <div class='value editable' id='comment_<c:out value="${person.getPk()}"/>' data-type="text" data-url='/person/update_comment' data-pk='<c:out value="${person.getPk()}"/>' data-name='comment'>
                                            <c:out value="${person.getComment()}"/>
                                        </div>
                                    </td>
                                    <td class='membership'>
                                        <c:if test="${loop.index == 0}">
                                            <div class='heading'>Member</div>
                                        </c:if>
                                        <div class="editable-click">
                                            <!-- only the first person in the house shows the membership status -->
                                            <c:if test="${loop.index == 0}">
                                                <span class='<c:out value="${house.memberInYear2012Style()}"/>' style='width:50px; margin-left: 5px;'>
                                                    2012
                                                </span>
                                                <span class='<c:out value="${house.memberInYear2013Style()}"/>' style='width:50px; margin-left: 5px;'>
                                                    2013
                                                </span>
                                                <span id="2014_<c:out value="${house.getId()}"/>" onclick="toggle2014Membership('<c:out value="${house.getId()}"/>'); return false;"  class='<c:out value="${house.memberInYear2014Style()}"/>' style='width:50px; margin-left: 5px;'>
                                                    2014
                                                </span>
                                            </c:if>
                                        </div>
                                    </td>
                                </tr></table>
                            </c:forEach>
                    </td>
                </tr>
                <tr height="20px"><td colspan="2"></td></tr>
            </c:forEach>
        </table>                
                </c:forEach>
            </c:forEach>
    
    </body>
</html>