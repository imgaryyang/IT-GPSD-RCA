/**<pre> 
 *==========================================================================
 *
 * Copyright: (C) IBM Corporation 2014 -- IBM Internal Use Only
 *
 *==========================================================================
 *
 *    FILE: monthly-1.0.js
 *    CREATOR:Waqar Malik
 *    DEPT: GBS PAK
 *    DATE: 01/07/2013
 *
 *-PURPOSE-----------------------------------------------------------------
 * 
 *--------------------------------------------------------------------------
 *
 *
 *-CHANGE LOG--------------------------------------------------------------
 * 01/07/2013Waqar Malik Initial coding.
 * 03/09/2014Waqar Malik.
 *==========================================================================
 * </pre> */

function hide(id) {
	try {
		dojo.byId(id).style.display = 'none';
	} catch (err) {
		console.error("Error hide(" + id + ") => " + err.message);
	}
}

function show(id) {
	try {
		dojo.byId(id).style.display = 'block';
	} catch (err) {
		console.error("Error show(" + id + ") => " + err.message);
	}
}

function showInLine(id) {
	try {
		dojo.byId(id).style.display = 'table-row';
	} catch (err) {
		console.error("Error show(" + id + ") => " + err.message);
	}
}

function showOrHide(source, dest) {
	try {
		if (dojo.byId(source).checked) {
			show(dest);
		} else {
			hide(dest);
		}
	} catch (err) {
		console.info("Error showOrHide(" + source + ", " + dest + ") => "
				+ err.message);
	}

}

function getById(id) {
	try {
		return dojo.byId(id);
	} catch (err) {
		console.warn("Error getById(" + id + ") => " + err.message);
	}
}

function setText(node, text){
	try {
		node = getById(node);
	    node.innerHTML = text;
	} catch (err) {
		console.warn("Error setText(" + node + ", "+text+") => " + err.message);
	}
}
// /////////////////////////// search filter // /////////////////////////////////////
function getMothlyStatusList() {
	// Local var representing if the form has been sent at all
	// var hasBeenSent = false;
	// Local var representing node to be updated
	var contractListDiv = dojo.byId("contractList");
	// Using dojo.xhrPost, as the amount of data sent could be large
	dojo
			.xhrPost({
				// The URL of the request
				// url: "contractList.htm",
				form : dojo.byId("searchFilterForm"),
				// The success handler
				load : function(response) {
					try {
						contractListDiv.innerHTML = response;
					} catch (err) {
						console.debug("Error contractListDiv:  " + err.message);
						console.debug("response:  " + response);
					}

				},
				// The error handler
				error : function() {
					contractListDiv.innerHTML = "Your search could not be performed, please try again.";
				},
				// The complete handler
				handle : function() {
					// hasBeenSent = true;
				}
			});
}


function getRcaList() {

    alert('calling getRcaList()');
	// Local var representing if the form has been sent at all
	// var hasBeenSent = false;
	// Local var representing node to be updated
	var contractListDiv = dojo.byId("contractList");
	// Using dojo.xhrPost, as the amount of data sent could be large
	dojo
			.xhrPost({
				// The URL of the request
				// url: "contractList.htm",
				form : dojo.byId("searchFilterForm"),
				// The success handler
				load : function(response) {
					try {
						contractListDiv.innerHTML = response;
					} catch (err) {
						console.debug("Error contractListDiv:  " + err.message);
						console.debug("response:  " + response);
					}
				},
				// The error handler
				error : function() {
					contractListDiv.innerHTML = "Your search could not be performed, please try again.";
				},
				// The complete handler
				handle : function() {
					// hasBeenSent = true;
				}
			});
}

function clearMonthlyFilters() {
	getById('month').value = "";
	getById('year').value = "";
	getById('contract').value = "";
	getById('lob').value = "";
	getById('stage').value = "";
	getById('tower').value = "";
	getById('sector').value = "";
	getById('customer').value = "";
	getById('country').value = "";
	getById('geo').value = "";
	getById('iot').value = "";
	getById('imt').value = "";
}

// ///////////////////////// end search filter // /////////////////////////////////////

function calcSLASummary(processId) {
	var totalNoSLAs = dojo.byId("totalNoSLAs").value;
	var totals = 0;
	var totalsG = 0;
	var totalsY = 0;
	var totalsR = 0;
	var totalNetPenalty = 0;
	var omsla_greenMade;

	var omsla_greenMade_i;
	var omsla_missed_i;
	var rag_status = "Green";

	var subTotal = 0;

	hide(processId+"_sla_exclude_section");
	omsla_greenMade = dojo.byId("omsla_greenMade_"+processId).value;
	omsla_missed_ = dojo.byId("omsla_missed_"+processId).value;

	if (omsla_greenMade != "") {
		omsla_greenMade_i = parseInt(omsla_greenMade);
		subTotal += omsla_greenMade_i;
		totalsG += omsla_greenMade_i;
	}

	if (omsla_missed_ != "") {
		omsla_missed_i = parseInt(omsla_missed_);
		if (omsla_missed_i > 0) {
			rag_status = "Red";
		}
	}
	
	console.log(dojo.byId("rag_sla_"+processId));
	
	try {
		if (rag_status == "Green") {
			dojo.byId(processId+"_process_rag_status").className = "rag_green";
			dojo.byId(processId+"_process_rag_status").innerHTML = "Green";
			dojo.byId("rag_sla_"+processId).value = 'G';
		}
		if (rag_status == "Amber") {
			dojo.byId("rag_status").className = "rag_amber";
			dojo.byId("rag_status").innerHTML = "Amber";
			dojo.byId("rag_sla_"+processId).value = 'A';
		}
		if (rag_status == "Red") {
			dojo.byId(processId+"_process_rag_status").className = "rag_red";
			dojo.byId(processId+"_process_rag_status").innerHTML = "Red";
			dojo.byId("rag_sla_"+processId).value = 'R';
			showInLine(processId+"_sla_exclude_section");
		}
	} catch (err) {
		console.log("error: " + err.message);
	}
	
	try {
		setOverAllStatus();
	} catch (err) {
		console.log("error: " + err.message);
	}
//	dojo.byId("slaTotals").innerHTML = totals;
//	dojo.byId("slaTotalGreen").innerHTML = totalsG;
//	dojo.byId("slaTotalRed").innerHTML = totalsR;
//	dojo.byId("percentMissed").innerHTML = Math.round(((totalsR / totals) * 100) * 100)/ 100 + "%";
//	dojo.byId("percentMade").innerHTML = Math.round(((totalsG / totals) * 100) * 100)/ 100 + "%";
//	dojo.byId("totalNetPenalty").innerHTML = totalNetPenalty;
}

function calcSLOSummary(processId) {
	var totalsG = 0;
	var omslo_greenMade;

	var omslo_greenMade_i;
	var omslo_missed_i;
	var rag_status = "Green";

	var subTotal = 0;

	hide(processId+"_slo_exclude_section");
	omslo_greenMade = dojo.byId("omslo_greenMade_"+processId).value;
	var omslo_missed_ = dojo.byId("omslo_missed_"+processId).value;

	if (omslo_greenMade != "") {
		omslo_greenMade_i = parseInt(omslo_greenMade);
		subTotal += omslo_greenMade_i;
		totalsG += omslo_greenMade_i;
	}

	if (omslo_missed_ != "") {
		omslo_missed_i = parseInt(omslo_missed_);
		if (omslo_missed_i > 0) {
			rag_status = "Red";
		}
	}
	
	console.log(dojo.byId("rag_slo_"+processId));
	
	try {
		if (rag_status == "Green") {
			dojo.byId(processId+"_process_rag_status").className = "rag_green";
			dojo.byId(processId+"_process_rag_status").innerHTML = "Green";
			dojo.byId("rag_slo_"+processId).value = 'G';
		}
		if (rag_status == "Amber") {
			dojo.byId("rag_status").className = "rag_amber";
			dojo.byId("rag_status").innerHTML = "Amber";
			dojo.byId("rag_slo_"+processId).value = 'A';
		}
		if (rag_status == "Red") {
			dojo.byId(processId+"_process_rag_status").className = "rag_green";
			dojo.byId(processId+"_process_rag_status").innerHTML = "Green";
			dojo.byId("rag_slo_"+processId).value = 'G';
			showInLine(processId+"_slo_exclude_section");
		}
		calcSLASummary(processId);
	} catch (err) {
		console.log("error: " + err.message);
	}
	
	try {
		setOverAllStatus();
	} catch (err) {
		console.log("error: " + err.message);
	}
//	dojo.byId("slaTotals").innerHTML = totals;
//	dojo.byId("slaTotalGreen").innerHTML = totalsG;
//	dojo.byId("slaTotalRed").innerHTML = totalsR;
//	dojo.byId("percentMissed").innerHTML = Math.round(((totalsR / totals) * 100) * 100)/ 100 + "%";
//	dojo.byId("percentMade").innerHTML = Math.round(((totalsG / totals) * 100) * 100)/ 100 + "%";
//	dojo.byId("totalNetPenalty").innerHTML = totalNetPenalty;
}

function expandSLATable(source, rowNo, slaId, processId) {
	if (dojo.byId(source).checked) {
		show("sla_table_" + slaId);
		if (dojo.byId(slaId + '_isSlaExcludedN').checked) {
			show(slaId + '_sla_exclude_tab');
		}
		if (dojo.byId(slaId + '_isSlaExcludedY').checked) {
			show(slaId + '_burn_in_tab');
			if (dojo.byId(slaId + '_isBurnIn').checked) {
				show(slaId + '_burn_in_period');
				calculateSlaBurnPeriod(slaId);
			}
		}

	} else {
		hide("sla_table_" + slaId);
	}
}

function slaManOr(rag, rowNo, slaId, processId) {
	rag = rag.trim();
	console.debug("slaManOr() => slaId = " + slaId + ", rag=" + rag);
	var overiden = getById(slaId + "_isSlaOveriden");
	if (rag == 'Green' || rag == 'Red' || rag == 'Amber') {
		overiden.value = 'Y';
		console.debug("_isSlaOveriden.value = Y");
	} else {
		overiden.value = 'N';
	}
	calcNewSLASummary(rowNo, slaId, processId);
}

function sloManOr(rag, rowNo, sloId, processId) {
	rag = rag.trim();
	var overiden = getById(sloId + "_isSloOveriden");
	if (rag == 'Green' || rag == 'Red' || rag == 'Amber') {
		overiden.value = 'Y';
		console.debug("_isSloOveriden.value = Y");
	} else {
		overiden.value = 'N';
	}
	calcNewSLOSummary(rowNo, sloId, processId);
}

function setSLORagValue(id, value) {
	if (value == "") {
		getById(id + "_slo_rag_status").className = "rag_none";
		getById(id + "_slo_rag_status").innerHTML = "N/A";
		getById(id + "_slo_ragStatus").value = "N";
	} else if (value == "Red") {
		dojo.byId(id + "_slo_rag_status").className = "rag_red";
		dojo.byId(id + "_slo_rag_status").innerHTML = "Red";
		dojo.byId(id + "_slo_ragStatus").value = "R";
	} else if (value == "Amber") {
		dojo.byId(id + "_slo_rag_status").className = "rag_amber";
		dojo.byId(id + "_slo_rag_status").innerHTML = "Amber";
		dojo.byId(id + "_slo_ragStatus").value = "A";
	} else if (value == "Green") {
		dojo.byId(id + "_slo_rag_status").className = "rag_green";
		dojo.byId(id + "_slo_rag_status").innerHTML = "Green";
		dojo.byId(id + "_slo_ragStatus").value = "G";
	}

}

function setSLARagValue(id, value) {
	if (value == "") {
		getById(id + "_sla_rag_status").className = "rag_none";
		getById(id + "_sla_rag_status").innerHTML = "N/A";
		getById(id + "_sla_ragStatus").value = "N";
	} else if (value == "Red") {
		dojo.byId(id + "_sla_rag_status").className = "rag_red";
		dojo.byId(id + "_sla_rag_status").innerHTML = "Red";
		dojo.byId(id + "_sla_ragStatus").value = "R";
	} else if (value == "Amber") {
		dojo.byId(id + "_sla_rag_status").className = "rag_amber";
		dojo.byId(id + "_sla_rag_status").innerHTML = "Amber";
		dojo.byId(id + "_sla_ragStatus").value = "A";
	} else if (value == "Green") {
		dojo.byId(id + "_sla_rag_status").className = "rag_green";
		dojo.byId(id + "_sla_rag_status").innerHTML = "Green";
		dojo.byId(id + "_sla_ragStatus").value = "G";
	}

}

