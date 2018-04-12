/**<pre> 
 *==========================================================================
 *
 * Copyright: (C) IBM Corporation 2013 -- IBM Internal Use Only
 *
 *==========================================================================
 *
 *    FILE: ContractProcessDaoImpl.java
 *    CREATOR:Waqar Malik
 *    DEPT: GBS PAK
 *    DATE: 09/03/2013
 *
 *-PURPOSE-----------------------------------------------------------------
 * 
 *--------------------------------------------------------------------------
 *
 *
 *-CHANGE LOG--------------------------------------------------------------
 * 09/03/2013Waqar Malik Initial coding.
 *==========================================================================
 * </pre> */

package com.gps.dao.impl;

import com.gps.dao.ContractProcessDao;
import com.gps.exceptions.GPSException;
import com.gps.util.StringUtils;
import com.gps.vo.ContractProcess;
import com.gps.vo.helper.ReportParam;
import com.gps.vo.helper.SearchFilter;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * This class provides implementation for ContractProcessDao.
 * 
 * @authorWaqar Malik
 */
@Repository
public class ContractProcessDaoImpl implements ContractProcessDao {
	private static Logger log = Logger.getLogger(ContractProcessDaoImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;


	@Override
	public void addContractProcess(ContractProcess contractProcess) throws GPSException {
		try {
			entityManager.persist(contractProcess);
		} catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
	}

	@Override
	public boolean removeContractProcess(ContractProcess contractProcess) throws GPSException {
		try {
			entityManager.remove(contractProcess);
		} catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
		return true;
	}

	@Override
	public void addContractProcess(List<ContractProcess> contractProcessList) throws GPSException {
		for(ContractProcess contractProcess: contractProcessList){
			addContractProcess(contractProcess);
		}
	}

	
	@Override
	public ContractProcess getContractProcess(Integer contractProcessId) throws GPSException {
		ContractProcess contractProcess = null;
		try {
			contractProcess = entityManager.find(ContractProcess.class, contractProcessId);
		} catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
		return contractProcess;
	}


	@Override
	public ContractProcess getContractProcess(Integer contractId, Integer processId) throws GPSException {
		ContractProcess contractProcess = null;
		try {
			Query query = entityManager.createQuery("from ContractProcess cp where cp.contract.contractId = :contractId and cp.process.processId = :processId");
			query.setParameter("contractId", contractId);
			query.setParameter("processId", processId);
			contractProcess = (ContractProcess) query.getSingleResult();
		} catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
		return contractProcess;
	}


	@Override
	public List<ContractProcess> getContractProcessList(Integer contractId) throws GPSException {
		List<ContractProcess> contractProcessList = null;
		StringBuilder builder = new StringBuilder();
		builder.append("from ContractProcess cp Left JOIN FETCH cp.process p ");
		builder.append(" Left JOIN FETCH cp.slas Left JOIN FETCH cp.slos ");
		builder.append(" where cp.contract.contractId = :contractId ");
		builder.append(" order by p.name ");
		
		try {
			Query query = entityManager.createQuery(builder.toString());
			query.setParameter("contractId", contractId);
			contractProcessList = (List<ContractProcess>) query.getResultList();
			log.debug("got ContractProcess's list of "+contractProcessList.size());
		} catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
		return contractProcessList;
	}

	@Override
	public Long getSlaCount(Integer contractProcessId) throws GPSException {
		log.debug("count SLA for contractProcessId = "+contractProcessId);
		Long slaCount = 0L;
		StringBuilder queryString = new StringBuilder();
		queryString.append("select count(s) from Sla s inner join s.contractProcess cp ");
		queryString.append(" where s.contractProcess.contractProcessId = :contractProcessId ");
		try {
			TypedQuery<Long> query  = entityManager.createQuery(queryString.toString(), Long.class);
			query.setParameter("contractProcessId", contractProcessId);
			slaCount =  query.getSingleResult();
		}
		catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
		
		return slaCount;
		
	}
	
	@Override
	public Long getSloCount(Integer contractProcessId) throws GPSException {
		log.debug("count SLA for contractProcessId = "+contractProcessId);
		Long sloCount = 0L;
		StringBuilder queryString = new StringBuilder();
		queryString.append("select count(s) from Slo s inner join s.contractProcess cp ");
		queryString.append(" where s.contractProcess.contractProcessId = :contractProcessId ");
		try {
			TypedQuery<Long> query  = entityManager.createQuery(queryString.toString(), Long.class);
			query.setParameter("contractProcessId", contractProcessId);
			sloCount =  query.getSingleResult();
		}
		catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
		
		return sloCount;
		
	}
	
	public List<Object[]> getSlaReport(ReportParam reportParam,SearchFilter  searchFilter) throws GPSException 
	{
		log.debug("getSlaReport()............");
		List<Object[]> results = null;
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT CONTRACT_PROCESS.CONTRACT_ID,CUSTOMER.NAME CUSTOMER_NAME, CONTRACT.TITLE CONTRACT_TITLE,");
		queryString.append(" PROCESS.PROCESS_ID,PROCESS.NAME PROCESS_NAME,TOWER.NAME TOWER_NAME,COUNTRY.NAME COUNTRY_NAME,COUNTRY.IMT,COUNTRY.IOT,");
		queryString.append(" COUNTRY.GEO,INDUSTRY_SECT.INDUSTRY_NAME,CONTRACT_INDICATOR,INDUSTRY_SECT.SECTOR_NAME,SLA.SUB_PROCES_ID,");
		queryString.append(" SLA_ID,SLA_REF_NO,SLA.NAME SLA_NAME,SLA.REPORT_INTERVAL,SLA.REPORT_MONTH,SLA.CALCULATION_FORMULA,");
		queryString.append(" SLA.DESCRIPTION,SLA.Status,SLA.CONTACT_NAME,SLA.CONTACT_ID,SLA.CONTACT_PHONE");
		queryString.append(" FROM CUSTOMER , TOWER,SLA, COUNTRY, CONTRACT_PROCESS LEFT OUTER JOIN PROCESS ON CONTRACT_PROCESS.PROCESS_ID = PROCESS.PROCESS_ID,CONTRACT");
		queryString.append(" LEFT OUTER JOIN INDUSTRY_SECT ON  INDUSTRY_SECT.INDUSTRY_ID=CONTRACT.INDUSTRY_ID");
		queryString.append(" WHERE CONTRACT_PROCESS.CONTRACT_ID = CONTRACT.CONTRACT_ID");
		queryString.append(" AND CUSTOMER.INAC= CONTRACT.INAC");
		queryString.append(" AND  TOWER.TOWER_ID=CONTRACT.TOWER_ID");
		queryString.append(" AND COUNTRY.COUNTRY_CODE= CONTRACT.COUNTRY_CODE");
		queryString.append(" AND CONTRACT_PROCESS.CONTRACT_PROCESS_ID=SLA.CONTRACT_PROCESS_ID");

		if(null!= searchFilter)
		{
			if(!StringUtils.isNullOrEmpty(searchFilter.getContract()))
			{
				queryString.append(" AND CONTRACT.TITLE = '" +searchFilter.getContract()+ "'");
			}
			if(!StringUtils.isNullOrEmpty(searchFilter.getCustomer()))
			{
				queryString.append(" AND CUSTOMER.NAME = '" +searchFilter.getCustomer()+ "'");
			}
			if(!StringUtils.isNullOrEmpty(searchFilter.getLob()))
			{
				queryString.append(" AND CONTRACT.LOB_NAME = '" +searchFilter.getLob()+ "'");
			}
			if(!StringUtils.isNullOrEmpty(searchFilter.getTower()))
			{
				queryString.append(" AND TOWER.TOWER_ID =" + searchFilter.getTower());
			}
			if(!StringUtils.isNullOrEmpty(searchFilter.getStage()))
			{
				queryString.append(" AND CONTRACT.STATE = '" +searchFilter.getStage()+ "'");
			}
			if(!StringUtils.isNullOrEmpty(searchFilter.getCountry()))
			{
				queryString.append(" AND CONTRACT.COUNTRY_CODE = '" +searchFilter.getCountry()+ "'");
			}
			if(!StringUtils.isNullOrEmpty(searchFilter.getImt()))
			{
				queryString.append(" AND COUNTRY.IMT = '" +searchFilter.getImt()+ "'");
			}
			if(!StringUtils.isNullOrEmpty(searchFilter.getIot()))
			{
				queryString.append(" AND COUNTRY.IOT = '" +searchFilter.getIot()+ "'");
			}
			if(!StringUtils.isNullOrEmpty(searchFilter.getGeo()))
			{
				queryString.append(" AND COUNTRY.GEO = '" +searchFilter.getGeo()+ "'");
			}
			if(!StringUtils.isNullOrEmpty(searchFilter.getSector()))
			{
				queryString.append(" AND INDUSTRY_SECT.INDUSTRY_NAME = '" +searchFilter.getSector()+ "'");
			}
			
			
			
		}
		
		try {
			Query query  = entityManager.createNativeQuery(queryString.toString());
			results = query.getResultList();
		}
		catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
		
		return results;
		
	}

	
	
	
	
}
