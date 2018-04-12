function hide(id) {
    try {
        dojo.byId(id).style.display = 'none';
    } catch (err) {
        console.debug("Error hide(" + id + ") => " + err.message);
    }
}

function show(id) {
    try {
        dojo.byId(id).style.display = 'block';
    } catch (err) {
        console.debug("Error show(" + id + ") => " + err.message);
    }
}


function showOrHide(source, dest) {
    try {
        if (dojo.byId(source).checked) {
            show(dest);
        } else {
            hide(dest);
        }
    } catch (err) {
        console.debug("Error show(" + id + ") => " + err.message);
    }
}

function initiaePagination() {
    var _page = dojo.byId("page");
    var _pagingAction = dojo.byId("pagingAction");
    _page.value = 1;
    _pagingAction.value = "NEW-SEARCH";
}

/////////////////////////////// contract listing////////////////////////////////////////////////

function getRCAs() {
    // Local var representing if the form has been sent at all
    //var hasBeenSent = false;
    // Local var representing node to be updated
    var contractListDiv = dojo.byId("contractList");
    contractListDiv.innerHTML = "Please wait... <img alt=\"Loading RCAs...\" src=\"images/w3/loader.gif \">";
    // Using dojo.xhrPost, as the amount of data sent could be large
    dojo.xhrPost({
        // The URL of the request
        //url: "contractList.htm",
        form: dojo.byId("searchFilterForm"),
        // The success handler
        load: function (response) {
            contractListDiv.innerHTML = response;
        },
        // The error handler
        error: function () {
            contractListDiv.innerHTML = "Your search could not be performed, please try again."
        },
        // The complete handler
        handle: function () {
            // hasBeenSent = true;
        }
    });
}

/*
 function resetRCAFilter(basePath) {
 console.log("resetRCAFilter()........");

 var URL = basePath + "resetRcaFitler.htm";
 var month = dojo.byId("month").value;
 var year =  dojo.byId("year").value;
 console.log("url :" + url);
 console.log("month :" + month);

 // The parameters to pass to xhrGet, the url, how to handle it, and the callbacks.
 var xhrArgs = {
 url: URL,
 handleAs: "text",
 preventCache: true,
 content: {
 month: month,
 year: year
 },
 load: function (data) {
 //dojo.place(data, modifyAclDiv, "only");
 console.log("resetRCAFilter()....success");
 },
 error: function (error) {
 console.log("resetRCAFilter().....failure: " + error);
 }
 }

 // Call the asynchronous xhrGet
 var deferred = dojo.xhrGet(xhrArgs);
 }

 */
function resetRCAFilter(basePath) {
    console.log("resetRCAFilter()........");
    var month = dojo.byId("month").value;
    var year =  dojo.byId("year").value;
    var URL = basePath + "resetRcaFitler.htm?month="+month+"&year="+year;
    window.open(URL,"_self");
}





function clearFilters() {
    document.getElementById('contract').value = "";
    document.getElementById('rcaOrActionNumber').value = "";
    document.getElementById('stage').value = "";
    document.getElementById('rcaCoordinator').value = "";
    document.getElementById('rcaOwner').value = "";
    document.getElementById('rcaDelegate').value = "";
    document.getElementById('formType').value = "";
    document.getElementById('primaryTicket').value = "";
    getRCAs();
}


function getNextPage() {
    var _page = dojo.byId("page");
    var _pagingAction = dojo.byId("pagingAction");
    _page.value = parseInt(_page.value) + 1;
    _pagingAction.value = "PAGINATE";
    //dojo.byId("displayMsg").innerHTML = "page = "+_page.value;
    getRCAs();
    return false;
}

function getPreviousPage() {
    var _page = dojo.byId("page");
    var _pagingAction = dojo.byId("pagingAction");
    _page.value = parseInt(_page.value) - 1;
    _pagingAction.value = "PAGINATE";
    if (parseInt(_page.value) < 1) {
        _page.value = 1;
    }
    //dojo.byId("displayMsg").innerHTML = "page = "+dojo.byId("page").value;
    getRCAs();
    return false;
}

/////////////////////////////// contract listing end////////////////////////////////////////////////


function setFormAction(_formAction) {
    dojo.byId("formAction").value = _formAction;
    // set file description
    var description = dojo.byId('description').value;
    dojo.query('#fileDescription').val(description);
    $("#rcaForm").submit();
    return true;
}


function validateForm() {
    var GTLemail = document.forms["editForm"]["GTLemail"].value;
    var GDLemail = document.forms["editForm"]["GDLemail"].value;
    var PEemail = document.forms["editForm"]["PEemail"].value;
    var DPEemail = document.forms["editForm"]["DPEemail"].value;
    var message = "Please enter the following fields : \n";
    var allOk = 0;


    /*if (GTLemail==null || GTLemail=="" || GDLemail==null || GDLemail=="" || PEemail==null || PEemail=="" || DPEemail==null || DPEemail=="")
     {
     alert("Please enter all mandatory fields");
     return false;
     }*/

    if (GTLemail == null || GTLemail == "") {
        message = message + "Global Tower Lead email \n";
        allOk = 1;
    }

    if (GDLemail == null || GDLemail == "") {
        message = message + "Global Delivery Leader email \n";
        allOk = 1;
    }

    if (PEemail == null || PEemail == "") {
        message = message + "Project Executive email \n";
        allOk = 1;
    }

    if (DPEemail == null || DPEemail == "") {
        message = message + "Delivery Project Executive email \n";
        allOk = 1;
    }

    if (allOk == 1) {
        alert(message);
        return false;
    }
}


function clearForm(oForm) {
    var elements = oForm.elements;
    for (i = 0; i < elements.length; i++) {
        field_type = elements[i].type.toLowerCase();

        switch (field_type) {

            case "text":
            case "textarea":
                elements[i].value = "";
                break;

            case "radio":
            case "checkbox":
                if (elements[i].checked) {
                    elements[i].checked = false;
                }
                break;

            case "select-one":
            case "select-multi":
                elements[i].selectedIndex = -1;
                break;

            default:
                break;
        }
    }
}

function resetContract() {
    var r = confirm("Are you sure you want to reset the form? Resetting the form will clear all the data currently entered");
    if (r == true) {
        setFormAction('reset');
        resetIndustry();
        resetForm();
        document.forms["editForm"].submit();
    }
    else {
    }
}


function resetForm() {
    var elements = document.getElementsByTagName("input");
    for (var i = 0; i < elements.length; i++) {
        if (elements[i].type == "text") {
            elements[i].value = "";
        }
    }

    document.getElementById('customer.name').value = "";
    document.getElementById("towers").checked = false;
    document.getElementById("contractHealth").value = "";
    document.getElementById("reportingInclude").value = "";
    document.getElementById("reportingExclude").value = "";
    document.getElementById("contractIndicator").value = "";
}


//*********************************   RCA Form  *********************************//

// submit button
function hideSubmitButton() {
    $("#submit-span").hide();
    $("#ibm-submit").hide();
}

function showSubmitButton() {
    $("#submit-span").show();
    $("#ibm-submit").show();
}

//save button
function hideSaveButton() {
    $("#save-span").hide();
    $("#ibm-save").hide();
}
function showSaveButton() {
    $("#save-span").show();
    $("#ibm-save").show();
}

//approve button
function hideApproveButton() {
    $("#approve-span").hide();
    $("#ibm-approve").hide();
}
function showApproveButton() {
    $("#approve-span").show();
    $("#ibm-approve").show();
}

