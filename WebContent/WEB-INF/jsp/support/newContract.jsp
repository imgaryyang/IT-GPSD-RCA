<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
									href="https://w3-connections.ibm.com/wikis/home?lang=en-us#!/wiki/GPS%20-%20BPDCHIP/page/Adding%20a%20Contract%20in%20BPDCHIP">Help</a>
							</div>
							<form:form method="post" commandName="contractRequest"
								cssClass="ibm-column-form" name="contractRequest">
								<form:hidden path="" />
								<form:hidden path="status" id="status" />

								<div id="ibm-leadspace-head" class="ibm-alternate">
									<div id="ibm-leadspace-body">
										<ul id="ibm-navigation-trail">
											<li><a href="/GPSDRCA/contracts.htm">GPSDRCA</li>
											<li><a href="/GPSDRCA/support.htm">Support</a></li>
											<li>Add New Account</li>
										</ul>

										<br />
										<br />
										<form:errors path="*" element="div" cssClass="errors" />

										<span id="requiredFieldExplaination"> Required fields
											are marked with asterisk(<span class="ibm-required">*</span>)and
											must be completed.
										</span>
									</div>
								</div>
								<div id="body" class="ibm-container-body; sol-bor-1px">

									<div id="clientInformationDiv">
										<div class="bar-blue-black">New Account Information</div>
									</div>
									<br>
									<div class="bar-green-black">Account Details</div>
									<table class="lr-mar-10px">
										<tr>
											<td width="40%">*Account Name:</td>
											<td><form:input id="contractName" path="contractName" size="66" /></td>
										</tr>


										<tr>
											<td width="40%"><br />*Business Reasons:</td>
											<td><br />
											<form:textarea id="businessPurpose"  path="businessPurpose" cols="63" rows="3" /></td>
										</tr>


									</table>



									<table class="lr-mar-10px">

									</table>
									<div class="bar-green-black">Initial Profile Form -
										Access Level</div>

									<table class="lr-mar-10px">
										<tr>
											<td width="40%">Users Intranet Ids:</td>
											<td style="padding-bottom: 5px"><form:input
													id="profileEmail" path="profileEmail" size="66" /></td>
										</tr>
										<tr>
											<td width="40%">Active Access Level:</td>
											<td style="padding-bottom: 5px"><form:select
													id="profileActiveLevel" path="profileActiveLevel">
													<form:option value="No access">No access</form:option>
													<form:option value="Read">Read</form:option>
													<form:option value="Save">Save</form:option>
													<form:option value="Submit">Submit</form:option>
													<form:option value="Approve">Approve</form:option>
												</form:select></td>
										</tr>

										<tr>
											<td width="40%">Approved Access Level:</td>
											<td style="padding-bottom: 5px"><form:select
													 id="profileApprovedLevel" path="profileApprovedLevel">
													<form:option value="No access">No access</form:option>
													<form:option value="Read">Read</form:option>
													<form:option value="Save">Save</form:option>
													<form:option value="Submit">Submit</form:option>
													<form:option value="Approve">Approve</form:option>
												</form:select></td>
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
													 id="rejectionComments"	path="rejectionComments" cols="63" rows="3" /></td>
											</tr>
										</table>
									</c:if>
									<table class="lr-mar-10px">
										<tr>
											<td colspan="2"><form:hidden path="requestId"
													id="_requestId" /> <form:hidden path="formAction"
													id="formAction" /> <input type="submit" value="Back"
												tabindex="5" name="ibm-submit"
												onclick="setFormAction('back');" class="ibm-btn-cart-sec">&nbsp;

												<c:if
													test="${contractRequest.requestId == null && action != 'rev' && contractRequest.status != '2'}">
													<input type="submit" value="Submit" name="ibm-submit"
														tabindex="6" id="ibm_submit_action"
														onclick="return setFormAction('submit');"
														class="ibm-btn-cart-sec">
												</c:if> <c:if
													test="${contractRequest.requestId != null && action != 'rev' && contractRequest.status != '2'  && contractRequest.status != '9'}">
													<input type="submit" value="Resubmit" name="ibm-submit"
														tabindex="6" id="ibm_submit_action"
														onclick="return setFormAction('resubmit');"
														class="ibm-btn-cart-sec">
												</c:if> <c:if
													test="${accessLevel == 32 && contractRequest.requestId != null  && action == 'rev' }">
													<input type="submit" value="Approve" name="ibm-submit"
														tabindex="6" id="ibm_submit_action"
														onclick="return setFormAction('approve');"
														class="ibm-btn-cart-sec">
													<input type="submit" value="Reject" name="ibm-submit"
														tabindex="6" id="ibm_submit_action"
														onclick="return setFormAction('reject');"
														class="ibm-btn-cart-sec">
													<input type="submit" value="Return" name="ibm-submit"
														tabindex="6" id="ibm_submit_action"
														onclick="return setFormAction('return');"
														class="ibm-btn-cart-sec">
												</c:if> <!--	<c:if test="${(action == 'rev') }">

													<input type="submit" value="Reviewed" name="ibm-submit" tabindex="6" id="ibm_submit_action" onclick="return setFormAction('Reviewed');" class="ibm-btn-cart-sec">
												</c:if>
												<c:if test="${action == 'appr' }">
													<input type="submit" value="Save" name="ibm-submit" tabindex="6" id="ibm_submit_action" onclick="return setFormAction('save');" class="ibm-btn-cart-sec">
												</c:if>  --></td>
										</tr>
										<tr>
											<td>&nbsp;</td>
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
enableOrDisableNewContractForm();

	function legalName(id){
		var isManOr = document.getElementById('listed');
		if(isManOr.checked){
			dojo.byId(id).style.display = 'block';
		}
		else{
			dojo.byId(id).style.display = 'none';
		}
	}
	function setFormAction(_formAction){
		dojo.byId("formAction").value =  _formAction;
	}

	function customers(id){
		var isManOr = document.getElementById('unlisted');
		if(isManOr.checked){
			dojo.byId(id).style.display = 'block';
		}
		else{
			dojo.byId(id).style.display = 'none';
		}
	}
	function hide(id){
		try {
			dojo.byId(id).style.display = 'none';
		} catch(err)
		{
		   console.debug("Error show(" + id+") => "+err.message);
		}
	}
	function show(id){
		try {
			dojo.byId(id).style.display = 'block';
		} catch(err)
		{
		   console.debug("Error show(" + id+") => "+err.message);
		}
	}


	function enableOrDisableNewContractForm() {
		var status = dojo.byId('status').value;
		console.log('status: ' + status);
		if (status == '2' || status == '9') {
		$('#contractName').attr('readonly', 'readonly');
		$('#businessPurpose').attr('readonly', 'readonly');
		$('#profileEmail').attr('readonly', 'readonly');
		$('#profileActiveLevel').attr('disabled', 'disabled');
		$('#profileApprovedLevel').attr('disabled', 'disabled');
		$('#rejectionComments').attr('readonly', 'readonly');
		}
	}
</script>
