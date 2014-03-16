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
<script>
function toggleListed( personUuid ) {
  $.get( "/person/toggle_listed/" + personUuid, function( data ) {
   $("#listed_" + personUuid).toggleClass('negate');
   $("#listed_" + personUuid).html(data);
  });
}
function toggleDirectory( personUuid ) {
  $.get( "/person/toggle_directory/" + personUuid, function( data ) {
   $("#dir_" + personUuid).toggleClass('negate');
   $("#dir_" + personUuid).html(data);
  });
}
function toggle2014Membership( houseUuid ) {
  $.get( "/house/toggle_2014_membership/" + houseUuid, function( data ) {
   $("#2014_" + houseUuid).toggleClass('negate');
  });
}
//turn to inline mode
$.fn.editable.defaults.mode = 'inline';

$(document).ready(function() {
    $('#captain_<c:out value="${block.getBlockName()}"/>').editable();
    <c:forEach items="${block.getPeople()}" var="person">
        $('#lastname_<c:out value="${person.getPk()}"/>').editable();
        $('#firstname_<c:out value="${person.getPk()}"/>').editable();
        $('#phone_<c:out value="${person.getPk()}"/>').editable();
        $('#email_<c:out value="${person.getPk()}"/>').editable();
        $('#comment_<c:out value="${person.getPk()}"/>').editable();
    </c:forEach>
});

</script>
        <h1>Greenbriar Membership Management</h1>

        <a href='/'>Home</a> : 
        <a href='/districts'>Districts</a> :
        <a href='/district/<c:out value="${block.getDistrictName()}"/>'><c:out value="${block.getDistrictName()}"/></a> :
        <c:out value="${block.getBlockName()}"/>

        <table cellpadding="5" cellspacing="0" border="1" style="margin-top: 15px; margin-left: 15px;">
            <tr>
                <td>Houses</td>
                <td><c:out value="${block.getHouses().size()}" /></td>
            </tr>
            <tr>
                <td>Captain</td>
                <td>
                    <div class='value editable' id='captain_<c:out value="${block.getBlockName()}"/>' data-type="text" data-url='/block/update_captain' data-pk='<c:out value="${block.getBlockName()}"/>' data-name='captain'>
                    <c:out value='${block.getCaptainName()}' />
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
                            <td align="right"><c:out value='${block.getPercentMembership("2012")}' />%</td>
                            <td align="right"><c:out value='${block.getPercentMembership("2013")}' />%</td>
                            <td align="right"><c:out value='${block.getPercentMembership("2014")}' />%</td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        
        <table border="0" cellpadding="3" cellspacing="3">
            <c:forEach items="${block.getHouses()}" var="house">
                <tr height="10px"><td></td></tr>
                <tr>
                    <td valign="top"><c:out value="${house.getHouseNumber()}"/> <c:out value="${house.getStreetName()}"/></td>
                    <td>

                        Add Person

                        <c:forEach items="${house.getPeople()}" var="person" varStatus="loop">
                            
                            <script>
                            </script>
                            
                            <table cellpadding="0" border="0" cellspacing="0">
                                <tr>
                                    <td class='last'>
                                        <c:if test="${loop.index == 0}">
                                            <div class='heading'>&nbsp;</div>
                                        </c:if>
                                        <div class="editable-click">
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
                                            <c:out value="${person.getPhone()}"/>
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
                                                <span id="2014_<c:out value="${house.getUuid()}"/>" onclick="toggle2014Membership('<c:out value="${house.getUuid()}"/>'); return false;"  class='<c:out value="${house.memberInYear2014Style()}"/>' style='width:50px; margin-left: 5px;'>
                                                    2014
                                                </span>
                                            </c:if>
                                        </div>
                                    </td>
                                </tr></table>
                            </c:forEach>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>