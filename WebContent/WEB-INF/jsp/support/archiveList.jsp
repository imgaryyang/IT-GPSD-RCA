<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

 <Head>
<style type="text/css" media="all">
.bar-green-black	{background:#666666; color:#fff; padding:.3em .5em; font-size:1.1em; font-weight:bold;}
.bar-gray-light	{background:#eeeeee; color:#000; padding:.3em .5em; font-size:1.1em; font-weight:bold;}
.bar-gray-light-test	{background:#eee; color:#000; padding:.3em .5em; font-size:1.0em;}
</style>
</Head>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<table cellspacing="0" cellpadding="0" border="0" id="archiveData"
	summary="archived contract requests" class="ibm-data-table">
	<thead>
		<tr>
			<th scope="col">Form Name</th>
			<th scope="col">Submit Date</th>
			<th scope="col">Status</th>
			<th scope="col">Submitter</th>
		</tr>
	</thead>

	<tbody>
		<c:forEach items="${archiveList}" var="reqAct">
			<tr>
				<td scope="row"><c:if test="${reqAct.requestType == 'New Contract'}">
						<a href="newContract.htm?action=sh&requestId=${reqAct.requestId}">Add New Contract</a>
					</c:if> <c:if test="${reqAct.requestType == 'Modify Contract'}">
						<a href="modifyContract.htm?action=sh&requestId=${reqAct.requestId}">Modify Existing Contract</a>
					</c:if>
					<c:if test="${reqAct.requestType == 'Access Request'}">
						<a href="accessRequest.htm?action=sh&requestId=${reqAct.requestId}">RCA Access Request</a>
					</c:if>
					<c:if test="${reqAct.requestType == 'Remove Request'}">
                       <a href="removeAccessRequest.htm?action=sh&requestId=${reqAct.requestId}">Remove Access Request</a>
                    </c:if>
					</td>
				<td scope="row">${reqAct.dateRequested}</td>
				<td scope="row">
					<c:if test="${reqAct.status == '8'}">Rejected</c:if>
					<c:if test="${reqAct.status == '2'}">Approved</c:if></td>
				<td scope="row">${reqAct.requestedBy}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>