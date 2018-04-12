/**<pre> 
 *==========================================================================
 *
 * Copyright: (C) IBM Corporation 2013 -- IBM Internal Use Only
 *
 *==========================================================================
 *
 *    FILE: formGeneration-1.0.js
 *    CREATOR:Waqar Malik
 *    DEPT: GBS PAK
 *    DATE: 03/09/2014
 *
 *-PURPOSE-----------------------------------------------------------------
 * 
 *--------------------------------------------------------------------------
 *
 *
 *-CHANGE LOG--------------------------------------------------------------
 * 03/09/2014Waqar Malik Initial coding.
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

// /////////////////////////// search filter // ////////////////////////////////////////

function generateForm(){
	// Local var representing if the form has been sent at all
	//var hasBeenSent = false;
	// Local var representing node to be updated
	dojo.byId('progress-bar').style.display='block';
	var contractListDiv = dojo.byId("addContractResponse");
	// Using dojo.xhrPost, as the amount of data sent could be large
	dojo.xhrPost({
	    // The URL of the request
	    url: "generate_cvr_form_ajax.htm",
	    form: dojo.byId("generateCvrForm"),
	    // The success handler
	    load: function(response) {
	    	contractListDiv.innerHTML = response;
	    	wait(2000);
	    	dojo.byId("title").value='';
	    	
	    },
	    // The error handler
	    error: function() {
	    	//contractListDiv.innerHTML = "An error occured, please try again."
	    	return false;
	    },
	    // The complete handler
	    handle: function() {
	    	dojo.byId('progress-bar').style.display='none';
	    	
	    	
	    }
	});
	
}


function generateContractsList(){
	// Local var representing if the form has been sent at all
	//var hasBeenSent = false;
	// Local var representing node to be updated
	dojo.byId('progress-bar').style.display='block';
	var contractListDiv = dojo.byId("addContractResponse");
	var div = dojo.byId('contractsList');
	div.innerHTML = "<a href=\"javascript:void(0)\" onclick=\"selectAllContracts();\">All</a>&nbsp;<a href=\"javascript:void(0)\" onclick=\"deselectAllContracts();\">None</a><br>";
	var ajaxDataString = "";
	// Using dojo.xhrPost, as the amount of data sent could be large
	dojo.xhrPost({
	    // The URL of the request
	    url: "get_contracts_list_ajax.htm",
	    form: dojo.byId("generateCvrForm"),
	    // The success handler
	    load: function(response) {
	    	//alert(response);
	    	//contractListDiv.innerHTML = response;
	    	ajaxDataString = response;
	    	var data = ajaxDataString.split("|");
	    	var contractsList = data[0].split(";");
	    	
	    	for(var i = 0; i < contractsList.length; i++)
	    	{
	    		var contractId = contractsList[i].split(":")[0];
	    		var contractName = contractsList[i].split(":")[1];
	    		addCheckBox('arrContracts', 'arrContracts'+(i+1), contractId,contractName,div);
	    	}
	    	
	    	contractListDiv.innerHTML = data[1];
	    	wait(2000);
	    	dojo.byId("title").value='';
	    	
	    },
	    // The error handler
	    error: function() {
	    	//contractListDiv.innerHTML = "An error occured, please try again."
	    	return false;
	    },
	    // The complete handler
	    handle: function() {
	    	dojo.byId('progress-bar').style.display='none';
	    	
	    	
	    }
	});
	
	
	
}

function generateEmailIdsList(){
	// Local var representing if the form has been sent at all
	//var hasBeenSent = false;
	// Local var representing node to be updated
	dojo.byId('progress-bar').style.display='block';
	var contractListDiv = dojo.byId("addContractResponse");
	var div = dojo.byId('emailIdsList');
	div.innerHTML = "<a href=\"javascript:void(0)\" onclick=\"selectAllEmailIds();\">all</a>&nbsp;<a href=\"javascript:void(0)\" onclick=\"deselectAllEmailIds();\">none</a><br>";
	var ajaxDataString = "";
	// Using dojo.xhrPost, as the amount of data sent could be large
	dojo.xhrPost({
	    // The URL of the request
	    url: "get_email_ids_list_ajax.htm",
	    form: dojo.byId("generateCvrForm"),
	    // The success handler
	    load: function(response) {
	    	//contractListDiv.innerHTML = response;
	    	ajaxDataString = response;
	    	var data = ajaxDataString.split("|");
	    	var contractsList = data[0].split(";");
	    	
	    	for(var i = 0; i < contractsList.length; i++)
	    	{		    		
	    		addCheckBox('arrEmailIds', 'arrEmailIds'+(i+1), contractsList[i],contractsList[i],div);
	    	}
	    	
	    	contractListDiv.innerHTML = data[1];
	    	wait(2000);
	    	dojo.byId("title").value='';
	    	
	    },
	    // The error handler
	    error: function() {
	    	//contractListDiv.innerHTML = "An error occured, please try again."
	    	return false;
	    },
	    // The complete handler
	    handle: function() {
	    	dojo.byId('progress-bar').style.display='none';
	    	
	    	
	    }
	});
	
	
	
}

function addCheckBox(chkName,chkId,contractId,contractName,div) {
	var input = document.createElement('input');
	var label = document.createElement('label');
	var inlineDiv = document.createElement('div');
	var labeltext = document.createTextNode(contractName);
	
	label.setAttribute("for",chkId);
	label.setAttribute("style","width:150px;float:right; display: inline;");
	label.appendChild(labeltext);
	
	inlineDiv.setAttribute("style","white-space: nowrap;display:inline");
	
	input.type = 'checkbox';		
	input.name = chkName;
	input.id = chkId;
	input.value = contractId;
	input.checked = true;
	
	if(chkName == "arrContracts") {
		input.setAttribute("onclick","clearEmailIdsDiv();");
	}
	
	inlineDiv.appendChild(label);
	inlineDiv.appendChild(input);		
	inlineDiv.appendChild(document.createElement('br'));
	div.appendChild(inlineDiv);
	//div.appendChild(document.createElement('br'));
}

function clearEmailIdsDiv() {
	var div = dojo.byId('emailIdsList');
	div.innerHTML = "";
}

function selectAllEmailIds() {
	var chkBox = document.getElementsByName("arrEmailIds");
	
	for(var i = 0; i < chkBox.length; i++) {
		chkBox[i].checked = true;
	}
}

function deselectAllEmailIds() {
	var chkBox = document.getElementsByName("arrEmailIds");
	
	for(var i = 0; i < chkBox.length; i++) {
		chkBox[i].checked = false;
	}
}

function selectAllContracts() {
	var chkBox = document.getElementsByName("arrContracts");
	
	for(var i = 0; i < chkBox.length; i++) {
		chkBox[i].checked = true;
	}
	clearEmailIdsDiv();
}

function deselectAllContracts() {
	var chkBox = document.getElementsByName("arrContracts");
	
	for(var i = 0; i < chkBox.length; i++) {
		chkBox[i].checked = false;
	}
	clearEmailIdsDiv();
}


function disableEmailTemplateSeletion(skipEmailChkBox) {		
	var emailTemplateSelect = dojo.byId('emailTemplate');
	if(skipEmailChkBox.checked == true){
		emailTemplateSelect.selectedIndex = 0;
		emailTemplateSelect.disabled = true;
	} else {		
		emailTemplateSelect.disabled = false;
	}
}

function loadEmailTemplates(formSelect)
{
	dojo.byId('progress-bar').style.display='block';
	var contractListDiv = dojo.byId("addContractResponse");
	
	var ajaxDataString = "";
	// Using dojo.xhrPost, as the amount of data sent could be large
	dojo.xhrPost({
	    // The URL of the request
	    url: "load_email_templates.htm",
	    form: dojo.byId("generateCvrForm"),
	    // The success handler
	    load: function(response) {

	    	ajaxDataString = response;
	    	var data = ajaxDataString.split("|");
	    	var contractsList = data[0].split(";");
	    	
	    	removeAllOptions("emailTemplate");
	    	for(var i = 0; i < contractsList.length; i++)
	    	{	
	    		var value = contractsList[i].split(":")[0];
	    		var text = contractsList[i].split(":")[1];
	    		addOption("emailTemplate",text,value);
	    	}
	    	
	    	contractListDiv.innerHTML = data[1];
	    	wait(2000);
	    	dojo.byId("title").value='';
	    	
	    },
	    // The error handler
	    error: function() {
	    	//contractListDiv.innerHTML = "An error occured, please try again."
	    	return false;
	    },
	    // The complete handler
	    handle: function() {
	    	dojo.byId('progress-bar').style.display='none';
	    	
	    	
	    }
	});

}

function addOption(selectbox,text,value )
{	
	if(getById(selectbox)!=null)
	{		
		var optn = document.createElement("OPTION");
		optn.text = text;
		optn.value = value;
		//selectbox.options.add(optn);
		//alert("text>>"+text);
		//alert("value>>"+value);
		if(text != null && text != 'undefined' && value != '' &&  getById(selectbox).options !=null ){
		getById(selectbox).options.add(optn);
		
		}
	}
}

function removeAllOptions(selectbox) {
	
	if(getById(selectbox)!=null && getById(selectbox).options !=null ){
		var i;
		for(i=getById(selectbox).options.length-1;i>0;i--){
			getById(selectbox).remove(i);
		}
	}
}
// ///////////////////////// end search filter // /////////////////////////////////////

