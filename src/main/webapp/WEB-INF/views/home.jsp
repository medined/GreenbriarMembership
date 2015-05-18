<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Greenbriar Membership Management</title>
    </head>
    <body>
        <%@include file="header.jsp" %>

        <ul style='line-height: 24px; list-style-type: none;'>
            <li><a href='/districts'>District List</a></li>
            <li><a href='/changes'>Changes List</a></li>
            <li>&nbsp;</li>
            <li><a href='/editeveryhouse'>Edit Every House</a></li>
            <li><a href='/houses'>House Lookup</a></li>
            <li><a href='/people'>Person Lookup</a></li>
            <li>&nbsp;</li>
            <li><a href='/districtrepreport'>District Representative Report</a></li>
            <li>&nbsp;</li>
            <li><a href='/blockcaptains'>Block Captain List</a></li>
            <li><a href='/noblockcaptains'>Blocks without Captain List</a></li>
            <li><a href='/simplenoblockcaptains'>Simple Blocks without Captain List</a></li>
            <sec:authorize access="hasRole('ROLE_REPORT')">
                    <li><a href='/blockcaptains/pdf'>Block Captain Renewal Report</a></li>
            </sec:authorize>
            <li>&nbsp;</li>
            <li><a href='/person/emails'>Email List</a></li>
            <li><a href='/person/bad_emails'>Bad Email List</a></li>
            <div>
                <form action="/emailsearch" method="post">
                     Email Search: <input type="text" name="email" />
                 </form>
            </div>
            <li>&nbsp;</li>
            <sec:authorize access="isAuthenticated()">  
                <li><a href="/j_spring_security_logout">Logout</a></li>
            </sec:authorize>
        </ul>
                
        </p>
    </body>
</html>
