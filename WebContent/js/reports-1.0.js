

function hide(id){
	try {
		dojo.byId(id).style.display = 'none';
	} catch(err) {
		console.debug("Error hide(" + id+") => "+err.message);
	}
}

function show(id){
	try {
		dojo.byId(id).style.display = 'block';
	} catch(err) {
		console.debug("Error show(" + id+") => "+err.message);
	}
}


function showOrHide(source, dest){
	try {
		if(dojo.byId(source).checked){
			show(dest);
		}else{
			hide(dest);
		}
	} catch(err) {
		console.debug("Error show(" + id+") => "+err.message);
	}
}

/////////////////////////////// contract filter////////////////////////////////////////////////

function getContracts(){
	// Local var representing node to be updated
	var contractListDiv = dojo.byId("contractList");
	var form = dojo.byId("searchFilterForm");
	// Using dojo.xhrPost, as the amount of data sent could be large
	dojo.xhrPost({
		// The URL of the request
		url: "contractReportList.htm",
		form: dojo.byId("searchFilterForm"),
		// The success handler
		load: function(response) {
			contractListDiv.innerHTML = response;
		},
		// The error handler
		error: function() {
			contractListDiv.innerHTML = "Your search could not be performed, please try again."
		},
		// The complete handler
		handle: function() {
			// hasBeenSent = true;
		}
	});
}

function clearFilters() {
	document.getElementById('contract').value = "";
	document.getElementById('lob').value = "";
	document.getElementById('stage').value = "";
	document.getElementById('tower').value = "";
	document.getElementById('sector').value = "";
	document.getElementById('customer').value = "";
	document.getElementById('country').value = "";
	document.getElementById('geo').value = "";
	document.getElementById('iot').value = "";
	document.getElementById('imt').value = "";
}


function getNextPage(){
	var _page =  dojo.byId("page");
	var _pagingAction =  dojo.byId("pagingAction");
	_page.value = parseInt(_page.value)+1;
	_pagingAction.value = "PAGINATE";
	//dojo.byId("displayMsg").innerHTML = "page = "+_page.value;
	getContracts();
	return false;
}

function getPreviousPage(){
	var _page =  dojo.byId("page");
	var _pagingAction =  dojo.byId("pagingAction");
	_page.value = parseInt(_page.value)-1;
	_pagingAction.value = "PAGINATE";
	if(parseInt(_page.value) < 1){
		_page.value = 1;
	}
	//dojo.byId("displayMsg").innerHTML = "page = "+dojo.byId("page").value;
	getContracts();
	return false;
}

/////////////////////////////// contract Filer end////////////////////////////////////////////////


///////////////////////////// monthlyReport filter /////////////////////////////////////
function getMothlyStatusList(){
	console.log('calling getRcaReport().....');

	var contractListDiv = dojo.byId("contractList");
	dojo.xhrPost({
		// The URL of the request
		url: "rcaReportCount.htm",
		form: dojo.byId("rcaReportFilter"),
		// The success handler
		load: function(response) {
			try {
				contractListDiv.innerHTML = response;
			} catch(err) {
				console.debug("Error contractListDiv:  "+err.message);
				console.debug("response:  "+response);
			}
		},
		// The error handler
		error: function() {
			contractListDiv.innerHTML = "Your search could not be performed, please try again.";
		},
		// The complete handler
		handle: function() {
			// hasBeenSent = true;
		}
	});
}

function clearMonthlyFilters() {
	console.debug('clearFilters().........');
	document.getElementById('month').value = "";
	document.getElementById('year').value = "";
	document.getElementById('contract').value = "";
	document.getElementById('lob').value = "";
	document.getElementById('stage').value = "";
	document.getElementById('tower').value = "";
	document.getElementById('sector').value = "";
	document.getElementById('customer').value = "";
	document.getElementById('country').value = "";
	document.getElementById('geo').value = "";
	document.getElementById('iot').value = "";
	document.getElementById('imt').value = "";
}

