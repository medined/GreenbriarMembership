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
        <a href='/'>Home</a> : 
        <a href='/districts'>Districts</a> :
        <a href='/district/<c:out value="${block.getDistrictName()}"/>'><c:out value="${block.getDistrictName()}"/></a> :
        <c:out value="${block.getBlockName()}"/>

        <h1>Greenbriar Membership Management</h1>

        <h1><c:out value="${block.getHouses().size()}" /> Houses for Block <c:out value="${block.getBlockName()}"/></h1>

        <table border="0" cellpadding="3" cellspacing="3">
            <c:forEach items="${block.getHouses()}" var="house">
                <tr height="10px"><td></td></tr>
                <tr>
                    <td valign="top"><c:out value="${house.getHouseNumber()}"/> <c:out value="${house.getStreetName()}"/></td>
                    <td>

                        Add Person

                        <c:forEach items="${house.getPeople()}" var="person" varStatus="loop">
                            <table cellpadding="0" border="0" cellspacing="0">
                                <tr>
                                    <td style="padding-right: 5px;"><span class="<c:out value="${person.listedStyle()}"/>"><c:out value="${person.listed()}"/></span></td>
                                    <td style="padding-right: 5px;"><span class="<c:out value="${person.directoryStyle()}"/>"><c:out value="${person.directory()}"/></span></td>

                                    <td class='last'>
                                        <c:if test="${loop.index == 0}">
                                            <div class='heading'>Last Name</div>
                                        </c:if>
                                        <div class='value editable'><c:out value="${person.getLast()}"/></div>
                                    </td>
                                    <td class='first'>
                                        <c:if test="${loop.index == 0}">
                                            <div class='heading'>First Name</div>
                                        </c:if>
                                        <div class='value editable'><c:out value="${person.getFirst()}"/></div>
                                    </td>
                                    <td class='phone'>
                                        <c:if test="${loop.index == 0}">
                                            <div class='heading'>Phone</div>
                                        </c:if>
                                        <div class='value editable'><c:out value="${person.getPhone()}"/></div>
                                    </td>
                                    <td class='email'>
                                        <c:if test="${loop.index == 0}">
                                            <div class='heading'>Email</div>
                                        </c:if>
                                        <div class='value editable'><c:out value="${person.getEmail()}"/></div>
                                    </td>
                                    <td class='comment'>
                                        <c:if test="${loop.index == 0}">
                                            <div class='heading'>Comment</div>
                                        </c:if>
                                        <div class='value editable'><c:out value="${person.getComment()}"/></div>
                                    </td>
                                    <td class='membership'>
                                        <c:if test="${loop.index == 0}">
                                            <div class='heading'>Member</div>
                                        </c:if>
                                        <div class='value'>
                                            <!-- only the first person in the house shows the membership status -->
                                            <c:if test="${loop.index == 0}">
                                                <c:if test='${house.memberInYear("2012")}'>
                                                    <img height="15px" width="40px" src="/resources/2012_green.png" />
                                                </c:if>
                                                <c:if test='${house.notMemberInYear("2012")}'>
                                                    <img height="15px" width="40px" src="/resources/2012_red.png" />
                                                </c:if>

                                                <c:if test='${house.memberInYear("2013")}'>
                                                    <img height="15px" width="40px" src="/resources/2013_green.png" />
                                                </c:if>
                                                <c:if test='${house.notMemberInYear("2013")}'>
                                                    <img height="15px" width="40px" src="/resources/2013_red.png" />
                                                </c:if>

                                                <c:if test='${house.memberInYear("2014")}'>
                                                    <img height="15px" width="40px" src="/resources/2014_green.png" />
                                                </c:if>
                                                <c:if test='${house.notMemberInYear("2014")}'>
                                                    <img class="editable" height="15px" width="40px" src="/resources/2014_red.png" />
                                                </c:if>
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