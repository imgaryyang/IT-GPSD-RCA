<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<c:set var="isAdmin" scope="request" value="${userSession.isAdmin}"/>
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

	.bar-blue-dark {
		background: #05386b;
		color: #fff;
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
		background: #001A2F;
		color: #fff;
		padding: .3em .5em;
		font-size: 1.1em;
		font-weight: bold;
	}

	.bar-green-black {
		background: #0F7D62;
		color: #fff;
		padding: .3em .5em;
		font-size: 1.1em;
		font-weight: bold;
	}

	.lr-mar-5px {
		margin-left: 5px;
		margin-right: 5px
	}

	.r-mar-5px {
		margin-right: 5px
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

	.lr-mar-10px {
		margin-top: 7px;
		margin-left: 10px;
		margin-right: 10px
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

<!-- <script src="js/CalendarPopup.js"   type="text/javascript"></script> -->

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
							<div id="main-content-btn" align="right">
								<a
										href="https://w3-connections.ibm.com/wikis/home?lang=en-us#!/wiki/GPS%20-%20BPDCHIP/page/Get%20Access%20on%20BPDCHIP%20Application">Help</a>
							</div>
							<form:form method="post" commandName="contractRequest"
									   cssClass="ibm-column-form" name="accessRequest">
								<form:hidden path="requestScope" id="_requestScope" />

								<div id="ibm-leadspace-head" class="ibm-alternate">
									<div id="ibm-leadspace-body">
										<ul id="ibm-navigation-trail">
											<li><a href="/GPSDRCA/contracts.htm">GPSDRCA</a></li>
											<li><a href="/GPSDRCA/support.htm">Support</a></li>
											<li><a href="#">RCA Access Request</a></li>
										</ul>

										<br />
										<br />
										<form:errors path="*" element="div" cssClass="errors" />
										<br /> <span id="requiredFieldExplaination">&nbsp;Required
											fields are marked with asterisk(<span class="ibm-required">*</span>)and
											must be completed.
										</span>
									</div>
								</div>
								<div id="body" class="ibm-container-body; sol-bor-1px">
									<!--	<div id="clientInformationDiv">
											<div class="bar-blue-black">Request Type</div>
										</div>
										<table>
											<tr>
												<td>
													<div style="height: 30px;">
														<span class="ibm-input-group ibm-radio-group">
															<input type="radio" id="CON" name="q" onclick="setRequestScope('contract level'); cont('contract'); hide('group'); hide('access');"><label>CONTRACT level ACLs</label></input>
															<input type="radio" id="level" name="q" onclick="setRequestScope('group level'); group('group'); hide('access'); hide('contract');"><label>GROUP level ACLs</label></input>
															<input type="radio" id="Remove" name="q" onclick="setRequestScope('remove user access'); access('access'); hide('contract'); hide('group');"><label>Remove BPDCHIP User Access</label></input>
														</span>
													</div>
												</td>
											</tr>
										</table> -->
									<table class="lr-mar-10px" id="contract">
										<tr>
											<td>
												<div class="bar-blue-black">Account Level Access
													Request</div> <br>
											</td>
										</tr>

										<tr>
											<td>
												<div>
													<div class="bar-blue-dark">Select Account Name</div>

													<table class="lr-mar-10px">
														<tr>
															<td width="39.5%">*Account Name:</td>
															<td width="60.5%" style="padding-bottom: 5px"><form:select
																	path="contractName"
																	cssStyle="background: white; width:385px;"
																	onchange="reloadPage();" id="contract_id">
																<form:option value="" selected="selected">Please select the Account</form:option>
																<form:options items="${referenceData.listContracts}" itemValue="title" itemLabel="title" />
																<form:option value="All" label="--All Contracts--" />
															</form:select></td>
														</tr>
													</table>

													<div class="bar-blue-dark">Account Details</div>
													<table class="lr-mar-10px">
														<tr>
															<td width="40%">Account Name:</td>
															<td width="60%"><input type="text"
																				   value="${contractRequest.contractName}" size="66"
																				   readonly="true" /></td>
														</tr>

														<tr>
															<td>GPSDRCA Account Id:</td>
															<td style="padding-top: 5px; padding-bottom: 5px"><input
																	type="text" value="${contract.contractId}" size="66"
																	readonly="true" /></td>
														</tr>
														<tr>
															<td>* Users Intranet Ids:</td>
															<td style="padding-bottom: 5px"><form:input
																	path="profileEmail" size="66" /></td>
														</tr>
													</table>

													<div class="bar-blue-dark">Role Access</div>


													<table class="lr-mar-10px" border="0">
														<tr>
															<td width="30%">Request Type:</td>
															<td width="41%"></td>
															<td width="29%" style="padding-bottom: 5px"><form:select
																	id="accessRequestType" path="accessRequestType"
																	cssStyle="background: white;">
																<form:option value="Add">Add</form:option>
																<form:option value="Modify">Modify</form:option>
																<form:option value="Delete">Delete</form:option>
															</form:select></td>
														</tr>
														<tr> </tr><tr> </tr>
														<tr>
															<td width="30%">Requested Role:</td>
															<td width="41%"></td>
															<td width="29%" style="padding-bottom: 5px"><form:select
																	id="requestedRole" path="requestedRole"
																	cssStyle="background: white;"
																	onchange="showOrHideAccessLevels();">
																<form:option value="owner">Owner</form:option>
																<form:option value="delegate">Delegate</form:option>
																<form:option value="reader">Reader</form:option>
																<form:option value="editor">Editor</form:option>
																<form:option value="coordinator">Coordinator</form:option>
																<form:option value="creator">Creator</form:option>
																<form:option value="dpe">DPE</form:option>


																<!--	<form:option value="admin">Admin</form:option>
																<form:option value="reportAdmin">Report Admin</form:option> -->
															</form:select></td>
														</tr>

													</table>

													<!--		<div id="accessLevelDiv" class="bar-blue-dark">Profile Form - Access
														Level</div>

													<table class="lr-mar-10px" border="0">

														<tr id="activeTr">
															<td width="38%">Active Access Level:</td>
															<td width="32%"></td>
															<td width="30%" style="padding-bottom: 5px"><form:select
																	id="profileActiveLevel" path="profileActiveLevel">
																	<form:option value="No access">No access</form:option>
																	<form:option value="Read">Read</form:option>
																	<form:option value="Save">Save</form:option>
																	<form:option value="Submit">Submit</form:option>
																</form:select></td>
														</tr>

														</tr>
														<tr id="approvedTr"">
															<td width="38%">Approved Access Level:</td>
															<td width="32%"></td>
															<td width="30%" style="padding-bottom: 5px"><form:select
																id="profileApprovedLevel"	path="profileApprovedLevel">
																	<form:option value="No access">No access</form:option>
																	<form:option value="Read">Read</form:option>
																	<form:option value="Save">Save</form:option>
																	<form:option value="Submit">Submit</form:option>

																</form:select></td>
														</tr>
													</table>
												</div> -->
													<div class="bar-blue-dark">Access Justification</div>
													<table class="lr-mar-10px">
														<tr>
															<td style="bgcolor: #ffffff;"><font color="#ffffff">Maximum
																level of Read access only will be allowed for Approved
																stage unless valid business justification warrants
																otherwise. Remove Access option is for removal of access
																on specified Stage of the Contract.</font></td>
														</tr>
													</table>


													<table class="lr-mar-10px">
														<tr>
															<td width="40%">*Business Reason:</td>
															<td style="padding-bottom: 5px"><form:textarea
																	path="justification" cols="63" rows="3" /></td>
														</tr>
													</table>
											</td>
										</tr>

									</table>
									<br />
									<div id="clientInformationDiv_1">
										<div class="bar-blue-black">Form Actions</div>
									</div>
									<br />
									<c:if test="${contractRequest.requestId != null}">
										<table class="lr-mar-10px">
											<tr>
												<td width="40%">Approval/Rejection comments:</td>
												<td style="padding-bottom: 5px"><form:textarea
														id="rejectionComments" path="rejectionComments" cols="63"
														rows="3" /></td>
											</tr>
										</table>
									</c:if>


									<table class="lr-mar-10px">

										<tr>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td><form:hidden path="requestId" id="_requestId" />
												<form:hidden path="formAction" id="formAction" />
												<form:hidden path="action" id="action" />
												<input type="submit"
													   value="Back" tabindex="5" name="ibm-submit"
													   onclick="setFormAction('back');" class="ibm-btn-cart-sec">&nbsp;
												<c:if
														test="${contractRequest.requestId == null && action != 'rev' && contractRequest.status != '2'}">
													<input type="submit" value="Submit" name="ibm-submit"
														   tabindex="6" id="ibm_submit_action"
														   onclick="return setFormAction('submit');"
														   class="ibm-btn-cart-sec">
												</c:if> <c:if
														test="${contractRequest.requestId != null && action != 'rev' && contractRequest.status != '2'  && contractRequest.status != '1'}">
													<input type="submit" value="Resubmit" name="ibm-submit"
														   tabindex="6" id="ibm_submit_action"
														   onclick="return setFormAction('resubmit');"
														   class="ibm-btn-cart-sec">
												</c:if> <c:if
														test="${(isAdmin == 'true' || isApplicationOwner == 'true') && contractRequest.requestId != null  && action == 'rev'  && contractRequest.status != '2' && contractRequest.status != '8' }">
													<input type="submit" value="Approve" name="ibm-submit"
														   tabindex="6" id="ibm_submit_action"
														   onclick="return setFormAction('approve');"
														   class="ibm-btn-cart-sec">
													<input type="submit" value="Reject" name="ibm-submit"
														   tabindex="6" id="ibm_submit_action"
														   onclick="return setFormAction('reject');"
														   class="ibm-btn-cart-sec">
												</c:if>
												<c:if test="${(isAdmin == 'true' || isApplicationOwner == 'true') && contractRequest.requestId != null  && action == 'rev'  && contractRequest.status == '2' && contractRequest.status != '8' }">
													<input type="submit" value="Close" name="ibm-submit"
														   tabindex="6" id="ibm_submit_action"
														   onclick="return setFormAction('close');"
														   class="ibm-btn-cart-sec">
												</c:if></td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>&nbsp;</td>
										</tr>
									</table>
								</div>
							</form:form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script src="js/jquery.js"></script>
<script type="text/javascript">
	dojo.replaceClass("supportTab", "ibm-active");
	dojo.byId("supporSubtTab").style.display = 'block';
	dojo.replaceClass("supportListTab", "ibm-active");

	displayRequests();

	function reloadPage(){
		console.log("reloadPage()........");
		var title = dojo.byId("contract_id");
		var _requestId = dojo.byId("_requestId");
		var action = dojo.byId("action");
		console.log('action : '+ action.value);



		if(title.value != ''){

			var URL = "<%=basePath%>accessRequest.htm?title=" + title.value;

			if(action != null && action.value != null && action.value != ''){
				URL = URL + "&action=" + action.value;
			}
			if (_requestId.value != '') {
				URL = URL + "&requestId=" + _requestId.value;
			}
			console.log("loading........" + URL);
			window.location.href = URL;
		}
	}
	function showOrHideAccessLevels(){

		var role = dojo.byId("requestedRole").value;
		if(role == 'Reports'){
			$('#accessLevelDiv').hide();
			$('#activeTr').hide();
			$('#awaitingTr').hide();
			$('#approvedTr').hide();
		}else {
			$('#accessLevelDiv').show();
			$('#activeTr').show();
			$('#awaitingTr').show();
			$('#approvedTr').show();
		}

	}

	function setFormAction(_formAction) {
		dojo.byId("formAction").value = _formAction;
	}

	function displayRequests() {
		var _scope = dojo.byId('_requestScope').value;
		console.log("_requestScope = " + _scope);
		if (_scope == 'contract level') {
			dojo.byId('contract').style.display = 'block';
			dojo.byId('CON').checked = true;
		} else if (_scope == 'group level') {
			dojo.byId('group').style.display = 'block';
			dojo.byId('level').checked = true;
		} else if (_scope == 'remove user access') {
			dojo.byId('access').style.display = 'block';
			dojo.byId('Remove').checked = true;
		}
	}

	function setRequestScope(_scope) {
		console.log("_requestScope = " + _scope);
		dojo.byId('_requestScope').value = _scope;
	}

	function cont(id) {
		var isManOr = document.getElementById('CON');
		if (isManOr.checked) {
			dojo.byId(id).style.display = 'block';
		} else {
			dojo.byId(id).style.display = 'none';
		}
	}
	function group(id) {
		var isManOr = document.getElementById('level');
		if (isManOr.checked) {
			dojo.byId(id).style.display = 'block';
		} else {
			dojo.byId(id).style.display = 'none';
		}
	}

	function access(id) {
		var isManOr = document.getElementById('Remove');
		if (isManOr.checked) {
			dojo.byId(id).style.display = 'block';
		} else {
			dojo.byId(id).style.display = 'none';
		}
	}
	function country(id) {
		var isManOr = document.getElementById('c');
		if (isManOr.checked) {
			dojo.byId(id).style.display = 'block';
		} else {
			dojo.byId(id).style.display = 'none';
		}
	}
	function imt(id) {
		var isManOr = document.getElementById('i');
		if (isManOr.checked) {
			dojo.byId(id).style.display = 'block';
		} else {
			dojo.byId(id).style.display = 'none';
		}
	}
	function iot(id) {
		var isManOr = document.getElementById('o');
		if (isManOr.checked) {
			dojo.byId(id).style.display = 'block';
		} else {
			dojo.byId(id).style.display = 'none';
		}
	}
	function sector(id) {
		var isManOr = document.getElementById('s');
		if (isManOr.checked) {
			dojo.byId(id).style.display = 'block';
		} else {
			dojo.byId(id).style.display = 'none';
		}
	}
	function customer(id) {
		var isManOr = document.getElementById('cu');
		if (isManOr.checked) {
			dojo.byId(id).style.display = 'block';
		} else {
			dojo.byId(id).style.display = 'none';
		}
	}

	function hide(id) {
		dojo.byId(id).style.display = 'none';
	}
</script>
