<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript">


function initCustomerOther(){
	console.log('initCustomerOther().......');
	var custImpactList = jQuery('#custImpactList').val();
	console.log('custImpactList = '+custImpactList);
	if(custImpactList != ''){
		var array = custImpactList.split(',');
		for(j =0; j < 60; j++){
			var otherBox = document.getElementById('customerImpacted_'+j);
			for (var i = 0; i < array.length; i++) {
			    if(otherBox.value == array[i].trim()){
			    	console.log('otherBox.checked = '+otherBox.value);
			    	otherBox.checked = 'Checked';
			    	jQuery('#customer_other1').show();
			    }
			}
		}
	}
	
}
function showCustomerOther(){
	console.log('showCustomerOther().......');
	var foo = [];
	var other_ = false;
	var custImpactList = '';
	jQuery('#custImpactList').val('');
	for(i =0; i < 60; i++){
		var otherBox = document.getElementById('customerImpacted_'+i);
		if(otherBox.checked){
			custImpactList += otherBox.value + ',';
			if(otherBox.value  == 'Other'){
				 other_ = true;
			}
		}
	}
	
	jQuery('#custImpactList').val(custImpactList);
	if(other_){
		 jQuery('#customer_other1').show();
	} else {
		jQuery('#customerOther').val('');
		jQuery('#customer_other1').hide();
	}
	
// 	var selectedValues = jQuery('#customerImpactedList').val();
	
}

function showCustomerImpactNoneAll(opt){
	console.log('showCustomerImpactNoneAll().......'+opt);
	var foo = [];
	var all_ = false;
	var custImpactList = '';
	jQuery('#custImpactList').val('');
	
	
	if(opt === 'All'){
		all_ = true;
		jQuery('#customer_other1').show();
		jQuery('#customerImpacted_none').attr('checked', false); 
	} else {
		jQuery('#customerImpacted_all').attr('checked', false);
		jQuery('#customerOther').val('');
		jQuery('#customer_other1').hide();
	}
	for(i =0; i < 60; i++){
		var checkBox = document.getElementById('customerImpacted_'+i);
		if(all_){
			checkBox.checked = 'Checked';
			custImpactList += checkBox.value + ',';
		} else {
			checkBox.checked = '';
		}
	}
	
	jQuery('#custImpactList').val(custImpactList);
	
}


</script>

<table id="Contacts" summary="Contacts" class="ibm-results-table" border="0">
    <thead>
         <tr>
            <th scope="col" align width="15%"><span class="ibm-required">*</span> <span style="font-weight: normal;">IBM Managed </span><form:checkbox id="ibmManaged" path="rca.managedBy" value="IBM Managed" onclick="ibmClientManage(this);"/></th>
            <th scope="col" width="15%" ><span class="ibm-required">*</span> <span style="font-weight: normal;">Client Managed</span> <form:checkbox id="clientManaged" path="rca.managedBy" value="Client Managed" onclick="ibmClientManage(this);"/></th>
            <th scope="col" width="5%"></th>
            <th scope="col" width="16.5%"></th>
            <th scope="col" width="43.5%" style="font-weight: normal;"></th>
         </tr>
         <tr>
             <th scope="col" width="15%" ><span class="ibm-required">*</span> Account(s)/Customer Impacted:</th>
             <th scope="col" width="15%" style="font-weight: normal;"><span class="ibm-input-group ibm-radio-group">
					<form:radiobutton id="customerImpactedY" path="rca.customerImpacted"  value="Y" onclick="showCustomerImpact();"/>Yes
					<form:radiobutton id="customerImpactedN" path="rca.customerImpacted" value="N" onclick="showCustomerImpact();"/>No </span>
			</th>
			<th scope="col" width="5%"></th>
			<th scope="col" align width="16.5%"></th>
            <th scope="col" width="43.5%" ></th>
        </tr>
       </thead>
  </table>

<table id="customer_impact_details" summary="Contacts" class="ibm-results-table" border="0" style="display: none;">
	<thead>
		<tr id="customer_impact_options">
			<td scope="col" ></td>
			<td scope="col" >
				<input type="checkbox" id="customerImpacted_all" value="Y" onclick="showCustomerImpactNoneAll('All');"> All &nbsp;&nbsp;
				<input type="checkbox" id="customerImpacted_none" value="N" onclick="showCustomerImpactNoneAll('None');"> None
			</td>

		</tr>
		<tr>
			<th scope="col" align width="16.5%" style="color: #ff0000;"></th>
			<td scope="col">
				<table id="customer_impacted_list" summary="customerImpacted" border="0">
				<form:hidden path="custImpactList" />
					<c:forEach  items="${customerImpactedList}" var="cil" varStatus="itemRow">
						<c:if test="${itemRow.count % 4 == 1}">
							<tr>
						</c:if>
							<td>
								<input type="checkbox" id="customerImpacted_${itemRow.index}" value="${cil}" onclick="showCustomerOther();"> ${cil}
							</td>
						<c:if test="${itemRow.count % 4 == 0 || itemRow.count == fn:length(values)}">
							</tr>
						</c:if>
					</c:forEach>
				</table>
			</td>
		</tr>
		
		<tr id="customer_other1" style="display: none;">
			<th scope="col" ><span class="ibm-required">**</span> Other:</th>
			<th scope="col" ><form:input path="rca.customerOther" id="customerOther" size="80" /></th>

		</tr>
	</thead>
</table>
<c:catch var="boxExcep">

</c:catch>
<c:if test="${boxExcep != null }">
	 <p>The exception is : ${boxExcep} <br />
         There is an exception: ${boxExcep.message}</p>
</c:if>
<%--                                                 	<form:select path="custImpactList" id="customerImpactedList" multiple="true"  onmouseup="showCustomerOther();"> --%>
<%--                                                      	<form:options items="${customerImpactedList}" />  --%>
<%--                                                		</form:select> --%>