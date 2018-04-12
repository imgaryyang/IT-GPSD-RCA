<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<style type="text/css" media="all">
    .bar-green-dark {
        background: #7a3;
        color: #fff;
        padding: .3em .5em;
        font-size: 1.1em;
        font-weight: bold;
    }

    .bar-green-med-dark {
        background: #9c3;
        color: #fff;
        padding: .3em .5em;
        font-size: 1.1em;
        font-weight: bold;
    }

    .bar-green-med-dark_cell {
        background: #9c3;
        color: #fff;
        margin: 2px 2px 2px 2px;
        padding: 2px 2px 2px 2px;
        font-size: 1.1em;
        font-weight: bold;
    }

    .bar-green-med-light {
        background: #bd6;
        color: #000;
        padding: .3em .5em;
        font-size: 1.1em;
        font-weight: bold;
    }

    .bar-blue-med-dark {
        background: #47b;
        color: #fff;
        padding: .3em .5em;
        font-size: 1.1em;
        font-weight: bold;
    }

    .bar-blue-med {
        background: #69c;
        color: #fff;
        padding: .3em .5em;
        font-size: 1.1em;
        font-weight: bold;
    }

    .bar-blue-med-light {
        background: #9be;
        color: #000;
        padding: .3em .5em;
        font-size: 1.1em;
        font-weight: bold;
    }

    .bar-gray-dark {
        background: #666;
        color: #fff;
        padding: .3em .5em;
        font-size: 1.1em;
        font-weight: bold;
    }

    .bar-gray-med-dark {
        background: #999;
        color: #fff;
        padding: .3em .5em;
        font-size: 1.1em;
        font-weight: bold;
    }

    .bar-gray-med-light {
        background: #ccc;
        color: #000;
        padding: .3em .5em;
        font-size: 1.1em;
        font-weight: bold;
    }

    .bar-gray-light {
        background: #ddd;
        color: #000;
        padding: .3em .5em;
        font-size: 1.1em;
        font-weight: bold;
    }

    .bar-blue-black {
        background: #001A2F;
        color: #fff;
        padding: .3em .5em;
        font-size: 1.1em;
        font-weight: bold;
    }

    .bar-green-black {
        background: #0F7D62;
        color: #fff;
        padding: .3em .5em;
        font-size: 1.1em;
        font-weight: bold;
    }

    .lr-mar-5px {
        margin-left: 5px;
        margin-right: 5px
    }

    .r-mar-5px {
        margin-right: 5px
    }

    .dot-bor-1px {
        border: 2px dotted #CCC
    }

    .sol-bor-1px {
        border: 1px solid #CCC
    }

    .contract-contact-info {
        width: 180px
    }

    .errors {
        color: #ff0000;
        font-style: italic;
    }

    #general_Information span {
        display: block;
        height: 25px;
    }

    #general_Information label {
        width: auto;
        width: 250px;
        font-weight: normal;
    }

    #general_Information tr {
        height: 25px;
    }

    .lr-mar-10px {
        margin-top: 7px;
        margin-left: 10px;
        margin-right: 10px
    }

    #bcrs span {
        display: block;
        height: 30px;
    }

    #bcrs label {
        width: auto;
        width: 550px;
        font-weight: normal;
    }

    #csst span {
        display: block;
        height: 30px;
    }

    #csst label {
        width: auto;
        width: 550px;
        font-weight: normal;
    }
</style>

<!-- <script src="js/CalendarPopup.js" type="text/javascript"></script> -->

