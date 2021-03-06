<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    System.out.println("showing editContract.jsp....");
%>
<style type="text/css" media="all">
.bar-green-dark {
	background: #7a3;
	color: #fff;
	padding: .3em .5em;
	font-size: 1.1em;
	font-weight: bold;
}

.bar-green-med-dark {
	background: #9c3;
	color: #fff;
	padding: .3em .5em;
	font-size: 1.1em;
	font-weight: bold;
}

.bar-green-med-dark_cell {
	background: #9c3;
	color: #fff;
	margin: 2px 2px 2px 2px;
	padding: 2px 2px 2px 2px;
	font-size: 1.1em;
	font-weight: bold;
}

.bar-green-med-light {
	background: #bd6;
	color: #000;
	padding: .3em .5em;
	font-size: 1.1em;
	font-weight: bold;
}

.bar-blue-med-dark {
	background: #47b;
	color: #fff;
	padding: .3em .5em;
	font-size: 1.1em;
	font-weight: bold;
}

.bar-blue-med {
	background: #69c;
	color: #fff;
	padding: .3em .5em;
	font-size: 1.1em;
	font-weight: bold;
}

.bar-blue-med-light {
	background: #9be;
	color: #000;
	padding: .3em .5em;
	font-size: 1.1em;
	font-weight: bold;
}

.bar-gray-dark {
	background: #666;
	color: #fff;
	padding: .3em .5em;
	font-size: 1.1em;
	font-weight: bold;
}

.bar-gray-med-dark {
	background: #999;
	color: #fff;
	padding: .3em .5em;
	font-size: 1.1em;
	font-weight: bold;
}

.bar-gray-med-light {
	background: #ccc;
	color: #000;
	padding: .3em .5em;
	font-size: 1.1em;
	font-weight: bold;
}

.bar-gray-light {
	background: #ddd;
	color: #000;
	padding: .3em .5em;
	font-size: 1.1em;
	font-weight: bold;
}

.bar-blue-black {
	background: #003f69;
	color: #fff;
	padding: .3em .5em;
	font-size: 1.1em;
	font-weight: bold;
}

.bar-green-black {
	background: #00a6a0;
	color: #fff;
	padding: .3em .5em;
	font-size: 1.1em;
	font-weight: bold;
}

.lr-mar-10px {
	margin-left: 10px;
	margin-right: 10px
}

.dot-bor-1px {
	border: 2px dotted #CCC
}

.sol-bor-1px {
	border: 1px solid #CCC
}

.contract-contact-info {
	width: 180px
}

.errors {
	color: #ff0000;
	font-style: italic;
}

#general_Information span {
	display: block;
	height: 25px;
}

#general_Information label {
	width: auto;
	width: 250px;
	font-weight: normal;
}

#general_Information tr {
	height: 25px;
}

#bcrs span {
	display: block;
	height: 30px;
}

#bcrs label {
	width: auto;
	width: 550px;
	font-weight: normal;
}

#csst span {
	display: block;
	height: 30px;
}

#csst label {
	width: auto;
	width: 550px;
	font-weight: normal;
}
</style>

