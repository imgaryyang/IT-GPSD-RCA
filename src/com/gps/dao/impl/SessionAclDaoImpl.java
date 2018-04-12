/**<pre> 
 *==========================================================================
 *
 * Copyright: (C) IBM Corporation 2013 -- IBM Internal Use Only
 *
 *==========================================================================
 *
 *    FILE: OmSloDaoImpl.java
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

package com.gps.dao.impl;

import com.gps.dao.SessionAclDao;
import com.gps.exceptions.GPSException;
import com.gps.vo.SessionAcl;
import com.gps.vo.helper.Constant;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;

/**
 * This class provides implementation for SessionAclDao.
 *  
 * @authorWaqar Malik
 */
@Repository
public class SessionAclDaoImpl implements SessionAclDao {
    private static Logger log = Logger.getLogger(SessionAclDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Resource
	private DataSource dataSource;
	private SimpleJdbcInsert simpleJdbcInsert;
	
	@Override
	public void addSessionAcl(SessionAcl sessionAcl) throws GPSException {
		try {
			entityManager.persist(sessionAcl);
		}
		catch(Exception e){
			log.error(e.getMessage(),e);
		}
		
	}
	
	@Override
	public void addSessionAcl(List<SessionAcl> sessionAcls) throws GPSException {
		Integer i = 0;
		for(SessionAcl sessionAcl  : sessionAcls){
			addSessionAcl(sessionAcl);
			if((i % Constant.SESSION_FLUSH_SIZE) == 0){
				entityManager.flush();
				entityManager.clear();
			}
			i++;
		}
	}
	
	@Override
	public void batchInsertSessionAcl(List<SessionAcl> sessionAcls) throws GPSException {
		simpleJdbcInsert =   
                new SimpleJdbcInsert(dataSource)  
                 .withTableName("SESSION_ACL")
                 .usingGeneratedKeyColumns("SESSION_ACL_ID"); 
		
		SqlParameterSource[] array = new SqlParameterSource[sessionAcls.size()]; 
		SessionAcl sessionAcl;
		for (int i = 0; i < sessionAcls.size(); i++) {			
			sessionAcl = (SessionAcl)sessionAcls.get(i);						
			array[i] = new MapSqlParameterSource()		
				.addValue("CONTRACT_ID", sessionAcl.getContract().getContractId(), Types.INTEGER)
				.addValue("ACTIVE_ACCESS_LEVEL", sessionAcl.getActiveAccessLevel(), Types.INTEGER)
				.addValue("APPROVED_ACCESS_LEVEL", sessionAcl.getApprovedAccessLevel(), Types.INTEGER)
				.addValue("FORM_TYPE", sessionAcl.getFormType(), Types.VARCHAR)
				.addValue("SESSION_ID", sessionAcl.getSessionId(), Types.VARCHAR);
		}
		simpleJdbcInsert.executeBatch(array);
	}

	@Override
	public void updateSessionAcl(SessionAcl sessionAcl) throws GPSException {
		try {
			entityManager.merge(sessionAcl);
		}
		catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
		
	}
	
	@Override
	public void removeSessionAcl(SessionAcl sessionAcl) throws GPSException {
		try {
			entityManager.remove(sessionAcl);
		}
		catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
	}
	
	@Override
	public void removeSessionAcl(List<SessionAcl> sessionAcls) throws GPSException {
		for(SessionAcl sessionAcl  : sessionAcls){
			removeSessionAcl(sessionAcl);
		}
	}

	@Override
	public Integer removeSessionAclBySession(String sessionId) throws GPSException {
		Integer rows = 0;
		StringBuilder queryString = new StringBuilder();
		queryString.append("delete SessionAcl sa where sa.sessionId = :sessionId");
		try {
			Query query  = entityManager.createQuery(queryString.toString());
			query.setParameter("sessionId", sessionId);
			rows = query.executeUpdate();
		}
		catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
		
		return rows;
	}
	
	@Override
	public List<SessionAcl> getSessionAcl(String sessionId) throws GPSException{
		List<SessionAcl> listSessionAcl = null;
		StringBuilder queryString = new StringBuilder();
		queryString.append("from SessionAcl sa ");
		queryString.append(" where sa.sessionId = :sessionId ");
		try {
			Query query  = entityManager.createQuery(queryString.toString());
			query.setParameter("sessionId", sessionId);
			listSessionAcl = (List<SessionAcl> ) query.getResultList();
		}
		catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
		return listSessionAcl;
	}

	@Override
	public SessionAcl getSessionAcl(String sessionId, Integer contractId, String formType) throws GPSException {
		SessionAcl sessionAcl = null;
		StringBuilder queryString = new StringBuilder();
		queryString.append("from SessionAcl sa ");
		queryString.append(" where sa.sessionId = :sessionId ");
		queryString.append(" and sa.contract.contractId = :contractId ");
		queryString.append(" and sa.formType = :formType ");
		try {
			Query query  = entityManager.createQuery(queryString.toString());
			query.setParameter("sessionId", sessionId);
			query.setParameter("contractId", contractId);
			query.setParameter("formType", formType);
			sessionAcl = (SessionAcl) query.getSingleResult();
		}
		catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
		return sessionAcl;
	}

	
}