function generateSLAReport(){
	// Local var representing if the form has been sent at all
	//var hasBeenSent = false;
	// Local var representing node to be updated
	var contractListDiv = dojo.byId("contractList");
	contractListDiv.innerHTML = "Please wait while the report is being generated.....";
	// Using dojo.xhrPost, as the amount of data sent could be large
	dojo.xhrPost({
		// The URL of the request
		url: "genMonthlySLAReport.htm",
		form: dojo.byId("searchFilterForm"),
		// The success handler
		load: function(response) {
			if(response == 'ok'){
				response = 'Download report <a class="ibm-download-link" href="downloadReport.htm?title=Analysis" target="_blank">Here</a>';
			}
			try {
				contractListDiv.innerHTML = response;
			} catch(err) {
				console.debug("Error contractListDiv:  "+err.message);
				console.debug("response:  "+response);
			}

		},
		// The error handler
		error: function() {
//		    	console.debug("Report could not be generated, please try again later.  "+err.message);
			contractListDiv.innerHTML = "Report could not be generated, please try again later.";
		},
		// The complete handler
		handle: function() {
			// hasBeenSent = true;
		}
	});
}

function generateMonthlySLAReport(){
	// Local var representing if the form has been sent at all
	//var hasBeenSent = false;
	// Local var representing node to be updated
	var contractListDiv = dojo.byId("contractList");
	contractListDiv.innerHTML = "Please wait while the report is being generated.....";
	// Using dojo.xhrPost, as the amount of data sent could be large
	dojo.xhrPost({
		// The URL of the request
		url: "genSLAReport.htm",
		form: dojo.byId("searchFilterForm"),
		// The success handler
		load: function(response) {
			if(response == 'ok'){
				response = 'Download report <a class="ibm-download-link" href="downloadReport.htm?title=SLA" target="_blank">Here</a>';
			}
			try {
				contractListDiv.innerHTML = response;
			} catch(err) {
				console.debug("Error contractListDiv:  "+err.message);
				console.debug("response:  "+response);
			}

		},
		// The error handler
		error: function() {
//		    	console.debug("Report could not be generated, please try again later.  "+err.message);
			contractListDiv.innerHTML = "Report could not be generated, please try again later.";
		},
		// The complete handler
		handle: function() {
			// hasBeenSent = true;
		}
	});
}

function generateMonthlySLOReport(){
	// Local var representing if the form has been sent at all
	//var hasBeenSent = false;
	// Local var representing node to be updated
	var contractListDiv = dojo.byId("contractList");
	contractListDiv.innerHTML = "Please wait while the report is being generated.....";
	// Using dojo.xhrPost, as the amount of data sent could be large
	dojo.xhrPost({
		// The URL of the request
		url: "genSLOReport.htm",
		form: dojo.byId("searchFilterForm"),
		// The success handler
		load: function(response) {
			if(response == 'ok'){
				response = 'Download report <a class="ibm-download-link" href="downloadReport.htm?title=SLO" target="_blank">Here</a>';
			}
			try {
				contractListDiv.innerHTML = response;
			} catch(err) {
				console.debug("Error contractListDiv:  "+err.message);
				console.debug("response:  "+response);
			}

		},
		// The error handler
		error: function() {
//		    	console.debug("Report could not be generated, please try again later.  "+err.message);
			contractListDiv.innerHTML = "Report could not be generated, please try again later.";
		},
		// The complete handler
		handle: function() {
			// hasBeenSent = true;
		}
	});
}

function generateMonthlyFASLAReport(){
	// Local var representing if the form has been sent at all
	//var hasBeenSent = false;
	// Local var representing node to be updated
	var contractListDiv = dojo.byId("contractList");
	contractListDiv.innerHTML = "Please wait while the report is being generated.....";
	// Using dojo.xhrPost, as the amount of data sent could be large
	dojo.xhrPost({
		// The URL of the request
		url: "genFaScmSLAReport.htm",
		form: dojo.byId("searchFilterForm"),
		// The success handler
		load: function(response) {
			if(response == 'ok'){
				response = 'Download report <a class="ibm-download-link" href="downloadReport.htm?title=New_SLA_Summary_Report" target="_blank">Here</a>';
			}
			try {
				contractListDiv.innerHTML = response;
			} catch(err) {
				console.debug("Error contractListDiv:  "+err.message);
				console.debug("response:  "+response);
			}

		},
		// The error handler
		error: function() {
//		    	console.debug("Report could not be generated, please try again later.  "+err.message);
			contractListDiv.innerHTML = "Report could not be generated, please try again later.";
		},
		// The complete handler
		handle: function() {
			// hasBeenSent = true;
		}
	});
}

