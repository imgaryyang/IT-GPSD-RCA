<%@page import="com.gps.util.UserSession"%>
<%@page import="com.gps.vo.GpsUser"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	// System.out.println("loading header.jsp......");
	String userEmail = "";
	UserSession userSession = (UserSession)request.getSession().getAttribute("userSession");
	if(userSession != null){
// 	System.out.println("GPS user......");
		GpsUser gpsUser =  userSession.getGpsUser();
		if(gpsUser != null)
			userEmail = gpsUser.getEmail();
// 	System.out.println("userEmail = "+userEmail);

	}
%>
<div id="ibm-pcon">
	<!-- CONTENT_BEGIN -->
	<div id="ibm-content">
		<!-- TITLE_BEGIN -->
		<!-- TITLE_END -->
		<!-- CONTENT_BODY -->
		<div id="ibm-content-body">
			<div id="ibm-content-main">
				<div class="ibm-columns">
					<div class="ibm-col-6-6">
						<div id="ibm-mapping-app" class="ibm-container main"
							 role="presentation">

							<div class="ibm-container-descs">
								<div id="main-content-top" class="main-content-top-head">
									<div id="main-content-bar" class="columns">
										<div id="msgDiv" align="center" style="margin-bottom: 1px;"></div>
										<div id="main-content-title" style="float: left;">
											<div id="main-content-title-text">
												<span><h2>GPSD RCA - Reports</h2></span>
											</div>
										</div>

										<div id="main-content-btn" align="right"></div>
									</div>
								</div>

							</div>

							<tiles:insertAttribute name="rcaReportFilter" />

							<div id="reportsDiv" class="ibm-container-body" >

								<table class="ibm-results-table" width="100%" border="0">
									<thead>
									<th  id="accountsTh" scope="row" width="70%" bgcolor="#fafafa" style="display:none"><label align="centre">Accounts</label></th>
									<th width="1%"></th>
									<th  id="reportsTh" width="29%"  align="center" bgcolor="#fafafa"  style="display:none" >Reports</th>
									</thead>
									<tbody>
									<tr>
										<td scope="row" width="70%">
											<div class="ibm-container-body" id="contractList" >

											</div>
										</td>
										<th width="1%"></th>
										<th  id="rcaReportsTh" align="centre" scope="row" width="28%">
											<br /> &nbsp;&nbsp; &nbsp;&nbsp; <input type="button" align="centre" id="rca-cdr-rpt-btn" class="ibm-btn-cart-sec" style="display:none; width:150px; height:28px;" value="RCA Coordinator" onclick="loadCheckbox(); generateRCACoordinatorReport();" /> <br />
											&nbsp;&nbsp; &nbsp;&nbsp; <input type="button" align="centre" id="cs-ft-rpt-btn" class="ibm-btn-cart-sec" style="display:none; width:150px; height:28px;" value="Customer Formatted" onclick="loadCheckbox(); generateCustomerFormattedReport();" /> <br />
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="button" align="centre" id="rca-sm-rpt-btn" class="ibm-btn-cart-sec" style="display:none; width:150px; height:28px;" value="RCA Summary" onclick="loadCheckbox(); generateRCASummaryReport();" /> <br />
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="button" align="centre" id="rca-de-rpt-btn" class="ibm-btn-cart-sec" style="display:none; width:150px; height:28px;" value="RCA Detailed" onclick="loadCheckbox(); generateRCADetailedReport();" /> <br />
                                           <!-- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="button" align="centre" id="rca-gn-rpt-btn" class="ibm-btn-cart-sec" style="display:none; width:150px; height:28px;" value="RCA Green Template" onclick="loadCheckbox(); generateGreenTemplateReport();" /> <br /> -->
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="button" align="centre" id="rca-wo-rpt-btn" class="ibm-btn-cart-sec" style="display:none; width:150px; height:28px;" value="Weekly Operations" onclick="loadCheckbox(); generateWeeklyOperationsReport();" /> <br />


										</th>
										<th  id="actionReportsTh" style="display:none;" scope="row">
											<br /> &nbsp;&nbsp; &nbsp;&nbsp;<input id="act-sm-rpt-btn" type="button" class="ibm-btn-cart-sec" value="Action Summary" onclick="loadActionCheckbox(); generateActionSummaryReport();" /> <br />
										</th>

									</tr>
									<tr>
										<th>
											<div class="ibm-container-body" id="rcaList">
												<div id="progress-bar" style="color: #ccc; display: none"
													 align="center">
													<br /> Please wait... <img alt="loading..."
																			   src="images/w3/loader.gif"> <br /> <br /> <br />
												</div>
											</div>
										</th>
									</tr>
									</tbody>
								</table>

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="js/reports-1.0.js"> // </script>

<script type="text/javascript">
	dojo.replaceClass("reportsTab", "ibm-active");
	dojo.byId("reportSubTabs").style.display = 'block';

</script>

<script type="text/javascript">
	function loadCheckbox(){
		console.log('calling loadCheckbox()...');
		var totalRows = dojo.byId("totalRows").value;
		var selectedRcaIdList = [];
		console.log('total Rca: '+ dojo.byId("totalRows").value)
		for(var i=0; i<totalRows;i++){
			if(document.getElementById('selectedRca_'+i).checked){
				var selectRcaValue = dojo.byId("selectedRca_"+i).value
				console.log('selectedRca: '+ selectRcaValue);
				selectedRcaIdList.push("'"+selectRcaValue+"'");
			}
		}
		var str = selectedRcaIdList.join(',');
		console.log('selected : ' + str);
		dojo.query('#selectedRcaNumbers').val(str);
		console.log(dojo.byId("selectedRcaNumbers").value);
	}

	function selectAllRptCheckBoxes(){
		var totalRows = dojo.byId("totalRows").value;
		for(var i=0; i<totalRows;i++){
			if(document.getElementById('selectedRca_'+i).checked == false){
				document.getElementById('selectedRca_'+i).checked = true;
			}
		}
	}

	function unSelectAllRptCheckBoxes(){
		var totalRows = dojo.byId("totalRows").value;
		for(var i=0; i<totalRows;i++){
			if(document.getElementById('selectedRca_'+i).checked == true){
				document.getElementById('selectedRca_'+i).checked = false;
			}
		}
	}

	function loadActionCheckbox(){
		console.log('calling loadActionCheckbox()...');
		var totalRows = dojo.byId("totalRows").value;
		var selectedRcaIdList = [];
		console.log('total Actions: '+ dojo.byId("totalRows").value)
		for(var i=0; i<totalRows;i++){
			if(document.getElementById('selectedRca_'+i).checked){
				var selectRcaValue = dojo.byId("selectedRca_"+i).value
				console.log('selectedRca: '+ selectRcaValue);
				selectedRcaIdList.push("'"+selectRcaValue+"'");
			}
		}
		var str = selectedRcaIdList.join(',');
		console.log('selected : ' + str);
		dojo.query('#selectedActionNumbers').val(str);
		console.log(dojo.byId("selectedActionNumbers").value);
	}
</script>
