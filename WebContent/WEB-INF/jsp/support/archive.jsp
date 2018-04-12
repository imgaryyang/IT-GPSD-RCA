<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div id="ibm-pcon"><!-- CONTENT_BEGIN -->
	<div id="ibm-content"><!-- TITLE_BEGIN --> 
 		<!-- TITLE_END --> <!-- CONTENT_BODY -->
		<div id="ibm-content-body">
			<div id="ibm-content-main">
				<div class="ibm-columns">
					<div class="ibm-col-6-6">
						<div id="ibm-mapping-app" class="ibm-container main">
							<div class="ibm-container-descs">
								<div id="main-content-top" class="main-content-top-head">
									<div id="main-content-bar" class="columns">
										<div id="msgDiv" align="center" style=" margin-bottom: 1px; " ></div>
										<div id="main-content-title" style="float:left;">
											<div id="main-content-title-text">
												<span><h2>GPSDRCA - Archive</h2></span>
											</div>
										</div>	
										<div id="main-content-btn" align="right">
											<a href="#">Help</a>
											<form:form id="createProfile_action" method="post" action="#" name="createProfile_action">
											<!--  input id="createProfile" class="ibm-btn-cart-sec" type="button" value="Create" onclick="ibmweb.overlay.show('create-profile-box',this);" > -->
											
											</form:form>
										</div>									
									</div>
								</div>
								
							</div>
					<!--		<fieldset style="border: 1px solid #ccc;" class="ibm-container-body">
								<legend id="fieldset-legend"><span><h3>Filter Criteria</h3></span></legend>											
									<div id="searchFilters-main">
										<div style="padding: 0 5px 0 5px;"> -->
												<tiles:insertAttribute name="searchFilter" />
							<!--			</div>
									</div>
							</fieldset> -->							
							
							<div class="ibm-container-body" id="archiveList">
														
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
							



<script type="text/javascript">
dojo.replaceClass("supportTab", "ibm-active");
dojo.byId("supporSubtTab").style.display = 'block';
dojo.replaceClass("archiveTab", "ibm-active");
	getArchiveList();
	function resetPageNo()
	{
		dojo.byId("page").value = "1";
	}

function getArchiveList(){
	// Local var representing if the form has been sent at all
	//var hasBeenSent = false;
	// Local var representing node to be updated
	var archiveListDiv = dojo.byId("archiveList");
	// Using dojo.xhrPost, as the amount of data sent could be large
	dojo.xhrPost({
	    // The URL of the request
	    //url: "contractList.htm",
	    form: dojo.byId("archiveFilterForm"),
	    // The success handler
	    load: function(response) {
	    	archiveListDiv.innerHTML = response;
	    },
	    // The error handler
	    error: function() {
	    	archiveListDiv.innerHTML = "Your search could not be performed, please try again."
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