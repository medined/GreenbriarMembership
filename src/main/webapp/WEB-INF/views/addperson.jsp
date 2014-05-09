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
    <%@include file="header.jsp" %>
    <h2>Add Person</h2>

<form action="/house/newperson" method="post">
  <input type="hidden" name="districtName" value="<c:out value="${districtName}"/>">
  <input type="hidden" name="blockName" value="<c:out value="${blockName}"/>">
  <input type="hidden" name="houseId" value="<c:out value="${house.getId()}"/>">
  <table cellpadding="2" bgcolor="#CCCCCC" style="padding: 25px">
    <tr><td>House Number</td><td><c:out value="${house.getHouseNumber()}"/></td></tr>
    <tr><td>Street Name</td><td><c:out value="${house.getStreetName()}"/></td></tr>
    <tr><td>Last Name</td><td><input type="text" name="lastName" value=""></td></tr>
    <tr><td>First Name</td><td><input type="text" name="firstName" value=""></td></tr>
    <tr><td>Phone</td><td><input type="text" name="phone" value=""></td></tr>
    <tr><td>Email</td><td><input type="text" name="email" value=""></td></tr>
    <tr><td>Comments</td><td><input type="text" name="comments" value=""></td></tr>
    <tr><td>Phone is Unlisted</td><td>
        <select name="unlisted">
          <option value="1">Yes</option>
          <option value="0">No</option>
        </select> 
    </td></tr>
    <tr><td>List in Greenbriar Directory</td><td>
        <!-- notice the boolean values are reversed from the label. -->
        <select name="nodirectory">
          <option value="0">Yes</option>
          <option value="1" selected>No</option>
        </select> 
    </td></tr>
    <tr colspan="2" align="center"><td>
            <input type="submit" value="Submit">
            &nbsp;&nbsp;
            <a href="javascript:history.back()">Cancel</a>
        </td></tr>
  </table>
</form>

    </body>
</html>