//reject button
function hideRejectButton() {
    $("#reject-span").hide();
    $("#ibm-reject").hide();
}
function showRejectButton() {
    $("#reject-span").show();
    $("#ibm-reject").show();
}

// cancel button
function hideCancelButton() {
    $("#cancel-span").hide();
    $("#ibm-cancel").hide();
}
function showCancelButton() {
    $("#cancel-span").show();
    $("#ibm-cancel").show();
}

// close button
function hideCloseButton() {
    $("#close-span").hide();
    $("#ibm-close").hide();
}
function showCloseButton() {
    $("#close-span").show();
    $("#ibm-close").show();
}

// Reopen button
function hideReopenButton() {
    $("#reopen-span").hide();
    $("#ibm-reopen").hide();
}
function showReopenButton() {
    $("#reopen-span").show();
    $("#ibm-reopen").show();
}


function enableOrDisableRCAFormButtons() {
    console.log("calling enableOrDisableRCAFormButtons()....");

    var rcaStage = dojo.byId('rcaStage').value;
    var isDpeApprovalRequestSent = dojo.byId('isDpeApprovalRequestSent').value;
    console.log('isDpeApprovalRequestSent : '+ isDpeApprovalRequestSent);

    if (rcaStage == "Awaiting") {
        console.log('rca is in awaiting state');
        // make due date uneditable except for coordinator and dpe
        if(!isRcaCoordinator() && !isRcaDpe()){
            $('#dueDate').attr('disabled', 'disabled');
            $('#dueDate').css('background-color', '#ffffff');
        }

        if (isRcaCoordinator() || isGlobalCoordinator() || isAdmin()) {
            $('#coordAprDiv').show();
        }

        if ((isRcaDpe() || isAdmin() || isRcaCoordinator()) && !(isEditor() || isGlobalEditor())) {
            console.log('user is dpe or coordinator');
            if(isRcaCoordinator() == true){
                hideApproveButton();
            }else {
                if(isDpeApprovalRequestSent == 'Y'){
                    showApproveButton();
                }else {
                    hideApproveButton();
                }
            }
            if(isDpeApprovalRequestSent == 'Y') {
                showRejectButton();
            }else{
                hideRejectButton();
            }
        } else {
            hideApproveButton();
            hideRejectButton();
        }
        hideSubmitButton();
        hideCloseButton();
        hideReopenButton();
        if (!isRcaCoordinator() && !isGlobalCoordinator() && !isAdmin()) {
            hideCancelButton();
        }
        if (isDpeApprovalRequestSent != 'Y' && (isRcaCoordinator() || isGlobalCoordinator() || isAdmin())) {
            showSubmitButton();
        }

    }
    if (rcaStage == "Approved") {
        if(!isRcaCoordinator() && !isRcaDpe()){
            $('#dueDate').attr('disabled', 'disabled');
            $('#dueDate').css('background-color', '#ffffff');
        }
        hideApproveButton();
        hideSubmitButton();
        hideReopenButton();
        hideRejectButton();

        if (isAdmin()) {
            showRejectButton();
        }
        if (!isRcaCoordinator() && !isGlobalCoordinator() && !isAdmin()) {
            hideSaveButton();
            hideCancelButton();
            hideCloseButton();
        }
    }
    if (rcaStage == "Open" || rcaStage == "Active") {
        // due date readonly
        $('#dueDate').attr('disabled', 'disabled');
        $('#dueDate').css('background-color', '#ffffff');


        hideApproveButton();
        hideReopenButton();
        console.log('rca creator: ' + isRcaCreator());


        if (isAdmin() || isRcaCoordinator() || isRcaDpe() || isRcaDelegate() || isRcaOwner()
            || isGlobalCoordinator() || isGlobalDelegate() || isGlobalDpe() || isGlobalOwner()) {
            console.log('show submit');
            showSubmitButton();
        }
        else {
            hideSubmitButton();
        }
        hideRejectButton();
        hideCloseButton();
        if (!isRcaCoordinator() && !isGlobalCoordinator() && !isAdmin()) {
            hideCancelButton();
        }
        if (isRcaReader() || isGlobalReader()) {
            hideSaveButton();
        } else {
            showSaveButton();
        }
        if (rcaStage == "Open") {
            console.log('rca is in open state');
            hideSubmitButton();
        }

    }
    if (rcaStage == "Cancelled" || rcaStage == "Closed") {
        console.log("disabling cancelled or closed rca form")
        $('#contractDetailContainer').find('button, select').attr('disabled', 'disabled');
        $('#contractDetailContainer').find('input, textarea').attr('readonly', 'true');
        $('#contributingCauseDiv').find('button, select').attr('disabled', 'disabled');
        $('#contributingCauseDiv').find('input, textarea').attr('readonly', 'true');
        $('#actionItemsDiv').find('button, select').attr('disabled', 'disabled');
        $('#actionItemsDiv').find('input, textarea').attr('readonly', 'true');
        $('#incident_duration_tab').find('input, textarea').attr('disabled', 'disabled');


        $('#dueDate').attr('disabled', 'disabled');
        $('#dueDate').css('background-color', '#ffffff');
        $('#incidentStartTime').attr('disabled', 'disabled');
        $('#incidentStartTime').css('background-color', '#ffffff');
        $('#incidentEntTime').attr('disabled', 'disabled');
        $('#incidentEntTime').css('background-color', '#ffffff');


        $("#isOwnerAcceptedBox").prop("disabled", true);
        $("#isDelegateAcceptedBox").prop("disabled", true);

        hideApproveButton();
        hideSubmitButton();
        hideRejectButton();
        hideCancelButton();
        hideSaveButton();
        hideCloseButton();
        hideReopenButton();

        if (rcaStage == "Closed") {
            if (isRcaCoordinator() || isGlobalCoordinator() || isAdmin()) {
                showReopenButton();
            }
        }

    }


} // end enableOrDisableRCAFormButtons()

function enableOrDisableActionFormButtons() {
    console.log("calling enableOrDisableActionFormButtons()....");

    if (isRcaReader() || isGlobalReader()) {
        $("#ibm-save").hide();
        $("#ibm-close").hide();

    }
    else if (isEditor() || isGlobalEditor()) {
        $("#ibm-close").hide();
    }
    else {
        $("#ibm-save").show();
        $("#ibm-close").show();
    }


} // end enableOrDisableActionFormButtons()


function getOutageSubCategories2(basePath, outageCategoryId, outageSubCategoryId) {
    console.log("getOutageSubCategories()........");

    var outageCategory = dojo.byId(outageCategoryId).value;
    var outSubCategories = dojo.byId(outageSubCategoryId);

    // remove all existing options if exists
    while (outSubCategories.firstChild) outSubCategories.removeChild(outSubCategories.firstChild);

    // adding default option
    dojo.create("option", {value: '', innerHTML: 'Please select the option'}, outSubCategories);

    var URL = basePath + "outageSubCategories2.htm";
    dojo.xhrGet({
        // The URL of the request
        url: URL,
        handleAs: "text",
        preventCache: true,
        content: {
            outage_category: outageCategory
        },
        // The success handler
        load: function (response) {
            if (response != null) {
                var data = $.parseJSON(response);
                if (data != null && data.length > 0) {
                    for (var i = 0; i < data.length; i++) {
                        dojo.create("option", {
                            value: data[i].serviceDescriptionName,
                            innerHTML: data[i].serviceDescriptionName
                        }, outSubCategories);
                    }
                }
                return true;
            }
            else {
                return false;
            }
        },
        // The error handler
        error: function () {
            return false;
        },
        // The complete handler
        handle: function () {
            // hasBeenSent = true;
        }
    });
}