<div id="ibm-pcon">
	<!-- CONTENT_BEGIN -->
	<div id="ibm-content">
		<!-- TITLE_BEGIN -->
		<!-- TITLE_END -->
		<!-- CONTENT_BODY -->
		<div id="ibm-content-body">
			<div id="ibm-content-main">
				<div class="ibm-columns">
					<div class="ibm-col-6-6">
						<div id="contractDetailContainer" class="ibm-container">
							<form:form method="post" commandName="contract"
								cssClass="ibm-column-form" name="editForm">
								<div id="pii" style="display: none">
									<p style="display: none">The fields indicated with an
										asterisk (*) are required to complete this transaction; other
										fields are optional. If you do not want to provide us with the
										required information, please use the "Back" button on your
										browser to return to the previous page, or close the window or
										browser session that is displaying this page.</p>
								</div>

								<c:if
									test="${(contract.state==null || contract.state=='' || contract.state=='0' || contract.state=='1')}">
									<c:set var="accessLevel" scope="request"
										value="${sessionAcl.activeAccessLevel}" />
								</c:if>
								<c:if test="${(contract.state == '2' || contract.state=='3')}">
									<c:set var="accessLevel" scope="request"
										value="${sessionAcl.approvedAccessLevel}" />
								</c:if>


								<div id="ibm-leadspace-head" class="ibm-alternate">
									<div align="right">
										<a
											href="https://w3-connections.ibm.com/wikis/home?lang=en_US#/wiki/GPS%20Root%20Cause%20Analysis%20%28RCA%29/page/Introduction"
											target="_blank">Help</a>
									</div>
									<div id="ibm-leadspace-body">
										<ul id="ibm-navigation-trail">
											<li><a href="/GPSDRCA/contracts.htm">GPSDRCA</a></li>
											<li><a href="/GPSDRCA/contracts.htm">Accounts</a></li>
											<li><a
												href="/GPSDRCA/editContract.htm?contractId=${contract.contractId}">Edit
													Contract</a></li>
										</ul>
										<h1 class="ibm-small">
											<form:label path="title" style="width:800px">${contract.contractId} | ${contract.title}</form:label>
											<form:hidden path="title" id="title"></form:hidden>
										</h1>

										<br />
										<form:errors path="*" element="div" cssClass="errors" />
										<br /> <span id="requiredFieldExplaination"> Required
											fields are marked with asterisk(<span class="ibm-required">*</span>)and
											a double asterisk (<span class="ibm-required">**</span>)indicates
											conditionally required fields.
										</span>
									</div>
								</div>

								<div id="body" class="ibm-container-body; sol-bor-1px">
									<br />
									<div id="clientInformationDiv">
										<div class="bar-blue-black">CIO-RCA Client Information</div>
										<br />
										<div class="lr-mar-10px" id="clientInfoDiv">
											<table cellspacing="0" cellpadding="0" border="1"
												width="100%" summary="edit contract"
												class="ibm-results-table">
												<thead>
													<tr>
														<form:hidden path="contractId" id="contractId" />
														<th scope="row">
															<table id="contractData" summary="contracts"
																class="ibm-data-table">
																<thead>
																	<tr>
																		<th scope="col" class="bar-green-black"
																			style="color: #fff">Application/Asset Name</th>
																		<th scope="col" class="bar-green-black"
																			style="color: #fff">Unique BTMT ID</th>
																		<th scope="col" class="bar-green-black"
																			style="color: #fff">Application Alias</th>
																	</tr>
																</thead>

																<tbody>
																	<tr>
																		<th scope="row"><form:input
																				path="applicationName" size="50" /></th>
																		<th scope="row"><form:input path="btmtId"
																				size="25" /></th>
																		<th scope="row"><form:input
																				path="applicationAlias" size="25" /></th>
																	</tr>
																</tbody>

															</table>
														</th>
													</tr>
												</thead>
											</table>
										</div>

									</div>

									<div id="generalInfoDiv" class="lr-mar-10px">
										<div class="bar-green-black" align="center">General
											Information</div>
										<br />
										<div class="lr-mar-10px">
											<table id="general_Information" summary="general Information"
												class="ibm-results-table">
												<thead>
													<tr>
														<th scope="col" width="20%"><label
															for="applicationDesc">Application Description:</label></th>
														<th scope="col"><form:textarea id="applicationDesc"
																path="applicationDesc" cols="100" rows="3" /></th>
													</tr>

												</thead>
											</table>

										</div>
									</div>
									<br />

									<div class="bar-blue-black" align="left">Contract
										Contacts</div>
									<br />
									<div class="lr-mar-10px" id="contractContactsDiv">
										<table id="contract_contacts" summary="Contacts"
											class="ibm-data-table">
											<thead>
												<tr>
													<th scope="col" class="bar-green-black" width="40%"><label
														style="color: #fff">Title</label></th>
													<th scope="col" class="bar-green-black"><label
														style="color: #fff">Name</label></th>
													<th scope="col" class="bar-green-black"><label
														style="color: #fff">Intranet ID</label></th>
													<th scope="col" class="bar-green-black"><label
														style="color: #fff">External Phone</label></th>
												</tr>

												<tr>
													<th scope="row">Application Owner:</th>
													<th scope="row"><label for="AOName"
														style="display: none">AOName</label> <form:input
															id="AOName" path="contractHelper.aoName" size="25"
															cssStyle="top:0em" /></th>
													<th scope="row"><label for="AOemail"
														style="display: none">AOemail</label><span
														class="ibm-required">*</span> <form:input id="AOEmail"
															path="contractHelper.aoEmail" size="25"
															cssStyle="top:0em" /></th>
													<th scope="row"><label for="AOPhone"
														style="display: none">AOPhone</label> <form:input
															id="AOPhone" path="contractHelper.aoPhone" size="25"
															cssStyle="top:0em" /></th>
												</tr>

												<tr>
													<th scope="row">Application Focal Point:</th>
													<th scope="row"><label for="contractHelper.afpName"
														style="display: none">contractHelper.afpName</label> <form:input
															path="contractHelper.afpName" size="25"
															cssStyle="top:0em" /></th>
													<th scope="row"><label for="AFPemail"
														style="display: none">AFPemail</label><span
														class="ibm-required">*</span> <form:input id="afpEmail"
															path="contractHelper.afpEmail" size="25"
															cssStyle="top:0em" /></th>
													<th scope="row"><label for="contractHelper.afpPhone"
														style="display: none">contractHelper.afpPhone</label> <form:input
															path="contractHelper.afpPhone" size="25"
															cssStyle="top:0em" /></th>
												</tr>
												<tr>
													<th scope="row">Business Process Owner:</th>
													<th scope="row"><label for="contractHelper.bpoName"
														style="display: none">bpoName</label> <form:input
															path="contractHelper.bpoName" size="25"
															cssStyle="top:0em" /></th>
													<th scope="row"><label for="bpoemail"
														style="display: none">bpoemail</label><span
														class="ibm-required">*</span> <form:input id="bpoemail"
															path="contractHelper.bpoEmail" size="25"
															cssStyle="top:0em" /></th>
													<th scope="row"><label for="contractHelper.bpoPhone"
														style="display: none">bpoPhone</label> <form:input
															path="contractHelper.bpoPhone" size="25"
															cssStyle="top:0em" /></th>
												</tr>

												<tr>
													<th scope="row">Service Delivery Manager :</th>
													<th scope="row"><label for="contractHelper.sdmName"
														style="display: none">sdmName</label> <form:input
															path="contractHelper.sdmName" size="25"
															cssStyle="top:0em" /></th>
													<th scope="row" style="padding-left: 10px"><label
														for="contractHelper.sdmEmail" style="display: none">sdmEmail</label>
														<form:input path="contractHelper.sdmEmail" size="25"
															cssStyle="top:0em" /></th>
													<th scope="row"><label for="contractHelper.sdmPhone"
														style="display: none">sdpePhone</label> <form:input
															path="contractHelper.sdmPhone" size="25"
															cssStyle="top:0em" /></th>
												</tr>

												<tr>
													<th scope="row">Delivery Project Manager (DPE):</th>
													<th scope="row"><label for="contractHelper.dpeName"
														style="display: none">dpeName</label> <form:input
															path="contractHelper.dpeName" size="25"
															cssStyle="top:0em" /></th>
													<th scope="row"><label for="DPEemail"
														style="display: none">DPEemail</label><span
														class="ibm-required">*</span> <form:input id="DPEemail"
															path="contractHelper.dpeEmail" size="25"
															cssStyle="top:0em" /></th>
													<th scope="row"><label for="contractHelper.dpePhone"
														style="display: none">dpePhone</label> <form:input
															path="contractHelper.dpePhone" size="25"
															cssStyle="top:0em" /></th>
												</tr>

												<tr>
													<th scope="row">Primary Customer :</th>
													<th scope="row"><label for="contractHelper.pcName"
														style="display: none">pcName</label> <form:input
															path="contractHelper.pcName" size="25" cssStyle="top:0em" /></th>
													<th scope="row" style="padding-left: 10px"><label
														for="contractHelper.pcEmail" style="display: none">pcEmail</label>
														<form:input path="contractHelper.pcEmail" size="25"
															cssStyle="top:0em" /></th>
													<th scope="row"><label for="contractHelper.pcPhone"
														style="display: none">pcPhone</label> <form:input
															path="contractHelper.pcPhone" size="25"
															cssStyle="top:0em" /></th>
												</tr>


												<tr>
													<th scope="row">RCA Coordinator :</th>
													<th scope="row"><label
														for="contractHelper.priRcacName" style="display: none">pcName</label>
														<form:input path="contractHelper.priRcacName" size="25"
															cssStyle="top:0em" /></th>
													<th scope="row" style="padding-left: 10px"><label
														for="contractHelper.priRcacEmail" style="display: none">pcEmail</label>
														<form:input path="contractHelper.priRcacEmail" size="25"
															cssStyle="top:0em" /></th>
													<th scope="row"><label
														for="contractHelper.priRcacPhone" style="display: none">pcPhone</label>
														<form:input path="contractHelper.priRcacPhone" size="25"
															cssStyle="top:0em" /></th>
												</tr>

												<div id="rca" style="display: none; margin-left: 50px;">
													<table class="ibm-results-table" summary="addRCA">
														<thead>
															<tr>
																<th scope="col" style="font-weight: normal;"
																	align="left"><input type="button"
																	name="AddRCACoordinator" class="ibm-btn-pri"
																	value="Add RCA Coordinator"
																	onclick="addRCACoordinator()" /></th>
															</tr>
															<tr>
																<td colspan="2">&nbsp;
																	<table class="ibm-results-table"
																		id="rca_coordinator_table" summary="inciData">
																		<thead>
																			<tr>
																				<td><input type="hidden" id="rowCount"
																					name="rowCount" value="0" /></td>
																			</tr>

																			<c:forEach
																				items="${contract.contractHelper.rcaCoordinators}"
																				var="item" varStatus="itemRow">
																				<tr id="tr_${item.rcaCoordinatorId}" name="tr_${item.rcaCoordinatorId}">

																					<th scope="row" width="25%">RCA Coordinator :</th>
																					<th id="th_btn_${item.rcaCoordinatorId}" scope="row" width="10%"><input
																						type="button" value="Delete" class="ibm-btn-cancel-pri" style="height:26px; width:0.2px; font-weight:normal;"
																						id="rca_delete_${item.rcaCoordinatorId}"
																						name="rca_delete_${item.rcaCoordinatorId}"
																						onclick="deleteRCACoordinator('${item.rcaCoordinatorId}', 'rca_delete_${item.rcaCoordinatorId}');">
																					</th>
																					<th width ="5%"></th>
																					<th scope="row" width="20%"><form:input
																							id="rca_name"
																							path="contractHelper.rcaCoordinators[${itemRow.index}].coordinator.coordinatorName"
																							size="25" cssStyle="top:0em" /></th>
																					<th scope="row" width="20%"><form:input
																							id="rca_email"
																							path="contractHelper.rcaCoordinators[${itemRow.index}].coordinator.intranetId"
																							size="25" cssStyle="top:0em" /></th>
																					<th scope="row" width="20%"><form:input
																							id="rca_phone"
																							path="contractHelper.rcaCoordinators[${itemRow.index}].coordinator.phone"
																							size="25" cssStyle="top:0em" /></th>
																					<form:hidden
																						path="contractHelper.rcaCoordinators[${itemRow.index}].coordinator.coordinatorId" />
																					<form:hidden
																						id="contractHelper.rcaCoordinators[${itemRow.index}].rcaCoordinatorId"
																						path="contractHelper.rcaCoordinators[${itemRow.index}].rcaCoordinatorId" />
																				</tr>
																			</c:forEach>
																		</thead>
																	</table>
																</td>
															</tr>
														</thead>
													</table>
												</div>

											</thead>
										</table>

									</div>
									<br /> <br />



									<c:if test="${accessLevel >= 16}">
										<div class="bar-blue-black" align="left">RCA Settings</div>
										<br />
										<div class="lr-mar-10px">
											<table id="contract_contacts" summary="Contacts"
												class="ibm-results-table" border="0">
												<thead>

													<tr>
														<th scope="row" style="font-weight: normal">*Number
															of target business days to complete RCA</th>
														<th scope="row"><form:input id="rcaCompletionTargetBusinessDays"
																path="contractHelper.emailNotificationSetting.rcaCompletionTargetBusinessDays" size="6" /></th>
													</tr>
													<tr>
														<th scope="row" style="font-weight: normal">*Selectable
															RCA routing matrix enabled</th>
														<th scope="row"><form:select
																id="rcaRoutingMatrixEnabled"
																path="contractHelper.emailNotificationSetting.rcaRoutingMatrixEnabled">
																<form:option value="Y">True</form:option>
																<form:option value="N">False</form:option>
															</form:select></th>
													</tr>
													<tr>
														<th scope="row" style="font-weight: normal">*RCA
															workflow notification enable (notifications are sent
															immediately on action performed.)</th>
														<th scope="row"><form:select
																id="rcaWorkflowNotificationEnabled" 
																path="contractHelper.emailNotificationSetting.rcaWorkflowNotificationEnabled">
																<form:option value="Y">True</form:option>
																<form:option value="N">False</form:option>
															</form:select></th>
													</tr>

												</thead>
											</table>

											<table id="contract_contacts" summary="Contacts"
												class="ibm-results-table" border="0">
												<thead>
													<tr>
														<th width="2%">
														<th>
														<th scope="row" style="font-weight: normal" width="35%">*New
															RCA notification to RCA Coordinator</th>
														<th scope="row" width="15%"><span
															class="ibm-input-group ibm-radio-group"> <form:radiobutton
																	id="newRcaNotificationToCoordinatorY"
																	path="contractHelper.emailNotificationSetting.newRcaNotificationToCoordinator" value="Y" /> <label
																for="newRcaNotificationToCoordinatorY">Yes</label> <form:radiobutton
																	id="newRcaNotificationToCoordinatorN" 
																	path="contractHelper.emailNotificationSetting.newRcaNotificationToCoordinator" value="N" /> <label
																for="newRcaNotificationToCoordinatorN">No</label>
														</span></th>
														<th scope="row" width="25%"></th>
														<th scope="row" width="28%"></th>
													</tr>
												</thead>
											</table>
											<table id="contract_contacts" summary="Contacts"
												class="ibm-results-table" border="0">
												<thead>
													<tr>
														<th width="2%">
														<th>
														<th scope="row" style="font-weight: normal" width="35%">*Notification
															to the RCA owner on RCA assignment</th>
														<th scope="row" width="15%"><span
															class="ibm-input-group ibm-radio-group"> <form:radiobutton
																	id="rcaAssignmentNotificationToOwnerY" 
																	path="contractHelper.emailNotificationSetting.rcaAssignmentNotificationToOwner" value="Y" /> <label
																for="rcaAssignmentNotificationToOwnerY">Yes</label> <form:radiobutton
																	id="rcaAssignmentNotificationToOwnerN"
																	path="contractHelper.emailNotificationSetting.rcaAssignmentNotificationToOwner" value="N" /> <label
																for="rcaAssignmentNotificationToOwnerN">No</label>
														</span></th>
														<th scope="row" width="30%"></th>
														<th scope="row" width="23%"></th>
													</tr>
													<tr>
														<th width="2%">
														<th>
														<th scope="row" style="font-weight: normal" width="35%">*Reminder
															on RCA not accepted by owner</th>
														<th scope="row" width="15%"><span
															class="ibm-input-group ibm-radio-group"> <form:radiobutton
																	id="rcaNotAcceptedReminderForOwnerY"
																	path="contractHelper.emailNotificationSetting.rcaNotAcceptedReminderForOwner" value="Y" /> <label
																for="rcaNotAcceptedReminderForOwnerY">Yes</label> <form:radiobutton
																	id="rcaNotAcceptedReminderForOwnerN" 
																	path="contractHelper.emailNotificationSetting.rcaNotAcceptedReminderForOwner" value="N" /> <label
																for="rcaNotAcceptedReminderForOwnerN">No</label>
														</span></th>
														<th scope="row" width="30%" style="font-weight: normal">No.
															of days for reminder after assignment</th>
														<td width="23%" align="center"><form:input
																id="rcaNotAcceptedReminderForOwnerDays" path="contractHelper.emailNotificationSetting.rcaNotAcceptedReminderForOwnerDays" size="6" /></td>
													</tr>
													<tr>
														<th width="2%">
														<th>
														<th scope="row" style="font-weight: normal" width="35%">*Reminder
															on RCA not submitted by owner</th>
														<th scope="row" width="15%"><span
															class="ibm-input-group ibm-radio-group"> <form:radiobutton
																	id="rcaNotSubmittedReminderForOwnerY" 
																	path="contractHelper.emailNotificationSetting.rcaNotSubmittedReminderForOwner" value="Y" /> <label
																for="rcaNotSubmittedReminderForOwnerY">Yes</label> <form:radiobutton
																	id="rcaNotSubmittedReminderForOwnerN" 
																	path="contractHelper.emailNotificationSetting.rcaNotSubmittedReminderForOwner" value="N" /> <label
																for="rcaNotSubmittedReminderForOwnerN">No</label>
														</span></th>
														<th scope="row" width="30%" style="font-weight: normal">No.
															of days for reminder after assignment</th>
														<td width="23%" align="center"><form:input
																id="rcaNotSubmittedReminderForOwnerDays" 
																path="contractHelper.emailNotificationSetting.rcaNotSubmittedReminderForOwnerDays" size="6" /></td>
													</tr>
													<tr>
														<th width="2%">
														<th>
														<th scope="row" style="font-weight: normal" width="35%">*Notification
															to the approver on RCA awaiting approval</th>
														<th scope="row" width="15%"><span
															class="ibm-input-group ibm-radio-group"> <form:radiobutton
																	id="rcaAwaitingApprovalNotificationY" 
																	path="contractHelper.emailNotificationSetting.rcaAwaitingApprovalNotification" value="Y" /> <label
																for="rcaApprovedNotificationY">Yes</label> <form:radiobutton
																	id="rcaAwaitingApprovalNotificationN" 
																	path="contractHelper.emailNotificationSetting.rcaAwaitingApprovalNotification" value="N" /> <label
																for="rcaApprovedNotificationN">No</label>
														</span></th>
														<th scope="row" width="30%" style="font-weight: normal"></th>
														<td width="23%" align="center"></td>
													</tr>
													<tr>
														<th width="2%">
														<th>
														<th scope="row" style="font-weight: normal" width="35%">*Notification
															on RCA Approved</th>
														<th scope="row" width="15%"><span
															class="ibm-input-group ibm-radio-group"> <form:radiobutton
																	id="rcaApprovedNotificationY" 
																	path="contractHelper.emailNotificationSetting.rcaApprovedNotification" value="Y" /> <label
																for="rcaApprovedNotificationY">Yes</label> <form:radiobutton
																	id="rcaApprovedNotificationN" 
																	path="contractHelper.emailNotificationSetting.rcaApprovedNotification" value="N" /> <label
																for="rcaApprovedNotificationN">No</label>
														</span></th>
														<th scope="row" width="30%" style="font-weight: normal"></th>
														<td width="23%" align="center"></td>
													</tr>
													<tr>
														<th width="2%">
														<th>
														<th scope="row" style="font-weight: normal" width="35%">*Notification
															on RCA Rejected</th>
														<th scope="row" width="15%"><span
															class="ibm-input-group ibm-radio-group"> <form:radiobutton
																	id="rcaRejectedNotificationY" 
																	path="contractHelper.emailNotificationSetting.rcaRejectedNotification" value="Y" /> <label
																for="rcaRejectedNotificationY">Yes</label> <form:radiobutton
																	id="rcaRejectedNotificationN" 
																	path="contractHelper.emailNotificationSetting.rcaRejectedNotification" value="N" /> <label
																for="rcaRejectedNotificationN">No</label>
														</span></th>
														<th scope="row" width="30%" style="font-weight: normal"></th>
														<td width="23%" align="center"></td>
													</tr>
													<tr>
														<th width="2%">
														<th>
														<th scope="row" style="font-weight: normal" width="35%">*Reminder
															on RCA not Approved</th>
														<th scope="row" width="15%"><span
															class="ibm-input-group ibm-radio-group"> <form:radiobutton
																	id="rcaNotApprovedReminderY" 
																	path="contractHelper.emailNotificationSetting.rcaNotApprovedReminder" value="Y" /> <label
																for="rcaNotApprovedReminderY">Yes</label> <form:radiobutton
																	id="rcaNotApprovedReminderN" 
																	path="contractHelper.emailNotificationSetting.rcaNotApprovedReminder" value="N" /> <label
																for="rcaNotApprovedReminderN">No</label>
														</span></th>
														<th scope="row" width="30%" style="font-weight: normal">No.
															of days after RCA create date &nbsp; &nbsp;&nbsp;<form:input
																id="rcaNotApprovedReminderDaysAfterCreation" 
																path="contractHelper.emailNotificationSetting.rcaNotApprovedReminderDaysAfterCreation" 
																size="5" />
														</th>
														<td width="23%">and &nbsp;<form:input
																id="rcaNotApprovedReminderDuration" 
																path="contractHelper.emailNotificationSetting.rcaNotApprovedReminderDuration" size="5" />&nbsp;duration
														</td>
													</tr>
													<tr>
														<th width="2%">
														<th>
														<th scope="row" style="font-weight: normal" width="35%">*Notification
															on new Action item</th>
														<th scope="row" width="15%"><span
															class="ibm-input-group ibm-radio-group"> <form:radiobutton
																	id="NewActionItemNotificationY" 
																	path="contractHelper.emailNotificationSetting.NewActionItemNotification" value="Y" /> <label
																for="NewActionItemNotificationY">Yes</label> <form:radiobutton
																	id="NewActionItemNotificationN" 
																	path="contractHelper.emailNotificationSetting.NewActionItemNotification" value="N" /> <label
																for="NewActionItemNotificationN">No</label>
														</span></th>
														<th scope="row" width="30%"></th>
														<th scope="row" width="23%"></th>
													</tr>
													<tr>
														<th width="2%">
														<th>
														<th scope="row" style="font-weight: normal" width="35%">*Reminder
															on Action item not Closed</th>
														<th scope="row" width="15%"><span
															class="ibm-input-group ibm-radio-group"> <form:radiobutton
																	id="actionNotClosedReminderY" 
																	path="contractHelper.emailNotificationSetting.actionNotClosedReminder" value="Y" /> <label
																for="actionNotClosedReminderY">Yes</label> <form:radiobutton
																	id="actionNotClosedReminderN" 
																	path="contractHelper.emailNotificationSetting.actionNotClosedReminder" value="N" /> <label
																for="actionNotClosedReminderN">No</label>
														</span></th>
														<th scope="row" width="30%" style="font-weight: normal">No.
															of days prior to target date &nbsp; &nbsp;&nbsp;<form:input
																id="actionNotClosedReminderDaysBeforeTarget" 
																path="contractHelper.emailNotificationSetting.actionNotClosedReminderDaysBeforeTarget" size="5" />
														</th>
														<td width="23%">and &nbsp;<form:input
																id="actionNotClosedReminderDuration" 
																path="contractHelper.emailNotificationSetting.actionNotClosedReminderDuration" size="5" />&nbsp;duration
														</td>
													</tr>
													<tr>
														<th width="2%">
														<th>
														<th scope="row" style="font-weight: normal" width="35%">*Notification
															on Closed Action item</th>
														<th scope="row" width="15%"><span
															class="ibm-input-group ibm-radio-group"> <form:radiobutton
																	id="actionItemClosedNotificationY"
																	path="contractHelper.emailNotificationSetting.actionItemClosedNotification" value="Y" /> <label
																for="actionItemClosedNotificationY">Yes</label> <form:radiobutton
																	id="actionItemClosedNotificationN"
																	path="contractHelper.emailNotificationSetting.actionItemClosedNotification" value="N" /> <label
																for="actionItemClosedNotificationN">No</label>
														</span></th>
														<th scope="row" width="30%"></th>
														<th scope="row" width="23%"></th>
													</tr>
													<tr>
														<th width="2%">
														<th>
														<th scope="row" style="font-weight: normal" width="35%">*Reminder
															on RCA Not Closed</th>
														<th scope="row" width="15%"><span
															class="ibm-input-group ibm-radio-group"> <form:radiobutton
																	id="rcaNotClosedReminderY" 
																	path="contractHelper.emailNotificationSetting.rcaNotClosedReminder" value="Y" /> <label
																for="rcaNotClosedReminderY">Yes</label> <form:radiobutton
																	id="rcaNotClosedReminderN" 
																	path="contractHelper.emailNotificationSetting.rcaNotClosedReminder" value="N" /> <label
																for="rcaNotClosedReminderN">No</label>
														</span></th>
														<th scope="row" width="30%" style="font-weight: normal">No.
															of days after RCA create date &nbsp; &nbsp;&nbsp;<form:input
																id="rcaNotClosedReminderDaysAfterCreation" 
																path="contractHelper.emailNotificationSetting.rcaNotClosedReminderDaysAfterCreation" size="5" />
														</th>
														<td width="23%">and &nbsp;<form:input
																id="rcaNotClosedReminderDuration" 
																path="contractHelper.emailNotificationSetting.rcaNotClosedReminderDuration" size="5" />&nbsp;duration
														</td>
													</tr>
													<tr>
														<th width="2%">
														<th>
														<th scope="row" style="font-weight: normal" width="35%">*Notification
															on RCA Closed</th>
														<th scope="row" width="15%"><span
															class="ibm-input-group ibm-radio-group"> <form:radiobutton
																	id="rcaClosedNotificationY" 
																	path="contractHelper.emailNotificationSetting.rcaClosedNotification" value="Y" /> <label
																for="rcaClosedNotificationY">Yes</label> <form:radiobutton
																	id="rcaClosedNotificationN" 
																	path="contractHelper.emailNotificationSetting.rcaClosedNotification" value="N" /> <label
																for="rcaClosedNotificationN">No</label>
														</span></th>
														<th scope="row" width="30%"></th>
														<th scope="row" width="23%"></th>
													</tr>
													<tr>
														<th width="2%">
														<th>
														<th scope="row" style="font-weight: normal" width="35%">*Notification
															on RCA Cancelled</th>
														<th scope="row" width="15%"><span
															class="ibm-input-group ibm-radio-group"> <form:radiobutton
																	id="rcaCancelledNotificationY" 
																	path="contractHelper.emailNotificationSetting.rcaCancelledNotification" value="Y" /> <label
																for="rcaCancelledNotificationY">Yes</label> <form:radiobutton
																	id="rcaCancelledNotificationN" 
																	path="contractHelper.emailNotificationSetting.rcaCancelledNotification" value="N" /> <label
																for="rcaCancelledNotificationN">No</label>
														</span></th>
														<th scope="row" width="30%"></th>
														<th scope="row" width="23%"></th>
													</tr>
													<tr>
														<th width="2%">
														<th>
														<th scope="row" style="font-weight: normal" width="35%">*Notification
															on Cancelled Action item</th>
														<th scope="row" width="15%"><span
															class="ibm-input-group ibm-radio-group"> <form:radiobutton
																	id="actionItemCancelledNotificationY" 
																	path="contractHelper.emailNotificationSetting.actionItemCancelledNotification" value="Y" /> <label
																for="actionItemCancelledNotificationY">Yes</label> <form:radiobutton
																	id="actionItemCancelledNotificationN"
																	path="contractHelper.emailNotificationSetting.actionItemCancelledNotification" value="N" /> <label
																for="actionItemCancelledNotificationN">No</label>
														</span></th>
														<th scope="row" width="30%"></th>
														<th scope="row" width="23%"></th>
													</tr>

												</thead>
											</table>


										</div>
									</c:if>




									<div class="bar-blue-black">Form Actions</div>

									<br />
									<div class="lr-mar-10px">
										<input type="submit" value="Back" tabindex="5"
											name="ibm-submit" onclick="return setFormAction('back');"
											class="ibm-btn-cart-sec">&nbsp;


										<c:if test="${accessLevel >= 2}">
											<input type="submit" value="Save" tabindex="5"
												name="ibm-submit" onclick="return setFormAction('save');"
												class="ibm-btn-cart-sec">&nbsp;
                                         </c:if>
										<c:if test="${contract.state == '0' }">
											<c:if test="${accessLevel >= 4}">
												<input type="submit" value="Submit" name="ibm-submit"
													tabindex="6" id="ibm_submit_action"
													onclick="return setFormAction('Submit');"
													class="ibm-btn-cart-sec">
											</c:if>
										</c:if>
										<c:if test="${contract.state == '2' }">
											<c:if test="${accessLevel >= 8}">
												<input type="submit" value="Reject" name="ibm-submit"
													tabindex="6" id="ibm_submit_action"
													onclick="return setFormAction('reject');"
													class="ibm-btn-cart-sec">
											</c:if>
										</c:if>
										<c:if test="${accessLevel >= 4 && contract.state != '2'}">
											<input type="submit" value="Reset" name="ibm-submit"
												tabindex="6" id="ibm-submit" onclick="resetContract();"
												class="ibm-btn-cart-sec">
										</c:if>
									</div>
									<form:hidden path="contractHelper.formAction" id="formAction" />
									<form:hidden path="contractHelper.accessLevel" id="accessLevel" />




								</div>

							</form:form>
						</div>


					</div>
				</div>
			</div>
		</div>
	</div>