<div id="ibm-pcon"><!-- CONTENT_BEGIN -->
    <div id="ibm-content"><!-- TITLE_BEGIN -->
        <!-- TITLE_END --> <!-- CONTENT_BODY -->
        <div id="ibm-content-body">
            <div id="ibm-content-main">
                <div class="ibm-columns">
                    <div class="ibm-col-6-6">
                        <div id="contractDetailContainer" class="ibm-container">
                            <form:form method="POST" commandName="userRoleForm" cssClass="ibm-column-form"
                                       name="addUserRole">
                            <div id="pii" style="display:none">
                                <p style="display:none">The fields indicated with an asterisk (*) are required to
                                    complete this transaction; other fields are optional. If you do not want to provide
                                    us with the required information, please use the "Back" button on your browser to
                                    return to the previous page, or close the window or browser session that is
                                    displaying this page.</p>
                            </div>
                            <form:hidden path="command" id="_command"/>
                            <!--<div id="ibm-leadspace-head" class="ibm-alternate">
                                <div id="ibm-leadspace-body">	-->
                            <div>
                                <br/>
                                <form:errors path="*" element="div" cssClass="errors"/>
																		
											<span id="requiredFieldExplaination">
												Required fields are marked with asterisk(<span
                                                    class="ibm-required">*</span>)and must be completed.
												<br/>
											</span>
                            </div>
                            <!--	</div>
                    </div> -->
                            <div id="body" class="ibm-container-body; sol-bor-1px">
                                <div id="clientInformationDiv">
                                    <div class="bar-blue-black">Add User Role</div>
                                </div>
                                <table class="ibm-results-table" summary="ACLs">
                                    <thead>

                                    <tr>
                                        <th scope="row">
                                            <div><p><span id="addUserRoleResponse" style="color: #ccc;"></span></p>
                                            </div>
                                        </th>
                                    </tr>
                                    </thead>
                                </table>
                                <table id="level" class="ibm-results-table" summary="ACLs">
                                    <thead>
                                    <tr>
                                        <th scope="row">
                                            <div id="nonContractDiv"><tiles:insertAttribute name="addUR"/></div>
                                        </th>
                                    </tr>
                                    <tr>
                                        <td>
                                            <table class="ibm-results-table" summary="ACLs" border="0">
                                                <thead>

                                                <tr>
                                                    <th scope="col" width="1100">
                                                        <div class="bar-blue-black">Modify User Roles</div>
                                                    </th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <tr>
                                                    <td scope="col">&nbsp;</td>
                                                </tr>
                                                <tr>
                                                    <td><form:select
                                                            path="email" id="userId">
                                                        <form:options items="${userWithRoles}" itemValue="userId"
                                                                      itemLabel="email"/>
                                                    </form:select>
                                                     <!--   &nbsp; &nbsp;
                                                        <form:select
                                                                path="email" id="userId">
                                                            <form:option value="">Please select the Account</form:option>
                                                            <form:options items="${userRoleForm.contracts}" itemValue="contractId"
                                                                          itemLabel="title"/>
                                                        </form:select>
                                                        -->
                                                        </td>
                                                </tr>
                                                <tr>
                                                    <td scope="col">&nbsp;</td>
                                                </tr>
                                                <tr>
                                                    <td scope="col">
                                                        <div class="ibm-buttons-row">
                                                            <input value="Load User Roles" id="loadAcl-submit-btn"
                                                                   type="button" name="loadAcl-submit-btn"
                                                                   class="ibm-btn-arrow-pri"
                                                                   onClick="loadUserRoleEntry();"/>
                                                    </td>

                            </div>
                            </tr>
                            <tr>
                                <td scope="col">
                                    <div id="modifyUserRoleDiv"></div>
                                </td>
                            </tr>

                            </tbody>
                            </table>
                            </td>
                            </tr>
                            </thead>
                            </table>


                            <table class="ibm-results-table" style="display: none;" id="access" summary="ACLs">
                                <thead>
                                <tr>
                                    <th scope="row">
                                        <div class="bar-blue-black">Remove BPDCHIP User Access Request</div>
                                    </th>
                                </tr>
                                <tr>
                                    <th scope="row">Note: The intranet ids entered will be permanently deleted from the
                                        BPDCHIP application.
                                    </th>
                                </tr>
                                <tr>
                                    <th scope="row">
                                        <table class="ibm-results-table" summary="ACLs">
                                            <thead>
                                            <tr>
                                                <td>*Users Intranet Ids:</td>
                                                <td style="padding-bottom: 5px"><form:input
                                                        path="userRole.gpsUser.email" id="gEmail"/></td>
                                            </tr>
                                            </thead>
                                        </table>
                                    </th>
                                </tr>

                                <tr>
                                    <th scope="row">
                                        <div class="bar-green-black">Business Justification</div>
                                        <table class="ibm-results-table" summary="ACLs">
                                            <thead>
                                            <tr>
                                                <th scope="col"><label for="gBusReason">*Business Reason:</label></th>
                                                <th scope="col" style="padding-bottom: 5px"><form:textarea
                                                        path="userRole.gpsUser.email" id="gBusReason" cols="63"
                                                        rows="3"/></th>
                                            </tr>
                                            </thead>
                                        </table>
                                    </th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>


