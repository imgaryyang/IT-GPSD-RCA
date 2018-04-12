<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="userRoles" scope="request" value="${userSession.roles}"/>
<c:set var="isAdmin" scope="request" value="${userSession.isAdmin}"/>
<c:set var="email" scope="request" value="${userSession.gpsUser.email}"/>
<!--<style type="text/css" media="all">
.lr-mar-10px {margin-left: 10px; margin-right:10px; margin-bottom: 2px; margin-top: 2px }
</style> -->

<!-- <script type="text/javascript" src="js/rca-1.0.js"> // </script> -->
<script type="text/javascript" src="js/jquery-ui-1.10.3.custom.js">// </script>
<script type="text/javascript" src="js/jquery-1.9.1.js">// </script>
<link rel="stylesheet" href="css/jquery-ui-1.10.3.custom.css">
<script>
$.noConflict();
// Code that uses other library's $ can follow here.
</script>
<form:form commandName="searchFilter" method="POST"
		   id="searchFilterForm" action="rcaList.htm">
	<div id="pii" style="display: none">
		<p style="display: none">The fields indicated with an asterisk (*)
			are required to complete this transaction; other fields are optional.
			If you do not want to provide us with the required information,
			please use the "Back" button on your browser to return to the
			previous page, or close the window or browser session that is
			displaying this page.</p>
	</div>
	<fieldset style="border: 1px solid #ccc;" class="ibm-container-body">
		<legend id="fieldset-legend">
			<span><h3>Filter Criteria</h3></span>
		</legend>
		<div id="searchFilters-main">
			<div style="padding: 0 5px 0 5px;" role="region"
				 aria-label="form input">

				<form:hidden path="pagination.pageNumber" id="page" />
				<form:hidden path="pagingAction" id="pagingAction" />
				<table width="100%" cellspacing="4" cellpadding="4" id="filterTab"
					   class="ibm-results-table" role="presentation">
					<tbody>
					<thead>
					<tr>
						<td>
							<table class="ibm-results-table" width="100%" cellspacing="1"
								   cellpadding="1" style="margin-bottom: 10px;"
								   summary="The following table shows the labels of profile listing">
								<tbody>
								<thead>


								<tr>

									<th width="25%" scope="col"><span> <label
											for="month"><h5>Month</h5></label>
												<form:select path="month" id="month" onchange="resetRCAFilter('${basePath}')" >
													<form:option value="" label="--Select month--" />
													<form:options items="${referenceData.monthList}" />
												</form:select>
											</span></th>

									<th width="25%" align="left" scope="col"><span>
													<label for="year"><h5>Year</h5></label> <form:select
											path="year" id="year">
										<form:option value="" label="--Select Year--" />
										<form:options items="${referenceData.yearList}" />
									</form:select>

											</span></th>

									<th width="25%"></th>
									<th width="25%"></th>
								</thead>
								</tbody>
							</table>
						</td>

					</tr>


					<tr>
						<td>
							<table class="ibm-results-table" width="100%" cellspacing="1"
								   cellpadding="1" style="margin-bottom: 10px;"
								   summary="The following table shows the labels of profile listing">
								<tbody>
								<thead>

								<th align="left" scope="col" width="25%"><span> <label
										for="contract">Account Name</label><br /> <form:select
										path="contract" cssStyle="background: white;" id="contract">
									<form:option value="" label="All Contracts" />
									<form:options items="${referenceData.listRcaContracts}"
												  itemValue="title" itemLabel="title" />
								</form:select>
										</span></th>

								<th align="center" scope="col" width="25%"><span>
												<label for="contract">RCA/Action Number</label><br /> <form:select
										path="rcaOrActionNumber" cssStyle="background: white;"
										id="rcaOrActionNumber">
									<form:option value="" label="All" />
									<form:options items="${referenceData.listRcasAndActions}"
												  itemValue="numberAndType" itemLabel="rcaOrActionNumber" />
								</form:select>
										</span></th>


								<th align="center" scope="col" width="25%"><span>
												<label for="contract">RCA Coordinator</label><br /> <form:select
										path="rcaCoordinator" cssStyle="background: white;"
										id="rcaCoordinator">
									<form:option value="" label="All" />
									<form:options items="${referenceData.listRcaCoordinators}" />
								</form:select>
										</span></th>

								<th align="center" scope="col" width="25%"><span>
												<label for="contract">RCA Owner</label><br /> <form:select
										path="rcaOwner" cssStyle="background: white;" id="rcaOwner">
									<form:option value="" label="All" />
									<form:options items="${referenceData.rcaOwners}" />
								</form:select>
										</span></th>


								</thead>
								</tbody>
							</table>
						</td>

					</tr>


					<tr>

						<td>
							<table class="ibm-results-table" width="100%" cellspacing="1"
								   cellpadding="1" style="margin-bottom: 10px;"
								   summary="The following table shows the labels of profile listing">
								<tbody>
								<thead>


								<th align="center" scope="col" width="25%"><span>
												<label for="contract">RCA Delegate</label><br /> <form:select
										path="rcaDelegate" cssStyle="background: white;"
										id="rcaDelegate">
									<form:option value="" label="All" />
									<form:options items="${referenceData.rcaDelegates}" />
								</form:select>
										</span></th>

								<th align="left" scope="col" width="25%"><span> <label
										for="stage">Stage</label><br /> <form:select path="stage"
																					 cssStyle="background: white;" id="stage">
									<form:option value="" label="All" />
									<form:option value="Open" label="Open" />
									<form:option value="Awaiting" label="Awaiting" />
									<form:option value="Approved" label="Approved" />
									<form:option value="Active" label="Active" />
									<form:option value="Closed" label="Closed" />
									<form:option value="Cancelled" label="Cancelled" />

								</form:select>
										</span></th>

								<th align="left" scope="col" width="25%"><span> <label
										for="formType">Type</label><br /> <form:select
										path="formType" cssStyle="background: white;" id="formType">
									<form:option value="" label="All" />
									<form:option value="rca" label="RCAs" />
									<form:option value="action" label="Actions" />

								</form:select>
										</span></th>

								<th width="25%"><span> <label for="primaryTicket">Primary Ticket</label><br />
												<form:select path="primaryTicket" cssStyle="background: white;"
															 id="primaryTicket">
													<form:option value="" label="All" />
													<form:options items="${referenceData.primaryTickets}" />
												</form:select>
										</span></th>

								</thead>
								</tbody>
							</table>
						</td>

					</tr>

					</thead>
					</tbody>
				</table>
				</td>
				</tr>


				<tr>
					<td>
						<table class="ibm-results-table" width="100%" cellspacing="2"
							   cellpadding="2"
							   summary="The following table shows the buttons for profile listing">
							<tbody>

							<tr height="5">
							</tr>
							<tr>
								<th width="30%" align="left" scope="row"></th>
								<th width="20%" align="left" scope="row">
									<!--<input type="hidden" id="" value="Y" name="">--> <input
										type="button" value="Apply" tabindex="5" name="ibm-submit"
										class="ibm-btn-cart-sec" onclick="initiaePagination(); getRCAs();">&nbsp;
								</th>
								<th width="20%" align="left" scope="row"><br /> <a
										class="ibm-reset-link" name="ibm-cancel" tabindex="6"
										id="ibm-cancel" href="javascript:void(0)"
										onclick="clearFilters();">Reset</a></th>
								<th width="30%" align="left" scope="row"></th>
							</tr>


							</tbody>
						</table>

						<table class="ibm-results-table" width="100%" cellspacing="2"
							   cellpadding="2"
							   summary="The following table shows the buttons for profile listing">
							<tbody>

							<tr>
								<c:if test="${(isAdmin == 'true' || hasCoordinatorOrDpeOrCreatorRole == 'true') && not empty referenceData.initiateRcaContracts}">
									<th width="20%" align="left" scope="row"><input
											type="button" value="Initiate RCA" tabindex="5"
											name="ibm-submit" class="ibm-btn-cart-sec"
											onclick="showInitiateRcaBox();"> &nbsp;</th>
								</c:if>
								<th width="80%" align="left" scope="row">
									<br />

									<p><font size="7" style="color:red;">
										To access forms till 4th March, 2016 please click here 
										<c:choose>
											<c:when test="${not empty email}">
												<a target="_blank" href="http://mopbz171055.pssc.mop.fr.ibm.com:8080/gpsdrca/GPSRCA?iid=${email}">RCA Forms till 4th March, 2016</a>
											</c:when>
											<c:otherwise>
												<a target="_blank" href="http://mopbz171055.pssc.mop.fr.ibm.com:8080/gpsdrca/GPSRCA">RCA Forms till 4th March, 2016</a>
											</c:otherwise>
										</c:choose>
										</font></p>

								</th>
							</tr>

							</tbody>
						</table>
					</td>
				</tr>
				</thead>
				</tbody>
				</table>

			</div>
		</div>
	</fieldset>

