<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> 
<div id="ibm-pcon"><!-- CONTENT_BEGIN -->
	<div id="ibm-content"><!-- TITLE_BEGIN --> 
 		<!-- TITLE_END --> <!-- CONTENT_BODY -->
		<div id="ibm-content-body">
			<div id="ibm-content-main">
				<div class="ibm-columns">
					<div class="ibm-col-6-6">
							<div id="progress-bar" style="color: gray; display:none" align="center">
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
										<li>GPSDRCA</li>
										<li><a href="#">Admin</a></li>
										<li>Modify/Delete Account(s)</li>
									</ul>									
									<h1 class="ibm-small">Modify/Delete Account(s)</h1>
									<!--<p><em></em></p>-->
																		
								</div>
								
								
							</div>
							<h2 class="ibm-rule"></h2>
							
							<div class="ibm-container-body" id="searchFilterDiv">
							
								<fieldset style="border: 1px solid;" class="ibm-container-body">
									<legend id="fieldset-legend">Filter Criteria</legend>											
										<form:form commandName="searchFilter" method="POST" id="searchFilterForm" action="modifyDeleteContractList.htm">
											<div id="searchFilters-main">
												<div style="padding: 0 5px 0 5px;">
													<table width="100%" cellspacing="2" cellpadding="2" id="filterTab" style="margin-bottom: 2px;">													
														<tbody>
														<tr>
															<td width="30%" align="left"><h5>Contract Name</h5></td>														
								 						</tr>
														<tr >
															<td align="left">
																<span> <form:input path="contract" maxlength="100" size="100" /> </span>
															 </td>
															<td align="right">															 
																<input type="button" value="Search" tabindex="5" name="ibm-submit" class="ibm-btn-cart-sec" onclick="getContracts();">&nbsp;
															</td>
														</tr>
														
														
													</tbody>
												</table>
											</div>
										</div>
									</form:form>
								</fieldset>	
								
							
								<div class="filterbar" style="height: 20px; text-align: right; font-size: 0.9em; font-weight: bold; margin-top: 5px;">
									<form action="nextPrevious" method="GET">
                                    									<span> Showing Records [ ${startRow} - ${endRow} ] of Total ${totalRowCount}       </span>
                                    									<span>    </span>
                                    								<c:if test="${totalRowCount>25}">
                                    									<a href="javascript:void(0)" rel="prev" onclick="return getPreviousPage();">>> Previous </a>
                                    									  |
                                    									<a href="javascript:void(0)" rel="next" onclick="return getNextPage();">Next <<</a>
                                    								</c:if>
                                    								</form>
								</div>


								
							</div>				
							<div class="ibm-container-body" id="contractList">
								
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>



<script type="text/javascript" src="js/contracts-1.0.js" > // </script>
<script type="text/javascript">

	dojo.replaceClass("adminTab", "ibm-active");
	dojo.byId("adminSubTabs").style.display = 'block';
	dojo.replaceClass("profileModifyDeleteTab", "ibm-active");
	
	function getContracts(){
		// Local var representing if the form has been sent at all
		//var hasBeenSent = false;
		// Local var representing node to be updated
		dojo.byId('progress-bar').style.display='block';
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
		    	dojo.byId('progress-bar').style.display='none';
		       // hasBeenSent = true;
		    }
		});
	}


	
	function modifyContract(contractId){
		console.log("modifyContract()........"+contractId);
		var URL = "<%=basePath%>showModifyContract.htm?contract_id="+contractId;
		window.location.href = URL;
	}
	
	function deleteContract(contractId){
		console.log("deleteContract()........"+contractId);
		// Using dojo.xhrPost, as the amount of data sent could be large
		dojo.byId('progress-bar').style.display='block';
		var URL = "<%=basePath%>deleteContract.htm";
		var msgBox = dojo.byId("upDelResponse");
		// The parameters to pass to xhrGet, the url, how to handle it, and the callbacks.
		  var xhrArgs = {
		    url: URL,
		    handleAs: "text",
		    preventCache: true,
		    content: {
		    	contract_id: contractId
		    },
		    load: function(data){
		    	console.log(URL+" [Success]");
		    	 getContracts();
                 // data = data.replace(/\t/g, "first/last/only");
             	//dojo.place(data, msgBox, "only");
		    },
		    error: function(error){
		       	console.log("[Error] "+error);
		    	modifyAclDiv.innerHTML = "An unexpected error occurred: " + error;
		    }
		  }
			
		  // Call the asynchronous xhrGet
		 var deferred = dojo.xhrGet(xhrArgs);
		 console.log("deleteContract() End");
	}

	function removeContracts(){
		// Local var representing if the form has been sent at all
		//var hasBeenSent = false;
		// Local var representing node to be updated
		//var contractListDiv = dojo.byId("addContractResponse");
		// Using dojo.xhrPost, as the amount of data sent could be large
		dojo.byId('progress-bar').style.display='block';
		dojo.xhrPost({
		    // The URL of the request
		    url: "removeContracts.htm",
		    form: dojo.byId("modifyDeleteContractsForm"),
		    // The success handler
		    load: function(response) {
		    	getContracts();
		    },
		    // The error handler
		    error: function() {
		    	//contractListDiv.innerHTML = "An error occured, please try again."
		    	return false;
		    },
		    // The complete handler
		    handle: function() {
		    	//dojo.byId('progress-bar').style.display='none';
		    }
		});
		
	}


	

	
	
</script>