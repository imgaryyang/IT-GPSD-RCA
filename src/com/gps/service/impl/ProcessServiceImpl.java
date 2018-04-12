/**<pre> 
 *==========================================================================
 *
 * Copyright: (C) IBM Corporation 2013 -- IBM Internal Use Only
 *
 *==========================================================================
 *
 *    FILE: ProcessServiceImpl.java
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

import com.gps.dao.ProcessDao;
import com.gps.exceptions.GPSException;
import com.gps.service.ProcessService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class provides implementation of ProcessService.
 * 
 * @authorWaqar Malik
 */
@Service
public class ProcessServiceImpl implements ProcessService {
	private static final Logger log = Logger.getLogger(ProcessServiceImpl.class);
	
	@Autowired
	ProcessDao processDao;

	@Override
	public com.gps.vo.Process getProcess(Integer processId) throws GPSException {
		return processDao.getProcess(processId);
	}

	@Override
	public List<com.gps.vo.Process> getProcessList(Integer contractId) throws GPSException {
		return processDao.getProcessList();
	}
	
	
}