</form:form>

<div class="ibm-common-overlay ibm-overlay-alt-two" id="initiate-rca-box">
	<div class="ibm-head">
		<p>
			<a class="ibm-common-overlay-close" href="#close">Close [x]</a>
		</p>
	</div>
	<div class="ibm-body">
		<div class="ibm-main">
			<div class="ibm-title">
				<h2>Initiate Root Cause Analysis</h2>
			</div>
			<div class="ibm-overlay-rule">
				<hr />
			</div>
			<div class="ibm-container ibm-alternate">
				<div class="ibm-container-body">
					<p>

						<%@ include file="/WEB-INF/jsp/rca/initiateRca.jsp"%>

					</p>
				</div>
			</div>
		</div>
	</div>
	<div class="ibm-footer"></div>
</div>


<script type="text/javascript">

	//   function resetRCAFilterInline(){
	//	   console.log("resetRCAFilterInline()...");
	//	   resetRCAFilter("<%=basePath%>");
	//  }

	function showInitiateRcaBox() {
		var _today = new Date();
		var _thisMonth = _today.getMonth() + 1;
		var _thisYear = _today.getFullYear();
		var earlier = false;
		console.log("_thisYear=" +_thisYear+", _thisMonth="+_thisMonth);
		
		var month = dojo.byId("month").value;
		var year = dojo.byId("year").value;
		
		console.log("year=" +year+", month="+month);
		dojo.query('#rcaMonth').val(month);
		dojo.query('#rcaYear').val(year);

		
		if(_thisYear > year){
			earlier = true;
			console.log("Year => earlier=" +earlier);
		} else if(_thisMonth > month){
			earlier = true;
			console.log("Month => earlier=" +earlier);
		}
		
		
		console.debug("_thisYear=" +_thisYear+", _thisMonth="+_thisMonth);
		if (month == '1') {
			month = 'January'
		}
		if (month == '2') {
			month = 'February'
		}
		if (month == '3') {
			month = 'March'
		}
		if (month == '4') {
			month = 'April'
		}
		if (month == '5') {
			month = 'May'
		}
		if (month == '6') {
			month = 'June'
		}
		if (month == '7') {
			month = 'July'
		}
		if (month == '8') {
			month = 'August'
		}
		if (month == '9') {
			month = 'September'
		}
		if (month == '10') {
			month = 'October'
		}
		if (month == '11') {
			month = 'November'
		}
		if (month == '12') {
			month = 'December'
		}

		var label = document.getElementById('monthValue');
		label.innerHTML = month + '-' + year;

		if(earlier){
			var answer = confirm("You are about to initiate RCA for month of "+month+"-"+year+". To continue, press OK." +
			" \n For RCA in another month, press Cancel and navigate to the appropriate month before initiating RCA.");
		} else {
			var answer = confirm("You are about to initiate RCA for the month of "+month+"-"+year+". To continue, press OK.");
		}
		
		if(answer) {
			ibmweb.overlay.show('initiate-rca-box');
		}



	}
</script>


