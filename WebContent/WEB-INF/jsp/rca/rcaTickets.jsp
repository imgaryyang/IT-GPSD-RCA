<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:forEach items="${rcaForm.rcaTickets}" var="item" varStatus="itemRow">

	<form:hidden id="rcaForm.rcaTickets[${itemRow.index}].rcaTicketId" path="rcaForm.rcaTickets[${itemRow.index}].rcaTicketId" />

	<table id="incident_details" summary="Contacts" class="ibm-results-table" border="0">
		<thead>
		<tr>

			<th scope="col" align  width="10%" style="font-weight:normal;">**Ticket Number(s):</th>
			<th scope="col" width="12%" ><form:input path="rcaForm.rcaTickets[${itemRow.index}].ticketNum"/></th>
			<th scope="col" width="1%"></th>
			<th scope="col" width="12%" style="font-weight: normal;">Ticket Type:
			</th>
			<th scope="col" width="12%"><form:select
					id="secondaryTicketType" path="rcaForm.rcaTickets[${itemRow.index}].secondaryTicketType">
				<form:option value="Incident">Incident</form:option>
				<form:option value="Problem">Problem</form:option>
				<form:option value="Change">Change</form:option>
			</form:select>
			</th>
			<th scope="col" width="1%"></th>
			<th scope="col" width="10%" style="font-weight:normal;">Severity:</th>
			<th scope="col" width="12%"><form:select id="rcaForm.rcaTickets[${itemRow.index}].severity" path="rcaForm.rcaTickets[${itemRow.index}].severity">
				<form:option value="1" >1</form:option>
				<form:option value="2" >2</form:option>
				<form:option value="3" >3</form:option>
				<form:option value="4" >4</form:option>
				<form:option value="4" >5</form:option>
			</form:select></th>

			<th scope="col" width="12%" style="font-weight: normal;">Associated
				Tool:
			</th>

			<th scope="col" width="12%">
				<form:select id="rcaForm.rcaTickets[${itemRow.index}].associatedTool"
							 path="rcaForm.rcaTickets[${itemRow.index}].associatedTool" onclick="enableSecOtherAssociatedTool('rcaForm.rcaTickets[${itemRow.index}].associatedTool', 'other_associated_tool_${itemRow.index}')">
					<form:option value=""></form:option>
					<form:option value="i service">i service</form:option>
					<form:option value="AT&T Network">AT&T Network</form:option>
					<form:option value="Atlassian">Atlassian</form:option>
					<form:option value="Cirats">Cirats</form:option>
					<form:option value="Clarfy">Clarfy</form:option>
					<form:option value="Codesk Dalian">Codesk Dalian</form:option>
					<form:option value="Codesk Manila">Codesk Manila</form:option>
					<form:option value="EMEA DSR">EMEA DSR</form:option>
					<form:option value="Elixir">Elixir</form:option>
					<form:option value="FiServ">FiServ</form:option>
					<form:option value="GPS Development Lab">GPS Development Lab</form:option>
					<form:option value="Maximo">Maximo</form:option>
					<form:option value="Other">Other</form:option>

				</form:select></th>
			<th>
			</th>
			<th scope="col" width="2%"></th>
		</tr>
		<c:choose>
			<c:when test="${rcaForm.rcaTickets[itemRow.index].associatedTool != null && rcaForm.rcaTickets[itemRow.index].associatedTool == 'Other'}">
				<table id="other_associated_tool_${itemRow.index}" summary="Contacts"
					   class="ibm-results-table" border="0" >
					<thead></thead>
					<tr>
						<th scope="col" align width="20%"
							style="font-weight: normal;"><span class="ibm-required">**</span>
							Other Associated Tool:                                               </th>
						<th scope="col" width="80%"><form:textarea
								id="rcaForm.rcaTickets[${itemRow.index}].otherAssociatedTool"
								path="rcaForm.rcaTickets[${itemRow.index}].otherAssociatedTool" cols="137" rows="3"/></th>

					</tr>

				</table>
			</c:when>
			<c:otherwise>
				<table id="other_associated_tool_${itemRow.index}" summary="Contacts"
					   class="ibm-results-table" border="0" style="display:none;">
					<thead></thead>
					<tr>
						<th scope="col" align width="20%"
							style="font-weight: normal;"><span class="ibm-required">**</span>
							Other Associated Tool:                                               </th>
						<th scope="col" width="80%"><form:textarea
								id="rcaForm.rcaTickets[${itemRow.index}].otherAssociatedTool"
								path="rcaForm.rcaTickets[${itemRow.index}].otherAssociatedTool" cols="137" rows="3"/></th>

					</tr>

				</table>
			</c:otherwise>
		</c:choose>
		<tr>
			<th width="10%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</th>
			<th width="10%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</th>
			<th width="10%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</th>
			<th width="10%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</th>
			<th width="10%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</th>
			<th width="10%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</th>
			<th width="10%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</th>
			<th width="10%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;


			</th>
			<th scope="col" width="4%" >

				<input  align="right" type="button" value="Delete" style="height:28px;  width: 1px;" name="ibm-submit"
						class="ibm-btn-cart-sec" onclick="deleteSecondaryRcaTicket('${basePath}', '${rcaForm.rcaTickets[itemRow.index].rcaTicketId}')"></th>
		</tr>
		</thead>
	</table>
</c:forEach>