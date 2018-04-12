<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!--<style type="text/css" media="all">
.lr-mar-10px {margin-left: 10px; margin-right:10px; margin-bottom: 2px; margin-top: 2px }
</style>
-->

<form:form commandName="monthlySearchFilter" method="POST" id="searchFilterForm" action="monthlyStatusList.htm">
<form:hidden path="pagination.pageNumber" id="page" />
<form:hidden path="pagingAction" id="pagingAction" />


<div  id="pii" style="display:none">
<p style="display:none">The fields indicated with an asterisk (*) are required to complete this transaction; other fields are optional. If you do not want to provide us with the required information, please use the "Back" button on your browser to return to the previous page, or close the window or browser session that is displaying this page.</p>
</div>
<fieldset style="border: 1px solid #ccc;" class="ibm-container-body">
<legend id="fieldset-legend"><span><h3>Filter Criteria</h3></span></legend>											
<div id="searchFilters-main">
<div style="padding: 0 5px 0 5px;">
						
<table width="100%" cellspacing="2" cellpadding="2" id="filterTab"
	class="ibm-results-table" summary="This monthly shows the labels and buttons for monthly forms">
	<tbody>
	<thead>
		<tr>
			<th scope="row">
			<table class="ibm-results-table" width="100%" cellspacing="1" cellpadding="1" style="margin-bottom: 10px;" summary="This table shows the labels for monthly listing">
				<tbody>
				 <thead>
					<!--<tr>
						<th  width="55%"><h5>Contract Name</h5> </th> -->
					<!--	<td></td>
						<td width="9%"></td>
						<th width="15%" scope="col"><h5>Month</h5></th>
						<th width="31%" align="center" scope="col"><h5>Year</h5></th>
						<th align="center" scope="col"><h5>LOB</h5></th>
						<th align="center" scope="col"><h5>Stage</h5></th>
						<th align="center" scope="col"><h5>Tower</h5></th>
						<th align="center" scope="col"><h5>Sector</h5></th>
					</tr> -->
					<tr>
						<th scope="col"></th>
						<th width="9%" scope="col"></th>
						<th width="15%" scope="col"><span>
						<label for="month" ><h5>Month</h5></label>
							<form:select path="month" >
								<form:option value="" label="--Select month--" />
								<form:options items="${referenceData.monthList}" />
							</form:select>
						</span></th>
						
						<th width="31%" align="center" scope="col"><span>
						<label for="year" ><h5>Year</h5></label>
							<form:select path="year" >
								<form:option value="" label="--Select Year--" />
								<form:options items="${referenceData.yearList}" />
							</form:select>
							
						</span></th>
						<!--<th align="left"><span>
						
						<form:select path="contract"  cssStyle="background: white;">
									<form:option value="" label="All Contracts" />
									<form:options items="${referenceData.listContracts}" itemValue="title" itemLabel="title"/> 
						</form:select>
						</span></th> -->
						<th scope="col" align="center"><span>
						<label for="lob" >LOB</label><br />
							<form:select path="lob" cssStyle="background: white;">
								<form:option value="" label="-- All --" />
								<form:options items="${referenceData.lobs}" />
							</form:select>
						</span></th>
						<th scope="col">
						<span> 
						<label for="stage" align="center">Stage</label><br />
							<form:select path="stage" cssStyle="background: white;">
								<form:option value="" label="All" />
								<form:option value="0" label="Active" />
								<form:option value="2" label="Approved" />											    
							</form:select>
						</span>
						</th>
						<th scope="col" align="center"><span> 
						<label for="tower" >Tower</label><br />
							<form:select path="tower" cssStyle="background: white;">
								<form:option value="" label="-- All --" />
								<form:options items="${referenceData.towers}" itemValue="towerId" itemLabel="name"/>
							</form:select>
						</span></th>
						<th scope="row" align="center"><span>
						<label for="sector" >Sector</label><br />
							<form:select path="sector" cssStyle="background: white;">
								<form:option value="" label="-- All --" />
								<form:options items="${referenceData.sectors}"/>
							</form:select> 
						</span></th>
					</tr>
					</thead>
				</tbody>
			</table>
			</th>
		</tr>

		<tr>
			<th scope="row">
			<table class="ibm-results-table" width="100%" cellspacing="1" cellpadding="1" style="margin-bottom: 10px;" summary="This table shows the labels for monthly listings">
				<tbody>
				<thead>
				<!--	<tr>
						
						<!-- <th width="55%"><h5>Customer Name</h5></th> 
						<th  width="55%" scope="col"><h5>Contract Name</h5> </th>
						<th width="18%" align="center" scope="col"><h5>Country</h5></th>
						<th align="center" scope="col"><h5>Geo</h5></th>
					</tr>-->
					<tr>
						<th width="55%" align="left" scope="col"><span>
						<label for="contract"><h5>Contract Name</h5></label>
						<form:select path="contract"  cssStyle="background: white;">
									<form:option value="" label="All Contracts" />
									<form:options items="${referenceData.listContracts}" itemValue="title" itemLabel="title"/> 
						</form:select>
						</span></th>
						<!--<th align="left">
							<span> 
								<form:select path="customer"  cssStyle="background: white;">
									<form:option value="" label="All Customers" />
									<form:options items="${referenceData.customers}" itemValue="inac" itemLabel="name"/> 
								</form:select>
							</span>
						</th> -->
						<th scope="col" width="18%" align="center"><span>
						<label for="country"><h5>Country</h5></label>
						<form:select path="country" cssStyle="background: white;">
								<form:option value="" label="-- All --" />
								<form:options items="${referenceData.countries}" itemValue="countryCode" itemLabel="name"/>
							</form:select>
						</span></th>
						<th scope="col" align="center"><span>
						<label for="geo"><h5>GEO</h5></label>
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

		
		<tr>
			<th scope="row">
			<table class="ibm-results-table" width="100%" cellspacing="1" cellpadding="1" style="margin-bottom: 10px;" summary="This table shows the labels for monthly listings">
				<tbody>
				 <thead>
				<!--	<tr>
						<th  width="55%" scope="row"><h5>Customer Name</h5> </th>
						<!--<th width="30%" ><h5>Month</h5></th>
						<th width="30%" align="center"><h5>Year</h5></th> 
						<th width="18%" align="left" scope="col"><h5>IOT</h5></th>
						<th align="left" scope="col"><h5>IMT</h5></th>
					</tr>-->
					<tr>
						<th  width="55%"  align="left" scope="row">
							<span> 
							    <label for="customer"><h5>Customer Name</h5> </label>
								<form:select path="customer"  cssStyle="background: white;">
									<form:option value="" label="All Customers" />
									<form:options items="${referenceData.customers}" itemValue="inac" itemLabel="name"/> 
								</form:select>
							</span>
						</th>
						<!--<td><span>
							<form:select path="month" >
								<form:option value="" label="--Select month--" />
								<form:options items="${referenceData.monthList}" />
							</form:select>
						</span></td>
						
						<td><span>
							<form:select path="year" >
								<form:option value="" label="--Select Year--" />
								<form:options items="${referenceData.yearList}" />
							</form:select>
							
						</span></td> -->
						<th width="18%" align="left"  scope="col"><span>
						<label for="iot"><h5>IOT</h5> </label>
							<form:select path="iot" cssStyle="background: white;">
								<form:option value="" label="-- All --" />
								<form:options items="${referenceData.iots}" />
							</form:select>
						</span></th>
						<th scope="col"><span>
						    <label for="imt"><h5>IMT</h5> </label>
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
			<th scope="row">
			<table class="ibm-results-table" width="100%" cellspacing="2" cellpadding="2" summary="This table shows the buttons for monthly listings">
				<tbody>
				 <thead>
					<tr height="5">
					</tr>
					<tr>
						<th width="10%" align="left" scope="col">
					
						</th>
						<th width="20%" align="center" scope="col"> <!-- <input type="hidden" id="" value="Y" name=""> -->
							<input type="button" value="Apply" tabindex="5" name="ibm-submit" class="ibm-btn-cart-sec" onclick="getMothlyStatusList();">&nbsp; 
						</th>
						<th width="20%" align="center" scope="col"><br />
							<a class="ibm-reset-link" name="ibm-cancel" tabindex="6" id="ibm-cancel" href="javascript:void(0)" onclick="clearMonthlyFilters();">Clear</a> 
						</th>
						<th width="50%" align="center" scope="col">
					
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
</div>				
</fieldset>			
</form:form>
