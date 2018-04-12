/**<pre> 
 *==========================================================================
 *
 * Copyright: (C) IBM Corporation 2013 -- IBM Internal Use Only
 *
 *==========================================================================
 *
 *    FILE: SessionACLServiceImpl.java
 *    CREATOR:Waqar Malik
 *    DEPT: GBS PAK
 *    DATE: 27/08/2013
 *
 *-PURPOSE-----------------------------------------------------------------
 * 
 *--------------------------------------------------------------------------
 *
 *
 *-CHANGE LOG--------------------------------------------------------------
 * 27/08/2013Waqar Malik Initial coding.
 *==========================================================================
 * </pre> */

package com.gps.service.impl;

import com.gps.dao.ContractDao;
import com.gps.dao.GpsUserDao;
import com.gps.dao.SessionAclDao;
import com.gps.exceptions.GPSException;
import com.gps.service.SessionACLService;
import com.gps.vo.GpsUser;
import com.gps.vo.SessionAcl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * This class provides implementation of SessionACLService.
 * 
 * @authorWaqar Malik
 */

@Service("sessionACLService")
public class SessionACLServiceImpl implements SessionACLService {
	private static Logger log = Logger.getLogger(SessionACLServiceImpl.class);

    @Autowired
    GpsUserDao gpsUserDao;

    @Autowired
    ContractDao contractDao;
	
	@Autowired
	SessionAclDao sessionAclDao;
	
	
	@Override
	@Transactional
	public void storeSessionAcl(List<SessionAcl> sessionAcls, String sessionId, GpsUser user) throws GPSException {
		if(sessionAcls == null || sessionAcls.size() < 0){
			return;
		}
		log.debug("got list of session ACLs to persist..........."+sessionAcls.size());
		for(SessionAcl sessionAcl : sessionAcls){
			sessionAcl.setSessionId(sessionId);
            sessionAcl.setGpsUser(gpsUserDao.getUserById(user.getUserId()));
            sessionAcl.setContract(contractDao.getContractById(sessionAcl.getContract().getContractId()));
			log.info("SID = "+sessionAcl.getSessionId()+", contract = "+sessionAcl.getContract().getContractId()+", "+sessionAcl.getActiveAccessLevel()+", "+sessionAcl.getApprovedAccessLevel());
		}
		try{
			sessionAclDao.addSessionAcl(sessionAcls);
//			sessionAclDao.batchInsertSessionAcl(sessionAcls);
			log.debug("successfully persisted session acl.");
		}catch(GPSException e){
			log.error(e.getMessage(),e);
			throw e;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new GPSException(e.getMessage(),e);
		}
		log.debug("storeSessionAcl() method finished.");
	}


	@Override
	public void dropSessionAcl(List<SessionAcl> sessionAcls) throws GPSException {
		if(sessionAcls == null || sessionAcls.size() < 0){
			return;
		}
		log.debug("got list of session ACLs to drop..........."+sessionAcls.size());
		try{
			sessionAclDao.removeSessionAcl(sessionAcls);
			log.debug("successfully droped session acl.");
		}catch(GPSException e){
			log.error(e.getMessage(),e);
			throw e;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new GPSException(e.getMessage(),e);
		}
		log.debug("dropSessionAcl() method finished.");
		
	}

	@Override
	@Transactional
	public void dropSessionAcl(String sessionId) throws GPSException {
		if(sessionId == null || sessionId.isEmpty()){
			return;
		}
		log.debug("drop session ACLs for session ..........."+sessionId);
		try{
			sessionAclDao.removeSessionAclBySession(sessionId);
			log.debug("successfully droped session acl.");
		}catch(GPSException e){
			log.error(e.getMessage(),e);
			throw e;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new GPSException(e.getMessage(),e);
		}
		log.debug("dropSessionAcl() method finished.");
		
	}

	@Override
	public List<SessionAcl> getSessionAcl(String sessionId) throws GPSException {
		List<SessionAcl> sessionAcls = null;
		if(sessionId == null || sessionId.isEmpty()){
			return sessionAcls;
		}
		log.debug("retrieving list of session ACLs for the session: "+sessionId);
		try{
			sessionAcls = sessionAclDao.getSessionAcl(sessionId);
			if(sessionAcls != null){
				log.debug("got list of stored session acl: "+sessionAcls.size());
			}
		}catch(GPSException e){
			log.error(e.getMessage(),e);
			throw e;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new GPSException(e.getMessage(),e);
		}
		log.debug("getSessionAcl() method finished.");
		return sessionAcls;
	}


	@Override
	public SessionAcl getSessionAcl(String sessionId, Integer contractId, String formType) throws GPSException {
		SessionAcl sessionAcl =  null;
		if(sessionId == null || sessionId.isEmpty()){
			return sessionAcl;
		}
		log.debug("retrieving session ACL for the session: "+sessionId+",  contractId = "+contractId);
		try{
			sessionAcl = sessionAclDao.getSessionAcl(sessionId, contractId, formType);
			if(sessionAcl != null){
				log.debug("got stored session acl: "+sessionAcl.getSessionAclId());
			}
		}catch(GPSException e){
			log.error(e.getMessage(),e);
			throw e;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new GPSException(e.getMessage(),e);
		}
		return sessionAcl;
	}


	
	

}