<script type="text/javascript">

    dojo.replaceClass("adminTab", "ibm-active");
    dojo.byId("adminSubTabs").style.display = 'block';
    dojo.replaceClass("adminUserRoleTab", "ibm-active");


    function show(id) {
        dojo.byId(id).style.display = 'block';
    }

    function hide(id) {
        dojo.byId(id).style.display = 'none';
    }

    function loadUserRoleEntry() {
        console.log("loadUserRoleEntry()........");
        var modifyAclDiv = dojo.byId("modifyUserRoleDiv");
        modifyAclDiv.innerHTML = "";
        var URL = "<%=basePath%>modifyUserRole.htm";
        var userId = dojo.byId("userId").value;
        // The parameters to pass to xhrGet, the url, how to handle it, and the callbacks.
        var xhrArgs = {
            url: URL,
            handleAs: "text",
            preventCache: true,
            content: {
                user_id: userId
            },
            load: function (data) {
                // Replace tabs with spacess.
                // data = data.replace(/\t/g, "first/last/only");
                dojo.place(data, modifyAclDiv, "only");
                console.log("userId = " + userId);
            },
            error: function (error) {
                modifyAclDiv.innerHTML = "An unexpected error occurred: " + error;
            }
        }

        // Call the asynchronous xhrGet
        var deferred = dojo.xhrGet(xhrArgs);

    }

    function dropUserRoleEntry(urId) {
        console.log("deleteUserRoleEntry()........urId = " + urId);
        var URL = "<%=basePath%>dropUserRole.htm";
        // The parameters to pass to xhrGet, the url, how to handle it, and the callbacks.
        var xhrArgs = {
            url: URL,
            handleAs: "text",
            preventCache: true,
            content: {
                ur_id: urId
            },
            load: function (data) {
                console.log("got server response.... ");
                loadUserRoleEntry();
            },
            error: function (error) {
                modifyAclDiv.innerHTML = "An unexpected error occurred: " + error;
            }
        }

        // Call the asynchronous xhrGet
        var deferred = dojo.xhrGet(xhrArgs);
        return false;

    }


    function addSingleAclEntry() {
        // Local var representing if the form has been sent at all
        // Local var representing node to be updated
        var contractListDiv = dojo.byId("addAclResponse");
        var sAclId = dojo.byId("sAclId").value;
        var sEmail = dojo.byId("sEmail").value;
        var sFormType = dojo.byId("sFormType").value;
        var sContractId = dojo.byId("sContractId").value;
        var sActiveLevel = dojo.byId("sActiveLevel").value;
        var sAwaitingLevel = dojo.byId("sAwaitingLevel").value;
        var sApprovedLevel = dojo.byId("sApprovedLevel").value;

        var URL = "<%=basePath%>addSingleAcl.htm";

        contractListDiv.innerHTML = "";
        // Using dojo.xhrPost, as the amount of data sent could be large
        dojo.xhrGet({
            // The URL of the request
            url: URL,
            handleAs: "text",
            preventCache: true,
            content: {
                acl_id: sAclId,
                email: sEmail,
                formType: sFormType,
                contractId: sContractId,
                activeLevel: sActiveLevel,
                awaitingLevel: sAwaitingLevel,
                approvedLevel: sApprovedLevel
            },
            // The success handler
            load: function (response) {
                //alert(response);
                contractListDiv.innerHTML = response;
            },
            // The error handler
            error: function () {
                contractListDiv.innerHTML = "An error occured, please try again."
                return false;
            },
            // The complete handler
            handle: function () {
                // hasBeenSent = true;
            }
        });

    }


    function addUserRoleEntry() {
        var contractListDiv = dojo.byId("addUserRoleResponse");
        var urId = dojo.byId("urId").value;
        var uEmail = dojo.byId("uEmail").value;
        var uRole = dojo.byId("uRole").value;
        var uContractId = dojo.byId("uContractId").value;

        console.log("addUserRole()........urId = " + urId);

        var URL = "<%=basePath%>addSingleRole.htm";

        contractListDiv.innerHTML = "";
        // Using dojo.xhrPost, as the amount of data sent could be large
        dojo.xhrGet({
            // The URL of the request
            url: URL,
            handleAs: "text",
            preventCache: true,
            content: {
                ur_id: urId,
                email: uEmail,
                role: uRole,
                contract_id: uContractId
            },
            // The success handler
            load: function (response) {
                //alert(response);
                contractListDiv.innerHTML = response;
            },
            // The error handler
            error: function () {
                contractListDiv.innerHTML = "An error occured, please try again."
                return false;
            },
            // The complete handler
            handle: function () {
                // hasBeenSent = true;
            }
        });

    }

    function addACLEntry() {
        // Local var representing if the form has been sent at all
        // Local var representing node to be updated
        var contractListDiv = dojo.byId("addAclResponse");
        contractListDiv.innerHTML = "";
        // Using dojo.xhrPost, as the amount of data sent could be large
        dojo.xhrPost({
            // The URL of the request
            //url: "validateUser.htm",
            form: dojo.byId("aCLForm"),
            // The success handler
            load: function (response) {
                //alert(response);
                contractListDiv.innerHTML = response;
            },
            // The error handler
            error: function () {
                contractListDiv.innerHTML = "An error occured, please try again."
                return false;
            },
            // The complete handler
            handle: function () {
                // hasBeenSent = true;
            }
        });

    }

    function editACLEntry(aclId, contractId) {
        // Local var representing if the form has been sent at all
        // Local var representing node to be updated
        console.log("editACLEntry()........aclId = " + aclId);

        var singleContractDiv = dojo.byId("singleContractDiv");
        var nonContractDiv = dojo.byId("nonContractDiv");
        var modifyAclDiv = dojo.byId("modifyAclDiv");
        var toUpdate = singleContractDiv;
        var toShow = "contract";
        if (contractId == null || contractId == '') {
            toUpdate = nonContractDiv;
            toShow = "level";

        }
        console.log("toUpdate = " + toUpdate.id);
        var URL = "<%=basePath%>/editAcl.htm";
        // The parameters to pass to xhrGet, the url, how to handle it, and the callbacks.
        var xhrArgs = {
            url: URL,
            handleAs: "text",
            preventCache: true,
            content: {
                acl_id: aclId
            },
            load: function (data) {
                // Replace tabs with spacess.
                // data = data.replace(/\t/g, "first/last/only");
                dojo.place(data, toUpdate, "only");
                console.log("got server response.... ");
                modifyAclDiv.innerHTML = "";
                if (toShow == 'contract') {
                    show('contract');
                    hide('level');
                    dojo.byId('CON').checked = true;
                } else {
                    show('level');
                    hide('contract');
                    dojo.byId('level_').checked = true;
                }
            },
            error: function (error) {
                modifyAclDiv.innerHTML = "An unexpected error occurred: " + error;
            }
        }

        // Call the asynchronous xhrGet
        var deferred = dojo.xhrGet(xhrArgs);
        return false;

    }

    function levelChecked(id) {
        console.log(id);
        dojo.byId("addAclResponse").innerHTML = "";
        var isManOr = document.getElementById('level_');
        dojo.byId("_command").value = 'Add-Group-ACL';
        if (isManOr.checked) {
            dojo.byId(id).style.display = 'block';
        }
        else {
            dojo.byId(id).style.display = 'none';
        }
    }

    function cont(id) {
        var isManOr = document.getElementById('CON');
        dojo.byId("addAclResponse").innerHTML = "";
        dojo.byId("_command").value = 'Add-Single-ACL';
        if (isManOr.checked) {
            dojo.byId(id).style.display = 'block';
        }
        else {
            dojo.byId(id).style.display = 'none';
        }
    }
    function groupChecked(id) {
        console.log(id);
        dojo.byId("addAclResponse").innerHTML = "";
        var isManOr = document.getElementById('group_');
        dojo.byId("_command").value = 'Add-User-Group';
        if (isManOr.checked) {
            dojo.byId(id).style.display = 'block';
        }
        else {
            dojo.byId(id).style.display = 'none';
        }
    }


    function access(id) {
        dojo.byId("addAclResponse").innerHTML = "";
        var isManOr = document.getElementById('Remove');
        dojo.byId("_command").value = 'remove-ACL';
        if (isManOr.checked) {
            dojo.byId(id).style.display = 'block';
        }
        else {
            dojo.byId(id).style.display = 'none';
        }
    }

    function country(id) {
        var isManOr = document.getElementById('c');
        if (isManOr.checked) {
            dojo.byId(id).style.display = 'block';
        }
        else {
            dojo.byId(id).style.display = 'none';
        }
    }
    function imt(id) {
        var isManOr = document.getElementById('i');
        if (isManOr.checked) {
            dojo.byId(id).style.display = 'block';
        }
        else {
            dojo.byId(id).style.display = 'none';
        }
    }
    function iot(id) {
        var isManOr = document.getElementById('o');
        if (isManOr.checked) {
            dojo.byId(id).style.display = 'block';
        }
        else {
            dojo.byId(id).style.display = 'none';
        }
    }
    function sector(id) {
        var isManOr = document.getElementById('s');
        if (isManOr.checked) {
            dojo.byId(id).style.display = 'block';
        }
        else {
            dojo.byId(id).style.display = 'none';
        }
    }
    function customer(id) {
        var isManOr = document.getElementById('cu');
        if (isManOr.checked) {
            dojo.byId(id).style.display = 'block';
        }
        else {
            dojo.byId(id).style.display = 'none';
        }
    }

    function hide(id) {
        dojo.byId(id).style.display = 'none';
    }

    function moveValues(srcId, tgtId) {
        var src = dojo.byId(srcId);
        var tgt = dojo.byId(tgtId);
        var options = src.options;
        var i = 0;
        while (i < options.length) {
            if (options[i].selected) {
                tgt.options[tgt.options.length] = new Option(options[i].text, options[i].value, options[i].defaultSelected, options[i].selected);
                options[i] = null;
            } else {
                i++;
            }
        }
    }
</script>		