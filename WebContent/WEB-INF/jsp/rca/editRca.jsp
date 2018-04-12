<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    System.out.println("showing editContract.jsp....");
%>

<head profile="w3.ibm.com">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />
    <title>Datepicker</title>
    <link rel="stylesheet" type="text/css"
          href="css/jquery.datetimepicker.css"/>
    <link href="http://ciolab.ibm.com/misc/typeahead/builds/facestypeahead-0.4.4.css" rel="stylesheet"></link>
    <link rel="stylesheet" type="text/css"
          href="css/style.css"/>

    <script src="js/jquery.js"></script>
    <script src="js/jquery.datetimepicker.js"></script>
    <script src="http://ciolab.ibm.com/misc/typeahead/builds/facestypeahead-0.4.4.js"></script>
    <script>
        dojo.addOnLoad(function(){
            ta1 = FacesTypeAhead.init(
                    dojo.query(".typeahead"),
                    {
                        key: "typeahead_demo;webahead@us.ibm.com",
                        faces: {
                            headerLabel: "People",

                            onclick: function(person) {
                                return person['notes-id'];
                            }
                        }
                    });
            
        });
        
        jQuery(document).ready(function(){
    		console.log('jQuery(document).ready().......');
    		initCustomerOther();
    		showCustomerImpact();
    	});
    	
        function showCustomerImpact(){
        	if(document.getElementById('customerImpactedY').checked) {
        		show('customer_impact_details');
        	} else {
        		hide('customer_impact_details');
        		jQuery('#customerImpactedList :selected').each(function(i, selected){ 
              	  jQuery(selected).prop("selected", false);
              	});
        		showCustomerOther();
        	}
        	 
        }
       
        function ibmClientManage(clickId){
        	console.log('ibmClientManage().......');
        	if(jQuery(clickId).attr('id') == 'ibmManaged') {
        		document.getElementById('clientManaged').checked = false;
        	} else {
        		console.log('clientManaged');
        		document.getElementById('ibmManaged').checked = false;
        	}
        	 
        } 
        
        
    </script>
