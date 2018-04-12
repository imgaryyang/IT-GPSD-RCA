<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tr>
	<td>
		<table class="ibm-results-table" width="100%" cellspacing="1" cellpadding="1"
			   style="margin-bottom: 10px;" summary="The following table shows the labels of profile listing">

			<thead>

			<th align="left" scope="col" width="40%" style="white-space: nowrap;">*Select RCA Coordinator:<p>&nbsp;</p></th>

			<th align="left" scope="col" width="30%">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;                       </th>
			<th align="left" scope="col" width="30%">
						<span>						
						
						<form:select path="initiateRcaForm.rcaCoordinator" cssStyle="background: white;" id="rcaCoordinator">
							<form:options items="${rcaCoordinators}" itemValue="gpsUser.email" itemLabel="gpsUser.notesId"/>
						</form:select>
                        </span>

			</th>


			</thead>
		</table>
	</td>
</tr>