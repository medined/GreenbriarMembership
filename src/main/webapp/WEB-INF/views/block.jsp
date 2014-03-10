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
        <title>Greenbriar Membership Management</title>
        <style>
            .editable:hover { 
                background-color:yellow;
            }

            .heading {
  font-size: 10px;
  vertical-align:text-top;
  padding-top: 1px;
  padding-left: 2px;
  padding-bottom: 2px;
  background: black;
  color: yellow;
  border-right: 1px solid gray;
}

.value {
  font-size: 12px;
  border-right: 1px solid gray;
  padding-top: 1px;
  padding-left: 2px;
  padding-bottom: 2px;
  text-overflow: ellipsis;
	white-space: nowrap;
	overflow: hidden;
  }

.email .value {
  width: 10em;
  height: 15px;
}

.phone .value {
  width: 8em;
  height: 15px;
}

.last .value {
  width: 15em;
  height: 15px;
}

.first .value {
  width: 15em;
  height: 15px;
}
        </style>
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
                        <c:forEach items="${house.getPeople()}" var="person">
<table cellpadding="0" border="0" cellspacing="0">
<tr>
    <c:if test="${person.isListed()}">
    <td style="padding-right: 5px;"><img height="20px" width="60px" src="/resources/listed.png" /></td>
    </c:if>
    <c:if test="${person.isUnlisted()}">
    <td style="padding: 0px; padding-right: 5px;"><img height="20px" width="60px" src="/resources/unlisted.png" /></td>
    </c:if>

    <c:if test="${person.isNoDirectory()}">
    <td style="padding-right: 5px;"><img height="20px" width="60px" src="/resources/no_directory.png" /></td>
    </c:if>
    <c:if test="${person.inDirectory()}">
    <td style="padding: 0px; padding-right: 5px;"><img height="20px" width="60px" src="/resources/directory.png" /></td>
    </c:if>

    <!-- 1cc758 green -->
<td class='last'>
<div class='heading'>Last Name</div>
<div class='value'><c:out value="${person.getLast()}"/></div>
</td>
<td class='first'>
<div class='heading'>First Name</div>
<div class='value'><c:out value="${person.getFirst()}"/></div>
</td>
<td class='phone'>
<div class='heading'>Phone</div>
<div class='value'><c:out value="${person.getPhone()}"/></div>
</td>
<td class='email'>
<div class='heading'>Email</div>
<div class='value'><c:out value="${person.getEmail()}"/></div>
</td>
</tr></table>
                            Comment: <c:out value="${person.getComment()}"/><br/>
                            <br/>
                        </c:forEach>
                    </td>
                    <td valign="top">
                        <c:forEach items="${house.getYears()}" var="year">
                            <c:out value="${year.getYear()}"/><br/>
                        </c:forEach>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>