function calcNewSLASummary(rowNo, slaId, processId) {
	console.debug("calcNewSLASummary('slaId') => " + slaId);
	var _expectedSla;
	var _minimumSla;
	var _actualSla;

	var _isBurnIn;
	var totalSLA;

	var noExpectedSla = 0;
	var noMinimumSla = 0;
	var noActualSla = 0;

	var overiden = getById(slaId + "_isSlaOveriden");

	_expectedSla = dojo.byId(slaId + "_expectedSla").value;
	_minimumSla = dojo.byId(slaId + "_minimumSla").value;
	_actualSla = dojo.byId(slaId + "_actualSla").value;

	_isBurnIn = dojo.byId(slaId + "_isBurnIn").checked;
	totalSLA = parseInt(dojo.byId("no_sla_" + processId).innerHTML);

	if (_expectedSla.trim() != "") {
		noExpectedSla = parseFloat(_expectedSla);
	}
	if (_minimumSla.trim() != "") {
		noMinimumSla = parseFloat(_minimumSla);
	}
	if (_actualSla.trim() != "") {
		noActualSla = parseFloat(_actualSla);
	}

	if (overiden.value == 'Y') {
		var manRag = getById(slaId + "_man_rag").value;
		console.debug("overiden.value => " + overiden.value + ", manRag = "
				+ manRag);
		if (manRag == 'Red') {
			setSLARagValue(processId + "_" + rowNo, "Red");
			show(slaId + "_sla_exclude_section");
		} else if (manRag == 'Amber') {
			clearSlaExcludedFields(slaId);
			setSLARagValue(processId + "_" + rowNo, "Amber");
		} else if (manRag == 'Green') {
			clearSlaExcludedFields(slaId);
			setSLARagValue(processId + "_" + rowNo, "Green");
		} else {
			setSLARagValue(processId + "_" + rowNo, "");
		}
	} else {
		if (_actualSla.trim() == "") {
			console.debug(processId + "_" + rowNo + " actualSla is empty..");
			setSLARagValue(processId + "_" + rowNo, "");
		} else if (noActualSla < noMinimumSla || noActualSla < noExpectedSla) {
			console.debug(processId + "_" + rowNo + " status is red.");
			setSLARagValue(processId + "_" + rowNo, "Red");
			show(slaId + "_sla_exclude_section");
		} else if (noActualSla >= noMinimumSla && noActualSla >= noExpectedSla) {
			console.debug(slaId + " status is G");
			setSLARagValue(processId + "_" + rowNo, "Green");
			if (noActualSla >= noMinimumSla) {
				clearSlaExcludedFields(slaId);
			} else if (!dojo.byId(slaId + "_isSlaExcludedY").checked) {
				// show(slaId+"_sla_exclude_section");
			}
		}
	}
	if (dojo.byId(slaId + "_isSlaExcludedN").checked) {
		setSLARagValue(processId + "_" + rowNo, "Red");
	}
	if (dojo.byId(slaId + "_isSlaExcludedY").checked) {
		setSLARagValue(processId + "_" + rowNo, "Green");
	}
	showHideSlaExclude(slaId);
	var rag_status = "N/A";
	var sla_status = "";
	for (var j = 0; j < totalSLA; j++) {
		sla_status = dojo.byId(processId + "_" + j + "_sla_rag_status").innerHTML;
		if (rag_status == "N/A") {
			rag_status = sla_status;
		} else if (rag_status == "Green"
				&& (sla_status == "Amber" || sla_status == "Red")) {
			rag_status = sla_status;
		} else if (rag_status == "Amber" && (sla_status == "Red")) {
			rag_status = sla_status;
		}
	}

	dojo.byId(processId + "_process_rag_status").innerHTML = rag_status;
	if (rag_status == "N/A") {
		dojo.byId(processId + "_process_rag_status").className = "rag_none";
	} else if (rag_status == "Green") {
		dojo.byId(processId + "_process_rag_status").className = "rag_green";
	} else if (rag_status == "Amber") {
		dojo.byId(processId + "_process_rag_status").className = "rag_amber";
	} else {
		dojo.byId(processId + "_process_rag_status").className = "rag_red";
	}

	try {
		setOverAllStatus();
	} catch (err) {
		console.log("error: " + err.message);
	}

}

function calcNewSLOSummary(rowNo, sloId, processId) {
	console.debug("calcNewSLOSummary('sloId') => " + sloId);
	var _isBurnIn;
	var totalSLA;

	var _expectedSlo;
	var _minimumSlo;
	var _actualSlo;

	var noExpectedSlo = 0;
	var noMinimumSlo = 0;
	var noActualSlo = 0;

	// Triger OM rag status calculation.
	var overiden = getById(sloId + "_isSloOveriden");

	_expectedSlo = dojo.byId(sloId + "_expectedSlo").value;
	_minimumSlo = dojo.byId(sloId + "_minimumSlo").value;
	_actualSlo = dojo.byId(sloId + "_actualSlo").value;

	_isBurnIn = getById(sloId + "_isBurnIn").checked;
	totalSLA = parseInt(dojo.byId("no_slo_" + processId).innerHTML);

	if (_expectedSlo.trim() != "") {
		noExpectedSlo = parseFloat(_expectedSlo);
	}
	if (_minimumSlo.trim() != "") {
		noMinimumSlo = parseFloat(_minimumSlo);
	}
	if (_actualSlo.trim() != "") {
		noActualSlo = parseFloat(_actualSlo);
	}

	console.debug("noActualSlo=" + noActualSlo + ",  noMinimumSlo="
			+ noMinimumSlo + ", noExpectedSlo=" + noExpectedSlo);
	console.debug(sloId + " overiden.value=" + overiden.value);
	if (overiden.value == 'Y') {
		var manRag = getById(sloId + "_man_rag").value;
		console.debug(sloId + " manRag=" + manRag);
		setSLORagValue(processId + "_" + rowNo, "");
		if (manRag == 'Red') {
			setSLORagValue(processId + "_" + rowNo, "Red");
			show(sloId + "_slo_exclude_section");
		} else if (manRag == 'Amber') {
			clearSloExcludedFields(sloId);
			setSLORagValue(processId + "_" + rowNo, "Amber");
		} else if (manRag == 'Green') {
			clearSloExcludedFields(sloId);
			setSLORagValue(processId + "_" + rowNo, "Green");
		} else {
			setSLORagValue(processId + "_" + rowNo, "");
		}
	} else {
		if (_actualSlo.trim() == "") {
			setSLORagValue(processId + "_" + rowNo, "");
		} else if (noActualSlo < noMinimumSlo || noActualSlo < noExpectedSlo) {
			console.debug(sloId + " is red");
			setSLORagValue(processId + "_" + rowNo, "Red");
			show(sloId + "_slo_exclude_section");
		} else if (noActualSlo >= noMinimumSlo && noActualSlo >= noExpectedSlo) {
			console.debug(sloId + " is G");
			setSLORagValue(processId + "_" + rowNo, "Green");
			if (noActualSlo >= noMinimumSlo) {
				console.debug("hide again");
				clearSloExcludedFields(sloId);
			} else if (!getById(sloId + "_isSloExcludedY").checked) {
				// show(slaId+"_sla_exclude_section");
			}
		}
	}

	if (getById(sloId + "_isSloExcludedN").checked) {
		setSLORagValue(processId + "_" + rowNo, "Red");
	}
	if (getById(sloId + "_isSloExcludedY").checked) {
		setSLORagValue(processId + "_" + rowNo, "Green");
	}

	console.debug("_isSloExcludedN="
			+ getById(sloId + "_isSloExcludedN").checked + ", _isSloExcludedY="
			+ getById(sloId + "_isSloExcludedY").checked);

	showHideSloExclude(sloId);
	var rag_status = "Green";
	var sla_status = "";
	for (var j = 0; j < totalSLA; j++) {
		sla_status = dojo.byId(processId + "_" + j + "_slo_rag_status").innerHTML;
		if (rag_status == "N/A") {
			rag_status = sla_status;
		} else if (rag_status == "Green"
				&& (sla_status == "Amber" || sla_status == "Red")) {
			rag_status = sla_status;
		} else if (rag_status == "Amber" && (sla_status == "Red")) {
			rag_status = sla_status;
		}
	}

	dojo.byId(processId + "_process_rag_status").innerHTML = rag_status;

	if (rag_status == "N/A") {
		dojo.byId(processId + "_process_rag_status").className = "rag_none";
	} else if (rag_status == "Green") {
		dojo.byId(processId + "_process_rag_status").className = "rag_green";
	} else if (rag_status == "Amber") {
		dojo.byId(processId + "_process_rag_status").className = "rag_amber";
	} else {
		dojo.byId(processId + "_process_rag_status").className = "rag_red";
	}

	try {
		setOverAllStatus();
	} catch (err) {
		console.log("error: " + err.message);
	}
}

function clearSloExcludedFields(sloId) {
	dojo.byId(sloId + "_isSloExcludedN").checked = false;
	dojo.byId(sloId + "_isSloExcludedY").checked = false;
	getById(sloId + '_burn_details').value = '';
	getById(sloId + '_burn_start').value = '';
	getById(sloId + '_burn_end').value = '';
	hide(sloId + "_slo_exclude_section");
}

function clearSlaExcludedFields(slaId) {
	dojo.byId(slaId + "_isSlaExcludedN").checked = false;
	dojo.byId(slaId + "_isSlaExcludedY").checked = false;
	hide(slaId + "_sla_exclude_section");
}

function getAllProcesseRAG() {
	// get all process ids.
	var inputs = document.getElementsByTagName("input"); // or
															// document.forms[0].elements;
	var _processIds = []; // will contain all checked checkboxes
	for (var i = 0; i < inputs.length; i++) {
		if (inputs[i].type == "checkbox") {
			if (inputs[i].className == 'process_box') {
				_processIds.push(inputs[i]);
			}
		}
	}

	var all_process_rag = "N/A";
	var process_status = "";
	for (var i = 0; i < _processIds.length; i++) {
		var _pId = _processIds[i].value;
		_pId = _pId + "_process_rag_status";
		process_status = dojo.byId(_pId).innerHTML;
		if (all_process_rag == "N/A") {
			all_process_rag = process_status;
		} else if (all_process_rag == "Green"
				&& (process_status == "Amber" || process_status == "Red")) {
			all_process_rag = process_status;
		} else if (all_process_rag == "Amber" && (process_status == "Red")) {
			all_process_rag = process_status;
		}
	}
	console.debug("getAllProcesseRAG(): all_process_rag = " + all_process_rag);
	return all_process_rag;
}

// This method computes over all status and sets.
function setOverAllStatus() {
	// get all process ids.
	var process_status = getAllProcesseRAG();
	try {
		calculateOmRagStatus('All');
	} catch (err) {
		console.debug("Error calculateOmRagStatus('All') => " + err.message);
	}
	var ragField = dojo.byId("rag_status_om");
	var om_rag_status = ragField.innerHTML;
	console.debug("om_rag_status = " + om_rag_status);

	var isManOr = getById('om_Man_Or').checked;
	if (isManOr) {
		console.debug("No rag calculations for manual override");
		return;
	}
	if (om_rag_status == "N/A") {
		om_rag_status = process_status;
	} else if (om_rag_status == "Green"
			&& (process_status == "Amber" || process_status == "Red")) {
		om_rag_status = process_status;
	} else if (om_rag_status == "Amber" && (process_status == "Red")) {
		om_rag_status = process_status;
	}

	ragField.innerHTML = om_rag_status;
	if (om_rag_status == "N/A") {
		ragField.className = "rag_none";
		dojo.byId("om_ragStatus").value = 'N';
	} else if (om_rag_status == "Green") {
		ragField.className = "rag_green";
		dojo.byId("om_ragStatus").value = 'G';
	} else if (om_rag_status == "Amber") {
		ragField.className = "rag_amber";
		dojo.byId("om_ragStatus").value = 'A';
	} else {
		ragField.className = "rag_red";
		dojo.byId("om_ragStatus").value = 'R';
	}

	var overAllField = dojo.byId("overallStatus");
	var all_rag_status = overAllField.innerHTML;
	if (all_rag_status == "N/A") {
		all_rag_status = om_rag_status;
	} else if (all_rag_status == "Green"
			&& (om_rag_status == "Amber" || om_rag_status == "Red")) {
		all_rag_status = om_rag_status;
	} else if (all_rag_status == "Amber" && (om_rag_status == "Red")) {
		all_rag_status = om_rag_status;
	}
	console.debug("after calculations: om_rag_status = " + om_rag_status);
	console.debug("after calculations: overall_rag_status = " + all_rag_status);
	overAllField.innerHTML = all_rag_status;
}

function calcTotalSLO() {

	var totalNoSLOs = dojo.byId("totalNoSLOs").value;
	var totals = 0;
	var slos;
	for (var i = 0; i < totalNoSLOs; i++) {
		slos = dojo.byId("no_slos_" + i).value;
		if (slos != "") {
			totals += parseInt(slos);
		}
	}
	console.debug("No of SLOs: " + totalNoSLOs);
	console.debug("totals slos: " + totals);
	dojo.byId("totalSlos").innerHTML = totals;

}

function calcTotalSLOMissed() {
	var totalNoSLOs = dojo.byId("totalNoSLOs").value;
	var totals = 0;
	var mslos;
	for (var i = 0; i < totalNoSLOs; i++) {
		mslos = dojo.byId("slos_missed_" + i).value;
		if (mslos != "") {
			totals += parseInt(mslos);
		}
	}
	console.debug("totals slo incidents: " + totals);
	dojo.byId("totalSloMissed").innerHTML = totals;
}

function calcTotalSLOIncidents() {
	var totalNoSLOs = dojo.byId("totalNoSLOs").value;
	var totals = 0;
	var sloInc;
	for (var i = 0; i < totalNoSLOs; i++) {
		sloInc = dojo.byId("slo_incidents_" + i).value;
		if (sloInc != "") {
			totals += parseInt(sloInc);
		}
	}
	if (totals > 0) {
		dojo.byId("rag_status").className = "rag_amber";
		dojo.byId("rag_status").innerHTML = "Amber";
		dojo.byId("rag_status").value = 'A';
	}
	console.debug("totals slo incidents: " + totals);
	dojo.byId("totalSloIncidents").innerHTML = totals;
}

//
function showHideSLATables(source, nodeId) {
	if (dojo.byId(source).checked) {
		show(nodeId);
	} else {
		hide(nodeId);
	}
}

function showHideSlaExclude(rowNo) {
	hide(rowNo + '_sla_exclude_tab');
	hide(rowNo + '_burn_in_tab');
	if (dojo.byId(rowNo + '_isSlaExcludedN').checked) {
		show(rowNo + '_sla_exclude_tab');
	} else if (dojo.byId(rowNo + '_isSlaExcludedY').checked) {
		dojo.byId(rowNo + '_grossPenalty').value = '';
		dojo.byId(rowNo + '_netPenalty').value = '';
		dojo.byId(rowNo + '_issueDescription').value = '';
		dojo.byId(rowNo + '_correctiveAction').value = '';
		dojo.byId(rowNo + '_targetDate').value = '';
		dojo.byId(rowNo + '_actualDate').value = '';
		dojo.byId(rowNo + '_issueStatus').value = 'Active';
		clearSLAWaiverData(rowNo);
		clearSLABurnInData(rowNo);
		show(rowNo + '_burn_in_tab');
	}
}