/*function checkAccessLevelForRcaForm(){
 console.log("calling checkAccessLevel for rca form....")
 var accessLevel = $("#accessLevel").val();
 console.log("acessLevel: " + accessLevel);
 if(accessLevel == '1'){
 console.log("acessLevel: " + accessLevel);
 $('#headerDiv').find('input, textarea').attr('readonly','true');
 $('#contactsDiv').find('input, textarea').attr('readonly','true');
 $('#descriptionDiv').find('input, textarea').attr('readonly','true');
 $('#descriptionDiv').find('select, radio').attr('disabled','disabled');
 $('#eventDetailsDiv').find('input, textarea').attr('readonly','true');
 $('#eventDetailsDiv').find('select, radio').attr('disabled','disabled');
 $('#approvalsDiv').find('input, textarea').attr('readonly','true');
 $("#ibm-close-with-open-actions").hide();
 $("#ibm-approve").hide();
 $("#ibm-submit").hide();
 $("#ibm-save").hide();
 $("#ibm-cancel").hide();
 $("#ibm-add-action").prop("disabled", true);
 $("#ibm-close").hide();
 $("#ibm-reject").hide();
 }

 if(accessLevel == '0' ){
 console.log("acessLevel: " + accessLevel);
 $('#headerDiv').find('input, textarea').attr('readonly','true');
 $('#contactsDiv').find('input, textarea').attr('readonly','true');
 $('#descriptionDiv').find('input, textarea').attr('readonly','true');
 $('#descriptionDiv').find('select, radio').attr('disabled','disabled');
 $('#eventDetailsDiv').find('input, textarea').attr('readonly','true');
 $('#eventDetailsDiv').find('select, radio').attr('disabled','disabled');
 $('#approvalsDiv').find('input, textarea').attr('readonly','true');
 $("#ibm-close-with-open-actions").hide();
 $("#ibm-approve").hide();
 $("#ibm-submit").hide();
 $("#ibm-save").hide();
 $("#ibm-cancel").hide();
 $("#ibm-add-action").prop("disabled", true);
 $("#ibm-close").hide();
 $("#ibm-reject").hide();
 }

 if(accessLevel == '2'){
 console.log(" save acessLevel: " + accessLevel);
 $("#ibm-submit").hide();
 $("#ibm-approve").hide();
 $("#ibm-cancel").hide();
 $("#ibm-close").hide();
 $("#ibm-reject").hide();
 $("#ibm-close-with-open-actions").hide();
 }
 if(accessLevel == '4' || accessLevel == '8' || accessLevel == '16' || accessLevel =='32'){
 console.log("submit access Level: " + accessLevel);
 var userRoles = $("#userRoles").val();
 if( !isRcaCoordinator() && !isRcaDelegate()
 && !isRcaDpe() && !isRcaOwner()
 && !isGlobalCoordinator() && !isGlobalDelegate()
 && !isGlobalDpe && !isGlobalOwner()){
 $("#ibm-submit").hide();
 $("#ibm-approve").hide();
 $("#ibm-cancel").hide();
 $("#ibm-close").hide();
 $("#ibm-reject").hide();
 $("#ibm-close-with-open-actions").hide();
 $("#ibm-cancel").hide();
 }
 }

 }// checkAccessLevel
 */

function showRcaApproveBox() {
    console.log("showRcaApproveBox.....");
    ibmweb.overlay.show('approve-rca-box');
}

function showRejectRcaBox() {
    console.log("showRejectRcaBox.....");
    ibmweb.overlay.show('reject-rca-box');
}

function showCancelRcaBox() {
    console.log("showCancelRcaBox.....");
    ibmweb.overlay.show('cancel-rca-box');
}

function showReopenBox() {
    console.log("showReopenBox.....");
    ibmweb.overlay.show('reopen-rca-box');
}

function approveRcaForm() {
    console.log('calling approveRcaForm....');
    var comment = dojo.byId('approvalComments').value;
    console.log('approval comments :' + comment);
    dojo.query('#approvedComments').val(comment);
    setFormAction('approve');
    $("#rcaForm").submit();
}


function rejectRcaForm() {
    console.log('calling rejectRcaForm....');
    var comment = dojo.byId('rejectionComments').value;
    console.log('rejected comments :' + comment);
    dojo.query('#rejectedComments').val(comment);
    setFormAction('reject');
    $("#rcaForm").submit();
}
function cancelRcaForm() {
    console.log('calling cancelRcaForm....');
    var comment = dojo.byId('cancellationComments').value;
    console.log('cancellation :' + comment);
    dojo.query('#cancelledComments').val(comment);
    setFormAction('cancel');
    $("#rcaForm").submit();
}
function reopenRcaForm() {
    console.log('calling reopenRcaForm....');
    var comment = dojo.byId('reopenComments').value;
    console.log('reopen Comments :' + comment);
    dojo.query('#reopenedComments').val(comment);
    setFormAction('reopen');
    $("#rcaForm").submit();
}


function setFormAction(_formAction) {
    dojo.byId("formAction").value = _formAction;
    $("#rcaForm").submit();
}


function showOrHideHistoryLog() {
    if ($("#historyLogCheckBox").is(':checked')) {
        $('#historyLogDiv').show();
    }
    else {
        $('#historyLogDiv').hide();
    }
}

function setOwnerAccepted() {
    if ($("#isOwnerAcceptedBox").is(':checked')) {
        $("#isOwnerAccepted").val("Y");
    }
    else {
        $("#isOwnerAccepted").val("N");
    }
}

function setDelegateAccepted() {
    if ($("#isDelegateAcceptedBox").is(':checked')) {
        $("#isDelegateAccepted").val("Y");
    }
    else {
        $("#isDelegateAccepted").val("N");
    }
}

function setHasCoordinatorApproved() {
    if ($("#hasCoordinatorApprovedBox").is(':checked')) {
        $("#hasCoordinatorApproved").val("Y");
    }
    else {
        $("#hasCoordinatorApproved").val("N");
    }
}


function loadRcaHistoryLog(basePath) {
    console.log("loadRcaHistoryLog()........");
    var modifyAclDiv = dojo.byId("historyLogDiv");
    modifyAclDiv.innerHTML = "";
    var URL = basePath + "loadRcaHistoryLog.htm";
    var rcaId = dojo.byId("rcaId").value;

    // The parameters to pass to xhrGet, the url, how to handle it, and the callbacks.
    var xhrArgs = {
        url: URL,
        handleAs: "text",
        preventCache: true,
        content: {
            rca_id: rcaId
        },
        load: function (data) {
            dojo.place(data, modifyAclDiv, "only");
            console.log("success");
        },
        error: function (error) {
            console.log("failure: " + error);
        }
    }

    // Call the asynchronous xhrGet
    var deferred = dojo.xhrGet(xhrArgs);
}