function generateMonthlyFASLOReport(){
	// Local var representing if the form has been sent at all
	//var hasBeenSent = false;
	// Local var representing node to be updated
	var contractListDiv = dojo.byId("contractList");
	contractListDiv.innerHTML = "Please wait while the report is being generated.....";
	// Using dojo.xhrPost, as the amount of data sent could be large
	dojo.xhrPost({
		// The URL of the request
		url: "genFaScmSLOReport.htm",
		form: dojo.byId("searchFilterForm"),
		// The success handler
		load: function(response) {
			if(response == 'ok'){
				response = 'Download report <a class="ibm-download-link" href="downloadReport.htm?title=New_SLO_Summary_Report.xls" target="_blank">Here</a>';
			}
			try {
				contractListDiv.innerHTML = response;
			} catch(err) {
				console.debug("Error contractListDiv:  "+err.message);
				console.debug("response:  "+response);
			}

		},
		// The error handler
		error: function() {
//		    	console.debug("Report could not be generated, please try again later.  "+err.message);
			contractListDiv.innerHTML = "Report could not be generated, please try again later.";
		},
		// The complete handler
		handle: function() {
			// hasBeenSent = true;
		}
	});
}

function generateProfileReport(){
	// Local var representing if the form has been sent at all
	//var hasBeenSent = false;
	// Local var representing node to be updated
	var contractListDiv = dojo.byId("contractList");
	contractListDiv.innerHTML = "Please wait while the report is being generated.....";
	// Using dojo.xhrPost, as the amount of data sent could be large
	dojo.xhrPost({
		// The URL of the request
		url: "genProfileReport.htm",
		form: dojo.byId("searchFilterForm"),
		// The success handler
		load: function(response) {
			if(response == 'ok'){
				response = 'Download report <a class="ibm-download-link" href="downloadReport.htm?title=Profile" target="_blank">Here</a>';
			}
			try {
				contractListDiv.innerHTML = response;
			} catch(err) {
				console.debug("Error contractListDiv:  "+err.message);
				console.debug("response:  "+response);
			}

		},
		// The error handler
		error: function() {
//		    	console.debug("Report could not be generated, please try again later.  "+err.message);
			contractListDiv.innerHTML = "Report could not be generated, please try again later.";
		},
		// The complete handler
		handle: function() {
			// hasBeenSent = true;
		}
	});
}

function generateWeeklyTransitionBPD(){
	// Local var representing if the form has been sent at all
	//var hasBeenSent = false;
	// Local var representing node to be updated
	var contractListDiv = dojo.byId("contractList");
	contractListDiv.innerHTML = "Please wait while the report is being generated.....";
	var myForm = dojo.byId("searchFilterForm");
	console.debug(myForm);
	// Using dojo.xhrPost, as the amount of data sent could be large
	dojo.xhrPost({
		// The URL of the request
		url: "genBPDTransitionReport.htm",
		form: myForm,
		// The success handler
		load: function(response) {
			if(response == 'ok'){
				response = 'Download report <a class="ibm-download-link" href="downloadReport.htm?title=WeeklyTransition" target="_blank">Here</a>';
			}
			try {
				contractListDiv.innerHTML = response;
			} catch(err) {
				console.debug("Error contractListDiv:  "+err.message);
				console.debug("response:  "+response);
			}

		},
		// The error handler
		error: function(err) {
			console.debug("Report could not be generated, please try again later.  "+err.message);
			contractListDiv.innerHTML = "Report could not be generated, please try again later.";

		},
		// The complete handler
		handle: function() {
			// hasBeenSent = true;
		}
	});
}

