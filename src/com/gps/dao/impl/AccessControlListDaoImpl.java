/**<pre> 
 *==========================================================================
 *
 * Copyright: (C) IBM Corporation 2013 -- IBM Internal Use Only
 *
 *==========================================================================
 *
 *    FILE: AccessControlListDaoImpl.java
 *    CREATOR:Waqar Malik
 *    DEPT: GBS PAK
 *    DATE: 19/07/2013
 *
 *-PURPOSE-----------------------------------------------------------------
 * 
 *--------------------------------------------------------------------------
 *
 *
 *-CHANGE LOG--------------------------------------------------------------
 * 19/07/2013Waqar Malik Initial coding.
 *==========================================================================
 * </pre> */

package com.gps.dao.impl;

import com.gps.dao.AccessControlListDao;
import com.gps.exceptions.GPSException;
import com.gps.vo.AccessControlList;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * This class provides implementation for AccessControlListDao.
 *  
 * @author Waqar malik
 */
@Repository
public class AccessControlListDaoImpl implements AccessControlListDao {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void addAccessControlList(AccessControlList acl) throws GPSException {
		try {
			entityManager.persist(acl);
		}
		catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
	}
	
	@Override
	public void updateAccessControlList(AccessControlList acl) throws GPSException {
		try {
			entityManager.merge(acl);
		}
		catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
	}

	@Override
	public void deleteAccessControlList(AccessControlList acl) throws GPSException {
		try {
			entityManager.remove(acl);
		}
		catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
		
	}
	
	@Override
	public void deleteAccessControlList(Integer aclId) throws GPSException {
		try {
			AccessControlList acl = entityManager.find(AccessControlList.class, aclId);
			entityManager.remove(acl);
		}
		catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<AccessControlList> getACLByUser(Integer userId) throws GPSException {
		List<AccessControlList> acls = null; 
		StringBuilder queryString = new StringBuilder("FROM AccessControlList acl ");
		queryString.append(" where acl.bpdUser.userId = :userId ");
		try {
			Query query = entityManager.createQuery(queryString.toString());
			query.setParameter("userId", userId);
			acls = query.getResultList();
		}
		catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
		return acls;
	}

	@Override
	public List<AccessControlList> listSingleContractAcls() throws GPSException {
		List<AccessControlList> acls = null; 
		StringBuilder queryString = new StringBuilder("FROM AccessControlList acl  LEFT JOIN FETCH acl.bpdUser u ");
		queryString.append(" where acl.contract != null ");
		queryString.append(" order by u.email, acl.formType ");
		try {
			TypedQuery<AccessControlList> query = entityManager.createQuery(queryString.toString(), AccessControlList.class);
			acls = query.getResultList();
		}
		catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
		return acls;
	}
	
	@Override
	public List<AccessControlList> listNonContractAcls() throws GPSException {
		List<AccessControlList> acls = null; 
		StringBuilder queryString = new StringBuilder("FROM AccessControlList acl LEFT JOIN FETCH acl.bpdUser u ");
		queryString.append(" where acl.contract = null ");
		queryString.append(" order by u.email, acl.formType ");
		try {
			TypedQuery<AccessControlList> query = entityManager.createQuery(queryString.toString(), AccessControlList.class);
			acls = query.getResultList();
		}
		catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
		return acls;
	}

	@Override
	public List<AccessControlList> getAclsByUserId(Integer userId) throws GPSException {
		List<AccessControlList> acls = null; 
		StringBuilder queryString = new StringBuilder();
		queryString.append("FROM AccessControlList acl LEFT JOIN FETCH acl.gpsUser u ");
		queryString.append("LEFT JOIN FETCH acl.contract c ");
		queryString.append(" where u.userId = :userId ");
		queryString.append(" order by u.email, acl.formType ");
		try {
			TypedQuery<AccessControlList> query = entityManager.createQuery(queryString.toString(), AccessControlList.class);
			query.setParameter("userId", userId);
			acls = query.getResultList();
		}
		catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
		return acls;
	}

	@Override
	public AccessControlList getACLById(Integer aclId) throws GPSException {
		AccessControlList acl = null; 
		StringBuilder queryString = new StringBuilder("FROM AccessControlList acl LEFT JOIN FETCH acl.gpsUser u ");
		queryString.append("  where acl.aclId = :aclId  ");
		try {
			TypedQuery<AccessControlList> query = entityManager.createQuery(queryString.toString(), AccessControlList.class);
			query.setParameter("aclId", aclId);
			acl = query.getSingleResult();
		}
		catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
		return acl;
	}

	
	
}
