/**<pre> 
 *==========================================================================
 *
 * Copyright: (C) IBM Corporation 2013 -- IBM Internal Use Only
 *
 *==========================================================================
 *
 *    FILE: ProcessDaoImpl.java
 *    CREATOR:Waqar Malik
 *    DEPT: GBS PAK
 *    DATE: 17/04/2013
 *
 *-PURPOSE-----------------------------------------------------------------
 * 
 *--------------------------------------------------------------------------
 *
 *
 *-CHANGE LOG--------------------------------------------------------------
 * 17/04/2013Waqar Malik Initial coding.
 *==========================================================================
 * </pre> */

package com.gps.dao.impl;

import com.gps.dao.ProcessDao;
import com.gps.exceptions.GPSException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * This class provides implementation for ProcessDao.
 * 
 * @authorWaqar Malik
 */
@Repository
public class ProcessDaoImpl implements ProcessDao {
	private static Logger log = Logger.getLogger(ProcessDaoImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;

	
	@Override
	public com.gps.vo.Process getProcess(Integer processId) throws GPSException {
		com.gps.vo.Process process = null;
		try {
			process = entityManager.find(com.gps.vo.Process.class, processId);
		} catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
		return process;
	}

	@Override
	public List<com.gps.vo.Process> getProcessList() throws GPSException {
		List<com.gps.vo.Process> processList = null;
		try {
			Query query = entityManager.createQuery("from Process p");
			processList = (List<com.gps.vo.Process>) query.getResultList();
			log.debug("got process's list of "+processList.size());
		} catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
		return processList;
	}


}
