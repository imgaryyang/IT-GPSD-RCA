<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!--<style type="text/css" media="all">
.lr-mar-10px {margin-left: 10px; margin-right:10px; margin-bottom: 2px; margin-top: 2px }
</style> -->

<script type="text/javascript" src="js/rca-1.0.js">
	//
</script>
<form:form commandName="rcaReportFilter" method="POST" id="rcaReportFilter" action="reportRcaList.htm">
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
				<form:hidden path="selectedRcaNumbers" id="selectedRcaNumbers" />
				<form:hidden path="selectedActionNumbers" id="selectedActionNumbers" />


				<table width="100%" cellspacing="4" cellpadding="4" id="filterTab"
					   class="ibm-results-table" role="presentation">
					<tbody>
					<thead>
					<tr>
						<td>
							<table class="ibm-results-table" width="100%" cellspacing="1"
								   cellpadding="1" style="margin-bottom: 10px;" border="0"
								   summary="The following table shows the labels of profile listing">
								<tbody>
								<thead>

								<tr>
									<th width="16%"><span> <label
											for="startOutageDate">
										<h5>Start outage Date</h5>
									</label> <form:select path="startOutageDay" id="startOutageDay">
										<form:option value="" label="All" />
										<form:option value="1" label="1" />
										<form:option value="2" label="2" />
										<form:option value="3" label="3" />
										<form:option value="4" label="4" />
										<form:option value="5" label="5" />
										<form:option value="6" label="6" />
										<form:option value="7" label="7" />
										<form:option value="8" label="8" />
										<form:option value="9" label="9" />
										<form:option value="10" label="10" />
										<form:option value="11" label="11" />
										<form:option value="12" label="12" />
										<form:option value="13" label="13" />
										<form:option value="14" label="14" />
										<form:option value="15" label="15" />
										<form:option value="16" label="16" />
										<form:option value="17" label="17" />
										<form:option value="18" label="18" />
										<form:option value="19" label="19" />
										<form:option value="20" label="20" />
										<form:option value="21" label="21" />
										<form:option value="22" label="22" />
										<form:option value="23" label="23" />
										<form:option value="24" label="24" />
										<form:option value="25" label="25" />
										<form:option value="26" label="26" />
										<form:option value="27" label="27" />
										<form:option value="28" label="28" />
										<form:option value="29" label="29" />
										<form:option value="30" label="30" />
										<form:option value="31" label="31" />

									</form:select>
											</span></th>
									<th width="17%" scope="col"><span> <label
											for="month"><h5>Start Month</h5></label> <form:select
											path="startOutageMonth" id="startOutageMonth">
										<form:option value="" label="--Select month--" />
										<form:options items="${referenceData.monthList}" />
									</form:select>
											</span></th>

									<th width="17%" align="center" scope="col"><span>
													<label for="startOutageYear"><h5>Start Year</h5></label> <form:select
											path="startOutageYear" id="startOutageYear">
										<form:option value="" label="--Select Year--" />
										<form:options items="${referenceData.yearList}" />
									</form:select>
											</span></th>

									<th width="16%"><span> <label for="endOutageDay">
										<h5>End outage Date</h5>
									</label> <form:select path="endOutageDay" id="endOutageDay">
										<form:option value="" label="All" />
										<form:option value="1" label="1" />
										<form:option value="2" label="2" />
										<form:option value="3" label="3" />
										<form:option value="4" label="4" />
										<form:option value="5" label="5" />
										<form:option value="6" label="6" />
										<form:option value="7" label="7" />
										<form:option value="8" label="8" />
										<form:option value="9" label="9" />
										<form:option value="10" label="10" />
										<form:option value="11" label="11" />
										<form:option value="12" label="12" />
										<form:option value="13" label="13" />
										<form:option value="14" label="14" />
										<form:option value="15" label="15" />
										<form:option value="16" label="16" />
										<form:option value="17" label="17" />
										<form:option value="18" label="18" />
										<form:option value="19" label="19" />
										<form:option value="20" label="20" />
										<form:option value="21" label="21" />
										<form:option value="22" label="22" />
										<form:option value="23" label="23" />
										<form:option value="24" label="24" />
										<form:option value="25" label="25" />
										<form:option value="26" label="26" />
										<form:option value="27" label="27" />
										<form:option value="28" label="28" />
										<form:option value="29" label="29" />
										<form:option value="30" label="30" />
										<form:option value="31" label="31" />

									</form:select>
											</span></th>
									<th width="17%" scope="col"><span> <label
											for="month"><h5>End Month</h5></label> <form:select
											path="endOutageMonth" id="endOutageMonth">
										<form:option value="" label="--Select month--" />
										<form:options items="${referenceData.monthList}" />
									</form:select>
											</span></th>

									<th width="17%" align="center" scope="col"><span>
													<label for="year"><h5>End Year</h5></label> <form:select
											path="endOutageYear" id="endOutageYear">
										<form:option value="" label="--Select Year--" />
										<form:options items="${referenceData.yearList}" />
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

							<th align="left" scope="col" width="25%"><span> <label
									for="contract">Account Name</label><br /> <form:select
									path="contract" cssStyle="background: white;" id="contract">
								<form:option value="" label="All Accounts" />
								<form:options items="${referenceData.listRcaContracts}"
											  itemValue="title" itemLabel="title" />
							</form:select>
										</span></th>

							<th align="left" scope="col" width="25%"><span> <label
									for="contract">RCA/Action Number</label><br /> <form:select
									path="rcaOrActionNumber" cssStyle="background: white;"
									id="rcaOrActionNumber">
								<form:option value="" label="All" />
								<form:options items="${referenceData.listRcasAndActions}"
											  itemValue="numberAndType" itemLabel="rcaOrActionNumber" />
							</form:select>
										</span></th>

							<th align="left" scope="col" width="35%"><span> <label
									for="contract">RCA Coordinator</label><br /> <form:select
									path="rcaCoordinator" cssStyle="background: white;"
									id="rcaCoordinator">
								<form:option value="" label="All" />
								<form:options items="${referenceData.listRcaCoordinators}" />
							</form:select>
										</span></th>

							<th width="15%"><span> <label for="contract">RCA
								Owner</label><br /> <form:select path="rcaOwner"
																 cssStyle="background: white;" id="rcaOwner">
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

							<th align="center" scope="col" width="25%"><span>
												<label for="stage">Stage</label><br /> <form:select
									path="stage" cssStyle="background: white;" id="stage">
								<form:option value="" label="All" />
								<form:option value="Open" label="Open" />
								<form:option value="Awaiting" label="Awaiting" />
								<form:option value="Approved" label="Approved" />
								<form:option value="Active" label="Active" />
								<form:option value="Closed" label="Closed" />
								<form:option value="Cancelled" label="Cancelled" />

							</form:select>
										</span></th>


							<th align="center" scope="col" width="25%"><span>
												<label for="formType">Type</label><br /> <form:select
									path="formType" cssStyle="background: white;" id="formType" onchange="hideReportsDiv(); showOrHideReportsButtons();">
								<form:option value="rca" label="RCAs" />
								<form:option value="action" label="Actions" />

							</form:select>
										</span></th>

							<th align="center" scope="col" width="25%"></th>

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
							   summary="The following table shows the buttons for rca listing">
							<tbody>
							<thead>
							<tr height="5">
							</tr>
							<tr>
								<th width="30%" align="left" scope="row"></th>
								<th width="20%" align="left" scope="row">
									<input
											type="button" value="Apply" tabindex="5" name="ibm-submit"
											class="ibm-btn-cart-sec" onclick="getReportRCAs();">&nbsp;
								</th>
								<th width="20%" align="left" scope="row"><br /> <a
										class="ibm-reset-link" name="ibm-cancel" tabindex="6"
										id="ibm-cancel" href="javascript:void(0)"
										onclick="clearFilters();">Clear</a></th>
								<th width="30%" align="left" scope="row"></th>
							</tr>

							</thead>
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

<script type="text/javascript" src="js/reports-1.0.js"></script>
<script src="js/jquery.js"></script>

<script type="text/javascript">


	function showOrHideReportsButtons(){
		console.log("showOrHideReportsButtons()....")
		var formType = dojo.byId("formType").value;
		if(formType == 'rca'){
			$("#actionReportsTh").hide();
			$("#rcaReportsTh").show();
		}
		else if(formType == 'action'){
			$("#rcaReportsTh").hide();
			$("#actionReportsTh").show();
		}
	}


	function showInitiateRcaBox() {

		var month = dojo.byId("month").value;
		var year = dojo.byId("year").value;

		dojo.query('#rcaMonth').val(month);
		dojo.query('#rcaYear').val(year);

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

		var answer = confirm("You are about to initiate RCA for the month of "
				+ month
				+ "-"
				+ year
				+ ". To continue, press OK."
				+ " \n For RCA in another month, press Cancel and navigate to the appropriate month before initiating RCA.");
		if (answer) {
			ibmweb.overlay.show('initiate-rca-box');
		}

	}


</script>