<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<div>
	<div class="bar-green-black" style="width: 100%">Add Logon / ACL entry</div>
	<form:hidden path="acl.aclId" id="sAclId" />
	<table class="ibm-results-table" summary="single contract">
	<thead>
		<tr>
			<th scope="col"><label for="sEmail"> Intranet Id * :</label></th>
			<th scope="col" style="padding-bottom: 5px"><form:input
					path="acl.gpsUser.email" id="sEmail" /></th>
		</tr>
		<tr>
			<th scope="col"><label for="sFormType">Questionnaire Title:</label></th>
			<th scope="col" style="padding-bottom: 5px"><form:select path="acl.formType" id="sFormType" onchange="enableOrDisableSingleAwaitingAccess();">
					<form:option value="Profile">Profile</form:option>
					<form:option value="RCA">RCA</form:option>
				</form:select></th>
		</tr>
		<tr>
			<th scope="col"><label for="sContractId">Contract:</label></th>
			<th scope="col" style="padding-bottom: 5px"><form:select path="acl.contract.contractId"  id="sContractId">
					<form:options items="${aCLForm.contracts}" itemValue="contractId" itemLabel="title" />
				</form:select></th>
		</tr>
		<tr>
			<th scope="col"><label for="sActiveLevel">Active Access Level:</label></th>
			<th scope="col" style="padding-bottom: 5px"><form:select path="acl.activeAccessLevel" id="sActiveLevel">
					<form:option value="-1">No Access</form:option>
					<form:option value="1">Read</form:option>
					<form:option value="2">Save</form:option>
					<form:option value="4">Submit</form:option>
					<form:option value="8">Approve/Reject</form:option>
					<form:option value="16">Account Admin</form:option>
					<form:option value="32">Full Admin</form:option>
				</form:select></th>
		</tr>

		<tr id="sAwaitingLevelTr" style="display:none;">
			<th scope="col"><label for="sAwaitingLevel">Awaiting Access Level:</label></th>
			<th scope="col" style="padding-bottom: 5px"><form:select path="acl.awaitingAccessLevel" id="sAwaitingLevel">
					<form:option value="-1">No Access</form:option>
					<form:option value="1">Read</form:option>
					<form:option value="2">Save</form:option>
					<form:option value="4">Submit</form:option>
					<form:option value="8">Approve/Reject</form:option>
					<form:option value="16">Account Admin</form:option>
					<form:option value="32">Full Admin</form:option>
				</form:select></th>
		</tr>



		<tr>
			<th scope="col"><label for="sApprovedLevel">Approved Access Level:</label></th>
			<th scope="col" style="padding-bottom: 5px"><form:select
					path="acl.approvedAccessLevel" id="sApprovedLevel">
					<form:option value="-1">No Access</form:option>
					<form:option value="1">Read</form:option>
					<form:option value="2">Save</form:option>
					<form:option value="4">Submit</form:option>
					<form:option value="8">Approve/Reject</form:option>
					<form:option value="16">Account Admin</form:option>
					<form:option value="32">Full Admin</form:option>
				</form:select></th>
		</tr>

		<tr>
			<td></td>
			<td style="padding-bottom: 5px"></td>
		</tr>

		<tr>
			<td colspan="2" style="padding-bottom: 5px">
				<input value="Save Acl" id="create-submit-btn" type="button" name="ibm-submit" class="ibm-btn-arrow-pri" onClick="addSingleAclEntry();" />
			</td>
		</tr>
    </thead>
	</table>

	


</div>
