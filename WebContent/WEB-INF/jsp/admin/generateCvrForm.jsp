<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--<script type="text/javascript" src="<c:url value="/resources/dojo/dojo.js" />"></script>-->
    <script type="text/javascript" src="<c:url value="/resources/spring/Spring.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/spring/Spring-Dojo.js" />"></script>

<style type="text/css" media="all">
.bar-green-dark	{background:#7a3; color:#fff; padding:.3em .5em; font-size:1.1em; font-weight:bold;}
.bar-green-med-dark	{background:#9c3; color:#fff; padding:.3em .5em; font-size:1.1em; font-weight:bold;}
.bar-green-med-light	{background:#bd6; color:#000; padding:.3em .5em; font-size:1.1em; font-weight:bold;}
.bar-blue-med-dark	{background:#47b; color:#fff; padding:.3em .5em; font-size:1.1em; font-weight:bold;}
.bar-blue-med	{background:#69c; color:#fff; padding:.3em .5em; font-size:1.1em; font-weight:bold;}
.bar-blue-med-light	{background:#9be; color:#000; padding:.3em .5em; font-size:1.1em; font-weight:bold;}
.bar-gray-dark	{background:#666; color:#fff; padding:.3em .5em; font-size:1.1em; font-weight:bold;}
.bar-gray-med-dark	{background:#999; color:#fff; padding:.3em .5em; font-size:1.1em; font-weight:bold;}
.bar-gray-med-light	{background:#ccc; color:#000; padding:.3em .5em; font-size:1.1em; font-weight:bold;}
.bar-gray-light	{background:#ddd; color:#000; padding:.3em .5em; font-size:1.1em; font-weight:bold;}
.lr-mar-10px {margin-left: 10px; margin-right:10px}
.dot-bor-1px {border:2px dotted #CCC}
.sol-bor-1px { border:1px solid #CCC}
.contract-contact-info{width:180px}
</style>

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
										<li>GPSDCHIP</li>
										<li><a href="#">Admin</a></li>
										<li>Form Generation</li>
									</ul>									
									<h1 class="ibm-small">Form Generation</</h1>
									<!--<p><em></em></p>-->
																		
								</div>	
							</div>
							<h2 class="ibm-rule"></h2>
							<div id="requiredFieldExplaination">
								<p>Required fields are marked with asterisk (<span class="ibm-required">*</span>)</p>
							</div>
							<div id="body" class="ibm-container-body">
								<br/>
								<div id="contractInformation">
									<!--<h2>Client Information</h2>-->
									<div class="ibm-alternate-rule"></div>
									<!-- Single Column Form -->
									
									<div id="addContractResponseDiv"><p><span id="addContractResponse" style="color: red;"></span>
										<br/></p>
									</div>
									
									<form:form id="generateCvrForm" enctype="multipart/form-data" method="post">
										<table width="100%" cellspacing="0" cellpadding="0" border="1" id="formGenerationTable" >
													<tbody>
													<tr>
														<td width="10%" align="center"><h5>Form</h5></td>
														<td width="40%" align="center">
															
																	
																		<select id="formName" name="formName" onChange="loadEmailTemplates(this);">
																		    <option value="" >--- Select ---</option>
																		    <option value="monthly" >monthly</option>
																		    <option value="cvr" >cvr</option>
																		    <option value="weekly" >weekly</option>
																		    <option value="weeklyTrans" >weeklyTrans</option>																														    
																		</select>
																	
																															
														</td>
														<td width="10%" align="center"><h5>Date</h5></td>														
														<td width="40%" align="center">
															<input type="text" name="formDate" id="formDate" value=""
															    class="ibm-date-picker"/>			
														</td>
							 						</tr>
													<tr >
														<td width="10%" align="center" valign="middle">
															<input value="List Contracts >>>" name="listContracts" class="ibm-btn-pri" type="button" onclick="generateContractsList();">
														 </td>
														<td width="40%" align="left" valign="middle">
															<div id="contractsList" style="width:200px;height:100px;overflow:auto;border:1px solid black" ></div>
														</td>
														<td width="10%" align="center" valign="middle">
															<input value="List Email Ids >>>" name="listEmailIds" class="ibm-btn-pri" type="button" onclick="generateEmailIdsList();">
														</td>
														<td width="40%" align="left" valign="middle">															 
															<div id="emailIdsList" style="width:200px;height:100px;overflow:auto;border:1px solid black"></div>
														</td>
													</tr>
													<tr >														
														<td width="10%" align="center" colspan="1" valign="middle">															
															<label>Skip Email</label>
														</td>
														<td width="40%" align="center" colspan="1" valign="middle">															 
															<input value="skipEmail" id="skipEmail" name="skipEmail" type="checkbox" onclick="disableEmailTemplateSeletion(this);"/>															
														</td>
														<td width="10%" align="center" colspan="1" valign="middle">															 
															<label for="emailTemplate">Email Template</label>															
														</td>
														<td width="40%" align="center" colspan="1" valign="middle">															 
															<select id="emailTemplate" name="emailTemplate">
															    <option value="" >--- Select ---</option>
															   	<c:forEach items="${emailTemplateList}" var="vo">																			
										                 			<option value="${vo.emailTemplateId}">${vo.emailTemplateName}</option>
										                 		</c:forEach>																														    
															</select>
														</td>
													</tr>
													<tr >														
														<td width="100%" align="center" colspan="4" valign="middle">															 
															<p><input value="Generate" name="ibm-submit" class="ibm-btn-pri" type="button" onclick="generateForm();"></p>
														</td>
													</tr>
													
												</tbody>
											</table>
											
											
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


<script type="text/javascript" src="js/formGeneration-1.0.js"> </script>

<script type="text/javascript">

	dojo.replaceClass("adminTab", "ibm-active");
	dojo.byId("adminSubTabs").style.display = 'block';
	dojo.replaceClass("generateFormTab", "ibm-active");

	function myOnClick(){
		if(dijit.byId("listed").checked){
			show('listedCustomer');
			hide('unListedCustomer');
		}else if(dijit.byId("unListed").checked){
			show('unListedCustomer');
			hide('listedCustomer');
		}
		//alert('Something clicked');
  	};

	dojo.addOnLoad(
			function(){
				dojo.connect(dojo.byId("radioButtons"), "click", myOnClick);
				
	});



	
</script>