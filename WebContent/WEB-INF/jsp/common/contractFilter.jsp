<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!--<style type="text/css" media="all">
.lr-mar-10px {margin-left: 10px; margin-right:10px; margin-bottom: 2px; margin-top: 2px }
</style> -->


<form:form commandName="searchFilter" method="POST" id="searchFilterForm" action="contractList.htm">
<div  id="pii" style="display:none">
<p style="display:none">The fields indicated with an asterisk (*) are required to complete this transaction; other fields are optional. If you do not want to provide us with the required information, please use the "Back" button on your browser to return to the previous page, or close the window or browser session that is displaying this page.</p>
</div>
<fieldset style="border: 1px solid #ccc;" class="ibm-container-body">
<legend id="fieldset-legend"><span><h3>Filter Criteria</h3></span></legend>											
<div id="searchFilters-main">
<div style="padding: 0 5px 0 5px;" role="region" aria-label="form input">

<form:hidden path="pagination.pageNumber" id="page" />
<form:hidden path="pagingAction" id="pagingAction" />
<table width="100%" cellspacing="4" cellpadding="4" id="filterTab"
	class="ibm-results-table" role="presentation">
	<tbody>
	<thead>
		<tr>
			<td>
			<table class="ibm-results-table" width="100%" cellspacing="1" cellpadding="1" style="margin-bottom: 10px;" summary="The following table shows the labels of profile listing">
				<tbody>
                 <thead>
				
					<tr>
						<th align="left" scope="col" width="20%"></th>

						<th align="left" scope="col" width="30%">
						<span>
						<label for="contract">Contract Name</label><br />
						
						<form:select path="contract"  cssStyle="background: white;" id="contract">
									<form:option value="" label="All Contracts" />
									<form:options items="${referenceData.listContracts}" itemValue="title" itemLabel="title"/> 
						</form:select>
						</span></th>
						
						<th scope="col" align="center" width="30%">
						<span>
						<label for="stage" >Stage</label><br />						
                        <form:select path="stage" cssStyle="background: white;" id="stage">
								<form:option value="" label="All" />
								<form:option value="0" label="Active" />
								<form:option value="2" label="Approved" />																	    
							</form:select>
						</span>
						</th>

						<th align="left" scope="col" width="20%"></th>
                        		
					<!--	<th  width="60%">
							<span> 
							<label for="customer">Customer Name</label><br/>
								<form:select path="customer"  cssStyle="background: white;" id="customer">
									<form:option value="" label="All Customers" />
									<form:options items="${referenceData.customers}" itemValue="inac" itemLabel="name"/> 
								</form:select>
							</span>
						</th> -->
						
					</tr>
				 </thead>
				</tbody>
			</table>
			</td>
		</tr>

	<!--	<tr>
			<td>
			<table class="ibm-results-table"  width="100%" cellspacing="1" cellpadding="1" style="margin-bottom: 10px;" summary="The following table shows the labels of profile listing">
				<tbody>
				 <thead>
			
					<tr>
						<th align="left" scope="col" width="55%">
							<span> 
							<label for="customer" >Customer Name</label><br/>
								<form:select path="customer"  cssStyle="background: white;" id="customer">
									<form:option value="" label="All Customers" />
									<form:options items="${referenceData.customers}" itemValue="inac" itemLabel="name"/> 
								</form:select>
							</span>
						</th>
			
					</tr>
				  <thead>
				</tbody>
			</table>
			</td>
		</tr> -->

		
		
		
		<tr>
			<td>
			<table class="ibm-results-table" width="100%" cellspacing="2" cellpadding="2" summary="The following table shows the buttons for profile listing">
				<tbody>
				 <thead>
					<tr height="5">
					</tr>
					<tr>
						<th width="30%" align="left" scope="row">
					     
						</th>
						<th width="20%" align="left" scope="row"><!--<input type="hidden" id="" value="Y" name="">-->
							<input type="button" value="Apply" tabindex="5" name="ibm-submit" class="ibm-btn-cart-sec" onclick=" initiaePagination(); getContracts();">&nbsp; 
						</th>
						<th width="20%" align="left" scope="row"><br />
						<a class="ibm-reset-link" name="ibm-cancel" tabindex="6" id="ibm-cancel" href="javascript:void(0)" onclick="clearFilters();">Clear</a> 
						</th>
						<th width="30%" align="left" scope="row">
					
						</th>
					</tr>
				  </thead>	
				</tbody>
			</table>
			</td>
		</tr>
	  </thead>	
	</tbody>
</table>

</div>
</div>
</fieldset>		

</form:form>