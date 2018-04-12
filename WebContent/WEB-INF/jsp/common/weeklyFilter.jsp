<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!--<HEAD>
<STYLE type="text/css">
.lr-mar-10px {margin-left: 10px; margin-right:10px; margin-bottom: 2px; margin-top: 2px }
</STYLE>
</HEAD>-->
<form:form commandName="searchFilter" method="POST" id="searchFilterForm">
<form:hidden path="page" id="page"/>
<form:hidden path="pagingAction" id="pagingAction" />

<div  id="pii" style="display:none">
<p style="display:none">The fields indicated with an asterisk (*) are required to complete this transaction; other fields are optional. If you do not want to provide us with the required information, please use the "Back" button on your browser to return to the previous page, or close the window or browser session that is displaying this page.</p>
</div>

<fieldset style="border: 1px solid #ccc;" class="ibm-container-body">
<legend id="fieldset-legend"><span><h3>Filter Criteria</h3></span></legend>											
<div id="searchFilters-main" role="presentation">
<div style="padding: 0 5px 0 5px;" role="presentation">
							
<table width="100%" cellspacing="4" cellpadding="4" id="filterTab"
	class="ibm-results-table" summary=" This table shows all the selection criteria for weekly list">
	<tbody>
	<thead>
		<tr>
			<th scope="row">
			<table class="ibm-results-table" width="100%" cellspacing="1" cellpadding="1" summary="This table shows the labels">
				<tbody>
				<thead>
			<!--		<tr>
						<td></td>
						<td width="9%"></td>
						<th width="15%" scope="col"><h5>Weekly</h5> </th>
						<th width="31%" align="center" scope="col"><h5>Year</h5></th>
						<th align="center" scope="col"><h5>LOB</h5></th>
						<th align="center" scope="col"><h5>Stage</h5></th>
						<th align="center" scope="col"><h5>Tower</h5></th>
					</tr> -->
					<tr>
						<td></td>
						<th width="9%" scope="col"></th>
						<th align="center" scope="col" width="15%">
							<span> 
							  <label for="week" >Weekly</label><br />
								<form:select path="week" title="week" id="week" name="week" style="width: 100px">
									<form:options items="${listWeeks}"  />									    
								</form:select>
								<input type="hidden" id="currentWeek" value="${currentWeek}"  name="currentWeek"/>
							</span>
						</th>
						<th align="left" scope="col" width="31%">
							<span>
							    <label for="year">Year</label><br />	
								<form:select path="year" id="year"  cssStyle="background: white;">									
									<form:options items="${yearList}" />									 
								</form:select>								
							</span>
						</th>
						<th scope="col"><span>
						    <label for="lob">LOB</label><br />	
							<form:select path="lob" cssStyle="background: white;">
								<form:option value="" label="-- All --" />
								<form:options items="${referenceData.lobs}" />
							</form:select>
						</span></th>
						
						<th scope="col">
						<span> 
						    <label for="stage">Stage</label><br />	
							<form:select path="stage" cssStyle="background: white;">
								<form:option value="" label="All" />
								<form:option value="0" label="Active" />
								<form:option value="2" label="Approved" />																	    
							</form:select>
						</span>
						
						</th>
						<th scope="col"><span> 
						    <label for="tower">Tower</label><br />
							<form:select path="tower" cssStyle="background: white;">
								<form:option value="" label="-- All --" />
								<form:options items="${referenceData.towers}" itemValue="towerId" itemLabel="name"/>
							</form:select>
						</span></th>
					</tr>
				  </thead>
				</tbody>
			</table>
			</th>
		</tr>

		<tr>
			<th scope="col">
			<table class="ibm-results-table" width="100%" cellspacing="1" cellpadding="1" summary ="This table shows the labels">
				<tbody>
				 <thead>
				<!--	<tr>
						<th width="55%" scope="col"><h5>Contract Name</h5></th>
						
						<th width="18%" align="center" scope="col"><h5>Country</h5></th>
						<th width="30%" align="center" scope="col"><h5>Geo</h5></th>
						
					</tr> -->
					<tr>
						<th scope="col" width="55%">
							<span> 
							    <label for="contract">Contract Name</label> <br />
								<form:select path="contract"  cssStyle="background: white;">
									<form:option value="" label="All Contracts" />
									<form:options items="${referenceData.listContracts}" itemValue="title" itemLabel="title"/> 
								</form:select>
							</span>
						</th>
						
						
						
						
						<th scope="col" width="18%"><span>
						<label for="country">Country</label> <br />
						<form:select path="country" cssStyle="background: white;">
								<form:option value="" label="-- All --" />
								<form:options items="${referenceData.countries}" itemValue="countryCode" itemLabel="name"/>
							</form:select>
						</span></th>
						<th scope="col"><span>
						    <label for="geo" width="30%">Geo</label><br />
							<form:select path="geo" cssStyle="background: white;">
								<form:option value="" label="-- All --" />
								<form:options items="${referenceData.geos}" />
							</form:select>
						</span></th>
					</tr>
				   </thead>
				</tbody>
			</table>
			</th>
		</tr>

	<!--	<tr>
			<td>
			<table class="ibm-results-table" width="100%" cellspacing="1" cellpadding="1" summary="This table shows the labels and heading">
				<tbody>
				 <thead>
					<tr>
						
						
						<th width="40%" align="center" scope="row"><h5 style="display:none">GPSCHIP</h5></th>
					</tr>
					<tr>
						
						
						<th scope="col" ><span>
							
						</span></th>
					</tr>
				  </thead>	
				</tbody>
			</table>
			</td>
		</tr> -->
		
		<tr>
			<th scope="col">
			<table class="ibm-results-table" width="100%" cellspacing="1" cellpadding="1" summary="This tables shows the labels">
				<tbody>
				 <thead>
				<!--	<tr>
						
						<th width="18%" align="left" scope="col"><h5>IOT</h5></th>
						<th align="left" scope="col"><h5>IMT</h5></th>
					</tr> -->
					<tr>
					
						
						<th  scope="col" width="18%"><span>
						    <label for="iot">IOT</label> <br />
							<form:select path="iot" cssStyle="background: white;">
								<form:option value="" label="-- All --" />
								<form:options items="${referenceData.iots}" />
							</form:select>
						</span></th>
						<th scope="col"><span>
						    <label for="imt">IMT</label> <br />
							<form:select path="imt" cssStyle="background: white;">
								<form:option value="" label="-- All --" />
								<form:options items="${referenceData.imts}"/>
							</form:select>
						</span></th>
					</tr>
					</thead>
				</tbody>
			</table>
			</th>
		</tr>
		
		<tr>
			<th scope="col">
			<table class="ibm-results-table" width="100%" cellspacing="2" cellpadding="2" summary="This table shows the selection criteria buttons">
				<tbody>
				 <thead>
					<tr height="5">
					</tr>
					<tr>
						<th width="30%" align="left" scope="col">
					
						</th>
						<th width="20%" align="left" scope="col" ><!-- <input type="hidden" id="" value="Y" name=""> -->
							<input type="button" value="Apply" tabindex="5" name="ibm-submit" class="ibm-btn-cart-sec" onclick=" getWeeklyStatusList();">&nbsp; 
						</th>
						<th width="20%" align="left" scope="col">
							<a class="ibm-reset-link" name="ibm-cancel" tabindex="6" id="ibm-cancel" href="javascript:void(0)" onclick="clearWeeklyFilters();">Clear</a> 
						</th>
						<th width="30%" align="left" scope="col">
					
						</th>
					</tr>
				  </thead>	
				</tbody>
			</table>
			</th>
		</tr>
	  </thead>
	</tbody>
</table>
</div>
<div>
</fieldset>	
</form:form>
<script>
	
	
	
</script>