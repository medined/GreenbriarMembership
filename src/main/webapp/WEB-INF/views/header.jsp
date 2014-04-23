<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="username">
    <sec:authorize access="isAuthenticated()">  
        User: <strong><sec:authentication property="principal.username"/></strong>
    </sec:authorize>
</div>
<div class="breadcrumbs">
<c:forEach items="${breadcrumbs}" var="breadcrumb">
    <c:if test="${breadcrumb.value.isEmpty()}">
        <c:out value="${breadcrumb.key}"/>
    </c:if>
    <c:if test="${breadcrumb.value.isEmpty() == false}">
        <a href='<c:out value="${breadcrumb.value}"/>'><c:out value="${breadcrumb.key}"/></a>
    </c:if>
</c:forEach>
</div>
<h1>Greenbriar Membership Management</h1>