function clearSLABurnInData(rowNo) {
	getById(rowNo + '_burn_start').value = '';
	getById(rowNo + '_burn_end').value = '';
	getById(rowNo + '_burn_details').value = '';
}
function clearSLAWaiverData(rowNo) {
	getById(rowNo + '_burn_details').value = '';

}

function showHideSloExclude(rowNo) {
	hide(rowNo + '_slo_burn_in_tab');
	if (dojo.byId(rowNo + '_isSloExcludedN').checked) {
		getById(rowNo + '_isBurnIn').checked = false;
		getById(rowNo + '_burn_details').value = '';
		getById(rowNo + '_burn_start').value = '';
		getById(rowNo + '_burn_end').value = '';
	} else if (dojo.byId(rowNo + '_isSloExcludedY').checked) {
		show(rowNo + '_slo_burn_in_tab');
		showHideSloBurnIn(rowNo);
	}
}

function showHideSlaBurnIn(rowNo) {
	if (getById(rowNo + '_isBurnIn').checked) {
		show(rowNo + '_burn_in_period');
		clearSLAWaiverData(rowNo);
	} else {
		hide(rowNo + '_burn_in_period');
		clearSLABurnInData(rowNo);
	}
}

function showHideSloBurnIn(rowNo) {
	if (dojo.byId(rowNo + '_isBurnIn').checked) {
		show(rowNo + '_burn_in_period');
	} else {
		hide(rowNo + '_burn_in_period');
		getById(rowNo + '_burn_start').value = '';
		getById(rowNo + '_burn_end').value = '';
	}
}

function calculateSlaBurnPeriod(rowNo) {
	var _burn_start = dojo.byId(rowNo + '_burn_start');
	var _burn_end = dojo.byId(rowNo + '_burn_end');

	if (_burn_start.value != '' && _burn_end.value != '') {
		var start = parseDate(_burn_start.value);
		var end = parseDate(_burn_end.value);
		var days = dateDifference(end, start);
		dojo.byId(rowNo + '_burn_period').innerHTML = humanise(days);
		console.debug(start + ' ' + end);
		console.debug('days = ' + days);
		console.debug(humanise(days));
	}
}

function calculateSloBurnPeriod(rowNo) {
	var _burn_start = dojo.byId(rowNo + '_burn_start');
	var _burn_end = dojo.byId(rowNo + '_burn_end');

	if (_burn_start.value != '' && _burn_end.value != '') {
		var _start = parseDate(_burn_start.value);
		var _end = parseDate(_burn_end.value);
		var days = dateDifference(_end, _start);
		dojo.byId(rowNo + '_burn_period').innerHTML = humanise(days);
		console.debug(_start + ' ' + _end);
		console.debug('days = ' + days);
		console.debug(humanise(days));
	}
}

function dateDifference(_startDate, _endDate) {

	// The number of milliseconds in one day
	var ONE_DAY = 1000 * 60 * 60 * 24;

	// Convert both dates to milliseconds
	var date1_ms = _startDate.getTime();
	var date2_ms = _endDate.getTime();

	// Calculate the difference in milliseconds
	var difference_ms = Math.abs(date1_ms - date2_ms);

	// Convert back to days and return
	return Math.round(difference_ms / ONE_DAY);

}

function humanise(total_days) {
	// var total_days = 1001;
	var date_current = new Date();
	var utime_target = date_current.getTime() + total_days * 86400 * 1000;
	var date_target = new Date(utime_target);

	var diff_year = parseInt(date_target.getUTCFullYear()
			- date_current.getUTCFullYear());
	var diff_month = parseInt(date_target.getUTCMonth()
			- date_current.getUTCMonth());
	var diff_day = parseInt(date_target.getUTCDate()
			- date_current.getUTCDate());

	var days_in_month = [ 31, (date_target.getUTCFullYear() % 4 ? 29 : 28), 31,
			30, 31, 30, 31, 31, 30, 31, 30, 31 ];
	var date_string = "";
	while (true) {
		date_string = "";
		date_string += (diff_year > 0 ? diff_year + " Year(s) " : "");

		if (diff_month < 0) {
			diff_year -= 1;
			diff_month += 12;
			continue;
		}
		date_string += (diff_month > 0 ? diff_month + " Month(s) " : "");

		if (diff_day < 0) {
			diff_month -= 1;
			diff_day += days_in_month[((11 + date_target.getUTCMonth()) % 12)];
			continue;
		}
		date_string += (diff_day > 0 ? diff_day + " Day(s) " : "");
		break;
	}
	console.log(date_string);
	return date_string;
}

function calcBusinessDays(dDate1, dDate2) { // input given as Date objects
	var iWeeks, iDateDiff, iAdjust = 0;
	if (dDate2 < dDate1)
		return -1; // error code if dates transposed
	var iWeekday1 = dDate1.getDay(); // day of week
	var iWeekday2 = dDate2.getDay();
	iWeekday1 = (iWeekday1 == 0) ? 7 : iWeekday1; // change Sunday from 0 to 7
	iWeekday2 = (iWeekday2 == 0) ? 7 : iWeekday2;
	if ((iWeekday1 > 5) && (iWeekday2 > 5))
		iAdjust = 1; // adjustment if both days on weekend
	iWeekday1 = (iWeekday1 > 5) ? 5 : iWeekday1; // only count weekdays
	iWeekday2 = (iWeekday2 > 5) ? 5 : iWeekday2;

	// calculate differnece in weeks (1000mS * 60sec * 60min * 24hrs * 7 days =
	// 604800000)
	iWeeks = Math.floor((dDate2.getTime() - dDate1.getTime()) / 604800000);

	if (iWeekday1 <= iWeekday2) {
		iDateDiff = (iWeeks * 5) + (iWeekday2 - iWeekday1);
	} else {
		iDateDiff = ((iWeeks + 1) * 5) - (iWeekday1 - iWeekday2);
	}

	iDateDiff -= iAdjust; // take into account both days on weekend

	return (iDateDiff + 1); // add 1 because dates are inclusive
}

function parseDate(input) {
	var parts = input.split('-');
	// new Date(year, month [, date [, hours[, minutes[, seconds[, ms]]]]])
	return new Date(parts[2], parts[1] - 1, parts[0]); // months are 0-based
}

function showKCPI(source, dest) {
	if (dojo.byId(source).checked) {
		var noSla = dojo.byId("no_sla_" + dest);
		var totalSLA = 1;
		if(noSla != null || noSla.innerHTML != ''){
			totalSLA = parseInt(noSla.innerHTML);
		}
		if(totalSLA == 0 ||  isNaN(totalSLA)){
			totalSLA = 1;
		}
		var totalSLO = parseInt(dojo.byId("no_slo_" + dest).innerHTML);
		if (totalSLA >= 0 || totalSLO > 0) {
			console.log('displaying sla/slo div: ' + dest.concat('_table'));
			show(dest.concat('_table'));
		} else {
			console.log('totalSLA = '+totalSLA+", totalSLO="+totalSLO);
			console.log('There is no sla/slo to display.');
		}
	} else {
		hide(dest + '_table');
	}
}

function displaySLA(source, dest) {
	if (dojo.byId(source).checked) {
		show(dest.concat('_dataTable'));
	} else {
		hide(dest + '_dataTable');
	}
}

function displayOldSLA(source, dest) {
	if (dojo.byId(source).checked) {
		show(dest.concat('_table'));
	} else {
		hide(dest + '_table');
	}
}

function displaySLO(source, dest) {
	if (dojo.byId(source).checked) {
		show(dest.concat('_sloDataTable'));
	} else {
		hide(dest + '_sloDataTable');
	}
}

function displayIncident(source, dest, procRow) {
	if (dojo.byId(source).checked) {
		getIncidents(dest, dest, procRow, 'get');
		show(dest.concat('_inciTable'));
		show(dest.concat('_inciDataTable'));
	} else {
		hide(dest + '_inciDataTable');
		hide(dest + '_inciTable');

	}
}

function addRowIncident(processId, itemName, procRow) {
	saveIncidents();
	getIncidents(processId, itemName, procRow, 'add');
}

// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// ///////////////////////////////////////////////////////////////// monthly
// form
// ////////////////////////////////////////////////////////////////////////////////////////
// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

var bizCount = 0;
var finCount = 0;
var custCount = 0;
var omCount = 0;
var overallCount = 0;

function initOnLoad() {
	console.debug("initOnLoad().........initializing form");
	attritionVoice();
	attritionNonVoice();
	showOrHideOnLoad();
	loadOmRag();
	loadFinRag();
	checkCRMR();
	loadCustRag();
	loadBizRag();
	initCRMR();
	loadOverallRag();
	calculateBizRagStatus();
	calculateCustRagStatus();
	calculateOmRagStatus('All');
	showHideTransitionKeys();
	calculateRagStatus();
	loadRag();
	showOrHideOverallOnLoad();
	calculateOverall();
	hideAll();
	show('os');
	setRag();
	setRagForOr();
	try {
		setOverAllStatus();
	} catch (err) {
		console.log("error: " + err.message);
	}

	try {
		console.log("displaying sla dates..........");
		for (var i = 0; i < window.slas.length; i++) {
			console.info("display calendar for => " + slas[i]);
			// display calendar
			dispDate(slas[i] + '_targetDate');
			dispDate(slas[i] + '_actualDate');
		}
	} catch (error) {
		console.error(error.message);
	}
	console.debug("End initOnLoad()");
}

function deleteRow(tableID) {
	try {
		var table = getById(tableID);
		var rowCount = table.rows.length;

		for (var i = 0; i < rowCount; i++) {
			var row = table.rows[i];
			var chkbox = row.cells[0].childNodes[0];
			if (null != chkbox && true == chkbox.checked) {
				table.deleteRow(i);
				rowCount--;
				i--;
			}
		}
	} catch (error) {
		console.error("Error deleteRow(" + tableID + ") => " + error.message);
	}
}

function initBeforeSave() {
	console.debug("initBeforeSave().........");
	calculateOmRagStatus('All');
	calculateOverall();
	setRag();
	setRagForOr();
	try {
		setOverAllStatus();
	} catch (err) {
		console.log("error: " + err.message);
	}

	console.debug("End initBeforeSave()");
}

function dispDate(id) {
	$('#' + id).datepicker({
		dateFormat : 'dd-mm-yy',
		showOn : "button",
		buttonImage : "images/calendar_icon.gif",
		buttonImageOnly : true
	});
}

function setIsFileDelete(isFileDelete) {
	dojo.byId("isDeleted_" + isFileDelete).value = 'Y';
}

function calculateOmRagStatus(caller) {
	console.debug("calculateOmRagStatus() = " + caller);
	var omRag = "N";
	var isManOr = getById('om_Man_Or');
	var overallRag = new Array();

	if (!isManOr.checked) {
		overallRag[0] = getById('tempRag').value;
		overallRag[1] = getById('tempRag1').value;
		overallRag[2] = getById('tempRag2').value;
		overallRag[3] = getById('tempRag3').value;
		overallRag[4] = getById('tempRag4').value;
		overallRag[5] = getById('tempRag5').value;

		if (caller == "transition" || caller == "All") {
			var isNotTransition = getById('transitionN').checked;

			if (!isNotTransition) {
				overallRag[0] = "G";
				getById('tempRag').value = "G";
				for (var i = 0; i < 7; i++) {
					var rag = getById("transitionRag_" + i).value;
					if (rag == "R") {
						overallRag[0] = "R";
						getById('tempRag').value = "R";
						break;
					} else if (rag == "A") {
						overallRag[0] = "A";
						getById('tempRag').value = "A";
					}
				}
			} else if (isNotTransition) {
				overallRag[0] = "N";
				getById('tempRag').value = "N";
			}
		}

		if (caller == "resources" || caller == "All") {
			var motivated = getById('motivated').checked;
			var improving = getById('improving').checked;
			var critical = getById('critical').checked;

			if (motivated) {
				overallRag[1] = "G";
				getById('tempRag1').value = "G";
			} else if (improving) {
				overallRag[1] = "A";
				getById('tempRag1').value = "A";
			} else if (critical) {
				overallRag[1] = "R";
				getById('tempRag1').value = "R";
			} else {
				overallRag[1] = "N";
				getById('tempRag1').value = "N";
			}
		}
		if (caller == "resourceStrength" || caller == "All") {
			resourceY = getById('resourceStrengthY').checked;
			resourceN = getById('resourceStrengthN').checked;

			if (resourceY) {
				overallRag[2] = "A";
				getById('tempRag2').value = "R";
			} else if (resourceN) {
				overallRag[2] = "G";
				getById('tempRag2').value = "G";
			} else {
				overallRag[2] = "N";
				getById('tempRag2').value = "N";
			}
		}
		if (caller == "referenceability" || caller == "All") {
			greenReferenceable = getById('greenReferenceable').checked;
			redReferenceable = getById('redReferenceable').checked;
			transition = getById('transition').checked;
			renegotiation = getById('renegotiation').checked;
			contractuallyPrevented = getById('contractuallyPrevented').checked;

			if (greenReferenceable) {
				overallRag[3] = "G";
				getById('tempRag3').value = "G";
			} else if (redReferenceable) {
				overallRag[3] = "R";
				getById('tempRag3').value = "R";
			} else if (transition) {
				overallRag[3] = "G";
				getById('tempRag3').value = "G";
			} else if (renegotiation) {
				overallRag[3] = "G";
				getById('tempRag3').value = "G";
			} else if (contractuallyPrevented) {
				overallRag[3] = "G";
				getById('tempRag3').value = "G";
			} else {
				overallRag[3] = "N";
				getById('tempRag3').value = "N";
			}
		}
		if (caller == "attritionG") {
			overallRag[4] = "G";
			getById('tempRag4').value = "G";
		} else if (caller == "attritionR") {
			overallRag[4] = "R";
			getById('tempRag4').value = "R";
		} else if (caller == "attritionA") {
			// console.debug("setting overallRag[4]=A, caller="+caller);
			overallRag[4] = "A";
			getById('tempRag4').value = "A";
		}

		if (caller == "attritionNonG") {
			overallRag[5] = "G";
			getById('tempRag5').value = "G";
		} else if (caller == "attritionNonR") {
			overallRag[5] = "R";
			getById('tempRag5').value = "R";
		} else if (caller == "attritionNonA") {
			// console.debug("setting overallRag[5]=A, caller="+caller);
			overallRag[5] = "A";
			getById('tempRag5').value = "A";
		}

		for (var i = 0; i < 8; i++) {
			if (overallRag[i] == "G") {
				omRag = "G";
				break;
			} else if (overallRag[i] == "N") {
				omRag = "N";
			}
		}
		for (var i = 0; i < 8; i++) {
			if (overallRag[i] == "R") {
				omRag = "R";
				break;
			} else if (overallRag[i] == "A") {
				console.debug("set omRag = overallRag[" + i + "] = "
						+ overallRag[i]);
				omRag = "A";
			}
		}

		// console.debug("before calculations: omRag = "+omRag);
		// set om status from SLA/SLO.
		var process_status = getAllProcesseRAG();
		if (omRag == "A" && process_status == "Red") {
			omRag = "R";
		} else if (omRag == "G" && process_status == "Red") {
			omRag = "R";
		} else if (omRag == "G" && process_status == "Amber") {
			omRag = "A";
		} else if (omRag == "N") {
			console.debug("omRag is N. setting om status to SLA/SLO");
			if (process_status == "Red") {
				omRag = "R";
			} else if (process_status == "Amber") {
				omRag = "A";
			} else if (process_status == "Green") {
				omRag = "G";
			}
		}

		// console.debug("after calculations: omRag = "+omRag);

		if (omRag == "G") {
			getById('rag_status_om').innerHTML = "Green";
			getById('rag_status_om').className = "rag_green";
			hide('issueCorrectiveOm');
		} else if (omRag == "A") {
			getById('rag_status_om').innerHTML = "Amber";
			getById('rag_status_om').className = "rag_amber";
			show('issueCorrectiveOm');
		} else if (omRag == "R") {
			getById('rag_status_om').innerHTML = "Red";
			getById('rag_status_om').className = "rag_red";
			show('issueCorrectiveOm');
		} else if (omRag == "N") {
			getById('rag_status_om').innerHTML = "N/A";
			getById('rag_status_om').className = "rag_none";
			hide('issueCorrectiveOm');
		}
	}
	console.debug("rag_status_om = " + getById('rag_status_om').innerHTML);
}