function setOwnerAndDelegateCheckBox() {
    console.log("calling setOwnerAndDelegateCheckBox...");
    // setting owner checkbox
    var isOnwerAccepted = dojo.byId("isOwnerAccepted").value;
    console.log('isOnwerAccepted : ' + isOnwerAccepted);
    if (isOnwerAccepted.trim() == "N") {
        $("#isOwnerAcceptedBox").prop("checked", false);
    }
    else {
        $("#isOwnerAcceptedBox").prop("checked", true);
    }

    // set the delegate checkbox
    var isDelegateAccepted = dojo.byId("isDelegateAccepted").value;
    console.log('isDelegateAccepted : ' + isDelegateAccepted);
    if (isDelegateAccepted.trim() == "N") {
        $("#isDelegateAcceptedBox").prop("checked", false);
    }
    else {
        console.log('setting delegate accepted true')
        $("#isDelegateAcceptedBox").prop("checked", true);
    }

    //enable or disable owner checkbox
    var isRcaOwnerLoggedIn = dojo.byId("isRcaOwnerLoggedIn").value
    if (isRcaOwnerLoggedIn == "true") {
        $("#isOwnerAcceptedBox").prop("disabled", false);
    }
    else if (isRcaOwnerLoggedIn == "false") {
        $("#isOwnerAcceptedBox").prop("disabled", true);
    }

    //enable or disable delegate checkbox
    var isRcaDelegateLoggedIn = dojo.byId("isRcaDelegateLoggedIn").value
    if (isRcaDelegateLoggedIn == "true") {
        $("#isDelegateAcceptedBox").prop("disabled", false);
    }
    else if (isRcaDelegateLoggedIn == "false") {
        console.log("isRcaDelegateLoggedIn disabling");
        $("#isDelegateAcceptedBox").prop("disabled", true);
    }
}


function loadRcaTickets(basePath) {
    console.log("loadRcaTickets()........");
    var modifyAclDiv = dojo.byId("rcaTicketsDiv");
    modifyAclDiv.innerHTML = "Loading Secondary Tickets... <img alt=\"Loading Secondary Tickets...\" src=\"images/w3/loader.gif \">";
    var URL = basePath + "loadRCATickets.htm";
    var rcaId = dojo.byId("rcaId").value;

    // The parameters to pass to xhrGet, the url, how to handle it, and the callbacks.
    var xhrArgs = {
        url: URL,
        handleAs: "text",
        preventCache: true,
        content: {
            rca_id: rcaId
        },
        load: function (data) {
            //  alert(data);
            dojo.place(data, modifyAclDiv, "only");
            console.log("success");
        },
        error: function (error) {
            console.log("failure: " + error);
            //modifyAclDiv.innerHTML = "An unexpected error occurred: " + error;
        }
    }

    // Call the asynchronous xhrGet
    var deferred = dojo.xhrGet(xhrArgs);
}


function isRcaCoordinator() {
    var userRoles = $("#userRoles").val();
    if (userRoles != null && userRoles != '') {
        if (userRoles.indexOf('coordinator') > -1) {
            console.log(' user is rca coordinator');
            return true;
        }
    }
    return false;
}

function isRcaDpe() {
    var userRoles = $("#userRoles").val();
    if (userRoles.indexOf('dpe') > -1) {
        console.log(' user is rca dpe');
        return true;
    }
    return false;
}

function isRcaDelegate() {
    var userRoles = $("#userRoles").val();
    if (userRoles.indexOf('delegate') > -1) {
        console.log(' user is rca delegate');
        return true;
    }
    return false;
}

function isRcaOwner() {
    var userRoles = $("#userRoles").val();
    if (userRoles.indexOf('owner') > -1) {
        console.log(' user is rca owner');
        return true;
    }
    return false;
}

function isGlobalCoordinator() {
    var userRoles = $("#userRoles").val();
    if (userRoles.indexOf('globalCoordinator') > -1) {
        console.log(' user is global coordinator');
        return true;
    }
    return false;
}

function isGlobalOwner() {
    var userRoles = $("#userRoles").val();
    if (userRoles.indexOf('globalOwner') > -1) {
        console.log(' user is global owner');
        return true;
    }
    return false;
}

function isGlobalDpe() {
    var userRoles = $("#userRoles").val();
    if (userRoles.indexOf('globalDpe') > -1) {
        console.log(' user is global dpe');
        return true;
    }
    return false;
}

function isGlobalDelegate() {
    var userRoles = $("#userRoles").val();
    if (userRoles.indexOf('globalDelegate') > -1) {
        console.log(' user is global delegate');
        return true;
    }
    return false;
}


function isGlobalEditor() {
    var userRoles = $("#userRoles").val();
    if (userRoles.indexOf('globalEditor') > -1) {
        console.log(' user is global editor');
        return true;
    }
    return false;
}

function isRcaCreator() {
    var userRoles = $("#userRoles").val();
    if (userRoles.indexOf('creator') > -1 && !isRcaCoordinator()
        && !isRcaOwner() && !isRcaDelegate() && !isRcaDpe()
        && !isGlobalCoordinator() && !isGlobalDelegate()
        && !isGlobalDpe() && !isGlobalOwner()) {
        console.log(' user is RCA creator');
        return true;
    }
    return false;
}

function isRcaReader() {
    console.log('isRcaReader()...')
    var userRoles = $("#userRoles").val();
    console.log('use roles:' + userRoles);
    if (userRoles.indexOf('reader') > -1 && !isRcaCoordinator()
        && !isRcaOwner() && !isRcaDelegate() && !isRcaDpe() && !isRcaCreator()
        && !isGlobalCoordinator() && !isGlobalDelegate()
        && !isGlobalDpe() && !isGlobalOwner() && !isGlobalReader()) {
        console.log(' user is RCA creator');
        return true;
    }
    return false;
}

function isGlobalReader() {
    var userRoles = $("#userRoles").val();
    if (userRoles.indexOf('globalReader') > -1 && !isRcaReader() && !isRcaCoordinator()
        && !isRcaOwner() && !isRcaDelegate() && !isRcaDpe() && !isRcaCreator()
        && !isGlobalCoordinator() && !isGlobalDelegate()
        && !isGlobalDpe() && !isGlobalOwner()) {
        console.log(' user is RCA creator');
        return true;
    }
    return false;
}

function isGlobalCreator() {
    var userRoles = $("#userRoles").val();
    if (userRoles.indexOf('globalCreator') > -1 && !isRcaReader() && !isRcaCoordinator()
        && !isRcaOwner() && !isRcaDelegate() && !isRcaDpe() && !isRcaCreator()
        && !isGlobalCoordinator() && !isGlobalDelegate()
        && !isGlobalDpe() && !isGlobalOwner()) {
        console.log(' user is RCA creator');
        return true;
    }
    return false;
}


function isAdmin() {
    var userRoles = $("#userRoles").val();
    if (userRoles.indexOf('admin') > -1) {
        return true;
    }
    return false;
}

function isEditor() {
    var userRoles = $("#userRoles").val();
    if (userRoles.indexOf('editor') > -1 && !isRcaReader() && !isGlobalReader() && !isRcaCoordinator() && !isGlobalCoordinator() && !isRcaOwner() && !isGlobalOwner()
        && !isRcaDelegate() && !isGlobalDelegate() && !isRcaDpe() && !isGlobalDpe()
        && !isRcaCreator() && !isGlobalCreator() && !isGlobalEditor()) {
        console.log(' user is editor');
        return true;
    }
    return false;
}


function loadRCAContributingCauses(basePath) {
    console.log("loadRCAContributingCauses()........");
    var modifyAclDiv = dojo.byId("contributingCauseDiv");
    modifyAclDiv.innerHTML = "Loading Contributing Causes... <img alt=\"Loading Contributing Causes...\" src=\"images/w3/loader.gif \">";
    var URL = basePath + "loadRCAContributingCauses.htm";
    var rcaId = dojo.byId("rcaId").value;

    // The parameters to pass to xhrGet, the url, how to handle it, and the callbacks.
    var xhrArgs = {
        url: URL,
        handleAs: "text",
        preventCache: true,
        content: {
            rca_id: rcaId
        },
        load: function (data) {
            if (data != null) {
                dojo.place(data, modifyAclDiv, "only");
            }
            console.log("success");
        },
        error: function (error) {
            console.log("failure: " + error);
            //  modifyAclDiv.innerHTML = "An unexpected error occurred: " + error;
        }
    }

    // Call the asynchronous xhrGet
    var deferred = dojo.xhrGet(xhrArgs);
}

