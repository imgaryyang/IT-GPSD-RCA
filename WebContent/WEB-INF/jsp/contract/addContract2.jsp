<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<div class="ibm-container ibm-alternate ibm-buttons-last">
<div class="ibm-container-body">
<p class="ibm-overlay-intro" style="text-align: justify;">Please select the appropriate data for new contract.</p>

<div><p><span id="addContractResponse" style="color: red;"></span></p></div>

<form:form id="addContractForm" action="addContract.htm" commandName="contract" class="ibm-column-form" method="POST" style="margin-top: 5px;">
<p><label for="name">Name:</label><span><form:input path="title" size="20" cssStyle="width: 175px;" maxlength="200" cssClass="required"/></span></p>
<p><label for="country">Country:</label><span>
											<form:select path="country.countryCode" cssStyle="width: 175px;" cssClass="required">												
												<form:options items="${referenceData.countries}" itemValue="countryCode" itemLabel="name" />
											</form:select>
										</span></p>
<p><label for="sector">Sector:</label><span>
										<form:select path="industrySect.sectorName" cssStyle="width: 175px;" cssClass="required">											
											<form:options items="${referenceData.sectors}" />
										</form:select>
									</span></p>
<p><label for="lob">Line of Business:</label><span>
												<form:select path="lobName" cssStyle="width: 175px;" cssClass="required">
													<form:options items="${referenceData.lobs}" />
												</form:select>
											</span></p>
<p><label for="tower">Tower:</label><span>
										<form:select path="tower.towerId" cssStyle="width: 175px;" cssClass="required">
											<form:options items="${referenceData.towers}" itemValue="towerId" itemLabel="name"/>
										</form:select>
									</span></p>

<p><label for="contractHelper.customerPreference">Customer:</label><span>
												<form:radiobutton path="contractHelper.customerPreference" value="Listed" onclick="show('listedCustomer');hide('unListedCustomer');" />Listed 
												<form:radiobutton path="contractHelper.customerPreference" value="Unlisted" onclick="hide('listedCustomer');show('unListedCustomer');" />Unlisted
											</span></p>
											
<div id="listedCustomer" style="display: none">
	<p><span>
		<form:select path="customer.inac" cssStyle="width: 175px;" cssClass="required">											
			<form:options items="${referenceData.customers}" itemValue="inac" itemLabel="name"/> 
		</form:select>
		</span>
	</p>
</div>

<div id="unListedCustomer" style="display: none">
	<p>
		<label for="contractHelper.inac">INAC:</label>
		<span>
			<form:input path="contractHelper.inac" cssStyle="width: 175px;" cssClass="required" />
		</span>
	</p>
	
	<p>								
		<label for="contractHelper.customerName">Customer Name:</label>
		<span>
			<form:input path="contractHelper.customerName" cssStyle="width: 175px;" cssClass="required" />	
		</span>
	</p>										
</div>

<div style="margin-top: 10px;" class="ibm-overlay-rule"><hr/></div>
<div class="ibm-buttons-row">
<p>
<input value="Cancel" id="create-cancel-btn" type="button" name="ibm-cancel" class="ibm-btn-cancel-sec" onclick="ibmweb.overlay.hide('create-profile-box');"/>
<span class="ibm-sep">&nbsp;</span>
<input value="Create" id="create-submit-btn" type="button" name="ibm-submit" class="ibm-btn-arrow-pri" onClick="addContract();" />
</p>
</div>
</form:form>

</div>
</div>
<script type="text/javascript">
try{
	
}catch(e){}

	function addContract(){
		// Local var representing if the form has been sent at all
		//var hasBeenSent = false;
		// Local var representing node to be updated
		var contractListDiv = dojo.byId("addContractResponse");
		// Using dojo.xhrPost, as the amount of data sent could be large
		dojo.xhrPost({
		    // The URL of the request
		    //url: "validateUser.htm",
		    form: dojo.byId("addContractForm"),
		    // The success handler
		    load: function(response) {
		    	//alert(response);		    	
				contractListDiv.innerHTML = response;
		    },
		    // The error handler
		    error: function() {
		    	contractListDiv.innerHTML = "An error occured, please try again."
		    	return false;
		    },
		    // The complete handler
		    handle: function() {
		       // hasBeenSent = true;
		    }
		});
		
	}

	function show(id){
		dojo.byId(id).style.display = 'block';
	}

	function hide(id){
		dojo.byId(id).style.display = 'none';
	}

</script>