function generateWeeklyTransitionBPDDetailed(){
	// Local var representing if the form has been sent at all
	//var hasBeenSent = false;
	// Local var representing node to be updated
	var contractListDiv = dojo.byId("contractList");
	contractListDiv.innerHTML = "Please wait while the report is being generated.....";
	var myForm = dojo.byId("searchFilterForm");
	console.debug(myForm);
	// Using dojo.xhrPost, as the amount of data sent could be large
	dojo.xhrPost({
		// The URL of the request
		url: "genBPDTransitionReportDetailed.htm",
		form: myForm,
		// The success handler
		load: function(response) {
			if(response == 'ok'){
				response = 'Download report <a class="ibm-download-link" href="downloadReport.htm?title=WeeklyTransitionDetailed" target="_blank">Here</a>';
			}
			try {
				contractListDiv.innerHTML = response;
			} catch(err) {
				console.debug("Error contractListDiv:  "+err.message);
				console.debug("response:  "+response);
			}

		},
		// The error handler
		error: function(err) {
			console.debug("Report could not be generated, please try again later.  "+err.message);
			contractListDiv.innerHTML = "Report could not be generated, please try again later.";

		},
		// The complete handler
		handle: function() {
			// hasBeenSent = true;
		}
	});
}

function generateProfileSLOReport(){
	// Local var representing if the form has been sent at all
	//var hasBeenSent = false;
	// Local var representing node to be updated
	var contractListDiv = dojo.byId("contractList");
	contractListDiv.innerHTML = "Please wait while the report is being generated.....";
	var myForm = dojo.byId("searchFilterForm");
	console.debug(myForm);
	// Using dojo.xhrPost, as the amount of data sent could be large
	dojo.xhrPost({
		// The URL of the request
		url: "genProfileSLOReport.htm",
		form: myForm,
		// The success handler
		load: function(response) {
			if(response == 'ok'){
				response = 'Download report <a class="ibm-download-link" href="downloadReport.htm?title=ProfileSLO" target="_blank">Here</a>';
			}
			try {
				contractListDiv.innerHTML = response;
			} catch(err) {
				console.debug("Error contractListDiv:  "+err.message);
				console.debug("response:  "+response);
			}

		},
		// The error handler
		error: function(err) {
			console.debug("Report could not be generated, please try again later.  "+err.message);
			contractListDiv.innerHTML = "Report could not be generated, please try again later.";

		},
		// The complete handler
		handle: function() {
			// hasBeenSent = true;
		}
	});
}

function generateProfileSLAReport(){
	// Local var representing if the form has been sent at all
	//var hasBeenSent = false;
	// Local var representing node to be updated
	var contractListDiv = dojo.byId("contractList");
	contractListDiv.innerHTML = "Please wait while the report is being generated.....";
	var myForm = dojo.byId("searchFilterForm");
	console.debug(myForm);
	// Using dojo.xhrPost, as the amount of data sent could be large
	dojo.xhrPost({
		// The URL of the request
		url: "genProfileSLAReport.htm",
		form: myForm,
		// The success handler
		load: function(response) {
			if(response == 'ok'){
				response = 'Download report <a class="ibm-download-link" href="downloadReport.htm?title=ProfileSLA" target="_blank">Here</a>';
			}
			try {
				contractListDiv.innerHTML = response;
			} catch(err) {
				console.debug("Error contractListDiv:  "+err.message);
				console.debug("response:  "+response);
			}

		},
		// The error handler
		error: function(err) {
			console.debug("Report could not be generated, please try again later.  "+err.message);
			contractListDiv.innerHTML = "Report could not be generated, please try again later.";

		},
		// The complete handler
		handle: function() {
			// hasBeenSent = true;
		}
	});
}