</head>


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

                            <form:form method="post" commandName="rcaForm"
                                       cssClass="ibm-column-form" id="rcaForm" name="rcaForm" enctype="multipart/form-data">
                            <form:hidden path="rca.rcaId" id="rcaId"/>
                            <form:hidden path="rca.contract.contractId" id="contractId"/>
                            <form:hidden path="isRcaOwnerLoggedIn" id="isRcaOwnerLoggedIn"/>
                            <form:hidden path="isRcaDelegateLoggedIn"
                                         id="isRcaDelegateLoggedIn"/>
                            <form:hidden path="loggedInUserRoles" id="loggedInUserRoles"/>
                            <form:hidden path="rcaFormActionsHelper.approvalComments"
                                         id="approvedComments"/>
                            <form:hidden path="rcaFormActionsHelper.rejectionComments"
                                         id="rejectedComments"/>
                            <form:hidden path="rcaFormActionsHelper.cancellationComments"
                                         id="cancelledComments"/>
                            <form:hidden path="rcaFormActionsHelper.reopenComments"
                                         id="reopenedComments"/>

                            <form:hidden path="formAction" id="formAction"/>
                            <form:hidden path="accessLevel" id="accessLevel"/>
                            <form:hidden path="userRoles" id="userRoles"/>
                            <form:hidden path="fileDescription" id="fileDescription" />
                            <form:hidden path="isDueDateModificationEnabled" id="isDueDateModificationEnabled" />
                            <form:hidden path="isDpeApprovalRequestSent" id="isDpeApprovalRequestSent" />
                            <form:hidden path="rcaDueDate" id="rcaDueDate" />

                            <div id="pii" style="display: none">
                                <p style="display: none">The fields indicated with an
                                    asterisk (*) are required to complete this transaction; other
                                    fields are optional. If you do not want to provide us with the
                                    required information, please use the "Back" button on your
                                    browser to return to the previous page, or close the window or
                                    browser session that is displaying this page.</p>
                            </div>


                            <div id="ibm-leadspace-head" class="ibm-alternate">
                                <div align="right">
                                    <a
                                            href="https://w3-connections.ibm.com/wikis/home?lang=en_US#/wiki/GPS%20Root%20Cause%20Analysis%20%28RCA%29/page/Introduction"
                                            target="_blank">Help</a>
                                </div>
                                <div id="ibm-leadspace-body">
                                    <ul id="ibm-navigation-trail">
                                        <li><a href="/gpsrca/rcas.htm">GPSDRCA</a></li>
                                        <li><a href="/gpsrca/rcas.htm">RCAs/Actions</a></li>
                                        <li><a href="#">Edit RCA</a></li>
                                    </ul>

                                    <br/>

                                    <h1 class="ibm-small">
                                        <form:label path="rca.contract.title"
                                                    style="width:800px">${contractId} | ${contractTitle}</form:label>
                                    </h1>
                                    <br/> <br/>
                                    <h5> &nbsp; &nbsp; Month: ${rcaMonth} - Year: ${rcaYear} </h5>

                                    <br />
                                    <form:errors path="*" element="div" cssClass="errors"/>
                                    <span class="errors" align="center">${rcaForm.formSavedMessage}</span>
                                    <br />

                                    <br/> <span>
               								<span class="ibm-required">- </span>Required fields are marked with asterisk(<span class="ibm-required">*</span>)and
											a double asterisk (<span class="ibm-required">**</span>)indicates
											conditionally required fields.
										</span>
                                    <br/>
										 <span>
										 <span class="ibm-required">- </span>The allowed character limit in string attribute is 250 characters.
										</span>
                                </div>
                            </div>

                            <div id="body" class="ibm-container-body; sol-bor-1px">
                                <div id="clientInformationDiv">
                                    <div class="bar-blue-black">Header</div>
                                    <br/>

                                    <div class="lr-mar-10px" id="headerDiv">
                                        <table id="contract_contacts" summary="Contacts"
                                               class="ibm-results-table" border="0">
                                            <thead>
                                            <tr>
                                                <th scope="col" width="15%"><label
                                                        style="font-weight: normal;"><span
                                                        class="ibm-required">*</span> RCA Title:</label></th>
                                                <th scope="col"><form:textarea id="title"
                                                                               path="rca.title" cols="137"
                                                                               rows="1" maxlength="4999" onpaste="return (this.value.length < 4999);"/></th>

                                            </tr>


                                            </thead>
                                        </table>

										

										<jsp:include page="boxes.jsp" />
                                        
                                        
                                        <table id="rca_number" summary="Contacts"
                                               class="ibm-results-table" border="0">
                                            <thead>
                                            <tr>
                                                <th scope="col" align width="15%"><label
                                                        style="font-weight: normal;">RCA Number:</label></th>
                                                <th scope="col" width="15%"><form:input
                                                        path="rca.rcaNumber" readonly="true"
                                                        style="background: #fafafa" size="28"/></th>
                                                <th scope="col" width="5%"></th>
                                                <th scope="col" width="16%" style="font-weight: normal;">RCA
                                                    Stage:
                                                </th>
                                                <th scope="col" width="9%"><form:input
                                                        path="rca.rcaStage" readonly="true" id="rcaStage"
                                                        style="background: #fafafa"/></th>
                                                <th scope="col" width="10%"></th>
                                                <th scope="col" align="left" width="15%"
                                                    style="font-weight: normal;">RCA Create Date:
                                                </th>
                                                <th scope="col" width="15%"><form:input
                                                        path="rcaFormDateHelper.rcaCreatedDate" id="createDate"
                                                        readonly="true" style="background: #fafafa"/></th>
                                            </tr>

                                            </thead>
                                        </table>

                                        <table id="rca_number" summary="Contacts"
                                               class="ibm-results-table" border="0">
                                            <thead>
                                            <tr>
                                                <th scope="col" align width="15%"><label style="font-weight: normal;">RCA Associated with:</label></th>
                                                <th scope="col" width="15%">
                                                    <form:select id="rcaAssociatedWith" path="rca.rcaAssociatedWith"
                                                                 style="width:178px;">
                                                        <form:option value=""></form:option>
                                                        <form:option value="Sev 1 incident">Sev 1 incident</form:option>
                                                        <form:option value="SLA miss">SLA miss</form:option>
                                                        <form:option value="Chronic issue">Chronic issue</form:option>
                                                        <form:option value="Exec request">Exec request</form:option>
                                                        <form:option value="SLO miss">SLO miss</form:option>
                                                        <form:option value="Client Managed">Client Managed</form:option>
                                                    </form:select></th>
                                                <th scope="col" width="5%"></th>
                                                <th scope="col" width="16%"><span class="ibm-required">*</span>List SLA/SLO
                                                    Impacted:
                                                </th>
                                                <th scope="col" width="9%"><form:input
                                                        path="rca.listOfSlaSloImpacted" id="listOfSlaSloImpacted"
                                                        maxlength="250" /></th>
                                                <th scope="col" width="10%"></th>
                                                <th scope="col" width="15%" style="font-weight: normal;">&nbsp;&nbsp;&nbsp;&nbsp;RCA
                                                    Due Date:
                                                </th>
                                                <th scope="col" width="15%"><form:input
                                                        path="rcaFormDateHelper.rcaDueDate" id="dueDate" onchange="enableDueDateJustification();"/>
                                                    <input type="hidden" id="oldDueDate" value="" />
                                                    <input type="hidden" id="savedDueDate" value="" />
                                                </th>
                                            </tr>

                                            </thead>
                                        </table>
                                        <table id="due_Date_Modification_Reason" summary="Contacts"
                                               class="ibm-results-table" border="0" style="display:none;">
                                            <thead>

                                            <tr>
                                                <th scope="col" width="15%"><label
                                                        style="font-weight: normal;"><span
                                                        class="ibm-required">**</span>Justification/Reason for Changing RCA Due Date:</label></th>
                                                <th scope="col"><form:textarea id="dueDateModificationReason"
                                                                               path="rca.dueDateModificationReason" cols="137"
                                                                               rows="1" maxlength="4999" onpaste="return (this.value.length < 4999);"/></th>

                                            </tr>

                                            </thead>
                                        </table>

                                        <table id="rca_closed_date" summary="Contacts"
                                               class="ibm-results-table" border="0">
                                            <thead>
                                            <tr>
                                                <th scope="col" align width="15%"><label
                                                        style="font-weight: normal;">Action Items Open:</label></th>
                                                <th scope="col" width="15%" style="bgcolor: #cccccc"><form:input
                                                        path="rca.actionItemOpen" readonly="true"
                                                        style="background: #fafafa" size="28"/></th>
                                                <th scope="col" width="5%"></th>
                                                <th scope="col" width="16%" style="font-weight: normal;">Action
                                                    Items Closed:
                                                </th>
                                                <th scope="col" width="9%"><form:input
                                                        path="rca.actionItemClosed" readonly="true"
                                                        style="background: #fafafa"/></th>

                                                <th scope="col" width="10%"></th>
                                                <th scope="col" align="left" width="15%"
                                                    style="font-weight: normal;">RCA Closed Date:
                                                </th>
                                                <th scope="col" width="15%"><form:input
                                                        path="rcaFormDateHelper.rcaClosedDate"
                                                        readonly="true" style="background: #fafafa"/></th>
                                            </tr>

                                            </thead>
                                        </table>


                                    </div>


                                    <div class="bar-blue-black">Contacts</div>
                                    Note: RCA Owner and RCA DPE cannot be the same
                                    <br/>


                                    <div class="lr-mar-10px" id="contactsDiv">
                                        <table id="Contacts" summary="Contacts"
                                               class="ibm-results-table" border="0">
                                            <thead>
                                            <tr>
                                                <th scope="col" align width="22%"
                                                    style="font-weight: normal;">RCA Coordinator:
                                                </th>
                                                <th scope="col" width="20%"><form:input
                                                        path="rcaCoordinatorName"
                                                        readonly="true" style="background: #fafafa"
                                                        size="25"/></th>
                                                <th scope="col" width="2%"></th>
                                                <th scope="col" width="15%" style="font-weight: normal;"><span
                                                        class="ibm-required">*</span> RCA
                                                    Owner:
                                                </th>
                                                <th scope="col" width="10%"><form:input
                                                        path="rca.rcaOwner" size="25" class="typeahead" maxlength="250"/></th>
                                                <th scope="col" width="2%"></th>
                                                <th scope="col" align="centre" width="22%"
                                                    style="font-weight: normal;"><span
                                                        class="ibm-required">**</span> RCA
                                                    Accepted By Owner:
                                                </th>

                                                <th scope="col" width="7%" style="font-weight:normal;"><input
                                                        type="checkbox"
                                                        path="rca.isOwnerAccepted" id="isOwnerAcceptedBox"
                                                        value="" onclick="setOwnerAccepted();"/> Yes <form:hidden
                                                        id="isOwnerAccepted" path="rca.isOwnerAccepted"/></th>
                                            </tr>


                                            </thead>
                                        </table>

                                        <table id="rca_number" summary="Contacts"
                                               class="ibm-results-table" border="0">
                                            <thead>
                                            <tr>
                                                <th scope="col" align width="22%"
                                                    style="font-weight: normal;">RCA Coordinator
                                                    Manager:
                                                </th>
                                                <th scope="col" width="20%"><form:input
                                                        path="rcaCoordinatorManager" readonly="true"
                                                        style="background: #fafafa" size="25"/></th>
                                                <th scope="col" width="2%"></th>
                                                <th scope="col" width="15%" style="font-weight: normal;">
                                                    <span class="ibm-required">*</span> RCA
                                                    Delegate:
                                                </th>
                                                <th scope="col" width="10%"><form:input
                                                        path="rca.rcaDelegate" size="25" class="typeahead" maxlength="250"/></th>
                                                <th scope="col" width="2%"></th>
                                                <th scope="col" align="right" width="22%"
                                                    style="font-weight: normal;"><span
                                                        class="ibm-required">**</span> RCA
                                                    Accepted By Delegate:
                                                </th>
                                                <th scope="col" align="right" width="7%" style="font-weight:normal;">
                                                    <input
                                                            type="checkbox" id="isDelegateAcceptedBox" value=""
                                                            onclick="setDelegateAccepted();"/> Yes<form:hidden
                                                        id="isDelegateAccepted" path="rca.isDelegateAccepted"/>
                                                </th>
                                            </tr>

                                            </thead>
                                        </table>


                                        <table id="rca_number" summary="Contacts"
                                               class="ibm-results-table" border="0">
                                            <thead>
                                            <tr>
                                                <th scope="col" width="20%" style="font-weight: normal;">
                                                    Additional Editor:
                                                </th>
                                                <th scope="col" width="80%"><form:select
                                                        path="rcaEditorIds" id="rcaEditorIds" multiple="true">
                                                    <form:options items="${contractEditors}"
                                                                  itemValue="gpsUser.userId" itemLabel="gpsUser.notesId"/>
                                                </form:select></th>

                                            </tr>

                                            </thead>
                                        </table>

                                        <table id="rca_closed_date" summary="Contacts"
                                               class="ibm-results-table" border="0">
                                            <thead>
                                            <tr>
                                                <th scope="col" align width="20%"
                                                    style="font-weight: normal;">RCA Created By:
                                                </th>
                                                <th scope="col" width="20%" style="bgcolor: #cccccc">
                                                    <form:input path="rca.rcaCreatedBy.notesId"
                                                                readonly="true" style="background: #fafafa"
                                                                size="25"/>
                                                </th>
                                                <th scope="col" width="1%"></th>
                                                <th scope="col" width="13%" style="font-weight: normal;">
                                                    <span class="ibm-required">*</span>RCA DPE:
                                                </th>
                                                <th scope="col" width="46%"><form:select
                                                        path="rca.rcaDpeId" id="rcaDpeId">
                                                    <form:option value="0" label="Please select the option"/>
                                                    <form:options items="${contractDpeList}"
                                                                  itemValue="gpsUser.userId" itemLabel="gpsUser.notesId"
                                                                  size="25"/>
                                                </form:select></th>


                                            </tr>

                                            </thead>
                                        </table>
                                        <table id="incident_details" summary="Contacts"
                                               class="ibm-results-table" border="0">
                                            <thead>
                                            <tr>
                                                <th scope="col" align width="20%"><span class="ibm-required">*</span>Outage Start Date:
                                                </th>
                                                <th scope="col" width="17%"><form:input
                                                        path="rcaFormDateHelper.incidentStartDateTime"
                                                        id="incidentStartTime" size="25"
                                                        onchange="calculateIncidentDuration();" maxlength="250"/></th>
                                                <th scope="col" width="2%"></th>
                                                <th scope="col" align="centre" width="14%"><span class="ibm-required">*</span>Outage End Date:
                                                </th>
                                                <th scope="col" width="17%"><form:input
                                                        path="rcaFormDateHelper.incidentEndDateTime"
                                                        id="incidentEntTime" size="25" maxlength="250"
                                                        onchange="calculateDurationAndDueDate('${asePath}')"/>

                                                </th>
                                                <th scope="col" align="left" width="20%"></th>
                                                <th scope="col" width="8%"></th>
                                            </tr>
                                            </thead>
                                        </table>
                                        <table id="incident_details" summary="Contacts"
                                               class="ibm-results-table" border="0">
                                            <thead>
                                            <tr>
                                                <th scope="col" align width="20%"
                                                    style="font-weight:normal;"><span class="ibm-required">*</span>Outage Duration:
                                                </th>
                                                <th scope="col" width="17%"><form:input
                                                        path="rca.incidentDuration"
                                                        id="incidentDuration" size="25" maxlength="250"
                                                /></th>
                                                <th scope="col" width="2%"></th>
                                                <th scope="col" align="centre" width="14%"></th>
                                                <th scope="col" width="17%"></th>
                                                <th scope="col" align="left" width="20%"></th>
                                                <th scope="col" width="8%"></th>
                                            </tr>
                                            </thead>
                                        </table>


                                        <br/>

                                        <!-- Incident Details -->
                                        <div class="bar-green-black" style="font-weight: normal;">
                                            <table id="rca_closed_date" summary="Contacts" border="0">
                                                <thead>
                                                <tr>

                                                    <th width="60%">Incident, Problem or Change Ticket Numbers</th>
                                                    <th width="10%"></th>
                                                    <th width="10%"></th>
                                                    <th width="20%"></th>
                                                </tr>
                                                </thead>
                                            </table>
                                        </div>
                                        <br/>

                                        </table>

                                        <table id="incident_details" summary="Contacts"
                                               class="ibm-results-table" border="0">
                                            <thead></thead>
                                            <tbody>
                                            <tr>
                                                <td scope="col" width="100%" style="font-weight:normal;">

                                                    <span class="ibm-required">*</span>Do you have a Primary Ticket?:
											<span class="ibm-input-group ibm-radio-group">
												<form:radiobutton id="primaryTicketExistsY"
                                                                  path="rca.primaryTicketExists" value="Y"
                                                                  onclick="show('yes_primary_ticket_detail'); hide('no_primary_ticket_detail'); resetPrimaryTktDetails('Y');"/>
												Yes
												<form:radiobutton id="primaryTicketExistsN"
                                                                  path="rca.primaryTicketExists" value="N"
                                                                  onclick="show('no_primary_ticket_detail'); hide('yes_primary_ticket_detail'); resetPrimaryTktDetails('N');"/>
												No
											</span>
                                                </td>

                                            </tr>
                                            </tbody>
                                        </table>


                                        <table id="yes_primary_ticket_detail" summary="Contacts"
                                               class="ibm-results-table" border="0" style="display:none">
                                            <thead>
                                            <tr>
                                                <th scope="col" align width="12%"
                                                    style="font-weight: normal;"><span class="ibm-required">**</span>Primary Ticket#
                                                </th>
                                                <th scope="col" width="12%"><form:input id="problemIndidentRecord"
                                                                                        path="rca.problemIndidentRecord" maxlength="250"/></th>
                                                <th scope="col" width="2%"></th>
                                                <th scope="col" width="12%" style="font-weight: normal;"><span class="ibm-required">**</span>Ticket Type:
                                                </th>
                                                <th scope="col" width="12%"><form:select
                                                        id="primaryTicketType" path="rca.primaryTicketType">
                                                    <form:option value=""></form:option>
                                                    <form:option value="Incident">Incident</form:option>
                                                    <form:option value="Problem">Problem</form:option>
                                                    <form:option value="Change">Change</form:option>
                                                </form:select>
                                                </th>
                                                <th scope="col" width="2%"></th>
                                                <th scope="col" width="12%" style="font-weight: normal;"><span class="ibm-required">**</span>Severity:
                                                </th>
                                                <th scope="col" width="12%"><form:select
                                                        id="severity" path="rca.severity">
                                                    <form:option value="1">1</form:option>
                                                    <form:option value="2">2</form:option>
                                                    <form:option value="3">3</form:option>
                                                    <form:option value="4">4</form:option>
                                                    <form:option value="4">5</form:option>
                                                </form:select></th>
                                                <th scope="col" width="12%" style="font-weight: normal;"><span class="ibm-required">**</span>Associated
                                                    Tool:
                                                </th>

                                                <th scope="col" width="12%">
                                                    <form:select id="primaryTicketAssociation"
                                                                 path="rca.primaryTicketAssociation" onclick="enableOtherAssociatedTool('primaryTicketAssociation');">
                                                        <form:option value=""></form:option>
                                                        <form:option value="i service">i service</form:option>
                                                        <form:option value="AT&T Network">AT&T Network</form:option>
                                                        <form:option value="Atlassian">Atlassian</form:option>
                                                        <form:option value="Cirats">Cirats</form:option>
                                                        <form:option value="Clarfy">Clarify</form:option>
                                                        <form:option value="Codesk Dalian">Codesk Dalian</form:option>
                                                        <form:option value="Codesk Manila">Codesk Manila</form:option>
                                                        <form:option value="EMEA DSR">EMEA DSR</form:option>
                                                        <form:option value="Elixir">Elixir</form:option>
                                                        <form:option value="FiServ">FiServ</form:option>
                                                        <form:option value="GPS Development Lab">GPS Development Lab</form:option>
                                                        <form:option value="Maximo">Maximo</form:option>
                                                        <form:option value="Other">Other</form:option>
                                                    </form:select></th>

                                            </tr>
                                            </thead>
                                        </table>


                                        <table id="other_assoc_tool" summary="Contacts"
                                               class="ibm-results-table" border="0" style="display:none;">
                                            <thead></thead>
                                            <tr>
                                                <th scope="col" align width="20%"
                                                    style="font-weight: normal;"><span class="ibm-required">**</span>
                                                    Other Associated Tool:                                               </th>
                                                <th scope="col" width="80%"><form:textarea
                                                        id="priOtherAssociatedTool"
                                                        path="rca.priOtherAssociatedTool" cols="137" rows="3" maxlength="4999" onpaste="return (this.value.length < 4999);"/></th>
                                            </tr>

                                        </table>


                                        <table id="no_primary_ticket_detail" summary="Contacts"
                                               class="ibm-results-table" border="0" style="display:none;">
                                            <thead></thead>
                                            <tr>
                                                <th scope="col" align width="20%"
                                                    style="font-weight: normal;"><span class="ibm-required">**</span>Explanation for no Primary Ticket
                                                </th>
                                                <th scope="col" width="80%"><form:textarea
                                                        id="noPrimaryTicketExplanation"
                                                        path="rca.noPrimaryTicketExplanation" cols="137" rows="3" maxlength="4999" onpaste="return (this.value.length < 4999);"/></th>

                                            </tr>

                                        </table>

                                        <table id="secondary_tickets" summary="Contacts"
                                               class="ibm-results-table" border="0">
                                            <thead>
                                            <tr>
                                                <th scope="col" align width="19%"
                                                    style="font-weight: normal;"></th>
                                                <th scope="col" width="15%"></th>
                                                <th scope="col" width="2%"></th>
                                                <th scope="col" width="15%" style="font-weight: normal;"></th>
                                                <th scope="col" width="20%"></th>
                                                <th scope="col" width="3%"></th>
                                                <th scope="col" align="centre" width="23%"
                                                    style="font-weight: normal;"></th>
                                                <th scope="col" width="3%"><input type="button"
                                                                                  value="Add Secondary Tickets"
                                                                                  style="height: 30px;"
                                                                                  name="ibm-submit"
                                                                                  class="ibm-btn-cart-sec"
                                                                                  onclick="addSecondaryRcaTicket('<%=basePath%>');">
                                                </th>
                                            </tr>
                                            </thead>
                                        </table>


                                        <div id="rcaTicketsDiv"></div>

                                    </div>


                                    <!-- Start Description -->
                                    <div class="bar-blue-black">Description</div>
                                    <div class="bar-green-black" style="font-weight: normal;">
                                        <table id="rca_closed_date" summary="Contacts" border="0">
                                            <thead>
                                            <tr>
                                                <th width="20%"></th>
                                                <th width="40%"></th>
                                                <th width="20%">Impact Statements</th>
                                                <th width="10%"></th>
                                                <th width="10%"></th>
                                            </tr>
                                            </thead>
                                        </table>
                                    </div>

                                    <div class="lr-mar-10px" id="descriptionDiv">
                                        <table id="Contacts" summary="Contacts"
                                               class="ibm-results-table" border="0">
                                            <thead>

                                            <tr>
                                                <th scope="col" width="22%">
                                                    Detailed Business Impact:
                                                </th>
                                                <th scope="col"><form:textarea id="rca.impactDetails"
                                                                               path="rca.impactDetails" cols="137"
                                                                               rows="7" maxlength="4999" onpaste="return (this.value.length < 4999);"/></th>

                                            </tr>
                                            </thead>
                                        </table>

                                        <table id="Contacts" summary="Contacts"
                                               class="ibm-results-table" border="0">
                                            <thead></thead>
                                            <tbody>
                                            <tr>
                                                <td width="16.5%">
                                                    <span class="ibm-required">*</span>Service(s) Impacted:
                                                </td>
                                                <td width="33.5%">
                                                    <form:input id="serviceImpacted" path="rca.serviceImpacted"
                                                                size="25" maxlength="250"/>
                                                </td>
                                                <td width="20%" style="font-weight:bold;">
                                                    <span class="ibm-required">*</span>Business Impacts in Minutes:
                                                </td>
                                                <td width="30%">
                                                    <form:input id="businessImpactInMins"
                                                                path="rca.businessImpactInMins" size="25" maxlength="250"/>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>


                                        <table id="Contacts" summary="Contacts"
                                               class="ibm-results-table" border="0">
                                            <thead></thead>
                                            <tbody>
                                            <tr>
                                                <td width="16.5%">
                                                    <span class="ibm-required">*</span>System/Server Name:
                                                </td>
                                                <td width="33.5%">
                                                    <form:input id="systemImpacted" path="rca.systemImpacted"
                                                                size="25" maxlength="250"/>
                                                </td>
                                                <td scope="col" width="20%" style="font-weight: normal;">Source
                                                    of Notification:
                                                </td>
                                                <td scope="col" width="30%"><form:select
                                                        id="sourceNotification" path="rca.sourceNotification">
                                                    <form:option value="Monitoring">Monitoring</form:option>
                                                    <form:option value="Customer">Customer</form:option>
                                                </form:select></td>
                                            </tr>
                                            </tbody>
                                        </table>

                                        <table id="Contacts" summary="Contacts"
                                               class="ibm-results-table" border="0">
                                            <thead></thead>
                                            <tbody>
                                            <tr>
                                                <td width="16.5%" style="font-weight:bold;">
                                                    <span class="ibm-required">*</span>Total GPS practitioners impacted and how impacted?:
                                                </td>
                                                <td width="83.5%">
                                                    <form:textarea id="totalGpsPractitionersImpacted"
                                                                   path="rca.totalGpsPractitionersImpacted" cols="137"
                                                                   rows="2" maxlength="249" onpaste="return (this.value.length < 249);"/>
                                                </td>

                                            </tr>
                                            </tbody>
                                        </table>

                                        


                                        <table id="Contacts" summary="Contacts"
                                               class="ibm-results-table" border="0">
                                            <thead>

                                            <tr>
                                                <th scope="col" width="16.5%">
                                                    <span class="ibm-required">*</span>Location of Failure :
                                                </th>
                                                <th scope="col" width="83.5%">
                                                <table id="tab_location_other" summary="Location_other" class="ibm-results-table" border="0">
                                               <tr><th scope="col" ><form:select id="locationOfFailure" path="rca.locationOfFailure" onchange="enabledRcaLocations();">
                                                    <form:option value="" label="Please select the option"/>
                                                    <form:options items="${locationsOfFailure}"/>
                                                </form:select></th>
                                                <th id="loc_other_th1" style="display:none;">Other Location:</th>
                                                <th id="loc_other_th2" style="display:none;"><form:input id="location_other" path="rca.rcaOtherLocation" /></th>
                                                </tr>
                                                </table>
                                                </th>
                                            </tr>


                                            <tr>
                                                <th scope="col" width="16.5%"><span class="ibm-required">*</span>Reason
                                                    RCA Required:
                                                </th>
                                                <th scope="col" width="83.5%">
                                                <table id="rcaReason_outage" summary="rcaReason_outage" class="ibm-results-table" border="0">
                                               <tr><th scope="col" > <form:select id="rcaReason" path="rca.rcaReason"  onchange="enabledRcaReasons();">
                                                    <form:option value="Outage">Outage</form:option>
                                                    <form:option
                                                            value="Performance Degradation">Performance Degradation</form:option>
                                                    <form:option
                                                            value="Multiple Customers Impacted">Multiple Customers Impacted</form:option>
                                                    <form:option
                                                            value="RCA requested by Customer or Mgmt">RCA requested by Customer or Mgmt</form:option>
                                                    <form:option
                                                            value="Chronic / Re-occurring Problem">Chronic / Re-occurring Problem</form:option>
                                                    <form:option value="Failed Change">Failed Change</form:option>
                                                    <form:option value="Other">Other</form:option>
                                                </form:select></th>
                                                <th id="outage_th1" style="display:none;">Outage:</th>
                                                <th id="outage_th2" style="display:none;"><form:input id="rcaReason_outage" path="rca.rcaReasonOutage" /></th>
                                                </tr>
                                                </table>
                                               </th>
                                            </tr>

                                            <tr id="rcaOpeningReasonTr" style="display:none">
                                                <th scope="col" width="30%" style="font-weight: normal;">Reason for
                                                    opening RCA:
                                                </th>
                                                <th scope="col" width="20%"><form:textarea
                                                        id="rcaOpeningReason" path="rca.rcaOpeningReason"
                                                        cols="137" rows="2" maxlength="4999" onpaste="return (this.value.length < 4999);"/></th>
                                            </tr>
                                            <tr id="fccTr" style="display:none;">
                                                <form:hidden id="failedChangeCriteria" path="rca.failedChangeCriteria"/>
                                                <th scope="col" width="30%" style="font-weight: normal;"><span class="ibm-required">*</span>Failed Change
                                                    Criteria:
                                                </th>
                                                <th scope="col" width="20%">
                                                    <select id="fccSelect" onchange="assignFailedCriteria();"
                                                            onselect="assignFailedCriteria();">
                                                        <option value="Backed out">Backed out</option>
                                                        <option value="Unauthorized">Unauthorized</option>
                                                        <option value="Unplanned">Unplanned</option>
                                                        <option value="Unsuccessful">Unsuccessful</option>
                                                    </select>
                                                </th>
                                            </tr>

                                            <tr id="fcilTr" style="display:none;">
                                                <td scope="col" width="40%" style="font-weight: normal;"><span class="ibm-required">*</span>Failed Change
                                                    Impact Level:
                                                </td>
                                                <td scope="col" width="60%" style="font-weight: normal;"><span
                                                        class="ibm-input-group ibm-radio-group"> <form:radiobutton
                                                        id="failedChangeImpactLevel1"
                                                        path="rca.failedChangeImpactLevel" value="BAU"/> BAU
																<form:radiobutton id="failedChangeImpactLevel2"
                                                                                  path="rca.failedChangeImpactLevel"
                                                                                  value="Minor"/>
																Minor <form:radiobutton id="failedChangeImpactLevel3"
                                                                                        path="rca.failedChangeImpactLevel"
                                                                                        value="Medium"/>
																Medium <form:radiobutton id="failedChangeImpactLevel4"
                                                                                         path="rca.failedChangeImpactLevel"
                                                                                         value="Major"/>
																Major <form:radiobutton id="failedChangeImpactLevel5"
                                                                                        path="rca.failedChangeImpactLevel"
                                                                                        value="Critical"/>
																Critical
														</span></td>
                                            </tr>
                                            <tr id="fcaTr" style="display:none;">
                                                <th scope="col" width="30%" style="font-weight: normal;"><span class="ibm-required">*</span>Failed Change
                                                    Assignee:
                                                </th>
                                                <th scope="col" width="20%"><form:input
                                                        id="failedChangeAssignee"
                                                        path="rca.failedChangeAssignee" size="40"/></th>
                                            </tr>
                                            <tr id="fcagTr" style="display:none;">
                                                <th scope="col" width="30%" style="font-weight: normal;"><span class="ibm-required">*</span>Failed Change
                                                    Assignee Group:
                                                </th>
                                                <th scope="col" width="20%"><form:input
                                                        id="failedChangeAssigneeGroup"
                                                        path="rca.failedChangeAssigneeGroup" size="40"/></th>
                                            </tr>
                                            <tr id="otherReasonDetailTr" style="display:none">
                                                <th scope="col" width="30%" style="font-weight: normal;"><span class="ibm-required">*</span>Other Reason
                                                    Details:
                                                </th>
                                                <th scope="col"><form:textarea id="otherReasonDetail" path="rca.otherReasonDetail" cols="137"
                                                                               rows="3" maxlength="4999" onpaste="return (this.value.length < 4999);"/></th>

                                            </tr>


                                            </thead>
                                        </table>


                                    </div>
                                    <!-- End Description Section -->

                                    <br/>

                                    <!-- Start Event Details Secion-->
                                    <div class="bar-blue-black">Event Details</div>
                                    <div class="bar-green-black" style="font-weight: normal;">
                                        <table id="rca_closed_date" summary="Contacts" border="0">
                                            <thead>
                                            <tr>
                                                <th width="20%"></th>
                                                <th width="40%"></th>
                                                <th width="20%">Issue Description</th>
                                                <th width="10%"></th>
                                                <th width="10%"></th>
                                            </tr>
                                            </thead>
                                        </table>
                                    </div>

                                    <div class="lr-mar-10px" id="eventDetailsDiv">
                                        <table id="issueDescription" summary="Contacts"
                                               class="ibm-results-table" border="0">
                                            <thead>
                                            <tr>
                                                <th scope="col" width="30%"><span class="ibm-required">*</span>Issue
                                                    / Incident / Problem Description:
                                                </th>
                                                <th scope="col"><form:textarea id="issueDescription"
                                                                               path="rca.rcaEventDetail.issueDescription"
                                                                               cols="130"
                                                                               rows="7" maxlength="4999" onpaste="return (this.value.length < 4999);"/></th>

                                            </tr>
                                            </thead>
                                        </table>

                                        <table id="chronology" summary="Contacts"
                                               class="ibm-results-table" border="0">
                                            <thead>
                                            <tr>
                                                <th scope="col" width="30%" style="font-weight: normal;"><span class="ibm-required">*</span>Chronology
                                                    of Event:
                                                </th>
                                                <th scope="col"><form:textarea id="issueDescription"
                                                                               path="rca.rcaEventDetail.chronology"
                                                                               cols="130"
                                                                               rows="17" maxlength="4999" onpaste="return (this.value.length < 4999);"/></th>

                                            </tr>
                                            </thead>
                                        </table>

                                        <table id="howServiceRestored" summary="Contacts"
                                               class="ibm-results-table" border="0">
                                            <thead>
                                            <tr>
                                                <th scope="col" width="25%"><span class="ibm-required">*</span>How
                                                    was the service restored:
                                                </th>
                                                <th scope="col"><form:textarea id="issueDescription"
                                                                               path="rca.rcaEventDetail.howServiceRestored"
                                                                               cols="130"
                                                                               rows="7" maxlength="4999" onpaste="return (this.value.length < 4999);"/></th>

                                            </tr>
                                            </thead>
                                        </table>

                                        <table id="exec_alert" summary="Contacts"
                                               class="ibm-results-table" border="0">
                                            <thead>
                                            <tr>
                                                <th scope="col" align width="21.5%"
                                                    style="font-weight: normal;">
                                                    <span class="ibm-required">*</span>Is Executive Alert (EA) on time/needed?
                                                    (on time within 1 hours of incident occurred):
                                                </th>
                                                <th scope="col" width="22%"><form:select id="executiveAlert"
                                                                                         path="rca.rcaEventDetail.executiveAlert">
                                                    <form:option value=""></form:option>
                                                    <form:option
                                                            value="Yes - Within 1 hour">Yes - With in 1 hour</form:option>
                                                    <form:option
                                                            value="Yes - After 1 hour">Yes - After 1 hour</form:option>
                                                    <form:option
                                                            value="No - But was required">No - But was required</form:option>
                                                    <form:option
                                                            value="No - Not required">No - Not required</form:option>
                                                </form:select></th>
                                                <th scope="col" width="7%"></th>
                                                <th scope="col" width="15%" style="font-weight: normal;">
                                                    <span class="ibm-required">*</span>Impacted Tower:
                                                </th>
                                                <th scope="col" width="10%">
                                                    <form:select id="impactedTower"
                                                                 path="rca.rcaEventDetail.impactedTower"
                                                                 style="width:170px;">
                                                        <form:option value="" label="Please select the option"/>
                                                        <form:options items="${impactedTowers}"/>>

                                                    </form:select></th>
                                                <th scope="col" width="2%"></th>
                                                <th scope="col" align="right" width="4%"
                                                    style="font-weight: normal;"></th>
                                                <th scope="col" align="right" width="18.5%"></th>
                                            </tr>

                                            </thead>
                                        </table>

                                        <table id="repeatIssueTab" summary="Contacts"
                                               class="ibm-results-table" border="0">
                                            <thead>
                                            <tr>
                                                <th scope="col" width="20.5%" style="font-weight: normal;"><span class="ibm-required">*</span>Was
                                                    this a repeat issue:
                                                </th>
                                                <th scope="col"><form:select id="repeatIssue"
                                                                             path="rca.rcaEventDetail.repeatIssue" onchange="enableRepeatRcaNum();">
                                                    <form:option value=""></form:option>
                                                    <form:option value="Repeat">Repeat--Occurred at least twice before</form:option>
                                                    <form:option value="Yes">Yes--occurred once before</form:option>
                                                    <form:option value="No">No--Has not occurred</form:option>
                                                </form:select></th>

                                            </tr>
                                            </thead>
                                        </table>

                                        <table id="repeatRcaNumber" summary="Contacts"
                                               class="ibm-results-table" border="0" style="display: none">
                                            <thead>
                                            <tr>
                                                <th scope="col" width="20%" style="font-weight: normal;">
                                                    <span class="ibm-required">**</span>RCA/Record/Ticket Number:
                                                </th>
                                                <th scope="col"><form:input id="repeatRcaOrTicketNum"
                                                                            path="rca.rcaEventDetail.repeatRcaOrTicketNum" size="40"/></th>
                                            </tr>
                                            </thead>
                                        </table>

                                        <table id="repeatRisk" summary="Contacts"
                                               class="ibm-results-table" border="0">
                                            <thead>
                                            <tr>
                                                <th scope="col" width="20.5%" style="font-weight: normal;"><span class="ibm-required">*</span>How
                                                    high is the risk for this issue to re-occur?:
                                                </th>
                                                <th scope="col"><form:select id="repeatRisk"
                                                                             path="rca.rcaEventDetail.repeatRisk">
                                                    <form:option value="High">High</form:option>
                                                    <form:option value="Medium">Medium</form:option>
                                                    <form:option value="Low">Low</form:option>
                                                </form:select></th>
                                            </tr>
                                            </thead>
                                        </table>


                                        <table id="keyIssues" summary="Contacts"
                                               class="ibm-results-table" border="0">
                                            <thead>
                                            <tr>
                                                <th scope="col" width="20.5%" style="font-weight: normal;">Key Issues
                                                    and Questions:
                                                </th>
                                                <th scope="col"><form:textarea
                                                        id="keyIssues"
                                                        path="rca.rcaEventDetail.keyIssues"
                                                        cols="130" rows="7" maxlength="4999" onpaste="return (this.value.length < 4999);"/></th>

                                            </tr>
                                            </thead>
                                        </table>

                                        <table id="additionalCommentIbm" summary="Contacts"
                                               class="ibm-results-table" border="0">
                                            <thead>
                                            <tr>
                                                <th scope="col" width="24%" class="bar-blue-med-light"><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The
                                                    5 Whys</label></th>
                                                <th width="1"></th>
                                                <th scope="col" width="75%" class="bar-blue-med-light"
                                                    style="text-align: centre;">&nbsp;&nbsp;&nbsp;Description
                                                </th>

                                            </tr>
                                            <tr>
                                                <th scope="col" width="24%" style="font-weight: normal;"><span class="ibm-required">*</span>1.
                                                    Why did this problem occur?
                                                </th>
                                                <th width="1"></th>
                                                <th scope="col" width="75%"><form:textarea
                                                        id="whyProblem" path="rca.rcaEventDetail.whyProblem"
                                                        cols="122" rows="2" maxlength="4999" onpaste="return (this.value.length < 4999);"/></th>
                                            </tr>
                                            <tr>
                                                <th scope="col" width="24%" style="font-weight: normal;"><span class="ibm-required">*</span>2.
                                                    Why did this #1 occur?
                                                </th>
                                                <th width="1"></th>
                                                <th scope="col" width="75%"><form:textarea id="why1"
                                                                                           path="rca.rcaEventDetail.why1"
                                                                                           cols="122" rows="2" maxlength="4999" onpaste="return (this.value.length < 4999);"/></th>
                                            </tr>
                                            <tr>
                                                <th scope="col" width="24%" style="font-weight: normal;"><span class="ibm-required">*</span>3.
                                                    Why did this #2 occur?
                                                </th>
                                                <th width="1"></th>
                                                <th scope="col" width="75%"><form:textarea id="why2"
                                                                                           path="rca.rcaEventDetail.why2"
                                                                                           cols="122" rows="2" maxlength="4999" onpaste="return (this.value.length < 4999);"/></th>
                                            </tr>
                                            <tr>
                                                <th scope="col" width="24%" style="font-weight: normal;"><span class="ibm-required">*</span>4.
                                                    Why did this #3 occur?
                                                </th>
                                                <th width="1"></th>
                                                <th scope="col" width="75%"><form:textarea id="why3"
                                                                                           path="rca.rcaEventDetail.why3"
                                                                                           cols="122" rows="2" maxlength="4999" onpaste="return (this.value.length < 4999);"/></th>
                                            </tr>
                                            <tr>
                                                <th scope="col" width="24%" style="font-weight: normal;"><span class="ibm-required">*</span>5.
                                                    Why did this #4 occur?
                                                </th>
                                                <th width="1"></th>
                                                <th scope="col" width="75%"><form:textarea id="why4"
                                                                                           path="rca.rcaEventDetail.why4"
                                                                                           cols="122" rows="2" maxlength="4999" onpaste="return (this.value.length < 4999);"/></th>
                                            </tr>
                                            </thead>
                                        </table>


                                        <table id="additionalCommentIbm" summary="Contacts"
                                               class="ibm-results-table" border="0">
                                            <thead>
                                            <tr>
                                                <th>
                                                    <div class="bar-green-black-small"
                                                         style="font-weight: normal; height: 23px;">
                                                        <table id="rca_closed_date" summary="Contacts"
                                                               border="0">
                                                            <thead>
                                                            <tr>
                                                                <th width="20%"></th>
                                                                <th width="40%"></th>
                                                                <th width="20%">Root Cause Summary</th>
                                                                <th width="10%"></th>
                                                                <th width="10%"></th>
                                                            </tr>
                                                            </thead>
                                                        </table>
                                                    </div>
                                                </th>
                                            </tr>
                                            </thead>
                                        </table>

                                        <table id="executiveSummary" summary="Contacts"
                                               class="ibm-results-table" border="0">
                                            <thead>
                                            <tr>
                                                <th scope="col" width="24%" style="font-weight: normal;"><span class="ibm-required">*</span>Root Cause
                                                    Summary:
                                                </th>
                                                <th width="1"></th>
                                                <th scope="col" width="75%"><form:textarea
                                                        id="executiveSummary"
                                                        path="rca.rcaEventDetail.executiveSummary" cols="122"
                                                        rows="8" maxlength="4999" onpaste="return (this.value.length < 4999);"/></th>
                                            </tr>
                                            </thead>
                                        </table>


                                        <table id="additionalCommentIbm" summary="Contacts"
                                               class="ibm-results-table" border="0">
                                            <thead>
                                            <tr>
                                                <th>
                                                    <div class="bar-blue-med-light"
                                                         style="font-weight: normal; height: 23px;">
                                                        <table id="rca_closed_date" summary="Contacts"
                                                               border="0">
                                                            <thead>
                                                            <tr>
                                                                <th width="20%"></th>
                                                                <th width="40%"></th>
                                                                <th width="20%">Root Cause Description</th>
                                                                <th width="10%"></th>
                                                                <th width="10%"></th>
                                                            </tr>
                                                            </thead>
                                                        </table>
                                                    </div>
                                                </th>
                                            </tr>

                                            </thead>
                                        </table>


                                        <!----------*** Begin Root Cause Description ***---------->
                                        <table id="rca_number" summary="Contacts"
                                               class="ibm-results-table" border="0">
                                            <thead>
                                            <tr>
                                                <form:hidden id="rca.primaryRcaCause.rcaCauseId"
                                                             path="rca.primaryRcaCause.rcaCauseId"/>

                                                <th scope="col" align width="35%">
                                                    Select a contracted Service Provider if it contributed to the
                                                    incident:
                                                </th>
                                                <th scope="col" width="25%">
                                                    <form:select id="rcaReason" path="rca.primaryRcaCause.serviceProvider" style="bottom: 100%;">
                                                        <form:option value="AMS">AMS</form:option>
                                                        <form:option value="AOD">AOD</form:option>
                                                        <form:option value="Aspect">Aspect</form:option>
                                                        <form:option value="ATT">ATT</form:option>
                                                        <form:option value="ATT Network">ATT Network</form:option>
                                                        <form:option value="Atlassian">Atlassian</form:option>
                                                        <form:option value="Autonomy">Autonomy</form:option>
                                                        <form:option value="AVAYA">AVAYA</form:option>
                                                        <form:option value="Bell Canada">Bell Canada</form:option>
                                                        <form:option value="Bharti">Bharti</form:option>
                                                        <form:option value="Bluecoat">Bluecoat</form:option>
                                                        <form:option value="British Telecom">British Telecom</form:option>
                                                        <form:option value="BT">BT</form:option>
                                                        <form:option value="C4">C4</form:option>
                                                        <form:option value="China Telecom">China Telecom</form:option>
                                                        <form:option value="Cisco">Cisco</form:option>
                                                        <form:option value="eBHS">eBHS</form:option>
                                                        <form:option value="Erricson">Erricson</form:option>
                                                        <form:option value="F5">F5</form:option>
                                                        <form:option value="FiServ">FiServ</form:option>
                                                        <form:option value="Genesys">Genesys</form:option>
                                                        <form:option value="Global Asset Delivery">Global Asset Delivery</form:option>
                                                        <form:option value="Global Business Services">Global Business Services</form:option>
                                                        <form:option value="Globe">Globe</form:option>
                                                        <form:option value="GPS Development Lab">GPS Development Lab</form:option>
                                                        <form:option value="Hubwoo">Hubwoo</form:option>
                                                        <form:option value="IBM Product Support">IBM Product Support</form:option>
                                                        <form:option value="IEX/WFM (India)">IEX/WFM (India)</form:option>
                                                        <form:option value="IGA">IGA</form:option>
                                                        <form:option value="Innove">Innove</form:option>
                                                        <form:option value="Iron Mountain">Iron Mountain</form:option>
                                                        <form:option value="Managed Services Delivery">Managed Services Delivery</form:option>
                                                        <form:option value="Masergy">Masergy</form:option>
                                                        <form:option value="Nortel">Nortel</form:option>
                                                        <form:option value="Novatel">Novatel</form:option>
                                                        <form:option value="Oracle">Oracle</form:option>
                                                        <form:option value="Reliance">Reliance</form:option>
                                                        <form:option value="Siemens">Siemens</form:option>
                                                        <form:option value="Sprint">Sprint</form:option>
                                                        <form:option value="Stategic Outsourcing">Stategic Outsourcing</form:option>
                                                        <form:option value="SWG IVR Development">SWG IVR Development</form:option>
                                                        <form:option value="Tata">Tata</form:option>
                                                        <form:option value="Telstra">Telstra</form:option>
                                                        <form:option value="Tulsa Help Desk">Tulsa Help Desk</form:option>
                                                        <form:option value="Verizon">Verizon</form:option>
                                                        <form:option value="Virtella">Virtella</form:option>
                                                        <form:option value="Wipro">Wipro</form:option>
                                                        <form:option value="Xerox">Xerox</form:option>
                                                        <form:option value="Other">Other</form:option>
                                                    </form:select>
                                                </th>

                                                <th scope="col" width="20%" style="font-weight: normal;">Other Service
                                                    Provider:
                                                </th>
                                                <th scope="col" width="20%"><form:input
                                                        path="rca.primaryRcaCause.otherServiceProvider" size="30"/></th>
                                            </tr>
                                            </thead>
                                        </table>

                                        <table id="service_description" summary="Contacts"
                                               class="ibm-results-table" border="0">
                                            <thead>
                                            <tr>
                                                <th scope="col" align width="15%"
                                                    style="font-weight: normal;"><span class="ibm-required">*</span>Outage Category:
                                                </th>
                                                <th scope="col" width="15%"><form:select
                                                        path="rca.primaryRcaCause.outageCategory" id="outageCategory"
                                                        onchange="getOutageSubCategories('${basePath}','outageCategory','outageSubCategory');"
                                                        onselect="getOutageSubCategories('${basePath}','outageCategory','outageSubCategory');">
                                                    <form:option value="" label="Please select the option"/>
                                                    <form:options items="${outageCategories}"
                                                                  itemValue="serviceDescriptionId"
                                                                  itemLabel="serviceDescriptionName"/>
                                                </form:select></th>
                                                <th scope="col" width="5%"></th>
                                                <th scope="col" width="15%"><span class="ibm-required">*</span>Location of
                                                    Business Impact:
                                                </th>
                                                <th scope="col" width="15%">
                                                    <form:select path="rca.primaryRcaCause.locationOfBusinessImpact"
                                                                 id="locationOfBusinessImpact">
                                                        <form:option value="" label="Please select the option"/>
                                                        <form:options items="${locationsOfBusiness}"/>
                                                    </form:select></th>
                                                <th scope="col" width="5%"></th>
                                                <th scope="col" align="centre" width="15%"
                                                    style="font-weight: normal;">Other Location of Business Impact:
                                                </th>
                                                <th scope="col" width="15%"><form:input
                                                        path="rca.primaryRcaCause.otherLocOfBusinessImpact"
                                                        id="otherLocOfBusinessImpact" size="25"/></th>
                                            </tr>
                                            </thead>
                                        </table>

                                        <table id="cause_description" summary="Contacts"
                                               class="ibm-results-table" border="0">
                                            <thead>
                                            <tr>
                                                <th scope="col" align width="14.5%"><span class="ibm-required">*</span>Sub Category:
                                                </th>
                                                <th scope="col" width="15%"><form:select
                                                        path="rca.primaryRcaCause.outageSubCategory"
                                                        id="outageSubCategory"
                                                        onchange="getOutageSubCategories2('${basePath}','outageSubCategory','outageSubCategory2');"
                                                        onselect="getOutageSubCategories2('${basePath}','outageSubCategory','outageSubCategory2');">
                                                    <form:option value="" label="Please select the option"/>
                                                    <form:options items="${outageSubCategories}" itemValue="serviceDescriptionId" itemLabel="serviceDescriptionName"/>
                                                </form:select></th>
                                                <th scope="col" width="5%"></th>
                                                <th scope="col" width="14.5%"><span class="ibm-required">*</span> Sub Category 2:
                                                </th>
                                                <th scope="col" width="15.5%"><form:select path="rca.primaryRcaCause.outageSubCategory2" id="outageSubCategory2" onchange="enableOtherSubCat2();">
                                                    <form:option value="" label="Please select the option"/>
                                                    <form:options items="${outageSubCategories2}" itemValue="serviceDescriptionName" itemLabel="serviceDescriptionName"/>
                                                </form:select></th>
                                                <th scope="col" width="5%"></th>
                                                <th scope="col" id="other_SubCat2_1" align="centre" style="display: none; font-weight: normal;"> Other subcategory 2:</th>
                                                <th scope="col" id="other_SubCat2_2" style="display: none; "><form:input path="rca.primaryRcaCause.otherOutageSubCat2" id="RcaOtherOutageSubCat2" size="25"/></th>
                                            </tr>
                                            <tr>
                                                <th scope="col" align="centre" style="font-weight: normal;"> Location of System:</th>
                                                <th scope="col" ><form:input path="rca.primaryRcaCause.locOfSystem" id="rca.primaryRcaCause.locOfSystem" size="25"/></th>
                                            </tr>
                                            </thead>
                                        </table>

                                        <table id="cause_description" summary="Contacts"
                                               class="ibm-results-table" border="0">
                                            <thead>
                                            <tr>
                                                <th scope="col" align width="15%"
                                                    style="font-weight:normal;"><span class="ibm-required">*</span>Root Cause:
                                                </th>
                                                <th scope="col" width="15%"><form:select
                                                        path="rca.primaryRcaCause.rootOrContributingCause"
                                                        id="rootOrContributingCause"
                                                        onchange="getCauses('${basePath}','rootOrContributingCause','causeCategory'); resetCauseGuidence('#causeSelectionGuidance');"
                                                        onselect="getCauses('${basePath}','rootOrContributingCause','causeCategory');">
                                                    <form:option value="" label="Please select the option"/>
                                                    <form:options items="${rootCauses}"
                                                                  itemValue="causeDescriptionName"
                                                                  itemLabel="causeDescriptionName"/>
                                                </form:select></th>
                                                <th scope="col" width="5%"></th>
                                                <th scope="col" width="14%" style="font-weight:normal;"><span class="ibm-required">*</span>Cause
                                                    Category:
                                                </th>
                                                <th scope="col" width="18%"><form:select
                                                        path="rca.primaryRcaCause.causeCategory" id="causeCategory"
                                                        onchange="getCauses('${basePath}','causeCategory','causeSubCategory'); resetCauseGuidence('#causeSelectionGuidance');"
                                                        onselect="getCauses('${basePath}','causeCategory','causeSubCategory'); resetCauseGuidence('#causeSelectionGuidance');">
                                                    <form:option value="" label="Please select the option"/>
                                                    <form:options items="${causeCategories}"
                                                                  itemValue="causeDescriptionName"
                                                                  itemLabel="causeDescriptionName"/>
                                                </form:select></th>
                                                <th scope="col" width="3%"></th>
                                                <th scope="col" align="centre" width="15%"
                                                    style="font-weight: normal;">Cause Subcategory:
                                                </th>
                                                <th scope="col" width="15%"><form:select
                                                        path="rca.primaryRcaCause.causeSubCategory"
                                                        id="causeSubCategory" onchange="setCauseGuidence('causeCategory','causeSubCategory','#causeSelectionGuidance');"
                                                        onselect="setCauseGuidence('causeCategory','causeSubCategory','#causeSelectionGuidance');">
                                                    <form:option value="" label="Please select the option"/>
                                                    <form:options items="${causeSubCategories}"
                                                                  itemValue="causeDescriptionName"
                                                                  itemLabel="causeDescriptionName"/>
                                                </form:select></th>
                                            </tr>
                                            </thead>
                                        </table>

                                        <table id="service_description" summary="Contacts"
                                               class="ibm-results-table" border="0">
                                            <thead>
                                            <tr>

                                                <th scope="col" align="centre" width="13%"
                                                    style="font-weight: normal;">Guidance for Cause selection:
                                                </th>
                                                <th scope="col" width="87%"><form:textarea
                                                        id="causeSelectionGuidance"
                                                        path="rca.primaryRcaCause.causeSelectionGuidance" cols="143"
                                                        rows="3" readonly="true" style="background: #fafafa" maxlength="4999" onpaste="return (this.value.length < 4999);"/></th>
                                            </tr>
                                            </thead>
                                        </table>

                                        <!----------*** Begin Root Cause Description ***---------->


                                        <!----------*** Begin Contributing Causes ***---------->
                                        <table id="additionalCommentIbm" summary="Contacts"
                                               class="ibm-results-table" border="0">
                                            <thead>
                                            <tr>
                                                <th>
                                                    <div class="bar-blue-med-light"
                                                         style="font-weight: normal; height: 23px;">
                                                        <table id="rca_closed_date" summary="Contacts"
                                                               border="0">
                                                            <thead>
                                                            <tr>
                                                                <th width="20%"></th>
                                                                <th width="40%"></th>
                                                                <th width="20%">Contributing Causes</th>
                                                                <th width="10%"></th>
                                                                <th width="10%"></th>
                                                            </tr>
                                                            </thead>
                                                        </table>
                                                    </div>
                                                </th>
                                            </tr>
                                            </thead>
                                        </table>

                                        <table id="secondary_tickets" summary="Contacts"
                                               class="ibm-results-table" border="0">
                                            <thead>
                                            <tr>
                                                <th scope="col" align width="19%"
                                                    style="font-weight: normal;"></th>
                                                <th scope="col" width="15%"></th>
                                                <th scope="col" width="2%"></th>
                                                <th scope="col" width="15%" style="font-weight: normal;"></th>
                                                <th scope="col" width="20%"></th>
                                                <th scope="col" width="3%"></th>
                                                <th scope="col" align="centre" width="23%"
                                                    style="font-weight: normal;"></th>
                                                <th scope="col" width="3%"><input type="button"
                                                                                  value="Add Contributing Cause"
                                                                                  style="height: 30px;"
                                                                                  name="ibm-submit"
                                                                                  class="ibm-btn-cart-sec"
                                                                                  onclick="addRCAContributingCause('${basePath}');">
                                                </th>
                                            </tr>
                                            </thead>
                                        </table>

                                        <div id="contributingCauseDiv"></div>
                                        <!--*** Begin Additional Supporting Information ***-->
                                        <table id="additionalCommentIbm" summary="Contacts"
                                               class="ibm-results-table" border="0">
                                            <thead>
                                            <tr>
                                                <th>
                                                    <div class="bar-green-black-small"
                                                         style="font-weight: normal; height: 23px;">
                                                        <table id="rca_closed_date" summary="Contacts"
                                                               border="0">
                                                            <thead>
                                                            <tr>
                                                                <th width="20%"></th>
                                                                <th width="30%"></th>
                                                                <th width="30%">Additional Supporting
                                                                    Information
                                                                </th>
                                                                <th width="10%"></th>
                                                                <th width="10%"></th>
                                                            </tr>
                                                            </thead>
                                                        </table>
                                                    </div>
                                                </th>
                                            </tr>
                                            </thead>
                                        </table>

                                        <table class="ibm-results-table"
                                               summary="additional support info">
                                            <thead>
                                            <tr>
                                                <td width="100%"><label for="file"
                                                                        style="font-weight: normal; white-space: nowrap;">Click
                                                    browse to upload files. Maximum size allowed is 10MB.</label></td>
                                            </tr>
                                            </thead>
                                        </table>
                                        <table class="ibm-results-table" summary="file">
                                            <thead>
                                            <tr>
                                                <th scope="col" style="font-weight: normal;" width="5%"><input
                                                        id="file" type="file" name="file"
                                                        onchange="enableRcaFileDescription();"/></th>
                                                <th scope="col" width="10%"></th>
                                                <td width="10%" id="descTd" style="display: none;">Description:
                                                </td>
                                                <td width="75%"><input type="text" id="description"
                                                                       style="display: none;" size="30"/></td>
                                            </tr>
                                            </thead>
                                        </table>

                                        <!--*** End Additional Supporting Information ***-->
                                        <c:if test="${not empty rcaForm.supportingFiles}">

                                            <div class="lr-mar-10px">

                                                <table id="additionalCommentIbm" summary="Contacts"
                                                       class="ibm-results-table" border="0">
                                                    <thead>
                                                    <tr>
                                                        <th>
                                                            <div class="bar-blue-med-light"
                                                                 style="font-weight: normal; height: 23px;">
                                                                <table id="rca_closed_date" summary="Contacts"
                                                                       border="0">
                                                                    <thead>
                                                                    <tr>
                                                                        <th width="20%">Uploaded Files</th>
                                                                        <th width="40%"></th>
                                                                        <th width="20%"></th>
                                                                        <th width="10%"></th>
                                                                        <th width="10%"></th>
                                                                    </tr>
                                                                    </thead>
                                                                </table>
                                                            </div>
                                                        </th>
                                                    </tr>

                                                    </thead>
                                                </table>

                                                <table class="ibm-data-table" summary="file name">
                                                    <thead>

                                                    <tr></tr>

                                                    <tr style="">
                                                        <th class="bar-gray-light-test" scope="col"
                                                            align="center" width="15%">File Name
                                                        </th>

                                                        <th class="bar-gray-light-test" scope="col"
                                                            align="center" width="23%">Date
                                                        </th>

                                                        <th class="bar-gray-light-test" scope="col"
                                                            align="center" width="24%">Description
                                                        </th>

                                                        <th class="bar-gray-light-test" scope="col"
                                                            align="center" width="20%">Uploaded By
                                                        </th>

                                                        <th class="bar-gray-light-test" scope="col"
                                                            align="center" width="21%"></th>

                                                    </tr>

                                                    </thead>
                                                    <c:forEach items="${rcaForm.supportingFiles}" var="file"
                                                               varStatus="loop">
                                                        <c:if test="${file.name != null || fn:trim(file.name) != ''}">
                                                            <form:hidden
                                                                    path="supportingFiles[${loop.index}].fileId"/>
                                                            <form:hidden
                                                                    path="supportingFiles[${loop.index}].name"/>
                                                            <form:hidden
                                                                    path="supportingFiles[${loop.index}].type"/>
                                                            <form:hidden
                                                                    path="supportingFiles[${loop.index}].saveDateTime"/>
                                                            <form:hidden
                                                                    path="supportingFiles[${loop.index}].isDeleted"
                                                                    id="isDeleted_${loop.index}"/>
                                                            <tr id="rcaFile_${loop.index}">
                                                                <td width="15%"><a class="ibm-download-link"
                                                                                   href="getRcaFile.htm?rcaFileId=${file.fileId}"
                                                                                   target="_blank">${file.name}</a></td>

                                                                <td width="20%">${file.saveDateTime}</td>

                                                                <td width="23%">${file.description}</td>

                                                                <td width="23%">${file.uploadedBy.notesId}</td>

                                                                <td width="21%"><a class="ibm-delete-link"
                                                                                   href="javascript:void(0)"
                                                                                   onclick="hide('rcaFile_${loop.index}'); setIsRcaFileDelete('${loop.index}');"
                                                                                   alt="Delete">Delete</a></td>
                                                            </tr>
                                                        </c:if>
                                                    </c:forEach>

                                                </table>
                                            </div>

                                        </c:if>


                                    </div>
                                    <!-- End Event Details Section -->


                                    <!-- Start Action Items Sections -->
                                    <div class="bar-blue-black">Action Items</div>
                                    <div class="lr-mar-10px">
                                        <table id="action_items" summary="action_items"
                                               class="ibm-results-table" border="0">
                                            <thead>
                                            <tr>
                                                <th scope="col" align width="19%"
                                                    style="font-weight: normal;"></th>
                                                <th scope="col" width="15%"></th>
                                                <th scope="col" width="2%"></th>
                                                <th scope="col" width="15%" style="font-weight: normal;"></th>
                                                <th scope="col" width="20%"></th>
                                                <th scope="col" width="3%"></th>
                                                <th scope="col" align="centre" width="23%"
                                                    style="font-weight: normal;"></th>
                                                <th scope="col" width="3%"><input type="button"
                                                                                  value="Add Action"
                                                                                  style="height: 30px;"
                                                                                  name="ibm-submit"
                                                                                  class="ibm-btn-cart-sec"
                                                                                  onclick="addRcaAction('<%=basePath%>');">
                                                </th>
                                            </tr>
                                            </thead>
                                        </table>


                                        <div id="actionItemsDiv"></div>

                                    </div>


                                </div>
                                <!-- End Action Items Sections -->

                                <div class="bar-blue-black">Future Prevention and Lessons Learned</div>
                                <div class="bar-green-black" style="font-weight: normal;">
                                    <table id="rca_closed_date" summary="Contacts" border="0">
                                        <thead>
                                        <tr>
                                            <th width="20%"></th>
                                            <th width="40%"></th>
                                            <th width="20%">Future Prevention</th>
                                            <th width="10%"></th>
                                            <th width="10%"></th>
                                        </tr>
                                        </thead>
                                    </table>
                                </div>
                                <table id="action_items" summary="action_items"
                                       class="ibm-results-table" border="0">
                                    <thead>
                                    <tr>
                                        <th scope="col" align width="37%">
                                            <br/><br/><span class="ibm-required">*</span>
                                            Please click on Add Action button above to create & assign Actions for each
                                            root cause and contributing cause. If no action items identified, please
                                            provide explanation in this field:
                                        </th>
                                        <th width="3%"></th>
                                        <th scope="col" width="60%">
                                            <form:textarea
                                                    id="futurePrevention"
                                                    path="rca.rcaPreventionDetail.futurePrevention"
                                                    cols="100" rows="6" maxlength="4999" onpaste="return (this.value.length < 4999);"/>
                                        </th>
                                    </tr>
                                    </tr>
                                    </thead>
                                </table>

                                <div class="bar-green-black" style="font-weight: normal;">
                                    <table id="rca_closed_date" summary="Contacts" border="0">
                                        <thead>
                                        <tr>
                                            <th width="20%"></th>
                                            <th width="32%"></th>
                                            <th width="28%">Lessons Learned Checklist</th>
                                            <th width="10%"></th>
                                            <th width="10%"></th>
                                        </tr>
                                        </thead>
                                    </table>
                                </div>
                                <table id="incident_details" summary="Contacts"
                                       class="ibm-results-table" border="0">
                                    <thead></thead>
                                    <tbody>
                                    <tr>
                                        <td scope="col" width="53%" style="font-weight:normal;">
                                            <br/><br/><br/>

                                            <span class="ibm-required">**</span>New Policies or procedures Implemented? (Including updates to
                                            documentation) to prevent recurrence?:
											<span class="ibm-input-group ibm-radio-group">
												<form:radiobutton id="newPoliciesImplementedY"
                                                                  path="rca.rcaPreventionDetail.newPoliciesImplemented"
                                                                  value="Y"
                                                />
												Yes
												<form:radiobutton id="newPoliciesImplementedN"
                                                                  path="rca.rcaPreventionDetail.newPoliciesImplemented"
                                                                  value="N"
                                                />
												No
											</span>
                                        </td>
                                        <td width="2%"></td>
                                        <td width="5%"> <br/><br/><br/><br/> <span class="ibm-required">*</span>Comments:</td>
                                        <td scope="col" width="40%" style="font-weight:normal;">
                                                <form:textarea
                                                        id="comments" path="rca.rcaPreventionDetail.comments"
                                                        cols="80" rows="6" maxlength="4999" onpaste="return (this.value.length < 4999);"/>

                                        </th>
                                        </td>

                                    </tr>
                                    </tbody>
                                </table>


                                <!-- Start Approval/Reviews Section -->
                                <div class="bar-blue-black">Approvals / Reviews:</div>
                                <div class="lr-mar-10px" id="approvalsDiv">
                                    <table id="action_items" summary="action_items"
                                           class="ibm-results-table" border="0">
                                        <thead>
                                        <tr>
                                            <th scope="col" align width="19%"
                                                style="font-weight: normal;">Approvals / Reviews:
                                            </th>
                                            <th scope="col" width="81%"><form:textarea
                                                    id="approvalsOrReviews" path="rca.approvalsOrReviews"
                                                    cols="137" rows="6" maxlength="4999" onpaste="return (this.value.length < 4999);"/></th>
                                        </tr>
                                        </thead>
                                    </table>

                                    <table id="action_items" summary="action_items"
                                           class="ibm-results-table" border="0">
                                        <thead>
                                        <tr>
                                            <th scope="col" align width="19%"
                                                style="font-weight: normal;">Notes and Escalations:
                                            </th>
                                            <th scope="col" width="81%"><form:textarea
                                                    id="notesAndEscalations" path="rca.notesAndEscalations"
                                                    cols="137" rows="6" maxlength="4999" onpaste="return (this.value.length < 4999);"/></th>
                                        </tr>
                                        </thead>
                                    </table>


                                </div>


                            </div>
                            <!-- End Approval/Reviews Section -->


                            <!-- Start Document Event History Log -->
                            <div class="bar-blue-black">
                                <input type="checkbox" id="historyLogCheckBox"
                                       name="historyLogCheckBox" onclick="showOrHideHistoryLog();">&nbsp;Document
                                Event History Log
                            </div>
                            <div class="lr-mar-10px">

                                <div id="historyLogDiv" style="display: none"></div>

                            </div>
                        </div>
                        <!-- End Document Event History Log -->


                        <!-- Start Form Action -->
                        <div class="bar-blue-black">Form Actions</div>
                        <div id="coordAprDiv" class="lr-mar-10px" style="display:none;">
                            <table id="Contacts" summary="Contacts"
                                   class="ibm-results-table" border="0">
                                <thead></thead>
                                <tbody>
                                <tr>
                                    <td width="25%">
                                        RCA Approved by RCA Coordinator:
                                    <td>
                                    <td width="5%">
                                        <input type="checkbox" path="rca.hasCoordinatorApproved" id="hasCoordinatorApprovedBox"
                                               value="" onclick="setHasCoordinatorApproved();"/>
                                        <form:hidden id="hasCoordinatorApproved" path="rca.hasCoordinatorApproved"/>
                                    </td>
                                    <td width="70%"></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                        <div class="lr-mar-10px">
                            <input type="button" value="Back" style="height: 30px;" name="ibm-back"
                                   class="ibm-btn-cart-sec" onclick="setFormAction('back');"/>
                            <span class="ibm-sep">&nbsp;</span>
                            <input type="button" value="Save" style="height: 30px;" id="ibm-save" name="ibm-save"
                                   onclick="setFormAction('save');" class="ibm-btn-cart-sec">
                            <span id="submit-span" class="ibm-sep">&nbsp;</span>
                            <input type="button" value="Submit" style="height: 30px;" id="ibm-submit" name="ibm-submit"
                                   onclick="setFormAction('submit');" class="ibm-btn-cart-sec">
                            <span id="approve-span" class="ibm-sep">&nbsp;</span>
                            <input type="button" value="Approve" style="height: 30px;" id="ibm-approve"
                                   name="ibm-approve" onclick="showRcaApproveBox();" class="ibm-btn-cart-sec">
                            <span id="reject-span" class="ibm-sep">&nbsp;</span>
                            <input type="button" value="Reject" style="height: 30px;" id="ibm-reject" name="ibm-reject"
                                   onclick="showRejectRcaBox();" class="ibm-btn-cart-sec">
                            <span id="close-span" class="ibm-sep">&nbsp;</span>
                            <input type="button" value="Close" style="height: 30px;" id="ibm-close" name="ibm-close"
                                   onclick="setFormAction('close');" class="ibm-btn-cart-sec">
                            <span id="reopen-span" class="ibm-sep">&nbsp;</span>
                            <input type="button" value="Reopen" style="height: 30px;"
                                   id="ibm-reopen" name="ibm-reopen"
                                   onclick="showReopenBox();" class="ibm-btn-cart-sec">
                            <span id="cancel-span" class="ibm-sep">&nbsp;</span>
                            <input type="button" value="Cancel RCA" style="height: 30px;" id="ibm-cancel"
                                   name="ibm-cancel" onclick="showCancelRcaBox();" class="ibm-btn-cart-sec">
                        </div>


                    </div>
                    <!-- End Form Action -->


                </div>
            </div>
            </form:form>

        </div>
    </div>
