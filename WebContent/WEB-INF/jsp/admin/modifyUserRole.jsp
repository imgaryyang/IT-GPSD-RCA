<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";

    Map<String, String> accessMap = new HashMap<String, String>();
    accessMap.put("-1", "No Access");
    accessMap.put("0", "Public Read");
    accessMap.put("1", "Read");
    accessMap.put("2", "Save");
    accessMap.put("4", "Submit");
    accessMap.put("8", "Accept/Reject");
    accessMap.put("16", "Contract Admin");
    accessMap.put("32", "Full Admin");
    request.setAttribute("accessMap", accessMap);

%>

<table>
    <tr>
        <td>
            <div class="bar-blue-black">User Roles</div>
        </td>
    </tr>
    <tr>
        <td width="900">
            <div class="filterbar"
                 style="height: 20px; text-align: right; font-size: 0.9em; font-weight: bold; margin-top: 5px;">
                <span> Showing Records [ ${startRow} - ${endRow} ] of Total${totalRowCount} </span>
                <span> </span> <a href="javascript:void(0)" rel="prev" onclick="return getPreviousPage();">�
                Previous </a> |
                <a href="javascript:void(0)" rel="next" onclick="return getNextPage();">Next �</a>
            </div>
        </td>
    </tr>

    <tr>
        <td>
            <table cellspacing="0" cellpadding="0" border="0" id="nonContractData" summary="nonContracts"
                   class="ibm-data-table">
                <thead>
                <tr class="eap-table-data">
                    <th class="bar-gray-light-test" scope="col" align="center">Intranet Id</th>
                    <th class="bar-gray-light-test" scope="col" align="center">Role</th>
                    <th class="bar-gray-light-test" scope="col" align="center">Account</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach items="${userRole}" var="userRole">
                    <c:if test="${userRole.contract != null}">
                        <c:set var="contractId" scope="request" value="${userRole.contract.contractId}"/>
                    </c:if>
                    <c:if test="${userRole.contract == null}">
                        <c:set var="contractId" scope="request" value=""/>
                    </c:if>
                    <tr>
                        <td scope="row"><a href="addUserRole.htm?ur_id=${userRole.urId}"
                                           onclick="return editACLEntry(${userRole.urId}, '${contractId}');">${userRole.gpsUser.email}</a>
                        </td>
                        <td scope="row">${userRole.role}</td>
                        <td scope="row">
                            <c:if test="${userRole.contract != null}">${userRole.contract.title}</c:if>
                        </td>
                        <td scope="row"><a class="ibm-delete-link"
                                           onclick="return dropUserRoleEntry('${userRole.urId}')">Delete</a></td>

                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </td>
    </tr>
</table>