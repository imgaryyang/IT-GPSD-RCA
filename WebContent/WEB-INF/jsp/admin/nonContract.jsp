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
<table class="ibm-results-table" summary="ACLs">
<thead>
<tr>
	<th scope="row">
		<div class="bar-blue-black">Add Global ACL</div>
	</th>
</tr>

<tr>
	<th scope="row">
		<form:hidden path="group.aclId" id="gAclId" />
		<table class="ibm-results-table" summary="ACLs">
		 <thead>
			<tr>
				<th scope="col"><label for="gEmail">*Users Intranet Ids:</label></th>
				<th scope="col" style="padding-bottom: 5px"><form:input path="group.gpsUser.email" id="gEmail" /></th>
			</tr>

			
			<tr>
				<th scope="col"><label for="gFormType">Questionnaire Title:</label></th>
				<th scope="col" style="padding-bottom: 5px"><form:select path="group.formType" id="gFormType" onchange="enableOrDisableGroupAwaitingAccess();">
						<form:option value="Profile">Profile</form:option>
						<form:option value="RCA">RCA</form:option>
					</form:select></th>
			</tr>



			<tr>
				<th scope="col"><label for="gActiveLevel">Active Access Level:</label></th>
				<th scope="col" style="padding-bottom: 5px">
					<form:select path="group.activeAccessLevel" id="gActiveLevel">
						<form:option value="-1">No Access</form:option>
                    	<form:option value="0">Public Read</form:option>
                    	<form:option value="1">Read</form:option>
                    	<form:option value="2">Save</form:option>
                    	<form:option value="4">Submit</form:option>
                    	<form:option value="8">Approve/Reject</form:option>
                    	<form:option value="16">Account Admin</form:option>
                    	<form:option value="32">Full Admin</form:option>
					</form:select>
				</th>
			</tr>

				<tr id="gAwaitingLevelTr" style="display:none;">
                        	<th scope="col"><label for="gAwaitingLevel">Awaiting Access Level:</label></th>
                        	<th scope="col" style="padding-bottom: 5px"><form:select path="group.awaitingAccessLevel" id="gAwaitingLevel">
                        					<form:option value="-1">No Access</form:option>
                        					<form:option value="0">Public Read</form:option>
                        					<form:option value="1">Read</form:option>
                        					<form:option value="2">Save</form:option>
                        					<form:option value="4">Submit</form:option>
                        					<form:option value="8">Approve/Reject</form:option>
                        					<form:option value="16">Account Admin</form:option>
                        					<form:option value="32">Full Admin</form:option>
                        				</form:select></th>
                        </tr>


			<tr>
				<th scope="col"><label for="gApprovedLevel">Approved Access Level:</label></th>
				<th scope="col" style="padding-bottom: 5px">
				<form:select path="group.approvedAccessLevel" id="gApprovedLevel">
					<form:option value="-1">No Access</form:option>
                					<form:option value="0">Public Read</form:option>
                					<form:option value="1">Read</form:option>
                					<form:option value="2">Save</form:option>
                					<form:option value="4">Submit</form:option>
                					<form:option value="8">Approve/Reject</form:option>
                					<form:option value="16">Account Admin</form:option>
                					<form:option value="32">Full Admin</form:option></form:select>
				</th>
			</tr>

			<tr>
				<th scope="row" colspan="2" style="padding-bottom: 5px">
				<div class="ibm-buttons-row"> 
				<input value="Save Acl" id="nonContract-submit-btn" type="button" name="nonContract-ibm-submit" class="ibm-btn-arrow-pri" onClick="addNonContractAclEntry();" />
                </div>   				
				</th>
			</tr>
		  </thead>
		</table>
	</th>
	</tr>
 </thead>
</table>