</div>

</div>
</div>


</div>
</div>


<!-- Approve RCA -->
<div class="ibm-common-overlay ibm-overlay-alt" id="approve-rca-box">
    <div class="ibm-head">
        <p>
            <a class="ibm-common-overlay-close" href="#close">Close [x]</a>
        </p>
    </div>
    <div class="ibm-body">
        <div class="ibm-main">
            <div class="ibm-title">
                <h2> <span class="ibm-required">*</span>Approval Comments</h2>
            </div>
            <div class="ibm-overlay-rule">
                <hr/>
            </div>
            <div class="ibm-container ibm-alternate">
                <div class="ibm-container-body">
                    <p>
                        <tr>
                            <td>
                                <table class="ibm-results-table" width="100%" cellspacing="1"
                                       cellpadding="1" style="margin-bottom: 10px;"
                                       summary="The following table shows the labels of profile listing">
                                    <tbody>
                                    <thead>
                                    <span class="ibm-required">*</span>&nbsp;<form:textarea id="approvalComments"
                                                                                            path="rcaForm.rcaFormActionsHelper.approvalComments" cols="68"
                                                                                            rows="3" style="font-size: 12px;" align="right" maxlength="4999" onpaste="return (this.value.length < 4999);"/>
                                    </thead>
                                    </tbody>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <div class="ibm-overlay-rule">
                                <hr/>
                            </div>
                            <div class="ibm-buttons-row">
                    <p>
                        <input value="Submit" id="approve-submit-btn" type="submit"
                               name="ibm-submit" class="ibm-btn-arrow-pri"
                               onclick="approveRcaForm();"/> <span class="ibm-sep">&nbsp;</span>
                        <input value="Cancel" id="create-rca-cancel-btn" type="button"
                               name="ibm-cancel" class="ibm-btn-cancel-sec"
                               onclick="ibmweb.overlay.hide('approve-rca-box');"/>
                    </p>
                </div>
                </tr>
                </p>
            </div>
        </div>
    </div>
