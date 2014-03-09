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
        <a href='/'>Home</a> : 
        <a href='/districts'>Districts</a> :
        <c:out value="${district.name}"/>

        <h1>Greenbriar Membership Management</h1>

        <h1><c:out value="${district.getBlocks().size()}" /> Blocks for District <c:out value="${district.name}"/></h1>
        
        <ul>
            <c:forEach items="${district.getBlocks()}" var="block"><li><a href='/block/${block.getBlockName()}'><c:out value="${block.getBlockName()}"/></a></li></c:forEach>
        </ul>
    </body>
</html>