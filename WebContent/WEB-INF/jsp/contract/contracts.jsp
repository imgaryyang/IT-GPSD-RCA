<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    System.out.println("showing contracts.jsp....");
%> 
<div id="ibm-pcon"><!-- CONTENT_BEGIN -->
	<div id="ibm-content"><!-- TITLE_BEGIN --> 
 		<!-- TITLE_END --> <!-- CONTENT_BODY -->
		<div id="ibm-content-body">
			<div id="ibm-content-main">
				<div class="ibm-columns">
					<div class="ibm-col-6-6">
						<div id="ibm-mapping-app" class="ibm-container main" role="main" aria-label="main container">
							<div class="ibm-container-descs">
								<div id="main-content-top" class="main-content-top-head">
									<div id="main-content-bar" class="columns">
										<div id="msgDiv" align="center" style=" margin-bottom: 1px; " ></div>
										<div id="main-content-title" style="float:left;">
											<div id="main-content-title-text" role="main" aria-label="main heading">
												<h2>GPSDRCA - Contract Profiles</h2>
											</div>
										</div>
										<div id="main-content-btn" align="right" role="region" aria-label="help" >
										<a href="https://w3-connections.ibm.com/wikis/home?lang=en_US#/wiki/GPS%20Root%20Cause%20Analysis%20%28RCA%29/page/Introduction" target="_blank">Help</a>
											<form:form id="createProfile_action" method="post" action="#" name="createProfile_action">
											<!--  input id="createProfile" class="ibm-btn-cart-sec" type="button" value="Create" onclick="ibmweb.overlay.show('create-profile-box',this);" > -->
											
											</form:form>
										</div>
									</div>
								</div>
								
							</div>
							
				
										 <tiles:insertAttribute name="searchFilter" /> 
												
							
							<div class="ibm-container-body" id="contractList">
								
							</div>
						</div><!-- mapping top -->
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="js/contracts-1.0.js" > // </script>

<script type="text/javascript"> 
	
	dojo.replaceClass("profileTab", "ibm-active");
	/* dojo.byId("profileSubTabs").style.display = 'block'; */
	//dojo.replaceClass("profileContractsTab", "ibm-active");
	dojo.byId("page").value = 1;
	
	getContracts();
</script>