</div>
<div class="ibm-footer"></div>

</div>


<!-- Reject RCA -->
<div class="ibm-common-overlay ibm-overlay-alt" id="reject-rca-box">
    <div class="ibm-head">
        <p>
            <a class="ibm-common-overlay-close" href="#close">Close [x]</a>
        </p>
    </div>
    <div class="ibm-body">
        <div class="ibm-main">
            <div class="ibm-title">
                <h2>Rejection Comments</h2>
            </div>
            <div class="ibm-overlay-rule">
                <hr/>
            </div>
            <div class="ibm-container ibm-alternate">
                <div class="ibm-container-body">
                    <p>
                        <tr>
                            <td>
                                <table class="ibm-results-table" width="100%" cellspacing="1"
                                       cellpadding="1" style="margin-bottom: 10px;"
                                       summary="The following table shows the labels of profile listing">
                                    <tbody>
                                    <thead>
                                    <form:textarea id="rejectionComments"
                                                   path="rcaForm.rcaFormActionsHelper.rejectionComments"
                                                   cols="70" rows="3" style="font-size: 12px;" maxlength="4999" onpaste="return (this.value.length < 4999);"/>
                                    </thead>
                                    </tbody>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <div class="ibm-overlay-rule">
                                <hr/>
                            </div>
                            <div class="ibm-buttons-row">
                    <p>
                        <input value="Submit" id="approve-submit-btn" type="submit"
                               name="ibm-submit" class="ibm-btn-arrow-pri"
                               onclick="rejectRcaForm();"/> <span class="ibm-sep">&nbsp;</span>
                        <input value="Cancel" id="create-rca-cancel-btn" type="button"
                               name="ibm-cancel" class="ibm-btn-cancel-sec"
                               onclick="ibmweb.overlay.hide('reject-rca-box');"/>
                    </p>
                </div>
                </tr>
                </p>
            </div>
        </div>
    </div>