function loadRcaActions(basePath) {
    console.log("loadRcaActions()........");
    var modifyAclDiv = dojo.byId("actionItemsDiv");
    modifyAclDiv.innerHTML = "Loading Actions... <img alt=\"Loading Actions...\" src=\"images/w3/loader.gif \">";
    var URL = basePath + "loadRcaActions.htm";
    var rcaId = dojo.byId("rcaId").value;

    // The parameters to pass to xhrGet, the url, how to handle it, and the callbacks.
    var xhrArgs = {
        url: URL,
        handleAs: "text",
        preventCache: true,
        content: {
            rca_id: rcaId
        },
        load: function (data) {
            console.log(' load action success...');
            dojo.place(data, modifyAclDiv, "only");
            console.log("success");
        },
        error: function (error) {
            console.log("failure: " + error);
        }
    }

    // Call the asynchronous xhrGet
    var deferred = dojo.xhrGet(xhrArgs);

    // actions
    var rcaStage = dojo.byId('rcaStage').value;
    if (rcaStage == "ClosedWOpenActions") {
        $("#ibm-approve").hide();
        $("#ibm-submit").hide();
        $("#ibm-save").hide();
        $("#ibm-cancel").hide();

    }
}






function deleteSecondaryRcaTicket(basePath, rcaTicketId) {
    console.log("deleteSecondaryRcaTicket()........");
    var URL = basePath + "deleteRcaTicket.htm";
    var modifyAclDiv = dojo.byId("rcaTicketsDiv");
    modifyAclDiv.innerHTML = "Deleting Secondary Ticket... <img alt=\"Deleting Secondary Ticket...\" src=\"images/w3/loader.gif \">";

    dojo.xhrPost({
        // The URL of the request
        url: URL,
        form: dojo.byId("rcaForm"),
        handleAs: "text",
        preventCache: true,
        content: {
            rca_ticket_id: rcaTicketId
        },
        // The success handler
        load: function (response) {
            if (response != null && response == "SUCCESS") {
                loadRcaTickets(basePath);
                return true;
            }
            else {
                return false;
            }
        },
        // The error handler
        error: function () {
            return false;
        },
        // The complete handler
        handle: function () {
            // hasBeenSent = true;
        }
    });
}


function deleteRcaAction(basePath, rcaActionId) {
    console.log("deleteRcaAction()........");
    var URL = basePath + "deleteRcaAction.htm";

    var modifyAclDiv = dojo.byId("actionItemsDiv");
    modifyAclDiv.innerHTML = "Deleting Action... <img alt=\"Deleting Action...\" src=\"images/w3/loader.gif \">";


    dojo.xhrPost({
        // The URL of the request
        url: URL,
        handleAs: "text",
        form: dojo.byId("rcaForm"),
        preventCache: true,
        content: {
            rca_action_id: rcaActionId
        },
        // The success handler
        load: function (response) {
            if (response != null && response == "SUCCESS") {
                loadRcaActions(basePath);
                return true;
            }
            else {
                return false;
            }
        },
        // The error handler
        error: function () {
            return false;
        },
        // The complete handler
        handle: function () {
            // hasBeenSent = true;
        }
    });
}





function getOutageSubCategories(basePath, outageCategoryId, outageSubCategoryId) {
    console.log("getOutageSubCategories()........");

    var outageCategory = dojo.byId(outageCategoryId).value;
    var outSubCategories = dojo.byId(outageSubCategoryId);

    // remove all existing options if exists
    while (outSubCategories.firstChild) outSubCategories.removeChild(outSubCategories.firstChild);

    // adding default option
    dojo.create("option", {value: '', innerHTML: 'Please select the option'}, outSubCategories);

    var URL = basePath + "outageSubCategories.htm";
    dojo.xhrGet({
        // The URL of the request
        url: URL,
        handleAs: "text",
        preventCache: true,
        content: {
            outage_category: outageCategory
        },
        // The success handler
        load: function (response) {
            if (response != null) {
                var data = $.parseJSON(response);
                if (data != null && data.length > 0) {
                    for (var i = 0; i < data.length; i++) {
                        dojo.create("option", {
                            value: data[i].serviceDescriptionName,
                            innerHTML: data[i].serviceDescriptionName
                        }, outSubCategories);
                    }
                }
                return true;
            }
            else {
                return false;
            }
        },
        // The error handler
        error: function () {
            return false;
        },
        // The complete handler
        handle: function () {
            // hasBeenSent = true;
        }
    });
}


function getCauses(basePath, causeSource, causeReason) {
    console.log("getCauseReasons()........");

    var causeSource = dojo.byId(causeSource).value;
    var causeReasons = dojo.byId(causeReason);

    // remove all existing options if exists
    while (causeReasons.firstChild) causeReasons.removeChild(causeReasons.firstChild);

    // adding default option
    dojo.create("option", {value: '', innerHTML: 'Please select the option'}, causeReasons);

    var URL = basePath + "loadCauses.htm";

    dojo.xhrGet({
        // The URL of the request
        url: URL,
        handleAs: "text",
        preventCache: true,
        content: {
            cause_description_name: causeSource
        },
        // The success handler
        load: function (response) {
            if (response != null) {
                var data = $.parseJSON(response);
                if (data != null && data.length > 0) {
                    for (var i = 0; i < data.length; i++) {
                        dojo.create("option", {
                            value: data[i].causeDescriptionName,
                            innerHTML: data[i].causeDescriptionName
                        }, causeReasons);
                    }
                }
                return true;
            }
            else {
                return false;
            }
        },
        // The error handler
        error: function () {
            return false;
        },
        // The complete handler
        handle: function () {
            // hasBeenSent = true;
        }
    });
}

/*
 function deleteRCAContributingCause(basePath, rcaCauseId) {
 console.log("deleteRCAContributingCause()........");
 var URL = basePath + "deleteRCAContributingCause.htm";

 dojo.xhrGet({
 // The URL of the request
 url: URL,
 handleAs: "text",
 preventCache: true,
 content: {
 rca_cause_id: rcaCauseId
 },
 // The success handler
 load: function (response) {
 if (response != null && response == "SUCCESS") {
 loadRCAContributingCauses(basePath);
 return true;
 }
 else {
 return false;
 }
 },
 // The error handler
 error: function () {
 return false;
 },
 // The complete handler
 handle: function () {
 // hasBeenSent = true;
 }
 });
 }
 */

function deleteRCAContributingCause(basePath, rcaCauseId) {
    console.log("deleteRCAContributingCause()........");
    var URL = basePath + "deleteRCAContributingCause.htm";

    var modifyAclDiv = dojo.byId("contributingCauseDiv");
    modifyAclDiv.innerHTML = "Deleting Contributing Cause... <img alt=\"Deleting Contributing Cause...\" src=\"images/w3/loader.gif \">";


    dojo.xhrPost({
        // The URL of the request
        url: URL,
        handleAs: "text",
        form: dojo.byId("rcaForm"),
        preventCache: true,
        content: {
            rca_cause_id: rcaCauseId
        },
        // The success handler
        load: function (response) {
            if (response != null && response == "SUCCESS") {
                loadRCAContributingCauses(basePath);
                return true;
            }
            else {
                return false;
            }
        },
        // The error handler
        error: function () {
            return false;
        },
        // The complete handler
        handle: function () {
            // hasBeenSent = true;
        }
    });
}