</div>


<script type="text/javascript" src="js/contracts-1.0.js"> // </script>
<script src="js/jquery.js"></script>
<script> 

function addCoordinator(){
  console.log("addCoordinator()........");
        var URL = "<%=basePath%>addRcaCoordinator.htm";
         var targetNode = dojo.byId("rca_coordinator_table");
        var contractId = dojo.byId("contractId").value;

        dojo.xhrGet({
            // The URL of the request
            url: URL,
            handleAs: "text",
            preventCache: true,
            content: {
                contract_id: contractId
            },
            // The success handler
            load: function (response) {
                if (response != null) {
                    //loadRCAContributingCauses();
					
                    dojo.place(response, targetNode, "only");
                    return true;
                }
                else {
                    return false;
                }
            },
            // The error handler
            error: function () {
                return false;
            },
            // The complete handler
            handle: function () {
                // hasBeenSent = true;
            }
        });
}



function addRCACoordinator() {
console.log('calling addRCACoordinator...')
 var URL = "<%=basePath%>addRcaCoordinator.htm";
 var contractId = dojo.byId("contractId").value;
 
 dojo.xhrGet({
            // The URL of the request
            url: URL,
            handleAs: "text",
            preventCache: true,
            content: {
                contract_id: contractId
            },
            // The success handler
            load: function (response) {
                if (response != null) {
				 console.log('response: '+response);
				 var table = document.getElementById('rca_coordinator_table');

 //var rowCount = dojo.byId("rowCount").value;
 //console.log('tableRows: '  + document.getElementById("rca_coordinator_table").rows.length);
 //if(rowCount == '0'){
 //var rowcount = table.rows.length;
 //}
 var rowCount = table.rows.length;
 console.log('rowcount : '+ rowCount);
 
  var row = table.insertRow(rowCount);
  console.log('row inserted');
  row.id= "tr_"+response;

  var cell0 = row.insertCell(0)
  var element0 = document.createElement("Label");
  //element0.setAttribute('id','td_rca');
  element0.innerHTML = "RCA Coordinator: ";
  cell0.appendChild(element0);
  var td0 = element0.parentNode;
  td0.setAttribute('width', '25%');
  
  
  var cell1 = row.insertCell(1)
  var element1 = document.createElement("input");
  element1.type = "button";
   element1.id= "rca_delete_"+response;
  element1.name= "rca_delete_"+response;
  element1.value = "Delete";
  element1.className = "ibm-btn-cancel-pri";
  element1.style["height"] = "26px";
  element1.style["width"] = "0.2px";
  element1.style["font-weight"] = "normal";
  element1.setAttribute("onclick", "deleteRCACoordinator("+response+","+element1.name+")");
  cell1.appendChild(element1);
  var td1 = element1.parentNode;
  td1.setAttribute('width', '10%');
  
  var cell5 = row.insertCell(2)
  var element5 = document.createElement("Label");
  //element0.setAttribute('id','td_rca');
  element5.innerHTML = "";
  cell5.appendChild(element5);
  var td5 = element5.parentNode;
  td5.setAttribute('width', '5%');
  
   

  var cell2 = row.insertCell(3);
  var element2 = document.createElement("input");
  element2.type = "text";
  element2.name= "rca_name_"+rowCount;
  element2.id = "rca_name_"+rowCount;
  element2.size = "25";
  cell2.appendChild(element2);
  var td2 = element2.parentNode;
  td2.setAttribute('width', '20%');
  

  var cell3 = row.insertCell(4);
  var element3 = document.createElement("input");
  element3.type = "text";
  element3.name= "rca_email_"+rowCount;
  element3.id= "rca_email_"+rowCount;
  element3.size = "25";
  cell3.appendChild(element3);
  var td3 = element3.parentNode;
  td3.setAttribute('width', '20%');

  var cell4 = row.insertCell(5);
  var element4 = document.createElement("input");
  element4.type = "text";
  element4.name= "rca_phone_"+rowCount;
  element4.id= "rca_phone_"+rowCount;
  element4.size = "25";
  cell4.appendChild(element4);
  var td4 = element4.parentNode;
  td4.setAttribute('width', '20%');
  

  var input = document.createElement("input");
  input.setAttribute("type", "hidden");
  input.setAttribute("name", "rca_coordinator_id_"+rowCount);
  input.setAttribute("id","rca_coordinator_id_"+rowCount);
  input.setAttribute("value", response);
  document.getElementById("tr_"+response).appendChild(input);

console.log('rowcount updated: '+ rowCount);

 // update row count
 var rowCountElement = document.getElementById("rowCount");
 rowCountElement.setAttribute('value', rowCount);
			  
                    return true;
                }
                else {
                    return false;
                }
            },
            // The error handler
            error: function () {
                return false;
            },
            // The complete handler
            handle: function () {
                // hasBeenSent = true;
            }
        });
}


