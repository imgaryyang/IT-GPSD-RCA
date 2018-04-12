<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
							<div role="region"  aria-label="pagination" class="filterbar" style="height: 20px; text-align: right; font-size: 0.9em; font-weight: bold; margin-top: 5px;">
								<form action="nextPrevious" method="GET">
									<span> Showing Records [ ${startRow} - ${endRow} ] of Total ${totalRowCount}       </span>
									<span>    </span>
								<c:if test="${totalRowCount>25}">
									<a href="javascript:void(0)" rel="prev" onclick="return getPreviousPage();">Previous </a>
                             <c:if test="${totalRowCount != endRow}">									
									|  
							<a href="javascript:void(0)" rel="next" onclick="return getNextPage();">Next </a>
								</c:if>	
								</c:if>
								</form>
							</div>
							<div id="displayMsg"></div>
							<!--form:form commandName="Contract.java" action="#" name="" id=""> -->
									<table cellspacing="0" cellpadding="0" border="0" id="contractData" summary="contracts" class="ibm-data-table">
											<thead>
												<tr>
													<th scope="col" width="25%">Account Name</th>
													<th scope="col" width="25%">Modified on</th>
													<th scope="col" width="25%">Modified by</th>
													<th scope="col" width="25%">Stage</th>
												</tr>
											</thead>
											
												<tbody>
													<c:forEach items="${contracts}" var="contract">
														<tr>															
															 <td scope="row" width="25%"><a href="editContract.htm?contractId=${contract.contractId}">${contract.title}</a></td>
                                                             <td scope="row" width="25%">${contract.updatedOn}</td>
															 <td scope="row" width="25%">${contract.email}</td>
															 <td scope="row" width="25%">${contract.state== '0'? 'Active':'Approved'}</td>
														</tr>
													</c:forEach>																										
												</tbody>
										</table>								
								