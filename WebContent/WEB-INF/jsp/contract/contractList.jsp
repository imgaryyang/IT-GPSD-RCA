<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
							<div class="filterbar" style="height: 20px; text-align: right; font-size: 0.9em; font-weight: bold; margin-top: 5px;">
								<form action="nextPrevious">
									<span> Showing Records [ ${startRow} - ${endRow} ] of Total ${totalRowCount}       </span>
									<span>    </span>
								<c:if test="${totalRowCount>25}">
									<a href="javascript:void(0)" class="ibm-forward-link" rel="prev" onclick="return getPreviousPage();">� Previous </a>
									  |  
									<a href="javascript:void(0)" rel="next" onclick="return getNextPage();">Next �</a>
								</c:if>
								</form>
							</div>
							<div id="displayMsg"></div>
							<!--form:form commandName="Contract.java" action="#" name="" id=""> -->
									<table cellspacing="0" cellpadding="0" border="0" id="contractData" summary="contracts" class="ibm-data-table">
											<thead>
												<tr>
													<th scope="col" width="25%">Profile Name</th>
													<th scope="col" width="20%">Updated by</th>
													<th scope="col" width="10%">Update Date</th>
													<th scope="col" width="15%">Country</th>
													<th scope="col" width="12%">Sector</th>
													<th scope="col" width="12%">Stage</th>
													<th scope="col" width="6%">Geo</th>												
												</tr>
											</thead>
											
												<tbody>
													<c:forEach items="${contracts}" var="contract">
														<tr>															
															<td scope="row"><a href="editContract.htm?contractId=${contract.contractId}">${contract.title}</a></td>
															<td scope="row">${contract.email}</td>
															<td scope="row"><fmt:formatDate value="${contract.updatedOn}" pattern="MM-dd-yyyy"/></td>
															<td scope="row">${contract.countryName}</td>
															<td scope="row">${contract.sectorName}</td>
															<td scope="row">${contract.state=='0'?'Active':'Approved'}</td>
															<td scope="row">${contract.geo}</td>
														</tr>
													</c:forEach>																										
												</tbody>
										</table>								
								