function deleteRCACoordinator(rcaId, r){
 console.log("deleteRCACoordinator()........");
        var URL = "<%=basePath%>deleteRcaCoordinator.htm";

		dojo.xhrGet({
			// The URL of the request
			url : URL,
			handleAs : "text",
			preventCache : true,
			content : {
				rca_coordinator_id : rcaId
			},
			// The success handler
			load : function(response) {
				if (response != null && response == "SUCCESS") {
					deleteRcaCoordinatorRow(r);
					return true;
				} else {
					return false;
				}
			},
			// The error handler
			error : function() {
				return false;
			},
			// The complete handler
			handle : function() {
				// hasBeenSent = true;
			}
		});
	}

function deleteRcaCoordinatorRow(r) {
		console.log("deleteRcaCoordinatorRow()...");
		var button = dojo.byId(r);
		console.log(button.id);
		var row = button.parentNode.parentNode;
		var index = row.rowIndex;
		console.log('index: '+index);
		var table = document.getElementById('rca_coordinator_table');
		console.log(table.id);
		table.deleteRow(index);
		
}

	function enforceMaxLength(obj, length) {
		var maxLength = length;
		if (obj.value.length > maxLength) {
			obj.value = obj.value.substring(0, maxLength);
		}
	}
</script>

<script type="text/javascript">
    checkAccessLevel();
</script>