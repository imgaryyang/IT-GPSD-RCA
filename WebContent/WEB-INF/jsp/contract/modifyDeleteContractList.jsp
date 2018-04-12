<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
							<div id="upDelResponse"></div>
							<form:form commandName="commandHelper" action="#" id="modifyDeleteContractsForm">
									<table cellspacing="0" cellpadding="0" border="0" id="contractData" summary="contracts" class="ibm-data-table">
										<caption><span><em>Contract(s) Detail</em></span></caption>
											<thead>
												<tr>
													<th scope="col">Account ID</th>
													<th scope="col">Account Name</th>
													<!--<th scope="col">Customer</th> -->
													<th scope="col" align="center">Delete</th>
												</tr>
											</thead>								
												<tbody>
														<c:if test="${fn:length(commandHelper.contracts) > 0 }">
														<c:catch var ="catchException">
															<c:forEach items="${commandHelper.contracts}" var="item" varStatus="itemRow">
																<tr>
																	<td scope="row"><a href="javascript:void(0)" onclick="modifyContract('${item.contractId}');" title="Edit" class="ibm-signin-link">${item.contractId}</a></td>
																	<td scope="row">${item.title}</td>
																	<!--<td scope="row">${item.customer.name}</td> -->
																	<td align="left" scope="row"><a href="javascript:void(0)" onsubmit="deleteContract('${item.contractId}');" onclick="deleteContract('${item.contractId}');" title="Delete" class="ibm-delete-link" ></a></td>
																</tr>
															</c:forEach>
															</c:catch>
													<c:if test="${catchException != null}" >
													   	<p>There is an exception: ${catchException.message}<br />
												   			The exception is : ${catchException} </p>
														<%
														 Exception e = (Exception) pageContext.findAttribute("catchException");
														 System.err.println(e.getMessage());
														 e.printStackTrace();
														%>
													</c:if>
														</c:if>														
													<tr>
												</tr>	
												
																																		
												</tbody>
												
												
										</table>
										
								</form:form>								
								
								
								
