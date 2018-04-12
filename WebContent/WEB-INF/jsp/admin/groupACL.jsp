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
		<div class="bar-blue-black">Add Group</div>
	</th>
</tr>

<tr>
	<th scope="row">
		<table class="ibm-results-table" summary="ACLs">
		 <thead>
			<tr>
				<th scope="col"><label for="groupName">Name: &nbsp;&nbsp;&nbsp; </label></th>
				<th scope="col" style="padding-bottom: 5px" colspan="2"><form:input path="groupName" size="35" /></th>
			</tr>
			
			<tr>
				<th scope="col">&nbsp;</th>
				<th scope="col" >&nbsp;</th>
				<th scope="col">&nbsp;</th>
			</tr>
			
			<tr>
				<th scope="row" colspan="3">Select Users from the Left List to add to Group.</th>
			</tr>
			
			<tr>
				<th scope="col">&nbsp;</th>
				<th scope="col">&nbsp;</th>
				<th scope="col">&nbsp;</th>
			</tr>

			<tr>
				<th scope="col"><label for="email_list" style="disabled:none">Emails</label><form:select path="" multiple="true" size="10" id="email_list" name="email_list">
						<form:options items="${emails}"  itemValue="userId" itemLabel="email"  />
					</form:select></th>
					<td>
					    <div class="ibm-buttons-row"> 
						<div><input type="button" onclick="moveValues('email_list', 'group_emails')" value=" >> " name="agAddUser" class="ibm-btn-pri" ></div>
						<div><input type="button" onclick="moveValues('group_emails', 'email_list')" value=" << " name="agRemoveUser" class="ibm-btn-pri" ></div>
						</div>
					</td>

			</tr>
			</thead>
		</table>
	</th>
</tr>

<tr>
	<th scope="row" style="padding-bottom: 5px">
	<div class="ibm-buttons-row"> 
		<input value="Save group" id="user-group-submit-btn" type="button" name="ibm-submit" class="ibm-btn-arrow-pri" onClick="addACLEntry();" />
	
	</th>
</tr>
</thead>
</table>