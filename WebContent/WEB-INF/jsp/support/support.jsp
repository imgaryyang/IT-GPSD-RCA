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
<c:set var="userRoles" scope="request" value="${userSession.roles}"/>
<c:set var="isAdmin" scope="request" value="${userSession.isAdmin}"/>

<style type="text/css" media="all">
.bar-green-dark {
	background: #007670;
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
	background: #000;
	color: #fff;
	padding: .3em .5em;
	font-size: 1.1em;
	font-weight: bold;
}

.bar-green-black {
	background: #007670;
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
						<div id="ibm-mapping-app" class="ibm-container main">
							<div class="ibm-container-descs">
								<div id="main-content-top" class="main-content-top-head">
									<div id="main-content-bar" class="columns">
										<div id="msgDiv" align="center" style="margin-bottom: 1px;"></div>
										<div id="main-content-title" style="float: left;">
											<div id="body" class="ibm-container-body; sol-bor-1px">
												<div id="clientInformationDiv">
													<div class="bar-blue-black">Available Request Forms</div>
												</div>
												<br>

												<table class="ibm-results-table" width="90%"
													summary="form names">
													<thead>
														<tr>
															<th scope="col" width="40%" style="padding-right: 5px"><div
																	class="bar-green-black">Form Name [Language]</div></th>
															<th scope="col" width="20%" style="margin: 5px"><div
																	class="bar-green-black">Languages</div></th>
															<th scope="col" width="30%" style="padding-left: 5px"><div
																	class="bar-green-black">Description</div></th>
														</tr>
													</thead>
												</table>

												<table class="ibm-results-table" width="90%"
													summary="form names">
													<thead>
													<!--	<tr>
															<th scope="row" width="40%" style="padding-right: 5px"><a
																href="newContract.htm">Add New Account[en]</a></th>
															<th scope="row" width="20%" style="margin: 5px"></th>
															<th scope="row" width="30%" style="padding-left: 5px">Add
																a new account in the system.</th>
														</tr>
																<tr>
																<th scope="row" width="40%" style="padding-right: 5px"><a href="modifyContract.htm">Modify Existing Contract[en]</a></th>
																<th scope="row" width="20%" style="margin: 5px"></th>
																<th scope="row" width="30%" style="padding-left: 5px">Modify an already existing contract in the system.</th>
															</tr>
															 -->
															<tr>
																<th scope="row" style="padding-right: 5px"><a href="accessRequest.htm">RCA Access Request[en]</a></th>
																<th scope="row" width="20%" style="margin: 5px"></th>
																<th scope="row" width="30%" style="padding-left: 5px">To gain access on a specific contract or request access for the team such as DPE, Coordinator, Delegate, Editor etc.</th>
															</tr> 												
													<!--
													 <tr>
																<th scope="row" style="padding-right: 5px"><a href="removeAccessRequest.htm">Remove Access Request[en]</a></th>
																<th scope="row" width="20%" style="margin: 5px"></th>
																<th scope="row" width="30%" style="padding-left: 5px">Remove Access Request.</th>
															</tr>
													-->
													 </thead>
												</table>
												<br>
												<c:if test="${fn:length(awaiting) gt 0}">
													<div class="bar-green-black">Forms Awaiting Approval</div>
													<table cellspacing="0" cellpadding="0" border="0"
														id="contractDataWithId" summary="contract requests"
														class="ibm-data-table">
														<thead>
															<tr>
																<th scope="col">ID</th>
																<th scope="col">Form Name</th>
																<th scope="col">Submitter</th>
																<th scope="col">Submit Date</th>
																<th scope="col">Status</th>
																<th scope="col">Form Action</th>
															</tr>
														</thead>

														<tbody>
															<c:forEach items="${awaiting}" var="contractRequest">
																<tr>
																	<td scope="row">${contractRequest.requestId}</td>
																	<td scope="row"><c:if test="${contractRequest.requestType == 'New Contract'}">
																			<a href="newContract.htm?action=sh&requestId=${contractRequest.requestId}">Add	New GPSDRCA Account</a>
																		</c:if> <c:if
																			test="${contractRequest.requestType == 'Modify Contract'}">
																			<a href="modifyContract.htm?action=sh&requestId=${contractRequest.requestId}">Modify Existing Account</a>
																		</c:if> 
																		<c:if test="${contractRequest.requestType == 'Access Request'}">
																			<a href="accessRequest.htm?action=sh&requestId=${contractRequest.requestId}">RCA Access Request</a>
																		</c:if>
																		<c:if test="${contractRequest.requestType == 'Remove Request'}">
                                                                        <a href="removeAccessRequest.htm?action=sh&requestId=${contractRequest.requestId}">Remove Access Request</a>
                                                                        </c:if>

																		</td>
																	<td scope="row">${contractRequest.requestedBy}</td>
																	<td scope="row">${contractRequest.dateRequested}</td>
																	<td scope="row">
																	    <c:if test="${contractRequest.status == '0'}">Awaiting</c:if>
																	    <c:if test="${contractRequest.status == '8'}">Requires changes</c:if>
																	</td>
																	<td scope="row"><c:if
																			test="${contractRequest.status == '0' || contractRequest.status == '8'}">
																			<a href="#"
																				onclick="cancelRequest('${contractRequest.requestId}');">Cancel
																				Request</a>
																		</c:if></td>

																</tr>
															</c:forEach>
														</tbody>
													</table>
												</c:if>
												<c:if test="${isAdmin == 'true' }">
													<br>
													<div class="bar-green-black">Forms Requiring Action</div>
													<table cellspacing="0" cellpadding="0" border="0"
														id="contractData" summary="contract requests"
														class="ibm-data-table">
														<thead>
															<tr>
																<th scope="col">ID</th>
																<th scope="col">Form Name</th>
																<th scope="col">Submit Date</th>
																<th scope="col">Status</th>
																<th scope="col">Submitter</th>
															</tr>
														</thead>

														<tbody>
															<c:forEach items="${requiringAction}" var="reqAct">
																<tr>
																<td scope="row">${reqAct.requestId}</td>
																	<td scope="row">
																			<c:if test="${reqAct.requestType == 'New Contract'}">
																				<a href="newContract.htm?action=rev&requestId=${reqAct.requestId}">Add New Account[en]</a>
																			</c:if> <c:if test="${reqAct.requestType == 'Modify Contract'}">
																				<a href="modifyContract.htm?action=rev&requestId=${reqAct.requestId}">Modify Existing Account</a>
																			</c:if> 
																			<c:if test="${reqAct.requestType == 'Access Request'}">
																				<a href="accessRequest.htm?action=rev&requestId=${reqAct.requestId}">RCA Access Request</a>
																			</c:if>
																			<c:if
	                                                                        	test="${reqAct.requestType == 'Remove Request'}">
	                                                                        	<a	href="removeAccessRequest.htm?action=rev&requestId=${reqAct.requestId}">Remove Access Request</a>
	                                                                        </c:if>
																		</td>
																	<td scope="row">${reqAct.dateRequested}</td>
																	<td scope="row"><c:if test="${reqAct.status == '0'}">Awaiting</c:if>
																		<c:if test="${reqAct.status == '2'}">Approved</c:if>
																	</td>
																	<td scope="row">${reqAct.requestedBy}</td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</c:if>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>


<script type="text/javascript">
dojo.replaceClass("supportTab", "ibm-active");
dojo.byId("supporSubtTab").style.display = 'block';
dojo.replaceClass("supportListTab", "ibm-active");

function cancelRequest(requestId){
	console.log("cancelRequest()........"+requestId);
	if(requestId != ''){
		var URL = "<%=basePath%>/cancelRequest.htm?requestId="+requestId;
		console.log("loading........"+URL);
		window.location.href = URL;
	}
}

function putArchive(requestId){
	console.log("cancelRequest()........"+requestId);
	if(requestId != ''){
		var URL = "<%=basePath%>/markArchive.htm?requestId="+requestId;
		console.log("loading........"+URL);
		window.location.href = URL;
	}
}
</script>

