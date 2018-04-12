<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<table class="ibm-results-table" id="rca_coordinator_table"
																		summary="inciData">
																		<thead>
																			<tr>
																				<td><input type="hidden" id="rowCount"
																					name="rowCount" value="0" /></td>
																			</tr>

																			<c:forEach
																				items="${contract.contractHelper.rcaCoordinators}"
																				var="item" varStatus="itemRow">
																				<tr>
																					<th scope="row" width="30%">RCA Coordinator :</th>
																					<th scope="row" width="10%">
																						<!--  <input type="button" value="Delete" tabindex="5" name="rca_delete"  onclick="return setFormAction('back');"> -->
																					</th>
																					<th scope="row" width="20%"><form:input
																							id="rca_name_${itemRow.index}"
																							path="contractHelper.rcaCoordinators[${itemRow.index}].coordinator.coordinatorName"
																							size="25" cssStyle="top:0em" /></th>
																					<th scope="row" width="20%"><form:input
																							id="rca_email_${itemRow.index}"
																							path="contractHelper.rcaCoordinators[${itemRow.index}].coordinator.intranetId"
																							size="25" cssStyle="top:0em" /></th>
																					<th scope="row" width="20%"><form:input
																							id="rca_phone_${itemRow.index}"
																							path="contractHelper.rcaCoordinators[${itemRow.index}].coordinator.phone"
																							size="25" cssStyle="top:0em" /></th>
																					<form:hidden
																						path="contractHelper.rcaCoordinators[${itemRow.index}].coordinator.coordinatorId" />
																					<form:hidden
																						path="contractHelper.rcaCoordinators[${itemRow.index}].rcaCoordinatorId" />
																				</tr>
																			</c:forEach>
																		</thead>
																	</table>