<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> 
<style type="text/css" media="all">
.bar-green-dark	{background:#7a3; color:#fff; padding:.3em .5em; font-size:1.1em; font-weight:bold;}
.bar-green-med-dark	{background:#9c3; color:#fff; padding:.3em .5em; font-size:1.1em; font-weight:bold;}
.bar-green-med-dark_cell	{background:#9c3; color:#fff; margin: 2px 2px 2px 2px; padding:2px 2px 2px 2px; font-size:1.1em; font-weight:bold;}
.bar-green-med-light	{background:#bd6; color:#000; padding:.3em .5em; font-size:1.1em; font-weight:bold;}
.bar-blue-med-dark	{background:#47b; color:#fff; padding:.3em .5em; font-size:1.1em; font-weight:bold;}
.bar-blue-med	{background:#69c; color:#fff; padding:.3em .5em; font-size:1.1em; font-weight:bold;}
.bar-blue-med-light	{background:#9be; color:#000; padding:.3em .5em; font-size:1.1em; font-weight:bold;}
.bar-gray-dark	{background:#666; color:#fff; padding:.3em .5em; font-size:1.1em; font-weight:bold;}
.bar-gray-med-dark	{background:#999; color:#fff; padding:.3em .5em; font-size:1.1em; font-weight:bold;}
.bar-gray-med-light	{background:#ccc; color:#000; padding:.3em .5em; font-size:1.1em; font-weight:bold;}
.bar-gray-light	{background:#ddd; color:#000; padding:.3em .5em; font-size:1.1em; font-weight:bold;}
.bar-blue-black	{background:#001A2F; color:#fff; padding:.3em .5em; font-size:1.1em; font-weight:bold;}
.bar-green-black	{background:#0F7D62; color:#fff; padding:.3em .5em; font-size:1.1em; font-weight:bold;}
.lr-mar-5px {margin-left: 5px; margin-right: 5px}
.r-mar-5px {margin-right: 5px}
.dot-bor-1px {border:2px dotted #CCC}
.sol-bor-1px { border:1px solid #CCC}
.contract-contact-info{width:180px}
.errors{color: #ff0000; font-style: italic;}
#general_Information span { display: block; height: 25px;}
#general_Information label { width: auto; width: 250px; font-weight: normal;}  
#general_Information tr {height: 25px;}
.lr-mar-10px {margin-top: 7px; margin-left: 10px; margin-right:10px}

#bcrs span { display: block; height: 30px;}
#bcrs label { width: auto; width: 550px; font-weight: normal;}  
#csst span { display: block; height: 30px;}
#csst label { width: auto; width: 550px; font-weight: normal;}  
</style>

<!-- <script src="js/CalendarPopup.js"   type="text/javascript"></script> -->

<div id="ibm-pcon"><!-- CONTENT_BEGIN -->
	<div id="ibm-content"><!-- TITLE_BEGIN --> 
 		<!-- TITLE_END --> <!-- CONTENT_BODY -->
		<div id="ibm-content-body">
			<div id="ibm-content-main">
				<div class="ibm-columns">
					<div class="ibm-col-6-6">						
						<div id="contractDetailContainer" class="ibm-container">
							<div id="main-content-btn" align="right">
								<a href="https://w3-connections.ibm.com/wikis/home?lang=en-us#!/wiki/GPS%20-%20BPDCHIP/page/Modify%20Existing%20Contract%20Information%20in%20BPDCHIP">Help</a>
							</div>						
							<form:form method="post" commandName="contractRequest" cssClass="ibm-column-form" name="">
							<form:hidden path="" />
							<form:hidden path="status" id="status" />
							
															
									<div id="ibm-leadspace-head" class="ibm-alternate">
										<div id="ibm-leadspace-body">
											<ul id="ibm-navigation-trail">
												<li><a href="/contracts.htm">GPSDRCA</a></li>
												<li><a href="/support.htm">Support</a></li>
												<li><a href="#">Modify Existing Account</a></li>
											</ul>									
													
											<br/>
											<form:errors path="*" element="div" cssClass="errors"/>
										    <br/><br/>

											<span id="requiredFieldExplaination">
												Required fields are marked with asterisk(<span class="ibm-required">*</span>)and must be completed.
											</span>
										</div>	
									</div>
									<div id="body" class="ibm-container-body; sol-bor-1px">
										<div id="clientInformationDiv">
											<div class="bar-blue-black">Modify Account Information</div>		
										</div>
										<table class="lr-mar-10px">
											<tr>
												<td>This Form is for requesting updates/modification to the existing Account information. This Form should not be used to request Account deletion/closure or to change a Account's reporting frequency.</td>
											</tr>
										</table>
										<div class="bar-green-black">Select Account Name</div>
										<table class="lr-mar-10px" >
											<tr>
												<td width="39.5%">*Account Name:</td>
												<td width="60.5%" style="padding-bottom: 5px">
													<form:select  path="contractName"  cssStyle="background: white; width:385px;" onchange="reloadPage();" id="contract_id">
														<form:option value="" label="-- Select Account --" />
														<form:options items="${referenceData.listContracts}" itemValue="title" itemLabel="title"/> 
													</form:select>
												</td>
											</tr>
										</table>
										<div class="bar-green-black">Modify Account Details</div>
										<table class="lr-mar-10px">
											<tr>
												<td width="40%">Current Account Name:</td>
												<td width="60%"> <input type="text" value = "${contractRequest.contractName}" size="66" readonly="true"/></td>
											</tr>
											<tr>
												<td width="40%">*New Account Name:</td>
												<td width="60%" style="padding-top: 5px"><form:input id="newContractName"  size="66" path="newContractName"/></td>
											</tr>

											<tr>
												<td> GPSDRCA Account Id:</td>
												<td style="padding-top: 5px; padding-bottom: 5px"><input type="text" value="${contract.contractId}" size="66" readonly="true"/></td>
											</tr>
										</table>
										<div class="bar-green-black">Business Justification</div>
										<table class="lr-mar-10px">
											<tr>
												<td width="40%">Business Reason:</td>
												<td style="padding-bottom: 5px"><form:textarea id="businessPurpose" path="justification" cols="63" rows="3"/></td>
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
												<td>&nbsp;</td>
												<td>&nbsp;</td>
											</tr>
											<tr>
												<td>
												<form:hidden path="requestId" id="_requestId"/>
												<form:hidden path="formAction" id="formAction"/>
												<input type="submit" value="Back" tabindex="5" name="ibm-submit" onclick="setFormAction('back');" class="ibm-btn-cart-sec">&nbsp;
												<c:if test="${contractRequest.requestId == null && action != 'rev' && contractRequest.status != '2'}">
													<input type="submit" value="Submit" name="ibm-submit" tabindex="6" id="ibm_submit_action" onclick="return setFormAction('submit');" class="ibm-btn-cart-sec">
												</c:if>
												<c:if
													test="${contractRequest.requestId != null && action != 'rev' && contractRequest.status != '2'  && contractRequest.status != '9'}">
													<input type="submit" value="Resubmit" name="ibm-submit"
														tabindex="6" id="ibm_submit_action"
														onclick="return setFormAction('resubmit');"
														class="ibm-btn-cart-sec">
												</c:if> 
												<c:if test="${accessLevel == 32 && contractRequest.requestId != null  && action == 'rev' }">
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
												</c:if> 
												</td>
												<td>&nbsp;</td>
											</tr>
											<tr>
												<td>&nbsp;</td>
												<td>&nbsp;</td>
											</tr>
										</table>
								</form:form>
								</div>
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

function reloadPage(){
	console.log("reloadPage()........");
	var title = dojo.byId("contract_id");
	if(title.value != ''){
		var URL = "<%=basePath%>modifyContract.htm?title="+title.value;
		console.log("loading........"+URL);
		window.location.href = URL;
	}
}

function setFormAction(_formAction){
	dojo.byId("formAction").value =  _formAction;
}

function enableOrDisableNewContractForm() {
		var status = dojo.byId('status').value;
		console.log('status: ' + status);
		if (status == '2' || status == '9') {
		$('#newContractName').attr('readonly', 'readonly');
		$('#contract_id').attr('disabled', 'disabled');
		$('#businessPurpose').attr('readonly', 'readonly');
		$('#rejectionComments').attr('readonly', 'readonly');
		}
	}
</script>

	