function enabledRcaLocations() {
	console.log('enabledRcaLocations()...');
    var rcaFailure = $("#locationOfFailure").val();
    $('#loc_other_th1').hide();
	$('#loc_other_th2').hide();
	
    if (rcaFailure == 'Other') {
    	console.log('show loc_other ...');
        $('#loc_other_th1').show();
    	$('#loc_other_th2').show();
    } else {
    	console.log('hide loc_other ...');
    	dojo.query('#location_other').val("");
    }
}


function enableOtherSubCat2() {
	console.log('enableOtherSubCat2()...');
    var rcaFailure = $("#outageSubCategory2").val();
    $('#other_SubCat2_1').hide();
	$('#other_SubCat2_2').hide();
	
    if (rcaFailure == 'Other') {
    	console.log('show Other.');
        $('#other_SubCat2_1').show();
    	$('#other_SubCat2_2').show();
    } else {
    	console.log('hide Other.');
    	dojo.query('#RcaOtherOutageSubCat2').val("");
    }
}


function enableOtherAssociatedTool() {
	
    var rcaFailure = $("#primaryTicketAssociation").val();
    $('#other_assoc_tool').hide();
	
    if (rcaFailure == 'Other') {
        $('#other_assoc_tool').show();
    } else {
    	dojo.query('#priOtherAssociatedTool').val("");
    }
}

function enabledRcaReasons() {
    resetDependentFields();
    var rcaReason = $("#rcaReason").val();
    $('#outage_th1').hide();
	$('#outage_th2').hide();
	
    if (rcaReason == 'RCA requested by Customer or Mgmt') {
        $('#rcaOpeningReasonTr').show();
        $('#otherReasonDetailTr').hide();
        $('#fccTr').hide();
        $('#fcilTr').hide();
        $('#fcaTr').hide();
        $('#fcagTr').hide();
    }
    else if (rcaReason == 'Failed Change') {
        $('#rcaOpeningReasonTr').hide();
        $('#otherReasonDetailTr').hide();
        $('#fccTr').show();
        $('#fcilTr').show();
        $('#fcaTr').show();
        $('#fcagTr').show();
    }
    else if (rcaReason == 'Other') {
        $('#otherReasonDetailTr').show();
        $('#rcaOpeningReasonTr').hide();
        $('#fccTr').hide();
        $('#fcilTr').hide();
        $('#fcaTr').hide();
        $('#fcagTr').hide();
    }else if (rcaReason == 'Outage') {
    	$('#outage_th1').show();
    	$('#outage_th2').show();
    	$('#otherReasonDetailTr').hide();
        $('#rcaOpeningReasonTr').hide();
        $('#fccTr').hide();
        $('#fcilTr').hide();
        $('#fcaTr').hide();
        $('#fcagTr').hide();
    }
    else {
        $('#otherReasonDetailTr').hide();
        $('#rcaOpeningReasonTr').hide();
        $('#fccTr').hide();
        $('#fcilTr').hide();
        $('#fcaTr').hide();
        $('#fcagTr').hide();
    }

}


function assignFailedCriteria() {
    console.log('assignFailedCriteria..')
    var fccSelect = dojo.byId('fccSelect').value;
    dojo.query('#failedChangeCriteria').val(fccSelect);
}

function loadDependentFields() {
    console.log('loadDependentFields()...')

    if ($('#primaryTicketExistsY').is(':checked')) {
        $('#yes_primary_ticket_detail').show();
        $('#no_primary_ticket_detail').hide();
    }
    if ($('#primaryTicketExistsN').is(':checked')) {
        $('#yes_primary_ticket_detail').hide();
        $('#no_primary_ticket_detail').show();
    }


// repeat issue //
    var repeatIssue = $("#repeatIssue").val();
    if (repeatIssue == 'Repeat' || repeatIssue == 'Yes') {
        $('#repeatRcaNumber').show();
    } else {
        document.getElementById('repeatRcaNumber').value = "";
        $('#repeatRcaNumber').hide();
    }

// reason rca required //
    var rcaReason = $("#rcaReason").val();
    if (rcaReason == 'RCA requested by Customer or Mgmt') {
        $('#rcaOpeningReasonTr').show();
        $('#otherReasonDetailTr').hide();
        $('#fccTr').hide();
        $('#fcilTr').hide();
        $('#fcaTr').hide();
        $('#fcagTr').hide();
    }
    else if (rcaReason == 'Failed Change') {
        var failedChangeCriteria = dojo.byId('failedChangeCriteria').value;
        dojo.query('#fccSelect').val(failedChangeCriteria);
        $('#rcaOpeningReasonTr').hide();
        $('#otherReasonDetailTr').hide();
        $('#fccTr').show();
        $('#fcilTr').show();
        $('#fcaTr').show();
        $('#fcagTr').show();
    }
    else if (rcaReason == 'Other') {
        $('#otherReasonDetailTr').show();
        $('#rcaOpeningReasonTr').hide();
        $('#fccTr').hide();
        $('#fcilTr').hide();
        $('#fcaTr').hide();
        $('#fcagTr').hide();
    }
    else {
        $('#otherReasonDetailTr').hide();
        $('#rcaOpeningReasonTr').hide();
        $('#fccTr').hide();
        $('#fcilTr').hide();
        $('#fcaTr').hide();
        $('#fcagTr').hide();
    }


    // rca approved by coordinator
    var hasCoordinatorApproved = dojo.byId("hasCoordinatorApproved").value;
    console.log('hasCoordinatorApproved : ' + hasCoordinatorApproved);
    if (hasCoordinatorApproved.trim() == "N") {
        $("#hasCoordinatorApprovedBox").prop("checked", false);
    }
    else if (hasCoordinatorApproved.trim() == "Y"){
        console.log('setting delegate accepted true')
        $("#hasCoordinatorApprovedBox").prop("checked", true);
    }

    //dueDate
    var dueDate = dojo.byId("dueDate").value;
    var dueDateModificationReason = dojo.byId("dueDateModificationReason").value;
    console.log("dueDateModificationReason : " + dueDateModificationReason );
    dojo.query('#oldDueDate').val(dueDate);
    dojo.query('#savedDueDate').val(dueDate);
    var isDueDateModificationEnabled = $("#isDueDateModificationEnabled").val();
    console.log("dueDueModi : " + isDueDateModificationEnabled);
    if(isDueDateModificationEnabled == "Y" || (dueDateModificationReason != null && dueDateModificationReason != ''))
    {
        $('#due_Date_Modification_Reason').show();
    }

   // customer impacted list
    if ($('#customerImpactedY').is(':checked')) {
        $('#customer_impact_details').show();
    }

}

function resetDependentFields() {
    dojo.query('#rcaOpeningReason').val("");
    dojo.query('#failedChangeImpactLevel1').val("");
    dojo.query('#failedChangeImpactLevel2').val("");
    dojo.query('#failedChangeImpactLevel3').val("");
    dojo.query('#failedChangeImpactLevel4').val("");
    dojo.query('#failedChangeImpactLevel5').val("");
    dojo.query('#failedChangeAssignee').val("");
    dojo.query('#failedChangeAssigneeGroup').val("");
    dojo.query('#failedChangeCriteria').val("");
    dojo.query('#fccSelect').val("");
    dojo.query('#otherReasonDetail').val("");
    dojo.query('#dueDateModificationReason').val("");
}


