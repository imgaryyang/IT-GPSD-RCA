package com.gps.dao.impl;

import com.gps.dao.UserRoleDao;
import com.gps.exceptions.GPSException;
import com.gps.vo.UserRole;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Waqar Malik
 */
@Repository
public class UserRoleDaoImpl implements UserRoleDao {
    private static final Logger log = Logger.getLogger(UserRoleDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void addUserRole(UserRole userRole) {
        try {
            entityManager.persist(userRole);
        } catch (Exception e) {
            throw new GPSException(e.getMessage(), e);
        }

    }

    @Override
    public void updateUserRole(UserRole userRole) throws GPSException {
        try {
            entityManager.merge(userRole);
        } catch (Exception e) {
            throw new GPSException(e.getMessage(), e);
        }
    }

    @Override
    public Integer getMaxUserRoleId() throws GPSException {
        Integer maxId = null;
        try {
            TypedQuery<Integer> query = entityManager.createQuery("select max(urId) from UserRole", Integer.class);
            maxId = query.getSingleResult();
        } catch (Exception e) {
            throw new GPSException(e.getMessage(), e);
        }
        return maxId;
    }

    @Override
    public List<UserRole> getUserRolesByUser(Integer userId) throws GPSException {
        StringBuilder builder = new StringBuilder();
        List<UserRole> userRoles = null;
        try {
            builder.append("SELECT ur FROM UserRole ur LEFT JOIN FETCH ur.contract c LEFT JOIN FETCH ur.gpsUser u ");
            builder.append("WHERE ur.isEnabled = 'Y' AND u.userId=:Id ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("Id", userId);
            userRoles = (List<UserRole>) query.getResultList();
        } catch (Exception e) {
            throw new GPSException(e.getMessage(), e);
        }
        return userRoles;
    }

    @Override
    public UserRole getUserRoleById(Integer userRoleId) throws GPSException {
        UserRole ur = null;
        StringBuilder queryString = new StringBuilder("FROM UserRole ur LEFT JOIN FETCH ur.gpsUser u LEFT JOIN FETCH ur.contract c ");
        queryString.append("  where ur.isEnabled = 'Y' AND ur.urId = :Id  ");
        try {
            TypedQuery<UserRole> query = entityManager.createQuery(queryString.toString(), UserRole.class);
            query.setParameter("Id", userRoleId);
            ur = query.getSingleResult();
        } catch (Exception e) {
            throw new GPSException(e.getMessage(), e);
        }
        return ur;
    }

    @Override
    public void deleteUserRole(UserRole userRole) throws GPSException {
        try {
            entityManager.remove(userRole);
        } catch (Exception e) {
            throw new GPSException(e.getMessage(), e);
        }
    }

    @Override
    public void softDeleteUserRole(UserRole userRole) throws GPSException {
        try {
            entityManager.merge(userRole);
        } catch (Exception e) {
            throw new GPSException(e.getMessage(), e);
        }
    }
    
    @Override
    public List<UserRole> getUserRolesByContractIdAndRole(Integer contractId, String role) {
        StringBuilder builder = new StringBuilder();
        List<UserRole> userRoles = null;
        try {

            builder.append("SELECT ur FROM UserRole ur LEFT JOIN FETCH ur.contract c LEFT JOIN FETCH ur.gpsUser u ");
            builder.append("WHERE ur.role=:role AND ur.isEnabled = 'Y' ");
            builder.append("and c.contractId=:contractId");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("role", role);
            query.setParameter("contractId", contractId);
            userRoles = (List<UserRole>) query.getResultList();
        } catch (Exception e) {
            throw new GPSException(e.getMessage(), e);
        }
        return userRoles;
    }

    @Override
    public List<UserRole> getUserRolesByContractIdAndUserId(Integer contractId, Integer userId) {
        StringBuilder builder = new StringBuilder();
        List<UserRole> userRoles = null;
        try {

            builder.append("SELECT ur FROM UserRole ur LEFT JOIN FETCH ur.contract c LEFT JOIN FETCH ur.gpsUser u ");
            builder.append("WHERE u.userId=:userId AND ur.isEnabled = 'Y' ");
            builder.append("AND c.contractId=:contractId");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("userId", userId);
            query.setParameter("contractId", contractId);
            userRoles = (List<UserRole>) query.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return userRoles;
    }

    @Override
    public UserRole getUserRolesByContractIdAndUserIdAndRole(Integer contractId, Integer userId, String role) {
        StringBuilder builder = new StringBuilder();
        UserRole userRole = null;
        try {

            builder.append("SELECT ur FROM UserRole ur LEFT JOIN FETCH ur.contract c LEFT JOIN FETCH ur.gpsUser u ");
            builder.append("WHERE u.userId=:userId AND ur.isEnabled = 'Y'  ");
            builder.append("AND c.contractId=:contractId ");
            builder.append("AND ur.role=:role");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("userId", userId);
            query.setParameter("contractId", contractId);
            query.setParameter("role", role);
            userRole = (UserRole) query.getSingleResult();
        } catch (Exception e) {
           // log.error(e.getMessage(), e);
        }
        return userRole;
    }

    @Override
    public List<UserRole> getUserRolesByRoleName(String role) {
        StringBuilder builder = new StringBuilder();
        List<UserRole> userRoles = null;
        try {

            builder.append("SELECT ur FROM UserRole ur LEFT JOIN FETCH ur.contract c LEFT JOIN FETCH ur.gpsUser u ");
            builder.append("WHERE ur.role=:role  AND ur.isEnabled = 'Y' ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("role", role);
            userRoles = (List<UserRole>) query.getResultList();
        } catch (Exception e) {
            throw new GPSException(e.getMessage(), e);
        }
        return userRoles;
    }

    @Override
    public UserRole findGlobalRole(Integer userId, String role) {
        StringBuilder builder = new StringBuilder();
        UserRole userRole = null;
        try {

            builder.append("SELECT ur FROM UserRole ur JOIN ur.gpsUser u ");
            builder.append("WHERE u.userId=:userId  AND ur.isEnabled = 'Y' ");
            builder.append("AND ur.role=:role ");
            builder.append("AND ur.contract is null ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("userId", userId);
            query.setParameter("role", role);
            userRole = (UserRole) query.getSingleResult();
        } catch (Exception e) {
          log.error(e.getMessage(), e);
        }
        return userRole;
    }

}