function generateWeeklyTransitionDaksh(){
	// Local var representing if the form has been sent at all
	//var hasBeenSent = false;
	// Local var representing node to be updated
	var contractListDiv = dojo.byId("contractList");
	contractListDiv.innerHTML = "Please wait while the report is being generated.....";
	// Using dojo.xhrPost, as the amount of data sent could be large
	dojo.xhrPost({
		// The URL of the request
		url: "genWeeklyDakshTransitionReport.htm",
		form: dojo.byId("searchFilterForm"),
		// The success handler
		load: function(response) {
			if(response == 'ok'){
				response = 'Download report <a class="ibm-download-link" href="downloadReport.htm?title=WeeklyTransitionDaksh" target="_blank">Here</a>';
			}
			try {
				contractListDiv.innerHTML = response;
			} catch(err) {
				console.debug("Error contractListDiv:  "+err.message);
				console.debug("response:  "+response);
			}

		},
		// The error handler
		error: function() {
//		    	console.debug("Report could not be generated, please try again later.  "+err.message);
			contractListDiv.innerHTML = "Report could not be generated, please try again later.";
		},
		// The complete handler
		handle: function() {
			// hasBeenSent = true;
		}
	});
}

/////////////////////////// end monthlyReport filter /////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////weekly searchFilter ////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


function getWeeklyStatusList() {
	console.debug('getWeeklyStatusList().........');
	// Local var representing if the form has been sent at all
	// var hasBeenSent = false;
	// Local var representing node to be updated
	var contractListDiv = dojo.byId("contractList");
	// Using dojo.xhrPost, as the amount of data sent could be large
	dojo.xhrPost({
		// The URL of the request
		url : "weeklyReportList.htm",
		form : dojo.byId("searchFilterForm"),
		// The success handler
		load : function(response) {
			contractListDiv.innerHTML = response;
		},
		// The error handler
		error : function() {
			contractListDiv.innerHTML = "Your search could not be performed, please try again later.";
		},
		// The complete handler
		handle : function() {
			// hasBeenSent = true;
		}
	});
}

function getWeeksListForCurrYear() {
	console.debug('getWeeksListForCurrYear().........');
	// alert(document.getElementById('currentWeek').value);
	var selectedYear = document.getElementById('year').value;
	document.getElementById('week').options.length = 0;
	var day = 5;
	var date = new Date(selectedYear);
	var nextYear = date.getFullYear() + 1;

	while (date.getDay() != day) {
		date.setDate(date.getDate() + 1);
	}

	while (date.getFullYear() < nextYear) {
		var yyyy = date.getFullYear();

		var mm = (date.getMonth() + 1);
		mm = (mm < 10) ? '0' + mm : mm;

		var dd = date.getDate();
		dd = (dd < 10) ? '0' + dd : dd;
		var nextWeek = yyyy + '-' + (mm) + '-' + (dd);

		var currentWeek = document.getElementById('currentWeek').value;
		var optn = document.createElement('option');
		optn.text = nextWeek;
		optn.value = nextWeek;
		if (nextWeek == currentWeek) {
			optn.selected = true;
		}

		document.getElementById('week').options.add(optn);
		date.setDate(date.getDate() + 7);
	}

}

function clearWeeklyFilters() {
	console.debug('clearWeeklyFilters().........');
	document.getElementById('contract').value = "";
	document.getElementById('lob').value = "";
	document.getElementById('stage').value = "";
	document.getElementById('tower').value = "";
	document.getElementById('sector').value = "";
	document.getElementById('customer').value = "";
	document.getElementById('country').value = "";
	document.getElementById('geo').value = "";
	document.getElementById('iot').value = "";
	document.getElementById('imt').value = "";
}