</div>
<div class="ibm-footer"></div>
</div>

<!-- Cancel RCA -->
<div class="ibm-common-overlay ibm-overlay-alt" id="cancel-rca-box">
    <div class="ibm-head">
        <p>
            <a class="ibm-common-overlay-close" href="#close">Close [x]</a>
        </p>
    </div>
    <div class="ibm-body">
        <div class="ibm-main">
            <div class="ibm-title">
                <h2>Cancellation Comments</h2>
            </div>
            <div class="ibm-overlay-rule">
                <hr/>
            </div>
            <div class="ibm-container ibm-alternate">
                <div class="ibm-container-body">
                    <p>
                        <tr>
                            <td>
                                <table class="ibm-results-table" width="100%" cellspacing="1"
                                       cellpadding="1" style="margin-bottom: 10px;"
                                       summary="The following table shows the labels of profile listing">
                                    <tbody>
                                    <thead>
                                    <form:textarea id="cancellationComments"
                                                   path="rcaForm.rcaFormActionsHelper.cancellationComments"
                                                   cols="70" rows="3" style="font-size: 12px;"/>
                                    </thead>
                                    </tbody>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <div class="ibm-overlay-rule">
                                <hr/>
                            </div>
                            <div class="ibm-buttons-row">
                    <p>
                        <input value="Submit" id="cancel-submit-btn" type="submit"
                               name="ibm-submit" class="ibm-btn-arrow-pri"
                               onclick="cancelRcaForm();"/> <span class="ibm-sep">&nbsp;</span>
                        <input value="Cancel" id="create-rca-cancel-btn" type="button"
                               name="ibm-cancel" class="ibm-btn-cancel-sec"
                               onclick="ibmweb.overlay.hide('reject-rca-box');"/>
                    </p>
                </div>
                </tr>
                </p>
            </div>
        </div>
    </div>
