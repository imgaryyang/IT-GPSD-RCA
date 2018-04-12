<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${not empty rcas}">
	<div class="filterbar"
		 style="height: 20px; text-align: right; font-size: 0.9em; font-weight: bold; margin-top: 5px;">
		<form action="nextPrevious">
			<span> Showing Records [ ${startRow} - ${endRow} ] of Total
				${totalRowCount} </span> <span> </span>
			<c:if test="${totalRowCount>25}">
				<a href="javascript:void(0)" class="ibm-forward-link" rel="prev"
				   onclick="return getPreviousPage();"> Previous </a>
				<c:if test="${totalRowCount != endRow}">
					|
					<a href="javascript:void(0)" rel="next"
					   onclick="return getNextPage();">Next </a>
				</c:if>
			</c:if>
		</form>
	</div>
	<div id="displayMsg"></div>
	<!--form:form commandName="Contract.java" action="#" name="" id=""> -->
	<table cellspacing="0" cellpadding="0" border="0" id="contractData"
		   summary="contracts" class="ibm-data-table">
		<thead>
		<tr>
			<th scope="col" width="15%">Account</th>
			<th scope="col" width="15%">RCA/Action#</th>
			<th scope="col" width="5%">Stage</th>
			<th scope="col" width="5%">RCA/Action Due Date</th>
			<th scope="col" width="15%">RCA/Action Owner</th>
			<th scope="col" width="10%">RCA Coordinator</th>
			<th scope="col" width="10%">RCA DPE</th>

		</tr>
		</thead>

		<tbody>
		<c:forEach items="${rcas}" var="rca">
			<tr>

				<td scope="row">${rca.rcaAccountTitle}</td>
				<c:if test="${rca.listingType != null && rca.listingType == 'rca'}">
					<td scope="row" style="width:100px; word-wrap:break-word;"><a	href="editRca.htm?rcaNumber=${rca.rcaOrActionNumber}">${rca.rcaOrActionNumber}</a></td>
				</c:if>
				<c:if
						test="${rca.listingType != null && rca.listingType == 'action'}">
					<td scope="row" style="width:100px; word-wrap:break-word;"><a href="showRcaAction.htm?actionNumber=${rca.rcaOrActionNumber}">${rca.rcaOrActionNumber}</a></td>
				</c:if>
				<td scope="row" style="width:100px; word-wrap:break-word;">${rca.rcaStage}</td>
				<td scope="row" style="width:100px; word-wrap:break-word;">${rca.rcaDueDate}</td>
				<td scope="row" style="width:100px; word-wrap:break-word;">${rca.rcaOwner}</td>
				<td scope="row" style="width:100px; word-wrap:break-word;">${rca.rcaCoordinator}</td>
				<td scope="row" style="width:100px; word-wrap:break-word;">${rca.rcaDpe}</td>


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