function showOrHideCust(id) {
	var isManOr = getById('custManOr');
	var actualRag = getById('custActualRagStatus').value;

	if (isManOr.checked) {
		if (getById('manOrScoreSelectCust').value == "green") {
			getById('custRagStatus').innerHTML = "Green";
			getById('custRagStatus').className = "rag_green";
			hide('issueCorrectiveCust');
		} else if (getById('manOrScoreSelectCust').value == "amber") {
			getById('custRagStatus').innerHTML = "Amber";
			getById('custRagStatus').className = "rag_amber";
		} else if (getById('manOrScoreSelectCust').value == "red") {
			getById('custRagStatus').innerHTML = "Red";
			getById('custRagStatus').className = "rag_red";
		}

		if (getById('custRagStatus').innerHTML == "N/A") {
			getById('custActualRagStatus').value = "NA";
		} else if (getById('custRagStatus').innerHTML == "Green") {
			getById('custActualRagStatus').value = "Green";
		} else if (getById('custRagStatus').innerHTML == "Amber") {
			getById('custActualRagStatus').value = "Amber";
		} else if (getById('custRagStatus').innerHTML == "Red") {
			getById('custActualRagStatus').value = "Red";
		}
		dojo.byId(id).style.display = 'block';
	} else {
		dojo.byId(id).style.display = 'none';
		if (actualRag == "NA") {
			getById('custRagStatus').innerHTML = "N/A";
			getById('custRagStatus').className = "rag_none";
		} else if (actualRag == "Green") {
			getById('custRagStatus').innerHTML = "Green";
			getById('custRagStatus').className = "rag_green";
		} else if (actualRag == "Amber") {
			getById('custRagStatus').innerHTML = "Amber";
			getById('custRagStatus').className = "rag_amber";
		} else if (actualRag == "Red") {
			getById('custRagStatus').innerHTML = "Red";
			getById('custRagStatus').className = "rag_red";
		}
		calculateCustRagStatus();
	}
}

function showOwHideFin() {
	var rag = getById('ragStatusCurr').innerHTML;
	if (rag == 'Green') {
		hide('Summary');

	} else if (rag == 'Amber') {
		show('Summary');
	} else if (rag == 'Red') {
		show('Summary');
	}
}

function showOrHide(id) {
	var isManOr = getById('manuallyOverride');
	var actualRag = getById('finActualRagStatus').value;

	if (isManOr.checked) {
		if (getById('manOrScoreSelect').value == "green") {
			getById('ragStatusCurr').innerHTML = "Green";
			getById('ragStatusCurr').className = "rag_green";
			hide('Summary');
		} else if (getById('manOrScoreSelect').value == "amber") {
			getById('ragStatusCurr').innerHTML = "Amber";
			getById('ragStatusCurr').className = "rag_amber";
		} else if (getById('manOrScoreSelect').value == "red") {
			getById('ragStatusCurr').innerHTML = "Red";
			getById('ragStatusCurr').className = "rag_red";
		}

		if (getById('ragStatusCurr').innerHTML == "N/A") {
			getById('finActualRagStatus').value = "NA";
		} else if (getById('ragStatusCurr').innerHTML == "Green") {
			getById('finActualRagStatus').value = "Green";
		} else if (getById('ragStatusCurr').innerHTML == "Amber") {
			getById('finActualRagStatus').value = "Amber";
		} else if (getById('ragStatusCurr').innerHTML == "Red") {
			getById('finActualRagStatus').value = "Red";
		}
		dojo.byId(id).style.display = 'block';
	} else {
		dojo.byId(id).style.display = 'none';
		if (actualRag == "NA") {
			getById('ragStatusCurr').innerHTML = "N/A";
			getById('ragStatusCurr').className = "rag_none";
		} else if (actualRag == "Green") {
			getById('ragStatusCurr').innerHTML = "Green";
			getById('ragStatusCurr').className = "rag_green";
		} else if (actualRag == "Amber") {
			getById('ragStatusCurr').innerHTML = "Amber";
			getById('ragStatusCurr').className = "rag_amber";
		} else if (actualRag == "Red") {
			getById('ragStatusCurr').innerHTML = "Red";
			getById('ragStatusCurr').className = "rag_red";
		}
		calculateRagStatus();
	}
}

function showOrHideFin(id) {
	var isManOr = getById('manuallyOverride');
	var actualRag = getById('finActualRagStatus').value;

	if (isManOr.checked) {

		if (getById('ragStatusCurr').innerHTML == "N/A") {
			getById('finActualRagStatus').value = "NA";
		} else if (getById('ragStatusCurr').innerHTML == "Green") {
			getById('finActualRagStatus').value = "Green";
		} else if (getById('ragStatusCurr').innerHTML == "Amber") {
			getById('finActualRagStatus').value = "Amber";
		} else if (getById('ragStatusCurr').innerHTML == "Red") {
			getById('finActualRagStatus').value = "Red";
		}

		if (getById('manOrScoreSelect').value == "green") {
			getById('ragStatusCurr').innerHTML = "Green";
			getById('ragStatusCurr').className = "rag_green";
			hide('Summary');
		} else if (getById('manOrScoreSelect').value == "amber") {
			getById('ragStatusCurr').innerHTML = "Amber";
			getById('ragStatusCurr').className = "rag_amber";
		} else if (getById('manOrScoreSelect').value == "red") {
			getById('ragStatusCurr').innerHTML = "Red";
			getById('ragStatusCurr').className = "rag_red";
		}

		dojo.byId(id).style.display = 'block';
	} else {

		dojo.byId(id).style.display = 'none';
		if (actualRag == "NA") {
			getById('ragStatusCurr').innerHTML = "N/A";
			getById('ragStatusCurr').className = "rag_none";
		} else if (actualRag == "Green") {
			getById('ragStatusCurr').innerHTML = "Green";
			getById('ragStatusCurr').className = "rag_green";
		} else if (actualRag == "Amber") {
			getById('ragStatusCurr').innerHTML = "Amber";
			getById('ragStatusCurr').className = "rag_amber";
		} else if (actualRag == "Red") {
			getById('ragStatusCurr').innerHTML = "Red";
			getById('ragStatusCurr').className = "rag_red";
		}

		calculateRagStatus();
	}

}

function hiddenCers() {
	var cers = getById('hiddenCers').value;
}

function showOrHideBiz(id) {
	var isManOr = getById('bizManOr');
	var actualRag = getById('bizActualRagStatus').value;

	if (isManOr.checked) {

		if (getById('bizRagStatus').innerHTML == "N/A") {
			getById('bizActualRagStatus').value = "NA";
		} else if (getById('bizRagStatus').innerHTML == "Green") {
			getById('bizActualRagStatus').value = "Green";
		} else if (getById('bizRagStatus').innerHTML == "Amber") {
			getById('bizActualRagStatus').value = "Amber";
		} else if (getById('bizRagStatus').innerHTML == "Red") {
			getById('bizActualRagStatus').value = "Red";
		}

		if (getById('manOrScoreBiz').value == "green") {
			getById('bizRagStatus').innerHTML = "Green";
			getById('bizRagStatus').className = "rag_green";
			hide('issueCorrectiveBiz');
		} else if (getById('manOrScoreBiz').value == "amber") {
			getById('bizRagStatus').innerHTML = "Amber";
			getById('bizRagStatus').className = "rag_amber";
		} else if (getById('manOrScoreBiz').value == "red") {
			getById('bizRagStatus').innerHTML = "Red";
			getById('bizRagStatus').className = "rag_red";
		}
		dojo.byId(id).style.display = 'block';
	} else {
		dojo.byId(id).style.display = 'none';
		if (actualRag == "NA") {
			getById('bizRagStatus').innerHTML = "N/A";
			getById('bizRagStatus').className = "rag_none";
		} else if (actualRag == "Green") {
			getById('bizRagStatus').innerHTML = "Green";
			getById('bizRagStatus').className = "rag_green";
		} else if (actualRag == "Amber") {
			getById('bizRagStatus').innerHTML = "Amber";
			getById('bizRagStatus').className = "rag_amber";
		} else if (actualRag == "Red") {
			getById('bizRagStatus').innerHTML = "Red";
			getById('bizRagStatus').className = "rag_red";
		}
		calculateBizRagStatus();
	}
}

function showOrHideOm(id) {
	var isManOr = getById('om_Man_Or');
	var actualRag = getById('actual_rag_om').value;

	if (isManOr.checked) {

		if (getById('rag_status_om').innerHTML == "N/A") {
			getById('actual_rag_om').value = "NA";
		} else if (getById('rag_status_om').innerHTML == "Green") {
			getById('actual_rag_om').value = "Green";
		} else if (getById('rag_status_om').innerHTML == "Amber") {
			getById('actual_rag_om').value = "Amber";
		} else if (getById('rag_status_om').innerHTML == "Red") {
			getById('actual_rag_om').value = "Red";
		}

		if (getById('manOrScoreOm').value == "green") {
			getById('rag_status_om').innerHTML = "Green";
			getById('rag_status_om').className = "rag_green";
			hide('issueCorrectiveOm');
		} else if (getById('manOrScoreOm').value == "amber") {
			getById('rag_status_om').innerHTML = "Amber";
			getById('rag_status_om').className = "rag_amber";
		} else if (getById('manOrScoreOm').value == "red") {
			getById('rag_status_om').innerHTML = "Red";
			getById('rag_status_om').className = "rag_red";
		}

		dojo.byId(id).style.display = 'block';
	} else {
		dojo.byId(id).style.display = 'none';
		if (actualRag == "NA") {
			getById('rag_status_om').innerHTML = "N/A";
			getById('rag_status_om').className = "rag_none";
		} else if (actualRag == "Green") {
			getById('rag_status_om').innerHTML = "Green";
			getById('rag_status_om').className = "rag_green";
		} else if (actualRag == "Amber") {
			getById('rag_status_om').innerHTML = "Amber";
			getById('rag_status_om').className = "rag_amber";
		} else if (actualRag == "Red") {
			getById('rag_status_om').innerHTML = "Red";
			getById('rag_status_om').className = "rag_red";
		}
	}
	calculateOmRagStatus('All');

}