</div>
<div class="ibm-footer"></div>
</div>


<!-- Reopen RCA -->
<div class="ibm-common-overlay ibm-overlay-alt" id="reopen-rca-box">
    <div class="ibm-head">
        <p>
            <a class="ibm-common-overlay-close" href="#close">Close [x]</a>
        </p>
    </div>
    <div class="ibm-body">
        <div class="ibm-main">
            <div class="ibm-title">
                <h2>Reopen Comments</h2>
            </div>
            <div class="ibm-overlay-rule">
                <hr/>
            </div>
            <div class="ibm-container ibm-alternate">
                <div class="ibm-container-body">
                    <p>
                        <tr>
                            <td>
                                <table class="ibm-results-table" width="100%" cellspacing="1"
                                       cellpadding="1" style="margin-bottom: 10px;"
                                       summary="The following table shows the labels of profile listing">
                                    <tbody>
                                    <thead>
                                    <form:textarea id="reopenComments"
                                                   path="rcaForm.rcaFormActionsHelper.reopenComments"
                                                   cols="70" rows="3" style="font-size: 12px;" maxlength="4999" onpaste="return (this.value.length < 4999);"/>
                                    </thead>
                                    </tbody>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <div class="ibm-overlay-rule">
                                <hr/>
                            </div>
                            <div class="ibm-buttons-row">
                    <p>
                        <input value="Submit" id="reopen-submit-btn" type="submit"
                               name="ibm-submit" class="ibm-btn-arrow-pri"
                               onclick="reopenRcaForm();"/> <span class="ibm-sep">&nbsp;</span>
                        <input value="Cancel" id="reopen-rca-cancel-btn" type="button"
                               name="ibm-cancel" class="ibm-btn-cancel-sec"
                               onclick="ibmweb.overlay.hide('reopen-rca-box');"/>
                    </p>
                </div>
                </tr>
                </p>
            </div>
        </div>
    </div>
