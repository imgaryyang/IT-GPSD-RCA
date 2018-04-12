/**<pre> 
 *==========================================================================
 *
 * Copyright: (C) IBM Corporation 2013 -- IBM Internal Use Only
 *
 *==========================================================================
 *
 *    FILE: SLAServiceImpl.java
 *    CREATOR:Waqar Malik
 *    DEPT: GBS PAK
 *    DATE: 06/04/2013
 *
 *-PURPOSE-----------------------------------------------------------------
 * 
 *--------------------------------------------------------------------------
 *
 *
 *-CHANGE LOG--------------------------------------------------------------
 * 06/04/2013Waqar Malik Initial coding.
 *==========================================================================
 * </pre> */
package com.gps.service.impl;

import com.gps.dao.ContractProcessDao;
import com.gps.exceptions.GPSException;
import com.gps.service.ContractProcessService;
import com.gps.vo.ContractProcess;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class provides implementation of ContractProcessService.
 * 
 * @authorWaqar Malik
 */
@Service
public class ContractProcessServiceImpl implements ContractProcessService {
	private static final Logger log = Logger.getLogger(ContractProcessServiceImpl.class);
	
	@Autowired
	ContractProcessDao contractProcessDao;

	@Override
	public boolean addContractProcess(ContractProcess contractProcess)
			throws GPSException {
		throw new GPSException("method not implemented...");
	}
	
	
	@Override
	public ContractProcess getContractProcess(Integer contractId, Integer processId) throws GPSException {
		log.debug("get ContractProcess for contractId = "+contractId+" & processId = "+processId);
		ContractProcess contractProcess = null;
		try{
			contractProcess = contractProcessDao.getContractProcess(contractId, processId);
		}catch (GPSException e){
			log.error(e.getMessage(),e);
		}
		return contractProcess;
	}

	@Override
	public ContractProcess getContractProcess(Integer contractProcessId) throws GPSException {
		log.debug("get ContractProcess for "+contractProcessId);
		return contractProcessDao.getContractProcess(contractProcessId);
	}


	@Override
	public Boolean isAnyDependents(Integer contractProcessId) throws GPSException {
		Boolean exists = false;
		Long slaCount = contractProcessDao.getSlaCount(contractProcessId);
		if(slaCount > 0L){
			exists =  true;
			return exists;
		}else {
			Long sloCount = contractProcessDao.getSloCount(contractProcessId);
			if(sloCount > 0L){
				exists =  true;
			}
		}
		return exists;
	}

	
	
	
}