function enableRepeatRcaNum() {
    console.log('enableRepeatRcaNum()....');
    var repeatIssue = $("#repeatIssue").val();
    console.log('repeatIssue: ' + repeatIssue);
    if (repeatIssue == 'Repeat' || repeatIssue == 'Yes') {
        dojo.query('#repeatRcaOrTicketNum').val("");
        $('#repeatRcaNumber').show();
    } else {
        document.getElementById('repeatRcaNumber').value = "";
        $('#repeatRcaNumber').hide();
    }
}

function resetPrimaryTktDetails(primaryTicketExists) {
    console.log('resetPrimaryTktDetails()...')

    console.log('primaryTicketExists: ' + primaryTicketExists);
    if (primaryTicketExists == 'Y') {
        dojo.query('#noPrimaryTicketExplanation').val("");
    } else if (primaryTicketExists == 'N') {
        dojo.query('#problemIndidentRecord').val("");
        dojo.query('#severity').val("");
        dojo.query('#primaryTicketAssociation').val("");
        dojo.query('#primaryTicketType').val("");
    }

}

function calculateDueDate(basePath) {
    console.log("calculateDueDate()........");
    console.log('basePath: ' + basePath);
    var incidentEndTime = dojo.byId("incidentEntTime").value;
    var URL = basePath + "calculateDueDate.htm";
    console.log('url: ' + url);

    dojo.xhrGet({
        // The URL of the request
        url: URL,
        handleAs: "text",
        preventCache: true,
        content: {
            incidentEndTime: incidentEndTime
        },
        // The success handler
        load: function (response) {
            if (response != null) {
                console.log('dueDate: ' + response);
                dojo.query('#dueDate').val(response);
                dojo.query('#rcaDueDate').val(response);
                return true;
            }
            else {
                return false;
            }
        },
        // The error handler
        error: function () {
            return false;
        },
        // The complete handler
        handle: function () {
            // hasBeenSent = true;
        }
    });
}

function calculateIncidentDuration(basePath) {
    console.log("calculateIncidentDuration()........");

    var incidentStartTime = dojo.byId("incidentStartTime").value;
    var incidentEndTime = dojo.byId("incidentEntTime").value;

    var URL = basePath + "calculateTimeSpan.htm";

    dojo.xhrGet({
        // The URL of the request
        url: URL,
        handleAs: "text",
        preventCache: true,
        content: {
            fromDate: incidentStartTime,
            toDate: incidentEndTime
        },
        // The success handler
        load: function (response) {
            if (response != null) {
                console.log('time span: ' + response);
                dojo.query('#incidentDuration').val(response);
                return true;
            }
            else {
                return false;
            }
        },
        // The error handler
        error: function () {
            return false;
        },
        // The complete handler
        handle: function () {
            // hasBeenSent = true;
        }
    });
}


function calculateDurationAndDueDate(basePath) {
    calculateIncidentDuration(basePath);
    calculateDueDate(basePath);
}

function loadRcaFormDetails(basePath) {
    loadRcaTickets(basePath);
    loadRCAContributingCauses(basePath);
    loadRcaActions(basePath);
    loadRcaHistoryLog(basePath);
    setOwnerAndDelegateCheckBox();
    enableOrDisableRCAFormButtons();
    loadDependentFields();
    //setCauseGuidence();
}

function enforceMaxLength(obj, length) {
    var maxLength = length;
    if (obj.value.length > maxLength) {
        obj.value = obj.value.substring(0, maxLength);
    }
}

function setCauseGuidence(causeCategoryIn, causeSubCategoryIn, causeSelectionGuidance) {
    console.log('setCauseGuidence()....');
    if (causeCategoryIn != null && causeSubCategoryIn != null) {
        var causeCategory = dojo.byId(causeCategoryIn).value;
        var causeSubCategory = dojo.byId(causeSubCategoryIn).value;
        console.log('causeCategory : ' + causeCategory);
        console.log('causeSubCategory : ' + causeSubCategory);
        console.log('causeSelectionGuidance : ' + causeSelectionGuidance);


        if (causeCategory == 'Staffing' && causeSubCategory == 'N/A') {
            dojo.query(causeSelectionGuidance).val('Inadequate staffing levels to cope with required work.\nExample: The staffing levels as agreed in the budget are not reached.');
        }
        if (causeCategory == 'Skills' && causeSubCategory == 'N/A') {
            dojo.query(causeSelectionGuidance).val('Staff found to have inadequate skill or knowledge to complete tasks appropriately, usually resulting in inappropriate judgment of what action to undertake. Includes things such as entering incorrect scripts, or not following prescribed work instructions. ');
        }
        if (causeCategory == 'Cultural Influences' && causeSubCategory == 'N/A') {
            dojo.query(causeSelectionGuidance).val('Cultural influences may cause communication issues when the difference are not understood.');
        }
        if (causeCategory == 'Decision making error' && causeSubCategory == 'N/A') {
            dojo.query(causeSelectionGuidance).val('Decision making error despite the available information an incorrect decision was made.\nNote: this cause code can not be used as a root cause (human error)');
        }

        if (causeCategory == 'Professional negligence' && causeSubCategory == 'N/A') {
            dojo.query(causeSelectionGuidance).val('Professional negligence despite clear and understood instructions, the instructions were not followed.\nNote: this cause code can not be used as a root cause (human error)');
        }
        if (causeCategory == 'Change Management' && causeSubCategory == 'Prep-Detail') {
            dojo.query(causeSelectionGuidance).val('Content of the change plan incorrect, incomplete or not fully thought through.  Example, an instruction was missing in the change implementation script, a command was described in the incorrect order etc.');
        }
        if (causeCategory == 'Change Management' && causeSubCategory == 'Prep-Backout plan') {
            dojo.query(causeSelectionGuidance).val('Change did not contain a backout plan or the available backout plan failed.\nExample: a change required a backout which failed. The scheduled change window was exceeded and resulted in an incident.');
        }
        if (causeCategory == 'Change Management' && causeSubCategory == 'Prep-Scheduling') {
            dojo.query(causeSelectionGuidance).val('Change Conflicted with another change or service.\nExample: During the preparation of the change other, a change scheduled at the same time was not taken into account.');
        }

        if (causeCategory == 'Change Management' && causeSubCategory == 'Prep-Category(risk)') {
            dojo.query(causeSelectionGuidance).val('Incorrectly Categorized or risk-assessed (No or incorrect change category)\nExample: the risk category of the change (Cat 1..5) was incorrectly set. Therefore the change had less attention and the detail level of the change plan was less.');
        }
        if (causeCategory == 'Change Management' && causeSubCategory == 'Prep-Authorization/Approval') {
            dojo.query(causeSelectionGuidance).val('Change did not contain the correct approval groups, Approvers listed did not proactively spot and call out issues that we would have expected them to.\nExample: an authorized change was executed and resulted in an incident.');
        }
        if (causeCategory == 'Change Management' && causeSubCategory == 'Prep-Testing') {
            dojo.query(causeSelectionGuidance).val('Testing was possible but was not performed, or was performed but was not complete or exhaustive enough.\nExample: a change was not tested before it was executed, this risk was not reflected in the change plan.');
        }
        if (causeCategory == 'Change Management' && causeSubCategory == 'Prep-Duration') {
            dojo.query(causeSelectionGuidance).val('Insufficient time was requested for the change, hence causing an overrun.\nExample: a change which was executed according to plan overran the scheduled change window.');
        }


        // add new
        if (causeCategory == 'Staffing' && causeSubCategory == 'N/A') {
            dojo.query(causeSelectionGuidance).val('Inadequate staffing levels to cope with required work.\nExample: The staffing levels as agreed in the budget are not reached.');
        }
        if (causeCategory == 'Skills' && causeSubCategory == 'N/A') {
            dojo.query(causeSelectionGuidance).val('Staff found to have inadequate skill or knowledge to complete tasks appropriately, usually resulting in inappropriate judgment of what action to undertake. Includes things such as entering incorrect scripts, or not following prescribed work instructions. ');
        }
        if (causeCategory == 'Cultural Influences' && causeSubCategory == 'N/A') {
            dojo.query(causeSelectionGuidance).val('Cultural influences may cause communication issues when the difference are not understood.');
        }
        if (causeCategory == 'Decision making error' && causeSubCategory == 'N/A') {
            dojo.query(causeSelectionGuidance).val('Decision making error despite the available information an incorrect decision was made.\nNote: this cause code can not be used as a root cause (human error)');
        }

        if (causeCategory == 'Professional negligence' && causeSubCategory == 'N/A') {
            dojo.query(causeSelectionGuidance).val('Professional negligence despite clear and understood instructions, the instructions were not followed.\nNote: this cause code can not be used as a root cause (human error)');
        }
        if (causeCategory == 'Change Management' && causeSubCategory == 'Prep-Detail') {
            dojo.query(causeSelectionGuidance).val('Content of the change plan incorrect, incomplete or not fully thought through.  Example, an instruction was missing in the change implementation script, a command was described in the incorrect order etc.');
        }
        if (causeCategory == 'Change Management' && causeSubCategory == 'Prep-Backout plan') {
            dojo.query(causeSelectionGuidance).val('Change did not contain a backout plan or the available backout plan failed.\nExample: a change required a backout which failed. The scheduled change window was exceeded and resulted in an incident.');
        }
        if (causeCategory == 'Change Management' && causeSubCategory == 'Prep-Scheduling') {
            dojo.query(causeSelectionGuidance).val('Change Conflicted with another change or service.\nExample: During the preparation of the change other, a change scheduled at the same time was not taken into account.');
        }

        if (causeCategory == 'Change Management' && causeSubCategory == 'Prep-Category(risk)') {
            dojo.query(causeSelectionGuidance).val('Incorrectly Categorized or risk-assessed (No or incorrect change category)\nExample: the risk category of the change (Cat 1..5) was incorrectly set. Therefore the change had less attention and the detail level of the change plan was less.');
        }
        if (causeCategory == 'Change Management' && causeSubCategory == 'Prep-Authorization/Approval') {
            dojo.query(causeSelectionGuidance).val('Change did not contain the correct approval groups, Approvers listed did not proactively spot and call out issues that we would have expected them to.\nExample: an authorized change was executed and resulted in an incident.');
        }
        if (causeCategory == 'Change Management' && causeSubCategory == 'Prep-Testing') {
            dojo.query(causeSelectionGuidance).val('Testing was possible but was not performed, or was performed but was not complete or exhaustive enough.\nExample: a change was not tested before it was executed, this risk was not reflected in the change plan.');
        }
        if (causeCategory == 'Change Management' && causeSubCategory == 'Prep-Duration') {
            dojo.query(causeSelectionGuidance).val('Insufficient time was requested for the change, hence causing an overrun.\nExample: a change which was executed according to plan overran the scheduled change window.');
        }

    }
}