</div>
<div class="ibm-footer"></div>
</div>


<script type="text/javascript" src="js/rca-1.0.js"> // </script>
<script src="js/jquery.js"></script>
<script src="js/jquery.datetimepicker.js"></script>
<script>
    $('#incidentStartTime').datetimepicker({
        dayOfWeekStart: 1,
        lang: 'en',
        format: 'Y-m-d H:i',
        formatDate: 'Y.m.d',
        formatTime: 'H:i',
        step: 5
    });


    $('#incidentEntTime').datetimepicker({
        dayOfWeekStart: 1,
        lang: 'en',
        format: 'Y-m-d H:i',
        formatDate: 'Y.m.d',
        formatTime: 'H:i',
        step: 5
    });

    $('#dueDate').datetimepicker({
        dayOfWeekStart: 1,
        lang: 'en',
        format: 'd-M-Y',
        formatDate: 'Y.m.d',
        timepicker: false
    });

</script>
<script>
    function ibmFaces(){
        ta1 = FacesTypeAhead.init(
                dojo.query(".typeahead"),
                {
                    key: "typeahead_demo;webahead@us.ibm.com",
                    faces: {
                        headerLabel: "People",

                        onclick: function(person) {
                            return person['notes-id'];
                        }
                    }
                });
    }
</script>

<script type="text/javascript">
    loadRcaFormDetails("<%=basePath%>");
</script>