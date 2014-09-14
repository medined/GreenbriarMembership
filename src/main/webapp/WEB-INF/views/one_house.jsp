                <tr height="10px"><td></td></tr>
                <tr>
                    <td valign="top">
                        <c:out value="${house.getHouseNumber()}"/> <c:out value="${house.getStreetName()}"/>
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
                            
                                <tr>
                                    <td class='last'>
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
                                        <div class='value editable' id='lastname_<c:out value="${person.getPk()}"/>' data-type="text" data-url='/person/update_last' data-pk='<c:out value="${person.getPk()}"/>' data-name='last'>
                                            <c:out value="${person.getLast()}"/>
                                        </div>
                                    </td>
                                    <td class='first'>
                                        <div class='value editable' id='firstname_<c:out value="${person.getPk()}"/>' data-type="text" data-url='/person/update_first' data-pk='<c:out value="${person.getPk()}"/>' data-name='first'>
                                            <c:out value="${person.getFirst()}"/>
                                        </div>
                                    </td>
                                    <td class='phone'>
                                        <div class='value editable' id='phone_<c:out value="${person.getPk()}"/>' data-type="text" data-url='/person/update_phone' data-pk='<c:out value="${person.getPk()}"/>' data-name='phone' style="height: 29px">
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
                                        <div class='value editable' id='email_<c:out value="${person.getPk()}"/>' data-type="text" data-url='/person/update_email' data-pk='<c:out value="${person.getPk()}"/>' data-name='email' style="height: 29px">
                                            <c:out value="${person.getEmail()}"/>
                                        </div>
                                    </td>
                                    <td class='email' style="font-size: 8pt">
                                        <div style="height: 29px"><c:out value="${person.getUpdatedBy()}"/> on <c:out value="${person.getDateUpdated()}"/></div>
                                    </td>
                                    <td class='comment'>
                                        <div class='value editable' id='comment_<c:out value="${person.getPk()}"/>' data-type="text" data-url='/person/update_comment' data-pk='<c:out value="${person.getPk()}"/>' data-name='comment'>
                                            <c:out value="${person.getComment()}"/>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </td>
                </tr>