function generateWeeklyStatusReport(title){
	console.debug("report tile:  "+title);
	// Local var representing if the form has been sent at all
	//var hasBeenSent = false;
	// Local var representing node to be updated
	var contractListDiv = dojo.byId("contractList");
	contractListDiv.innerHTML = "Please wait while the report is being generated.....";
	// Using dojo.xhrPost, as the amount of data sent could be large

	var methodUrl = "genWeeklyReport.htm";
	var rTitle = "Weekly_Status";
	if(title == 'Weekly Process'){
		methodUrl = "genWeeklyProcessReport.htm";
		rTitle = "Weekly_Detailed";
	}
	console.debug("Url: "+methodUrl);
	dojo.xhrPost({
		// The URL of the request
		url: methodUrl,
		form: dojo.byId("searchFilterForm"),
		// The success handler
		load: function(response) {
			if(response == 'ok'){
				response = 'Download report <a class="ibm-download-link" href="downloadReport.htm?title='+rTitle+'" target="_blank">Here</a>';
			}
			try {
				contractListDiv.innerHTML = response;
			} catch(err) {
				console.debug("Error contractListDiv:  "+err.message);
				console.debug("response:  "+response);
			}

		},
		// The error handler
		error: function() {
			contractListDiv.innerHTML = "Your search could not be performed, please try again later.";
		},
		// The complete handler
		handle: function() {
			// hasBeenSent = true;
		}
	});
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////End weekly searchFilter ////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


// RCA Reports /////

function enableRcaReportbuttons(){
	$('#rca-cdr-rpt-btn').show();
	$('#cs-ft-rpt-btn').show();
	$('#rca-sm-rpt-btn').show();
	$('#rca-de-rpt-btn').show();
	$('#act-sm-rpt-btn').show();
	$('#rca-wo-rpt-btn').show();
    $('#rca-gn-rpt-btn').show();
    $('#accountsTh').show();
	$('#reportsTh').show();
}
function disableReportbuttons(){
	$('#rca-cdr-rpt-btn').hide();
	$('#cs-ft-rpt-btn').hide();
	$('#rca-sm-rpt-btn').hide();
	$('#rca-de-rpt-btn').hide();
	$('#rca-wo-rpt-btn').hide();
	$('#act-sm-rpt-btn').hide();
    $('#rca-gn-rpt-btn').hide();
    $('#accountsTh').hide();
	$('#reportsTh').hide();
}
function hideReportsDiv(){
	$('#reportsDiv').hide();
}
function showReportsDiv(){
	$('#reportsDiv').show();
}
function resetSelectedList(){
	dojo.query('#selectedRcaNumbers').val("");
	dojo.query('#selectedActionNumbers').val("");
}

function getReportRCAs(){
	// Local var representing if the form has been sent at all
	//var hasBeenSent = false;
	// Local var representing node to be updated
	var contractListDiv = dojo.byId("contractList");
	var rcaListDiv = dojo.byId("rcaList");
	contractListDiv.innerHTML = "Please wait... <img alt=\"loading...\" src=\"images/w3/loader.gif \">";
	rcaListDiv.innerHTML = "";
	disableReportbuttons();
	showReportsDiv();
	resetSelectedList();
	// Using dojo.xhrPost, as the amount of data sent could be large
	dojo.xhrPost({
		// The URL of the request
		//url: "contractList.htm",
		form: dojo.byId("rcaReportFilter"),
		// The success handler
		load: function(response) {
			//  showReportsDiv();
			enableRcaReportbuttons();
			contractListDiv.innerHTML = response;
			selectAllRptCheckBoxes();
		},
		// The error handler
		error: function() {
			contractListDiv.innerHTML = "No RCA found with selected outage date criteria. Make sure this information is not missing on RCA form."
		},
		// The complete handler
		handle: function() {
			// hasBeenSent = true;
			// hasBeenSent = true;
		}
	});
}
function getRcaCoordinatorReport(){
	console.debug('getRcaReport().........');
	// Local var representing if the form has been sent at all
	// var hasBeenSent = false;
	// Local var representing node to be updated

	var contractListDiv = dojo.byId("contractList");
	contractListDiv.innerHTML = "Please wait... <img alt=\"loading...\" src=\"images/w3/loader.gif \">";
	// Using dojo.xhrPost, as the amount of data sent could be large
	dojo.xhrPost({
		// The URL of the request
		url : "rcaReportCount.htm",
		form : dojo.byId("rcaReportFilter"),
		// The success handler
		load : function(response) {
			contractListDiv.innerHTML = response;
		},
		// The error handler
		error : function() {
			contractListDiv.innerHTML = "Your search could not be performed, please try again later.";
		},
		// The complete handler
		handle : function() {
			// hasBeenSent = true;
		}
	});

}

function generateRCACoordinatorReport(){
	// Local var representing if the form has been sent at all
	//var hasBeenSent = false;
	// Local var representing node to be updated
	var contractListDiv = dojo.byId("rcaList");
	contractListDiv.innerHTML = "Please wait while the report is being generated.....";
	// Using dojo.xhrPost, as the amount of data sent could be large
	dojo.xhrPost({
		// The URL of the request
		url: "genRcaCoordinatorReport.htm",
		form: dojo.byId("rcaReportFilter"),
		// The success handler
		load: function(response) {
			if(response == 'ok'){
				response = 'Download report <a class="ibm-download-link" href="downloadReport.htm?title=RcaCoordinator" target="_blank">Here</a>';
			}
			try {
				contractListDiv.innerHTML = response;
			} catch(err) {
				console.debug("Error contractListDiv:  "+err.message);
				console.debug("response:  "+response);
			}

		},
		// The error handler
		error: function() {
			//		    	console.debug("Report could not be generated, please try again later.  "+err.message);
			contractListDiv.innerHTML = "Report could not be generated, please try again later.";
		},
		// The complete handler
		handle: function() {
			// hasBeenSent = true;
		}
	});
}

function generateRCASummaryReport(){
	console.log('generateRCASummaryReport()....');
	// Local var representing if the form has been sent at all
	//var hasBeenSent = false;
	// Local var representing node to be updated
	var contractListDiv = dojo.byId("rcaList");
	contractListDiv.innerHTML = "Please wait while the report is being generated.....";
	// Using dojo.xhrPost, as the amount of data sent could be large
	dojo.xhrPost({
		// The URL of the request
		url: "genRcaSummaryReport.htm",
		form: dojo.byId("rcaReportFilter"),
		// The success handler
		load: function(response) {
			if(response == 'ok'){
				response = 'Download report <a class="ibm-download-link" href="downloadReport.htm?title=RcaSummary" target="_blank">Here</a>';
			}
			try {
				contractListDiv.innerHTML = response;
			} catch(err) {
				console.debug("Error contractListDiv:  "+err.message);
				console.debug("response:  "+response);
			}

		},
		// The error handler
		error: function() {
			//		    	console.debug("Report could not be generated, please try again later.  "+err.message);
			contractListDiv.innerHTML = "Report could not be generated, please try again later.";
		},
		// The complete handler
		handle: function() {
			// hasBeenSent = true;
		}
	});
}

function generateRCADetailedReport(){
	console.log('generateRCADetailedReport()....');
	// Local var representing if the form has been sent at all
	//var hasBeenSent = false;
	// Local var representing node to be updated
	var contractListDiv = dojo.byId("rcaList");
	contractListDiv.innerHTML = "Please wait while the report is being generated.....";
	// Using dojo.xhrPost, as the amount of data sent could be large
	dojo.xhrPost({
		// The URL of the request
		url: "genRcaDetailedReport.htm",
		form: dojo.byId("rcaReportFilter"),
		// The success handler
		load: function(response) {
			if(response == 'ok'){
				response = 'Download report <a class="ibm-download-link" href="downloadReport.htm?title=RcaDetailed" target="_blank">Here</a>';
			}
			try {
				contractListDiv.innerHTML = response;
			} catch(err) {
				console.debug("Error contractListDiv:  "+err.message);
				console.debug("response:  "+response);
			}

		},
		// The error handler
		error: function() {
			//		    	console.debug("Report could not be generated, please try again later.  "+err.message);
			contractListDiv.innerHTML = "Report could not be generated, please try again later.";
		},
		// The complete handler
		handle: function() {
			// hasBeenSent = true;
		}
	});
}


function generateActionSummaryReport(){
	console.log('generateActionSummaryReport()....');
	// Local var representing if the form has been sent at all
	//var hasBeenSent = false;
	// Local var representing node to be updated
	var contractListDiv = dojo.byId("rcaList");
	contractListDiv.innerHTML = "Please wait while the report is being generated.....";
	// Using dojo.xhrPost, as the amount of data sent could be large
	dojo.xhrPost({
		// The URL of the request
		url: "genActionSummaryReport.htm",
		form: dojo.byId("rcaReportFilter"),
		// The success handler
		load: function(response) {
			if(response == 'ok'){
				response = 'Download report <a class="ibm-download-link" href="downloadReport.htm?title=ActionSummary" target="_blank">Here</a>';
			}
			try {
				contractListDiv.innerHTML = response;
			} catch(err) {
				console.debug("Error contractListDiv:  "+err.message);
				console.debug("response:  "+response);
			}

		},
		// The error handler
		error: function() {
			//		    	console.debug("Report could not be generated, please try again later.  "+err.message);
			contractListDiv.innerHTML = "Report could not be generated, please try again later.";
		},
		// The complete handler
		handle: function() {
			// hasBeenSent = true;
		}
	});
}


function generateCustomerFormattedReport(){
	console.log('generateCustomerFormattedReport()....');
	// Local var representing if the form has been sent at all
	//var hasBeenSent = false;
	// Local var representing node to be updated
	var contractListDiv = dojo.byId("rcaList");
	contractListDiv.innerHTML = "Please wait while the report is being generated.....";
	// Using dojo.xhrPost, as the amount of data sent could be large
	dojo.xhrPost({
		// The URL of the request
		url: "genCustomerFormattedReport.htm",
		form: dojo.byId("rcaReportFilter"),
		// The success handler
		load: function(response) {
			if(response == 'ok'){
				response = 'Download report <a class="ibm-download-link" href="downloadReport.htm?title=CustomerFormatted" target="_blank">Here</a>';
			}
			try {
				contractListDiv.innerHTML = response;
			} catch(err) {
				console.debug("Error contractListDiv:  "+err.message);
				console.debug("response:  "+response);
			}

		},
		// The error handler
		error: function() {
			//		    	console.debug("Report could not be generated, please try again later.  "+err.message);
			contractListDiv.innerHTML = "Report could not be generated, please try again later.";
		},
		// The complete handler
		handle: function() {
			// hasBeenSent = true;
		}
	});
}




function generateWeeklyOperationsReport(){
	console.log('generateWeeklyOperationsReport()....');
	// Local var representing if the form has been sent at all
	//var hasBeenSent = false;
	// Local var representing node to be updated
	var contractListDiv = dojo.byId("rcaList");
	contractListDiv.innerHTML = "Please wait while the report is being generated.....";
	// Using dojo.xhrPost, as the amount of data sent could be large
	dojo.xhrPost({
		// The URL of the request
		url: "genWeeklyOperationsReport.htm",
		form: dojo.byId("rcaReportFilter"),
		// The success handler
		load: function(response) {
			if(response == 'ok'){
				response = 'Download report <a class="ibm-download-link" href="downloadReport.htm?title=WeeklyOperations" target="_blank">Here</a>';
			}
			try {
				contractListDiv.innerHTML = response;
			} catch(err) {
				console.debug("Error contractListDiv:  "+err.message);
				console.debug("response:  "+response);
			}

		},
		// The error handler
		error: function() {
			//		    	console.debug("Report could not be generated, please try again later.  "+err.message);
			contractListDiv.innerHTML = "Report could not be generated, please try again later.";
		},
		// The complete handler
		handle: function() {
			// hasBeenSent = true;
		}
	});
}


function generateGreenTemplateReport(){
    console.log('generateGreenTemplateReport()....');
    // Local var representing if the form has been sent at all
    //var hasBeenSent = false;
    // Local var representing node to be updated
    var contractListDiv = dojo.byId("rcaList");
    contractListDiv.innerHTML = "Please wait while the report is being generated.....";
    // Using dojo.xhrPost, as the amount of data sent could be large
    dojo.xhrPost({
        // The URL of the request
        url: "genGreenTemplateReport.htm",
        form: dojo.byId("rcaReportFilter"),
        // The success handler
        load: function(response) {
            if(response == 'ok'){
                response = 'Download report <a class="ibm-download-link" href="downloadReport.htm?title=RCAGreenTemplate" target="_blank">Here</a>';
            }
            try {
                contractListDiv.innerHTML = response;
            } catch(err) {
                console.debug("Error contractListDiv:  "+err.message);
                console.debug("response:  "+response);
            }

        },
        // The error handler
        error: function() {
            //		    	console.debug("Report could not be generated, please try again later.  "+err.message);
            contractListDiv.innerHTML = "Report could not be generated, please try again later.";
        },
        // The complete handler
        handle: function() {
            // hasBeenSent = true;
        }
    });


}