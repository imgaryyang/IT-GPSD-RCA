<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--<script type="text/javascript" src="<c:url value="/resources/dojo/dojo.js" />"></script>-->
    <script type="text/javascript" src="<c:url value="/resources/spring/Spring.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/spring/Spring-Dojo.js" />"></script>


<div id="ibm-pcon"><!-- CONTENT_BEGIN -->
	<div id="ibm-content"><!-- TITLE_BEGIN --> 
 		<!-- TITLE_END --> <!-- CONTENT_BODY -->
		<div id="ibm-content-body">
			<div id="ibm-content-main">
				<div class="ibm-columns">
					<div class="ibm-col-6-6">
						<div id="progress-bar" style="color: #ccc; display:none" align="center">
								<br/>
									Please wait... <img alt="loading..." src="images/w3/loader.gif">
								<br/>
								<br/>
								<br/>
						</div>						
						<div id="contractDetailContainer" class="ibm-container">								
							<div id="ibm-leadspace-head" class="ibm-alternate">
								<div id="ibm-leadspace-body">
									<ul id="ibm-navigation-trail">
										<li><a href="/ISCRCA/contracts.htm">GPSDRCA</a></li>
										<li><a href="/ISCRCA/admin.htm">Admin</a></li>
										<li><a href="/ISCRCA/modifyDeleteContracts.htm">Modify Account</a></li>
									</ul>									
									<h1 class="ibm-small">Modify Account</h1>
									<!--<p><em></em></p>-->
																		
								</div>	
							</div>
							<h2 class="ibm-rule" style="display:none">Modify Account</h2>
							<div id="requiredFieldExplaination">
								<p>Required fields are marked with asterisk (<span class="ibm-required">*</span>)</p>
							</div>
							<div id="body" class="ibm-container-body">
								<br/>
								<div id="contractInformation">
									<!--<h2>Client Information</h2>-->
									<div class="ibm-alternate-rule"><hr /></div>
									<!-- Single Column Form -->
									
									<div id="addContractResponseDiv"><p><span id="modifyContractResponse" style="color: #d9182d;"></span>
										<br/></p>
									</div>
									
									<form:form id="addContractForm" action="addNewContract.htm" commandName="contract" cssClass="ibm-column-form" enctype="multipart/form-data" method="post">
										<div  id="pii" style="display:none">
                                         <p style="display:none">The fields indicated with an asterisk (*) are required to complete this transaction; other fields are optional. If you do not want to provide us with the required information, please use the "Back" button on your browser to return to the previous page, or close the window or browser session that is displaying this page.</p>
                                       </div>
										<p>										
											<label for="title">Name:<span class="ibm-required">*</span></label> 
											<form:hidden path="contractId" id="contractId" />
											<span><form:input path="title" size="40"  onfocus='dojo.byId("modifyContractResponse").innerHTML=""; ' /></span>	
										</p>
										
								<!--		<p id="radioButtons">	
                                           <div role="radiogroup" aria-label="Customer">										
											<label>Customer:<span class="ibm-required">*</span></label> 
											<span  class="ibm-input-group ibm-radio-group">
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<form:radiobutton id="listed" path="contractHelper.customerPreference" value="Listed"  onclick="return myOnClick();"></form:radiobutton>
												
												<label for="listed">Listed</label> 
												<form:radiobutton id="unListed" path="contractHelper.customerPreference" value="Unlisted" /><label for="unListed">Unlisted</label>
											</span>	
										</div>	
										</p> -->
										
										<div id="listedCustomer" style="display: none">
											<p><span style="width: 297px;">
											<label for="customer.inac" style="display:none"> Customer INAC:<span class="ibm-required">*</span></label>
												<form:select path="customer.inac" cssClass="required" cssStyle="background: white;">											
													<form:options items="${referenceData.customers}" itemValue="inac" itemLabel="name"/> 
												</form:select>
												</span>
											</p>
										</div>
										
										<div id="unListedCustomer" style="display: none">
											<p>
												<label for="contractHelper.inac">INAC:<span class="ibm-required">*</span></label>
												<span>
													<form:input path="contractHelper.inac" size="40" />
												</span>
											</p>
											
											<p>								
												<label for="contractHelper.customerName">Customer Name:<span class="ibm-required">*</span></label>
												<span>
													<form:input path="contractHelper.customerName" size="40" />	
												</span>
											</p>										
										</div>
										<div class="ibm-rule"><hr//></div>
											<div class="ibm-columns">
												<div class="ibm-col-6-1">
													<p><input value="Save" name="ibm-submit" class="ibm-btn-pri" type="button" onClick="modifyContract();"></p>
												</div>
											</div>
									</form:form>
									<!-- FORM_END -->
										
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
	
	//dojo.require("dijit.form.Button");
	//dojo.query("listed").onclick(function(e){  });
	dojo.replaceClass("adminTab", "ibm-active");
	dojo.byId("adminSubTabs").style.display = 'block';
	dojo.replaceClass("profileAddContractTab", "ibm-active");
	
	function myOnClick(){
		if(dojo.byId("listed").checked){
			show('listedCustomer');
			hide('unListedCustomer');
		}else if(dojo.byId("unListed").checked){
			show('unListedCustomer');
			hide('listedCustomer');
		}
		//alert('Something clicked');
  	};

	/* dojo.addOnLoad(
			function(){
				dojo.connect(dojo.byId("radioButtons"), "click", myOnClick);
				
	}); */

	function modifyContract(){
		// Local var representing if the form has been sent at all
		//var hasBeenSent = false;
		// Local var representing node to be updated
		dojo.byId('progress-bar').style.display='block';
		var contractListDiv = dojo.byId("modifyContractResponse");
		// Using dojo.xhrPost, as the amount of data sent could be large
		dojo.xhrPost({
		    // The URL of the request
		    url: "addNewContract.htm",
		    form: dojo.byId("addContractForm"),
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

	
	
	

	function show(id){
		dojo.byId(id).style.display = 'block';
	}

	function hide(id){
		dojo.byId(id).style.display = 'none';
	}

	
</script>