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
    </head>
    <body>
        <a href='/'>Home</a><br/>

        <h1>Greenbriar Membership Management</h1>
        
        <h2>Block Captains</h2>

        <c:forEach items="${captains.keySet()}" var="blockName">
            <c:out value="${blockName} ${captains.get(blockName)}"/><br/>
        </c:forEach>
    </body>
</html>