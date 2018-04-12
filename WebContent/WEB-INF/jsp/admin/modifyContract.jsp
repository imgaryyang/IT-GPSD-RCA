<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--<script type="text/javascript" src="<c:url value="/resources/dojo/dojo.js"/>"></script>-->
<script type="text/javascript" src="<c:url value="/resources/spring/Spring.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/spring/Spring-Dojo.js" />"></script>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<div id="ibm-pcon"><!-- CONTENT_BEGIN -->
    <div id="ibm-content"><!-- TITLE_BEGIN -->
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
                                        <li><a href="#">Profile</a></li>
                                        <li>Modify Contract</li>
                                    </ul>
                                    <h1 class="ibm-small">Modify Contract</h1>
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
                                    <div id="addContractResponseDiv"><p><span id="addContractResponse"
                                                                              style="color: red;"></span>
                                        <br/></p>
                                    </div>
                                    <form:form id="addContractForm" action="addNewContract.htm" commandName="contract" cssClass="ibm-column-form" enctype="multipart/form-data" method="post">
                                        <p>
                                            <label for="title">Name:<span class="ibm-required">*</span></label>
                                            <span><form:input path="title" size="40"  onfocus='dojo.byId("addContractResponse").innerHTML=""; ' /></span>
                                        </p>

                                        <p id="radioButtons">
                                            <label for="contractHelper.customerPreference">Customer:<span
                                                    class="ibm-required">*</span></label>
											<span class="ibm-input-group ibm-radio-group">
												<form:radiobutton id="listed" path="contractHelper.customerPreference" value="Listed"  onclick="return myOnClick();"></form:radiobutton>
												<label for="listed">Listed</label> 
												<form:radiobutton id="unListed" path="contractHelper.customerPreference" value="Unlisted"/>
												<label for="unListed">Unlisted</label>
											</span>
                                        </p>
										<c:choose>
											<c:when test="${contract.contractHelper.customerPreference == 'Listed' }">
                                        		<div id="listedCustomer" style="display: block">
                                        	</c:when>
                                        	<c:otherwise>
                                        		<div id="listedCustomer" style="display: none">
                                        	</c:otherwise>
                                        </c:choose>
                                            <p><span style="width: 297px;"> preference = ${contract.contractHelper.customerPreference}
												<form:select path="customer.inac" cssClass="required" cssStyle="background: white;">
                                                    <form:options items="${referenceData.customers}" itemValue="inac" itemLabel="nameAndInac"/>
                                                </form:select>
												</span>
                                            </p>
                                        </div>
                                        <div id="unListedCustomer" style="display: none">
                                            <p>
                                                <label for="contractHelper.inac">INAC:<span
                                                        class="ibm-required">*</span></label>
												<span>
													<form:input path="contractHelper.inac" size="40" />
												</span>
                                            </p>

                                            <p>
                                                <label for="contractHelper.customerName">Customer Name:<span
                                                        class="ibm-required">*</span></label>
												<span>
												<form:input path="contractHelper.customerName" size="40" />			
												</span>
                                            </p>
                                        </div>
                                        <div class="ibm-rule">
                                            <hr/>
                                        </div>
                                        <div class="ibm-columns">
                                            <div class="ibm-col-6-1">
                                                <p><input value="Save" name="ibm-submit" class="ibm-btn-pri"
                                                          type="button" onClick="addContract();"></p>
                                            </div>
                                        </div>
                                    </form:form>
                                    <div class="ibm-alternate-rule"></div>
                                    <div>
                                        <br/><br/> <br/>
                                        <form:select id="contractId" path="contract"  cssStyle="background: white;">
                                            <form:option value="" label="All Contracts" />
                                            <form:options items="${referenceData.listContracts}" itemValue="contractId" itemLabel="title"/>
                                        </form:select>
                                    </div>
                                    <div class="ibm-columns">
                                        <br/>
                                        <div class="ibm-col-6-1">
                                            <p><input value="Load Details" id="loadContract-submit-btn" type="button" name="loadContract-submit-btn"
                                                      class="ibm-btn-arrow-pri" onClick="loadContractDetails();"/></p>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">

    //dojo.require("dijit.form.Button");
    //dojo.query("listed").onclick(function(e){  });
    dojo.replaceClass("adminTab", "ibm-active");
    dojo.byId("adminSubTabs").style.display = 'block';
    dojo.replaceClass("profileAddContractTab", "ibm-active");

    function myOnClick(){
        if(dojo.byId("listed").checked){
            show('listedCustomer');
            hide('unListedCustomer');
        }else if(dojo.byId("unListed").checked){
            show('unListedCustomer');
            hide('listedCustomer');
        }
        //alert('Something clicked');
    };

    /* dojo.addOnLoad(
     function(){
     dojo.connect(dojo.byId("radioButtons"), "click", myOnClick);

     }); */

    function addContract(){
        // Local var representing if the form has been sent at all
        //var hasBeenSent = false;
        // Local var representing node to be updated
        dojo.byId('progress-bar').style.display='block';
        var contractListDiv = dojo.byId("addContractResponse");
        // Using dojo.xhrPost, as the amount of data sent could be large
        dojo.xhrPost({
            // The URL of the request
            url: "addNewContract.htm",
            form: dojo.byId("addContractForm"),
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



    function loadContractDetails(){
        alert('In loadContractDetails');

        var contractId = dojo.byId("contractId").value;
        alert(contractId);
        var URL = "<%=basePath%>/showContractDetails.htm?contract_id="+contractId;
        alert(URL);
        var addContractResponseDiv = dojo.byId("addContractResponseDiv");
        addContractResponseDiv.innerHTML = "";
        // The parameters to pass to xhrGet, the url, how to handle it, and the callbacks.
        var xhrArgs = {
            url: URL,
            handleAs: "text",
            preventCache: true,
            //   content: {
            //     contact_id: contractId
            //}
            //,
            load: function(data){
                alert('hello');
                alert(data);
                // Replace tabs with spacess.
                // data = data.replace(/\t/g, "first/last/only");
                dojo.place(data, addContractResponseDiv, "only");
                // console.log("userId = "+userId);
            },
            error: function(error){
                // modifyAclDiv.innerHTML = "An unexpected error occurred: " + error;
                alert('error occured');
            }
        }
        // Call the asynchronous xhrGet
        var deferred = dojo.xhrGet(xhrArgs);
        alert(deferred);
    }

</script>