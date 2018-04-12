
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

/////////////////////////////// contract listing////////////////////////////////////////////////
	
	function getContracts(){
		// Local var representing if the form has been sent at all
		//var hasBeenSent = false;
		// Local var representing node to be updated
		var contractListDiv = dojo.byId("contractList");
		// Using dojo.xhrPost, as the amount of data sent could be large
		dojo.xhrPost({
		    // The URL of the request
		    //url: "contractList.htm",
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
	
	function initiaePagination(){
		var _page =  dojo.byId("page");
		var _pagingAction =  dojo.byId("pagingAction");
		_page.value = 1;
		_pagingAction.value = "NEW-SEARCH";
	}
	
	function getNextPage(){
		var _page =  dojo.byId("page");
		console.log(_page.value);
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
	
/////////////////////////////// contract listing end////////////////////////////////////////////////

	
///////////////////////////// edit contract ////////////////////////////////////////////////////////////
	function showSelectedProcess(){
		var inputs = document.getElementsByTagName("input"); //or document.forms[0].elements;  
		var _checked = []; //will contain all checked checkboxes  
		for (var i = 0; i < inputs.length; i++) {  
		  if (inputs[i].type == "checkbox") {  
			  if (inputs[i].className == 'process_box'){
			    if (inputs[i].checked) {  
			      _checked.push(inputs[i]);  
			    }
			  }
		  }  
		} 
		
		for (var i = 0; i < _checked.length; i++) {
			var _checkedId =  _checked[i].id;
			_checkedId = _checkedId.replace("process_","");
			show(_checkedId.concat('_table'));
			show("no_sla_slo_".concat(_checkedId));
		}
	}
	

	function showIf() {
		if(document.getElementById('show_imt').checked) {
			show('imt_countries');
		}
		if(document.getElementById('show_dc').checked) {
			show('delivery_centers');
		}
	}
	

	function showHideSLATables(source, nodeId){
		if(dojo.byId(source).checked){
			show(nodeId);
		}else{
			hide(nodeId);
		}
	}
	
	function showKCPI(source, dest){
		if(dojo.byId(source).checked){
			show(dest.concat('_table'));
		}else{
			hide(dest+'_table');
		}
	}
	
	function displaySLA(source, dest, procRow){
		if(dojo.byId(source).checked){
			getSLAs(dest, dest, procRow, 'get');
			show(dest.concat('_dataTable'));
		}else{
			hide(dest+'_dataTable');
		}
	}
	
	function displaySLO(source, dest, procRow){
		if(dojo.byId(source).checked){
			getSLOs(dest, dest, procRow, 'get');
			show(dest.concat('_sloDataTable'));
		}else{
			hide(dest+'_sloDataTable');
		}
	}
	

	dojo.addOnLoad(function(){
		if(dojo.byId('steadyState').checked){
			show('isUnderGoingTransitionRadioGroup');
		}
		
		if(dojo.byId('closed').checked){
			show('accountClosureReasons');
		}
	});
	
	function setFormAction(_formAction){
	   console.log('form action : '+_formAction)
		dojo.byId("formAction").value =  _formAction;
		 console.log('after update form action : '+dojo.byId("formAction").value)
		return true;
	}

	function clearAccountClosureReasons() {
		document.getElementById('contractHelper.closureReasonDesc').value = "";
		document.getElementById('contractHelper.closureReason').value = "select one";
	}
	
	
	function validateForm() {
	var GTLemail=document.forms["editForm"]["GTLemail"].value;
	var GDLemail=document.forms["editForm"]["GDLemail"].value;
	var PEemail=document.forms["editForm"]["PEemail"].value;
	var DPEemail=document.forms["editForm"]["DPEemail"].value;
	var message = "Please enter the following fields : \n";
	var allOk = 0;

	
	/*if (GTLemail==null || GTLemail=="" || GDLemail==null || GDLemail=="" || PEemail==null || PEemail=="" || DPEemail==null || DPEemail=="")
	  {
	  alert("Please enter all mandatory fields");
	  return false;
	  }*/

		if(GTLemail==null || GTLemail==""){
	  		message = message + "Global Tower Lead email \n";
	  		allOk = 1;
		}

		if(GDLemail==null || GDLemail==""){
		  message = message + "Global Delivery Leader email \n";
		  allOk = 1;
		}

		if(PEemail==null || PEemail==""){
		  message = message + "Project Executive email \n";
		  allOk = 1;
		}

		if(DPEemail==null || DPEemail==""){
		  message = message + "Delivery Project Executive email \n";
		  allOk = 1;
		}

		if(allOk==1){
			alert(message);
			return false;
		}
	}

	function clearDcRadio()
	{
		var length = document.getElementById('dcLength').innerHTML;
		for(var i=1; i<length+1; i++)
		{
			document.getElementById('contractHelper.deliveryCenters'+i).checked = false;
		}
	}
	
	function clearImtRadio()
	{
		var length = document.getElementById('imtLength').innerHTML;
		for(var i=1; i<length+1; i++)
		{
			document.getElementById('contractHelper.imtCountries'+i).checked = false;
		}
	}

	function clearForm(oForm) {
	      var elements = oForm.elements; 
		  for(i=0; i<elements.length; i++) {
			field_type = elements[i].type.toLowerCase();
			
			switch(field_type) {
			
				case "text": 
				case "textarea":					
					elements[i].value = ""; 
					break;
		        
				case "radio":
				case "checkbox":
		  			if (elements[i].checked) {
		   				elements[i].checked = false; 
					}
					break;

				case "select-one":
				case "select-multi":
		            		elements[i].selectedIndex = -1;
					break;

				default: 
					break;
			}
		    }
		}
			
	function resetContract()
	{
		var r=confirm("Are you sure you want to reset the form? Resetting the form will clear all the data currently entered");
		if (r==true)
		  {
			setFormAction('reset'); 
			resetIndustry();
			resetForm();
			document.forms["editForm"].submit();
		  }
		else
		  {}
	}

	function resetIndustry()
	{
		var sector = document.getElementById('sectorName').innerHTML;
		if(sector.trim() == 'GB')
		{
			document.getElementById('industrySect.industryId').value = '2'; 
		}
		else if(sector.trim() == 'Communications')
		{
			document.getElementById('industrySect.industryId').value = '14';
		}
		else if(sector.trim() == 'Distribution')
		{	
			document.getElementById('industrySect.industryId').value = '25';
		}
		else if(sector.trim() == 'Financial')
		{
			document.getElementById('industrySect.industryId').value = '34';
		}
		else if(sector.trim() == 'Industrial')
		{
			document.getElementById('industrySect.industryId').value = '45';
		}
		else if(sector.trim() == 'Public')
		{
			document.getElementById('industrySect.industryId').value = '55';
		}
		else if(sector.trim() == 'IBM')
		{
			document.getElementById('industrySect.industryId').value = '82';
		}
	}
	
	function resetForm() {
		var elements = document.getElementsByTagName("input");
		for (var i=0; i < elements.length; i++) {
		  if (elements[i].type == "text") {
		    elements[i].value = "";
		  }
		}

		document.getElementById('customer.name').value="";
		document.getElementById("towers").checked = false;		
		document.getElementById("contractHealth").value = "";
		document.getElementById("reportingInclude").value = "";
		document.getElementById("reportingExclude").value = "";
		document.getElementById("contractIndicator").value = "";
	}
	
	 function addRowSLA(processId, itemName, procRow) {
			var prevAction = dojo.byId("formAction").value;
			console.log("procRow="+procRow);
// 			dojo.byId("formAction").value =  'Save';
// 			dojo.xhrPost({
// 			    form: dojo.byId("contract"),
// 			    // The success handler
// 			    load: function(response) {
// 					dojo.byId("formAction").value =  prevAction;
// 					console.log("SLAs saved ?");
// 					getSLAs(processId, itemName, 'add');
// 			    },
// 			    // The error handler
// 			    error: function() {
// 			    	console.log("An error occured, please try again");
// 			    	dojo.byId("formAction").value =  prevAction;
// 			    	getSLAs(processId, itemName, 'add');
// 			    	return false;
// 			    },
// 			    // The complete handler
// 			    handle: function() {
// 			       // hasBeenSent = true;
// 			    }
// 			});
			getSLAs(processId, itemName, procRow, 'add');
     }
	 
	 function addRowSLO(processId, itemName, procRow) {
		 var prevAction = dojo.byId("formAction").value;
		 console.log("procRow="+procRow);
// 			dojo.byId("formAction").value =  'Save';
// 			dojo.xhrPost({
// 			    form: dojo.byId("contract"),
// 			    // The success handler
// 			    load: function(response) {
// 					dojo.byId("formAction").value =  prevAction;
// 					console.log("SLOs saved ?");
// 					getSLOs(processId, itemName, 'add');
// 			    },
// 			    // The error handler
// 			    error: function() {
// 			    	console.log("An error occured, please try again");
// 			    	dojo.byId("formAction").value =  prevAction;
// 			    	getSLOs(processId, itemName, 'add');
// 			    	return false;
// 			    },
// 			    // The complete handler
// 			    handle: function() {
// 			       // hasBeenSent = true;
// 			    }
// 			});
		 getSLOs(processId, itemName, procRow, 'add');
     }
	 
	
	 
	 function addSLA(tableID, itemName){
		 var table = dojo.byId(tableID);
	 }

	 function addSLO(tableID, itemName){
		 var table = dojo.byId(tableID);
	 }

     function deleteRow(tableID) {
         try {
         var table = document.getElementById(tableID);
         var rowCount = table.rows.length;

         for(var i=0; i<rowCount; i++) {
             var row = table.rows[i];
             var chkbox = row.cells[0].childNodes[0];
             if(null != chkbox && true == chkbox.checked) {
                 table.deleteRow(i);
                 rowCount--;
                 i--;
             }


         }
         }catch(e) {
             alert(e);
         }
     }

        function checkAccessLevel(){
                 console.log("calling checkAccessLevel....")
        		 var accessLevel = $("#accessLevel").val();
            	if(accessLevel == '1' ){
            	 console.log("acessLevel: " + accessLevel);
            		 $('#clientInfoDiv').find('input, textarea').attr('readonly','true');
        			 $('#generalInfoDiv').find('input, textarea').attr('readonly','true');
        			 $('#contractContactsDiv').find('input').attr('readonly','true');
            	 }
        		 if(accessLevel == '0'){
        		  console.log("acessLevel: " + accessLevel);
            		 $('#clientInfoDiv').find('input, textarea').attr('readonly','true');
        			 $('#generalInfoDiv').find('input, textarea').attr('readonly','true');
        			 $('#contractContactsDiv').find('input').attr('readonly','true');
        		 }
             }