function showOrHideOverall(id) {
	var isManOr = getById('overallManOr');
	var actualRag = getById('overallActualRagStatus').value;

	if (isManOr.checked) {
		if (getById('currentOverall').innerHTML == "N/A") {
			getById('overallActualRagStatus').value = "NA";
		} else if (getById('currentOverall').innerHTML == "Green") {
			getById('overallActualRagStatus').value = "Green";
		} else if (getById('currentOverall').innerHTML == "Amber") {
			getById('overallActualRagStatus').value = "Amber";
		} else if (getById('currentOverall').innerHTML == "Red") {
			getById('overallActualRagStatus').value = "Red";
		}

		if (getById('manOrScoreSelectOv').value == "green") {
			getById('currentOverall').innerHTML = "Green";
			getById('currentOverall').className = "rag_green";
			getById('overallStatus').innerHTML = "Green";
			getById('overallStatus').className = "rag_green";

		} else if (getById('manOrScoreSelectOv').value == "amber") {
			getById('currentOverall').innerHTML = "Amber";
			getById('currentOverall').className = "rag_amber";
			getById('overallStatus').innerHTML = "Amber";
			getById('overallStatus').className = "rag_amber";
		} else if (getById('manOrScoreSelectOv').value == "red") {
			getById('currentOverall').innerHTML = "Red";
			getById('currentOverall').className = "rag_red";
			getById('overallStatus').innerHTML = "Red";
			getById('overallStatus').className = "rag_red";
		}

		dojo.byId(id).style.display = 'block';
	} else {
		dojo.byId(id).style.display = 'none';
		if (actualRag == "NA") {
			getById('currentOverall').innerHTML = "N/A";
			getById('currentOverall').className = "rag_none";
		} else if (actualRag == "Green") {
			getById('currentOverall').innerHTML = "Green";
			getById('currentOverall').className = "rag_green";
		} else if (actualRag == "Amber") {
			getById('currentOverall').innerHTML = "Amber";
			getById('currentOverall').className = "rag_amber";
		} else if (actualRag == "Red") {
			getById('currentOverall').innerHTML = "Red";
			getById('currentOverall').className = "rag_red";
		}
		calculateOverall();
	}
}

function set(id, value) {
	getById(id).value = value;
}
function setFormAction(_formAction) {
	dojo.byId("formAction").value = _formAction;
}

function resetContract() {
	var r = confirm("Are you sure you want to reset the form? Resetting the form will clear all the data currently entered");
	if (r == true) {
		setFormAction('reset');
		resetForm();
		document.forms["editForm"].submit();
	} else {
	}
}

function clear(id) {
	getById(id).value = ' ';
}

function clearRadio(id) {
	getById(id).checked = false;
}

function loadOverallRag() {
	if (overallCount == 0) {
		var overallRag = getById('overallRagStatus').value;
		if (overallRag == "G") {
			getById('currentOverall').className = "rag_green";
			getById('currentOverall').innerHTML = "Green";
			getById('overallStatus').className = "rag_green";
			getById('overallStatus').innerHTML = "Green";
		} else if (overallRag == "A") {
			getById('currentOverall').className = "rag_amber";
			getById('currentOverall').innerHTML = "Amber";
			getById('overallStatus').className = "rag_amber";
			getById('overallStatus').innerHTML = "Amber";
		} else if (overallRag == "R") {
			getById('currentOverall').className = "rag_red";
			getById('currentOverall').innerHTML = "Red";
			getById('overallStatus').className = "rag_red";
			getById('overallStatus').innerHTML = "Red";
		} else {
			getById('currentOverall').className = "rag_none";
			getById('currentOverall').innerHTML = "N/A";
			getById('overallStatus').className = "rag_none";
			getById('overallStatus').innerHTML = "N/A";
		}
	}
	overallCount = overallCount + 1;
}

function loadFinRag() {
	var isManOr = getById('manuallyOverride');
	var rag = getById('ragStatusCurr').innerHTML;

	if (finCount == 0) {
		var finRag = getById('finRagStatus').value;
		if (finRag == "G") {
			getById('ragStatusCurr').className = "rag_green";
			getById('ragStatusCurr').innerHTML = "Green";
		} else if (finRag == "A") {
			getById('ragStatusCurr').className = "rag_green";
			getById('ragStatusCurr').innerHTML = "Amber";
		} else if (finRag == "R") {
			getById('ragStatusCurr').className = "rag_red";
			getById('ragStatusCurr').innerHTML = "Red";
		} else {
			getById('ragStatusCurr').className = "rag_none";
			getById('ragStatusCurr').innerHTML = "N/A";
		}
	} else if (isManOr.checked) {
		if (rag == "Green") {
			getById('manOrScoreSelect').value = 'green';
		} else if (rag == "Amber") {
			getById('manOrScoreSelect').value = 'amber';
		} else if (rag == "Red") {
			getById('manOrScoreSelect').value = 'red';
		}
	}
	finCount = finCount + 1;
}

function loadBizRag() {
	var isManOr = getById('bizManOr');
	var rag = getById('bizRagStatus').innerHTML;

	if (bizCount == 0) {
		var bizRag = getById('ragStatusBiz').value;
		if (bizRag == "G") {
			getById('bizRagStatus').className = "rag_green";
			getById('bizRagStatus').innerHTML = "Green";
		} else if (bizRag == "A") {
			getById('bizRagStatus').className = "rag_amber";
			getById('bizRagStatus').innerHTML = "Amber";
		} else if (bizRag == "R") {
			getById('bizRagStatus').className = "rag_red";
			getById('bizRagStatus').innerHTML = "Red";
		} else {
			getById('bizRagStatus').className = "rag_none";
			getById('bizRagStatus').innerHTML = "N/A";
		}
	} else if (isManOr.checked) {
		if (rag == "Green") {
			getById('manOrScoreBiz').value = 'green';
		} else if (rag == "Amber") {
			getById('manOrScoreBiz').value = 'amber';
		} else if (rag == "Red") {
			getById('manOrScoreBiz').value = 'red';
		}
	}
	bizCount = bizCount + 1;
}

function loadCustRag() {
	var isManOr = getById('custManOr');
	var rag = getById('custRagStatus').innerHTML;

	if (custCount == 0) {
		var custRag = getById('ragStatusCust').value;
		if (custRag == "G") {
			getById('custRagStatus').className = "rag_green";
			getById('custRagStatus').innerHTML = "Green";
		} else if (custRag == "A") {
			getById('custRagStatus').className = "rag_amber";
			getById('custRagStatus').innerHTML = "Amber";
		} else if (custRag == "R") {
			getById('custRagStatus').className = "rag_red";
			getById('custRagStatus').innerHTML = "Red";
		} else {
			getById('custRagStatus').className = "rag_none";
			getById('custRagStatus').innerHTML = "N/A";
		}
	} else if (isManOr.checked) {
		if (rag == "Green") {
			getById('manOrScoreSelectCust').value = 'green';
		} else if (rag == "Amber") {
			getById('manOrScoreSelectCust').value = 'amber';
		} else if (rag == "Red") {
			getById('manOrScoreSelectCust').value = 'red';
		}
	}
	custCount = custCount + 1;
}

function loadOmRag() {
	var isManOr = getById('om_Man_Or');
	var rag = getById('om_ragStatus').value;

	if (omCount == 0) {
		var omRag = getById('om_ragStatus').value;
		if (omRag == "G") {
			getById('rag_status_om').className = "rag_green";
			getById('rag_status_om').innerHTML = "Green";
		} else if (omRag == "A") {
			getById('rag_status_om').className = "rag_amber";
			getById('rag_status_om').innerHTML = "Amber";
		} else if (omRag == "R") {
			getById('rag_status_om').className = "rag_red";
			getById('rag_status_om').innerHTML = "Red";
		} else {
			getById('rag_status_om').className = "rag_none";
			getById('rag_status_om').innerHTML = "N/A";
		}
	}
	if (isManOr.checked) {
		if (rag == "G") {
			getById('manOrScoreOm').value = 'green';
		} else if (rag == "A") {
			getById('manOrScoreOm').value = 'amber';
		} else if (rag == "R") {
			getById('manOrScoreOm').value = 'red';
		}
	}
	omCount = omCount + 1;
}

function setRag() {
	var isManOrOm = getById('om_Man_Or');
	var isManOrOv = getById('overallManOr');

	if (getById('custRagStatus').innerHTML == "Green") {
		getById('ragStatusCust').value = "G";
	} else if (getById('custRagStatus').innerHTML == "Amber") {
		getById('ragStatusCust').value = "A";
	} else if (getById('custRagStatus').innerHTML == "Red") {
		getById('ragStatusCust').value = "R";
	}

	if (getById('bizRagStatus').innerHTML == "Green") {
		getById('ragStatusBiz').value = "G";
	} else if (getById('bizRagStatus').innerHTML == "Amber") {
		getById('ragStatusBiz').value = "A";
	} else if (getById('bizRagStatus').innerHTML == "Red") {
		getById('ragStatusBiz').value = "R";
	}

	if (!isManOrOm.checked) {
		if (getById('rag_status_om').innerHTML == "Green") {
			getById('om_ragStatus').value = "G";
		} else if (getById('rag_status_om').innerHTML == "Amber") {
			getById('om_ragStatus').value = "A";
		} else if (getById('rag_status_om').innerHTML == "Red") {
			getById('om_ragStatus').value = "R";
		}
	}

	if (getById('ragStatusCurr').innerHTML == "Green") {
		getById('finRagStatus').value = "G";
		getById('ragStatusCurr').className = "rag_green";
	} else if (getById('ragStatusCurr').innerHTML == "Amber") {
		getById('finRagStatus').value = "A";
		getById('ragStatusCurr').className = "rag_amber";
	} else if (getById('ragStatusCurr').innerHTML == "Red") {
		getById('finRagStatus').value = "R";
		getById('ragStatusCurr').className = "rag_red";
	}

	if (!isManOrOv.checked) {
		if (getById('currentOverall').innerHTML == "Green") {
			getById('overallRagStatus').value = "G";
		} else if (getById('currentOverall').innerHTML == "Amber") {
			getById('overallRagStatus').value = "A";
		} else if (getById('currentOverall').innerHTML == "Red") {
			getById('overallRagStatus').value = "R";
		}
	}
}

function loadRag() {
	if (getById('custRagStatus').innerHTML == "Green") {
		getById('overallCustRag').innerHTML = "Green";
		getById('overallCustRag').className = "rag_green";
	} else if (getById('custRagStatus').innerHTML == "Amber") {
		getById('overallCustRag').innerHTML = "Amber";
		getById('overallCustRag').className = "rag_amber";
	} else if (getById('custRagStatus').innerHTML == "Red") {
		getById('overallCustRag').innerHTML = "Red";
		getById('overallCustRag').className = "rag_red";
	} else if (getById('custRagStatus').innerHTML == "N/A") {
		getById('overallCustRag').innerHTML = "N/A";
		getById('overallCustRag').className = "rag_none";
	}

	if (getById('bizRagStatus').innerHTML == "Green") {
		getById('overallBizRag').innerHTML = "Green";
		getById('overallBizRag').className = "rag_green";
	} else if (getById('bizRagStatus').innerHTML == "Amber") {
		getById('overallBizRag').innerHTML = "Amber";
		getById('overallBizRag').className = "rag_amber";
	} else if (getById('bizRagStatus').innerHTML == "Red") {
		getById('overallBizRag').innerHTML = "Red";
		getById('overallBizRag').className = "rag_red";
	} else if (getById('bizRagStatus').innerHTML == "N/A") {
		getById('overallBizRag').innerHTML = "N/A";
		getById('overallBizRag').className = "rag_none";
	}

	if (getById('ragStatusCurr').innerHTML == "Green") {
		getById('overallFinRag').innerHTML = "Green";
		getById('overallFinRag').className = "rag_green";
	} else if (getById('ragStatusCurr').innerHTML == "Amber") {
		getById('overallFinRag').innerHTML = "Amber";
		getById('overallFinRag').className = "rag_amber";
	} else if (getById('ragStatusCurr').innerHTML == "Red") {
		getById('overallFinRag').innerHTML = "Red";
		getById('overallFinRag').className = "rag_red";
	} else if (getById('ragStatusCurr').innerHTML == "N/A") {
		getById('overallFinRag').innerHTML = "N/A";
		getById('overallFinRag').className = "rag_none";
	}

	if (getById('rag_status_om').innerHTML == "Green") {
		getById('overallOpMeas').innerHTML = "Green";
		getById('overallOpMeas').className = "rag_green";
	} else if (getById('rag_status_om').innerHTML == "Amber") {
		getById('overallOpMeas').innerHTML = "Amber";
		getById('overallOpMeas').className = "rag_amber";
	} else if (getById('rag_status_om').innerHTML == "Red") {
		getById('overallOpMeas').innerHTML = "Red";
		getById('overallOpMeas').className = "rag_red";
	} else if (getById('rag_status_om').innerHTML == "N/A") {
		getById('overallOpMeas').innerHTML = "N/A";
		getById('overallOpMeas').className = "rag_none";
	}
}

function calculateOverall() {
	overallOr = getById('overallManOr').value;
	finRag = getById('overallFinRag').innerHTML;
	bizRag = getById('overallBizRag').innerHTML;
	custRag = getById('overallCustRag').innerHTML;
	omRag = getById('overallOpMeas').innerHTML;

	if (!overallOr.checked) {
		var overallRag = new Array();
		overallRag[0] = finRag;
		overallRag[1] = bizRag;
		overallRag[2] = custRag;
		overallRag[3] = omRag;
		var ragStatus = "N";

		for (var i = 0; i < 4; i++) {
			if (overallRag[i] == "Green") {
				ragStatus = "G";
				break;
			}
			if (overallRag[i] == "N/A") {
				ragStatus = "N";
			}
		}
		for (var i = 0; i < 4; i++) {
			if (overallRag[i] == "Red") {
				ragStatus = "R";
				break;
			}
			if (overallRag[i] == "Amber") {
				ragStatus = "A";
			}
		}

		if (ragStatus == "G") {
			getById('overallStatus').innerHTML = "Green";
			getById('overallStatus').className = "rag_green";
			getById('currentOverall').innerHTML = "Green";
			getById('currentOverall').className = "rag_green";
			getById("currentOverall").value = "Green";
		} else if (ragStatus == "A") {
			getById('overallStatus').innerHTML = "Amber";
			getById('overallStatus').className = "rag_amber";
			getById('currentOverall').innerHTML = "Amber";
			getById('currentOverall').className = "rag_amber";
			getById("currentOverall").value = "Amber";
		} else if (ragStatus == "R") {
			getById('overallStatus').innerHTML = "Red";
			getById('overallStatus').className = "rag_red";
			getById('currentOverall').innerHTML = "Red";
			getById('currentOverall').className = "rag_red";
			getById("currentOverall").value = "Red";
		} else if (ragStatus == "N") {
			getById('overallStatus').innerHTML = "N/A";
			getById('overallStatus').className = "rag_none";
			getById('currentOverall').innerHTML = "N/A";
			getById('currentOverall').className = "rag_none";
			getById("currentOverall").value = "NA";
		}
	}
}

