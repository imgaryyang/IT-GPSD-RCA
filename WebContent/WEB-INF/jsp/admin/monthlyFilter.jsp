<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<style type="text/css" media="all">
.lr-mar-10px {margin-left: 10px; margin-right:10px; margin-bottom: 2px; margin-top: 2px }
</style>

<table width="100%" cellspacing="4" cellpadding="4" id="filterTab"
	class="lr-mar-10px">
	<tbody>
		<tr>
			<td>
			<div id="generateMonthlyFormResponseResponseDiv">
			<p><span id="generateMonthlyFormResponseResponse" style="color: red;"></span>
				<br/>
			</p>
			</div>
									
			<table width="100%" cellspacing="1" cellpadding="1">
				<tbody>
					<tr>
						<th width="20%" align="left"></th>
						<th width="30%" align="left"><h5>Month</h5></th>
						<th width="30%" align="left"><h5>Year</h5></th>
						<th width="20%" align="left"></th>
					</tr>
					<tr>
					<th align="right">
							<input type="submit" value="< Previous month" tabindex="5" name="ibm-submit" class="ibm-btn-cart-sec">
						</th>
						<th align="left"><span> <select title="month"
							id="month" tabindex="1" name="stage">
							<option value="January">January</option>
							<option value="2">February</option>
							<option value="March">March</option>
							<option value="April">April</option>
							<option value="May">May</option>
							<option value="June">June</option>
							<option value="July">July</option>
							<option value="August">August</option>
							<option value="September">September</option>
							<option value="October">October</option>
							<option value="November" selected="selected">November</option>
							<option value="December">December</option>
						</select> </span></th>
						<th align="left"><span> <select title="year" id="year" tabindex="2" name="stage">
							<option value="2008">2008</option>
							<option value="2009">2009</option>
							<option value="2010">2010</option>
							<option value="2011">2011</option>
							<option value="2012" selected="selected">2012</option>
							<option value="2013">2013</option>
							<option value="2014">2014</option>
							<option value="2015">2015</option>
						</select> </span></th>
						


						<th align="right"> 
							<input type="submit" value="Next month >" tabindex="5" name="ibm-submit" class="ibm-btn-cart-sec">
						</th>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
		<tr>
			<td>
			<table width="100%" cellspacing="1" cellpadding="1">
				<tbody>
					<tr>
						<th><h5>Contract Name</h5> </th>
						<th><h5>Customer Name</h5></th>
					</tr>
					<tr>
						<th align="left"><span> <select title="Contract Name"
							id="contractName" tabindex="3" name="contractName">
							<option value="">All Contracts</option>
							<option value="201">1967 direct submit check</option>
							<option value="202">A healthcare</option>
							<option value="203">Advance Auto Parts</option>
							<option value="204">Agfa Healthcare (Shanghai) Co., Ltd</option>
							<option value="205">Amazon.com - Daksh</option>
							<option value="206">ANZ Bank Learning</option>
							<option value="207">Applied Material SCM</option>
							<option value="208">Avon Asia Pacific & Japan</option>
							<option value="209">BUNGE FERTILIZANTES (BZBATBB)</option>
							<option value="210">CRM JARC Export/Refund HM
							Administration Center</option>
							<option value="211">L'Oreal Strategic Sourcing Services</option>
							<option value="212">Maintenance & Technical Support
							Business Operations (MTS BA/Ops)</option>
						</select> </span></th>
						<th align="left"><span> 
							<select title="Customer Name" id="custormerName" tabindex="4" name="custormerName">
								<option value="">All Customers</option>
								<option value="201">ATCA</option>
								<option value="201">Advance Auto Parts</option>
								<option value="201">Agfa Healthcare (Shanghai) Co. Ltd</option>
								<option value="201">Amazon.com</option>
								<option value="201">Ciscos sys Inc.</option>
								<option value="201">China United Network Communications
								LTD</option>
								<option value="201">Avon Asia Pacific & Japan</option>
								<option value="201">TRUenergy Services Pty Ltd</option>
								<option value="201">CRM JARC Export/Refund HM
								Administration Center</option>
								<option value="201">Emirates Integrated
								Telecommunications Company PJSC</option>
								<option value="201">SCHNEIDER INDUSTRY CORP.</option>
								<option value="201">Yahoo Inc.</option>
							</select> </span>
						</th>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>

		<tr>
			<td>
			<table width="100%" cellspacing="1" cellpadding="1">
				<tbody>
					<tr>
						<th width="30%" align="center"><h5>Access</h5></th>
						<th width="30%" align="center"><h5>Status</h5></th>
						<th width="40%" align="center"><h5>Stage</h5></th>
					</tr>
					<tr>
						<th ><span> <select title="access" id="access" tabindex="4" name="access">
											<option value="">-- All --</option>
											<option value="1">Viewable</option>
											<option value="2">Editable</option>
						</select> </span></th>
						
						<th ><span> <select title="Status" id="status" tabindex="4" name="status">
								<option value="">-- All --</option>
								<option value="1">Green</option>
								<option value="2">Amber</option>
								<option value="2">Red</option>
						</select> </span></th>
						<th><span> <select title="stage" id="stage" tabindex="4" name="stage">
								<option value="">-- All --</option>
								<option value="1">Active</option>
								<option value="2">Approved</option>
							</select> </span></th>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>

		<tr>
			<td>
			<table width="100%" cellspacing="1" cellpadding="1">
				<tbody>
					<tr>
						<th width="30%" align="center"><h5>LOB</h5></th>
						<th width="30%" align="center"><h5>Tower</h5></th>
						<th width="40%" align="center"><h5>Sector</h5></th>
					</tr>
					<tr>
						<th ><span> <select title="lob" id="lob" tabindex="4" name="lob">
											<option value="">All LOBs</option>
											<option value="1">BPD</option>
											<option value="2">Daksh</option>
						</select> </span></th>
						<th ><span> <select title="tower" id="tower" tabindex="4" name="tower">
											<option value="">All Towers</option>
											<option value="1">Banking</option>
											<option value="2">FA</option>
											<option value="2">HealthCare</option>
											<option value="2">HR</option>
											<option value="2">Insurance</option>
											<option value="2">SCM</option>
											<option value="2">SDD</option>
						</select> </span></th>
						<th ><span> <select title="sector" id="sector" tabindex="4" name="sector">
											<option value="">All Sectors</option>
											<option value="1">Communications</option>
											<option value="2">Distribution</option>
											<option value="2">Financial</option>
						</select> </span></th>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
		
		<tr>
			<td>
			<table width="100%" cellspacing="1" cellpadding="1">
				<tbody>
					<tr>
						<th width="20%" align="center"><h5>Geo</h5></th>
						<th width="30%" align="center"><h5>IOT</h5></th>
						<th width="25%" align="center"><h5>IMT</h5></th>
						<th width="25%" align="center"><h5>Country</h5></th>
					</tr>
					<tr>
					
						<th ><span> <select title="geo" id="geo" tabindex="4" name="geo">
											<option value="">All Geos</option>
											<option value="1">AG</option>
											<option value="2">AP</option>
											<option value="2">EMEA</option>
						</select> </span></th>
						<th ><span> <select title="iot" id="iot" tabindex="4" name="iot">
											<option value="">All IOTs</option>
											<option value="1">AP</option>
											<option value="2">CEEMEA</option>
											<option value="3">NE Europe</option>
											<option value="4">SW Europe</option>
						</select> </span></th>
						<th ><span> <select title="imt" id="imt" tabindex="4" name="imt">
											<option value="">All IMTs</option>
											<option value="1">Apls</option>
											<option value="2">Germany</option>
											<option value="3">BeNeLux</option>
						</select> </span></th>
						<th ><span> <select title="country" id="country" tabindex="4" name="country">
											<option value="">All Countries</option>
											<option value="1">Pakistan</option>
											<option value="2">Germany</option>
											<option value="3">United Arab Emirates</option>
											
						</select> </span></th>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
		
		<tr>
			<td>
			<table width="100%" cellspacing="2" cellpadding="2">
				<tbody>
					<tr height="5">
					</tr>
					<tr>
					
						<th width="20%" align="left"><input type="hidden" id="" value="Y" name=""> 
							<input type="submit" value="Search" tabindex="5" name="ibm-submit" class="ibm-btn-cart-sec">&nbsp; 
						</th>
						<th width="20%" align="left">
							<input type="button" value="Reset" name="ibm-cancel" tabindex="6" id="ibm-cancel" class="ibm-btn-cart-sec">
						</th>
						<th width="60%" align="left">
						
						</th>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
	</tbody>
</table>

<script type="text/javascript">
	
	//dojo.require("dijit.form.Button");
	//dojo.query("listed").onclick(function(e){  });
	dojo.replaceClass("monthlyTab", "ibm-active");
	
	

	function generateMonthlyForm(){
		// Local var representing if the form has been sent at all
		//var hasBeenSent = false;
		// Local var representing node to be updated
		var contractListDiv = dojo.byId("generateMonthlyFormResponse");
		// Using dojo.xhrPost, as the amount of data sent could be large
		dojo.xhrPost({
		    // The URL of the request
		    url: "generateMonthlyForm.htm",
		    form: dojo.byId("generateMonthlyForm"),
		    // The success handler
		    load: function(response) {
		    	alert(response);		    	
				contractListDiv.innerHTML = response;
		    },
		    // The error handler
		    error: function() {
		    	contractListDiv.innerHTML = "An error occured, please try again."
		    	return false;
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