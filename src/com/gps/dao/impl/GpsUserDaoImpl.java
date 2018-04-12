/**<pre> 
 *==========================================================================
 *
 * Copyright: (C) IBM Corporation 2013 -- IBM Internal Use Only
 *
 *==========================================================================
 *
 *    FILE: GpsUserDao.java
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

import com.gps.dao.GpsUserDao;
import com.gps.exceptions.GPSException;
import com.gps.vo.GpsUser;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * This class provides implementation for GpsUserDao.
 *  
 * @authorWaqar Malik
 */
@Repository
public class GpsUserDaoImpl implements GpsUserDao {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void addGpsUser(GpsUser GpsUser) throws GPSException {
		try {
			entityManager.persist(GpsUser);
		}
		catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
	}

	@Override
	public void deleteGpsUser(Integer userId) throws GPSException {
		try {
			GpsUser GpsUser = entityManager.find(GpsUser.class, userId);
			entityManager.remove(GpsUser);
		}
		catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
	}

	@Override
	public void deleteGpsUser(GpsUser GpsUser) throws GPSException {
		try {
			entityManager.remove(GpsUser);
		}
		catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
	}
	
	@Override
	public GpsUser getGpsUserByEmailNoFetch(String email) throws GPSException {
		GpsUser GpsUser = null;
		try {
			StringBuilder queryString = new StringBuilder();
			queryString.append("from GpsUser u ");
			queryString.append(" where u.email = :email ");
			TypedQuery<GpsUser> query = entityManager.createQuery(queryString.toString(), GpsUser.class);
			query.setParameter("email", email);
			GpsUser = query.getSingleResult();
		}
		catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
		return GpsUser;
	}
	
	@Override
	public GpsUser getGpsUserByEmail(String email) throws GPSException {
		GpsUser GpsUser = null;
		try {
			StringBuilder queryString = new StringBuilder();
			queryString.append("from GpsUser u ");
			queryString.append(" where u.email = :email " );
			TypedQuery<GpsUser> query = entityManager.createQuery(queryString.toString(), GpsUser.class);
			query.setParameter("email", email);
			GpsUser = query.getSingleResult();
		}
		catch(Exception e){
		 	//throw new GPSException(e.getMessage(), e);
		}
		return GpsUser;
	}
	
	@Override
	public 	List<GpsUser> listEmails() throws GPSException {
		List<GpsUser> emails = null;
		try {
			StringBuilder queryString = new StringBuilder();
			queryString.append("from GpsUser ");
			Query query = entityManager.createQuery(queryString.toString());
			emails = (List<GpsUser>) query.getResultList();
		}
		catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
		return emails;
	}
	
	public List<GpsUser> listSingleContractAcls() throws GPSException {
		List<GpsUser> acls = null; 
		StringBuilder queryString = new StringBuilder();
		queryString.append("select distinct u FROM GpsUser u JOIN u.accessControlLists acl ");
		queryString.append("order by u.email ");
		try {
			TypedQuery<GpsUser> query = entityManager.createQuery(queryString.toString(), GpsUser.class);
			acls = query.getResultList();
		}
		catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
		return acls;
	}

    @Override
    public List<GpsUser> listUsersHavingRoles() throws GPSException {
        List<GpsUser> usersWithRoles = null;
        StringBuilder queryString = new StringBuilder();
        queryString.append("select distinct u FROM GpsUser u JOIN u.userRoles ur ");
        queryString.append("order by u.email ");
        try {
            TypedQuery<GpsUser> query = entityManager.createQuery(queryString.toString(), GpsUser.class);
            usersWithRoles = query.getResultList();
        }
        catch(Exception e){
            throw new GPSException(e.getMessage(), e);
        }
        return usersWithRoles;
    }

	@Override
	public GpsUser getUserById(Integer userId) throws GPSException {
		GpsUser GpsUser = null;
		try {
			StringBuilder queryString = new StringBuilder();
			queryString.append("from GpsUser u ");
				//	+ "left join fetch u.accessControlLists acl ");
			queryString.append(" where u.userId = :userId ");
			//and acl.approvedAccessLevel not in ('-1') ");
			//queryString.append(" and acl.activeAccessLevel not in ('-1')  ");
			TypedQuery<GpsUser> query = entityManager.createQuery(queryString.toString(), GpsUser.class);
			query.setParameter("userId", userId);
			GpsUser = query.getSingleResult();
		}
		catch(Exception e){
			//throw new GPSException(e.getMessage(), e);
		}
		return GpsUser;

	}

}