function hideShowDefectCodes() {
	finRag = getById('overallFinRag').value;
	bizRag = getById('overallBizRag').value;
	custRag = getById('overallCustRag').value;

	if (finRag == "Red") {
		show('finRed');
		hide('finAmber');
	} else if (finRag == "Amber") {
		show('finAmber');
		hide('finRed');
	} else {
		hide('finAmber');
		hide('finRed');
	}

	if (bizRag == "Red") {
		show('bizRed');
		hide('bizAmber');
	} else if (bizRag == "Amber") {
		show('bizAmber');
		hide('bizRed');
	} else {
		hide('bizAmber');
		hide('bizRed');
	}

	if (custRag == "Red") {
		show('custRed');
		hide('custAmber');
	} else if (custRag == "Amber") {
		show('custAmber');
		hide('custRed');
	} else {
		hide('custAmber');
		hide('custRed');
	}
}

function resetFin(id) {
	getById(id).value = "Y";
}

function calculateRagStatus() {
	var isManOr = getById('manuallyOverride');
	var GPTARGET = getById('GP_TARGET').value;
	var GPperTARGET = getById('GPP_TARGET').value;
	var GPPLAN = getById('GP_PLAN').value;
	var GPperPLAN = getById('GPP_PLAN').value;
	var FYGPPLAN = getById('FY_GP_PLAN').value;

	if (!isManOr.checked) {
		if (GPTARGET == "S" && GPPLAN == "S" && FYGPPLAN == "S") {
			getById("ragStatusCurr").className = "rag_none";
			getById("ragStatusCurr").innerHTML = "N/A";
			getById("finActualRagStatus").value = "NA";
		} else if (GPTARGET == "S" && GPPLAN == "S" && FYGPPLAN == "Y") {
			getById("ragStatusCurr").className = "rag_green";
			getById("ragStatusCurr").innerHTML = "Green";
			getById("finActualRagStatus").value = "Green";
		} else if (GPTARGET == "S" && GPPLAN == "Y" && FYGPPLAN == "S") {
			getById("ragStatusCurr").className = "rag_green";
			getById("ragStatusCurr").innerHTML = "Green";
			getById("finActualRagStatus").value = "Green";
		} else if (GPTARGET == "Y" && GPPLAN == "S" && FYGPPLAN == "S") {
			getById("ragStatusCurr").className = "rag_green";
			getById("ragStatusCurr").innerHTML = "Green";
			getById("finActualRagStatus").value = "Green";
		} else if (GPTARGET == "Y" && GPPLAN == "Y" && FYGPPLAN == "S") {
			getById("ragStatusCurr").className = "rag_green";
			getById("ragStatusCurr").innerHTML = "Green";
			getById("finActualRagStatus").value = "Green";
		} else if (GPTARGET == "Y" && GPPLAN == "S" && FYGPPLAN == "Y") {
			getById("ragStatusCurr").className = "rag_green";
			getById("ragStatusCurr").innerHTML = "Green";
			getById("finActualRagStatus").value = "Green";
		} else if (GPTARGET == "S" && GPPLAN == "Y" && FYGPPLAN == "Y") {
			getById("ragStatusCurr").className = "rag_green";
			getById("ragStatusCurr").innerHTML = "Green";
			getById("finActualRagStatus").value = "Green";
		} else {
			if (GPTARGET == "B" || GPPLAN == "B" || GPperTARGET == "Y"
					|| GPperPLAN == "Y") {
				getById("ragStatusCurr").className = "rag_amber";
				getById("ragStatusCurr").innerHTML = "Amber";
				getById("finActualRagStatus").value = "Amber";
			}
			if (GPperTARGET == "N" || GPPLAN == "N" || FYGPPLAN == "N"
					|| GPperPLAN == "N" || GPTARGET == "N") {
				getById("ragStatusCurr").className = "rag_red";
				getById("ragStatusCurr").innerHTML = "Red";
				getById("finActualRagStatus").value = "Red";
			}
			if (GPTARGET == "Y" && GPPLAN == "Y" && FYGPPLAN == "Y") {
				getById("ragStatusCurr").className = "rag_green";
				getById("ragStatusCurr").innerHTML = "Green";
				getById("finActualRagStatus").value = "Green";
			}
		}
	}
}

function showIfOpMeas() {
	var critical = getById('critical').checked;
	var transitionY = getById('transitionY').checked;
	var resourceStrengthY = getById('resourceStrengthY').checked;
	var rag = getById('rag_status_om').innerHTML;
	var isManOr = getById('om_Man_Or').checked;

	if (isManOr) {
		show('score');
	} else {
		hide('score');
	}

	if (critical) {
		show('criticalResource');
	}
	if (transitionY) {
		show('finance');
		show('hiring');
		show('training');
		show('it');
		show('facilities');
		show('delivery');
		show('transitions');
	}
	if (resourceStrengthY) {
		show('resourceStrengthDesc');
	}
	if (rag == 'Green') {
		hide('issueCorrectiveOm');
	} else if (rag == 'Amber') {
		show('issueCorrectiveOm');
	} else if (rag == 'Red') {
		show('issueCorrectiveOm');
	}
}

function showIfBiz() {
	var bizIssues = getById('bizIssues').value;
	var currentIscd = getById('currentIscd').value;
	var iscdNotFullImplY = getById('iscdNotFullImplY').checked;
	var iscdNotFullImplN = getById('iscdNotFullImplN').checked;
	var qaNA = getById('qaNA').checked;
	var qaA = getById('qaA').checked;
	var qaB = getById('qaB').checked;
	var qaC = getById('qaC').checked;
	var qaD = getById('qaD').checked;
	var rating = getById('rating').value;
	var csaIssuesY = getById('csaIssuesY').checked;
	var csaIssuesN = getById('csaIssuesN').checked;
	var isCaY = getById('isCaY').checked;
	var isCaN = getById('isCaN').checked;
	var caPassY = getById('caPassY').checked;
	var caPass = getById('caPass').checked;
	var audit = getById('audit').value;
	var kcoY = getById('kcoY').checked;
	var kcoN = getById('kcoN').checked;
	var kcoOnTrackY = getById('kcoOnTrackY').checked;
	var kcoOnTrackN = getById('kcoOnTrackN').checked;
	var bcrY = getById('bcrY').checked;
	var bcrN = getById('bcrN').checked;
	var bcrOnTrackY = getById('bcrOnTrackY').checked;
	var bcrOnTrackN = getById('bcrOnTrackN').checked;
	var prY = getById('prY').checked;
	var prN = getById('prN').checked;
	var prOnTrackY = getById('prOnTrackY').checked;
	var prOnTrackN = getById('prOnTrackN').checked;
	var dhcY = getById('dhcY').checked;
	var dataIncidents = getById('dataIncidents').value;
	var dhcScore = document.editMonthly.dhcScore.value;
	var rag = getById('bizRagStatus').innerHTML;

	if (dhcY) {
		show('dhcScoreCell');
		checkDhc();
		calculateBizRagStatus();
	}

	if (bizIssues == "N") {
		hide('bControls');
		clear('pastIssues');
		clear('pastResolution');
		calculateBizRagStatus();
	} else if (bizIssues == "T") {
		show('bControls');
		calculateBizRagStatus();
	} else if (bizIssues == "S") {
		show('bControls');
		calculateBizRagStatus();
	}

	if (currentIscd == "App impl") {
		hide('iscd');
		hide('approvalProb');
		hide('iscdDetails');
		clear('appProb');
		clearRadio('iscdNotFullImplY');
		clearRadio('iscdNotFullImplN');
		clear('iscdNFIDetails');
		calculateBizRagStatus();
	} else if (currentIscd == "App not impl") {
		show('iscd');
		hide('approvalProb');
		calculateBizRagStatus();
		clear('appProb');
	} else if (currentIscd == "Not app") {
		show('approvalProb');
		hide('iscd');
		hide('iscdDetails');
		clearRadio('iscdNotFullImplY');
		clearRadio('iscdNotFullImplN');
		clear('iscdNFIDetails');
		calculateBizRagStatus();
	} else if (currentIscd == "N/A-IA") {
		hide('approvalProb');
		hide('iscd');
		hide('iscdDetails');
		clear('appProb');
		clearRadio('iscdNotFullImplY');
		clearRadio('iscdNotFullImplN');
		clear('iscdNFIDetails');
		calculateBizRagStatus();
	} else if (currentIscd == "N/A-A") {
		hide('approvalProb');
		hide('iscd');
		hide('iscdDetails');
		clear('appProb');
		clearRadio('iscdNotFullImplY');
		clearRadio('iscdNotFullImplN');
		clear('iscdNFIDetails');
		calculateBizRagStatus();
	} else if (currentIscd == "N/A-N") {
		hide('approvalProb');
		hide('iscd');
		hide('iscdDetails');
		clear('appProb');
		clearRadio('iscdNotFullImplY');
		clearRadio('iscdNotFullImplN');
		clear('iscdNFIDetails');
		calculateBizRagStatus();
	}

	if (iscdNotFullImplY) {
		hide('iscdDetails');
		clear('iscdNFIDetails');
		calculateBizRagStatus();
	} else if (iscdNotFullImplN) {
		show('iscdDetails');
		calculateBizRagStatus();
	}

	if (qaNA) {
		hide('qaDate');
		hide('qaPrim');
		hide('qaSec');
		hide('isPmrDueToIt');
		clearRadio('isPMRN');
		clearRadio('isPMRY');
		calculateBizRagStatus();
	} else if (qaA) {
		show('qaDate');
		hide('qaPrim');
		hide('qaSec');
		hide('isPmrDueToIt');
		clearRadio('isPMRN');
		clearRadio('isPMRY');
		calculateBizRagStatus();
	} else if (qaB) {
		show('qaDate');
		hide('qaPrim');
		hide('qaSec');
		hide('isPmrDueToIt');
		clearRadio('isPMRN');
		clearRadio('isPMRY');
		calculateBizRagStatus();
	} else if (qaC) {
		show('qaDate');
		show('qaPrim');
		show('qaSec');
		show('isPmrDueToIt');
		calculateBizRagStatus();
	} else if (qaD) {
		show('qaDate');
		show('qaPrim');
		show('qaSec');
		show('isPmrDueToIt');
		calculateBizRagStatus();
	}

	if (rating == "Satis") {
		hide('csaIssuesOnTrack');
		hide('csaOpenIssues');
		clear('csaIssues');
		clearRadio('csaIssuesY');
		clearRadio('csaIssuesN');
		calculateBizRagStatus();
	} else if (rating == "Marginal") {
		show('csaIssuesOnTrack');
		calculateBizRagStatus();
	} else if (rating == "Unsatis") {
		show('csaIssuesOnTrack');
		calculateBizRagStatus();
	}

	if (csaIssuesY) {
		hide('csaOpenIssues');
		clear('csaIssues');
		calculateBizRagStatus();
	} else if (csaIssuesN) {
		show('csaOpenIssues');
		calculateBizRagStatus();
	}

	if (isCaY) {
		show('caPass');
		calculateBizRagStatus();
	} else if (isCaN) {
		hide('caPass');
		hide('failedAudit');
		hide('auditOpenIssues');
		clear('auditIssues');
		clearRadio('caPassY');
		clearRadio('caPass');
		calculateBizRagStatus();
	}

	if (caPassY) {
		hide('failedAudit');
		hide('auditOpenIssues');
		clear('auditIssues');
		calculateBizRagStatus();
	} else if (caPass) {
		show('failedAudit');
		calculateBizRagStatus();
	}

	if (audit == "select") {
		hide('auditOpenIssues');
		calculateBizRagStatus();
		clear('auditIssues');
	} else if (audit == "Confirmed") {
		hide('auditOpenIssues');
		calculateBizRagStatus();
		clear('auditIssues');
	} else if (audit == "On Target") {
		show('auditOpenIssues');
		calculateBizRagStatus();
	} else if (audit == "Not") {
		show('auditOpenIssues');
		calculateBizRagStatus();
	}

	if (kcoY) {
		show('kcoIssuesOnTrack');
		calculateBizRagStatus();
	} else if (kcoN) {
		hide('kcoIssuesOnTrack');
		hide('kcoIssues');
		clear('kcoIssuesDesc');
		clearRadio('kcoOnTrackY');
		clearRadio('kcoOnTrackN');
		calculateBizRagStatus();
	}

	if (kcoOnTrackY) {
		hide('kcoIssues');
		clear('kcoIssuesDesc');
		calculateBizRagStatus();
	} else if (kcoOnTrackN) {
		show('kcoIssues');
		calculateBizRagStatus();
	}

	if (bcrY) {
		show('bcrIssuesOnTrack');
		calculateBizRagStatus();
	} else if (bcrN) {
		hide('bcrIssuesOnTrack');
		hide('bcrOpenIssues');
		clear('bcrIssues');
		clearRadio('bcrOnTrackY');
		clearRadio('bcrOnTrackN');
		calculateBizRagStatus();
	}

	if (bcrOnTrackY) {
		hide('bcrOpenIssues');
		clear('bcrIssues');
		calculateBizRagStatus();
	} else if (bcrOnTrackN) {
		show('bcrOpenIssues');
		calculateBizRagStatus();
	}

	if (prY) {
		show('prIssuesOnTrack');
		calculateBizRagStatus();
	} else if (prN) {
		hide('prIssuesOnTrack');
		hide('prOpenIssues');
		clear('prIssues');
		clearRadio('prOnTrackY');
		clearRadio('prOnTrackN');
		calculateBizRagStatus();
	}

	if (prOnTrackY) {
		hide('prOpenIssues');
		clear('prIssues');
		calculateBizRagStatus();
	} else if (prOnTrackN) {
		show('prOpenIssues');
		calculateBizRagStatus();
	}

	if (dataIncidents == "No") {
		hide('incidentNo');
		clear('incident');
		calculateBizRagStatus();
	} else if (dataIncidents == "Yes") {
		show('incidentNo');
		calculateBizRagStatus();
	} else if (dataIncidents == "viaIHP") {
		show('incidentNo');
		calculateBizRagStatus();
	} else if (dataIncidents == "unsure") {
		hide('incidentNo');
		clear('incident');
		calculateBizRagStatus();
	} else if (dataIncidents == "closed") {
		hide('incidentNo');
		clear('incident');
		calculateBizRagStatus();
	} else if (dataIncidents == "Unknown") {
		hide('incidentNo');
		clear('incident');
		calculateBizRagStatus();
	}

	if (rag == 'Green') {
		hide('issueCorrectiveBiz');
	} else if (rag == 'Amber') {
		show('issueCorrectiveBiz');
	} else if (rag == 'Red') {
		show('issueCorrectiveBiz');
	}
}

