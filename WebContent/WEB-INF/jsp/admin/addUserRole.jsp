<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<table class="ibm-results-table" summary="ACLs">
    <thead>
    <tr>
        <th scope="row">
            <form:hidden path="userRole.urId" id="urId"/>
            <table class="ibm-results-table" summary="Roles">
                <thead>
                <tr>
                    <th scope="col"><label for="uEmail" width="20%">*Users Intranet Ids:</label></th>
                    <th scope="col" style="padding-bottom: 5px" width="50%"><form:input path="userRole.gpsUser.email"
                                                                                        id="uEmail"/></th>
                    <th width="30%"></th>
                </tr>


                <tr>
                    <th scope="col"><label for="uRole">Role:</label></th>
                    <th scope="col" style="padding-bottom: 5px"><form:select path="userRole.role" id="uRole">
                        <form:option value="dpe">DPE</form:option>
                        <form:option value="coordinator">Coordinator</form:option>
                        <form:option value="delegate">Delegate</form:option>
                        <form:option value="editor">Editor</form:option>
                        <form:option value="owner">Owner</form:option>
                        <form:option value="creator">Creator</form:option>
                        <form:option value="reader">Reader</form:option>
                        <form:option value="admin">Admin</form:option>
                        <form:option value="reportAdmin">Report Admin</form:option>
                    </form:select></th>
                </tr>

                <tr>
                    <th scope="col"><label for="uContractId">Account:</label></th>
                    <th scope="col" style="padding-bottom: 5px"><form:select path="userRole.contract.contractId"
                                                                             id="uContractId">
                        <form:option value="0" label="Please select the option" />
                        <form:options items="${userRoleForm.contracts}" itemValue="contractId" itemLabel="title"/>
                    </form:select></th>
                </tr>

                <tr>
                    <th scope="row" colspan="2" style="padding-bottom: 5px">
                        <div class="ibm-buttons-row">
                            <input value="Save User Role" id="nonContract-submit-btn" type="button"
                                   name="nonContract-ibm-submit" class="ibm-btn-arrow-pri"
                                   onClick="addUserRoleEntry();"/>
                        </div>
                    </th>
                </tr>
                </thead>
            </table>
        </th>
    </tr>
    </thead>
</table>