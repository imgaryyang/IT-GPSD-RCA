/**<pre> 
 *==========================================================================
 *
 * Copyright: (C) IBM Corporation 2013 -- IBM Internal Use Only
 *
 *==========================================================================
 *
 *    FILE: ContractRequestDaoImpl.java
 *    CREATOR:Waqar Malik
 *    DEPT: GBS PAK
 *    DATE: 25/09/2013
 *
 *-PURPOSE-----------------------------------------------------------------
 * 
 *--------------------------------------------------------------------------
 *
 *
 *-CHANGE LOG--------------------------------------------------------------
 * 25/09/2013Waqar Malik Initial coding.
 *==========================================================================
 * </pre> */
package com.gps.dao.impl;

import com.gps.dao.ContractRequestDao;
import com.gps.exceptions.GPSException;
import com.gps.vo.ContractRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional(propagation= Propagation.REQUIRED)
public class ContractRequestDaoImpl implements ContractRequestDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void dropContractRequest(ContractRequest contractRequest) throws GPSException {
		try {
			entityManager.remove(contractRequest);
		}
		catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
	}

	@Override
	public void addContractRequest(ContractRequest contractRequest) throws GPSException {
		try {
			entityManager.persist(contractRequest);
		}
		catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
	}

	@Override
	public void updateContractRequest(ContractRequest contractRequest) throws GPSException {
		try {
			entityManager.merge(contractRequest);
		}
		catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
		
	}

	@Override
	public ContractRequest getContractRequestById(Long requestId) throws GPSException {
		ContractRequest contractRequest = null;
		try {
			contractRequest = entityManager.find(ContractRequest.class, requestId);
		}
		catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
		return contractRequest;
	}

	@Override
	public List<ContractRequest> listAwaitingRequests(String requestedBy) throws GPSException {
		List<ContractRequest> listContractRequest = null;
		StringBuilder queryString = new StringBuilder();
		queryString.append("from ContractRequest cr where cr.status in ('0', '8') ");
		queryString.append("and cr.isArchive = 'N' and cr.requestedBy = :requestedBy ");
		
		try {
			TypedQuery<ContractRequest> query  = entityManager.createQuery(queryString.toString(), ContractRequest.class);
			query.setParameter("requestedBy", requestedBy);
			listContractRequest = query.getResultList();
		}
		catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
		return listContractRequest;
	}



	@Override
	public List<ContractRequest> listRequiringAction() throws GPSException {
		List<ContractRequest> listContractRequest = null;
		StringBuilder queryString = new StringBuilder("from ContractRequest cr where cr.status in ('0','1','2') and cr.isArchive = 'N' ");
		try {
			Query query  = entityManager.createQuery(queryString.toString());
			listContractRequest = (List<ContractRequest>) query.getResultList();
		}
		catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
		return listContractRequest;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ContractRequest> getContractRequestsByParam(String sQuery, List<Object> queryParameters) {
		Query query = entityManager.createQuery(sQuery);
		int i = 1;
		for(Object parameter: queryParameters){
			query.setParameter("A" + i++, parameter);
		}
		return	query.getResultList();
	}

	@Override
	public List<String> listSubmitters() throws GPSException {
		List<String> submitters = null;
		StringBuilder queryString = new StringBuilder("select distinct(requestedBy) from ContractRequest cr where cr.isArchive = 'Y' ");
		try {
			Query query  = entityManager.createQuery(queryString.toString());
			submitters = (List<String>) query.getResultList();
		}
		catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
		return submitters;
	}

	@Override
	public List<String> listApprovers() throws GPSException {
		List<String> approvers = null;
		StringBuilder queryString = new StringBuilder("select distinct(towerLeadId) from ContractRequest cr where cr.isArchive = 'Y' ");
		try {
			Query query  = entityManager.createQuery(queryString.toString());
			approvers = (List<String>) query.getResultList();
		}
		catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
		return approvers;
	}

}