function enforceMaxLength(obj, length) {
	var maxLength = length;
	if (obj.value.length > maxLength) {
		obj.value = obj.value.substring(0, maxLength);
	}
}

function checkCRMR() {
	var result = document.editMonthly.crmrScore.value;
	if (parseInt(result) < 80) {
		show('delivery2');
	} else if (parseInt(result) > 79) {
		hide('delivery2');
	} else {
		hide('delivery2');
	}
}

function initCRMR() {
	var result = document.editMonthly.crmrScore.value;
	if (result == "" || result == null) {
		document.editMonthly.crmrScore.value = "80";
	}
}

function showIfCust() {
	var cers = getById('CERS').value;
	var cersDueToBpdY = getById('cersDueToBpdY').checked;
	var cersDueToBpdN = getById('cersDueToBpdN').checked;
	var set = getById('SET').value;
	var rag = getById('custRagStatus').innerHTML;

	if (cers == "SS") {
		hide('issueCorrectiveCust');
		hide('delivery');
		calculateCustRagStatus();
	} else if (cers == "NN") {
		hide('delivery');
		calculateCustRagStatus();
	} else if (cers == "00") {
		show('delivery');
		calculateCustRagStatus();
	} else if (cers == "11") {
		show('delivery');
		calculateCustRagStatus();
	} else if (cers == "22") {
		show('delivery');
		calculateCustRagStatus();
	} else if (cers == "33") {
		show('delivery');
		calculateCustRagStatus();
	} else if (cers == "44") {
		show('delivery');
		calculateCustRagStatus();
	} else if (cers == "55") {
		show('delivery');
		calculateCustRagStatus();
	} else if (cers == "66") {
		show('delivery');
		calculateCustRagStatus();
	} else if (cers == "77") {
		show('delivery');
		calculateCustRagStatus();
	} else if (cers == "88") {
		hide('delivery');
		calculateCustRagStatus();
	} else if (cers == "99") {
		hide('delivery');
		calculateCustRagStatus();
	} else if (cers == "10") {
		hide('delivery');
		calculateCustRagStatus();
	}

	if (cersDueToBpdY) {
		show('cersDueToBPD');
	} else if (cersDueToBpdN) {
		hide('cersDueToBPD');
		clear('cersDetails');
	}

	if (set == "Notavailab") {
		hide('delivery1');
		calculateCustRagStatus();
	} else if (set == "MetSomeMet") {
		show('delivery1');
		calculateCustRagStatus();
	} else if (set == "Fallen Far") {
		show('delivery1');
		calculateCustRagStatus();
	} else if (set == "MetMetMetM") {
		hide('delivery1');
		calculateCustRagStatus();
	} else if (set == "ExceededEx") {
		hide('delivery1');
		calculateCustRagStatus();
	}

	if (rag == 'Green') {
		hide('issueCorrectiveCust');
	} else if (rag == 'Amber') {
		show('issueCorrectiveCust');
	} else if (rag == 'Red') {
		show('issueCorrectiveCust');
	}
}

function calculateCustRagStatus() {
	var isManOr = getById('custManOr');
	var custSatIssues;
	var CERS = getById('CERS').value;
	var SET = getById('SET').value;
	var result = document.editMonthly.crmrScore.value;
	var cersDueToBpdN = getById('cersDueToBpdN');
	var crmr = "G";
	if (parseInt(result) < 80) {
		crmr = "R";
	} else if (parseInt(result) > 79) {
		crmr = "G";
	} else {
		crmr = "N";
	}

	if (getById('custSatIssuesS').checked) {
		custSatIssues = "S";
	} else if (getById('custSatIssuesE').checked) {
		custSatIssues = "E";
	} else if (getById('custSatIssuesL').checked) {
		custSatIssues = "L";
	}

	if (CERS != "SS" || CERS != "NN") {
		var cerStatus = parseInt(CERS);
		if (cerStatus < 77 && cerStatus > 10 && (!cersDueToBpdN.checked)) {
			CERS = "R";
		} else if (cerStatus < 10 && !cersDueToBpdN.checked) {
			CERS = "R";
		} else if (cerStatus == 77 && !cersDueToBpdN.checked) {
			CERS = "A";
		} else if (cerStatus > 77 || cerStatus == 10) {
			CERS = "G";
		} else if (cersDueToBpdN.checked) {
			CERS = "G";
		}
	} else if (CERS == "SS") {
		CERS = "N";
	} else if (CERS == "NN") {
		CERS = "G";
	}

	if (!isManOr.checked) {
		if (custSatIssues == "S") {
			getById("custRagStatus").className = "rag_green";
			getById("custRagStatus").innerHTML = "Green";
			getById("custActualRagStatus").value = "Green";
		}
		if (custSatIssues == "S" && CERS == "G"
				&& (SET == "MetMetMetM" || SET == "ExceededEx") && crmr == "G") {
			getById("custRagStatus").className = "rag_green";
			getById("custRagStatus").innerHTML = "Green";
			getById("custActualRagStatus").value = "Green";
		}
		if (CERS == "N" && SET == "Notavailab" && crmr == "N") {
			getById("custRagStatus").className = "rag_none";
			getById("custRagStatus").innerHTML = "N/A";
			getById("custActualRagStatus").value = "NA";
		} else if (custSatIssues == "S"
				&& (CERS == "N" && SET == "Notavailab" && crmr == "N")) {
			getById("custRagStatus").className = "rag_green";
			getById("custRagStatus").innerHTML = "Green";
			getById("custActualRagStatus").value = "Green";
		} else if (CERS == "G" && SET == "Notavailab" && crmr == "N") {
			getById("custRagStatus").className = "rag_green";
			getById("custRagStatus").innerHTML = "Green";
			getById("custActualRagStatus").value = "Green";
		} else if (CERS == "N" && (SET == "MetMetMetM" || SET == "ExceededEx")
				&& crmr == "N") {
			getById("custRagStatus").className = "rag_green";
			getById("custRagStatus").innerHTML = "Green";
			getById("custActualRagStatus").value = "Green";
		} else if (CERS == "N" && SET == "Notavailab" && crmr == "G") {
			getById("custRagStatus").className = "rag_green";
			getById("custRagStatus").innerHTML = "Green";
			getById("custActualRagStatus").value = "Green";
		} else if (CERS == "N" && (SET == "MetMetMetM" || SET == "ExceededEx")
				&& crmr == "G") {
			getById("custRagStatus").className = "rag_green";
			getById("custRagStatus").innerHTML = "Green";
			getById("custActualRagStatus").value = "Green";
		} else if (CERS == "G" && SET == "Notavailab" && crmr == "G") {
			getById("custRagStatus").className = "rag_green";
			getById("custRagStatus").innerHTML = "Green";
			getById("custActualRagStatus").value = "Green";
		} else if (CERS == "G" && (SET == "MetMetMetM" || SET == "ExceededEx")
				&& crmr == "N") {
			getById("custRagStatus").className = "rag_green";
			getById("custRagStatus").innerHTML = "Green";
			getById("custActualRagStatus").value = "Green";
		}

		if (custSatIssues == "E" || CERS == "A") {
			getById("custRagStatus").className = "rag_amber";
			getById("custRagStatus").innerHTML = "Amber";
			getById("custActualRagStatus").value = "Amber";
		}

		if (custSatIssues == "L" || CERS == "R"
				|| (SET == "Fallen Far" || SET == "MetSomeMet") || crmr == "R") {
			getById("custRagStatus").className = "rag_red";
			getById("custRagStatus").innerHTML = "Red";
			getById("custActualRagStatus").value = "Red";
		}
	}
	showHideCustIssueCorrective();
}

function showHideCustIssueCorrective() {
	var rag = getById("custRagStatus").innerHTML;
	if (rag == "Red" || rag == "Amber") {
		show('issueCorrectiveCust');
	} else if (rag == "Green") {
		hide('issueCorrectiveCust');
	}
}

function checkDhc() {
	var dhcScore = document.editMonthly.dhcScore.value;
	var actionPlanY = getById('dhcActionPlanY').checked;
	var actionPlanN = getById('dhcActionPlanN').checked;

	if (parseInt(dhcScore) == 10) {
		hide('dhcActionPlan');
		hide('dhcIssuesOnTarget');
		clearRadio('dhcActionPlanN');
		clearRadio('dhcIssuesOnTrackY');
		clearRadio('dhcIssuesOnTrackN');
		clearRadio('dhcActionPlanY');
	} else if (parseInt(dhcScore) > 5 && parseInt(dhcScore) < 10) {
		show('dhcActionPlan');
		if (actionPlanY) {
			show('dhcIssuesOnTarget');
		} else if (actionPlanN) {
			hide('dhcIssuesOnTarget');
			clearRadio('dhcIssuesOnTrackY');
			clearRadio('dhcIssuesOnTrackN');
		}
	} else {
		show('dhcActionPlan');
		if (actionPlanY) {
			show('dhcIssuesOnTarget');
		} else if (actionPlanN) {
			hide('dhcIssuesOnTarget');
			clearRadio('dhcIssuesOnTrackY');
			clearRadio('dhcIssuesOnTrackN');
		}
	}
}

function resetCA() {
	getById('audit').value = 'select';
}

function calculateBizRagStatus() {
	var isManOr = getById('bizManOr');
	var bizIssues = getById('bizIssues').value;
	var currentIscd = getById('currentIscd').value;
	var dataPrivacy = getById('dataPrivacy').value;
	var rating = getById('rating').value;
	var audit = getById('audit').value;
	var dataIncidents = getById('dataIncidents').value;
	var dhcY = getById('dhcY').checked;
	var dhcN = getById('dhcN').checked;
	var dhcScore = document.editMonthly.dhcScore.value;
	var iscdNFI = "G";
	var qaRating = "G";
	var QA = "G";
	var kco = "G";
	var bcr = "G";
	var pr = "G";
	var dhc = "G";
	var isGreen = 2;
	var dataIncidentsRag = "G";
	var bizIssuesRag = "G";
	var dataPrivacyRag = "G";
	var ratingRag = "G";
	var currentIscdRag = "G";

	if (dataIncidents == "NoNoNoNoNo" || dataIncidents == "YesYesYesY") {
		dataIncidentsRag = "G";
	} else if (dataIncidents == "viaIHPviaI") {
		dataIncidentsRag = "A";
	} else if (dataIncidents == "unsureunsu" || dataIncidents == "closedclos"
			|| dataIncidents == "UnknownUnk") {
		dataIncidentsRag = "R";
	}

	if (bizIssues == "N") {
		bizIssuesRag = "G";
	} else if (bizIssues == "T") {
		bizIssuesRag = "A";
	} else if (bizIssues == "S") {
		bizIssuesRag = "R";
	}

	if (dataPrivacy == "No special handling" || dataPrivacy == "action plans") {
		dataPrivacyRag = "G";
	} else if (dataPrivacy == "being worked on") {
		dataPrivacyRag = "A";
	} else if (dataPrivacy == "being developed" || dataPrivacy == "Unknown") {
		dataPrivacyRag = "R";
	}

	if (rating == "Satis") {
		ratingRag = "G";
	} else if (rating == "Unsatis" || rating == "Marginal") {
		ratingRag = "A";
	}

	if ((currentIscd == "App impl") || (currentIscd == "N/A-IA")
			|| (currentIscd == "N/A-A") || (currentIscd == "App not impl")) {
		currentIscdRag = "G";
	} else if (currentIscd == "N/A-N") {
		currentIscdRag = "A";
	} else if (currentIscd == "Not app") {
		currentIscdRag = "R";
	}

	if (getById('iscdNotFullImplY').checked) {
		iscdNFI = "A";
	} else if (getById('iscdNotFullImplN').checked) {
		iscdNFI = "R";
	} else
		iscdNFI = "G";

	if (getById('qaNA').checked) {
		qaRating = "G";
	} else if (getById('qaA').checked) {
		qaRating = "G";
	} else if (getById('qaB').checked) {
		qaRating = "G";
	} else if (getById('isPMRN').checked) {
		qaRating = "G";
	} else if (getById('isPMRY').checked) {
		qaRating = "R";
	} else if (getById('qaC').checked) {
		qaRating = "A";
	} else if (getById('qaD').checked) {
		qaRating = "R";
	} else
		qaRating = "G";

	if (getById('isCaY').checked) {
		QA = "G";
	}
	if (getById('caPassN').checked && audit == "Not") {
		QA = "R";
	} else if (getById('caPassN').checked && audit == "Confirmed") {
		QA = "G";
	} else if (getById('caPassN').checked && audit == "On Target") {
		QA = "A";
	}

	if (getById('kcoOnTrackY').checked) {
		kco = "G";
	} else if (getById('kcoOnTrackN').checked) {
		kco = "A";
	} else
		kco = "G";

	if (getById('bcrOnTrackY').checked) {
		bcr = "G";
	} else if (getById('bcrOnTrackN').checked) {
		bcr = "A";
	} else
		bcr = "G";

	if (getById('prOnTrackY').checked) {
		pr = "G";
	} else if (getById('prOnTrackN').checked) {
		pr = "A";
	} else
		pr = "G";

	if (dhcY) {
		if (parseInt(dhcScore) <= 5) {
			dhc = "R";
		} else if (parseInt(dhcScore) > 5 && parseInt(dhcScore) < 10) {
			dhc = "A";
		} else if (parseInt(dhcScore) == 10) {
			dhc = "G";
		} else
			dhc = "G";
	} else if (dhcN) {
		dhc = "G";
	}

	if (!isManOr.checked) {
		if (bizIssuesRag == "G" && dataPrivacyRag == "G"
				&& currentIscdRag == "G" && qaRating == "G" && ratingRag == "G"
				&& QA == "G" && kco == "G" && bcr == "G" && pr == "G"
				&& dataIncidentsRag == "G" && dhc == "G") {
			getById("bizRagStatus").className = "green";
			getById("bizRagStatus").innerHTML = "Green";
			getById("bizActualRagStatus").value = "Green";
			isGreen = 1;
		} else {
			isGreen = 0;
		}

		if (isGreen == 0) {
			if (bizIssuesRag == "A" || currentIscdRag == "A" || iscdNFI == "A"
					|| dataPrivacyRag == "A" || qaRating == "A" || kco == "A"
					|| bcr == "A" || pr == "A" || ratingRag == "A"
					|| dataIncidentsRag == "A" || QA == "A" || dhc == "A") {
				getById("bizRagStatus").className = "amber";
				getById("bizRagStatus").innerHTML = "Amber";
				getById("bizActualRagStatus").value = "Amber";
			}

			if (bizIssuesRag == "R" || iscdNFI == "R" || dataPrivacyRag == "R"
					|| qaRating == "R" || QA == "R" || currentIscdRag == "R"
					|| dataIncidentsRag == "R" || dhc == "R") {
				getById("bizRagStatus").className = "red";
				getById("bizRagStatus").innerHTML = "Red";
				getById("bizActualRagStatus").value = "Red";
			}
		}
	}
	showHideIssueCorrectiveBiz();
}

