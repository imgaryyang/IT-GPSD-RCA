<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <c:if test="${not empty rcaForm.rcaHistoryLogs}">
        <table id="incident_details" summary="Contacts" class="ibm-results-table" border="0">
            <thead>
            <tr>
                <td scope="col" align="center" class="bar-green-black-small" width="20%">Submit Date</td>
                <th width="1%"></th>
                <td scope="col" align="center" class="bar-green-black-small" width="13%">Form Action</td>
                <th width="1%"></th>
                <td scope="col" align="center" class="bar-green-black-small" width="35%">Submit Comments</td>
                <th width="1%"></th>
                <td scope="col" align="center" class="bar-green-black-small" width="20%">Submitted By</td>
                <th width="1%"></th>
                <td scope="col" align="center" class="bar-green-black-small" width="13%">Role</td>
                 </tr>
            </thead>
        </table>
    </c:if>
    <c:forEach items="${rcaForm.rcaHistoryLogs}" var="item" varStatus="itemRow" >
           <table id="incident_details" summary="Contacts" class="ibm-results-table" border="0">
            <thead>
            <tr>
                <td scope="col" align="center" width="20%">${item.submittedOn}</td>
                <th width="1%"></th>
                <td scope="col" width="13%" align="center">${item.formAction}</td>
                <th width="1%"></th>
                <td scope="col" align="center" width="35%">${item.comments}</td>
                <th width="1%"></th>
                <td scope="col" align="center" width="20%">${item.submittedBy.email}</td>
                <th width="1%"></th>
                <td scope="col" align="center" width="13%">${item.role}</td>
            </tr>
            </thead>
        </table>

    </c:forEach>
