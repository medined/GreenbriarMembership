<tr height='10px'>
    <td>
        <a name='<c:out value="${house.getId()}"/>'/>
    </td>
</tr>
<tr>
    <td valign="top">
        <a title='District: <c:out value="${house.getDistrictName()}"/> Block: <c:out value="${house.getBlockName()}"/>' href="http://localhost:8080/block/<c:out value="${house.getBlockName()}"/>#<c:out value="${house.getId()}"/>">
        <c:out value="${house.getHouseNumber()}"/> <c:out value="${house.getStreetName()}"/>
        </a>
        <div><span class='<c:out value="${house.memberInYear2012Style()}"/>'>2012</span>
            <span class='<c:out value="${house.memberInYear2013Style()}"/>'>2013</span>
            <span id="2014_<c:out value="${house.getId()}"/>" onclick="toggle2014Membership('<c:out value="${house.getId()}"/>'); return false;"  class='<c:out value="${house.memberInYear2014Style()}"/>'>2014</span>
        </div>
        <sec:authorize access="hasRole('ROLE_ADMIN')"><div style="font-size: 15px; height: 20px;"><a href="/house/add_person/<c:out value="${house.getId()}"/>">Add Person</a></div></sec:authorize>
    </td>
    <td valign="top">

        <table class="person">
            <tr>
                <th class="flags">Flags</th>
                <th class="last_name">Last Name</th>
                <th class="first_name">First Name</th>
                <th class="phone">Phone</th>
                <th class="email">Email</th>
                <th class="updated_by">Updated By</th>
                <th class="comment">Comment</th>
            </tr>

            <c:forEach items="${peopleService.getPeopleInHouse(house.getHouseNumber(), house.getStreetName())}" var="person" varStatus="loop">
                <%@include file="one_person.jsp" %>
            </c:forEach>
        </table>
    </td>
</tr>
<tr height="20px">
    <td>
    </td>
</tr>