function showHideIssueCorrectiveBiz() {
	var rag = getById("bizRagStatus").innerHTML;
	if (rag == "Red" || rag == "Amber") {
		show('issueCorrectiveBiz');
	} else if (rag == "Green") {
		hide('issueCorrectiveBiz');
	}
}

function attritionVoice() {
	var isManOr = getById('om_Man_Or');

	var targetVar = document.editMonthly.attritionVoiceTarget.value; // getById("attritionVoiceTarget").value;
	var actualVar = document.editMonthly.attritionVoiceActual.value; // getById("attritionVoiceActual").value;
	var target = parseInt(targetVar) + 1;
	var actual = parseInt(actualVar) + 1;

	if (actual < target) {
		console.debug("attritionVoice(): actual<target => " + actual + " < "
				+ target);
		show("attritionVoiceIssues");
	} else if (target > actual + 1) {
		console.debug("attritionVoice(): target>actual+1 => " + target + " < "
				+ (actual + 1));
		hide("attritionVoiceIssues");
	} else if (target == actual + 1) {
		console.debug("attritionVoice(): target==actual+1 => " + target
				+ " == " + (actual + 1));
		show('attritionVoiceIssues');
	} else {
		console.debug("attritionNonVoice(): actual & target => " + actual
				+ " & " + target);
		hide('attritionVoiceIssues');
	}

	if (!isManOr.checked) {
		if (actual < target) {
			calculateOmRagStatus('attritionR');
		} else if (target > actual + 1) {
			calculateOmRagStatus('attritionG');
		} else if (target == actual + 1) {
			calculateOmRagStatus('attritionA');
		} else {
			calculateOmRagStatus('attritionG');
		}
	}
}

function resetOmKeys() {
	for (var i = 0; i < 7; i++) {
		getById("transitionRag_" + i).value = "G";
	}
}

function initRag() {
	getById('rag_status_om').innerHTML = 'N/A';
	getById('rag_status_om').className = 'rag_none';
}

function attritionNonVoice() {
	var isManOr = getById('om_Man_Or');
	var targetVar = document.editMonthly.nonVoiceTarget.value;
	var actualVar = document.editMonthly.nonVoiceActual.value;
	var target = parseInt(targetVar) + 1;
	var actual = parseInt(actualVar) + 1;

	if (actual < target) {
		console.debug("attritionNonVoice(): actual<target => " + actual + " < "
				+ target);
		show("attritionNonVoiceIssues");
	} else if (target > actual + 1) {
		console.debug("attritionNonVoice(): target>actual+1 => " + target
				+ " < " + (actual + 1));
		hide("attritionNonVoiceIssues");
	} else if (target == actual + 1) {
		console.debug("attritionNonVoice(): target==actual+1 => " + target
				+ " == " + (actual + 1));
		show('attritionNonVoiceIssues');
	} else {
		console.debug("attritionNonVoice(): actual & target => " + actual
				+ " & " + target);
		hide('attritionNonVoiceIssues');
	}

	if (!isManOr.checked) {
		if (actual < target) {
			calculateOmRagStatus('attritionNonR');
		} else if (target > actual + 1) {
			calculateOmRagStatus('attritionNonG');
		} else if (target == actual + 1) {
			calculateOmRagStatus('attritionNonA');
		} else {
			calculateOmRagStatus('attritionNonG');
		}
	}
}

function showHideTransitionKeys() {
	for (var i = 0; i < 7; i++) {
		if (getById('transitionRag_' + i).value == 'A'
				|| getById('transitionRag_' + i).value == 'R') {
			show('issue' + (i + 1));
		} else
			hide('issue' + (i + 1));
	}
}

function show_iD(id) {
	var finRag = getById('ragStatusCurr').innerHTML;
	if (finRag == "Green" || finRag == "N/A") {
		dojo.byId(id).style.display = 'none';
	} else if (finRag == "Amber" || finRag == "Red") {
		dojo.byId(id).style.display = 'block';
	}
}

function showIf() {
	var GPTARGET = getById('GP_TARGET').value;
	var GPPLAN = getById('GP_PLAN').value;
	var rag = getById('ragStatusCurr').innerHTML;

	if (GPTARGET == 'B' || GPTARGET == 'N') {
		show('gppTarget');
	} else {
		hide('gppTarget');
	}

	if (GPPLAN == 'B' || GPPLAN == 'N') {
		show('gppPlan');
	} else {
		hide('gppPlan');
	}

	if (rag == 'Green') {
		hide('Summary');
	} else if (rag == 'Amber') {
		show('Summary');
	} else if (rag == 'Red') {
		show('Summary');
	}
}

function hideAll(id) {
	dojo.byId('fp').style.display = 'none';
	dojo.byId('cs').style.display = 'none';
	dojo.byId('om').style.display = 'none';
	dojo.byId('bc').style.display = 'none';
	dojo.byId('os').style.display = 'none';
}

function manOrScoreOm(rag) {
	getById("rag_status_om").className = rag;
	if (rag == 'green') {
		getById("rag_status_om").innerHTML = "Green";
	} else if (rag == 'amber') {
		getById("rag_status_om").innerHTML = "Amber";
	} else if (rag == 'red') {
		getById("rag_status_om").innerHTML = "Red";
	}
}

function custManOr(rag) {
	getById("custRagStatus").className = rag;
	if (rag == 'green') {
		getById("custRagStatus").innerHTML = "Green";
		hide('issueCorrectiveCust');
	} else if (rag == 'amber') {
		getById("custRagStatus").innerHTML = "Amber";
		show('issueCorrectiveCust');
	} else if (rag == 'red') {
		getById("custRagStatus").innerHTML = "Red";
		show('issueCorrectiveCust');
	}
}

function overallManOr(rag) {
	getById("currentOverall").className = 'rag_' + rag;
	getById("overallStatus").className = 'rag_' + rag;
	if (rag == 'green') {
		getById("currentOverall").innerHTML = "Green";
		getById("overallRagStatus").value = "G";
		getById("overallStatus").innerHTML = "Green";

	} else if (rag == 'amber') {
		getById("currentOverall").innerHTML = "Amber";
		getById("overallRagStatus").value = "A";
		getById("overallStatus").innerHTML = "Amber";
	} else if (rag == 'red') {
		getById("currentOverall").innerHTML = "Red";
		getById("overallRagStatus").value = "R";
		getById(overallStatus).innerHTML = "Red";
	}
}

function manOr(rag) {
	if (rag == 'green') {
		getById("ragStatusCurr").innerHTML = "Green";
		getById("ragStatusCurr").className = "rag_green";
		hide('Summary');
	} else if (rag == 'amber') {
		getById("ragStatusCurr").innerHTML = "Amber";
		getById("ragStatusCurr").innerHTML = "rag_amber";
		show('Summary');
	} else if (rag == 'red') {
		getById("ragStatusCurr").innerHTML = "Red";
		getById("ragStatusCurr").innerHTML = "rag_red";
		show('Summary');
	}
}

function bizManOr(rag) {
	getById("bizRagStatus").className = rag;
	if (rag == 'green') {
		getById("bizRagStatus").innerHTML = "Green";
		hide('issueCorrectiveBiz');
	} else if (rag == 'amber') {
		getById("bizRagStatus").innerHTML = "Amber";
		show('issueCorrectiveBiz');

	} else if (rag == 'red') {
		getById("bizRagStatus").innerHTML = "Red";
		show('issueCorrectiveBiz');
	}
}

function setRagForOr() {
	var isManOrOm = getById('om_Man_Or');
	var isManOrOv = getById('overallManOr');
	var isManOrBiz = getById('bizManOr');
	var isManOrCust = getById('custManOr');
	var isManOrFin = getById('manuallyOverride');

	var OvRag = getById('manOrScoreSelectOv').value;
	var OmRag = getById('manOrScoreOm').value;

	if (isManOrBiz.checked) {
		var bizRag = getById("bizRagStatus").innerHTML;
		if (bizRag == "Green") {
			getById('manOrScoreBiz').value = "green";
		} else if (bizRag == "Amber") {
			getById('manOrScoreBiz').value = "amber";
		} else if (bizRag == "Red") {
			getById('manOrScoreBiz').value = "red";
		}
	}

	if (isManOrCust.checked) {
		var custRag = getById("custRagStatus").innerHTML;
		if (custRag == "Green") {
			getById('manOrScoreSelectCust').value = "green";
		} else if (custRag == "Amber") {
			getById('manOrScoreSelectCust').value = "amber";
		} else if (custRag == "Red") {
			getById('manOrScoreSelectCust').value = "red";
		}
	}

	if (isManOrFin.checked) {
		var finRag = getById("ragStatusCurr").innerHTML;
		if (finRag == "Green") {
			getById('manOrScoreSelect').value = "green";
		} else if (finRag == "Amber") {
			getById('manOrScoreSelect').value = "amber";
		} else if (finRag == "Red") {
			getById('manOrScoreSelect').value = "red";
		}
	}

	if (isManOrOv.checked) {
		if (OvRag == "green") {
			getById("currentOverall").innerHTML = "Green";
			getById("currentOverall").className = "rag_green";
			getById('overallStatus').className = "rag_green";
			getById('overallStatus').innerHTML = "Green";
		} else if (OvRag == "amber") {
			getById("currentOverall").innerHTML = "Amber";
			getById("currentOverall").className = "rag_amber";
			getById('overallStatus').className = "rag_amber";
			getById('overallStatus').innerHTML = "Amber";
		} else if (OvRag == "red") {
			getById('overallStatus').className = "rag_red";
			getById('overallStatus').innerHTML = "Red";
			getById("currentOverall").innerHTML = "Red";
			getById("currentOverall").className = "rag_red";
		}
	}

	if (isManOrOm.checked) {
		if (OmRag == "green") {
			getById("rag_status_om").innerHTML = "Green";
			getById("rag_status_om").className = "rag_green";
			getById("overallOpMeas").innerHTML = "Green";
			getById("overallOpMeas").className = "rag_green";
			getById("om_ragStatus").value = "G";
		} else if (OmRag == "amber") {
			getById("rag_status_om").innerHTML = "Amber";
			getById("rag_status_om").className = "rag_amber";
			getById("overallOpMeas").innerHTML = "Amber";
			getById("overallOpMeas").className = "rag_amber";
			getById("om_ragStatus").value = "A";
		} else if (OmRag == "red") {
			getById("rag_status_om").innerHTML = "Red";
			getById("rag_status_om").className = "rag_red";
			getById("overallOpMeas").innerHTML = "Red";
			getById("overallOpMeas").className = "rag_red";
			getById("om_ragStatus").value = "R";
		}
	}
}

function showOrHideOnLoad() {
	var isManOrOm = getById('om_Man_Or');
	var rag = getById('om_ragStatus').value;

	if (isManOrOm.checked) {
		show('manOrScoreOm');
		if (rag == "G") {
			getById('manOrScoreOm').value = "green";
		} else if (rag == "A") {
			getById('manOrScoreOm').value = "amber";
		} else if (rag == "R") {
			getById('manOrScoreOm').value = "red";
		}
	}

}

function showOrHideOverallOnLoad() {
	var isManOrOv = getById('overallManOr');
	var rag = getById('overallRagStatus').value;

	if (isManOrOv.checked) {
		show('manOrScoreOverall');
		show('overallScore');
		if (rag == "G") {
			getById('manOrScoreSelectOv').value = "green";
		} else if (rag == "A") {
			getById('manOrScoreSelectOv').value = "amber";
		} else if (rag == "R") {
			getById('manOrScoreSelectOv').value = "red";
		}
	}
}

// /////////////////////////// end monthly form
// /////////////////////////////////////////////////////
