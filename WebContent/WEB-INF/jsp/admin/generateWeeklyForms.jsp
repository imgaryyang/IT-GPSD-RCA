<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript" src="<c:url value="/resources/spring/Spring.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/spring/Spring-Dojo.js" />"></script>
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
						
						
						<div id="ibm-mapping-app" class="ibm-container main">
						
						<div id="ibm-leadspace-head" class="ibm-alternate">
								<div id="ibm-leadspace-body">
									<ul id="ibm-navigation-trail">
										<li>GPSDCHIP</li>
										<li><a href="#">Admin</a></li>
										<li>Main</li>
									</ul>									
									<h1 class="ibm-small">Main</h1>
																		
								</div>	
							</div>
							<div id="requiredFieldExplaination">
								<p>Required fields are marked with asterisk (<span class="ibm-required">*</span>)</p>
							</div>
									
							<div class="ibm-container-body">
							<form:form id="generateWeeklyForm" action="generateWeeklyForm.htm"  commandName="weeklyForm" cssClass="ibm-column-form ibm-styled-form" enctype="multipart/form-data" method="post">
							
							<div id="generateFormResponseDiv"><p><span id="generateFormResponse" style="color: red;"></span>
										<br/></p>
									</div>
									
							<table width="100%" cellspacing="1" cellpadding="1">
									<tbody>
										<tr>
											<td align="left"><span>Month</span></td>
											<td align="left"><span>
											<form:select path="month" tabindex="11" >
												<form:options items="${monthList}" />
											</form:select>
											</span></td>
											
										</tr>
										
										<tr>
											<td align="left"><span>Year</span></td>
											<td align="left"><span> 
											<form:select path="year" tabindex="11" >
												<form:options items="${years}" />
											</form:select>
											</select> </span></td>
										</tr>
										
										<tr>
											<td align="left"><span>Contracts</span></td>
											<td align="left"><span> 
												<form:checkboxes items="${contractList}" path="contractIdList" />
											</span></td>
										</tr>
									</tbody>
								</table>
								<input type="button" value="Generete Form" name="ibm-submit" tabindex="16" id="ibm-cancel" class="ibm-btn-cart-sec"  onclick="generateForm();">
							</form:form> 
							</div>
						</div><!-- mapping top -->
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
dojo.replaceClass("adminTab", "ibm-active");
dojo.byId("adminSubTabs").style.display = 'block';
dojo.replaceClass("generateWeeklyFormTab", "ibm-active");

function generateForm(){
	// Local var representing if the form has been sent at all
	//var hasBeenSent = false;
	// Local var representing node to be updated
	dojo.byId('progress-bar').style.display='block';
	var contractListDiv = dojo.byId("generateFormResponse");
	// Using dojo.xhrPost, as the amount of data sent could be large
	dojo.xhrPost({
	    // The URL of the request
	    url: "generateWeeklyForm.htm",
	    form: dojo.byId("generateWeeklyForm"),
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