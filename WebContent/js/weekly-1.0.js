
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
	
	function getById(id){
		try {
			return dojo.byId(id);
		} catch(err) {
		   console.debug("Error getById("+id+") => "+err.message);
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

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////// weekly searchFilter ////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	function resetPageNo() {
		console.debug('resetPageNo().........');
		try {
			dojo.byId("page").value = "1";
		} catch(err) {
		   console.debug("Error resetPageNo(): "+err.message);
		}
	}
	
	function getWeeklyStatusList(){
		console.debug('getWeeklyStatusList().........');
		// Local var representing if the form has been sent at all
		//var hasBeenSent = false;
		// Local var representing node to be updated
		var contractListDiv = dojo.byId("contractList");
		// Using dojo.xhrPost, as the amount of data sent could be large
		dojo.xhrPost({
		    // The URL of the request
		    url: "weeklyListPagination.htm",
		    form: dojo.byId("searchFilterForm"),
		    // The success handler
		    load: function(response) {
				contractListDiv.innerHTML = response;
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
	
	function getWeeksListForCurrYear() {
		console.debug('getWeeksListForCurrYear().........');
		//alert(document.getElementById('currentWeek').value);
		var selectedYear=document.getElementById('year').value;
		document.getElementById('week').options.length = 0;
		var day = 5;
		var date = new Date(selectedYear);
		var nextYear = date.getFullYear() + 1;

		while(date.getDay() != day) {
		    date.setDate(date.getDate() + 1)  ;  
		}

		while(date.getFullYear() < nextYear) {
		    var yyyy = date.getFullYear();

		    var mm = (date.getMonth() + 1);
		    mm = (mm < 10) ? '0' + mm : mm;

		    var dd = date.getDate();
		    dd = (dd < 10) ? '0' + dd : dd;
		    var nextWeek=yyyy + '-' + (mm) + '-' + (dd);
		    
		    var currentWeek = document.getElementById('currentWeek').value;
		    var optn = document.createElement('option');
			optn.text = nextWeek;
			optn.value = nextWeek;
			if(nextWeek==currentWeek) {
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
	
	
	function getNextPage(){
		var _page =  dojo.byId("page");
		var _pagingAction =  dojo.byId("pagingAction");
		_page.value = parseInt(_page.value)+1;
		_pagingAction.value = "PAGINATE";
		//dojo.byId("displayMsg").innerHTML = "page = "+_page.value;
		getWeeklyStatusList();
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
		getWeeklyStatusList();
		return false;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////End weekly searchFilter /////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////// weekly BPD Form /////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	function beforeSubmitDo(submitInput){		
		document.getElementById("submitType").value = submitInput.id;
	}
	
	function loadRag()
	{
		var isManOr = document.getElementById('isManOr').value;
		var ragStatus = document.getElementById('ragStatus').value;

		if(isManOr.trim() == "Y")
		{
			document.getElementById("manuallyOverride").checked = true;
		}

		if(document.getElementById("manuallyOverride").checked)
		{
			if(ragStatus.trim() == "G")
			{
				document.getElementById("ragStatusCurr").innerHTML="Green";
				document.getElementById("ragStatusCurr").className="green";
				document.getElementById("ragStatusOr").value="G";
			}
			else if(ragStatus.trim() == "A")
			{
				document.getElementById("ragStatusCurr").innerHTML="Amber";
				document.getElementById("ragStatusCurr").className="amber";
				document.getElementById("ragStatusOr").value="A";
			}
			else if(ragStatus.trim() == "R")
			{
				document.getElementById("ragStatusCurr").innerHTML="Red";
				document.getElementById("ragStatusCurr").className="red";
				document.getElementById("ragStatusOr").value="R";
			}
		}
	}

	function resetContract()
	{
		var r = confirm("Are you sure you want to reset the form? Resetting the form will clear all the data currently entered");
		if (r == true) { 
			beforeSubmitDo("reset");
			document.forms["weeklyForm"].submit();
		}
	}

	function setIsFileDelete(index)
	{
		dojo.byId("weeklyFiles"+index+".isDeleted").value = "Y";
	}

	function checkRejection()
	{
		var submitType = document.getElementById("submitType").value;
		if(submitType == "reset")
		{
			var opt=confirm("Reset Form, All saved Data will be Lost!!!");
			if (opt==true)
			{
			  return true;
			}
			else
			{
				return false;
			}
		}
	}

	function enforceMaxLength(obj){
		var maxLength = 2000;
		if (obj.value.length > maxLength) {
			obj.value = obj.value.substring(0, maxLength);
		}
	}

	function setCheckBox(chkBox)
	{
		//var arr = chkBox.id.split("_");
		var sel = document.getElementById("manOveride");
			if(chkBox.checked)
			{
				sel.value = "Y";
			}
			else
			{
				sel.value = "N";
			}
	}

	function showOrHideGen(caller)
	{
		var staffingRag = document.getElementById('staffingRag').value;
		var crmrRag = document.getElementById('crmrRag').value;
		
		if(caller == "staffing")
		{
			if(staffingRag == "A" || staffingRag == "R")
			{
				show('staffingCell');
			}
			else if(staffingRag == "G" || staffingRag == "" || staffingRag == "N")
			{
				hide('staffingCell');
			}
		}
		else if(caller == "crmr")
		{
			if(crmrRag == "A" || crmrRag == "R")
			{
				show('crmrCell');
			}
			else if(crmrRag == "G" || crmrRag == "")
			{
				hide('crmrCell');
			}
		}
	}

	function showOrHideKeys()
	{
		var length=0;
		try
		{
			length = parseInt(document.getElementById("weeklyKeysLength").value);
		}
		catch(e)
		{
			alert(e);
		}		
		for(var i=0;i<length;i++)
		{
			var rag = document.getElementById("keysRag_"+i).value;
			if(rag=="R" || rag=="A")
			{
				var issue = "issue_"+i;
				show(issue);
			}
			else if(rag=="G" || rag=="")
			{
				var issue = "issue_"+i;
				hide(issue);
			}
		}
	}

	function manOr(rag){
		var isManOr = document.getElementById('manuallyOverride');
		var currStatus = document.getElementById("ragStatusCurr").innerHTML;
		
		if(isManOr.checked){

			if(currStatus=='Green')
			{
				document.getElementById("actualRag").value="G";
			}
			else if(currStatus=='Amber')
			{
				document.getElementById("actualRag").value="A";
			}
			else if(currStatus=='Red')
			{
				document.getElementById("actualRag").value="R";
			}
			
			if(rag=='G')
			{
				document.getElementById("ragStatusCurr").innerHTML="Green";
				document.getElementById("ragStatusCurr").className="green";
				document.getElementById("ragStatus").value="G";
				document.getElementById("ragStatusOr").value="G";
			}
			else if(rag=='A')
			{
				document.getElementById("ragStatusCurr").innerHTML="Amber";
				document.getElementById("ragStatusCurr").className="amber";
				document.getElementById("ragStatus").value="A";
				document.getElementById("ragStatusOr").value="A";
			}
			else if(rag=='R')
			{
				document.getElementById("ragStatusCurr").innerHTML="Red";
				document.getElementById("ragStatusCurr").className="red";
				document.getElementById("ragStatus").value="R";
				document.getElementById("ragStatusOr").value="R";
			}
		}
		else
		{
			calculateWeeklyRag('staffing');
			calculateWeeklyRag('crmr');
			calculateWeeklyRag('process');
			calculateWeeklyRag('ref');
			calculateWeeklyRag('keys');
			calculateWeeklyRag('attrition');
		}
	}

	function calculateWeeklyRag(caller){
		var manOr = document.getElementById('manuallyOverride');
		var currRag = document.getElementById('ragStatusCurr');
		var overallRag = new Array();
		overallRag[0] = document.getElementById('overallRag').value;
		overallRag[1] = document.getElementById('overallRag1').value;
		overallRag[2] = document.getElementById('overallRag2').value;
		overallRag[3] = document.getElementById('overallRag3').value;
		overallRag[4] = document.getElementById('overallRag4').value;
		overallRag[5] = document.getElementById('overallRag5').value;
		overallRag[6] = document.getElementById('overallRag6').value;
		overallRag[7] = document.getElementById('overallRag7').value;
		var ragStatus;

		if(caller=="staffing")
		{
			var staffingSel = document.getElementById('staffingRag').value;
			var staffingY = document.getElementById('staffingY');
			var staffingN = document.getElementById('staffingN');

			if(staffingSel == "G")
			{
				overallRag[0] = "G";
				document.getElementById('overallRag').value = "G";
			}
			else if(staffingSel == "A" && !staffingY.checked)
			{
				overallRag[0] = "A";
				document.getElementById('overallRag').value = "A";
			}
			else if(staffingSel == "A" && staffingY.checked)
			{
				overallRag[0] = "R";
				staffingSel = "R";
				document.getElementById('overallRag').value = "R";
			}
			else if(staffingSel == "R")
			{
				overallRag[0] = "R";
				document.getElementById('overallRag').value = "R";
			}
			else if(staffingSel == "N")
			{
				overallRag[0] = "";
				document.getElementById('overallRag').value = "";
			}
		}
		else if(caller=="crmr")
		{
			var crmrSel = document.getElementById('crmrRag').value;
			var crmrY = document.getElementById('crmrY');
			var crmrN = document.getElementById('crmrN');

			if(crmrSel == "G")
			{
				overallRag[1] = "G";
				document.getElementById('overallRag1').value = "G";
			}
			else if(crmrSel == "A" && !crmrY.checked)
			{
				overallRag[1] = "A";
				document.getElementById('overallRag1').value = "A";
			}
			else if(crmrSel == "A" && crmrY.checked)
			{
				overallRag[1] = "R";
				crmrSel = "R";
				document.getElementById('overallRag1').value = "R";
			}
			else if(crmrSel == "R")
			{
				overallRag[1] = "R";
				document.getElementById('overallRag1').value = "R";
			}
			else if(crmrSel == "")
			{
				overallRag[1] = "";
				document.getElementById('overallRag1').value = "";
			}
		}
		else if(caller=="ref") {
			ref = getById("refB");
			if(ref.checked) {
				overallRag[2] = "R";
				getById('overallRag2').value = "R";
				getById('refProcessRag').value = "R";
			} else {
				overallRag[2] = "G";
				getById('overallRag2').value = "G";
				getById('refProcessRag').value = "G";
			}	
		}
		else if(caller=="keys")
		{
			var length=0;
			try
			{
				length = parseInt(document.getElementById("weeklyKeysLength").value);
			}
			catch(e)
			{
				alert(e);
			}		
			for(var i=0;i<length;i++)
			{
				var rag = document.getElementById("keysRag_"+i).value;
				if(rag=="G")
				{
					overallRag[3] = "G";
					document.getElementById('overallRag3').value = "G";
					break;
				}
				else if(rag=="")
				{
					overallRag[3] = "";
					document.getElementById('overallRag3').value = "";
				}
			}
			for(var i=0;i<length;i++)
			{
				var rag = document.getElementById("keysRag_"+i).value;
				if(rag=="R")
				{
					overallRag[3] = "R";
					document.getElementById('overallRag3').value = "R";
					break;
				}
				else if(rag=="A")
				{
					overallRag[3] = "A";
					document.getElementById('overallRag3').value = "A";
				}
			}
		}
		else if(caller=="process")
		{
			var length=0;
			try
			{
				length = parseInt(document.getElementById("weeklyProcessesLength").value);
			}
			catch(e)
			{
				alert(e);
			}		
			for(var i=0;i<length;i++)
			{
				var rag = document.getElementById("process_"+i).value;
				if(rag=="G")
				{
					overallRag[4] = "G";
					document.getElementById('overallRag4').value = "G";
					break;
				}
				else if(rag=="")
				{
					overallRag[4] = "";
					document.getElementById('overallRag4').value = "";
				}
			}
			for(var i=0;i<length;i++)
			{
				var rag = document.getElementById("process_"+i).value;
				var isEscalatedY = document.getElementById('isEscalatedToClientY_'+i);
				var isEscalatedN = document.getElementById('isEscalatedToClientN_'+i);
				if(rag=="R")
				{
					overallRag[4] = "R";
					document.getElementById('overallRag4').value = "R";
					break;
				}
				else if(rag=="A" && !isEscalatedY.checked)
				{
					overallRag[4] = "A";
					document.getElementById('overallRag4').value = "A";
				}
				else if(rag=="A" && isEscalatedY.checked)
				{
					overallRag[4] = "R";
					document.getElementById('overallRag4').value = "R";
				}
			}
		}
		else if(caller=="attrition")
		{
			overallRag[5] = "";
			document.getElementById('overallRag5').value = "";
			overallRag[6] = "";
			document.getElementById('overallRag6').value = "";

			if(document.getElementById("oriLobName").trim == "Daksh")
			{
				var target = parseInt(document.getElementById('target').value);
				var actual = parseInt(document.getElementById('actual').value);
				var target1 = parseInt(document.getElementById('target1').value);
				var actual1 = parseInt(document.getElementById('actual1').value);
		
				if(actual>(target+1)){
					overallRag[5] = "R";
					document.getElementById('overallRag5').value = "R";
				}
				if(actual>target && actual<=(target+1)){
					overallRag[5] = "A";
					document.getElementById('overallRag5').value = "A";
				}	
				if(actual1>(target1+1)){
					overallRag[6] = "R";
					document.getElementById('overallRag6').value = "R";
				}
				if(actual1>target1 && actual1<=(target1+1)){
					overallRag[6] = "A";
					document.getElementById('overallRag6').value = "A";
				}
			}
		}
		else if(caller == "resource")
		{
			overallRag[7] = "";
			document.getElementById('overallRag7').value = "";
			var isResourceExcessiveY = document.getElementById('isResourceExcessiveY');
			var isResourceExcessiveN = document.getElementById('isResourceExcessiveN');
			if(isResourceExcessiveY.checked)
			{
				overallRag[7] = "A";
				document.getElementById('overallRag7').value = "A";
			}
			else if(isResourceExcessiveN.checked)
			{
				overallRag[7] = "G";
				document.getElementById('overallRag7').value = "G";
			}
			else
			{
				overallRag[7] = "";
				document.getElementById('overallRag7').value = "";
			}
		}

			for(var i=0;i<8;i++)
			{
				if(overallRag[i]=="G")
				{
					ragStatus = "G";
					break;
				}
				else if(overallRag[i]=="")
				{
					ragStatus = "";
				}
			}
			for(var i=0;i<8;i++)
			{
				if(overallRag[i]=="R")
				{
					ragStatus = "R";
					break;
				}
				else if(overallRag[i]=="A")
				{
					ragStatus = "A";
				}
			}
			
			if(ragStatus == "G")
			{
				if(!manOr.checked)
				{
					document.getElementById("ragStatusCurr").innerHTML="Green";
					document.getElementById("ragStatusCurr").className="green";
					getById("ragStatus").value="G";
				}
				document.getElementById("actualRag").value="G";
			}
			else if(ragStatus == "A")
			{
				if(!manOr.checked)
				{
					document.getElementById("ragStatusCurr").innerHTML="Amber";
					document.getElementById("ragStatusCurr").className="amber";
					getById("ragStatus").value="A";
				}
				document.getElementById("actualRag").value="A";
			}
			else if(ragStatus == "R")
			{
				if(!manOr.checked)
				{
					document.getElementById("ragStatusCurr").innerHTML="Red";
					document.getElementById("ragStatusCurr").className="red";
					getById("ragStatus").value="R";
				}
				document.getElementById("actualRag").value="R";
			}
			else if(ragStatus == "")
			{
				if(!manOr.checked)
				{
					document.getElementById("ragStatusCurr").innerHTML="N/A";
					document.getElementById("ragStatusCurr").className="rag_none";
					getById("ragStatus").value="";
				}
				document.getElementById("actualRag").value="";
			}	
	}

	function getById(id){
		try {
			return dojo.byId(id);
		} catch(err) {
		   console.debug("Error getById("+id+") => "+err.message);
		}
	}

	function checkManOr(){
		var isManOr = document.getElementById('isManOr').value;
		if(isManOr.trim() == "Y"){
			document.getElementById('manuallyOverride').checked = true;
		}
	}
	
	function setRag(){
		var ragStatus = document.getElementById('ragStatus');
		var isManOr = document.getElementById('manuallyOverride');
		if(isManOr.checked){
			ragStatus.value = document.getElementById('ragStatusOr').value;
		}
		else{
			ragStatus.value = document.getElementById("actualRag").value;
		}
	}

	function showOrHide(id){
		var isManOr = document.getElementById('manuallyOverride');
		var ragStatus = document.getElementById('actualRag').value;
		if(isManOr.checked){
			dojo.byId(id).style.display = 'block';
		}
		else{
			dojo.byId(id).style.display = 'none';
			if(ragStatus == "G")
			{
				document.getElementById("ragStatusCurr").innerHTML="Green";
				document.getElementById("ragStatusCurr").className="green";
			}
			else if(ragStatus == "A")
			{
				document.getElementById("ragStatusCurr").innerHTML="Amber";
				document.getElementById("ragStatusCurr").className="amber";
			}
			else if(ragStatus == "R")
			{
				document.getElementById("ragStatusCurr").innerHTML="Red";
				document.getElementById("ragStatusCurr").className="red";
			}
		}
	}

		function key(id){
			var target = document.getElementById('target').value;
			var actual = document.getElementById('actual').value;
			
			if(actual>target){
				dojo.byId(id).style.display = 'block';
			}
			else{
				dojo.byId(id).style.display = 'none';
			}
		}
		
		function key1(id){
			var target = document.getElementById('target1').value;
			var actual = document.getElementById('actual1').value;
			
			if(actual<(target) || actual>(target+1)){
				dojo.byId(id).style.display = 'block';
			}
			else{
				dojo.byId(id).style.display = 'none';
			}
		}