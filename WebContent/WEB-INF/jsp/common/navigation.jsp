<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<c:set var="accessLevel" scope="request" value="${userSession.accessLevel}"/>
<c:set var="accessLevelTypes" scope="request" value="${userSession.accessLevelTypes}"/>
<c:set var="userRoles" scope="request" value="${userSession.userRoles}"/>
<c:set var="isAdmin" scope="request" value="${userSession.isAdmin}"/>


<!-- CONTENT_NAV_BEGIN -->
<div id="ibm-content-nav">

<div id="ibm-primary-tabs" role="navigation" aria-label="Primary tabs">
<ul class="ibm-tabs" style="font-size: 0.87em">
<!--	<c:if test="${accessLevel == 32 || fn:contains(accessLevelTypes, 'profile')}">
	<li id="profileTab" role="presentation"><a href="contracts.htm" role="button">Profiles</a></li>
    </c:if> -->
    <c:if test="${ (not empty userRoles) && (fn:contains(userRoles, 'dpe') || fn:contains(userRoles, 'coordinator')
    || fn:contains(userRoles, 'delegate')  || fn:contains(userRoles, 'editor')  || fn:contains(userRoles, 'owner')
    || fn:contains(userRoles, 'creator') || fn:contains(userRoles, 'reader') || isAdmin == 'true' )}">
	<li id="rcaTab" role="presentation"><a href="rcas.htm" role="button">RCAs/Actions</a></li>
	</c:if>
	<c:if test="${(not empty userRoles) &&  (fn:contains(userRoles, 'reportAdmin') || isAdmin == 'true')}">
    	<li id="reportsTab" role="presentation"><a href="reports.htm" role="button">Reports</a></li>
    </c:if>
	<li id="supportTab" role="presentation"><a href="support.htm" role="button">Support</a></li>
	<c:if test="${not empty userRoles && isAdmin == 'true'}">
	  		<li id="adminTab" role="presentation"><a href="admin.htm" role="button">Admin</a></li>
	</c:if>
</ul>
</div>

<div id="ibm-secondary-tabs" role="navigation" aria-label="Secondary tabs">
<ul class="ibm-tabs" id="supporSubtTab" style="display:none" >
<li id="supportListTab" class="ibm-active"><a href="support.htm">In Progress</a></li>
<li id="archiveTab"><a href="archive.htm">Archive</a></li>
</ul>

<ul class="ibm-tabs" id="adminSubTabs" style="display:none" >
<li id="adminMainTab" class="ibm-active"><a href="admin.htm">main</a></li>
<li id="profileAddContractTab"><a href="showAddContract.htm">Add Account</a></li>
<li id="profileModifyDeleteTab"><a href="modifyDeleteContracts.htm">Modify/Delete Accounts</a></li>
<!--<li id="adminACLTab"><a href="addACL.htm?acl_id=">ACL</a></li>-->
<li id="adminUserRoleTab"><a href="addUserRole.htm?ur_id=">User Roles</a></li>

</ul>
</div>
</div>
