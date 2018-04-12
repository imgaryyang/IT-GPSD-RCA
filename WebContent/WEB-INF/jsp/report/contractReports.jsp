<%@page import="com.gps.util.UserSession"%>
<%@page import="com.gps.vo.GpsUser"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
// System.out.println("loading header.jsp......");
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
System.out.println("showing contractReports.jsp....");
String userEmail = "";
UserSession userSession = (UserSession)request.getSession().getAttribute("userSession");
if(userSession != null){
// 	System.out.println("bpd user......");
	GpsUser bpdUser =  userSession.getGpsUser();
	if(bpdUser != null)
	userEmail = bpdUser.getEmail();
// 	System.out.println("userEmail = "+userEmail);
}
%>


<!--
<style type="text/css" media="all">
.lr-mar-5px {margin-left: 5px; margin-right: 5px}
.r-mar-5px {margin-right: 5px}
.dot-bor-1px {border:2px dotted #CCC}
.sol-bor-1px { border:1px solid #CCC}
</style>
-->


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
												<span><h2>GPSCHIP - Contract Profile Reports</h2></span>
											</div>
										</div>
										
										<div id="main-content-btn" align="right">
									
										</div>
									</div>
								</div>
								
							</div>
						
				<!--		<div id="ibm-leadspace-head" class="ibm-alternate">
								<div id="ibm-leadspace-body">
									<ul id="ibm-navigation-trail">
										<li><a href="#">GPSDCHIP</a></li>
										<li><a href="/GPSApplication/monthlyDisclose.htm">Reports</a></li>
										<li><a href="/GPSApplication/profileReports.htm">Contract Profile Reports</a></li>
									</ul>									
									<h1 class="ibm-small">Contract Profile Reports</h1>
																		
								</div>	
							</div> -->
							
									
<!--							<fieldset style="border: 1px solid #ccc;" class="ibm-container-body">-->
<!--								<legend id="fieldset-legend"><span><h3>Filter Criteria</h3></span></legend>											-->
<!--									<div id="searchFilters-main">-->
<!--										<div style="padding: 0 5px 0 5px;">-->
												<tiles:insertAttribute name="searchFilter" />
<!--										</div>-->
<!--									</div>-->
<!--							</fieldset>										-->
							
							<div class="ibm-container-body">
							<br />
							<a target="_blank" href="http://mopbz171107.pssc.mop.fr.ibm.com:8080/reports/BPDCHIP?action=viewreports&uid=<%=userEmail %>">Reports till December, 2013</a>
							<br />
							<br />
							
							<table class="ibm-results-table" width="100%">
							<thead>
								<tr>
									<th scope="row" width="70%"><div class="ibm-container-body" id="contractList"> </div></th>
									<th scope="row">
 										<input type="button" class="ibm-btn-cart-sec" value="Detailed Contract Info" onclick="generateProfileReport();" /><br />
 										<input type="button" class="ibm-btn-cart-sec" value="Profile SLA" onclick="generateProfileSLAReport();" /><br />
 										<input type="button" class="ibm-btn-cart-sec" value="Profile SLO" onclick="generateProfileSLOReport();" /><br />
									</th>
								</tr>
							 </thead>	 
							</table>
							</div>
						</div><!-- mapping top -->
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="js/reports-1.0.js" > // </script>
<script> 
	dojo.replaceClass("reportTab", "ibm-active");
	dojo.byId("reportSubTabs").style.display = 'block';
	dojo.replaceClass("reportContractTab", "ibm-active");
	dojo.byId("page").value = 1;
	
</script>