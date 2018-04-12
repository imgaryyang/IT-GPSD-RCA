<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!--<div style='overflow:scroll; width:750px;height:400px;'>-->
<div style="overflow:scroll; width: 750px; height: 400px; position: relative; border-width: 1px; border-style: solid; border-color: rgb(51, 51, 51) rgb(204, 204, 204) rgb(204, 204, 204) rgb(51, 51, 51); -moz-border-top-colors: none; -moz-border-right-colors: none; -moz-border-bottom-colors: none; -moz-border-left-colors: none; border-image: none; font: 11px sans-serif;">
<c:if test="${not empty rcas}">
	<div id="displayMsg"></div>
	<table cellspacing="0" cellpadding="0" border="0" id="contractData"
				   summary="contracts" class="ibm-data-table">
	<thead>
	<tr>
		<th></th>
		<th><a href="#" onclick="selectAllRptCheckBoxes(); return false;">All</a>, <a href="#" onclick="unSelectAllRptCheckBoxes(); return false;">None</a></th>
	</tr>
	</thead>

	<tbody>
	<input type="hidden" id="totalRows" value="${totalRowCount}" />
	<c:forEach items="${rcas}" var="rca" varStatus="itemRow">
		<tr>
			<td width="5%"><input type="checkbox" id="selectedRca_${itemRow.index}" value="${rca.rcaOrActionNumber}" /></td>
			<td width="95%" scope="row">${rca.rcaAccountTitle} - ${rca.primaryTicket} | ${rca.month}, ${rca.year} | ${rca.rcaOrActionNumber}</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
</c:if>

<c:if test="${empty rcas}">
	<table cellspacing="0" cellpadding="0" border="0" id="contractData"
		summary="contracts" class="ibm-data-table">
		<thead>
			<tr>
				<td>There is nothing for you to view</td>
			</tr>
		</thead>
	</table>

</c:if>
</div>
	<table cellspacing="0" cellpadding="0" border="0" id="contractData"
		summary="contracts" class="ibm-results-table">
		<thead>
			<tr>
			     <td width="85%"><td>
				 <td width="15%">
				 <c:choose>
                 		<c:when test="${formType=='rca'}">
                            Total RCAs: ${totalRowCount}
                        </c:when>
                        <c:otherwise>
                            Total Actions: ${totalRowCount}
                        </c:otherwise>
                 </c:choose>

				 </td>
			</tr>
		</thead>
	</table>