function enableRcaFileDescription() {
    console.log('file is selected');
    $("#descTd").show();
    $("#description").show();
}


function setIsRcaFileDelete(isFileDelete) {
    dojo.byId("isDeleted_" + isFileDelete).value = 'Y';
}

function resetCauseGuidence(causeSelectionGuidance) {
    dojo.query(causeSelectionGuidance).val('');
}

function enableDueDateJustification() {
    console.log("enableDueDateJustification.....");
    var dueDate = dojo.byId("dueDate").value;
    console.log("dueDate: " + dueDate);
    dojo.query('#rcaDueDate').val(dueDate);
    var oldDueDate = dojo.byId("oldDueDate").value;
    console.log("Old Due Date: " + oldDueDate);
    var savedDueDate = dojo.byId("savedDueDate").value;
    console.log("Saved Due Date: " + savedDueDate);
    if(dueDate != oldDueDate && dueDate != savedDueDate){
        dojo.query('#dueDateModificationReason').val("");
        dojo.query('#oldDueDate').val(dueDate);
        $('#due_Date_Modification_Reason').show();
        dojo.query('#isDueDateModificationEnabled').val("Y");
    }else if (dueDate == savedDueDate){
        $('#due_Date_Modification_Reason').hide();
        dojo.query('#isDueDateModificationEnabled').val("N");
    }
}

function ibmFaces() {
    ta1 = FacesTypeAhead.init(
        dojo.query(".typeahead"),
        {
            key: "typeahead_demo;webahead@us.ibm.com",
            faces: {
                headerLabel: "People",

                onclick: function (person) {
                    return person['notes-id'];
                }
            }
        });
}


function addSecondaryRcaTicket(basePath) {
    console.log("addSecondaryRcaTicket()........");
    var URL = basePath + "addSecondaryRCATicket.htm";
    var rcaId = dojo.byId("rcaId").value;

    dojo.xhrPost({
        // The URL of the request
        url: URL,
        form: dojo.byId("rcaForm"),
        handleAs: "text",
        preventCache: true,
        content: {
            rca_id: rcaId
        },
        // The success handler
        load: function (response) {
            if (response != null && response == "SUCCESS") {
                loadRcaTickets(basePath);
                return true;
            }
            else {
                return false;
            }
        },
        // The error handler
        error: function () {
            return false;
        },
        // The complete handler
        handle: function () {
            // hasBeenSent = true;
        }
    });
}


function addRCAContributingCause(basePath) {
    console.log("addRCAContributingCause ()........");
    var URL = basePath + "addRCAContributingCause.htm";
    var rcaId = dojo.byId("rcaId").value;

    dojo.xhrPost({
        // The URL of the request
        url: URL,
        form: dojo.byId("rcaForm"),
        handleAs: "text",
        preventCache: true,
        content: {
            rca_id: rcaId
        },
        // The success handler
        load: function (response) {
            if (response != null && response == "SUCCESS") {
                loadRCAContributingCauses(basePath);
                return true;
            }
            else {
                return false;
            }
        },
        // The error handler
        error: function () {
            return false;
        },
        // The complete handler
        handle: function () {
            // hasBeenSent = true;
        }
    });
}

function addRcaAction(basePath) {
    console.log("addRcaAction()........");
    var URL = basePath + "addRcaAction.htm";
    var rcaId = dojo.byId("rcaId").value;
    $("#noActionsTextDiv").hide();

    dojo.xhrPost({
        // The URL of the request
        url: URL,
        form: dojo.byId("rcaForm"),
        handleAs: "text",
        preventCache: true,
        content: {
            rca_id: rcaId
        },
        // The success handler
        load: function (response) {
            if (response != null && response == "SUCCESS") {
                loadRcaActions(basePath);
                return true;
            }
            else {
                return false;
            }
        },
        // The error handler
        error: function () {
            return false;
        },
        // The complete handler
        handle: function () {
            // hasBeenSent = true;
        }
    });
}

